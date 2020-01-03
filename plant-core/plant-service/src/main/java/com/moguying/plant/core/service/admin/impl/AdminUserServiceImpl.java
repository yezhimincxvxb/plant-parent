package com.moguying.plant.core.service.admin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.admin.AdminMenuDAO;
import com.moguying.plant.core.dao.admin.AdminMessageDAO;
import com.moguying.plant.core.dao.admin.AdminUserDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminMenu;
import com.moguying.plant.core.entity.admin.AdminMessage;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.admin.AdminMenuService;
import com.moguying.plant.core.service.admin.AdminUserService;
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
    public AdminUser login(String phone,String ip) {
        AdminUser adminUser = adminUserDAO.selectOne(
                new QueryWrapper<AdminUser>().lambda()
                .eq(AdminUser::getPhone,phone)
                .eq(AdminUser::getIsLocked,false)
        );
        if(null == adminUser) return null;
        else {
            AdminUser update = new AdminUser();
            update.setId(adminUser.getId());
            update.setLastLoginTime(new Date());
            update.setLastLoginIp(ip);
            adminUserDAO.updateById(update);
            return adminUser;
        }
    }

    @Override
    @DS("read")
    public AdminUser userInfo(Integer id) {
        AdminUser user = adminUserDAO.adminUserInfoById(id);
        user.setHasNewMessage(adminMessageDAO.hasNewMessage(id) > 0);
        List<AdminMenu> menus = adminMenuService.generateMenuTree(Arrays.asList(user.getRole().getViewCode().split(",")));
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
        IPage<AdminMessage> pageResult = adminMessageDAO.selectPage(new Page<>(page, size),
                new QueryWrapper<>(where).lambda().orderByDesc(AdminMessage::getAddTime));
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @DS("write")
    @Override
    public ResultData<Integer> saveAdminUser(AdminUser adminUser) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        if(null != adminUser.getBindId()) {
            User user = userDAO.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, adminUser.getBindPhone()));
            if(null != user)
                adminUser.setBindId(user.getId());
            else
                return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);
        }

        if(null != adminUser.getId() && adminUserDAO.updateById(adminUser) > 0){
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        } else if(null == adminUser.getId() && adminUserDAO.insert(adminUser) > 0) {
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    @DS("read")
    public PageResult<AdminUser> adminUserList(Integer page, Integer size, AdminUser where) {
        IPage<AdminUser> pageResult = adminUserDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @DS("write")
    @Override
    public Integer readMessage(Integer userId) {
        return adminMessageDAO.updateUserMessage(userId,1);
    }
}
