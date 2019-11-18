package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.ActivityEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserActivityLogDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
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
import java.util.stream.Collectors;

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
    @Transactional
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
        return seedOrderDAO.userSeedOrderStatistics(userId).stream().filter(x -> x.getCount() > 0).collect(Collectors.toList());
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
    public ResultData<Integer> sendSeedSuccess(Integer userId) {

        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);

        // 奖励只发送一次
        QueryWrapper<UserActivityLog> wrapper = new QueryWrapper<UserActivityLog>()
                .eq("user_id", userId)
                .likeRight("number", OrderPrefixEnum.FREE_JUN_BAO.getPreFix());
        List<UserActivityLog> logs = userActivityLogDAO.selectList(wrapper);
        if (Objects.nonNull(logs) && logs.size() > 0)
            return resultData.setMessageEnum(MessageEnum.PICK_UP_SEED);

        // 发菌包
        Seed seed = getSeedType(userId);
        if (Objects.isNull(seed))
            return resultData.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);

        // 添加活动奖励记录
        UserActivityLog log = new UserActivityLog()
                .setNumber(OrderPrefixEnum.FREE_JUN_BAO.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate())
                .setUserId(userId)
                .setSeedTypeId(seed.getId())
                .setState(true)
                .setAddTime(new Date())
                .setReceiveTime(new Date());
        if (userActivityLogDAO.insert(log) <= 0) return resultData;
        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }

    @Override
    @DS("write")
    @Transactional
    public Seed getSeedType(Integer userId) {
        // 获取价值12.50元的30天菌包
        Seed seed = seedDAO.selectById(ActivityEnum.FREE_SEED_30DAY.getState());
        if (Objects.isNull(seed)) return null;

        // 是否购买过同类型的菌包
        QueryWrapper<SeedOrder> queryWrapper = new QueryWrapper<SeedOrder>()
                .eq("user_id", userId)
                .eq("seed_type", seed.getId());
        List<SeedOrder> seedOrders = seedOrderDAO.selectList(queryWrapper);
        if (Objects.isNull(seedOrders) || seedOrders.isEmpty()) {
            SeedOrder order = new SeedOrder();
            order.setSeedType(seed.getId());
            order.setUserId(userId);
            order.setBuyCount(1);
            order.setBuyAmount(seed.getPerPrice());
            if (seedOrderDAO.insert(order) <= 0) return null;
        } else {
            SeedOrder order = seedOrders.get(0);
            order.setBuyCount(order.getBuyCount() + 1);
            order.setBuyAmount(new BigDecimal(order.getBuyCount()).multiply(seed.getPerPrice()));
            if (seedOrderDAO.updateById(order) <= 0) return null;
        }
        return seed;
    }
}
