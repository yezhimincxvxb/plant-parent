package com.moguying.plant.core.controller.api;

import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taste")
public class ATasteController {

    @PostMapping
    public ResponseData<SeedOrderDetail> buy(@RequestBody BuyOrder buyOrder) {
        return null;
    }
}
