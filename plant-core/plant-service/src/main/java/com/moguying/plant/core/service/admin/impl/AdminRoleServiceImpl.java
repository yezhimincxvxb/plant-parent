package com.moguying.plant.core.service.admin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        IPage<AdminRole> pageResult = roleDAO.selectPage(new Page<>(page, size), new QueryWrapper<>(where));
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @DS("write")
    @Override
    public Integer saveRole(AdminRole role) {
        if (null != role.getActionIds() && role.getActionIds().size() > 0) {
            role.setActionCode(StringUtils.join(role.getActionIds(), ','));
        }
        if (null != role.getId()) {
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
        ResultData<AdminRole> resultData = new ResultData<>(MessageEnum.ERROR, null);
        AdminRole role = roleDAO.selectById(id);
        if (null == role)
            return resultData.setMessageEnum(MessageEnum.ADMIN_ROLE_NOT_EXIST);
        role.setTree(adminMenuService.generateMenuTree(Arrays.asList(role.getActionCode().split(","))));
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(role);
    }
}
