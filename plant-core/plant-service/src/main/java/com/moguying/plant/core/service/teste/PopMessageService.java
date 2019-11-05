package com.moguying.plant.core.service.teste;

import com.baomidou.mybatisplus.extension.api.R;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.taste.PopMessage;

public interface PopMessageService {

    /**
     * 保存弹幕信息
     * @param message
     * @return
     */
    ResultData<Boolean> savePopMessage(PopMessage message);

    /**
     * 删除弹幕信息
     * @param id
     * @return
     */
    ResultData<Boolean> deletePopMessage(String id);


    PageResult<PopMessage> popMessagePageResult(Integer page,Integer size,PopMessage message);

    PopMessage usedPopMessage();


    ResultData<Boolean> setUseState(String id);

}
