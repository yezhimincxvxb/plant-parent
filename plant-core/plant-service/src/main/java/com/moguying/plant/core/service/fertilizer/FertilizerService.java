package com.moguying.plant.core.service.fertilizer;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.fertilizer.FertilizerType;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;

import java.math.BigDecimal;
import java.util.List;

public interface FertilizerService {

    int addFertilizer(Fertilizer add);

    Integer updateFertilizer(Fertilizer update);

    ResultData<Integer> deleteFertilizer(int id);

    PageResult<Fertilizer> fertilizerList(int page, int size, Fertilizer where);

    List<FertilizerType> fertilizerType();

    ResultData<BigDecimal> useFertilizers(FertilizerUseCondition condition, List<Integer> fertilizers, String orderNumber);

    ResultData<Integer> distributeFertilizer(String triggerGetEvent, TriggerEventResult triggerEventResult);

    ResultData<Integer> distributeFertilizer(String triggerGetEvent, TriggerEventResult triggerEventResult, Integer fertilizerId);

    ResultData<Integer> distributeFertilizer(String triggerGetEvent, Integer userId);

    boolean useDoneFertilizers(String orderNumber);

    PageResult<ExchangeInfo> showFertilizer(Integer page, Integer size);

    Fertilizer findById(Integer fertilizerId);

    PageResult<ExchangeInfo> showFertilizerLog(Integer page, Integer size, Integer userId);

}
