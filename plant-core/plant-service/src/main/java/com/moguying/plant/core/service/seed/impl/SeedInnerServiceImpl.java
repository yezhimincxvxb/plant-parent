package com.moguying.plant.core.service.seed.impl;

import com.moguying.plant.constant.SeedEnum;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedInnerOrderDAO;
import com.moguying.plant.core.dao.user.UserInnerDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.SeedInnerBuy;
import com.moguying.plant.core.entity.seed.SeedInnerOrder;
import com.moguying.plant.core.entity.seed.SeedInnerOrderCount;
import com.moguying.plant.core.entity.user.UserInner;
import com.moguying.plant.core.service.seed.SeedInnerService;
import com.moguying.plant.core.service.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SeedInnerServiceImpl implements SeedInnerService {

    @Autowired
    SeedInnerOrderDAO innerOrderDAO;

    @Autowired
    SeedDAO seedDAO;

    @Autowired
    UserInnerDAO userInnerDAO;

    @Autowired
    SeedService seedService;

    @Pagination
    @Override
    public PageResult<SeedInnerOrderCount> seedInnerList(Integer page , Integer size) {
        innerOrderDAO.innerOrderCountList();
        return null;
    }


    @Override
    public List<UserInner> seedInnerUserList(Integer seedId, Integer innerCount, Integer userCount)  {
        Seed seed = seedDAO.selectById(seedId);
        if(seed == null || !seed.getState().equals(SeedEnum.REVIEWED.getState()))
            return null;
        if(userCount > innerCount)
            throw new IndexOutOfBoundsException();
        if(innerCount > seed.getLeftCount())
            throw new IndexOutOfBoundsException();
        Random random = new Random();
        Set<Integer> idSet = new HashSet<>();
        List<UserInner> userInnerList = new ArrayList<>();
        RandomCount randomCount = new RandomCount(innerCount,userCount);
        while (userInnerList.size() != userCount){
            Integer id = random.nextInt(50);
            if(!idSet.contains(id)) {
                idSet.add(id);
            } else continue;

            UserInner userInner = userInnerDAO.selectById(id);
            if( userInner != null){
                Integer count  = getRandomCount(randomCount);
                userInner.setInnerCount(count);
                userInnerList.add(userInner);
            }
        }
        return userInnerList;
    }


    /**
     * 生成随机数和
     * @param randomCount
     * @return
     */
    private Integer getRandomCount(RandomCount randomCount){

        if(randomCount.leftUser == 1 ){
            randomCount.leftUser --;
            return randomCount.leftCount;
        }
        Random random = new Random();
        int min = 1;
        int max = randomCount.leftCount / randomCount.leftUser * 2;
        int count = random.nextInt(max);

        count = count <= min ? 1 : count;
        randomCount.leftUser--;
        randomCount.leftCount -= count;
        return count;
    }


    @Override
    @Transactional
    public Integer seedInnerOrder(SeedInnerBuy innerBuy) {

        List<UserInner> inners = innerBuy.getUserInners();
        SeedInnerOrder order = new SeedInnerOrder();
        Integer result = -inners.size();
        Seed update = new Seed();
        update.setId(innerBuy.getSeedId());
        for(UserInner user : inners){

            Seed seed = seedDAO.selectById(innerBuy.getSeedId());
            if(seed == null || !seed.getState().equals(SeedEnum.REVIEWED.getState()))
                break;

            if(seed.getLeftCount() == 0)
                break;

            if(seed.getLeftCount() < user.getInnerCount())
                user.setInnerCount(seed.getLeftCount());

            order.setOrderNumber(Long.toString(System.currentTimeMillis()));
            order.setSeedId(innerBuy.getSeedId());
            order.setPlantCount(user.getInnerCount());
            order.setPlantTime(new Date());
            order.setUserId(user.getId());
            innerOrderDAO.insert(order);
            update.setLeftCount(seed.getLeftCount() - user.getInnerCount());
            update.setInnerCount(seed.getInnerCount() + user.getInnerCount());
            result += seedDAO.updateById(update);
            if(update.getLeftCount() == 0){
                //菌包售罄
                seedService.seedFull(innerBuy.getSeedId());
            }
        }

        return result;
    }
}

class RandomCount{

    public int leftCount;

    public int leftUser;

    public RandomCount(int leftCount, int leftUser) {
        this.leftCount = leftCount;
        this.leftUser = leftUser;
    }
}
