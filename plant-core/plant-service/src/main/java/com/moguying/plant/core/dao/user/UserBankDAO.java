package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.user.UserBank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserBankDAO继承基类
 */
@Repository
public interface UserBankDAO extends BaseMapper<UserBank> {
    List<UserBank> selectSelective(UserBank bank);
    UserBank bankInfoByUserIdAndId(@Param("userId") Integer userId, @Param("id") Integer id);
}