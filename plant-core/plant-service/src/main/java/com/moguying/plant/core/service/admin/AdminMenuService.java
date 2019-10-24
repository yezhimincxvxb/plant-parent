package com.moguying.plant.core.service.admin;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminMenu;

import java.util.List;

public interface AdminMenuService {

    @DataSource
    ResultData<Integer> saveAdminMenu(AdminMenu menu);


    @DataSource("read")
    List<AdminMenu> parentMenu();

    List<AdminMenu> menus(AdminMenu menu);

    @DataSource("read")
    PageResult<AdminMenu> menuList(Integer page, Integer size, AdminMenu where);

    @DataSource("read")
    AdminMenu adminMenu(Integer id);


    @DataSource
    ResultData<Integer> deleteMenu(Integer id);

    @DataSource("read")
    List<AdminMenu> generateMenuTree(List<AdminMenu> menus);


}
