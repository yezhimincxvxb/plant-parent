package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.user.UserMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMessageDAO继承基类
 */
@Repository
public interface UserMessageDAO extends BaseDAO<UserMessage> {
    IPage<UserMessage> messageListByUserId(Page<UserMessage> page, @Param("userId") Integer userId, @Param("isDelete") Boolean isDelete);

    Integer countMessageByUserId(Integer userId);

    Integer updateMessageByUserIdSelective(UserMessage userMessage);
}