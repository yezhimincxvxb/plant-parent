package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.admin.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdminUserDAO继承基类
 */
@Repository
public interface AdminUserDAO extends BaseMapper<AdminUser> {
    AdminUser selectByNameAndPassword(@Param("name") String name, @Param("password") String password);
    List<AdminUser> selectSelective(AdminUser where);
    AdminUser adminUserInfoById(Integer id);
}