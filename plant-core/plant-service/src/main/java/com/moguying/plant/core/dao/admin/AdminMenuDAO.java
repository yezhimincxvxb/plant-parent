package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.admin.AdminMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdminMenuDAO继承基类
 */
@Repository
public interface AdminMenuDAO extends BaseDAO<AdminMenu> {

    IPage<AdminMenu> selectSelective(Page<AdminMenu> page,@Param("wq") AdminMenu where);
    List<AdminMenu> selectSelective(@Param("wq") AdminMenu where);


    Integer deleteByMenuIds(@Param("ids") List<Integer> ids);


    List<AdminMenu> selectByMenuStringIds(@Param("ids") List<String> ids);
}