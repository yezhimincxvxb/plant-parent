package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.fertilizer.FertilizerType;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.system.SessionAdminUser;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/backEnd/fertilizer")
public class BFertilizerController {

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private UserFertilizerService userFertilizerService;


    /**
     * 券列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    @ResponseBody
    public PageResult<Fertilizer> fertilizerList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return fertilizerService.fertilizerList(page, size, null);
    }


    /**
     * 添加肥料（优惠券）
     *
     * @param fertilizer
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseData<Integer> addFertilizer(@RequestBody Fertilizer fertilizer) {
        if (null == fertilizer)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        if (fertilizerService.addFertilizer(fertilizer) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 删除优惠券
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseData<Integer> deleteFertilizer(@PathVariable Integer id) {
        if (id == null || id <= 0)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        ResultData<Integer> resultData = fertilizerService.deleteFertilizer(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 券类型
     *
     * @return
     */
    @GetMapping("/type")
    @ResponseBody
    public ResponseData<List<FertilizerType>> fertilizerType() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), fertilizerService.fertilizerType());
    }


    /**
     * 用户个人券列表
     *
     * @param search
     * @return
     */
    @PostMapping("/user/fertilizer")
    @ResponseBody
    public PageResult<UserFertilizer> userFertilizers(@RequestBody PageSearch<UserFertilizer> search) {
        return userFertilizerService.userFertilizerList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 下载用户个人券列表
     *
     * @param search
     * @return
     */
    @PostMapping("/user/excel")
    @ResponseBody
    public ResponseData<Integer> downloadUserFertilizer(@RequestBody PageSearch<UserFertilizer> search, HttpServletRequest request,
                                                        @SessionAttribute(SessionAdminUser.sessionKey) AdminUser user) {
        userFertilizerService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 手动添加用户优惠券
     *
     * @param fertilizer
     * @return
     */
    @PostMapping("/user/add")
    @ResponseBody
    public ResponseData<Integer> addUserFertilizer(@RequestBody UserFertilizer fertilizer) {
        ResultData<Integer> resultData = userFertilizerService.addUserFertilizer(fertilizer);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 券推送
     */
    @PostMapping("/user/push")
    @ResponseBody
    public ResponseData<String> pushFertilizer(@RequestBody Fertilizer push) {
        ResponseData<String> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

        if (push == null || push.getId() == null) return responseData;

        // 券不存在
        Fertilizer fertilizer = fertilizerService.findById(push.getId());
        if (fertilizer == null)
            return responseData.setMessage(MessageEnum.FERTILIZER_NOT_FOUND.getMessage())
                    .setState(MessageEnum.FERTILIZER_NOT_FOUND.getState());

        Integer success = fertilizerService.updateFertilizer(push);
        return success > 0 ?
                responseData.setMessage(MessageEnum.SUCCESS.getMessage())
                        .setState(MessageEnum.SUCCESS.getState())
                : responseData;
    }

}
