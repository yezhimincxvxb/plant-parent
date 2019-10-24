package com.moguying.plant.core.service.account;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyWithdraw;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.payment.response.SendWithdrawSmsCodeResponse;
import com.moguying.plant.core.entity.payment.response.WithdrawMoneyResponse;

import javax.servlet.http.HttpServletRequest;

public interface MoneyWithdrawService {
    @DataSource("read")
    PageResult<MoneyWithdraw> moneyWithdrawList(Integer page, Integer size, MoneyWithdraw where);

    @DataSource("write")
    ResultData<PaymentResponse> reviewMoneyWithdraw(Integer id, Integer state, Integer verifyUserId);

    @DataSource("write")
    ResultData<PaymentResponse> reviewMoneyWithdraw(Integer id, Integer state);

    @DataSource("write")
    ResultData<Integer> addMoneyWithdraw(MoneyWithdraw moneyWithdraw, Integer bankId);

    @DataSource("read")
    MoneyWithdraw selectById(Integer id);

    @DataSource("read")
    MoneyWithdraw selectByOrderNumber(String orderNumber);

    @DataSource("write")
    ResultData<PaymentResponse<SendWithdrawSmsCodeResponse>> sendWithdrawSms(MoneyWithdraw moneyWithdraw);

    @DataSource("write")
    ResultData<PaymentResponse<WithdrawMoneyResponse>> withdrawToAccount(MoneyWithdraw moneyWithdraw, String smsCode);

    @DataSource("read")
    PageResult<MoneyWithdraw> apiMoneyWithdrawList(Integer page, Integer size, MoneyWithdraw where);

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<MoneyWithdraw> search, HttpServletRequest request);
}
