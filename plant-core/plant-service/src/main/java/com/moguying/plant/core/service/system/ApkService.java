package com.moguying.plant.core.service.system;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.Apk;

public interface ApkService {

    @DataSource("read")
    PageResult<Apk> apkList(Integer page, Integer size, Apk where);

    @DataSource("write")
    ResultData<Integer> apkDelete(Integer id);

    @DataSource("write")
    ResultData<Integer> saveApk(Apk where);

    @DataSource("read")
    Apk newestApkInfo();
}
