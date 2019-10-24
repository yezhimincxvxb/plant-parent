package com.moguying.plant.core.service.payment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.system.PayOrder;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.payment.PayRequestInfo;
import com.moguying.plant.core.entity.payment.request.*;
import com.moguying.plant.core.entity.payment.response.*;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.seed.vo.SendPayOrderResponse;

public interface PaymentService {


    /**
     * 快捷注册发送信息
     * @param sendRegSmsCodeRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<SendRegSmsCodeResponse> sendRegSms(SendRegSmsCodeRequest sendRegSmsCodeRequest);


    /**
     * 用户信息注册
     * @param registerRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<RegisterSyncResponse> userRegister(RegisterRequest registerRequest);


    /**
     * 用户实名认证
     * @param realnameRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<RealnameSyncResponse> userRealname(RealnameRequest realnameRequest);


    /**
     * 绑定银行卡
     * @param bindCardRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<BindCardResponse> bindCard(BindCardRequest bindCardRequest);


    /**
     * 删除银行卡
     * @param deleteBankCardRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<DeleteBankCardResponse> deleteCard(DeleteBankCardRequest deleteBankCardRequest);


    /**
     * 查询绑卡信息
     * @param queryBankCardBinRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<QueryBankCardBinResponse> queryBankCardBin(QueryBankCardBinRequest queryBankCardBinRequest);

    /**
     * 签约查询
     * @param paySignQueryRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<PaySignQueryResponse> paySignQuery(PaySignQueryRequest paySignQueryRequest);



    /**
     * 支付签约与认证
     * @param bankCardRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<PayAuthAndSignResponse> authAndSignPayment(PayAuthAndSignRequest bankCardRequest);


    /**
     * 支付发送短信
     * @param sendPaySmsCodeRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<SendPaySmsCodeResponse> sendPaySmsCode(SendPaySmsCodeRequest sendPaySmsCodeRequest);


    /**
     * 支付
     * @param payRequestInfo
     * @return
     */
    @DataSource("write")
    ResultData<PaymentResponse> pay(PayRequestInfo payRequestInfo);


    /**
     * 转账
     * @param transferRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<TransferResponse> transferAmount(TransferRequest transferRequest);


    /**
     * 发送提现短信验证码
     * @return
     */
    @DataSource("write")
    PaymentResponse<SendWithdrawSmsCodeResponse> sendWithdrawSmsCode(SendWithdrawSmsCodeRequest smsCodeRequest);


    /**
     * 提现
     * @param withdrawMoneyRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<WithdrawMoneyResponse> withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest);




    /**
     * 设置支付密码-页面
     */
    @DataSource("write")
    PaymentRequest modifyPayPassword(ModifyPayPasswordRequest modifyPayPasswordRequest);


    /**
     * 提现-页面
     * @return
     */
    @DataSource("write")
    PaymentRequest withdrawMoneyPage(WithdrawMoneyPageRequest withdrawRequest);




    /**
     * 图片上传
     * @param imageUploadRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<ImageUploadResponse> imageUpload(ImageUploadRequest imageUploadRequest);


    /**
     * 发送绑卡信息
     * @param bindCardSmsCodeRequest
     * @return
     */
    @DataSource("write")
    PaymentResponse<SendSmsCodeResponse> sendBindCardSmsCode(SendBindCardSmsCodeRequest bindCardSmsCodeRequest);


    /**
     * 发送支付信息
     * @param payRequestInfo
     * @return
     */
    @DataSource("write")
    ResultData<PaymentResponse> sendPayInfo(PayRequestInfo payRequestInfo, String authMsg);


    /**
     * 支付订单前先发送支付验证码
     * @param payOrder
     * @param userId
     * @return
     */
    @DataSource("write")
    ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId, BaseMapper dao, Class<? extends PayOrder> classs);


    @DataSource("write")
    PaymentRequest<WebHtmlPayRequest> generateWebHtmlPayData(SeedOrderDetail payOrder);


}
