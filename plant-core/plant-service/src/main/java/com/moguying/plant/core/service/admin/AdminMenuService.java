package com.moguying.plant.core.service.admin;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminMenu;

import java.util.List;

public interface AdminMenuService {

    ResultData<Integer> saveAdminMenu(AdminMenu menu);

    List<AdminMenu> parentMenu();

    List<AdminMenu> menus(AdminMenu menu);

    PageResult<AdminMenu> menuList(Integer page, Integer size, AdminMenu where);

    AdminMenu adminMenu(Integer id);

    ResultData<Integer> deleteMenu(Integer id);

    List<AdminMenu> generateMenuTree(List<AdminMenu> menus);


}
