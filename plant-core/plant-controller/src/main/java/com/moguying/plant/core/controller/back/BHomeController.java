package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.common.vo.BHomeTopTotal;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/home")
@Api(tags = "首页统计")
public class BHomeController {

    @Autowired
    UserService userService;
    @Autowired
    MallOrderService mallOrderService;
    @Autowired
    ReapService reapService;

    /**
     * 首页顶部统计
     */
    @GetMapping("/top/total")
    @ApiOperation("顶部统计")
    public ResponseData<BHomeTopTotal> homeTopTotal() {
        BHomeTopTotal homeTopTotal = new BHomeTopTotal();
        homeTopTotal.setRegNum(userService.regUserTotal());
        homeTopTotal.setOrderNum(mallOrderService.getMallOrderNum());
        homeTopTotal.setPlantLines(reapService.getPlantLines());
        homeTopTotal.setPlantProfits(reapService.getPlantProfits());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), homeTopTotal);
    }


}
