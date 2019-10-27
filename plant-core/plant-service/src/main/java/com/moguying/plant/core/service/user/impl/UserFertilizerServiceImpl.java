package com.moguying.plant.core.service.user.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.FertilizerEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.dao.fertilizer.FertilizerDAO;
import com.moguying.plant.core.dao.fertilizer.FertilizerTypeDAO;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.fertilizer.FertilizerType;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerSearch;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.entity.user.vo.UserFertilizerInfo;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserFertilizerServiceImpl implements UserFertilizerService {

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private FertilizerDAO fertilizerDAO;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private FertilizerTypeDAO fertilizerTypeDAO;

    @Autowired
    private UserMoneyService userMoneyService;

    @Value("${excel.download.dir}")
    private String downloadDir;


    @Override
    @DS("write")
    public PageResult<UserFertilizerInfo> userFertilizers(Integer page, Integer size, Integer userId, FertilizerSearch search) {
        FertilizerUseCondition condition = new FertilizerUseCondition();
        condition.setUserId(userId);
        if (null != search.getType())
            condition.setType(search.getType());
        IPage<UserFertilizerInfo> pageResult = userFertilizerDAO.userFertilizers(new Page<>(page, size), condition);
        //查询即置过期
        userFertilizerDAO.updateOutTimeFertilizer(userId);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("read")
    public List<UserFertilizerInfo> canUseFertilizers(FertilizerUseCondition condition) {
        if (null != condition.getSeedOrderId()) {
            //查询匹配对应菌包类型
            SeedOrderDetail orderDetail =
                    seedOrderDetailDAO.selectByIdAndUserIdWithSeedTypeInfo(condition.getSeedOrderId(), condition.getUserId());
            condition.setSeedTypeId(orderDetail.getSeedTypeId());
        }

        /*//由于需要将券与种植的类型匹配，而不是与棚的id相匹配
        //将前端传入的棚id转为在棚区中种植的菌包类型id
        if (null != condition.getBlockId()) {
            Block block = blockDAO.selectById(condition.getBlockId());
            condition.setBlockId(block.getSeedType());
        }*/

        condition.setExpireTime(new Date());
        condition.setState(FertilizerEnum.FERTILIZER_NO_USE.getState());
        List<Integer> types = new ArrayList<>();
        //过滤为只显示非现金红包
        fertilizerTypeDAO.selectSelective(new FertilizerType())
                .stream()
                .filter((x) -> x.getId() < 4)
                .forEach((x) -> types.add(x.getId()));
        condition.setTypes(types);
        return userFertilizerDAO.userFertilizers(null,condition).getRecords();
    }

    @DS("read")
    @Override
    public PageResult<UserFertilizer> userFertilizerList(Integer page, Integer size, UserFertilizer where) {
        userFertilizerDAO.selectSelective(where);
        return null;
    }

    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<UserFertilizer> search, HttpServletRequest request) {
        new Thread(new DownloadService<>(userFertilizerDAO, search, UserFertilizer.class,
                new DownloadInfo("用户优惠券", request.getServletContext(), userId, downloadDir))).start();
    }

    /**
     * 手动添加用户券
     *
     * @param userFertilizer
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> addUserFertilizer(UserFertilizer userFertilizer) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        Fertilizer fertilizer = fertilizerDAO.selectById(userFertilizer.getFertilizerId());
        if (Objects.isNull(fertilizer))
            return resultData.setMessageEnum(MessageEnum.FERTILIZER_NOT_FOUND);
        return fertilizerService.distributeFertilizer(fertilizer.getTriggerGetEvent(),
                new TriggerEventResult().setUserId(userFertilizer.getUserId()), fertilizer.getId());
    }

    @Override
    public UserFertilizer getUserFertilizer(Integer userId, Integer id, Integer type) {
        if (type == null)
            return userFertilizerDAO.findByIdAndUserId(userId, id);

        if (type == 4)
            return userFertilizerDAO.getUserFertilizer(userId, id, type);

        return null;
    }

    @Override
    @DS("write")
    public Boolean redPackageSuccess(UserFertilizer userFertilizer) {
        UserMoneyOperator operator = new UserMoneyOperator();
        operator.setOperationId(userFertilizer.getUseOrderNumber());
        operator.setOpType(MoneyOpEnum.RED_PACKAGE);
        UserMoney money = new UserMoney(userFertilizer.getUserId());
        money.setAvailableMoney(userFertilizer.getFertilizerAmount());
        operator.setUserMoney(money);
        if( userMoneyService.updateAccount(operator) == null) return false;

        // 更新券状态
        userFertilizer.setState(1);
        return userFertilizerDAO.updateById(userFertilizer) > 0;
    }
}

