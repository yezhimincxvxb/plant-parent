package com.moguying.plant.core.service.feedback.material;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.feedback.FeedbackMaterial;


public interface FeedbackMaterialService {

    PageResult<FeedbackMaterial> findMaterialList(Integer page, Integer size, FeedbackMaterial where);

    ResultData<Integer> addMaterial(FeedbackMaterial feedbackMaterial);

    ResultData<Boolean> deleteMaterial(FeedbackMaterial feedbackMaterial);

    ResultData<Boolean> checkMaterial(String fileName);

}
