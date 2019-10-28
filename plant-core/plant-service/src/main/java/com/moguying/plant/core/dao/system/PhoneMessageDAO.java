package com.moguying.plant.core.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.system.PhoneMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * PhoneMessageDAO继承基类
 */
@Repository
public interface PhoneMessageDAO extends BaseDAO<PhoneMessage> {
    PhoneMessage selectByPhoneInTime(@Param("phone") String phone, @Param("inTime") String inTime);
}