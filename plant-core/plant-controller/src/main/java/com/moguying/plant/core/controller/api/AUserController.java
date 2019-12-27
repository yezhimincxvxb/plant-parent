package com.moguying.plant.core.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.moguying.plant.constant.*;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.ValidateUser;
import com.moguying.plant.core.dao.user.UserActivityLogDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.vo.BlockDetail;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerDot;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerSearch;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.payment.request.*;
import com.moguying.plant.core.entity.payment.response.*;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.reap.ReapWeigh;
import com.moguying.plant.core.entity.reap.vo.ReapSearch;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.CanPlantOrder;
import com.moguying.plant.core.entity.system.PhoneMessage;
import com.moguying.plant.core.entity.user.*;
import com.moguying.plant.core.entity.user.vo.*;
import com.moguying.plant.core.service.farmer.FarmerService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.reap.ReapWeighService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.core.service.seed.SeedOrderService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import com.moguying.plant.core.service.user.UserInviteService;
import com.moguying.plant.core.service.user.UserMessageService;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户中心")
public class AUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneMessageService messageService;

    @Autowired
    private SeedOrderService seedOrderService;

    @Autowired
    private ReapService reapService;

    @Autowired
    private SeedOrderDetailService seedOrderDetailService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserInviteService userInviteService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserFertilizerService userFertilizerService;

    @Autowired
    private ReapWeighService reapWeighService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private StringRedisTemplate redisUtil;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private UserActivityLogDAO userActivityLogDAO;


    @Value("${user.invite.bg.image}")
    private String inviteBgImagePath;

    @Value("${user.invite.icon}")
    private String inviteIcon;

    @Value("${user.invite.url}")
    private String inviteUrl;

    @Value("${wx.appid}")
    private String wxAppid;

    @Value("${wx.app.secret}")
    private String wxAppSecret;


    private static Map<Integer, String> realNameStateMap = new HashMap<>();

    static {
        realNameStateMap.put(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_ERROR.getState(), UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_ERROR.getStateName());
        realNameStateMap.put(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getState(), UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getStateName());
        realNameStateMap.put(UserEnum.USER_PAYMENT_ACCOUNT_NEED_VERIFY.getState(), UserEnum.USER_PAYMENT_ACCOUNT_NEED_VERIFY.getStateName());
        realNameStateMap.put(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_ING.getState(), UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_ING.getStateName());
    }

    /**
     * 用户中心首页信息
     */
    @GetMapping
    @ApiOperation("用户中心首页信息")
    public ResponseData<UserSummaryInfo> index(@LoginUserId Integer userId) {
        User user = userService.userInfoById(userId);
        if (null == user)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        // 用户首页摘要信息
        UserSummaryInfo summaryInfo = userService.userSummaryInfo(user);
        if (summaryInfo != null)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), summaryInfo);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }

    /**
     * PC端用户个人中心首页信息
     */
    @GetMapping("/info")
    @ApiOperation("PC端用户个人中心首页信息")
    public ResponseData<User> userInfo(@LoginUserId Integer userId) {
        User user = userService.userInfoById(userId);
        if (null == user)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        // 隐藏id
        user.setId(null);
        user.setPhone(user.getPhone().substring(0, 3).concat("***").concat(user.getPhone().substring(7, 11)));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), user);
    }


    /**
     * 设置/修改支付密码
     */
    @PutMapping(value = "/pay/password")
    @ApiOperation("设置/修改支付密码")
    public ResponseData<Integer> setPayPassword(@RequestBody PayPassword payPassword, @LoginUserId Integer userId) {
        User userInfo = userService.userInfoById(userId);
        if (null == userInfo)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        // 短信验证码是否失效(一分钟有效)
        PhoneMessage message = messageService.messageByPhone(userInfo.getPhone());
        if (message == null || !message.getCode().equals(payPassword.getCode()))
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_ERROR.getMessage(), MessageEnum.MESSAGE_CODE_ERROR.getState());
        messageService.setMessageState(message.getId(), SystemEnum.PHONE_MESSAGE_VALIDATE);
        // 首次设置密码
        if (StringUtils.isBlank(userInfo.getPayPassword()) && StringUtils.isNotBlank(payPassword.getOldPayPassword()))
            return new ResponseData<>(MessageEnum.NOT_NEED_OLD_PAY_PASSWORD.getMessage(), MessageEnum.NOT_NEED_OLD_PAY_PASSWORD.getState());
        // 旧密码错误
        if (StringUtils.isNotBlank(userInfo.getPayPassword())) {
            if (StringUtils.isBlank(payPassword.getOldPayPassword()))
                return new ResponseData<>(MessageEnum.PAY_PASSWORD_IS_EMPTY.getMessage(), MessageEnum.PAY_PASSWORD_IS_EMPTY.getState());
            String newPayPassword = PasswordUtil.INSTANCE.encode(payPassword.getOldPayPassword().getBytes());
            if (!newPayPassword.equals(userInfo.getPayPassword()))
                return new ResponseData<>(MessageEnum.OLD_PAY_PASSWORD_ERROR.getMessage(), MessageEnum.OLD_PAY_PASSWORD_ERROR.getState());
        }
        User update = new User();
        update.setPayPassword(payPassword.getPayPassword());
        ResultData<User> resultData = userService.saveUserInfo(userId, update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData().getId());
    }


    /**
     * 忘记支付密码
     */
    @PutMapping("/forget/password")
    @ApiOperation("忘记支付密码")
    public ResponseData<Integer> forgetPassword(@LoginUserId Integer userId, @RequestBody ForgetPayPassword forgetPayPassword) {
        User user = userService.userInfoById(userId);
        if (null == user)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        if (!user.getIdCard().equals(forgetPayPassword.getIdCard()) || !user.getRealName().equals(forgetPayPassword.getRealName()))
            return new ResponseData<>(MessageEnum.USER_INFO_ERROR.getMessage(), MessageEnum.USER_INFO_ERROR.getState());
        User update = new User();
        update.setPayPassword(forgetPayPassword.getPayPassword());
        ResultData<User> resultData = userService.saveUserInfo(userId, update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 设置/修改提现密码
     * 跳转第三方支付网页设置
     */
    @GetMapping("/withdraw/password")
    @ApiOperation("设置/修改提现密码")
    @SuppressWarnings("all")
    public ResponseData<PaymentRequestForHtml> setWithdrawPassword(@LoginUserId Integer userId) {
        User userInfo = userService.userInfoById(userId);
        if (!userInfo.getIsRealName().equals(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getState()))
            return new ResponseData<>(MessageEnum.USER_PAYMENT_REGISTER_INFO_ERROR.getMessage(),
                    MessageEnum.USER_PAYMENT_REGISTER_INFO_ERROR.getState());
        if (!userInfo.getPaymentState().equals(UserEnum.USER_PAYMENT_ACCOUNT_REGISTER.getState())
                || StringUtils.isEmpty(userInfo.getPaymentAccount())) {
            return new ResponseData<>(MessageEnum.USER_NEED_REGISTER_PAYMENT_ACCOUNT.getMessage(),
                    MessageEnum.USER_NEED_REGISTER_PAYMENT_ACCOUNT.getState());
        }
        ModifyPayPasswordRequest modifyPayPasswordRequest = new ModifyPayPasswordRequest();
        modifyPayPasswordRequest.setMerchantNo(userInfo.getPaymentAccount());
        modifyPayPasswordRequest.setType("H5");
        PaymentRequest paymentRequest = paymentService.modifyPayPassword(modifyPayPasswordRequest);
        if (null != paymentRequest) {
            PaymentRequestForHtml paymentRequestForHtml = new PaymentRequestForHtml();
            paymentRequestForHtml.setMerNo(paymentRequest.getMerNo());
            paymentRequestForHtml.setVersion(paymentRequest.getVersion());
            paymentRequestForHtml.setNotifyUrl(paymentRequest.getNotifyUrl());
            paymentRequestForHtml.setTimestamp(paymentRequest.getTimestamp());
            paymentRequestForHtml.setApiContent(JSON.toJSONString(paymentRequest.getApiContent()));
            paymentRequestForHtml.setSignType(paymentRequest.getSignType());
            paymentRequestForHtml.setSign(paymentRequest.getSign());
            paymentRequestForHtml.setActionUrl(PaymentRequestUrlEnum.MODIFY_PAY_PASSWORD.getUrl());
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                    paymentRequestForHtml);
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

    }


    /**
     * 修改登录密码
     *
     * @return
     */
    @PutMapping("/password")
    @ApiOperation("修改登录密码")
    public ResponseData<Integer> modifyPassword(@RequestBody LoginPassword loginPassword, @LoginUserId Integer userId) {
        User user = userService.userInfoById(userId);
        if (null == user)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        User userInfo = userService.loginByPhoneAndPassword(user.getPhone(), loginPassword.getOldPassword());
        if (null == userInfo)
            return new ResponseData<>(MessageEnum.OLD_PASSWORD_ERROR.getMessage(), MessageEnum.OLD_PASSWORD_ERROR.getState());
        if (loginPassword.getPassword().equals(loginPassword.getOldPassword()))
            return new ResponseData<>(MessageEnum.NEW_PASSWORD_EQUAL_OLD.getMessage(), MessageEnum.NEW_PASSWORD_EQUAL_OLD.getState());
        if (messageService.validateMessage(userInfo.getPhone(), loginPassword.getCode()) <= 0)
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_ERROR.getMessage(), MessageEnum.MESSAGE_CODE_ERROR.getState());
        User update = new User();
        update.setPassword(loginPassword.getPassword());
        ResultData<User> resultData = userService.saveUserInfo(user.getId(), update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData().getId());
    }


    /**
     * 用户实名信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/real/name")
    @ApiOperation("用户实名信息")
    public ResponseData<RealName> realName(@LoginUserId Integer userId) {
        User user = userService.userInfoById(userId);
        if (null == user)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        RealName realName = new RealName();
        realName.setName(user.getRealName());
        realName.setIdCard(user.getIdCard());
        realName.setState(user.getIsRealName());
        realName.setStateStr(realNameStateMap.get(user.getIsRealName()));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), realName);
    }


    /**
     * 信息注册
     *
     * @param realName
     * @param userId
     * @return
     */
    @PostMapping(value = "/real/name")
    @ApiOperation("信息注册")
    public ResponseData<Integer> validateRealName(@RequestBody RealName realName, @LoginUserId Integer userId) {
        User userInfo = userService.userInfoById(userId);
        if (userInfo == null)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());

        if (!userInfo.getIsRealName().equals(UserEnum.USER_PAYMENT_ACCOUNT_NEED_VERIFY.getState()))
            return new ResponseData<>(MessageEnum.USER_HAS_REAL_NAME.getMessage(), MessageEnum.USER_HAS_REAL_NAME.getState());

        if (null == realName.getName())
            return new ResponseData<>(MessageEnum.REAL_NAME_EMPTY.getMessage(), MessageEnum.REAL_NAME_EMPTY.getState());


        if (!CommonUtil.INSTANCE.isIDNumber(realName.getIdCard()))
            return new ResponseData<>(MessageEnum.IDCARD_ERROR.getMessage(), MessageEnum.IDCARD_ERROR.getState());


        //年龄超60不支持实名
        int bYear = Integer.parseInt(realName.getIdCard().substring(4, 10));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        if (year - bYear >= 60)
            return new ResponseData<>(MessageEnum.USER_OUT_OF_AGE.getMessage(), MessageEnum.USER_OUT_OF_AGE.getState());

        //发送注册信息到第三方支付
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCustomerNo(userInfo.getInviteCode());
        registerRequest.setCustName(realName.getName());
        registerRequest.setPhone(userInfo.getPhone());
        registerRequest.setIdNo(realName.getIdCard());
        PaymentResponse<RegisterSyncResponse> registerResponse = paymentService.userRegister(registerRequest);
        if (null != registerResponse && registerResponse.getCode().equals(PaymentStateEnum.RESPONSE_REGISTER_SUCCESS.getStateInfo())) {
            User user = userService.userInfoByInviteCodeAndId(userId, registerResponse.getData().getCustomerNo());
            if (null == user)
                return new ResponseData<>(MessageEnum.USER_PAYMENT_REGISTER_INFO_ERROR.getMessage(),
                        MessageEnum.USER_PAYMENT_REGISTER_INFO_ERROR.getState());
            User addPayment = new User();
            addPayment.setRealName(realName.getName());
            addPayment.setIdCard(realName.getIdCard());
            addPayment.setIsRealName(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getState());
            addPayment.setPaymentAccount(registerResponse.getData().getMerchantNo());
            addPayment.setPaymentState(UserEnum.USER_PAYMENT_ACCOUNT_REGISTER.getState());
            ResultData<User> resultData = userService.saveUserInfo(userId, addPayment);
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        } else if (null != registerResponse) {
            return new ResponseData<>(registerResponse.getMsg(), Integer.parseInt(registerResponse.getCode()));
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

    }


    /**
     * 银行卡信息
     *
     * @param userId
     * @return
     */
    @ValidateUser
    @GetMapping("/card")
    @ApiOperation("银行卡信息")
    public ResponseData<List<UserBank>> cardInfo(@LoginUserId Integer userId) {
        User userInfo = userService.userInfoById(userId);
        if (userInfo == null)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());

        List<UserBank> userBanks = userService.bankCardList(userId);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), userBanks);
    }


    /**
     * 绑定银行卡
     *
     * @param bindCard
     * @return
     */
    @PutMapping(value = "/bind/card")
    @ApiOperation("绑定银行卡")
    public ResponseData<Integer> bindCard(@RequestBody BindCard bindCard, @LoginUserId Integer userId) {
        User user = userService.userInfoById(userId);
        if (user == null)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        if (null == bindCard.getBankNumber() || StringUtils.isEmpty(bindCard.getBankNumber()))
            return new ResponseData<>(MessageEnum.BANK_NUMBER_IS_EMPTY.getMessage(), MessageEnum.BANK_NUMBER_IS_EMPTY.getState());
        if (null == bindCard.getMsgCode() || StringUtils.isEmpty(bindCard.getMsgCode()))
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_IS_EMPTY.getMessage(), MessageEnum.MESSAGE_CODE_IS_EMPTY.getState());
        if (null == bindCard.getPhone() || StringUtils.isEmpty(bindCard.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_IS_EMPTY.getMessage(), MessageEnum.PHONE_IS_EMPTY.getState());
        if (!CommonUtil.INSTANCE.isPhone(bindCard.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(), MessageEnum.PHONE_ERROR.getState());
        bindCard.setBankNumber(bindCard.getBankNumber().replaceAll("\\s", ""));
        UserBank userBank = new UserBank();
        userBank.setUserId(user.getId());
        userBank.setAddTime(new Date());
        userBank.setOrderNumber(OrderPrefixEnum.PAYMENT_BIND_CARD_ORDER.getPreFix() + bindCard.getSeqNo());
        userBank.setBankNumber(bindCard.getBankNumber());
        userBank.setBankPhone(bindCard.getPhone());
        userBank.setState(UserEnum.BANK_NOT_USED.getState());


        //查询银行卡信息
        QueryBankCardBinRequest queryBankCardBinRequest = new QueryBankCardBinRequest();
        queryBankCardBinRequest.setCardNo(bindCard.getBankNumber());
        PaymentResponse<QueryBankCardBinResponse> queryResponse = paymentService.queryBankCardBin(queryBankCardBinRequest);

        if (null != queryResponse && queryResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
            userBank.setBankId(queryResponse.getData().getBankCode());
            userBank.setCardType(queryResponse.getData().getCardType());
            userBank.setBankAddress(queryResponse.getData().getBankName());
        } else {
            if (null != queryResponse) {
                return new ResponseData<>(queryResponse.getMsg(), MessageEnum.ERROR.getState());
            }
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }

        BindCardRequest bindCardRequest = new BindCardRequest();
        bindCardRequest.setBkMerNo(user.getPaymentAccount());
        bindCardRequest.setCardNo(bindCard.getBankNumber());
        bindCardRequest.setSeqNo(bindCard.getSeqNo());
        bindCardRequest.setSmsCode(bindCard.getMsgCode());
        PaymentResponse<BindCardResponse> bindCardResponse = paymentService.bindCard(bindCardRequest);
        if (null != bindCardResponse && bindCardResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
            userBank.setState(UserEnum.BANK_IN_USED.getState());
            ResultData<Integer> resultData = userService.saveBankCard(userBank);
            if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
                return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
            }
        } else if (null != bindCardResponse)
            return new ResponseData<>(bindCardResponse.getMsg(), MessageEnum.ERROR.getState());

        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

    }

    /**
     * 删除绑定银行卡
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/card/{id}")
    @ApiOperation("删除绑定银行卡")
    public ResponseData<String> deleteBindCard(@LoginUserId Integer userId, @PathVariable Integer id) {
        UserBank bank = userService.bankCard(userId, id);
        if (null == bank)
            return new ResponseData<>(MessageEnum.USER_BANK_CARD_NOT_EXISTS.getMessage(), MessageEnum.USER_BANK_CARD_NOT_EXISTS.getState());
        DeleteBankCardRequest deleteBankCardRequest = new DeleteBankCardRequest();
        deleteBankCardRequest.setBkMerNo(bank.getPaymentAccount());
        deleteBankCardRequest.setCardNo(bank.getBankNumber());
        deleteBankCardRequest.setSeqNo(bank.getOrderNumber());
        PaymentResponse<DeleteBankCardResponse> paymentResponse = paymentService.deleteCard(deleteBankCardRequest);
        if (null != paymentResponse && paymentResponse.getCode().equals(PaymentStateEnum.RESPONSE_DELETE_SUCCESS.getStateInfo())) {
            ResultData<Integer> resultData = userService.deleteCard(id);
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        } else if (null != paymentResponse) {
            return new ResponseData<>(paymentResponse.getMsg(), MessageEnum.ERROR.getState());
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }

    /**
     * 绑定银行卡时发送短信
     *
     * @param bindCard
     * @param userId
     * @return
     */
    @PostMapping(value = "/send/bind/sms")
    @ApiOperation("绑定银行卡时发送短信")
    public ResponseData<SendSmsCodeResponse> sendBindCardSms(@RequestBody BindCard bindCard, @LoginUserId Integer userId) {

        User userInfo = userService.userInfoById(userId);
        if (userInfo == null)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        if (null == bindCard.getBankNumber() || StringUtils.isEmpty(bindCard.getBankNumber()))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        if (!userInfo.getIsRealName().equals(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getState()))
            return new ResponseData<>(MessageEnum.USER_NEED_REAL_NAME.getMessage(), MessageEnum.USER_NEED_REAL_NAME.getState());
        if (!CommonUtil.INSTANCE.isPhone(bindCard.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(), MessageEnum.PHONE_ERROR.getState());
        SendBindCardSmsCodeRequest sbcsc = new SendBindCardSmsCodeRequest();
        sbcsc.setBkMerNo(userInfo.getPaymentAccount());
        sbcsc.setCardNo(bindCard.getBankNumber());
        sbcsc.setPhone(bindCard.getPhone());
        sbcsc.setIdNo(userInfo.getIdCard());
        sbcsc.setCustName(userInfo.getRealName());
        PaymentResponse<SendSmsCodeResponse> paymentResponse = paymentService.sendBindCardSmsCode(sbcsc);
        if (null != paymentResponse && paymentResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), paymentResponse.getData());
        } else if (null != paymentResponse) {
            return new ResponseData<>(paymentResponse.getMsg(), MessageEnum.ERROR.getState());
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 用户默认地址信息
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/address/default")
    @ApiOperation("用户默认地址信息")
    public ResponseData<UserAddress> defaultAddress(@LoginUserId Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                userService.userDefaultAddress(userId));
    }


    /**
     * 地址列表
     *
     * @return
     */
    @GetMapping(value = "/address")
    @ApiOperation("地址列表")
    public ResponseData<List<UserAddress>> address(@LoginUserId Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                userService.addressList(userId));
    }


    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    @PostMapping(value = "/address")
    @ApiOperation("添加地址")
    public ResponseData<Integer> addAddress(@RequestBody UserAddress address, @LoginUserId Integer userId) {
        address.setUserId(userId);
        ResultData<Integer> resultData = userService.addAddress(address);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 编辑地址
     *
     * @param address
     * @return
     */
    @PutMapping(value = "/address/{id}")
    @ApiOperation("编辑地址")
    public ResponseData<Integer> updateAddress(@RequestBody UserAddress address, @PathVariable Integer id, @LoginUserId Integer userId) {
        if (id == null)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        address.setUserId(userId);
        ResultData<Integer> resultData = userService.updateAddress(id, address);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 删除地址
     *
     * @param id
     * @param userId
     * @return
     */
    @DeleteMapping("/address/{id}")
    @ApiOperation("删除地址")
    public ResponseData<Integer> deleteAddress(@PathVariable Integer id, @LoginUserId Integer userId) {
        if (id == null)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        UserAddress address = new UserAddress();
        address.setId(id);
        address.setUserId(userId);
        ResultData<Integer> resultData = userService.deleteAddress(address);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 获取单个地址详情
     *
     * @param id
     * @param userId
     * @return
     */
    @GetMapping("/address/{id}")
    @ApiOperation("获取单个地址详情")
    public ResponseData<UserAddress> addressDetail(@PathVariable Integer id, @LoginUserId Integer userId) {
        if (id == null)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        UserAddress address = userService.userAddressByIdAndUserId(id, userId, false);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), address);
    }


    /**
     * 菌包列表
     *
     * @return
     */
    @GetMapping(value = "/seed")
    @ApiOperation("菌包列表")
    public ResponseData<List<UserSeedOrder>> seedList(@LoginUserId Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                seedOrderService.userSeedOrder(userId));
    }


    /**
     * 个人在指定棚区可种植的菌包数量
     *
     * @param blockDetail
     * @param userId
     * @return
     */
    @PostMapping("/seed")
    @ApiOperation("个人在指定棚区可种植的菌包数量")
    public ResponseData<CanPlantOrder> seedInBlock(@RequestBody BlockDetail blockDetail, @LoginUserId Integer userId) {
        CanPlantOrder canPlantOrder = seedOrderService.sumUserSeedByBlockId(blockDetail.getBlockId(), userId);
        if (null == canPlantOrder) {
            canPlantOrder = new CanPlantOrder(0, 0);
        }
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), canPlantOrder);
    }


    /**
     * 获取成品列表
     *
     * @return
     */
    @PostMapping(value = "/product")
    @ApiOperation("获取成品列表")
    public PageResult<Reap> productList(@LoginUserId Integer userId, @RequestBody PageSearch<Reap> search) {
        Reap where = new Reap();
        where.setUserId(userId);
        where.setStates(Arrays.asList(ReapEnum.REAP_DONE.getState(), ReapEnum.SALE_DONE.getState(), ReapEnum.EXCHANGE_THING.getState()));
        if (search.getWhere() != null && null != search.getWhere().getGroupId())
            where.setGroupId(search.getWhere().getGroupId());

        return reapService.userReapList(search.getPage(), search.getSize(), where);
    }


    /**
     * 用户个人种植产量信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/product/weigh")
    @ApiOperation("用户个人种植产量信息")
    public ResponseData<ReapWeigh> userReapWeigh(@LoginUserId Integer userId) {
        ResultData<ReapWeigh> resultData = reapWeighService.userReapWeighInfo(userId);
        ResponseData<ReapWeigh> responseData = new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            responseData.setData(resultData.getData());
        return responseData;
    }


    /**
     * 菌包管理-已购买
     */
    @PostMapping("/order")
    @ApiOperation("菌包管理-已购买")
    public PageResult<SeedOrderDetail> seedOrderList(@RequestBody PageSearch search, @LoginUserId Integer userId) {
        return seedOrderDetailService.userSeedOrderList(search.getPage(), search.getSize(), userId, SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState());
    }


    /**
     * 菌包购买协议
     *
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/order/{orderId}/protocol")
    @ApiOperation("菌包购买协议")
    public ResponseData<String> seedOrderProtocol(@LoginUserId Integer userId, @PathVariable Integer orderId) {
        User userInfo = userService.userInfoById(userId);
        SeedOrderDetail seedOrderDetail = seedOrderDetailService.orderDetailByIdAndUserId(orderId, userId);
        if (null == userInfo || null == seedOrderDetail) {
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        map.put("orderDetail", seedOrderDetail);

        Context context = new Context();
        context.setVariables(map);
        String html = templateEngine.process("buy_contract", context);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), HtmlUtils.htmlUnescape(html));

    }


    /**
     * 菌包管理-已种植-已采摘-已出售
     *
     * @return
     */
    @PostMapping(value = "/reap")
    @ApiOperation("菌包管理-已种植-已采摘-已出售")
    public PageResult<Reap> reapList(@LoginUserId Integer userId, @RequestBody ReapSearch search) {
        Reap where = new Reap();
        where.setUserId(userId);
        where.setState(search.getType());
        return reapService.reapList(search.getPage(), search.getSize(), where);
    }


    /**
     * 菌包种植协议
     *
     * @return
     */
    @GetMapping("/reap/{reapId}/protocol")
    @ApiOperation("菌包种植协议")
    public ResponseData<String> reapProtocol(@LoginUserId Integer userId, @PathVariable Integer reapId, Model model) {
        User userInfo = userService.userInfoById(userId);
        Reap reapInfo = reapService.reapInfoByIdAndUserId(reapId, userId);
        if (null == userInfo || null == reapInfo)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("reapInfo", reapInfo);
        model.addAttribute("saleAmount", reapInfo.getPreAmount().add(reapInfo.getPreProfit()));
        model.addAttribute("CNNumber", NumberToCN.INSTANCE.number2CNMontrayUnit(reapInfo.getPreAmount()));

        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        map.put("reapInfo", reapInfo);
        map.put("saleAmount", reapInfo.getPreAmount().add(reapInfo.getPreProfit()));
        map.put("CNNumber", NumberToCN.INSTANCE.number2CNMontrayUnit(reapInfo.getPreAmount()));

        Context context = new Context();
        context.setVariables(map);
        String html = templateEngine.process("plant_contract", context);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), HtmlUtils.htmlUnescape(html));
    }


    /**
     * 菌包订单
     */
    @PostMapping(value = "/order/unpay")
    @ApiOperation("菌包订单")
    public PageResult<SeedOrderDetail> seedOrderDetailList(@RequestBody PageSearch search, @LoginUserId Integer userId) {
        return seedOrderDetailService.selectUserPayListByUserId(search.getPage(), search.getSize(), userId);
    }


    /**
     * 菌包订单详情
     *
     * @param id
     * @param userId
     * @return
     */
    @GetMapping(value = "/order/{id}")
    @ApiOperation("菌包订单详情")
    public ResponseData<SeedOrderDetail> seedOrderDetail(@PathVariable("id") Integer id, @LoginUserId Integer userId) {
        SeedOrderDetail detail = seedOrderDetailService.orderDetailByIdAndUserId(id, userId);
        if (null == detail)
            return new ResponseData<>(MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getMessage(), MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getState());
        detail.setRealPayAmount(detail.getBuyAmount().subtract(detail.getReducePayAmount()));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), detail);
    }


    /**
     * 站内信息列表
     */
    @PostMapping("/message")
    @ApiOperation("站内信息列表")
    public PageResult<UserMessage> messageList(@RequestBody PageSearch search, @LoginUserId Integer userId) {
        return userService.userMessageList(search.getPage(), search.getSize(), userId);
    }


    /**
     * 读信息
     *
     * @return
     */
    @PutMapping(value = "/message/read")
    @ApiOperation("读信息")
    public ResponseData<Boolean> readMessage(@LoginUserId Integer userId) {
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(userId);
        userMessage.setIsRead(true);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                userMessageService.setMessageState(userMessage));
    }

    /**
     * 删除信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/message/delete/{id}")
    @ApiOperation("删除信息")
    public ResponseData<Boolean> deletedMessage(@PathVariable Integer id, @LoginUserId Integer userId) {
        UserMessage userMessage = new UserMessage();
        userMessage.setId(id);
        userMessage.setUserId(userId);
        userMessage.setIsDelete(true);
        if (userMessageService.setMessageState(userMessage))
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 邀请信息统计
     *
     * @return
     */
    @GetMapping(value = "/invite")
    @ApiOperation("邀请信息统计")
    public ResponseData<InviteStatistics> inviteStatistics(@LoginUserId Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                userInviteService.inviteStatistics(userId));
    }


    /**
     * 被邀请人列表
     */
    @PostMapping("/invite/list")
    @ApiOperation("被邀请人列表")
    public PageResult<UserInvite> inviteList(@LoginUserId Integer userId, @RequestBody PageSearch<UserInvite> search) {
        return userInviteService.inviteList(search.getPage(), search.getSize(), userId);
    }


    /**
     * 用户红包列表
     *
     * @param userId
     * @return
     */
    @PostMapping("/fertilizer")
    @ApiOperation("用户红包列表")
    public PageResult<UserFertilizerInfo> userFertilizers(@LoginUserId Integer userId, @RequestBody FertilizerSearch search) {
        return userFertilizerService.userFertilizers(search.getPage(), search.getSize(), userId, search);
    }

    /**
     * 领取红包
     */
    @GetMapping("/redPackage/{id}")
    @ApiOperation("领取红包")
    public ResponseData<String> redPackage(@LoginUserId Integer userId, @PathVariable("id") Integer id) {
        ResponseData<String> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), "领取失败");

        // 参数错误
        if (userId == null || id == null) return responseData;

        // 该券是否可以用
        UserFertilizer userFertilizer = userFertilizerService.getUserFertilizer(userId, id, 4);
        if (userFertilizer == null) return responseData.setData("券无效");

        Boolean success = userFertilizerService.redPackageSuccess(userFertilizer);
        if (success)
            return responseData.setMessage(MessageEnum.SUCCESS.getMessage())
                    .setState(MessageEnum.SUCCESS.getState())
                    .setData("领取成功");

        return responseData;
    }

    /**
     * 在使用时可用的券
     */
    @PostMapping("/valid/fertilizer")
    @ApiOperation("在使用时可用的券")
    public ResponseData<List<UserFertilizerInfo>> fertilizers(@LoginUserId Integer userId, @RequestBody FertilizerUseCondition condition) {
        condition.setUserId(userId);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                userFertilizerService.canUseFertilizers(condition));
    }


    /**
     * 用户邀请个人专属海报
     *
     * @param userId
     */
    @GetMapping("/invite/poster")
    @ApiOperation("用户邀请个人专属海报")
    public ResponseData<UserPoster> invitePosterImage(@LoginUserId Integer userId, HttpServletResponse response) {
        ClassPathResource resource = new ClassPathResource(inviteBgImagePath);
        ClassPathResource iconResource = new ClassPathResource(inviteIcon);
        response.setHeader("Content-Type", "image/jpeg");
        ResponseData<UserPoster> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        try {
            User user = userService.userInfoById(userId);
            //获取背景
            BufferedImage bgImage = ImageIO.read(resource.getInputStream());
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            int width = 142, height = 142;
            //生成二维码
            BitMatrix bitMatrix = multiFormatWriter.encode(inviteUrl + user.getInviteCode(), BarcodeFormat.QR_CODE, width, height, hints);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bgImage.setRGB(x + 109, y + 946, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            //添加中间icon
            BufferedImage iconImage = ImageIO.read(iconResource.getInputStream());
            for (int x = 0; x < 30; x++) {
                for (int y = 0; y < 30; y++) {
                    bgImage.setRGB(x + 166, y + 1003, iconImage.getRGB(x, y));
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bgImage, "jpeg", outputStream);
            responseData.setData(new UserPoster(Base64.encodeBase64String(outputStream.toByteArray())));
            return responseData.setMessage(MessageEnum.SUCCESS.getMessage()).setState(MessageEnum.SUCCESS.getState());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }


    /**
     * 微信分享token
     *
     * @param userId
     * @param requestShare
     * @return
     */
    @PostMapping("/wechat/info")
    @ApiOperation("微信分享token")
    public ResponseData<WeChatShare> createWeChatInfo(@LoginUserId Integer userId, @RequestBody WeChatShare requestShare) {
        User userInfo = userService.userInfoById(userId);
        if (null == userInfo)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());

        String accessToken = redisUtil.opsForValue().get("access_token");
        String ticket = redisUtil.opsForValue().get("ticket");
        if (null == accessToken) {
            Map<String, Object> params = new HashMap<>();
            params.put("appid", wxAppid);
            params.put("secret", wxAppSecret);
            params.put("grant_type", "client_credential");
            String paramStr = CFCARAUtil.joinMapValue(params, '&');
            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
            String accessTokenStr = CurlUtil.INSTANCE.httpRequest(tokenUrl, paramStr, "GET");
            JSONObject jsonObject = JSONObject.parseObject(accessTokenStr);
            if (null != jsonObject && jsonObject.getIntValue("errorCode") == 0) {
                accessToken = jsonObject.getString("access_token");
                redisUtil.opsForValue().setIfAbsent("access_token", accessToken, Duration.ofSeconds(jsonObject.getLongValue("expires_in")));
            }
        }

        if (null == ticket) {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", accessToken);
            params.put("type", "jsapi");
            String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
            String ticketStr = CurlUtil.INSTANCE.httpRequest(ticketUrl, CFCARAUtil.joinMapValue(params, '&'), "GET");
            JSONObject jsonObject = JSONObject.parseObject(ticketStr);
            if (null != jsonObject && jsonObject.getIntValue("errorCode") == 0) {
                ticket = jsonObject.getString("ticket");
                redisUtil.opsForValue().setIfAbsent("ticket", ticket, Duration.ofSeconds(jsonObject.getLongValue("expires_in")));
            }
        }

        SortedMap<String, Object> signParams = new TreeMap<>();
        signParams.put("timestamp", Long.toString(new Date().getTime() / 1000));
        signParams.put("noncestr", RandomStringUtils.randomAlphanumeric(16));
        signParams.put("jsapi_ticket", ticket);
        signParams.put("url", requestShare.getUrl());
        String sign = CommonUtil.INSTANCE.sha1Sign(CFCARAUtil.joinMapValue(signParams, '&'));
        WeChatShare share = new WeChatShare(signParams.get("timestamp").toString(), signParams.get("noncestr").toString(), sign);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), share);
    }

    /**
     * 品宣部-是否已领
     */
    @GetMapping("/already/pick/up")
    @ApiOperation("品宣部-领取菌包")
    public ResponseData<Boolean> alreadyPickUp(@LoginUserId Integer userId) {

        QueryWrapper<UserActivityLog> wrapper = new QueryWrapper<UserActivityLog>()
                .eq("user_id", userId)
                .likeRight("number", OrderPrefixEnum.FREE_JUN_BAO.getPreFix());
        List<UserActivityLog> logs = userActivityLogDAO.selectList(wrapper);
        if (Objects.nonNull(logs) && logs.size() > 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }

    /**
     * 品宣部-领取菌包
     */
    @PostMapping("/free/seed")
    @ApiOperation("品宣部-领取菌包")
    public ResponseData<Integer> freeSeed(@LoginUserId Integer userId, @RequestBody Login login) {

        // 请求参数错误
        if (Objects.isNull(login) || StringUtils.isEmpty(login.getPhone()) || StringUtils.isEmpty(login.getCode()))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        // 校验手机号格式
        if (!CommonUtil.INSTANCE.isPhone(login.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(), MessageEnum.PHONE_ERROR.getState());

        // 短信验证
        PhoneMessage message = messageService.messageByPhone(login.getPhone());
        if (Objects.isNull(message) || !Objects.equals(message.getCode(), login.getCode()))
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_LOGIN_ERROR.getMessage(), MessageEnum.MESSAGE_CODE_LOGIN_ERROR.getState());
        messageService.setMessageState(message.getId(), SystemEnum.PHONE_MESSAGE_VALIDATE);

        // 赠送菌包
        ResultData<Integer> resultData = seedOrderService.sendSeedSuccess(userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 品宣部-邀请记录
     */
    @GetMapping("/invite/log")
    @ApiOperation("品宣部-邀请记录")
    public ResponseData<List<UserActivityLogVo>> inviteLog(@LoginUserId Integer userId) {

        ResponseData<List<UserActivityLogVo>> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

        // 发奖
        List<UserActivityLogVo> logs = userService.inviteUser(userId);
        if (Objects.isNull(logs) || logs.isEmpty())
            return responseData
                    .setMessage(MessageEnum.ONT_INVITE_LOG.getMessage())
                    .setState(MessageEnum.ONT_INVITE_LOG.getState());

        return responseData
                .setMessage(MessageEnum.SUCCESS.getMessage())
                .setState(MessageEnum.SUCCESS.getState())
                .setData(logs);
    }

    /**
     * 品宣部-领取奖励
     */
    @PostMapping("/pick/up/reward")
    @ApiOperation("品宣部-领取奖励")
    public ResponseData<Integer> pickUpReward(@LoginUserId Integer userId, @RequestBody Login login) {

        ResponseData<Integer> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());

        if (Objects.isNull(login.getId()))
            return responseData;

        // 短信验证
        if (StringUtils.isNotEmpty(login.getPhone()) && StringUtils.isNotEmpty(login.getCode())) {
            PhoneMessage message = messageService.messageByPhone(login.getPhone());
            if (Objects.isNull(message) || !Objects.equals(message.getCode(), login.getCode()))
                return new ResponseData<>(MessageEnum.MESSAGE_CODE_LOGIN_ERROR.getMessage(), MessageEnum.MESSAGE_CODE_LOGIN_ERROR.getState());
            messageService.setMessageState(message.getId(), SystemEnum.PHONE_MESSAGE_VALIDATE);
        }

        ResultData<Integer> resultData = userService.pickUpReward(userId, login.getId());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 田园种植-每日分享
     */
    @GetMapping("/grow/up/sharing")
    @ApiOperation("田园种植-每日分享")
    public ResponseData<Integer> growUpSharing(@LoginUserId Integer userId) {
        ResultData<Integer> resultData = farmerService.getShareReward(userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 田园种植-分享标识
     */
    @GetMapping("/help/sharing")
    @ApiOperation("田园种植-分享标识")
    public ResponseData<UserSymbol> helpShare(@LoginUserId Integer userId) {
        ResultData<UserSymbol> resultData = farmerService.addFriendLog(userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }

    /**
     * 田园种植-好友助力
     */
    @GetMapping("/friend/help/{symbol}")
    @ApiOperation("田园种植-好友助力")
    public ResponseData<Integer> friendHelp(@LoginUserId Integer userId, @PathVariable("symbol") String symbol) {

        ResponseData<Integer> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        if (Objects.isNull(userId) || StringUtils.isBlank(symbol))
            return responseData;

        ResultData<Integer> resultData = farmerService.friendHelpSuccess(userId, symbol);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    @GetMapping("/fertilizer/dot")
    @ApiOperation("用户券红点")
    public ResponseData<FertilizerDot> fertilizerDot(@LoginUserId Integer userId) {
        FertilizerDot fertilizerDot = userFertilizerService.fertilizerDot(userId);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), fertilizerDot);
    }

    @PostMapping("/fertilizer/dot")
    @ApiOperation("取消用户券红点")
    public ResponseData<Boolean> cancelFertilizerDot(@LoginUserId Integer userId) {
        Boolean aBoolean = userFertilizerService.cancelFertilizerDot(userId);
        if (aBoolean)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }

}
