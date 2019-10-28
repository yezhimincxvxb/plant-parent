package com.moguying.plant.core.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.system.Apk;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ApkDAO继承基类
 */
@Repository
public interface ApkDAO extends BaseDAO<Apk> {
    List<Apk> selectSelective(Apk where);
    Apk newestApkInfo();
}