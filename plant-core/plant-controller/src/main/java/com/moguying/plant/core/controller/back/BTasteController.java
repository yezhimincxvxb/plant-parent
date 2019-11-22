package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.taste.PopMessage;
import com.moguying.plant.core.entity.taste.Taste;
import com.moguying.plant.core.entity.taste.TasteApply;
import com.moguying.plant.core.service.teste.PopMessageService;
import com.moguying.plant.core.service.teste.TasteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/taste")
@Api(tags = "活动管理")
public class BTasteController {


    @Autowired
    private TasteService tasteService;

    @Autowired
    private PopMessageService popMessageService;


    /**
     * 添加弹幕信息
     * @param message
     * @return
     */
    @PostMapping("/pop")
    @ApiOperation("添加弹幕信息")
    public ResponseData<PopMessage> savePopMessage(@RequestBody PopMessage message) {
        ResultData<Boolean> resultData = popMessageService.savePopMessage(message);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }

    /**
     * 删除弹幕信息
     * @param id
     * @return
     */
    @DeleteMapping("/pop/{id}")
    @ApiOperation("删除弹幕信息")
    public ResponseData<Boolean> deletePopMessage(@PathVariable String id){
        ResultData<Boolean> b = popMessageService.deletePopMessage(id);
        return new ResponseData<>(b.getMessageEnum().getMessage(),b.getMessageEnum().getState());
    }

    /**
     * 设置弹幕使用状态
     * @param id
     * @return
     */
    @PostMapping("/pop/{id}")
    @ApiOperation("设置弹幕使用状态")
    public ResponseData<Boolean> setUseState(@PathVariable String id){
        ResultData<Boolean> resultData = popMessageService.setUseState(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 查询弹幕列表
     * @param search
     * @return
     */
    @PostMapping("/pop/list")
    @ApiOperation("查询弹幕列表")
    public PageResult<PopMessage> popMessagePageResult(@RequestBody PageSearch<PopMessage> search){
        return popMessageService.popMessagePageResult(search.getPage(),search.getSize(),search.getWhere());
    }





    /**
     * 试吃列表
     * @param search
     * @return
     */
    @PostMapping("/free/list")
    @ApiOperation("试吃列表")
    public PageResult<Taste> tasteList(@RequestBody PageSearch<Taste> search){
        return tasteService.tastePageResult(search.getPage(),search.getSize(),search.getWhere());
    }



    /**
     * 添加试吃内容
     * @return
     */
    @PostMapping("/free")
    @ApiOperation("添加试吃内容")
    public ResponseData<Taste> saveTasteItem(@RequestBody Taste taste) {
        ResultData<Boolean> resultData = tasteService.saveTaste(taste);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 删除试吃项
     * @param id
     * @return
     */
    @DeleteMapping("/free/{id}")
    @ApiOperation("删除试吃项")
    public ResponseData<Boolean> deleteTasteItem(@PathVariable String id){
        ResultData<Boolean> resultData = tasteService.deleteTaste(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 设置是否上下架
     * @param id
     * @return
     */
    @PutMapping("/free/{id}")
    @ApiOperation("设置是否上下架")
    public ResponseData<Boolean> setItemShowState(@PathVariable String id){
        if(tasteService.setShowState(id))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }

    /**
     * 设置是否申请通过
     */
    @PostMapping("/free/state")
    @ApiOperation("设置是否申请通过")
    public ResponseData<Boolean> setTaste(@RequestBody TasteApply taste){
        if (Objects.isNull(taste) || Objects.isNull(taste.getId()) || Objects.isNull(taste.getState()))
            new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(),MessageEnum.PARAMETER_ERROR.getState());

        if(tasteService.setState(taste))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());

        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }

    /**
     * 试吃申请记录
     * @param search
     * @return
     */
    @PostMapping("/free/apply/list")
    @ApiOperation("试吃申请记录")
    public PageResult<TasteApply> applyPageResult(@RequestBody PageSearch<TasteApply> search) {
        if(null == search.getWhere()){
            search.setWhere(new TasteApply());
        }
        return tasteService.bTasteApplyPageResult(search.getPage(),search.getSize(),search.getWhere());
    }

}
