package com.moguying.plant.core.service.teste.impl;

import com.moguying.plant.constant.ApiEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.taste.PopMessage;
import com.moguying.plant.core.entity.taste.Taste;
import com.moguying.plant.core.entity.taste.TasteApply;
import com.moguying.plant.core.service.teste.PopMessageService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PopMessageServiceImpl implements PopMessageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResultData<Boolean> savePopMessage(PopMessage message) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        if (null == message.getIcon())
            return resultData.setMessageEnum(MessageEnum.POP_MESSAGE_ICON_EMPTY);
        mongoTemplate.save(message);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
    }

    @Override
    public ResultData<Boolean> deletePopMessage(String id) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("id").is(id)), PopMessage.class);
        if(result.getDeletedCount() > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        return resultData;
    }

    @Override
    public PageResult<PopMessage> popMessagePageResult(Integer page,Integer size,PopMessage where) {
        Optional<PopMessage> optional = Optional.ofNullable(where);
        Query query = new Query().with(PageRequest.of(page-1,size));
        if(optional.map(PopMessage::isUsed).isPresent())
            query.addCriteria(Criteria.where("isUsed").is(where.isUsed()));
        List<PopMessage> items = mongoTemplate.find(query,PopMessage.class);

        long count = mongoTemplate.count(query,PopMessage.class);
        return new PageResult<>(count,items);
    }

    @Override
    public PopMessage usedPopMessage() {
       return mongoTemplate.findOne(new Query(Criteria.where("isUsed").is(true)), PopMessage.class);
    }

    @Override
    public ResultData<Boolean> setUseState(String id) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        Query query = new Query(Criteria.where("id").is(id));
        PopMessage popMessage = mongoTemplate.findOne(query,PopMessage.class);
        Optional<PopMessage> optional = Optional.ofNullable(popMessage);
        if(optional.isPresent()) {
            boolean state = optional.map(PopMessage::isUsed).get();
            UpdateResult isUsed = mongoTemplate.updateFirst(query, Update.update("isUsed", !state),PopMessage.class);
            if (isUsed.getMatchedCount() > 0) {
                if(!state){
                    //只能存在一个使用状态的弹幕
                    mongoTemplate.updateMulti(new Query(Criteria.where("isUsed").is(true).and("id").ne(id)),Update.update("isUsed",false),PopMessage.class);
                }
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
            }
        }
        return resultData;
    }
}
