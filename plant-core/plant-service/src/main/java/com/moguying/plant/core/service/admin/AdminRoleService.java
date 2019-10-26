package com.moguying.plant.core.service.admin;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminRole;

public interface AdminRoleService {

    PageResult<AdminRole> roleList(Integer page, Integer size, AdminRole where);

    Integer saveRole(AdminRole role);

    Integer delRole(Integer id);

    ResultData<AdminRole> role(Integer id);

}
