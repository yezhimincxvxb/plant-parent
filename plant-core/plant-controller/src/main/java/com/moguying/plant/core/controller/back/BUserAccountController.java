package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyStateEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyRecharge;
import com.moguying.plant.core.entity.account.MoneyWithdraw;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.account.UserMoneyLog;
import com.moguying.plant.core.entity.account.vo.RechargeReview;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.account.MoneyRechargeService;
import com.moguying.plant.core.service.account.MoneyWithdrawService;
import com.moguying.plant.core.service.account.UserMoneyLogService;
import com.moguying.plant.core.service.account.UserMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/account")
@Api(tags = "用户帐户管理")
public class BUserAccountController {

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private UserMoneyLogService userMoneyLogService;

    @Autowired
    private MoneyRechargeService moneyRechargeService;

    @Autowired
    private MoneyWithdrawService moneyWithdrawService;

    /**
     * 资金列表
     *
     * @return
     */
    @PostMapping(value = "/list")
    @ApiOperation("资金列表")
    public PageResult<UserMoney> userAccountList(@RequestBody PageSearch<UserMoney> search) {
        if (null == search.getWhere())
            search.setWhere(new UserMoney());
        return userMoneyService.userMoneyList(search.getPage(), search.getSize(), search.getWhere());
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
    @ApiOperation("资金导出表")
    public ResponseData<Integer> downloadExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                               @RequestBody PageSearch<UserMoney> search, HttpServletRequest request) {
        userMoneyService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 资金日志
     *
     * @return
     */
    @PostMapping(value = "/log")
    @ApiOperation("资金日志")
    public PageResult<UserMoneyLog> accountLogList(@RequestBody PageSearch<UserMoneyLog> search) {
        if (null == search.getWhere())
            search.setWhere(new UserMoneyLog());
        return userMoneyLogService.moneyLogList(search.getPage(), search.getSize(), search.getWhere());
    }

    /**
     * 导出资金日志表
     *
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/log/excel")
    @ApiOperation("导出资金日志表")
    public ResponseData<Integer> downloadLogExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                  @RequestBody PageSearch<UserMoneyLog> search, HttpServletRequest request) {
        if (Objects.isNull(search.getWhere())) search.setWhere(new UserMoneyLog());
        userMoneyLogService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 充值列表
     *
     * @return
     */
    @PostMapping(value = "/recharge/list")
    @ApiOperation("充值列表")
    public PageResult<MoneyRecharge> rechargeList(@RequestBody PageSearch<MoneyRecharge> search) {
        return moneyRechargeService.moneyRechargeList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 充值
     *
     * @return
     */
    @PostMapping("/recharge")
    @ApiOperation("充值")
    public ResponseData<Integer> recharge(@RequestBody MoneyRecharge recharge) {
        ResultData<Integer> addResult = moneyRechargeService.addMoneyRecharge(recharge);
        return new ResponseData<>(addResult.getMessageEnum().getMessage(), addResult.getMessageEnum().getState());
    }


    @PostMapping("/recharge/review/code")
    @ApiOperation("充值审核验证")
    public ResponseData<Integer> rechargeCode(@RequestBody RechargeReview review) {
        ResultData<Integer> reviewCode = moneyRechargeService.reviewCode(review);
        return new ResponseData<>(reviewCode.getMessageEnum().getMessage(),reviewCode.getMessageEnum().getState());
    }

    /**
     * 充值审核
     *
     * @return
     */
    @PostMapping("/recharge/review")
    @ApiOperation("充值审核")
    public ResponseData<Integer> rechargeReview(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,@RequestBody RechargeReview recharge) {
        ResultData<Integer> addResult = moneyRechargeService.reviewRecharge(user.getId(),recharge);
        return new ResponseData<>(addResult.getMessageEnum().getMessage(), addResult.getMessageEnum().getState());
    }




    /**
     * 提现列表
     *
     * @return
     */
    @PostMapping(value = "/withdraw")
    @ApiOperation("提现列表")
    public PageResult<MoneyWithdraw> withdrawList(@RequestBody PageSearch<MoneyWithdraw> search) {
        if (null == search.getWhere())
            search.setWhere(new MoneyWithdraw());
        return moneyWithdrawService.moneyWithdrawList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 下载用户提现列表
     *
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping(value = "/withdraw/excel")
    @ApiOperation("下载用户提现列表")
    public ResponseData<Integer> withdrawListExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                   @RequestBody PageSearch<MoneyWithdraw> search, HttpServletRequest request) {
        if (Objects.isNull(search.getWhere())) search.setWhere(new MoneyWithdraw());
        moneyWithdrawService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    /**
     * 提现复审
     *
     * @return
     */
    @PutMapping("/withdraw/{id}")
    @ApiOperation("提现复审")
    public ResponseData<Integer> reviewWithdraw(@PathVariable Integer id, @RequestParam("state") Boolean isPass,
                                                @SessionAttribute(SessionAdminUser.sessionKey) AdminUser adminUser) {
        ResultData<PaymentResponse> resultData = moneyWithdrawService.reviewMoneyWithdraw(id,
                isPass ? MoneyStateEnum.WITHDRAW_SUCCESS.getState() : MoneyStateEnum.WITHDRAW_FAILED.getState(),
                adminUser.getId());

        if (resultData.getMessageEnum().equals(MessageEnum.ERROR) && null != resultData.getData())
            return new ResponseData<>(resultData.getData().getMsg(), resultData.getMessageEnum().getState());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

}


