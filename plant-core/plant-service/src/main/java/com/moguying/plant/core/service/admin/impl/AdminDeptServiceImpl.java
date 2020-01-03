package com.moguying.plant.core.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.admin.AdminDeptDAO;
import com.moguying.plant.core.dao.admin.AdminUserDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminDept;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.service.admin.AdminDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDeptServiceImpl extends ServiceImpl<AdminDeptDAO,AdminDept> implements AdminDeptService {

    @Autowired
    private AdminUserDAO adminUserDAO;

    @Override
    public ResultData<Boolean> deleteAdminDept(Integer id) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        Integer hasBind = adminUserDAO.selectCount(new QueryWrapper<AdminUser>().lambda().eq(AdminUser::getDeptId, id));
        if(hasBind > 0)
            return resultData.setMessageEnum(MessageEnum.ADMIN_DEPT_CANNOT_DEL);
        if(removeById(id))
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        return resultData;
    }
}
