package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.admin.AdminMessage;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/backEnd/admin/user")
@Slf4j
public class BAdminUserController {


    @Autowired
    private AdminUserService adminUserService;


    /**
     * 后台用户信息
     * @param adminUser
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public ResponseData<AdminUser> userInfo(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser adminUser){

        AdminUser user =  adminUserService.userInfo(adminUser.getId());
        if(user != null)
            return  new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),user);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 后台用户站内信
     * @param user
     * @param search
     * @return
     */
    @PostMapping("/message")
    @ResponseBody
    public PageResult<AdminMessage> userMessage(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                @RequestBody PageSearch<AdminMessage> search) {
        AdminMessage where;
        if(null == search.getWhere())
            where = new AdminMessage();
        else
            where = search.getWhere();
        where.setUserId(user.getId());
        adminUserService.readMessage(user.getId());
        return adminUserService.adminMessageList(search.getPage(),search.getSize(),where);
    }


    /**
     * 添加/修改用户
     * @param user
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseData<Integer> addUser(@RequestBody AdminUser user){
        if(adminUserService.saveAdminUser(user) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 后台用户列表
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public PageResult<AdminUser> adminUserList(@RequestBody PageSearch<AdminUser> search){
        return adminUserService.adminUserList(search.getPage(),search.getSize(),search.getWhere());
    }



}