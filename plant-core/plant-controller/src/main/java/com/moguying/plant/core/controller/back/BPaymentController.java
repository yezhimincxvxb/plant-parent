package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.payment.PaymentInfo;
import com.moguying.plant.core.service.payment.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Slf4j
@Api(tags = "支付管理")
public class BPaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/list")
    @ApiOperation("支付列表")
    public PageResult<PaymentInfo> reapList(@RequestBody PageSearch<PaymentInfo> search) {
        PaymentInfo where = search.getWhere();
        if (where == null) where = new PaymentInfo();
        return paymentService.paymentList(search.getPage(), search.getSize(), where);
    }

}
