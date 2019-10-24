package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.dto.AdminUser;
import com.moguying.plant.core.entity.dto.SeedOrder;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;
import com.moguying.plant.core.entity.dto.SessionAdminUser;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.core.service.seed.SeedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/backEnd/seedOrder")
public class BSeedOrderController {

    @Autowired
    private SeedOrderService seedOrderService;

    @Autowired
    private SeedOrderDetailService seedOrderDetailService;

    /**
     * 菌包统计列表
     *
     * @param search
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageResult<SeedOrder> seedOrderList(@RequestBody PageSearch<SeedOrder> search) {
        return seedOrderService.seedOrderList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 下载菌包统计列表
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/excel")
    @ResponseBody
    public ResponseData<Integer> seedOrderListExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                    @RequestBody PageSearch<SeedOrder> search, HttpServletRequest request){
        if(Objects.isNull(search.getWhere()))
            search.setWhere(new SeedOrder());
        seedOrderService.downloadExcel(user.getId(),search,request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 菌包订单支付列表
     * @return
     */
    @PostMapping(value = "/pay/list")
    @ResponseBody
    public PageResult<SeedOrderDetail> seedOrderDetailList(@RequestBody PageSearch<SeedOrderDetail> search){
        return seedOrderDetailService.seedOrderDetailList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 菌包支付订单下载
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping("/pay/list/excel")
    @ResponseBody
    public ResponseData<Integer> seedPayOrderListExcel(@SessionAttribute(SessionAdminUser.sessionKey)AdminUser user,
                                                       @RequestBody PageSearch<SeedOrderDetail> search, HttpServletRequest request){
        if(Objects.isNull(search.getWhere()))
            search.setWhere(new SeedOrderDetail());
        seedOrderDetailService.downloadExcel(user.getId(),search,request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
    }


}
