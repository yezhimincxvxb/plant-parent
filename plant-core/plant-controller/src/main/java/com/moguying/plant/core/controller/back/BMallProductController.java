package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.service.mall.MallProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/mall")
@Api(tags = "商城商品管理")
public class BMallProductController {


    @Autowired
    MallProductService productService;


    @GetMapping("/{id}")
    @ApiOperation("商品详情")
    public ResponseData<MallProduct> productDetail(@PathVariable Integer id) {
        ResultData<MallProduct> resultData = productService.productDetail(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState()
                , resultData.getData());
    }


    /**
     * 添加商品
     *
     * @param product
     * @return
     */
    @PostMapping
    @ApiOperation("添加商品")
    public ResponseData<Integer> addProduct(@RequestBody MallProduct product) {
        ResultData<Integer> resultData = productService.saveProduct(product);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 更新商品
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation("更新商品")
    public ResponseData<Integer> updateProduct(@PathVariable Integer id, @RequestBody MallProduct product) {
        product.setId(id);
        ResultData<Integer> resultData = productService.saveProduct(product);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 对商品上下架
     *
     * @param id
     * @return
     */
    @PutMapping("/show/{id}")
    @ApiOperation("对商品上下架")
    public ResponseData<Integer> showProduct(@PathVariable Integer id) {
        if (productService.showProduct(id))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 商品列表
     *
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("商品列表")
    public PageResult<MallProduct> productList(@RequestBody PageSearch<MallProduct> search) {
        return productService.productList(search.getPage(), search.getSize(), search.getWhere());
    }

    /**
     * 商品推送到砍价列表
     */
    @PostMapping("/toBargain/list")
    @ApiOperation("商品推送到砍价列表")
    public ResponseData<Integer> productToBargain(@RequestBody MallProduct product) {
        ResponseData<Integer> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        // 参数错误
        if (Objects.isNull(product) || Objects.isNull(product.getId()))
            return responseData;
        Integer result = productService.updateProductToBargain(product);
        if (result > 0)
            return responseData
                    .setMessage(MessageEnum.SUCCESS.getMessage())
                    .setState(MessageEnum.SUCCESS.getState());
        return responseData;
    }

}

