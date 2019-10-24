package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.user.UserInner;
import org.springframework.stereotype.Repository;

/**
 * UserInnerDAO继承基类
 */
@Repository
public interface UserInnerDAO extends BaseMapper<UserInner> {
}