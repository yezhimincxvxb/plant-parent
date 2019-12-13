package com.moguying.plant.core.service.content.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moguying.plant.constant.ActivityEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.activity.LotteryLogDAO;
import com.moguying.plant.core.dao.content.ActivityDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.user.UserActivityLogDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.LotteryLog;
import com.moguying.plant.core.entity.activity.LotteryRule;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;
import com.moguying.plant.core.entity.activity.vo.LotteryResult;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.entity.mq.FertilizerSender;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;
import com.moguying.plant.core.service.content.ActivityService;
import com.moguying.plant.utils.DateUtil;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityDAO, Activity> implements ActivityService {

    /**
     * 概率项填充列表
     */
    private final List<String> probabilityList = Collections.synchronizedList(new LinkedList<>());

    private final String dailyLotteryCount = "3";
    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private UserActivityLogDAO userActivityLogDAO;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReapDAO reapDAO;

    @Value("${meta.content.img}")
    private String appendStr;

    @Autowired
    private LotteryLogDAO lotteryLogDAO;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @DS("read")
    public PageResult<Activity> activityList(Integer page, Integer size, Activity where) {
        IPage<Activity> activityIPage = activityDAO.selectPage(new Page<>(page, size), new QueryWrapper<>(where));
        return new PageResult<>(activityIPage.getTotal(), activityIPage.getRecords());
    }

    @Override
    @DS("read")
    public Activity activityDetail(Integer id) {
        return activityDAO.selectById(id);
    }

    @Override
    @DS("write")
    public Integer deleteActivityById(Integer id) {
        return activityDAO.deleteById(id);
    }

    @Override
    @DS("write")
    public Integer addActivity(Activity activity) {
        if (null != activity.getContent())
            activity.setContent(appendStr.concat(activity.getContent()));
        if (activityDAO.insert(activity) > 0)
            return activity.getId();
        return 0;
    }

    @Override
    @DS("write")
    public Integer updateActivity(Activity update) {
        Activity activity = activityDAO.selectById(update.getId());
        if (null == activity)
            return -1;
        if (null != update.getContent())
            update.setContent(appendStr.concat(update.getContent()));
        return activityDAO.updateById(update);
    }

    @Override
    @DS("read")
    public PageResult<Activity> activityListForHome(Integer page, Integer size, Date startTime, Date endTime) {
        IPage<Activity> pageResult = activityDAO.activityListForHome(new Page<>(page, size), startTime, endTime);
        List<Activity> list = pageResult.getRecords();
        list.forEach((x) -> {
            int compare = DateUtils.truncatedCompareTo(x.getOpenTime(), new Date(), Calendar.DATE);
            if (compare > 0)
                x.setState(0);
            else if (compare == 0)
                x.setState(1);
            else {
                int endTimeCompare = DateUtils.truncatedCompareTo(x.getCloseTime(), new Date(), Calendar.DATE);
                if (endTimeCompare > 0)
                    x.setState(1);
                else
                    x.setState(2);
            }
        });
        return new PageResult<>(pageResult.getTotal(), list);
    }

    @Override
    @DS("read")
    public List<Activity> newestActivity() {
        return activityDAO.newestActivity();
    }

    @Override
    @DS("read")
    public PageResult<UserActivityLogVo> activityLog(Integer page, Integer size, UserActivityLogVo userActivityLogVo) {
        IPage<UserActivityLogVo> iPage = userActivityLogDAO.activityLog(new Page<>(page, size), userActivityLogVo);
        return new PageResult<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    @DS("read")
    public LotteryQua lotteryQua(Integer userId) {
        Long size = redisTemplate.opsForList().size(ActivityEnum.LOTTERY_KEY_PRE.getMessage().concat(userId.toString()));
        String countKey = ActivityEnum.LOTTERY_COUNT_KEY_PRE.getMessage().concat(userId.toString());
        String dailyCount = redisTemplate.opsForValue().get(countKey);
        Optional<String> optional = Optional.ofNullable(dailyCount);
        if(!optional.isPresent()) {
            redisTemplate.opsForValue().setIfAbsent(countKey,
                    dailyLotteryCount, Duration.between(Instant.now(), DateUtil.INSTANCE.todayEnd().toInstant()));
            dailyCount = dailyLotteryCount;
        }

        LotteryQua lotteryQua = new LotteryQua();
        lotteryQua.setUserId(userId);
        lotteryQua.setLotteryCount(size);
        lotteryQua.setDailyLotteryCount(new Long(dailyCount));
        return lotteryQua;
    }

    @Override
    @DS("read")
    public ResultData<LotteryResult> lotteryDo(Integer userId,boolean isCheck) {
        ResultData<LotteryResult> resultResultData = new ResultData<>(MessageEnum.ERROR,new LotteryResult());
        String key = ActivityEnum.LOTTERY_KEY_PRE.getMessage().concat(userId.toString());

        Long dayLotteryCount = redisTemplate.opsForList().size(key);
        if(null == dayLotteryCount || dayLotteryCount <= 0)
            return resultResultData.setMessageEnum(MessageEnum.LOTTERY_DAILY_COUNT_USED);

        String reapId = redisTemplate.opsForList().rightPop(key);
        Optional<String> optional = Optional.ofNullable(reapId);

        if (!optional.isPresent()) return resultResultData.setMessageEnum(MessageEnum.LOTTERY_INFO_EMPTY);
        Reap reap = reapDAO.selectById(Integer.parseInt(reapId));
        if (null == reap)
            return resultResultData.setMessageEnum(MessageEnum.LOTTERY_INFO_EMPTY);

        //获取抽奖规则
        List<LotteryRule> all = mongoTemplate.findAll(LotteryRule.class);
        Stream<LotteryRule> lotteryRuleStream = all.stream().filter(x ->
                x.getMinPlantCount().compareTo(reap.getPlantCount()) <= 0 && x.getMaxPlantCount().compareTo(reap.getPlantCount()) >= 0);
        Optional<LotteryRule> firstRule = lotteryRuleStream.findFirst();
        if(!firstRule.isPresent()) {
            //因抽奖条件不符合的不应取消他的抽奖资格
            redisTemplate.opsForList().rightPush(ActivityEnum.LOTTERY_KEY_PRE.getMessage().concat(userId.toString()),reapId);
            return resultResultData.setMessageEnum(MessageEnum.LOTTERY_RULE_OUT_OF_RANGE);
        }

        if(isCheck) {
            LotteryResult lotteryResult = new LotteryResult();
            lotteryResult.setType(firstRule.get().getType());
            //仅是检查不取消资格
            redisTemplate.opsForList().rightPush(ActivityEnum.LOTTERY_KEY_PRE.getMessage().concat(userId.toString()),reapId);
            return resultResultData.setMessageEnum(MessageEnum.SUCCESS).setData(lotteryResult);
        }

        //保存抽奖信息
        LotteryResult lotteryResult = fillList(firstRule.get().getRule());
        lotteryResult.setType(firstRule.get().getType());
        LotteryLog log = new LotteryLog();
        log.setLotteryAmount(new BigDecimal(lotteryResult.getAmount()));
        log.setReapId(Integer.parseInt(reapId));
        log.setLotteryType(firstRule.get().getType());
        log.setUserId(userId);
        log.setAddTime(new Date());
        if(lotteryLogDAO.insert(log) > 0) {
            rabbitTemplate.convertAndSend("plant.topic","lottery.fertilizer",new FertilizerSender(userId,"lottery:".concat(lotteryResult.getAmount())));
            return resultResultData.setMessageEnum(MessageEnum.SUCCESS).setData(lotteryResult);
        }
        return resultResultData;
    }



    /**
     * 填充概率
     *
     * @param probability 概率规则
     * @return
     */
    private LotteryResult fillList(String probability) {
        probabilityList.clear();
        Set<String> result = new HashSet<>();
        Set<String> totalSet = new HashSet<>();
        String[] split = probability.split("\\|");
        for (String rate : split) {
            if(rate.isEmpty()) continue;
            String[] rateDes = rate.split("-");
            for (int i = 0; i < Integer.parseInt(rateDes[1]); i++) {
                probabilityList.add(rateDes[0]);
                totalSet.add(rateDes[0]);
            }
        }
        Collections.shuffle(probabilityList);
        long l = System.currentTimeMillis() % probabilityList.size();
        String s = probabilityList.get((int) l);
        result.add(s);
        totalSet.removeAll(result);
        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setAmount(s);
        lotteryResult.setLeftAmount(totalSet.toArray());
        return lotteryResult;
    }


    @Override
    @DS("read")
    public ResultData<Boolean> addLotteryRule(LotteryRule rule) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR, false);
        boolean matches = rule.getRule().matches("(\\d{1,4}-\\d{1,4}\\|?){3}");
        if (!matches) return resultData.setMessageEnum(MessageEnum.LOTTERY_RULE_ERROR);
        mongoTemplate.save(rule);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
    }

    @Override
    @DS("read")
    public List<LotteryRule> lotteryRuleList() {
        return mongoTemplate.findAll(LotteryRule.class);
    }

    @Override
    @DS("read")
    public Boolean deleteLotteryRule(String id) {
        DeleteResult deleteResult = mongoTemplate.remove(new Query().addCriteria(Criteria.where("id").is(id)), LotteryRule.class);
        return deleteResult.getDeletedCount() > 0L;
    }

    @Override
    @DS("read")
    public PageResult<LotteryLog> lotteryLog(Integer page, Integer size, LotteryLog search) {
        IPage<LotteryLog> lotteryLogIPage = lotteryLogDAO.selectSelective(new Page<>(page, size), search);
        return new PageResult<>(lotteryLogIPage.getTotal(),lotteryLogIPage.getRecords());
    }
}
