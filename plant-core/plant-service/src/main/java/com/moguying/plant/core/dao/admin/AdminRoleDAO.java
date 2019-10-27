package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.admin.AdminRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdminRoleDAO继承基类
 */
@Repository
public interface AdminRoleDAO extends BaseMapper<AdminRole> {
}