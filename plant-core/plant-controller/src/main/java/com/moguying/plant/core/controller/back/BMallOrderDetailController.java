package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.vo.OrderDetailSearch;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.mall.MallOrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mall/detail")
@Api(tags = "商城订单详情管理")
public class BMallOrderDetailController {

    @Autowired
    private MallOrderDetailService orderDetailService;

    /**
     * 商城订单详情列表
     *
     * @param search
     * @return
     */
    @PostMapping
    @ApiOperation("商城订单详情列表")
    public PageResult<MallOrderDetail> mallOrderList(@RequestBody PageSearch<MallOrderDetail> search) {
        return orderDetailService.mallOrderDetailList(search.getPage(), search.getSize(), search.getWhere());
    }

    /**
     * 导出表
     *
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/excel")
    @ApiOperation("商城订单详情导出表")
    public ResponseData<Integer> downloadExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                               PageSearch<MallOrderDetail> search, HttpServletRequest request) {
        orderDetailService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


}
