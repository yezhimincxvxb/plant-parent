package com.moguying.plant.core.service.system;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.system.Apk;

public interface ApkService {


    PageResult<Apk> apkList(Integer page, Integer size, Apk where);


    ResultData<Integer> apkDelete(Integer id);


    ResultData<Integer> saveApk(Apk where);


    Apk newestApkInfo();
}
