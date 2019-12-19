package com.moguying.plant.core.controller.api;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.constant.*;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.ValidateUser;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyWithdraw;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.account.vo.AccountInfo;
import com.moguying.plant.core.entity.account.vo.DetailInfo;
import com.moguying.plant.core.entity.account.vo.InAndOutMoney;
import com.moguying.plant.core.entity.account.vo.WithdrawRequest;
import com.moguying.plant.core.entity.common.vo.Profit;
import com.moguying.plant.core.entity.mall.vo.ProductInfo;
import com.moguying.plant.core.entity.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.payment.request.PaymentRequestForHtml;
import com.moguying.plant.core.entity.payment.request.WithdrawMoneyPageRequest;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.payment.response.SendWithdrawSmsCodeResponse;
import com.moguying.plant.core.entity.payment.response.WithdrawMoneyResponse;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.vo.MonthProfit;
import com.moguying.plant.core.entity.user.vo.TotalProfit;
import com.moguying.plant.core.entity.user.vo.UserMoneyDate;
import com.moguying.plant.core.entity.user.vo.UserMoneyDetail;
import com.moguying.plant.core.service.account.MoneyWithdrawService;
import com.moguying.plant.core.service.account.UserMoneyLogService;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;


@RestController
@RequestMapping("/account")
@Api(tags = "用户帐户")
public class AAccountController {


    @Autowired
    private UserMoneyService moneyService;

    @Autowired
    private UserMoneyLogService moneyLogService;

    @Autowired
    private MoneyWithdrawService moneyWithdrawService;

    @Autowired
    private ReapService reapService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneMessageService phoneMessageService;

    @Autowired
    private UserMoneyService userMoneyService;


