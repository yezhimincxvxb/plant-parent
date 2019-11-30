package com.moguying.plant.core.service.payment.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.constant.*;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.payment.PaymentInfoDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.payment.PayRequestInfo;
import com.moguying.plant.core.entity.payment.PaymentInfo;
import com.moguying.plant.core.entity.payment.request.*;
import com.moguying.plant.core.entity.payment.response.*;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.seed.vo.SendPayOrderResponse;
import com.moguying.plant.core.entity.system.PayOrder;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserBank;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.CFCARAUtil;
import com.moguying.plant.utils.CurlUtil;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


@Service
@SuppressWarnings("all")
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Value("${payment.merNo}")
    private String merNo;

    @Value("${payment.callback}")
    private String callbackUrl;

    @Value("${payment.return.url}")
    private String returnUrl;

    @Value("${payment.version}")
    private String version;

    @Value("${payment.signType}")
    private String signType;

    @Value("${payment.pfxfile}")
    private String pfxfile;

    @Value("${payment.cerfile}")
    private String cerfile;

    @Value("${payment.password}")
    private String password;

    @Autowired
    private PaymentInfoDAO paymentInfoDAO;

    @Autowired
    private
    UserDAO userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMoneyDAO moneyDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * 发送快捷注册短信
     *
     * @param sendRegSmsCodeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<SendRegSmsCodeResponse> sendRegSms(SendRegSmsCodeRequest sendRegSmsCodeRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //后续要改成异步
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            //com（公司）per（个人）pcy（个体工商户）目前只支持个人
            sendRegSmsCodeRequest.setMerType("per");
            //单独加密
            sendRegSmsCodeRequest.setPhone(CFCARAUtil.encryptMessageByRSA_PKCS(sendRegSmsCodeRequest.getPhone(), cerfile));
            sendRegSmsCodeRequest.setIdNo(CFCARAUtil.encryptMessageByRSA_PKCS(sendRegSmsCodeRequest.getIdNo(), cerfile));
            paymentRequest.setApiContent(sendRegSmsCodeRequest);
            paymentRequest.setSignType(signType);
            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));
            //签名后再进行UrlEncode
            sendRegSmsCodeRequest.setPhone(URLEncoder.encode(sendRegSmsCodeRequest.getPhone(), "UTF-8"));
            sendRegSmsCodeRequest.setIdNo(URLEncoder.encode(sendRegSmsCodeRequest.getIdNo(), "UTF-8"));
            paymentRequest.setApiContent(sendRegSmsCodeRequest);
            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.REGISTER_SEND_SMS.getUrl(),
                    requestParam, "POST");

            PaymentResponse<SendRegSmsCodeResponse> SendRegSmsCodeResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<SendRegSmsCodeResponse>>() {
                    });
            SendRegSmsCodeResponse.setData(JSON.parseObject(SendRegSmsCodeResponse.getResponseParameters(), SendRegSmsCodeResponse.class));
            addLog(paymentRequest, PaymentRequestUrlEnum.REGISTER_SEND_SMS.getUrl(), signData, responseStr, null);
            return SendRegSmsCodeResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void addLog(PaymentRequest request, String actionUrl, String signData, String responseStr, String orderNumber) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentRequest(JSON.toJSONString(request));
        paymentInfo.setRequestAction(actionUrl);
        paymentInfo.setSignData(signData);
        paymentInfo.setPaymentResponse(responseStr);
        if (null != orderNumber)
            paymentInfo.setOrderNumber(orderNumber);
        paymentInfo.setAddTime(new Date());
        mongoTemplate.insert(paymentInfo);
        // paymentInfoDAO.insert(paymentInfo);
    }


    /**
     * 用户注册信息
     *
     * @param registerRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<RegisterSyncResponse> userRegister(RegisterRequest registerRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //后续要改成异步
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            //单独加密
            registerRequest.setPhone(CFCARAUtil.encryptMessageByRSA_PKCS(registerRequest.getPhone(), cerfile));
            registerRequest.setIdNo(CFCARAUtil.encryptMessageByRSA_PKCS(registerRequest.getIdNo(), cerfile));
            paymentRequest.setApiContent(registerRequest);
            paymentRequest.setSignType(signType);
            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));
            //签名后再进行UrlEncode
            registerRequest.setPhone(URLEncoder.encode(registerRequest.getPhone(), "UTF-8"));
            registerRequest.setIdNo(URLEncoder.encode(registerRequest.getIdNo(), "UTF-8"));
            paymentRequest.setApiContent(registerRequest);
            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.REGISTER_URL.getUrl(),
                    requestParam, "POST");
            PaymentResponse<RegisterSyncResponse> registerResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<RegisterSyncResponse>>() {
                    });
            registerResponse.setData(JSON.parseObject(registerResponse.getResponseParameters(), RegisterSyncResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.REGISTER_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return registerResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 对响应验签
     *
     * @return
     */
    private boolean checkResponseSign(PaymentResponse response) throws Exception {
        return true;
        /*Map<String,Object> map = JSON.parseObject(JSON.toJSONString(response),LinkedHashMap.class);
        String beforeSignedData = CFCARAUtil.joinMapValue(map,'&');
        return CFCARAUtil.verifyMessageByP1(beforeSignedData,response.getSign(),cerfile);*/
    }

    /**
     * 用户实名认证
     *
     * @param realnameRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<RealnameSyncResponse> userRealname(RealnameRequest realnameRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //后续要改成异步
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));

            paymentRequest.setApiContent(realnameRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.REALNAME_URL.getUrl(),
                    requestParam, "POST");
            PaymentResponse<RealnameSyncResponse> realnameResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<RealnameSyncResponse>>() {
                    });
            realnameResponse.setData(JSON.parseObject(realnameResponse.getResponseParameters(), RealnameSyncResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.REALNAME_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return realnameResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 用户绑定银行卡
     *
     * @param bindCardRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<BindCardResponse> bindCard(BindCardRequest bindCardRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));

            paymentRequest.setApiContent(bindCardRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.BIND_CARD_URL.getUrl(),
                    requestParam, "POST");
            PaymentResponse<BindCardResponse> bindCardResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<BindCardResponse>>() {
                    });
            bindCardResponse.setData(JSON.parseObject(bindCardResponse.getResponseParameters(), BindCardResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.BIND_CARD_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return bindCardResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询银行卡信息
     *
     * @param queryBankCardBinRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<QueryBankCardBinResponse> queryBankCardBin(QueryBankCardBinRequest queryBankCardBinRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //选填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(queryBankCardBinRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.QUERY_BANK_CARD_BIN.getUrlWithoutBaseUrl(),
                    requestParam, "POST");
            PaymentResponse<QueryBankCardBinResponse> queryBankCardResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<QueryBankCardBinResponse>>() {
                    });
            if (queryBankCardResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
                List<QueryBankCardBinResponse> bankList = JSON.parseArray(queryBankCardResponse.getResponseParameters(),
                        QueryBankCardBinResponse.class);
                queryBankCardResponse.setData(bankList.get(0));
            }

            addLog(paymentRequest, PaymentRequestUrlEnum.QUERY_BANK_CARD_BIN.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return queryBankCardResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除银行卡
     *
     * @param deleteBankCardRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<DeleteBankCardResponse> deleteCard(DeleteBankCardRequest deleteBankCardRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //选填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            deleteBankCardRequest.setCardNo(CFCARAUtil.encryptMessageByRSA_PKCS(deleteBankCardRequest.getCardNo(), cerfile));
            paymentRequest.setApiContent(deleteBankCardRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));
            //签名后再urlencode
            deleteBankCardRequest.setCardNo(URLEncoder.encode(deleteBankCardRequest.getCardNo(), "UTF-8"));
            paymentRequest.setApiContent(deleteBankCardRequest);

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.BIND_CARD_DELETE_URL.getUrl(),
                    requestParam, "POST");
            PaymentResponse<DeleteBankCardResponse> deleteBankCardResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<DeleteBankCardResponse>>() {
                    });
            deleteBankCardResponse.setData(JSON.parseObject(deleteBankCardResponse.getResponseParameters(), DeleteBankCardResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.BIND_CARD_DELETE_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return deleteBankCardResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 签约查询
     *
     * @param paySignQueryRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<PaySignQueryResponse> paySignQuery(PaySignQueryRequest paySignQueryRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));

            paymentRequest.setApiContent(paySignQueryRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.PAY_SIGN_QUERY_URL.getUrl(),
                    requestParam, "POST");
            PaymentResponse<PaySignQueryResponse> bindCardResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<PaySignQueryResponse>>() {
                    });
            bindCardResponse.setData(JSON.parseObject(bindCardResponse.getResponseParameters(), PaySignQueryResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.PAY_SIGN_QUERY_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return bindCardResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签约认证
     *
     * @param bankCardRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<PayAuthAndSignResponse> authAndSignPayment(PayAuthAndSignRequest payAuthAndSignRequest) {

        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));

            paymentRequest.setApiContent(payAuthAndSignRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.AUTH_AND_SIGN_URL.getUrl(),
                    requestParam, "POST");

            PaymentResponse<PayAuthAndSignResponse> payauthAndsignResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<PayAuthAndSignResponse>>() {
                    });
            payauthAndsignResponse.setData(JSON.parseObject(payauthAndsignResponse.getResponseParameters(), PayAuthAndSignResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.AUTH_AND_SIGN_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return payauthAndsignResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 发送支付短信
     *
     * @param sendPaySmsCodeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<SendPaySmsCodeResponse> sendPaySmsCode(SendPaySmsCodeRequest sendPaySmsCodeRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(sendPaySmsCodeRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.SEND_PAY_SMS_URL.getUrl(),
                    requestParam, "POST");

            PaymentResponse<SendPaySmsCodeResponse> sendPaySmsCodeResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<SendPaySmsCodeResponse>>() {
                    });
            sendPaySmsCodeResponse.setData(JSON.parseObject(sendPaySmsCodeResponse.getResponseParameters(), SendPaySmsCodeResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.SEND_PAY_SMS_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);
            return sendPaySmsCodeResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 支付
     *
     * @param payRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public ResultData<PaymentResponse> pay(PayRequestInfo payRequestInfo) {
        User userInfo = userService.userInfoById(payRequestInfo.getUserId());
        UserBank bank = userService.bankCard(payRequestInfo.getUserId(), payRequestInfo.getBankId());
        ResultData resultData = new ResultData(MessageEnum.ERROR, null);
        ResultData<PaymentResponse> validateResult = payBeforeValidate(userInfo, bank);
        if (!validateResult.getMessageEnum().equals(MessageEnum.SUCCESS))
            return validateResult;

        if (null == payRequestInfo.getMoney() || StringUtils.isEmpty(payRequestInfo.getMoney()))
            return resultData.setMessageEnum(MessageEnum.OPERATE_MONEY_ERROR);

        PayRequest payRequest = new PayRequest();
        payRequest.setCardNo(bank.getBankNumber());
        payRequest.setCustName(userInfo.getRealName());
        //用填写的银行预留手机号
        payRequest.setPhone(bank.getBankPhone());
        payRequest.setIdNo(userInfo.getIdCard());
        payRequest.setPayAmount(payRequestInfo.getMoney());
        payRequest.setMerOrderNo(payRequestInfo.getMerOrderNo());
        payRequest.setOrderSubject(payRequestInfo.getOrderSubject());
        payRequest.setApiPayType(PaymentStateEnum.PAY_IN_TIME_TYPE.getStateInfo());
        payRequest.setSellerNo(merNo);
        payRequest.setSmsCode(payRequestInfo.getSmsCode());
        payRequest.setSeqNo(payRequestInfo.getSeqNo());

        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            paymentRequest.setNotifyUrl(callbackUrl + "/pay");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(payRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.PAY_URL.getUrl(),
                    requestParam, "POST");

            PaymentResponse<PayResponse> payResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<PayResponse>>() {
                    });
            payResponse.setData(JSON.parseObject(payResponse.getResponseParameters(), PayResponse.class));


            if (null != payResponse && payResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo()))
                resultData.setMessageEnum(MessageEnum.SUCCESS).setData(payResponse);
            else if (null != payResponse) {
                resultData.setMessageEnum(MessageEnum.ERROR).setData(payResponse);
            }

            addLog(paymentRequest, PaymentRequestUrlEnum.PAY_URL.getUrlWithoutBaseUrl(), signData, responseStr, payRequest.getMerOrderNo());

            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            return resultData;
        }
    }

    /**
     * 转账
     *
     * @param transferRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<TransferResponse> transferAmount(TransferRequest transferRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(transferRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.TRANSFER_URL.getUrl(),
                    requestParam, "POST");

            PaymentResponse<TransferResponse> transferResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<TransferResponse>>() {
                    });
            transferResponse.setData(JSON.parseObject(transferResponse.getResponseParameters(), TransferResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.TRANSFER_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return transferResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 发送提现手机短信
     *
     * @param smsCodeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<SendWithdrawSmsCodeResponse> sendWithdrawSmsCode(SendWithdrawSmsCodeRequest smsCodeRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(smsCodeRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.SEND_WITHDRAW_SMS_URL.getUrl(),
                    requestParam, "POST");

            PaymentResponse<SendWithdrawSmsCodeResponse> sendSmsResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<SendWithdrawSmsCodeResponse>>() {
                    });
            sendSmsResponse.setData(JSON.parseObject(sendSmsResponse.getResponseParameters(), SendWithdrawSmsCodeResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.SEND_WITHDRAW_SMS_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);
            return sendSmsResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<WithdrawMoneyResponse> withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //选填
            paymentRequest.setNotifyUrl(callbackUrl + "/withdraw");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(withdrawMoneyRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.WITHDRAW_MONEY_TO_ACCOUNT.getUrl(),
                    requestParam, "POST");

            PaymentResponse<WithdrawMoneyResponse> withdrawResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<WithdrawMoneyResponse>>() {
                    });
            withdrawResponse.setData(JSON.parseObject(withdrawResponse.getResponseParameters(), WithdrawMoneyResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.WITHDRAW_MONEY_TO_ACCOUNT.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return withdrawResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @DS("write")
    public PaymentRequest modifyPayPassword(ModifyPayPasswordRequest modifyPayPasswordRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(modifyPayPasswordRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(sign);


            return paymentRequest;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    @DS("write")
    public PaymentRequest withdrawMoneyPage(WithdrawMoneyPageRequest withdrawRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            paymentRequest.setApiContent(withdrawRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(sign);


            return paymentRequest;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片上传
     *
     * @param imageUploadRequest
     * @return
     */
    @Override
    @DS("write")
    public PaymentResponse<ImageUploadResponse> imageUpload(ImageUploadRequest imageUploadRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));

            paymentRequest.setApiContent(imageUploadRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            imageUploadRequest.setImage(URLEncoder.encode(imageUploadRequest.getImage(), "UTF-8"));
            paymentRequest.setApiContent(imageUploadRequest);

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.IMAGE_UPLOAD_URL.getUrl(),
                    requestParam, "POST");

            PaymentResponse<ImageUploadResponse> paymentResponse = JSON.parseObject(responseStr,
                    new TypeReference<PaymentResponse<ImageUploadResponse>>() {
                    });
            paymentResponse.setData(JSON.parseObject(paymentResponse.getResponseParameters(), ImageUploadResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.IMAGE_UPLOAD_URL.getUrlWithoutBaseUrl(), signData, responseStr, null);

            return paymentResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 发送绑卡短信
     *
     * @param bindCardSmsCodeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS("write")
    public PaymentResponse<SendSmsCodeResponse> sendBindCardSmsCode(SendBindCardSmsCodeRequest bindCardSmsCodeRequest) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl("");
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));

            paymentRequest.setApiContent(bindCardSmsCodeRequest);
            paymentRequest.setSignType(signType);

            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(URLEncoder.encode(sign, "UTF-8"));

            String requestParam = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String responseStr = CurlUtil.INSTANCE.httpRequest(PaymentRequestUrlEnum.SEND_BIND_CARD_SMS.getUrl(),
                    requestParam, "POST");
            PaymentResponse<SendSmsCodeResponse> realnameResponse =
                    JSON.parseObject(responseStr, new TypeReference<PaymentResponse<SendSmsCodeResponse>>() {
                    });
            realnameResponse.setData(JSON.parseObject(realnameResponse.getResponseParameters(), SendSmsCodeResponse.class));

            addLog(paymentRequest, PaymentRequestUrlEnum.SEND_BIND_CARD_SMS.getUrlWithoutBaseUrl(), signData, responseStr, null);
            return realnameResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @DS("write")
    public ResultData<PaymentResponse> sendPayInfo(PayRequestInfo payRequestInfo, String authMsg) {
        User userInfo = userService.userInfoById(payRequestInfo.getUserId());
        UserBank bank = userService.bankCard(payRequestInfo.getUserId(), payRequestInfo.getBankId());
        ResultData resultData = new ResultData(MessageEnum.ERROR, 0);
        ResultData<PaymentResponse> validateResult = payBeforeValidate(userInfo, bank);
        if (!validateResult.getMessageEnum().equals(MessageEnum.SUCCESS))
            return validateResult;

        if (null == payRequestInfo.getMoney() || StringUtils.isEmpty(payRequestInfo.getMoney()))
            return resultData.setMessageEnum(MessageEnum.OPERATE_MONEY_ERROR);

        // 查询签约情况
        PaySignQueryRequest paySignQueryRequest = new PaySignQueryRequest();
        paySignQueryRequest.setCardNo(bank.getBankNumber());
        paySignQueryRequest.setCustName(userInfo.getRealName());
        paySignQueryRequest.setPhone(bank.getBankPhone());
        paySignQueryRequest.setIdNo(userInfo.getIdCard());
        paySignQueryRequest.setMerOrderNo(payRequestInfo.getMerOrderNo());
        paySignQueryRequest.setPayAmount(payRequestInfo.getMoney());

        resultData = authAndSignPayment(paySignQueryRequest, authMsg);

        if (resultData.getMessageEnum().equals(MessageEnum.PAYMENT_AUTH_SUCCESS))
            return resultData.setMessageEnum(MessageEnum.PAYMENT_NEED_SIGN);

        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            //发送支付短信
            SendPaySmsCodeRequest paySmsCodeRequest = new SendPaySmsCodeRequest();
            paySmsCodeRequest.setCardNo(paySignQueryRequest.getCardNo());
            paySmsCodeRequest.setCustName(paySignQueryRequest.getCustName());
            paySmsCodeRequest.setPhone(paySignQueryRequest.getPhone());
            paySmsCodeRequest.setIdNo(paySignQueryRequest.getIdNo());
            paySmsCodeRequest.setPayAmount(paySignQueryRequest.getPayAmount());
            paySmsCodeRequest.setMerOrderNo(paySignQueryRequest.getMerOrderNo());
            paySmsCodeRequest.setOrderSubject(payRequestInfo.getOrderSubject());
            paySmsCodeRequest.setApiPayType(PaymentStateEnum.PAY_IN_TIME_TYPE.getStateInfo());
            paySmsCodeRequest.setSellerNo(merNo);
            PaymentResponse<SendPaySmsCodeResponse> paySmsCodeResponse = sendPaySmsCode(paySmsCodeRequest);
            if (null != paySmsCodeResponse && paySmsCodeResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(paySmsCodeResponse);
            } else if (null != paySmsCodeResponse) {
                return resultData.setMessageEnum(MessageEnum.ERROR).setData(paySmsCodeResponse);
            }
        } else if (null != resultData.getData()) {
            return resultData.setMessageEnum(MessageEnum.ERROR);
        }

        return resultData;
    }


    /**
     * 支付前较验
     *
     * @param userInfo
     * @param bank
     * @return
     */
    private ResultData<PaymentResponse> payBeforeValidate(User userInfo, UserBank bank) {
        ResultData<PaymentResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (null == userInfo)
            return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);

        // 未实名
        if (!userInfo.getIsRealName().equals(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getState()))
            return resultData.setMessageEnum(MessageEnum.USER_NEED_REAL_NAME);

        // 未绑卡
        if (!userInfo.getIsBindCard())
            return resultData.setMessageEnum(MessageEnum.USER_NOT_BIND_CARD);

        // 未注册第三方支付
        if (!userInfo.getPaymentState().equals(UserEnum.USER_PAYMENT_ACCOUNT_REGISTER.getState()))
            return resultData.setMessageEnum(MessageEnum.USER_NEED_REGISTER_PAYMENT_ACCOUNT);

        if (null == userInfo.getPaymentAccount() || StringUtils.isEmpty(userInfo.getPaymentAccount()))
            return resultData.setMessageEnum(MessageEnum.USER_NEED_REGISTER_PAYMENT_ACCOUNT);

        if (null == bank)
            return resultData.setMessageEnum(MessageEnum.USER_NOT_BIND_CARD);
        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }


    /**
     * 查询及认证
     */
    private ResultData<PaymentResponse> authAndSignPayment(PaySignQueryRequest paySignQueryRequest, String authMsg) {
        ResultData<PaymentResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);
        //已签约
        PaymentResponse<PaySignQueryResponse> paymentResponse = paySignQuery(paySignQueryRequest);
        if (null != paymentResponse && paymentResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        //构造参数
        PayAuthAndSignRequest payAuthAndSignRequest = new PayAuthAndSignRequest();
        payAuthAndSignRequest.setCardNo(paySignQueryRequest.getCardNo());
        payAuthAndSignRequest.setCustName(paySignQueryRequest.getCustName());
        payAuthAndSignRequest.setPhone(paySignQueryRequest.getPhone());
        payAuthAndSignRequest.setIdNo(paySignQueryRequest.getIdNo());
        payAuthAndSignRequest.setMerOrderNo(paySignQueryRequest.getMerOrderNo());
        if (null == authMsg || StringUtils.isEmpty(authMsg)) {
            payAuthAndSignRequest.setCustType(PaymentStateEnum.PAY_AUTH_TYPE.getStateInfo());
            payAuthAndSignRequest.setAuthMsg("");
            //先认证
            PaymentResponse<PayAuthAndSignResponse> authResponse = authAndSignPayment(payAuthAndSignRequest);
            if (null != authResponse && authResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
                return resultData.setMessageEnum(MessageEnum.PAYMENT_AUTH_SUCCESS);
            } else if (null != authResponse)
                return resultData.setData(authResponse);
            else return resultData;
        } else if (null != authMsg && StringUtils.isNotEmpty(authMsg)) {
            payAuthAndSignRequest.setCustType(PaymentStateEnum.PAY_SIGN_TYPE.getStateInfo());
            payAuthAndSignRequest.setAuthMsg(authMsg);
            //再签约
            PaymentResponse<PayAuthAndSignResponse> signResponse = authAndSignPayment(payAuthAndSignRequest);
            if (null != signResponse && signResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
            } else if (null != signResponse)
                return resultData.setData(signResponse);
        }
        return resultData;
    }


    @Transactional
    @Override
    @DS("write")
    public ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId, BaseMapper dao, Class<? extends PayOrder> classs) {
        ResultData<SendPayOrderResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);
        try {
            // 订单不存在
            PayOrder orderDetail = (PayOrder) dao.selectById(payOrder.getOrderId());
            if (null == orderDetail)
                return resultData.setMessageEnum(MessageEnum.PAY_ORDER_NOT_EXISTS);

            // 不是可支付状态
            if (null != orderDetail.getPayTime() || !orderDetail.getState().equals(PaymentStateEnum.ORDER_NEED_PAY.getState()))
                return resultData.setMessageEnum(MessageEnum.PAY_ORDER_CAN_NOT_PAY);

            // 在非支付验签时使用券
            BigDecimal reductAmount = BigDecimal.ZERO;
            if (null != payOrder.getFertilizerIds() && !payOrder.getFertilizerIds().isEmpty()) {
                FertilizerUseCondition condition = new FertilizerUseCondition();
                condition.setUserId(userId);
                condition.setAmount(orderDetail.getBuyAmount());

                // 菌包使用券
                if (orderDetail.getOpType().equals(MoneyOpEnum.BUY_SEED_ORDER)) {
                    condition.setSeedOrderId(orderDetail.getId());
                }

                // 商品使用券
                if (orderDetail.getOpType().equals(MoneyOpEnum.BUY_MALL_PRODUCT)) {
                    condition.setMallOrderId(orderDetail.getId());
                }

                // 使用券
                ResultData<BigDecimal> fertilizerResult =
                        fertilizerService.useFertilizers(condition, payOrder.getFertilizerIds(), orderDetail.getOrderNumber());

                // 使用的券总金额
                if (fertilizerResult.getMessageEnum().equals(MessageEnum.SUCCESS))
                    reductAmount = fertilizerResult.getData();
                else
                    return resultData.setMessageEnum(fertilizerResult.getMessageEnum());
            }

            SendPayOrderResponse payOrderResponse = new SendPayOrderResponse();
            PayOrder updateDetail = classs.newInstance();
            updateDetail.setId(orderDetail.getId());
            updateDetail.setReducePayAmount(reductAmount);

            // 实际购买价格：初始购买价 - 券金额
            updateDetail.setBuyAmount(orderDetail.getBuyAmount().subtract(reductAmount));
            // 实际支付金额
            BigDecimal payAmount = orderDetail.getBuyAmount().add(orderDetail.getFeeAmount()).subtract(reductAmount);

            // 卡支付部份
            BigDecimal carPayMoney;
            // 勾选余额支付
            if (payOrder.getIsCheck()) {
                UserMoney userMoney = moneyDAO.selectById(userId);
                // 余额充足
                if (userMoney.getAvailableMoney().compareTo(payAmount) >= 0) {
                    payOrderResponse.setNeedPassword(true);
                    updateDetail.setCarPayAmount(BigDecimal.ZERO);
                    updateDetail.setAccountPayAmount(payAmount);
                    dao.updateById(updateDetail);
                    return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(payOrderResponse);
                } else {
                    // 卡支付的部分
                    carPayMoney = payAmount.subtract(userMoney.getAvailableMoney());
                    updateDetail.setAccountPayAmount(userMoney.getAvailableMoney());
                }
            } else {
                updateDetail.setAccountPayAmount(BigDecimal.ZERO);
                carPayMoney = payAmount;
            }

            // 非全余额支付
            payOrderResponse.setNeedPassword(false);

            // 银行卡支付
            PayRequestInfo payRequestInfo = new PayRequestInfo();
            payRequestInfo.setUserId(userId);
            payRequestInfo.setMoney(carPayMoney.toString());
            payRequestInfo.setMerOrderNo(orderDetail.getOrderNumber());
            payRequestInfo.setOrderSubject(orderDetail.getOpType().getTypeStr() + orderDetail.getOrderNumber());
            payRequestInfo.setBankId(payOrder.getBankId());

            ResultData<PaymentResponse> payResult = this.sendPayInfo(payRequestInfo, payOrder.getAuthMsg());
            if (null != payResult && payResult.getMessageEnum().equals(MessageEnum.SUCCESS)) {
                if (payResult.getData().getData() instanceof SendPaySmsCodeResponse) {
                    SendPaySmsCodeResponse smsCodeResponse = (SendPaySmsCodeResponse) payResult.getData().getData();
                    updateDetail.setSeqNo(smsCodeResponse.getSeqNo());
                    updateDetail.setCarPayAmount(carPayMoney);
                    dao.updateById(updateDetail);
                    payOrderResponse.setSeqNo(smsCodeResponse.getSeqNo());
                    return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(payOrderResponse);
                }
            } else if (null != payResult) {
                if (null != payResult.getData())
                    payOrderResponse.setErrorMsg(payResult.getData().getMsg());
                resultData.setMessageEnum(payResult.getMessageEnum()).setData(payOrderResponse);
            }
            return resultData;
        } catch (IllegalAccessException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (InstantiationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return resultData;
    }


    @Override
    @DS("write")
    public PaymentRequest<WebHtmlPayRequest> generateWebHtmlPayData(SeedOrderDetail seedOrderDetail) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setMerNo(merNo);
            paymentRequest.setVersion(version);
            //不填
            paymentRequest.setNotifyUrl(callbackUrl.concat("/pay"));
            paymentRequest.setTimestamp(DateUtil.INSTANCE.formatDateForPayment(new Date()));
            User userInfo = userService.userInfoById(seedOrderDetail.getUserId());

            WebHtmlPayRequest webHtmlPayRequest = new WebHtmlPayRequest();
            webHtmlPayRequest.setSellerNo(merNo);

            //支付渠道设置
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fastpayXy", true);
            jsonObject.put("weChatPay", false);
            jsonObject.put("aliPay", true);
            jsonObject.put("corpBank", false);
            jsonObject.put("personalBank", true);
            jsonObject.put("balancePay", false);
            webHtmlPayRequest.setPayChannels(jsonObject.toJSONString());
            webHtmlPayRequest.setOrderBody(seedOrderDetail.getOpType().getTypeStr() + seedOrderDetail.getOrderNumber());
            webHtmlPayRequest.setPayAmount(seedOrderDetail.getBuyAmount().toString());
            webHtmlPayRequest.setApiPayType(PaymentStateEnum.PAY_IN_TIME_TYPE.getStateInfo());
            webHtmlPayRequest.setMerMerOrderNo(seedOrderDetail.getOrderNumber());
            webHtmlPayRequest.setBuyerNo(userInfo.getPaymentAccount());
            webHtmlPayRequest.setOrderSubject(webHtmlPayRequest.getOrderBody());
            webHtmlPayRequest.setReturnUrl(returnUrl);
            paymentRequest.setApiContent(webHtmlPayRequest);
            paymentRequest.setSignType(signType);
            String signData = CFCARAUtil.joinMapValue(JSON.parseObject(JSON.toJSONString(paymentRequest, SerializerFeature.SortField),
                    Feature.OrderedField), '&');
            String sign = CFCARAUtil.signMessageByP1(signData, pfxfile, password);
            paymentRequest.setSign(sign);

            SeedOrderDetail update = new SeedOrderDetail();
            update.setId(seedOrderDetail.getId());
            update.setCarPayAmount(seedOrderDetail.getBuyAmount());
            seedOrderDetailDAO.updateById(update);

            addLog(paymentRequest, PaymentRequestUrlEnum.PAY_TO_PAY_HTML.getUrlWithoutBaseUrl(), signData, "", seedOrderDetail.getOrderNumber());

            return paymentRequest;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
