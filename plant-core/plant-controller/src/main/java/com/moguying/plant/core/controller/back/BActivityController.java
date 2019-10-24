package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.dto.Activity;
import com.moguying.plant.core.service.content.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/backEnd/activity")
public class BActivityController {

    @Autowired
    private ActivityService activityService;


    /**
     * 活动列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    @ResponseBody
    public PageResult<Activity> activityList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                             @RequestParam(value = "size",defaultValue = "10") Integer size){
        return activityService.activityList(page,size);
    }


    /**
     * 活动详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseData<Activity> activityDetail(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),activityService.activityDetail(id));
    }


    /**
     * 添加活动
     * @param addActivity
     * @return
     */
    @PostMapping
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public ResponseData<Integer> deleteActivity(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),activityService.deleteActivityById(id));
    }



}
