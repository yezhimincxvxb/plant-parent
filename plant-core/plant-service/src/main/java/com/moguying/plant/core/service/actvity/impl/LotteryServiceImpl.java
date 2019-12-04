package com.moguying.plant.core.service.actvity.impl;

import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.LotteryRule;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;
import com.moguying.plant.core.service.actvity.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LotteryServiceImpl implements LotteryService {

    private final String quaPrefix = "lottery:qua";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReapDAO reapDAO;

    @Override
    public LotteryQua userLotteryQua(Integer userId) {
        String key = quaPrefix.concat(userId.toString());
        Long s = stringRedisTemplate.opsForList().size(key);
        String reapId = stringRedisTemplate.opsForList().leftPop(key);
        return null;
    }


    @Override
    public ResultData<Boolean> addLotteryRule(LotteryRule rule) {
        return null;
    }
}
