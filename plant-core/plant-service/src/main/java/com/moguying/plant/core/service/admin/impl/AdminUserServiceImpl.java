package com.moguying.plant.core.service.admin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.admin.AdminMenuDAO;
import com.moguying.plant.core.dao.admin.AdminMessageDAO;
import com.moguying.plant.core.dao.admin.AdminUserDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.admin.AdminMenu;
import com.moguying.plant.core.entity.admin.AdminMessage;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.admin.AdminMenuService;
import com.moguying.plant.core.service.admin.AdminUserService;
import com.moguying.plant.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserDAO adminUserDAO;

    @Autowired
    private AdminMessageDAO adminMessageDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AdminMenuService adminMenuService;

    @Autowired
    private AdminMenuDAO adminMenuDAO;



    @Override
    @DS("write")
    public AdminUser login(String name, String password) {
        AdminUser adminUser = adminUserDAO.selectByNameAndPassword(name,password);
        if(null == adminUser) return null;
        else {
            AdminUser update = new AdminUser();
            update.setId(adminUser.getId());
            update.setLastLoginTime(new Date());
            adminUserDAO.updateById(update);
            return adminUser;
        }
    }

    @Override
    @DS("read")
    public AdminUser userInfo(Integer id) {
        AdminUser user = adminUserDAO.adminUserInfoById(id);
        user.setHasNewMessage(adminMessageDAO.hasNewMessage(id) > 0);
        List<String> ids = Arrays.asList(user.getRole().getActionCode().split(","));
        List<AdminMenu> menus = adminMenuService.generateMenuTree(adminMenuDAO.selectByMenuStringIds(ids));
        user.setRouters(menus);
        return  user;
    }

    @Override
    @DS("write")
    public Integer addAdminMessage(AdminMessage adminMessage) {
        return adminMessageDAO.insert(adminMessage);
    }


    @Override
    @DS("read")
    public PageResult<AdminMessage> adminMessageList(Integer page, Integer size, AdminMessage where) {
        adminMessageDAO.selectSelective(where);
        return null;
    }

    @DS("write")
    @Override
    public Integer saveAdminUser(AdminUser adminUser) {
        if(null != adminUser.getPhone()){
            User where = new User();
            where.setPhone(adminUser.getPhone());
            List<User> users = userDAO.selectSelective(where);
            if(null != users && users.size() == 1){
                adminUser.setBindId(users.get(0).getId());
            }
        }

        if(null != adminUser.getPassword()) {
            adminUser.setPassword(PasswordUtil.INSTANCE.encode(adminUser.getPassword().getBytes()));
        }

        if(null != adminUser.getId()){
            return adminUserDAO.updateById(adminUser);
        } else {
            return adminUserDAO.insert(adminUser);
        }
    }

    @Override
    @DS("read")
    public PageResult<AdminUser> adminUserList(Integer page, Integer size, AdminUser where) {
        adminUserDAO.selectSelective(where);
        return null;
    }

    @DS("write")
    @Override
    public Integer readMessage(Integer userId) {
        return adminMessageDAO.updateUserMessage(userId,1);
    }
}
