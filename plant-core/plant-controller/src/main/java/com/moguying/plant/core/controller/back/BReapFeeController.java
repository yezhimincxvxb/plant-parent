package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.reap.ReapFee;
import com.moguying.plant.core.entity.reap.ReapFeeParam;
import com.moguying.plant.core.entity.reap.vo.FeeUpdateStateRequest;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.reap.ReapFeeParamService;
import com.moguying.plant.core.service.reap.ReapFeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reap/fee")
@Slf4j
@Api(tags = "渠道费用管理")
public class BReapFeeController {

    @Autowired
    private ReapFeeService reapFeeService;

    @Autowired
    private ReapFeeParamService reapFeeParamService;


    /**
     * 查询运营费用列表
     *
     * @param search
     * @return
     */
    @PostMapping
    @ApiOperation("查询运营费用列表")
    public PageResult<ReapFee> reapFeePageResult(@RequestBody PageSearch<ReapFee> search) {

        return reapFeeService.reapFeeList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 合作渠道查询
     * 费用列表
     *
     * @param search
     * @return
     */
    @PostMapping("/cop")
    @ApiOperation("合作渠道查询费用列表")
    public PageResult<ReapFee> reapFeeCopPageResult(@RequestBody PageSearch<ReapFee> search,
                                                    @SessionAttribute(SessionAdminUser.sessionKey) AdminUser user) {
        ReapFee reapFee = search.getWhere();
        if (null == reapFee)
            reapFee = new ReapFee();
        reapFee.setInviteUid(user.getBindId());
        return reapFeeService.reapFeeList(search.getPage(), search.getSize(), reapFee);
    }


    /**
     * 渠道商导出表
     *
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/cop/excel")
    @ApiOperation("渠道商导出表")
    public ResponseData<Integer> copDownloadExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                  @RequestBody PageSearch<ReapFee> search, HttpServletRequest request) {
        ReapFee reapFee = search.getWhere();
        if (null == reapFee)
            reapFee = new ReapFee();
        reapFee.setInviteUid(user.getBindId());
        search.setWhere(reapFee);
        downloadExcel(user, search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 导出表
     *
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/excel")
    @ApiOperation("渠道导出表")
    public ResponseData<Integer> downloadExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                               @RequestBody PageSearch<ReapFee> search, HttpServletRequest request) {
        reapFeeService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 结算参数列表
     *
     * @param search
     * @return
     */
    @PostMapping("/param/list")
    @ApiOperation("结算参数列表")
    public PageResult<ReapFeeParam> reapFeeParamList(@RequestBody PageSearch<ReapFeeParam> search) {
        return reapFeeParamService.reapFeeParamPageResult(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 添加或修改结算参数
     *
     * @param param
     * @return
     */
    @PostMapping("/param")
    @ApiOperation("添加或修改结算参数")
    public ResponseData<Integer> saveFeeParam(@RequestBody ReapFeeParam param) {
        ResultData<Boolean> resultData = reapFeeParamService.saveParam(param);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 修改审核状态
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改审核状态")
    public ResponseData<Integer> updateState(@RequestBody FeeUpdateStateRequest stateRequest) {
        if (null == stateRequest.getId() || null == stateRequest.getState())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        ResultData<Boolean> resultData = reapFeeService.updateState(stateRequest);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 删除参数
     *
     * @param id
     * @return
     */
    @DeleteMapping("/param/{id}")
    @ApiOperation("删除参数")
    public ResponseData<Integer> deleteFeeParam(@PathVariable Integer id) {
        if (reapFeeParamService.removeById(id))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


}
