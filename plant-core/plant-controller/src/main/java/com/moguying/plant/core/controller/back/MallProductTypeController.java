package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.mall.MallProductType;
import com.moguying.plant.core.service.mall.MallProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mall/type")
@Api(tags = "商城商品分类管理")
public class MallProductTypeController {

    @Autowired
    private MallProductTypeService typeService;


    /**
     * 商品类型列表
     *
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("商品类型列表")
    public ResponseData<List<MallProductType>> typePageResult(@RequestBody PageSearch<MallProductType> search) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), typeService.typeList(search.getWhere()));
    }


    /**
     * 添加/修改商品类型
     *
     * @param type
     * @return
     */
    @ApiOperation("添加/修改商品类型")
    @PostMapping
    public ResponseData<Integer> saveType(@RequestBody MallProductType type) {
        if (null == type || null == type.getTypeName())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        ResultData<Integer> resultData = typeService.saveType(type);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 删除对应id的商品类型
     *
     * @param id
     * @return
     */
    @ApiOperation("删除对应id的商品类型")
    @DeleteMapping("/{id}")
    public ResponseData<Integer> deleteType(@PathVariable Integer id) {
        ResultData<Integer> resultData = typeService.deleteType(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


}
