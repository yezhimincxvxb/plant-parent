package com.moguying.plant.core.controller.back;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminDept;
import com.moguying.plant.core.service.admin.AdminDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/dept")
@RestController
@Api(tags = "部门管理")
public class BAdminDeptController {

    @Autowired
    private AdminDeptService adminDeptService;

    @PostMapping("/list")
    @ApiOperation("部门列表")
    public PageResult<AdminDept> adminDeptPageResult(@RequestBody PageSearch<AdminDept> search) {
        IPage<AdminDept> page = adminDeptService.page(new Page<>(search.getPage(), search.getSize()), new QueryWrapper<>(search.getWhere()));
        return new PageResult<>(page.getTotal(),page.getRecords());
    }

    @PostMapping
    @ApiOperation("添加/更新部门")
    public ResponseData<AdminDept> updateAdminDept(@RequestBody AdminDept adminDept) {
        boolean b = adminDeptService.saveOrUpdate(adminDept);
        if(b) return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除指定部门")
    public ResponseData<Boolean> deleteAdminDept(@PathVariable Integer id) {
        ResultData<Boolean> resultData = adminDeptService.deleteAdminDept(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }

}
