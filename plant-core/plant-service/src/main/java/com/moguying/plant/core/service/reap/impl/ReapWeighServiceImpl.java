package com.moguying.plant.core.service.reap.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.reap.ReapWeighDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.ReapWeigh;
import com.moguying.plant.core.service.reap.ReapWeighService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReapWeighServiceImpl implements ReapWeighService {

    @Autowired
    private  ReapWeighDAO reapWeighDAO;

    @Autowired
    private ReapDAO reapDAO;


    @Override
    public ResultData<ReapWeigh> userReapWeighInfo(Integer userId) {
        ResultData<ReapWeigh> resultData = new ResultData<>(MessageEnum.ERROR,null);
        ReapWeigh reapWeighInfo = reapWeighDAO.selectById(userId);
        if(null == reapWeighInfo) return resultData.setMessageEnum(MessageEnum.USER_REAP_WEIGH_INFO_NOT_EXISTS);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(reapWeighInfo);
    }

}
