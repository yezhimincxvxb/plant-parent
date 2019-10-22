package com.moguying.plant.core.service.admin;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.AdminMessage;
import com.moguying.plant.core.entity.dto.AdminUser;

public interface AdminUserService {

    @DataSource("write")
    AdminUser login(String name, String password);

    @DataSource("read")
    AdminUser userInfo(Integer id);

    @DataSource("write")
    Integer addAdminMessage(AdminMessage adminMessage);

    @DataSource("read")
    PageResult<AdminMessage> adminMessageList(Integer page, Integer size, AdminMessage where);

    @DataSource("write")
    Integer saveAdminUser(AdminUser user);

    @DataSource("read")
    PageResult<AdminUser> adminUserList(Integer page, Integer size, AdminUser where);

    @DataSource("write")
    Integer readMessage(Integer userId);
}
