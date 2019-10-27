package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDAO继承基类
 */
@Repository
public interface UserDAO extends BaseMapper<User> {
    IPage<User> selectSelective(Page<User> page, @Param("wq") User user);
    User userInfoById(Integer id);
    User userInfoByPhoneAndPassword(User user);
    User userInfoByInviteCodeAndId(@Param("userId") Integer userId, @Param("inviteCode") String inviteCode);
}