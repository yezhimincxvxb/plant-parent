package com.moguying.plant.core.controller.payment;


import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.constant.*;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.MoneyWithdraw;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;
import com.moguying.plant.core.entity.dto.User;
import com.moguying.plant.core.entity.dto.UserBank;
import com.moguying.plant.core.entity.dto.payment.callback.BindCardCallback;
import com.moguying.plant.core.entity.dto.payment.callback.CallBackResponse;
import com.moguying.plant.core.entity.dto.payment.callback.CallBackResponseToPayment;
import com.moguying.plant.core.entity.dto.payment.request.QueryBankCardBinRequest;
import com.moguying.plant.core.entity.dto.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.dto.payment.response.QueryBankCardBinResponse;
import com.moguying.plant.core.service.account.MoneyWithdrawService;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Slf4j
@Controller
@RequestMapping("/payment/notify")
public class CallBackController {

    private static final String TYPE_WITHDRAW = "3";
    private static final String TYPE_IN_ACCOUNT = "1";
    private static final String TYPE_BIND_CARD = "5";

    @Autowired
    private MoneyWithdrawService moneyWithdrawService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SeedOrderDetailService seedOrderDetailService;

    @Autowired
    private PlantOrderService plantOrderService;

    /**
     * 支付异步处理
     * @param request
     * @return
     */
    @PostMapping("/pay")
    @ResponseBody
    public CallBackResponseToPayment payCallback(HttpServletRequest request, PaymentResponse paymentResponse){
        CallBackResponseToPayment responseToPayment = new CallBackResponseToPayment("000000", "success");
        //TODO 验签

        if (TYPE_IN_ACCOUNT.equals(paymentResponse.getResponseType()) &&

                PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo().equals(paymentResponse.getCode())) {
            CallBackResponse callBackResponse = null;
            if(null != paymentResponse.getData()){
                callBackResponse = (CallBackResponse) paymentResponse.getData();
            }
            if(null != callBackResponse){
                String orderNumber = callBackResponse.getMerMerOrderNo();
                if(orderNumber.startsWith("D")){
                    SeedOrderDetail detail = seedOrderDetailService.selectByOrderNumber(orderNumber);
                    if(detail.getState().equals(SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState()))
                        return null;
                    if(null == detail) return responseToPayment;
                    BigDecimal payAmount = new BigDecimal(callBackResponse.getPayAmount());
                    if(payAmount.compareTo(detail.getBuyAmount()) != 0) return null;
                    User user = userService.userInfoById(detail.getUserId());
                    if(plantOrderService.payOrderSuccess(detail,user).getMessageEnum().equals(MessageEnum.SUCCESS))
                        return responseToPayment;
                    else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return null;
                    }
                } else
                    return responseToPayment;
            }
        }

        return responseToPayment;
    }


    /**
     * 提现异步处理
     * @return
     */
    @PostMapping("/withdraw")
    @ResponseBody
    public CallBackResponseToPayment withdrawCallback(HttpServletRequest request, PaymentResponse paymentResponse) {
        if (TYPE_WITHDRAW.equals(paymentResponse.getResponseType()) &&
                PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo().equals(paymentResponse.getCode())) {
            MoneyWithdraw withdraw = null;
            CallBackResponse callBackResponse = null;
            if (null != paymentResponse.getData()) {
                callBackResponse = (CallBackResponse) paymentResponse.getData();
                withdraw = moneyWithdrawService.selectByOrderNumber(callBackResponse.getMerMerOrderNo());
            }
            if (PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo().equals(paymentResponse.getCode()) && null != withdraw
                    && withdraw.getState().equals(MoneyStateEnum.WITHDRAW_ACCOUNT_ING.getState())) {
                if (withdraw.getToAccountMoney().compareTo(new BigDecimal(callBackResponse.getPayAmount())) != 0) {
                    log.debug("withdraw:{},amount:{},not match", callBackResponse.getPayAmount(), callBackResponse.getMerMerOrderNo());
                    return null;
                }
                //提现到账
                ResultData<PaymentResponse> resultData =
                        moneyWithdrawService.reviewMoneyWithdraw(withdraw.getId(),MoneyStateEnum.WITHDRAW_IN_ACCOUNT.getState());
                if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
                    return new CallBackResponseToPayment("000000", "success");
                }
            } else {
                //退还提现金额
                if(null != withdraw) {
                    moneyWithdrawService.reviewMoneyWithdraw(withdraw.getId(), MoneyStateEnum.WITHDRAW_FAILED.getState());
                }
            }
        }
        return null;
    }


    /**
     * 绑卡回调
     * @return
     */
    @PostMapping("/bind/card")
    @ResponseBody
    public CallBackResponseToPayment bindCardCallback(HttpServletRequest request, PaymentResponse paymentResponse){
        if (TYPE_BIND_CARD.equals(paymentResponse.getResponseType()) &&
                PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo().equals(paymentResponse.getCode())) {
            BindCardCallback cardCallback = JSON.parseObject(paymentResponse.getResponseParameters(), BindCardCallback.class);
            if(null == cardCallback) return null;
            UserBank bank = userService.bankCardByOrderNumber(cardCallback.getOrderNo());
            if(null == bank) return null;
            //查询银行卡信息
            QueryBankCardBinRequest queryBankCardBinRequest = new QueryBankCardBinRequest();
            queryBankCardBinRequest.setCardNo(bank.getBankNumber());
            PaymentResponse<QueryBankCardBinResponse> queryResponse = paymentService.queryBankCardBin(queryBankCardBinRequest);

            if (null != queryResponse && queryResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
                UserBank update = new UserBank();
                update.setId(bank.getId());
                update.setBankId(queryResponse.getData().getBankCode());
                update.setCardType(queryResponse.getData().getCardType());
                update.setBankAddress(queryResponse.getData().getBankName());
                update.setState(UserEnum.BANK_IN_USED.getState());
                userService.saveBankCard(update);
            }

            return new CallBackResponseToPayment("000000","success");
        }
        return null;
    }







}
