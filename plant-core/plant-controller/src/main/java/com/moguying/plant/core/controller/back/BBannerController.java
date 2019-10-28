package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.Banner;
import com.moguying.plant.core.service.content.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banner")
public class BBannerController {


    @Autowired
    BannerService bannerService;


    /**
     * banner列表
     * @param search
     * @return
     */
    @PostMapping("/list")
    public PageResult<Banner> bannerList(@RequestBody PageSearch<Banner> search){
        if(null == search.getWhere())
            search.setWhere(new Banner());
        return bannerService.bannerList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 添加banner
     * @param banner
     * @return
     */

    @PostMapping
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
    public ResponseData<String> isShow(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),bannerService.setBannerShowState(id).toString());
    }


    /**
     * 删除banner
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData<Integer> deleteBanner(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),bannerService.deleteBanner(id));
    }


}
