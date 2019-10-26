package com.moguying.plant.core.service.admin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.admin.AdminMenuDAO;
import com.moguying.plant.core.dao.admin.AdminRoleDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminMenu;
import com.moguying.plant.core.entity.admin.AdminRole;
import com.moguying.plant.core.service.admin.AdminMenuService;
import com.moguying.plant.core.service.admin.AdminRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {


    @Autowired
    private AdminRoleDAO roleDAO;

    @Autowired
    private AdminMenuDAO adminMenuDAO;


    @Autowired
    private AdminMenuService adminMenuService;

    @Override
    @DS("read")
    public PageResult<AdminRole> roleList(Integer page, Integer size, AdminRole where) {
        roleDAO.selectSelective(where);
        return null;
    }

    @DS("write")
    @Override
    public Integer saveRole(AdminRole role) {
        if(null != role.getActionIds() && role.getActionIds().size() > 0){
            role.setActionCode(StringUtils.join(role.getActionIds(),','));
        }
        if(null != role.getId()){
            return roleDAO.updateById(role);
        } else {
            return roleDAO.insert(role);
        }
    }

    @Override
    @DS("write")
    public Integer delRole(Integer id) {
        return roleDAO.deleteById(id);
    }


    @Override
    @DS("read")
    public ResultData<AdminRole> role(Integer id) {
        ResultData<AdminRole> resultData = new ResultData<>(MessageEnum.ERROR,null);
        AdminRole role = roleDAO.selectById(id);
        if(null == role)
            return resultData.setMessageEnum(MessageEnum.ADMIN_ROLE_NOT_EXIST);
        List<String> strings = Arrays.asList(role.getActionCode().split(","));
        List<AdminMenu> menus = adminMenuDAO.selectByMenuStringIds(strings);
        role.setTree(adminMenuService.generateMenuTree(menus));
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(role);
    }
}
