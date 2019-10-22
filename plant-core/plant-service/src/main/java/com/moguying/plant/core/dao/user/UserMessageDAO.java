package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.UserMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMessageDAO继承基类
 */
@Repository
public interface UserMessageDAO extends BaseMapper<UserMessage> {
    List<UserMessage> messageListByUserId(@Param("userId") Integer userId, @Param("isDelete") Boolean isDelete);
    Integer countMessageByUserId(Integer userId);
    Integer updateMessageByUserIdSelective(UserMessage userMessage);
}