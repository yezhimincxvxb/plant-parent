package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDAO继承基类
 */
@Repository
public interface UserDAO extends BaseMapper<User> {
    List<User> selectSelective(User user);
    User userInfoById(Integer id);
    User userInfoByPhoneAndPassword(User user);
    User userInfoByInviteCodeAndId(@Param("userId") Integer userId, @Param("inviteCode") String inviteCode);
}