package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.SeedGroup;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.service.seed.SeedTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seedType")
@Slf4j
@Api(tags = "菌包分类管理")
public class BSeedTypeController {


    @Autowired
    private SeedTypeService seedTypeService;


    /**
     * 种子分类列表
     *
     * @return
     */
    @PostMapping(value = "/tree")
    @ApiOperation("种子分类列表")
    public PageResult<SeedType> seedTypeList(@RequestBody PageSearch<SeedType> search) {
        SeedType where = new SeedType();
        //未删除的
        where.setIsDelete(false);
        return seedTypeService.seedTypeList(search.getPage(), search.getSize(), where);
    }


    /**
     * 添加菌包分类
     *
     * @param seedTypeAdd
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("添加菌包分类")
    public ResponseData<Integer> seedTypeAdd(@RequestBody SeedType seedTypeAdd) {

        if (StringUtils.isEmpty(seedTypeAdd.getClassName()) || seedTypeAdd.getOrderNumber() < 0)
            return new ResponseData<>(MessageEnum.SEED_CLASS_NAME_EMPTY.getMessage(), MessageEnum.SEED_CLASS_NAME_EMPTY.getState());
        ResultData<Integer> resultData = seedTypeService.seedTypeSave(seedTypeAdd);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());

    }

    /**
     * 删除菌包分类
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    @ApiOperation("删除菌包分类")
    public ResponseData<Integer> seedTypeDelete(@PathVariable Integer id) {
        if (id == 0 || id < 0)
            return new ResponseData<>(MessageEnum.ID_ERROR.getMessage(), MessageEnum.ID_ERROR.getState(), id);

        id = seedTypeService.seedTypeDelete(id);

        if (id < 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), id);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), id);
    }


    /**
     * 更新指定id的菌类信息
     *
     * @param seedClass
     * @return
     */
    @PutMapping(value = "/edit")
    @ApiOperation("更新指定id的菌类信息")
    public ResponseData<Integer> seedTypeEdit(@RequestBody SeedType seedClass) {
        if (seedClass == null)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), null);
        ResultData<Integer> resultData = seedTypeService.seedTypeSave(seedClass);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 获取指定id的分类信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指定id的分类信息")
    public ResponseData<SeedType> seedType(@PathVariable Integer id) {
        if (id == 0 || id < 0)
            return new ResponseData<>(MessageEnum.ID_ERROR.getMessage(), MessageEnum.ID_ERROR.getState(), null);
        SeedType seedClass = seedTypeService.seedType(id);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), seedClass);
    }


    /**
     * 类型分组列表
     *
     * @return
     */
    @GetMapping("/group/list")
    @ApiOperation("类型分组列表")
    public ResponseData<List<SeedGroup>> seedGroupPageResult() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), seedTypeService.seedGroupList());
    }


    /**
     * 添加分组
     *
     * @param seedGroup
     * @return
     */
    @PostMapping("/group")
    @ApiOperation("添加分组")
    public ResponseData<Boolean> saveSeedGroup(@RequestBody SeedGroup seedGroup) {
        ResultData<Boolean> resultData = seedTypeService.saveSeedGroup(seedGroup);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


}
