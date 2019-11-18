package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.seed.vo.SeedInnerBuy;
import com.moguying.plant.core.entity.seed.SeedInnerOrderCount;
import com.moguying.plant.core.entity.user.UserInner;
import com.moguying.plant.core.service.seed.SeedInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seed/inner")
public class BSeedInnerController {


    @Autowired
    SeedInnerService seedInnerService;


    /**
     * 内购列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    public PageResult<SeedInnerOrderCount> innerList(@RequestParam Integer page, @RequestParam Integer size) {
        return seedInnerService.seedInnerList(page, size);
    }


    /**
     * 内购人员
     *
     * @param seedId
     * @param innerCount
     * @param userCount
     * @return
     */
    @GetMapping(value = "/user/list")
    public ResponseData<List<UserInner>> innerUserList(@RequestParam(name = "seed_id") Integer seedId, @RequestParam("icount") Integer innerCount, @RequestParam(name = "ucount") Integer userCount) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                seedInnerService.seedInnerUserList(seedId, innerCount, userCount));
    }


    /**
     * 内购订单
     *
     * @param seedInnerBuy
     * @return
     */
    @PostMapping(value = "/order")
    public ResponseData<Integer> innerOrder(@RequestBody SeedInnerBuy seedInnerBuy) {

        Integer count = -1;

        if (seedInnerBuy.getSeedId() <= 0 || seedInnerBuy.getUserInners() == null || seedInnerBuy.getUserInners().size() <= 0) {
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        }
        if ((count = seedInnerService.seedInnerOrder(seedInnerBuy)) == 0) {
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), count);
        }

        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), count);
    }


}
