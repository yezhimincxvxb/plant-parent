package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.feedback.FeedbackItem;
import com.moguying.plant.core.service.feedback.feebback.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/feedback")
@Api(tags = "溯源管理")
public class BFeedbackController {


    @Autowired
    private FeedbackService feedbackService;

    /**
     * 溯源列表
     *
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("溯源列表")
    @NoLogin
    public PageResult<FeedbackItem> findFeedbackList(@RequestBody PageSearch<FeedbackItem> search) {
        return feedbackService.findFeedbackList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 获取溯源
     *
     * @return
     */
    @PostMapping("/getFeedback")
    @ApiOperation("获取溯源")
    @NoLogin
    public ResponseData<FeedbackItem> getFeedback(@RequestBody FeedbackItem feedbackItem) {
        FeedbackItem feedback = feedbackService.getFeedback(feedbackItem);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), feedback);
    }



    /**
     * 添加溯源
     *
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加溯源")
    @NoLogin
    public ResponseData<Boolean> saveFeedbackItem(@RequestBody FeedbackItem feedbackItem) {
        ResultData<Boolean> resultData = feedbackService.saveFeedbackItem(feedbackItem);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 更新溯源
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新溯源")
    @NoLogin
    public ResponseData<Boolean> updateFeedbackItem(@RequestBody FeedbackItem feedbackItem) {
        ResultData<Boolean> resultData = feedbackService.updateFeedbackItem(feedbackItem);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 删除溯源
     *
     * @return
     */
    @DeleteMapping("/remove")
    @ApiOperation("删除溯源")
    @NoLogin
    public ResponseData<Boolean> removeFeedbackItem(@RequestBody FeedbackItem feedbackItem) {
        ResultData<Boolean> resultData = feedbackService.removeFeedbackItem(feedbackItem);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


}
