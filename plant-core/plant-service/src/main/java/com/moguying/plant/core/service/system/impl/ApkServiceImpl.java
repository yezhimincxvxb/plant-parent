package com.moguying.plant.core.service.system.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.system.ApkDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.system.Apk;
import com.moguying.plant.core.service.system.ApkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApkServiceImpl implements ApkService {

    @Autowired
    private ApkDAO apkDAO;

    @Override
    @DS("read")
    public PageResult<Apk> apkList(Integer page, Integer size , Apk where) {
        apkDAO.selectSelective(where);
        return null;
    }

    @Override
    @DS("write")
    public ResultData<Integer> apkDelete(Integer id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        Apk apk = apkDAO.selectById(id);
        if(null == apk)
            return resultData.setMessageEnum(MessageEnum.APK_NOT_EXIST);

        if(apkDAO.deleteById(id) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Override
    @DS("write")
    public ResultData<Integer> saveApk(Apk where) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(null == where.getId()){
            where.setAddTime(new Date());
            if(apkDAO.insert(where) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
        } else {
            Apk apk = apkDAO.selectById(where.getId());
            if (null == apk)
                return resultData.setMessageEnum(MessageEnum.APK_NOT_EXIST);
            if (apkDAO.updateById(where) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    @DS("read")
    public Apk newestApkInfo() {
        return apkDAO.newestApkInfo();
    }
}
