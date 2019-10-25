package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MallEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.mall.MallCompany;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.vo.MallOrderSearch;
import com.moguying.plant.core.service.mall.MallCompanyService;
import com.moguying.plant.core.service.mall.MallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/backEnd/mall/order")
public class BMallOrderController {

    @Autowired
    private MallOrderService mallOrderService;

    @Autowired
    private MallCompanyService mallCompanyService;

    /**
     * 商城订单列表
     *
     * @param search
     * @return
     */
    @PostMapping
    @ResponseBody
    public PageResult<MallOrder> mallOrderList(@RequestBody MallOrderSearch search) {
        return mallOrderService.mallOrderList(search.getPage(), search.getSize(), search);
    }


    /**
     * 商城订单详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseData<MallOrder> mallOrderDetail(@PathVariable Integer id) {
        if (null == id || id <= 0)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), mallOrderService.mallOrderDetail(id));
    }


    /**
     * 确认发货
     */
    @PutMapping
    @ResponseBody
    public ResponseData<Integer> addLogistics(@RequestBody MallOrder mallOrder) {

        if (null == mallOrder || mallOrder.getId() <= 0 || null == mallOrder.getExpressOrderNumber() || null == mallOrder.getExpressComCode())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        MallOrder order = mallOrderService.selectOrderById(mallOrder.getId());
        if (null == order)
            return new ResponseData<>(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage(), MessageEnum.MALL_ORDER_NOT_EXISTS.getState());

        if (!order.getState().equals(MallEnum.ORDER_HAS_PAY.getState()))
            return new ResponseData<>(MessageEnum.MALL_ORDER_NO_PAY_STATE.getMessage(), MessageEnum.MALL_ORDER_NO_PAY_STATE.getState());

        // 封装数据
        mallOrder.setState(MallEnum.ORDER_HAS_SEND.getState());
        mallOrder.setSendTime(new Date());

        if (mallOrderService.saveOrder(mallOrder) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }

    /**
     * 显示快递公司名称
     */
    @PostMapping("/getAllComName")
    @ResponseBody
    public ResponseData<List<MallCompany>> getAllComName() {
        List<MallCompany> mallCompanies = mallCompanyService.getAllComName();
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), mallCompanies);
    }

}
