package com.moguying.plant.core.service.reap.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.reap.ReapFeeParamDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.ReapFeeParam;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.reap.ReapFeeParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReapFeeParamServiceImpl extends ServiceImpl<ReapFeeParamDAO, ReapFeeParam> implements ReapFeeParamService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private ReapFeeParamDAO reapFeeParamDAO;

    @Override
    public ResultData<Boolean> saveParam(ReapFeeParam entity) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        User user = userDAO.selectById(entity.getInviteUid());
        if(!user.getIsChannel())
            return resultData.setMessageEnum(MessageEnum.IS_NOT_A_CHANNEL);
        SeedType seedType = seedTypeDAO.selectById(entity.getSeedType());
        if(null == seedType)
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_EXIST);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(saveOrUpdate(entity));
    }


    @Override
    public PageResult<ReapFeeParam> reapFeeParamPageResult(Integer page, Integer size, ReapFeeParam where) {
        IPage<ReapFeeParam> pageResult = reapFeeParamDAO.selectSelective(new Page(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }
}
