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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/taste")
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
    public PageResult<PopMessage> popMessagePageResult(@RequestBody PageSearch<PopMessage> search){
        return popMessageService.popMessagePageResult(search.getPage(),search.getSize(),search.getWhere());
    }











    /**
     * 试吃列表
     * @param search
     * @return
     */
    @PostMapping("/free/list")
    public PageResult<Taste> tasteList(@RequestBody PageSearch<Taste> search){
        return tasteService.tastePageResult(search.getPage(),search.getSize(),search.getWhere());
    }



    /**
     * 添加试吃内容
     * @return
     */
    @PostMapping("/free")
    public ResponseData<Taste> saveTasteItem(@RequestBody Taste taste) {
        ResultData<Boolean> resultData = tasteService.saveTaste(taste);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 删除项
     * @param id
     * @return
     */
    @DeleteMapping("/free/{id}")
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
    public ResponseData<Boolean> setItemShowState(@PathVariable String id){
        if(tasteService.setShowState(id))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }

    /**
     * 设置是否申请通过
     */
    @PostMapping("/free/state")
    public ResponseData<Boolean> setTaste(@RequestBody Taste taste){
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
    public PageResult<TasteApply> applyPageResult(@RequestBody PageSearch<TasteApply> search) {
        if(null == search.getWhere()){
            search.setWhere(new TasteApply());
        }
        return tasteService.tasteApplyPageResult(search.getPage(),search.getSize(),search.getWhere());
    }

}
