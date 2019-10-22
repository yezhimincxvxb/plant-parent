package com.moguying.plant.core.service.seed.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.dto.Block;
import com.moguying.plant.core.entity.dto.Seed;
import com.moguying.plant.core.entity.dto.SeedOrder;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;
import com.moguying.plant.core.entity.vo.CanPlantOrder;
import com.moguying.plant.core.entity.vo.UserSeedOrder;
import com.moguying.plant.core.service.seed.SeedOrderService;
import com.moguying.plant.utils.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class SeedOrderServiceImpl implements SeedOrderService {

    @Autowired
    private SeedOrderDAO seedOrderDAO;

    @Autowired
    private BlockDAO blockDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SeedDAO seedDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<SeedOrder> seedOrderList(Integer page, Integer size, SeedOrder where) {
        seedOrderDAO.selectSelective(where);
        return null;
    }

    @Override
    @DataSource("write")
    public synchronized Boolean incrSeedOrder(SeedOrderDetail seedOrderDetail) {

        //查找菌包
        Seed seed = seedDAO.selectById(seedOrderDetail.getSeedId());
        if(null == seed)
            return false;
        //个人一种seedType只有一条记录
        SeedOrder where = new SeedOrder();
        where.setUserId(seedOrderDetail.getUserId());
        where.setSeedType(seed.getSeedType());
        List<SeedOrder> seedOrders = seedOrderDAO.selectSelective(where);

        if(seedOrders == null || seedOrders.size() <= 0){
            SeedOrder add = new SeedOrder();
            add.setBuyAmount(seedOrderDetail.getBuyAmount());
            add.setBuyCount(seedOrderDetail.getBuyCount());
            add.setSeedType(seed.getSeedType());
            add.setUserId(seedOrderDetail.getUserId());
            return seedOrderDAO.insert(add) > 0;
        } else {
            SeedOrder incr = new SeedOrder();
            incr.setBuyCount(seedOrderDetail.getBuyCount());
            incr.setBuyAmount(seedOrderDetail.getBuyAmount());
            incr.setSeedType(seed.getSeedType());
            incr.setUserId(seedOrderDetail.getUserId());
            return seedOrderDAO.incrSeedOrder(incr) > 0;
        }
    }

    @Override
    @DataSource("read")
    public CanPlantOrder sumUserSeedByBlockId(Integer blockId, Integer userId) {
        Block block = blockDAO.selectById(blockId);
        if(null == block)
            return null;
        return seedOrderDAO.sumSeedCountBySeedType(block.getSeedType(),userId);
    }

    @Override
    @DataSource("read")
    public List<UserSeedOrder> userSeedOrder(Integer userId) {
        return seedOrderDAO.userSeedOrderStatistics(userId);
    }

    @Override
    @DataSource("read")
    public void downloadExcel(Integer userId, PageSearch<SeedOrder> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("菌包统计", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadUtil<>(seedOrderDAO, search, SeedOrder.class, downloadInfo)).start();
    }
}
