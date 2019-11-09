package com.moguying.plant.core.service.teste.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.ApiEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.constant.ReapEnum;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.dao.fertilizer.FertilizerDAO;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.SeedOrder;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.taste.Taste;
import com.moguying.plant.core.entity.taste.TasteApply;
import com.moguying.plant.core.entity.taste.vo.TasteReap;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.teste.TasteService;
import com.moguying.plant.utils.DateUtil;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TasteServiceImpl implements TasteService {


    @Autowired
    private SeedDAO seedDAO;

    @Autowired
    private PlantOrderService plantOrderService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private ReapDAO reapDAO;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MallProductDAO mallProductDAO;

    @Autowired
    private FertilizerDAO fertilizerDAO;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private SeedOrderDAO seedOrderDAO;

    @Autowired
    private BlockDAO blockDAO;

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;


    @Override
    @DS("read")
    public Boolean isNew(Integer userId) {
        Integer count = seedOrderDAO.selectCount(new QueryWrapper<>(new SeedOrder().setUserId(userId)));
        return count <= 0;
    }

    @DS("write")
    @Override
    @Transactional
    public ResultData<BuyOrderResponse> buy(BuyOrder buyOrder,Integer userId) {
        ResultData<BuyOrderResponse> resultData = new ResultData<>(MessageEnum.ERROR,null);

        // 体验购买只有一次机会
        QueryWrapper<SeedOrderDetail> wrapper = new QueryWrapper<SeedOrderDetail>()
                .and(i -> i.eq("user_id", userId).likeRight("order_number", OrderPrefixEnum.TI_YAN_BUY.getPreFix()));
        List<SeedOrderDetail> details = seedOrderDetailDAO.selectList(wrapper);
        if (Objects.nonNull(details) && details.size() > 0)
            return resultData.setMessageEnum(MessageEnum.YI_TI_YAN);

        // 菌包不存在
        Seed seed = seedDAO.seedInfoWithTypeById(buyOrder.getSeedId());
        if(null == seed)
            return resultData.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);

        // 菌包类型
        if(null == seed.getTypeInfo())
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_EXIST);

        // 非新手类型
        if(!seed.getTypeInfo().getIsForNew())
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_FOR_TASTE);

        // 固定一份
        if(1 != buyOrder.getCount())
            return resultData.setMessageEnum(MessageEnum.TASTE_BUY_SEED_COUNT_ERROR);

        //TODO 是否已参于过
        ResultData<BuyOrderResponse> buyResult = plantOrderService.plantOrder(buyOrder, userId, true);
        if(!buyResult.getMessageEnum().equals(MessageEnum.SUCCESS))
            return resultData.setMessageEnum(buyResult.getMessageEnum());

        ResultData<Integer> payResult =
                plantOrderService.payOrderSuccess(seedOrderDetailDAO.selectById(buyResult.getData().getOrderId()),
                        userDAO.selectById(userId));
        if(payResult.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            // 返回订单id，而不是订单详情id
            buyResult.getData().setOrderId(payResult.getData());
            return buyResult;
        }
        return resultData;
    }

    @Override
    @DS("write")
    public ResultData<TasteReap> reap(Integer userId, Integer reapId) {
        ResultData<TasteReap> resultData = new ResultData<>(MessageEnum.ERROR, null);
        Reap where = new Reap();
        where.setUserId(userId);
        where.setId(reapId);

        Reap reap = reapDAO.selectOne(new QueryWrapper<>(where));
        if (Objects.isNull(reap))
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_EXISTS);

        if (reap.getPreReapTime().getTime() != DateUtil.INSTANCE.todayEnd().getTime())
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_IN_TIME);

        Block block = blockDAO.selectById(reap.getBlockId());
        if (Objects.isNull(block))
            return  resultData.setMessageEnum(MessageEnum.BLOCK_NOT_EXISTS);

        SeedType seedType = seedTypeDAO.selectById(reap.getSeedType());
        if (Objects.isNull(seedType))
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_EXIST);

        Reap update = new Reap();
        update.setId(reap.getId());
        update.setState(ReapEnum.REAP_DONE.getState());
        if(reapDAO.updateById(update) > 0) {
            TasteReap tasteReap = new TasteReap();
            tasteReap.setSeedTypeName(seedType.getClassName());
            tasteReap.setBlockNumber(block.getNumber());
            tasteReap.setGrowDays(seedType.getGrowDays());
            tasteReap.setPlantCount(reap.getPlantCount());
            tasteReap.setPlantWeigh(reap.getPlantWeigh());
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(tasteReap);
        }
        return resultData;
    }


    @Override
    public ResultData<Boolean> saveTaste(Taste taste) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        Optional<Taste> optional = Optional.ofNullable(taste);
        if(!optional.isPresent())
            return resultData;
        MallProduct product = mallProductDAO.selectById(taste.getProductId());
        if(null == product)
            return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);
        taste.setThumbPic(product.getThumbPicUrl());
        taste.setPic(product.getPicUrl());
        taste.setState(ApiEnum.TASTE_OPEN.getType());
        taste.setIsShow(false);
        taste.setProductName(product.getName());
        taste.setProductPrice(product.getPrice());
        mongoTemplate.save(taste);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
    }

    @Override
    public PageResult<Taste> tastePageResult(Integer page, Integer size,Taste where,Integer userId) {
        Optional<Taste> optional = Optional.ofNullable(where);
        Query query = new Query().with(PageRequest.of(page-1,size, Sort.Direction.DESC,"tasteCount"));
        if(optional.map(Taste::getState).isPresent())
            query.addCriteria(Criteria.where("state").is(where.getState()));
        if(optional.map(Taste::getIsShow).isPresent())
            query.addCriteria(Criteria.where("isShow").is(where.getIsShow()));
        List<Taste> items = mongoTemplate.find(query,Taste.class);
        if(null != userId){
            items.forEach((taste)->{
                boolean exists = mongoTemplate.exists(new Query(Criteria.where("userId").is(userId).and("tasteId").is(taste.getId())), TasteApply.class);
                taste.setHasApply(exists);
                if(taste.getEndTime().compareTo(new Date()) <= 0){
                    mongoTemplate.updateFirst(new Query(Criteria.where("id").is(taste.getId())),Update.update("state",ApiEnum.TASTE_CLOSE.getType()),Taste.class);
                }
                taste.setApplyCount(mongoTemplate.count(new Query(Criteria.where("tasteId").is(taste.getId())),TasteApply.class));
            });
        }
        long count = mongoTemplate.count(query,Taste.class);
        return new PageResult<>(count,items);
    }

    @Override
    public PageResult<Taste> tastePageResult(Integer page, Integer size, Taste where) {
        return tastePageResult(page,size,where,null);
    }

    @Override
    public ResultData<Boolean> deleteTaste(String id) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Taste.class);
        if(result.getDeletedCount() > 0)
            return new ResultData<>(MessageEnum.SUCCESS,true);
        return new ResultData<>(MessageEnum.ERROR,false);
    }

    @Override
    public Boolean setShowState(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Taste taste = mongoTemplate.findOne(query,Taste.class);
        if(null != taste) {
            UpdateResult updateResult = mongoTemplate.updateFirst(query, Update.update("isShow", !taste.getIsShow()), Taste.class);
            return updateResult.getModifiedCount() > 0;
        }
        return false;
    }

    @Override
    public Boolean setState(Taste taste) {
        Query query = new Query(Criteria.where("id").is(taste.getId()));
        TasteApply one = mongoTemplate.findOne(query,TasteApply.class);
        if(null != one) {
            UpdateResult updateResult = mongoTemplate.updateFirst(query, Update.update("state", taste.getState()), TasteApply.class);
            return updateResult.getModifiedCount() > 0;
        }
        return false;
    }

    @Override
    public ResultData<Boolean> addTasteApply(Integer userId,Taste taste) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        User user = userDAO.selectById(userId);
        Optional<User> userOptional = Optional.ofNullable(user);
        if(!userOptional.isPresent())
            return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);
        taste =  Optional.ofNullable(taste).orElseGet(() -> mongoTemplate.findOne(new Query(Criteria.where("isShow")
                .is(true).and("endTime").gt(new Date()).and("tasteCount").gt(0)),Taste.class));
        //没有进行中免费申请活动
        if(null == taste)
            return resultData;

        TasteApply apply = new TasteApply(userId,taste.getId(), ApiEnum.TASTE_APPLY.getType());
        Taste tasteInfo = mongoTemplate.findOne(new Query(Criteria.where("id").is(taste.getId())), Taste.class);
        Long count = Optional.ofNullable(tasteInfo).map(Taste::getTasteCount).orElse(0L);
        AtomicLong atomicLong = new AtomicLong(count);
        if(count <= 0)
            return resultData.setMessageEnum(MessageEnum.TASTE_COUNT_NOT_ENOUGH);

        boolean exist = mongoTemplate.exists(new Query(Criteria.where("userId").is(userId).and("tasteId").is(taste.getId())),TasteApply.class);
        if(exist)
            return resultData.setMessageEnum(MessageEnum.TASTE_HAS_APPLY);

        MallProduct product = mallProductDAO.selectById(tasteInfo.getProductId());
        if (Objects.isNull(product)) return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);

        apply.setPhone(userOptional.map(User::getPhone).orElse(""));
        apply.setProductName(product.getName());
        apply.setApplyTime(new Date());
        mongoTemplate.save(apply);
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(taste.getId())),Update.update("tasteCount",atomicLong.decrementAndGet()),Taste.class);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
    }

    @Override
    public ResultData<TasteApply> checkApply(Integer userId, Taste taste) {
        ResultData<TasteApply> resultData = new ResultData<>(MessageEnum.ERROR,null);
        TasteApply tasteApply = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userId).and("tasteId").is(taste.getId())), TasteApply.class);
        Optional<TasteApply> optionalTasteApply = Optional.ofNullable(tasteApply);
        if(optionalTasteApply.isPresent()) {
            if(tasteApply.getState().equals(ApiEnum.TASTE_APPLY_SUCCESS.getType())){
                tasteApply.setUserAddress(userAddressDAO.userDefaultAddress(userId));
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(tasteApply);
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(tasteApply);
        }
        return resultData.setMessageEnum(MessageEnum.TASTE_APPLY_NOT_EXIST);
    }

    @Override
    public PageResult<TasteApply> tasteApplyPageResult(Integer page, Integer size, TasteApply where) {
        Query query = new Query().with(PageRequest.of(page-1,size, Sort.Direction.DESC,"applyTime"));
        Optional<TasteApply> optional = Optional.ofNullable(where);
        if(optional.map(TasteApply::getTasteId).isPresent())
            query.addCriteria(Criteria.where("tasteId").is(where.getTasteId()));
        List<TasteApply> tasteApplies = mongoTemplate.find(query, TasteApply.class);
        long count = mongoTemplate.count(query, TasteApply.class);
        return new PageResult<>(count,tasteApplies);
    }


    @Override
    public List<Fertilizer> tasteGiftList() {
        Fertilizer where = new Fertilizer();
        where.setTriggerGetEvent("taste");
        return fertilizerDAO.selectList(new QueryWrapper<>(where));
    }

    @Override
    public ResultData<Integer> pickUpGift(Integer userId) {
        // 新手礼包只能领取一次
        Integer count = userFertilizerDAO.countByTriggerEvent(userId, "taste");
        if (count > 0)
            return new ResultData<>(MessageEnum.ERROR, 1);

        return  fertilizerService.distributeFertilizer("taste",userId);
    }
}
