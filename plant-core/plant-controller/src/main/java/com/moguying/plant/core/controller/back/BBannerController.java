package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.Banner;
import com.moguying.plant.core.service.content.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/backEnd/banner")
public class BBannerController {


    @Autowired
    BannerService bannerService;


    /**
     * banner列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(produces = "application/json", value = "/list")
    @ResponseBody
    public PageResult<Banner> bannerList(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                         @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){
        return bannerService.bannerList(page,size,null);
    }


    /**
     * 添加banner
     * @param banner
     * @return
     */

    @PostMapping(produces = "application/json")
    @ResponseBody
    public ResponseData<Integer> addBanner(@RequestBody Banner banner){
        ResultData<Integer> resultData = bannerService.addBanner(banner);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }

    /**
     * 更新banner
     * @param banner
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<Integer> updateBanner(@RequestBody Banner banner, @PathVariable Integer id){
        ResultData<Integer> resultData = bannerService.updateBanner(id,banner);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 置banner是否显示
     * @param id
     * @return
     */
    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<String> isShow(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),bannerService.setBannerShowState(id).toString());
    }


    /**
     * 删除banner
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<Integer> deleteBanner(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),bannerService.deleteBanner(id));
    }


}
