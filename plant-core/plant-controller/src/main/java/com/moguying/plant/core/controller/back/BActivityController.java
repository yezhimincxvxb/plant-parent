package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;
import com.moguying.plant.core.service.content.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@Api(tags = "活动管理")
public class BActivityController {

    @Autowired
    private ActivityService activityService;


    /**
     * 活动列表
     *
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("活动列表")
    public PageResult<Activity> activityList(@RequestBody PageSearch<Activity> search) {
        return activityService.activityList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 活动详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("活动详情")
    public ResponseData<Activity> activityDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.activityDetail(id));
    }


    /**
     * 添加活动
     *
     * @param addActivity
     * @return
     */
    @PostMapping
    @ApiOperation("添加活动")
    public ResponseData<Integer> addActivity(@RequestBody Activity addActivity) {
        Integer id = activityService.addActivity(addActivity);
        if (id > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), id);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }

    /**
     * 编辑活动
     *
     * @param updateActivity
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation("编辑活动")
    public ResponseData<Integer> updateActivity(@RequestBody Activity updateActivity, @PathVariable Integer id) {
        updateActivity.setId(id);
        Integer rows = activityService.updateActivity(updateActivity);
        if (rows > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 删除活动
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除活动")
    public ResponseData<Integer> deleteActivity(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.deleteActivityById(id));
    }

    /**
     * 品宣部：后台查看，登录送菌包
     */
    @PostMapping("/prize/log")
    @ApiOperation("后台查看，登录送菌包")
    public PageResult<UserActivityLogVo> sendSeed(@RequestBody PageSearch<UserActivityLogVo> search) {
        return activityService.activityLog(search.getPage(), search.getSize(), search.getWhere());
    }

}
