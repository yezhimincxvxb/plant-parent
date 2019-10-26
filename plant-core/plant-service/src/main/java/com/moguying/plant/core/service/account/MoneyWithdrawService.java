package com.moguying.plant.core.service.account;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyWithdraw;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.payment.response.SendWithdrawSmsCodeResponse;
import com.moguying.plant.core.entity.payment.response.WithdrawMoneyResponse;

import javax.servlet.http.HttpServletRequest;

public interface MoneyWithdrawService {
    PageResult<MoneyWithdraw> moneyWithdrawList(Integer page, Integer size, MoneyWithdraw where);

    ResultData<PaymentResponse> reviewMoneyWithdraw(Integer id, Integer state, Integer verifyUserId);

    ResultData<PaymentResponse> reviewMoneyWithdraw(Integer id, Integer state);

    ResultData<Integer> addMoneyWithdraw(MoneyWithdraw moneyWithdraw, Integer bankId);

    MoneyWithdraw selectById(Integer id);

    MoneyWithdraw selectByOrderNumber(String orderNumber);

    ResultData<PaymentResponse<SendWithdrawSmsCodeResponse>> sendWithdrawSms(MoneyWithdraw moneyWithdraw);

    ResultData<PaymentResponse<WithdrawMoneyResponse>> withdrawToAccount(MoneyWithdraw moneyWithdraw, String smsCode);

    PageResult<MoneyWithdraw> apiMoneyWithdrawList(Integer page, Integer size, MoneyWithdraw where);

    void downloadExcel(Integer userId, PageSearch<MoneyWithdraw> search, HttpServletRequest request);
}
