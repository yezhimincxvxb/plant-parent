package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.constant.ReapEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.dto.AdminUser;
import com.moguying.plant.core.entity.dto.Reap;
import com.moguying.plant.core.entity.dto.SessionAdminUser;
import com.moguying.plant.core.service.reap.ReapService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/backEnd/reap")
public class BReapController {


    @Autowired
    private ReapService reapService;
    /**
     * 采摘列表
     * @param search
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageResult<Reap> reapList(@RequestBody PageSearch<Reap> search){
        return reapService.reapList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 采摘列表下载
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping("/excel")
    @ResponseBody
    public ResponseData<Integer> reapListExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                               @RequestBody PageSearch<Reap> search, HttpServletRequest request){
        if(Objects.isNull(search.getWhere()))
            search.setWhere(new Reap());
        reapService.downloadExcel(user.getId(),search,request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
    }



    /**
     * 采摘
     * @param ids
     * @return
     */
    @PutMapping(produces = "application/json")
    @ResponseBody
    public ResponseData<Integer> reap(@RequestParam String ids, @RequestParam Integer state){
        if(StringUtils.isEmpty(ids))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(),MessageEnum.PARAMETER_ERROR.getState());
        String[] idArr = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for(String id : idArr)
            idList.add(Integer.parseInt(id));
        Reap update = new Reap();

        switch (state) {
            case 1:
                update.setState(ReapEnum.REAP_DONE.getState());
                break;
            case 2:
                update.setState(ReapEnum.SALE_ING.getState());
                break;
        }
        if(update.getState() == null)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(),MessageEnum.PARAMETER_ERROR.getState());
        update.setRecReapTime(new Date());
        if(reapService.updateReapState(idList,update) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }













}
