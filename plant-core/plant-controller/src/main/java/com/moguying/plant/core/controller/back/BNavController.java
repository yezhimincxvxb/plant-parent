package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.content.Nav;
import com.moguying.plant.core.service.content.NavService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nav")
@Api(tags = "导航管理")
public class BNavController {


    @Autowired
    NavService navService;


    /**
     * 导航列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation("导航列表")
    public PageResult<Nav> mallNavList(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                       @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){

        return navService.navList(page,size,null);
    }


    /**
     * 添加导航
     * @param nav
     * @return
     */
    @PostMapping
    @ApiOperation("添加导航")
    public ResponseData<Integer> addNav(@RequestBody Nav nav){
        Integer result;
        if((result = navService.addNav(nav)) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),result);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState(),result);
    }



    /**
     * 删除导航
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除导航")
    public ResponseData<Integer> deleteNav(@PathVariable Integer id){
        if(navService.deleteNav(id) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 获取单个导航的信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个导航的信息")
    public ResponseData<Nav> getNav(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),navService.nav(id));
    }


    /**
     * 更新一个导航信息
     * @param id
     * @param nav
     * @return
     */
    @PutMapping(value = "/{id}")
    @ApiOperation("更新一个导航信息")
    public ResponseData<Integer> updateNav(@PathVariable Integer id, @RequestBody Nav nav){

        if(navService.updateNave(id,nav) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());

        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


}
