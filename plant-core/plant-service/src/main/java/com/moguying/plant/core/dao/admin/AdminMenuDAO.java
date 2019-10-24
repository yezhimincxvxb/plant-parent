package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.admin.AdminMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdminMenuDAO继承基类
 */
@Repository
public interface AdminMenuDAO extends BaseMapper<AdminMenu> {

    List<AdminMenu> selectSelective(AdminMenu where);


    Integer deleteByMenuIds(@Param("ids") List<Integer> ids);


    List<AdminMenu> selectByMenuStringIds(@Param("ids") List<String> ids);
}