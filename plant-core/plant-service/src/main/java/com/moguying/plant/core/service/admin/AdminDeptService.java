package com.moguying.plant.core.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminDept;

public interface AdminDeptService extends IService<AdminDept> {
    ResultData<Boolean> deleteAdminDept(Integer id);
}

