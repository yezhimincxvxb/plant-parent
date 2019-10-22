package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.MallProduct;
import com.moguying.plant.core.service.mall.MallProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/backEnd/mall")
public class BMallProductController {


    @Autowired
    MallProductService productService;


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseData<MallProduct> productDetail(@PathVariable Integer id){
        ResultData<MallProduct> resultData = productService.productDetail(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState()
                ,resultData.getData());
    }


    /**
     * 添加商品
     * @param product
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseData<Integer> addProduct(@RequestBody MallProduct product){
        ResultData<Integer> resultData = productService.saveProduct(product);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 更新商品
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseData<Integer> updateProduct(@PathVariable Integer id, @RequestBody MallProduct product){
        product.setId(id);
        ResultData<Integer> resultData = productService.saveProduct(product);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 对商品上下架
     * @param id
     * @return
     */
    @PutMapping("/show/{id}")
    @ResponseBody
    public ResponseData<Integer> showProduct(@PathVariable Integer id){
        if(productService.showProduct(id))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 商品列表
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public PageResult<MallProduct> productList(@RequestBody PageSearch<MallProduct> search){
        return productService.productList(search.getPage(),search.getSize(),search.getWhere());
    }








}

