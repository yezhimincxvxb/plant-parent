package com.moguying.plant.core.controller.back;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.service.content.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backEnd/activity")
public class BActivityController {

    @Autowired
    private ActivityService activityService;


    /**
     * 活动列表
     * @return
     */
    @PostMapping("/list")
    public PageResult<Activity> activityList(@RequestBody PageSearch<Activity> search){
        return activityService.activityList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 活动详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseData<Activity> activityDetail(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),activityService.activityDetail(id));
    }


    /**
     * 添加活动
     * @param addActivity
     * @return
     */
    @PostMapping
    public ResponseData<Integer> addActivity(@RequestBody Activity addActivity){
        Integer id = activityService.addActivity(addActivity);
        if(id > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),id);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }

    /**
     * 编辑活动
     * @param updateActivity
     * @return
     */
    @PutMapping("/{id}")
    public ResponseData<Integer> updateActivity(@RequestBody Activity updateActivity, @PathVariable Integer id){
        updateActivity.setId(id);
        Integer rows = activityService.updateActivity(updateActivity);
        if(rows > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }





    /**
     * 删除活动
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseData<Integer> deleteActivity(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),activityService.deleteActivityById(id));
    }



}
