package com.moguying.plant.core.service.fertilizer;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.Fertilizer;
import com.moguying.plant.core.entity.dto.FertilizerType;
import com.moguying.plant.core.entity.vo.ExchangeInfo;
import com.moguying.plant.core.entity.vo.FertilizerUseCondition;

import java.math.BigDecimal;
import java.util.List;

public interface FertilizerService {

    @DataSource("write")
    int addFertilizer(Fertilizer add);

    @DataSource("write")
    Integer updateFertilizer(Fertilizer update);

    @DataSource("write")
    ResultData<Integer> deleteFertilizer(int id);

    @DataSource("read")
    PageResult<Fertilizer> fertilizerList(int page, int size, Fertilizer where);

    @DataSource("read")
    List<FertilizerType> fertilizerType();

    @DataSource("write")
    ResultData<BigDecimal> useFertilizers(FertilizerUseCondition condition, List<Integer> fertilizers, String orderNumber);

    @DataSource("write")
    ResultData<Integer> distributeFertilizer(String triggerGetEvent, TriggerEventResult triggerEventResult);

    @DataSource
    ResultData<Integer> distributeFertilizer(String triggerGetEvent, TriggerEventResult triggerEventResult, Integer fertilizerId);

    @DataSource("write")
    ResultData<Integer> distributeFertilizer(String triggerGetEvent, Integer userId);

    @DataSource("write")
    boolean useDoneFertilizers(String orderNumber);

    @DataSource("read")
    PageResult<ExchangeInfo> showFertilizer(Integer page, Integer size);

    @DataSource("read")
    Fertilizer findById(Integer fertilizerId);

    @DataSource("read")
    PageResult<ExchangeInfo> showFertilizerLog(Integer page, Integer size, Integer userId);

}
