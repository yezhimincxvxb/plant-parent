package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.user.UserAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserAddressDAO继承基类
 */
@Repository
public interface UserAddressDAO extends BaseDAO<UserAddress> {
    List<UserAddress> selectSelective(UserAddress address);

    UserAddress selectByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId, @Param("isDelete") Boolean isDelete);

    Integer setNoDefault(@Param("id") Integer id, @Param("userId") Integer userId);

    Integer setDefault(@Param("id") Integer id, @Param("userId") Integer userId);

    UserAddress userDefaultAddress(Integer userId);

    Integer getDefaultNum(Integer userId);

    Integer setDefaultByTime(Integer userId);
}