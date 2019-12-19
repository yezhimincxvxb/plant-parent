package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SeedEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.SeedPic;
import com.moguying.plant.core.entity.seed.vo.SeedReview;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.seed.SeedPicService;
import com.moguying.plant.core.service.seed.SeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 菌包管理
 */
@RestController
@RequestMapping("/seed")
@Slf4j
@Api(tags = "菌包管理")
public class BSeedController {


    @Autowired
    private SeedService seedService;

    @Autowired
    private SeedPicService seedPicService;

    /**
     * 已审通过的菌包列表
     *
     * @return
     */
    @PostMapping(value = "/reviewed/list")
    @ApiOperation("已审通过的菌包列表")
    public PageResult<Seed> seedReviewedList(@RequestBody PageSearch<Seed> search) {

        Seed where;
        if (null != search.getWhere())
            where = search.getWhere();
        else
            where = new Seed();
        //查找已审核通过的菌包
        where.setState(SeedEnum.REVIEWED.getState());
        return seedService.seedList(search.getPage(), search.getSize(), where);
    }

    /**
     * 添加菌包
     *
     * @param addSeed
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("添加菌包")
    public ResponseData<Integer> seedAdd(@RequestBody Seed addSeed, @SessionAttribute(SessionAdminUser.sessionKey) AdminUser user) {
        addSeed.setAddUid(user.getId());
        ResultData<Integer> resultData = seedService.seedSave(addSeed, true);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 获取菌包详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取菌包详情")
    public ResponseData<Seed> seedDetail(@PathVariable Integer id) {
        if (id == 0 || id < 0) {
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }
        Seed seed = seedService.seed(id);
        SeedPic seedPic = seedPicService.seedPicById(Long.parseLong(seed.getPicIds()));
        seed.setPicUrl(seedPic.getPicUrl());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), seed);
    }

    /**
     * 编辑菌包
     *
     * @return
     */

    @PutMapping("/edit/{id}")
    @ApiOperation("编辑菌包")
    public ResponseData<Integer> seedEdit(@PathVariable Integer id, @RequestBody Seed seed) {
        seed.setId(id);
        ResultData<Integer> resultData = seedService.seedSave(seed, false);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 审核菌包
     *
     * @return
     */
    @PutMapping(value = "/review/{id}")
    @ApiOperation("审核菌包")
    public ResponseData<Integer> seedReview(@PathVariable Integer id, @RequestBody SeedReview seedReview, @SessionAttribute(SessionAdminUser.sessionKey) AdminUser user) {
        if (id < 0 || id == 0)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        ResultData<Integer> resultData = seedService.review(id, seedReview, user.getId());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());

    }


    /**
     * 撤销菌包
     *
     * @return
     * @deprecated
     */
    @PutMapping(value = "/cancel/{id}")
    @ApiOperation("撤销菌包")
    public ResponseData<Integer> seedCancel(@PathVariable Integer id) {
        ResultData<Integer> resultData = seedService.seedCancel(id);
        if (!resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), id);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), id);
    }


    /**
     * 待审菌包列表
     * 必须独立方法因为需权限管理
     *
     * @return
     */
    @PostMapping(value = "/review/list")
    @ApiOperation("待审菌包列表")
    public PageResult<Seed> seedReviewList(@RequestBody PageSearch<Seed> search) {
        Seed where = null;
        if (null != search.getWhere())
            where = search.getWhere();
        else
            where = new Seed();
        where.setState(SeedEnum.NEED_REVIEW.getState());
        return seedService.seedList(search.getPage(), search.getSize(), where);
    }


    /**
     * 已售罄的菌包列表
     * 必须独立方法因为需权限管理
     *
     * @return
     */
    @PostMapping(value = "/planted/list")
    @ApiOperation("已售罄的菌包列表")
    public PageResult<Seed> seedPlantedList(@RequestBody PageSearch<Seed> search) {
        Seed where = null;
        if (null != search.getWhere())
            where = search.getWhere();
        else
            where = new Seed();
        where.setState(SeedEnum.PLANTED.getState());
        return seedService.seedList(search.getPage(), search.getSize(), where);
    }


    /**
     * 售罄菌包下载
     *
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/planted/excel")
    @ApiOperation("售罄菌包下载")
    public ResponseData<Integer> seedPlantedListExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                      @RequestBody PageSearch<Seed> search, HttpServletRequest request) {
        if (Objects.isNull(search.getWhere()))
            search.setWhere(new Seed());
        search.getWhere().setState(SeedEnum.PLANTED.getState());
        seedService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 菌包取消种植列表
     *
     * @return
     * @deprecated
     */

    @GetMapping("/cancel/list")
    @ApiOperation("菌包取消种植列表")
    public PageResult<Seed> seedCancelList(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                           @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        Seed where = new Seed();
        where.setState(SeedEnum.CANCEL.getState());
        return seedService.seedList(page, size, where);
    }


    /**
     * 菌包上下架
     *
     * @param id
     * @return
     */
    @PutMapping("/show/{id}")
    @ApiOperation("菌包上下架")
    public ResponseData<String> seedIsShow(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), seedService.seedShow(id).toString());
    }


}
