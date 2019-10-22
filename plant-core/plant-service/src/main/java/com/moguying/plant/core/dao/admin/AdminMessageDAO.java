package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.AdminMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdminMessageDAO继承基类
 */
@Repository
public interface AdminMessageDAO extends BaseMapper<AdminMessage> {

    List<AdminMessage> selectSelective(AdminMessage where);

    Integer hasNewMessage(Integer userId);

    Integer updateUserMessage(@Param("userId") Integer userId, @Param("state") Integer state);
}