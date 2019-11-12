package com.moguying.plant.core.controller.back;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminRole;
import com.moguying.plant.core.service.admin.AdminMenuService;
import com.moguying.plant.core.service.admin.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/role")
@Api(tags = "角色管理")
public class BAdminRoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminMenuService adminMenuService;

    /**
     * 角色列表
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("角色列表")
    public PageResult<AdminRole> adminRoleList(@RequestBody PageSearch<AdminRole> search) {
        return adminRoleService.roleList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 修改/添加角色
     * @param role
     * @return
     */
    @PostMapping
    @ResponseBody
    @ApiOperation("修改/添加角色")
    public ResponseData<Integer> saveAdminRole(@RequestBody AdminRole role) {
        if(adminRoleService.saveRole(role) <= 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
    }



    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation("角色菜单树")
    public ResponseData<AdminRole> roleMenuTree(@PathVariable Integer id){
        ResultData<AdminRole> resultData = adminRoleService.role(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(), Optional.ofNullable(resultData.getData()).orElse(null));
    }



    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation("删除角色")
    public ResponseData<Integer> delAdminRole(@PathVariable Integer id){
        if(adminRoleService.delRole(id) <= 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
    }


}

