package com.moguying.plant.core.service.payment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.payment.PayRequestInfo;
import com.moguying.plant.core.entity.payment.request.*;
import com.moguying.plant.core.entity.payment.response.*;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.seed.vo.SendPayOrderResponse;
import com.moguying.plant.core.entity.system.PayOrder;

public interface PaymentService {


    /**
     * 快捷注册发送信息
     *
     * @param sendRegSmsCodeRequest
     * @return
     */

    PaymentResponse<SendRegSmsCodeResponse> sendRegSms(SendRegSmsCodeRequest sendRegSmsCodeRequest);


    /**
     * 用户信息注册
     *
     * @param registerRequest
     * @return
     */

    PaymentResponse<RegisterSyncResponse> userRegister(RegisterRequest registerRequest);


    /**
     * 用户实名认证
     *
     * @param realnameRequest
     * @return
     */

    PaymentResponse<RealnameSyncResponse> userRealname(RealnameRequest realnameRequest);


    /**
     * 绑定银行卡
     *
     * @param bindCardRequest
     * @return
     */

    PaymentResponse<BindCardResponse> bindCard(BindCardRequest bindCardRequest);


    /**
     * 删除银行卡
     *
     * @param deleteBankCardRequest
     * @return
     */

    PaymentResponse<DeleteBankCardResponse> deleteCard(DeleteBankCardRequest deleteBankCardRequest);


    /**
     * 查询绑卡信息
     *
     * @param queryBankCardBinRequest
     * @return
     */

    PaymentResponse<QueryBankCardBinResponse> queryBankCardBin(QueryBankCardBinRequest queryBankCardBinRequest);

    /**
     * 签约查询
     *
     * @param paySignQueryRequest
     * @return
     */

    PaymentResponse<PaySignQueryResponse> paySignQuery(PaySignQueryRequest paySignQueryRequest);


    /**
     * 支付签约与认证
     *
     * @param bankCardRequest
     * @return
     */

    PaymentResponse<PayAuthAndSignResponse> authAndSignPayment(PayAuthAndSignRequest bankCardRequest);


    /**
     * 支付发送短信
     *
     * @param sendPaySmsCodeRequest
     * @return
     */

    PaymentResponse<SendPaySmsCodeResponse> sendPaySmsCode(SendPaySmsCodeRequest sendPaySmsCodeRequest);


    /**
     * 支付
     *
     * @param payRequestInfo
     * @return
     */

    ResultData<PaymentResponse> pay(PayRequestInfo payRequestInfo);


    /**
     * 转账
     *
     * @param transferRequest
     * @return
     */

    PaymentResponse<TransferResponse> transferAmount(TransferRequest transferRequest);


    /**
     * 发送提现短信验证码
     *
     * @return
     */

    PaymentResponse<SendWithdrawSmsCodeResponse> sendWithdrawSmsCode(SendWithdrawSmsCodeRequest smsCodeRequest);


    /**
     * 提现
     *
     * @param withdrawMoneyRequest
     * @return
     */

    PaymentResponse<WithdrawMoneyResponse> withdrawMoney(WithdrawMoneyRequest withdrawMoneyRequest);


    /**
     * 设置支付密码-页面
     */

    PaymentRequest modifyPayPassword(ModifyPayPasswordRequest modifyPayPasswordRequest);


    /**
     * 提现-页面
     *
     * @return
     */

    PaymentRequest withdrawMoneyPage(WithdrawMoneyPageRequest withdrawRequest);


    /**
     * 图片上传
     *
     * @param imageUploadRequest
     * @return
     */

    PaymentResponse<ImageUploadResponse> imageUpload(ImageUploadRequest imageUploadRequest);


    /**
     * 发送绑卡信息
     *
     * @param bindCardSmsCodeRequest
     * @return
     */

    PaymentResponse<SendSmsCodeResponse> sendBindCardSmsCode(SendBindCardSmsCodeRequest bindCardSmsCodeRequest);


    /**
     * 发送支付信息
     *
     * @param payRequestInfo
     * @return
     */

    ResultData<PaymentResponse> sendPayInfo(PayRequestInfo payRequestInfo, String authMsg);


    /**
     * 支付订单前先发送支付验证码
     *
     * @param payOrder
     * @param userId
     * @return
     */

    ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId, BaseMapper dao, Class<? extends PayOrder> classs);


    PaymentRequest<WebHtmlPayRequest> generateWebHtmlPayData(SeedOrderDetail payOrder);


}