    /**
     * 首页数据
     *
     * @return
     */
    @GetMapping
    @ApiOperation("首页数据")
    public ResponseData<AccountInfo> accountInfo(@LoginUserId Integer userId) {
        UserMoney userMoney = moneyService.userMoneyInfo(userId);
        Date firstDayOfMonth = DateUtil.INSTANCE.firstDayOfMonth();
        Date lastDayOfMonth = DateUtil.INSTANCE.todayEnd(DateUtil.INSTANCE.lastDayOfMonth());
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setTotalAmount(userMoney.getAvailableMoney().add(userMoney.getCollectMoney()).add(userMoney.getFreezeMoney()));
        accountInfo.setAvailableAmount(userMoney.getAvailableMoney());
        accountInfo.setAccruedProfit(moneyLogService.sumFieldAndType(FieldEnum.AFFECT_MONEY, userId,
                Arrays.asList(
                        MoneyOpEnum.SALE_REAP_SEED_PROFIT,
                        MoneyOpEnum.INVITE_AWARD,
                        MoneyOpEnum.PANT_SEED_FERTILIZER)));
        accountInfo.setMonthProfit(reapService.reapProfitStatistics(userId, firstDayOfMonth, lastDayOfMonth, null));
        User userInfo = userService.userInfoById(userId);
        accountInfo.setPaymentAccount(userInfo.getPaymentAccount());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), accountInfo);
    }


    /**
     * 提现记录列表
     */
    @PostMapping("/withdraw/log")
    @ApiOperation("提现列表")
    public PageResult<MoneyWithdraw> withdrawList(@RequestBody PageSearch search, @LoginUserId Integer userId) {
        MoneyWithdraw where = new MoneyWithdraw();
        where.setUserId(userId);
        where.setState(MoneyStateEnum.WITHDRAW_IN_ACCOUNT.getState());
        return moneyWithdrawService.apiMoneyWithdrawList(search.getPage(), search.getSize(), where);
    }


    /**
     * 提现审核记录
     */
    @PostMapping("/withdraw/review")
    @ApiOperation("提现审核记录")
    public PageResult<MoneyWithdraw> withdrawReviewList(@RequestBody PageSearch search, @LoginUserId Integer userId) {
        MoneyWithdraw where = new MoneyWithdraw();
        where.setUserId(userId);
        List<Integer> states = new ArrayList<>();
        states.add(MoneyStateEnum.WITHDRAWING.getState());
        states.add(MoneyStateEnum.WITHDRAW_FAILED.getState());
        states.add(MoneyStateEnum.WITHDRAW_SUCCESS.getState());
        states.add(MoneyStateEnum.WITHDRAW_SUCCESS.getState());
        states.add(MoneyStateEnum.WITHDRAW_ACCOUNT_ING.getState());
        where.setInState(states);
        return moneyWithdrawService.apiMoneyWithdrawList(search.getPage(), search.getSize(), where);
    }


    /**
     * 提交提现
     */
    @ValidateUser
    @PostMapping("/withdraw")
    @ApiOperation("提交提现")
    public ResponseData<Integer> withdraw(@LoginUserId Integer userId, @RequestBody WithdrawRequest withdrawRequest) {
        if (null == withdrawRequest.getBankId())
            return new ResponseData<>(MessageEnum.BANK_CARD_ID_EMPTY.getMessage(), MessageEnum.BANK_CARD_ID_EMPTY.getState());
        if (null == withdrawRequest.getCode())
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_IS_EMPTY.getMessage(), MessageEnum.MESSAGE_CODE_IS_EMPTY.getState());
        User userInfo = userService.userInfoById(userId);
        if (StringUtils.isEmpty(userInfo.getPayPassword()))
            return new ResponseData<>(MessageEnum.NEED_PAY_PASSWORD.getMessage(), MessageEnum.NEED_PAY_PASSWORD.getState());
        if (phoneMessageService.validateMessage(userInfo.getPhone(), withdrawRequest.getCode()) <= 0)
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_ERROR.getMessage(), MessageEnum.MESSAGE_CODE_ERROR.getState());
        MoneyWithdraw withdraw = new MoneyWithdraw();
        withdraw.setUserId(userId);
        withdraw.setWithdrawMoney(withdrawRequest.getMoney());
        ResultData<Integer> resultData = moneyWithdrawService.addMoneyWithdraw(withdraw, withdrawRequest.getBankId());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 发送提现短信验证码
     */
    @ValidateUser
    @PostMapping("/withdraw/sms")
    @ApiOperation("提现发送第三方提现短信")
    public ResponseData<SendWithdrawSmsCodeResponse> sendWithdrawSms(@LoginUserId Integer userId, @RequestBody WithdrawRequest withdrawRequest) {
        if (null == withdrawRequest.getWithdrawId())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        MoneyWithdraw moneyWithdraw = new MoneyWithdraw();
        moneyWithdraw.setId(withdrawRequest.getWithdrawId());
        moneyWithdraw.setUserId(userId);
        ResultData<PaymentResponse<SendWithdrawSmsCodeResponse>> resultData = moneyWithdrawService.sendWithdrawSms(moneyWithdraw);
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            return new ResponseData<>(resultData.getMessageEnum().getMessage(),
                    resultData.getMessageEnum().getState(),
                    resultData.getData().getData());
        } else if (null != resultData.getData()) {
            return new ResponseData<>(resultData.getData().getMsg(), MessageEnum.ERROR.getState());
        }
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 提现到账-纯接口
     */
    @ValidateUser
    @PutMapping("/withdraw")
    @ApiOperation("提现到账-纯接口")
    public ResponseData<Integer> withdrawToAccount(@LoginUserId Integer userId, @RequestBody WithdrawRequest withdrawRequest) {
        if (null == withdrawRequest.getWithdrawId() || null == withdrawRequest.getSeqNo() || null == withdrawRequest.getSmsCode())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        MoneyWithdraw moneyWithdraw = new MoneyWithdraw();
        moneyWithdraw.setId(withdrawRequest.getWithdrawId());
        moneyWithdraw.setUserId(userId);
        moneyWithdraw.setOrderNumber(withdrawRequest.getOrderNumber());
        moneyWithdraw.setSeqNo(withdrawRequest.getSeqNo());
        ResultData<PaymentResponse<WithdrawMoneyResponse>> resultData = moneyWithdrawService.withdrawToAccount(moneyWithdraw, withdrawRequest.getSmsCode());
        if (null != resultData.getData() && !resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return new ResponseData<>(resultData.getData().getMsg(), MessageEnum.ERROR.getState());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 提现到账-页面
     *
     * @param userId
     * @return
     */
    @ValidateUser
    @PostMapping("/to/account")
    @ApiOperation("提现到账-页面")
    @SuppressWarnings("all")
    public ResponseData<PaymentRequestForHtml> toAccountPage(@LoginUserId Integer userId, @RequestBody WithdrawRequest withdrawRequest) {
        if (null == withdrawRequest.getWithdrawId())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        MoneyWithdraw withdraw = moneyWithdrawService.selectById(withdrawRequest.getWithdrawId());
        if (null == withdraw)
            return new ResponseData<>(MessageEnum.WITHDRAW_NOT_EXISTS.getMessage(), MessageEnum.WITHDRAW_NOT_EXISTS.getState());
        User userInfo = userService.userInfoById(userId);
        WithdrawMoneyPageRequest request = new WithdrawMoneyPageRequest();
        request.setWdMerNo(userInfo.getPaymentAccount());
        request.setMerMerOrderNo(withdraw.getOrderNumber());
        request.setPtUndertakeRate("");
        request.setPtWithholdFeeRoutingList("");
        PaymentRequest paymentRequest = paymentService.withdrawMoneyPage(request);
        if (null != paymentRequest) {
            PaymentRequestForHtml paymentRequestForHtml = new PaymentRequestForHtml();
            paymentRequestForHtml.setMerNo(paymentRequest.getMerNo());
            paymentRequestForHtml.setVersion(paymentRequest.getVersion());
            paymentRequestForHtml.setNotifyUrl(paymentRequest.getNotifyUrl());
            paymentRequestForHtml.setTimestamp(paymentRequest.getTimestamp());
            paymentRequestForHtml.setApiContent(JSON.toJSONString(paymentRequest.getApiContent()));
            paymentRequestForHtml.setSignType(paymentRequest.getSignType());
            paymentRequestForHtml.setSign(paymentRequest.getSign());
            paymentRequestForHtml.setActionUrl(PaymentRequestUrlEnum.WITHDRAW_MONEY_TO_ACCOUNT_PAGE.getUrl());
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                    paymentRequestForHtml);
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 利润统计
     */
    @ApiOperation("利润统计")
    @GetMapping(value = "/profit")
    public ResponseData<Profit> profitStatistics(@RequestParam("type") Integer type, @LoginUserId Integer userId) {
        if (type.equals(1)) {
            MonthProfit monthProfit = new MonthProfit();
            Date firstDayOfMonth = DateUtil.INSTANCE.firstDayOfMonth();
            Date lastDayOfMonth = DateUtil.INSTANCE.todayEnd(DateUtil.INSTANCE.lastDayOfMonth());
            Date firstDayOfNextMonth = DateUtil.INSTANCE.fistDayOfNextMonth();
            Date lastDayOfNextMonth = DateUtil.INSTANCE.todayEnd(DateUtil.INSTANCE.lastDayOfNextMonth());

            //本月利润
            monthProfit.setProfit(reapService.reapProfitStatistics(userId, firstDayOfMonth, lastDayOfMonth, null));

            //本月已完成利润
            monthProfit.setPlantedProfit(reapService.reapProfitStatistics(userId, firstDayOfMonth, lastDayOfMonth, Arrays.asList(ReapEnum.SALE_DONE, ReapEnum.REAP_DONE)));

            //本月种植中利润
            monthProfit.setPlantingProfit(reapService.reapProfitStatistics(userId, firstDayOfMonth, lastDayOfMonth, Collections.singletonList(ReapEnum.WAITING_REAP)));

            //下月种植中利润
            BigDecimal nextMonth = reapService.reapProfitStatistics(userId, firstDayOfNextMonth, lastDayOfNextMonth, Collections.singletonList(ReapEnum.WAITING_REAP));
            if (nextMonth == null)
                monthProfit.setNextMonthPlantingProfit(new BigDecimal(0));
            else
                monthProfit.setNextMonthPlantingProfit(nextMonth);
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), monthProfit);
        } else {
            TotalProfit totalProfit = new TotalProfit();
            totalProfit.setTotalProfit(reapService.reapProfitStatistics(userId, null, null, null));
            totalProfit.setPlantedProfit(reapService.plantProfitStatistics(userId, null, null, ReapEnum.SALE_DONE));
            totalProfit.setPlantingProfit(reapService.plantProfitStatistics(userId, null, null, ReapEnum.WAITING_REAP));
            //累计种植份数
            totalProfit.setTotalPlanted(reapService.reapStatistics(userId, null, true));
            //已采摘份数
            totalProfit.setReapCount(reapService.reapStatistics(userId, ReapEnum.REAP_DONE, false));
            //种植中份数
            totalProfit.setPlantedCount(reapService.reapStatistics(userId, ReapEnum.WAITING_REAP, true));
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), totalProfit);
        }

    }

    /**
     * 用户资金明细
     */
    @PostMapping("/capital/detail")
    @ApiOperation("用户资金明细")
    public PageResult<?> capitalDetail(@LoginUserId Integer userId, @RequestBody PageSearch<String> pageSearch) {

        // 请求参数
        Integer page = pageSearch.getPage();
        Integer size = pageSearch.getSize();
        String dateTime = pageSearch.getWhere() + "-00";

        // 收入/支出的类型
        List<Integer> list = Arrays.asList(
                MoneyOpEnum.WITHDRAW_DONE.getType(),
                MoneyOpEnum.INVITE_AWARD.getType(),
                MoneyOpEnum.BUY_MALL_PRODUCT.getType(),
                MoneyOpEnum.BUY_SEED_ORDER.getType(),
                MoneyOpEnum.SALE_REAP_SEED.getType(),
                MoneyOpEnum.PANT_SEED_FERTILIZER.getType(),
                MoneyOpEnum.SALE_REAP_SEED_PROFIT.getType(),
                MoneyOpEnum.RED_PACKAGE.getType());

        // 获取数据
        PageResult<UserMoneyDetail> userMoney = userMoneyService.findUserMoney(page, size, userId, dateTime, list);
        List<UserMoneyDetail> userMoneyDetails = userMoney.getData();
        if (userMoneyDetails.isEmpty())
            return userMoney;

        List<UserMoneyDate> userMoneyDates = Collections.synchronizedList(new ArrayList<>());
        for (UserMoneyDetail userMoneyDetail : userMoneyDetails) {
            if (Objects.isNull(userMoneyDetail))
                continue;

            String time = userMoneyDetail.getDateTime();
            if (userMoneyDates.isEmpty()) {

                List<UserMoneyDetail> userMoneyDateList = Collections.synchronizedList(new ArrayList<>());
                userMoneyDateList.add(userMoneyDetail);
                userMoneyDates.add(new UserMoneyDate(time, userMoneyDateList));

            } else {

                // 已有某日数据则添加到某日集合中
                boolean flag = true;
                for (UserMoneyDate userMoneyDate : userMoneyDates) {
                    String day = userMoneyDate.getDay();
                    if (day != null && day.equals(time)) {
                        List<UserMoneyDetail> userMonies = userMoneyDate.getUserMonies();
                        userMonies.add(userMoneyDetail);
                        flag = false;
                    }
                }

                // 某日数据没有则新增
                if (flag) {
                    List<UserMoneyDetail> userMoneyDateList = Collections.synchronizedList(new ArrayList<>());
                    userMoneyDateList.add(userMoneyDetail);
                    userMoneyDates.add(new UserMoneyDate(time, userMoneyDateList));
                }
            }
        }

        return new PageResult(userMoney.getStatus(), userMoney.getMessage(), userMoney.getCount(), userMoneyDates);
    }

    /**
     * 资金详情
     */
    @GetMapping("/capital/info/{id}")
    @ApiOperation("资金详情")
    public ResponseData<DetailInfo> capitalInfo(@PathVariable("id") Integer id) {

        UserMoneyDetail userMoney = userMoneyService.findUserMoneyById(id);
        if (Objects.isNull(userMoney))
            new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

        // 获取数据
        String transactionMoneyStr = Optional.ofNullable(userMoney.getAffectMoney()).orElse("0");
        BigDecimal transactionMoney = new BigDecimal(transactionMoneyStr);
        Date transactionTime = Optional.ofNullable(userMoney.getAffectTime()).orElse(new Date());
        String transactionType = Optional.ofNullable(userMoney.getAffectType()).orElse("");

        // 封装数据
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setTransactionMoney(transactionMoney);
        detailInfo.setTransactionTime(transactionTime);
        detailInfo.setTransactionType(transactionType);
        detailInfo.setTransactionMode("余额");

        // 获取商品详情
        String detailId = Optional.ofNullable(userMoney.getDetailId()).orElse("");
        List<ProductInfo> products = userMoneyService.findProducts(transactionType, detailId);
        detailInfo.setProductInfoList(products);

        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), detailInfo);
    }

    /**
     * 收入/支出
     */
    @GetMapping("/getTotal")
    @ApiOperation("收入/支出")
    public ResponseData<InAndOutMoney> getTotal(
            @LoginUserId Integer userId,
            @RequestParam("dateTime") String dateTime) {

        if (null == userId || null == dateTime)
            new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

        dateTime += "-00";

        // 保留2位小数
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // 收入类型
        List<Integer> inList = Arrays.asList(
                MoneyOpEnum.INVITE_AWARD.getType(),
                MoneyOpEnum.SALE_REAP_SEED.getType(),
                MoneyOpEnum.PANT_SEED_FERTILIZER.getType(),
                MoneyOpEnum.SALE_REAP_SEED_PROFIT.getType(),
                MoneyOpEnum.RED_PACKAGE.getType());
        BigDecimal in = userMoneyService.getTotal(userId, dateTime, inList);
        String inCome = decimalFormat.format(in);

        // 支出类型
        List<Integer> outList = Arrays.asList(
                MoneyOpEnum.WITHDRAW_DONE.getType(),
                MoneyOpEnum.BUY_MALL_PRODUCT.getType(),
                MoneyOpEnum.BUY_SEED_ORDER.getType());
        BigDecimal out = userMoneyService.getTotal(userId, dateTime, outList);
        String outPay = decimalFormat.format(out);

        InAndOutMoney money = new InAndOutMoney("+" + inCome, "-" + outPay);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), money);
    }

}
