package com.moguying.plant.core.service.feedback.feebback.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.feedback.FeedbackItem;
import com.moguying.plant.core.service.feedback.feebback.FeedbackService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PageResult<FeedbackItem> findFeedbackList(Integer page, Integer size, FeedbackItem where) {
        Optional<FeedbackItem> optional = Optional.ofNullable(where);
        Query query = new Query().with(PageRequest.of(page - 1, size));
        if (optional.map(FeedbackItem::getFeedbackType).isPresent())
            query.addCriteria(Criteria.where("feedbackType").is(where.getFeedbackType()));
        List<FeedbackItem> items = mongoTemplate.find(query, FeedbackItem.class);
        long count = mongoTemplate.count(query, FeedbackItem.class);
        return new PageResult<>(count, items);
    }

    @Override
    public FeedbackItem getFeedback(FeedbackItem where) {
        Query query = new Query(Criteria.where("_id").is(where.get_id()));
        FeedbackItem feedbackItem = mongoTemplate.findOne(query, FeedbackItem.class);
        if (null != feedbackItem)
            return feedbackItem;
        return new FeedbackItem();
    }

    @Override
    public ResultData<Boolean> saveFeedbackItem(FeedbackItem feedbackItem) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR, false);
        mongoTemplate.save(feedbackItem);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
    }

    @Override
    public ResultData<Boolean> updateFeedbackItem(FeedbackItem feedbackItem) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR, false);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(feedbackItem.get_id()));
        Update update = new Update();
        update.set("feedbackType", feedbackItem.getFeedbackType());
        update.set("banners", feedbackItem.getBanners());
        update.set("describeInfo", feedbackItem.getDescribeInfo());
        update.set("feedbackTypes", feedbackItem.getFeedbackTypes());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, FeedbackItem.class);
        if (updateResult.getModifiedCount() > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Override
    public ResultData<Boolean> removeFeedbackItem(FeedbackItem feedbackItem) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("_id").is(feedbackItem.get_id())), FeedbackItem.class);
        if (result.getDeletedCount() > 0)
            return new ResultData<>(MessageEnum.SUCCESS, true);
        return new ResultData<>(MessageEnum.ERROR, false);
    }


}
