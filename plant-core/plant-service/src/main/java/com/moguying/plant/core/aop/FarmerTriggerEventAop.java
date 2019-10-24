package com.moguying.plant.core.aop;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.FarmerTrigger;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.farmer.FarmerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FarmerTriggerEventAop {

    @Autowired
    private FarmerService farmerService;

    @AfterReturning(pointcut = "@annotation(trigger)",
            returning = "retVal"
    )
    private void addMessage(Object retVal, FarmerTrigger trigger){
        if(retVal instanceof ResultData){
            ResultData resultData = (ResultData) retVal;
            if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS)){
                if(resultData.getData() instanceof User){
                    farmerService.addEnergyToUserByTriggerEvent(trigger.action(),((User) resultData.getData()).getId());
                }
                if(resultData.getData() instanceof TriggerEventResult){
                    farmerService.addEnergyToUserByTriggerEvent(trigger.action(),((TriggerEventResult)resultData.getData()).getUserId());
                }
            }
        }
    }
}
