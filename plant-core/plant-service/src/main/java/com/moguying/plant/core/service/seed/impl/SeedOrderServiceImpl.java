package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserActivityLogDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.SeedOrder;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.entity.seed.vo.CanPlantOrder;
import com.moguying.plant.core.entity.user.UserActivityLog;
import com.moguying.plant.core.entity.user.vo.UserSeedOrder;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.seed.SeedOrderService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SeedOrderServiceImpl implements SeedOrderService {

    @Autowired
    private SeedOrderDAO seedOrderDAO;

    @Autowired
    private BlockDAO blockDAO;

    @Autowired
    private SeedDAO seedDAO;

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private UserActivityLogDAO userActivityLogDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Override
    @DS("read")
    public PageResult<SeedOrder> seedOrderList(Integer page, Integer size, SeedOrder where) {
        IPage<SeedOrder> pageResult = seedOrderDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("write")
    public synchronized Integer incrSeedOrder(SeedOrderDetail seedOrderDetail) {

        // 查找菌包
        Seed seed = seedDAO.selectById(seedOrderDetail.getSeedId());
        if (null == seed) return 0;
        //个人一种seedType只有一条记录
        SeedOrder where = new SeedOrder();
        where.setUserId(seedOrderDetail.getUserId());
        where.setSeedType(seed.getSeedType());
        List<SeedOrder> seedOrders = seedOrderDAO.selectList(new QueryWrapper<>(where));

        if (seedOrders == null || seedOrders.size() <= 0) {
            SeedOrder add = new SeedOrder();
            add.setBuyAmount(seedOrderDetail.getBuyAmount());
            add.setBuyCount(seedOrderDetail.getBuyCount());
            add.setSeedType(seed.getSeedType());
            add.setUserId(seedOrderDetail.getUserId());
            return seedOrderDAO.insert(add) > 0 ? add.getId() : 0;
        } else {
            SeedOrder incr = new SeedOrder();
            incr.setBuyCount(seedOrderDetail.getBuyCount());
            incr.setBuyAmount(seedOrderDetail.getBuyAmount());
            incr.setSeedType(seed.getSeedType());
            incr.setUserId(seedOrderDetail.getUserId());
            return seedOrderDAO.incrSeedOrder(incr) > 0 ? seedOrders.get(0).getId() : 0;
        }
    }

    @Override
    @DS("read")
    public CanPlantOrder sumUserSeedByBlockId(Integer blockId, Integer userId) {
        Block block = blockDAO.selectById(blockId);
        if (null == block)
            return null;
        return seedOrderDAO.sumSeedCountBySeedType(block.getSeedType(), userId);
    }

    @Override
    @DS("read")
    public List<UserSeedOrder> userSeedOrder(Integer userId) {
        return seedOrderDAO.userSeedOrderStatistics(userId);
    }

    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<SeedOrder> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("菌包统计", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(seedOrderDAO, search, SeedOrder.class, downloadInfo)).start();
    }

    @Override
    @DS("write")
    @Transactional
    public Boolean sendSeedSuccess(Integer userId) {

        SeedType seedType = getSeedType(userId);
        if (Objects.isNull(seedType)) return false;

        // 添加活动奖励记录
        UserActivityLog log = new UserActivityLog()
                .setNumber(OrderPrefixEnum.FREE_JUN_BAO.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate())
                .setUserId(userId)
                .setSeedTypeId(seedType.getId())
                .setAddTime(new Date());
        return userActivityLogDAO.insert(log) > 0;
    }

    @Override
    public SeedType getSeedType(Integer userId) {
        // 获取价值12.50元的30天菌包
        SeedType seedType = seedTypeDAO.selectById(12);
        if (Objects.isNull(seedType)) return null;

        // 发送菌包
        SeedOrder seedOrder = new SeedOrder();
        seedOrder.setUserId(userId);
        seedOrder.setSeedType(seedType.getId());

        // 是否购买过同类型的菌包
        List<SeedOrder> seedOrders = seedOrderDAO.selectSelective(seedOrder);
        if (Objects.isNull(seedOrders) || seedOrders.isEmpty()) {
            SeedOrder order = new SeedOrder();
            order.setSeedType(seedOrder.getSeedType());
            order.setUserId(seedOrder.getUserId());
            order.setBuyCount(1);
            order.setBuyAmount(seedType.getPerPrice());
            if (seedOrderDAO.insert(order) <= 0) return null;
        } else if (seedOrders.size() == 1) {
            SeedOrder order = seedOrders.get(0);
            order.setBuyCount(order.getBuyCount() + 1);
            order.setBuyAmount(new BigDecimal(order.getBuyCount()).multiply(seedType.getPerPrice()));
            if (seedOrderDAO.updateById(order) <= 0) return null;
        }
        return seedType;
    }
}
