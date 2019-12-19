package com.moguying.plant.core.service.feedback.feebback;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.feedback.FeedbackItem;

public interface FeedbackService {

    PageResult<FeedbackItem> findFeedbackList(Integer page, Integer size, FeedbackItem where);

    FeedbackItem getFeedback(FeedbackItem feedbackItem);

    Boolean existFeedback(FeedbackItem feedbackItem);

    ResultData<Boolean> saveFeedbackItem(FeedbackItem feedbackItem);

    ResultData<Boolean> updateFeedbackItem(FeedbackItem feedbackItem);

    ResultData<Boolean> removeFeedbackItem(FeedbackItem feedbackItem);

}
