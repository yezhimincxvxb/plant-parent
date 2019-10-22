package com.moguying.plant.core.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.PhoneMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * PhoneMessageDAO继承基类
 */
@Repository
public interface PhoneMessageDAO extends BaseMapper<PhoneMessage> {
    PhoneMessage selectByPhoneInTime(@Param("phone") String phone, @Param("inTime") String inTime);
}