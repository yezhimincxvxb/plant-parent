package com.moguying.plant.core.service.admin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.admin.AdminMenuDAO;
import com.moguying.plant.core.dao.admin.AdminMenuMetaDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminMenu;
import com.moguying.plant.core.entity.admin.AdminMenuMeta;
import com.moguying.plant.core.service.admin.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuServiceImpl implements AdminMenuService {

    @Autowired
    private AdminMenuDAO adminMenuDAO;

    @Autowired
    private AdminMenuMetaDAO adminMenuMetaDAO;


    @Override
    @DS("write")
    public ResultData<Integer> saveAdminMenu(AdminMenu menu) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(null != menu && null != menu.getId()){
            if(null != menu.getMeta()){
                menu.getMeta().setMenuId(menu.getId());
                if(adminMenuMetaDAO.updateById(menu.getMeta()) <= 0)
                    return resultData;
            }
            if(adminMenuDAO.updateById(menu) <= 0)
                return resultData;
        } else if(null != menu){
            if(adminMenuDAO.insert(menu) > 0){
                AdminMenuMeta meta = menu.getMeta();
                if(null != meta) {
                    meta.setMenuId(menu.getId());
                    if(adminMenuMetaDAO.insert(meta) <= 0)
                        return resultData;
                }
            } else
                return resultData;
        }
        return resultData.setMessageEnum(MessageEnum.SUCCESS);

    }

    @Override
    @DS("read")
    public List<AdminMenu> parentMenu() {
        AdminMenu where = new AdminMenu();
        where.setParentId(0);
        return adminMenuDAO.selectSelective(where);
    }

    @Override
    @DS("read")
    public PageResult<AdminMenu> menuList(Integer page, Integer size, AdminMenu where) {
        adminMenuDAO.selectSelective(where);
        return null;
    }

    @DS("read")
    @Override
    public AdminMenu adminMenu(Integer id) {
        AdminMenu where = new AdminMenu();
        where.setId(id);
        List<AdminMenu> adminMenus = adminMenuDAO.selectSelective(where);
        if(null != adminMenus && adminMenus.size() == 1)
            return adminMenus.get(0);
        return null;
    }


    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DS("write")
    @Override
    public ResultData<Integer> deleteMenu(Integer id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        AdminMenu menu = adminMenuDAO.selectById(id);
        if (null == menu)
            return resultData.setMessageEnum(MessageEnum.ADMIN_MENU_NOT_EXIST);
        if (menu.getParentId().equals(0)) {
            AdminMenu where = new AdminMenu();
            where.setParentId(id);
            List<Integer> menuIds = new ArrayList<>();
            adminMenuDAO.selectSelective(where).stream()
                    .map(AdminMenu::getId)
                    .forEach(menuIds::add);
            if(menuIds.size() > 0) {
                if (adminMenuDAO.deleteByMenuIds(menuIds) <= 0 || adminMenuMetaDAO.deleteByMenuIds(menuIds) <= 0) {
                    return resultData;
                }
            }
        }
        if (adminMenuDAO.deleteById(id) > 0 && adminMenuMetaDAO.deleteById(id) >0) {
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    @DS("read")
    public List<AdminMenu> menus(AdminMenu menu) {
        return adminMenuDAO.selectSelective(menu);
    }

    @DS("read")
    @Override
    public List<AdminMenu> generateMenuTree(List<AdminMenu> menus) {
        List<AdminMenu> parents = menus.stream().filter((menu) -> menu.getParentId() == 0).collect(Collectors.toList());
        for(AdminMenu menu : parents){
            menu.setChildren(menus.stream().filter((x)->x.getParentId().equals(menu.getId())).collect(Collectors.toList()));
        }
        return parents;
    }
}
