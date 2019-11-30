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
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/fertilizer")
@Api(tags = "优惠券管理")
public class BFertilizerController {

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private UserFertilizerService userFertilizerService;


    /**
     * 券列表
     *
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("券列表")
    public PageResult<Fertilizer> fertilizerList(@RequestBody PageSearch<Fertilizer> search) {
        if (null == search.getWhere())
            search.setWhere(new Fertilizer());
        return fertilizerService.fertilizerList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 添加肥料（优惠券）
     *
     * @param fertilizer
     * @return
     */
    @PostMapping
    @ApiOperation("添加肥料（优惠券）")
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
    @ApiOperation("删除优惠券")
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
    @ApiOperation("券类型")
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
    @ApiOperation("用户个人券列表")
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
    @ApiOperation("下载用户个人券列表")
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
    @ApiOperation("手动添加用户优惠券")
    public ResponseData<Integer> addUserFertilizer(@RequestBody UserFertilizer fertilizer) {
        ResultData<Integer> resultData = userFertilizerService.addUserFertilizer(fertilizer);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 券推送
     */
    @PostMapping("/user/push")
    @ApiOperation("券推送")
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
