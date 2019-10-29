package com.moguying.plant.core.controller.api;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.core.service.mall.MallProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 砍价
 */
@RestController
@RequestMapping("/bargain")
@Slf4j
public class ABargainController {

    @Autowired
    private BargainDetailService bargainDetailService;

    @Autowired
    private MallProductService mallProductService;

    /**
     * 砍价成功记录(所有用户)
     */
    @PostMapping("/success/logs")
    public PageResult<BargainVo> successLogs(@RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.successLogs(pageSearch.getPage(), pageSearch.getSize());
    }

    /**
     * 砍价产品列表
     */
    @PostMapping("/product/list")
    public PageResult<BargainVo> productList(@RequestBody PageSearch<?> pageSearch) {

        PageResult<BargainVo> pageResult = mallProductService.productList(pageSearch.getPage(), pageSearch.getSize());

        List<BargainVo> bargainList = pageResult.getData();
        if (bargainList == null || bargainList.isEmpty()) return pageResult.setData(null);

        // 移除限量商品数量不足
        bargainList.removeIf(bargain -> bargain.getIsLimit().equals(1) && bargain.getTotalNumber() < bargain.getProductCount());

        // 没有送出数量
        List<SendNumberVo> sendNumbers = bargainDetailService.sendNumber();
        if (sendNumbers == null || sendNumbers.isEmpty()) {
            bargainList.forEach(bargain -> {
                bargain.setSendNumber(0);
                if (bargain.getIsLimit().equals(1)) bargain.setLeftNumber(bargain.getTotalNumber());
            });
            return pageResult.setData(bargainList);
        }

        // 设置送出数量
        bargainList.forEach(bargain -> {
            Integer productCount = sendNumbers.stream()
                    .filter(Objects::nonNull)
                    .filter(sendNumber -> bargain.getProductId().equals(sendNumber.getProductId()))
                    .map(SendNumberVo::getProductCount)
                    .findFirst()
                    .orElse(0);
            bargain.setSendNumber(productCount);
        });

        // 剩余数量
        bargainList.stream()
                .filter(Objects::nonNull)
                .filter(bargain -> bargain.getIsLimit().equals(1))
                .forEach(bargain -> {
                    bargain.setLeftNumber(bargain.getTotalNumber() - bargain.getSendNumber());
                });

        return pageResult.setData(bargainList);
    }

    /**
     * 砍价产品详情
     */
    @GetMapping("/product/info/{productId}")
    public ResponseData<BargainVo> productInfo(@PathVariable("productId") Integer productId) {

        ResponseData<BargainVo> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), null);

        BargainVo bargain = mallProductService.productInfo(productId);
        if (bargain == null) return responseData;

        // 商品获得人数
        Integer number = bargainDetailService.getNumber(productId);
        bargain.setSendNumber(number);

        return responseData
                .setMessage(MessageEnum.SUCCESS.getMessage())
                .setState(MessageEnum.SUCCESS.getState())
                .setData(bargain);
    }

}
