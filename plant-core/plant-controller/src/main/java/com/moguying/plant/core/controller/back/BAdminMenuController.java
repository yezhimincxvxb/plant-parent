package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminMenu;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.system.vo.MenuTree;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/backEnd/admin/menu")
public class BAdminMenuController {

    @Autowired
    private AdminMenuService adminMenuService;


    /**
     * 菜单列表
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public PageResult<AdminMenu> menuList(@RequestBody PageSearch<AdminMenu> search){
        if(null == search.getWhere())
            search.setWhere(new AdminMenu());
        return adminMenuService.menuList(search.getPage(),search.getSize(),search.getWhere());
    }



    /**
     * 添加或修改菜单
     * @param user
     * @param menu
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseData<Integer> saveMenu(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user, @RequestBody AdminMenu menu) {
        ResultData<Integer> resultData = adminMenuService.saveAdminMenu(menu);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<Integer> deleteMenu(@PathVariable Integer id){
        ResultData<Integer> resultData  = adminMenuService.deleteMenu(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     *获取指定id的菜单信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseData<AdminMenu> adminMenu(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),adminMenuService.adminMenu(id));
    }




    /**
     * 获取父级菜单
     * @return
     */
    @GetMapping("/parent")
    @ResponseBody
    public ResponseData<List<AdminMenu>> parentMenu(){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),adminMenuService.parentMenu());
    }


    /**
     * 路由表
     * @return
     */
    @GetMapping("/tree")
    @ResponseBody
    public ResponseData<MenuTree> menuTree() {
        MenuTree tree = new MenuTree();
        List<AdminMenu> menus = adminMenuService.generateMenuTree(adminMenuService.menus(null));
        tree.setTree(menus);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),tree);
    }




}
