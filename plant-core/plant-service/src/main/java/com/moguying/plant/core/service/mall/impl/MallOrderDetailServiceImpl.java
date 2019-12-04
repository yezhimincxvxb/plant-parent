package com.moguying.plant.core.service.mall.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.mall.MallOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MallOrderDetailServiceImpl implements MallOrderDetailService {

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Override
    @DS("read")
    public List<OrderItem> orderItemListByOrderIdAndUserId(Integer orderId, Integer userId) {
        return mallOrderDetailDAO.selectDetailListByOrderId(orderId, userId);
    }

    @Override
    @DS("read")
    public PageResult<MallOrderDetail> mallOrderDetailList(Integer page, Integer size, MallOrderDetail search) {
        IPage<MallOrderDetail> pageResult = mallOrderDetailDAO.selectSelective(new Page<>(page, size), search);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<MallOrderDetail> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("订单详情表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(mallOrderDetailDAO, search, MallOrderDetail.class, downloadInfo)).start();
    }

}
