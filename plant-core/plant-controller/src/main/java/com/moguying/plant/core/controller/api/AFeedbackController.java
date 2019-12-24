package com.moguying.plant.core.controller.api;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.feedback.FeedbackItem;
import com.moguying.plant.core.entity.feedback.Result;
import com.moguying.plant.core.service.feedback.feebback.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/feedback")
public class AFeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/item")
    @NoLogin
    public ResponseData<FeedbackItem> feedbackItem(@RequestBody FeedbackItem queryFeedback) {
        FeedbackItem feedback = feedbackService.getFeedback(queryFeedback);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), feedback);
    }

    @PostMapping("/exist")
    @NoLogin
    public ResponseData<Result> exist(@RequestBody FeedbackItem queryFeedback) {
        Result result = new Result();
        result.setIsExist(feedbackService.existFeedback(queryFeedback));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), result);
    }


}
