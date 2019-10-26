package com.moguying.plant.core.service.admin;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.admin.AdminMessage;
import com.moguying.plant.core.entity.admin.AdminUser;

public interface AdminUserService {

    AdminUser login(String name, String password);

    AdminUser userInfo(Integer id);

    Integer addAdminMessage(AdminMessage adminMessage);

    PageResult<AdminMessage> adminMessageList(Integer page, Integer size, AdminMessage where);

    Integer saveAdminUser(AdminUser user);

    PageResult<AdminUser> adminUserList(Integer page, Integer size, AdminUser where);

    Integer readMessage(Integer userId);
}
