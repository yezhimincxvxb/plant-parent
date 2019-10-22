package com.moguying.plant.core.service.admin;


import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.AdminRole;

public interface AdminRoleService {

    @DataSource("read")
    PageResult<AdminRole> roleList(Integer page, Integer size, AdminRole where);

    @DataSource
    Integer saveRole(AdminRole role);

    @DataSource
    Integer delRole(Integer id);

    @DataSource
    ResultData<AdminRole> role(Integer id);

}
