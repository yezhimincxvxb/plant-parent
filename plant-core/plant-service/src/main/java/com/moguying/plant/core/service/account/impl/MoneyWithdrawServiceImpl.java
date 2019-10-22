package com.moguying.plant.core.service.account.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.constant.*;
import com.moguying.plant.core.dao.account.MoneyWithdrawDAO;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.user.UserBankDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.entity.dto.payment.request.SendWithdrawSmsCodeRequest;
import com.moguying.plant.core.entity.dto.payment.request.TransferRequest;
import com.moguying.plant.core.entity.dto.payment.request.WithdrawMoneyRequest;
import com.moguying.plant.core.entity.dto.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.dto.payment.response.SendWithdrawSmsCodeResponse;
import com.moguying.plant.core.entity.dto.payment.response.TransferResponse;
import com.moguying.plant.core.entity.dto.payment.response.WithdrawMoneyResponse;
import com.moguying.plant.core.service.account.MoneyWithdrawService;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.utils.DateUtil;
import com.moguying.plant.utils.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MoneyWithdrawServiceImpl implements MoneyWithdrawService {

    @Autowired
    private MoneyWithdrawDAO moneyWithdrawDAO;

    @Autowired
    private UserBankDAO userBankDAO;

    @Autowired
    private UserMoneyService moneyService;

    @Autowired
    private UserMoneyDAO userMoneyDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PhoneMessageService phoneMessageService;

    @Value("${withdraw.min}")
    private String withdrawMin;

    @Value("${withdraw.max}")
    private String withdrawMax;

    @Value("${withdraw.daily.max}")
    private String withdrawDailyMax;

    @Value("${withdraw.fee}")
    private String withdrawFee;

    @Value("${excel.download.dir}")
    private String downloadDir;

    private static Map<Boolean, String> markMap;

    static {
        markMap = new HashMap<>();
        markMap.put(true, "审核通过");
        markMap.put(false, "审核不通过");
    }


    @Pagination
    @Override
    @DataSource("read")
    public PageResult<MoneyWithdraw> apiMoneyWithdrawList(Integer page, Integer size, MoneyWithdraw where) {
        moneyWithdrawDAO.selectiveForApi(where);
        return null;
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<MoneyWithdraw> moneyWithdrawList(Integer page, Integer size, MoneyWithdraw where) {
        moneyWithdrawDAO.selectSelective(where);
        return null;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> addMoneyWithdraw(MoneyWithdraw moneyWithdraw, Integer bankId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);

        if (moneyWithdraw.getWithdrawMoney().compareTo(new BigDecimal(withdrawMin)) < 0
            || moneyWithdraw.getWithdrawMoney().compareTo(new BigDecimal(withdrawMax)) > 0) {
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_MONEY_ERROR);
        }

        BigDecimal totalWithdraw = moneyWithdrawDAO.withdrawDailyCountByUserId(moneyWithdraw.getUserId(),
                DateUtil.INSTANCE.todayBegin(),DateUtil.INSTANCE.todayEnd());

        if(null == totalWithdraw)
            totalWithdraw = BigDecimal.ZERO;

        if(totalWithdraw.compareTo(new BigDecimal(withdrawDailyMax)) > 0)
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_MONEY_OUT_RANGE);

        UserMoney account = userMoneyDAO.selectById(moneyWithdraw.getUserId());
        if (account == null || account.getAvailableMoney().compareTo(moneyWithdraw.getWithdrawMoney()) < 0)
            return resultData.setMessageEnum(MessageEnum.USER_MONEY_NOT_ENOUGH);

        UserBank bank;
        if((bank = userBankDAO.bankInfoByUserIdAndId(moneyWithdraw.getUserId(),bankId)) == null)
            return resultData.setMessageEnum(MessageEnum.USER_BANK_CARD_NOT_EXISTS);
        moneyWithdraw.setBankNumber(bank.getBankNumber());
        moneyWithdraw.setBankPhone(bank.getBankPhone());
        moneyWithdraw.setWithdrawTime(new Date());
        moneyWithdraw.setOrderNumber(OrderPrefixEnum.WITHDRAW_ORDER.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        moneyWithdraw.setFee(new BigDecimal(withdrawFee));

        if (moneyWithdrawDAO.insert(moneyWithdraw) > 0) {
            UserMoneyOperator operator = new UserMoneyOperator();
            operator.setOperationId(moneyWithdraw.getOrderNumber());
            operator.setOpType(MoneyOpEnum.WITHDRAW);
            UserMoney money = new UserMoney(moneyWithdraw.getUserId());
            money.setAvailableMoney(moneyWithdraw.getWithdrawMoney().negate());
            money.setFreezeMoney(moneyWithdraw.getWithdrawMoney());
            operator.setUserMoney(money);
            if (moneyService.updateAccount(operator) == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData;
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(moneyWithdraw.getId());
        }

        return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<PaymentResponse> reviewMoneyWithdraw(Integer id, Integer state) {
        return reviewMoneyWithdraw(id,state,null);
    }

    @Override
    @DataSource("write")
    public ResultData<PaymentResponse> reviewMoneyWithdraw(Integer id, Integer state, Integer verifyUserId) {

        ResultData<PaymentResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);

        MoneyWithdraw withdraw = moneyWithdrawDAO.selectById(id);
        if (withdraw == null)
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_NOT_EXISTS);
        //初步审核
        MoneyWithdraw update = new MoneyWithdraw();
        update.setId(id);
        if(null != verifyUserId) {
            update.setVerifyUser(verifyUserId);
        }
        if (state.equals(MoneyStateEnum.WITHDRAW_SUCCESS.getState())) {
            if (withdraw.getState().equals(MoneyStateEnum.WITHDRAWING.getState())) {
                //提现记录更新
                update.setVerifyTime(new Date());
                update.setState(MoneyStateEnum.WITHDRAW_SUCCESS.getState());
                update.setVerifyMark(markMap.get(true));

                //转账
                User user = userDAO.userInfoById(withdraw.getUserId());
                update.setToAccountMoney(withdraw.getWithdrawMoney().subtract(withdraw.getFee()));
                TransferRequest transferRequest = new TransferRequest();
                transferRequest.setAmount(update.getToAccountMoney().toString());
                transferRequest.setCardNo("");
                transferRequest.setPayeeName(user.getRealName());
                transferRequest.setPayeeNo(user.getPaymentAccount());
                PaymentResponse<TransferResponse> paymentResponse = paymentService.transferAmount(transferRequest);
                if (null != paymentResponse && paymentResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())) {
                    InnerMessage message = new InnerMessage();
                    message.setPhone(user.getPhone());
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    message.setTime(sdf.format(new Date()));
                    phoneMessageService.sendOtherMessage(message, SystemEnum.PHONE_MESSAGE_WITHDRAW_SUCCESS_TYPE.getState());
                    resultData.setMessageEnum(MessageEnum.SUCCESS).setData(paymentResponse);
                } else if(null != paymentResponse) {
                    resultData.setMessageEnum(MessageEnum.ERROR).setData(paymentResponse);
                }
            }
        } else if(state.equals(MoneyStateEnum.WITHDRAW_FAILED.getState())) {
            //退还提现金额
            update.setState(MoneyStateEnum.WITHDRAW_FAILED.getState());
            update.setVerifyMark(markMap.get(false));
            UserMoneyOperator operator = new UserMoneyOperator();
            operator.setOpType(MoneyOpEnum.WITHDRAW_FAILED);
            operator.setOperationId(withdraw.getOrderNumber());
            UserMoney userMoney = new UserMoney(withdraw.getUserId());
            userMoney.setAvailableMoney(withdraw.getWithdrawMoney());
            userMoney.setFreezeMoney(withdraw.getWithdrawMoney().negate());
            operator.setUserMoney(userMoney);
            if(null != moneyService.updateAccount(operator))
                resultData.setMessageEnum(MessageEnum.SUCCESS);
        } else if(state.equals(MoneyStateEnum.WITHDRAW_IN_ACCOUNT.getState())) {
            update.setState(MoneyStateEnum.WITHDRAW_IN_ACCOUNT.getState());
            update.setSuccessTime(new Date());
            //减去提现金额
            UserMoneyOperator operator = new UserMoneyOperator();
            UserMoney userMoney = new UserMoney(withdraw.getUserId());
            operator.setOpType(MoneyOpEnum.WITHDRAW_DONE);
            operator.setOperationId(withdraw.getOrderNumber());
            userMoney.setFreezeMoney(withdraw.getWithdrawMoney().negate());
            operator.setUserMoney(userMoney);
            if (null != moneyService.updateAccount(operator)) {
                resultData.setMessageEnum(MessageEnum.SUCCESS);
            }
        }

        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS) && moneyWithdrawDAO.updateById(update) > 0) {
            //发送失败短信
            if(state.equals(MoneyStateEnum.WITHDRAW_FAILED.getState())){
                User user = userDAO.userInfoById(withdraw.getUserId());
                InnerMessage message = new InnerMessage();
                message.setPhone(user.getPhone());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                message.setTime(sdf.format(new Date()));
                phoneMessageService.sendOtherMessage(message, SystemEnum.PHONE_MESSAGE_WITHDRAW_FAIL_TYPE.getState());
            }
            return resultData;
        }
        return resultData;
    }

    @Override
    @DataSource("read")
    public MoneyWithdraw selectById(Integer id) {
        return moneyWithdrawDAO.selectById(id);
    }

    @Override
    @DataSource("read")
    public MoneyWithdraw selectByOrderNumber(String orderNumber) {
        return moneyWithdrawDAO.selectByOrderNumber(orderNumber);
    }

    @Override
    @DataSource("write")
    public ResultData<PaymentResponse<SendWithdrawSmsCodeResponse>> sendWithdrawSms(MoneyWithdraw moneyWithdraw) {
        ResultData<PaymentResponse<SendWithdrawSmsCodeResponse>> resultData = new ResultData<>(MessageEnum.ERROR,null);
        List<MoneyWithdraw> withdraws = moneyWithdrawDAO.selectSelective(moneyWithdraw);
        if(null == withdraws || withdraws.size() != 1 )
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_NOT_EXISTS);

        MoneyWithdraw withdraw = withdraws.get(0);
        if(!withdraw.getState().equals(MoneyStateEnum.WITHDRAW_SUCCESS.getState()))
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_NO_SUCCESS);
        User userInfo = userDAO.userInfoById(withdraw.getUserId());
        SendWithdrawSmsCodeRequest sendCodeRequest = new SendWithdrawSmsCodeRequest();
        sendCodeRequest.setWdMerNo(userInfo.getPaymentAccount());
        sendCodeRequest.setMerMerOrderNo(withdraw.getOrderNumber());
        sendCodeRequest.setAmount(withdraw.getToAccountMoney().toString());
        sendCodeRequest.setCardNo(withdraw.getBankNumber());
        sendCodeRequest.setPhone(withdraw.getBankPhone());
        PaymentResponse<SendWithdrawSmsCodeResponse> smsResponse = paymentService.sendWithdrawSmsCode(sendCodeRequest);
        if(null != smsResponse && smsResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())){

            if(smsResponse.getData().getMerMerOrderNo().equals(withdraw.getOrderNumber())) {
                MoneyWithdraw update = new MoneyWithdraw();
                update.setId(withdraw.getId());
                update.setSeqNo(smsResponse.getData().getSeqNo());
                if (moneyWithdrawDAO.updateById(update) <= 0) {
                    return resultData.setMessageEnum(MessageEnum.WITHDRAW_SEND_SMS_UPDATE_ERROR);
                }
            } else {
                return resultData.setMessageEnum(MessageEnum.WITHDRAW_PAYMENT_RETURN_NOT_MATCH);
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(smsResponse);
        } else if(null != smsResponse) {
            return resultData.setData(smsResponse);
        }
        return resultData;
    }


    @Override
    @DataSource("write")
    public ResultData<PaymentResponse<WithdrawMoneyResponse>> withdrawToAccount(MoneyWithdraw moneyWithdraw, String smsCode) {
        ResultData<PaymentResponse<WithdrawMoneyResponse>> resultData = new ResultData<>(MessageEnum.ERROR,null);
        List<MoneyWithdraw> withdraws = moneyWithdrawDAO.selectSelective(moneyWithdraw);
        if(null == withdraws || withdraws.size() != 1 )
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_NOT_EXISTS);
        MoneyWithdraw withdraw = withdraws.get(0);
        if(!withdraw.getState().equals(MoneyStateEnum.WITHDRAW_SUCCESS.getState()))
            return resultData.setMessageEnum(MessageEnum.WITHDRAW_NO_SUCCESS);
        User userInfo = userDAO.userInfoById(withdraw.getUserId());
        WithdrawMoneyRequest withdrawMoneyRequest = new WithdrawMoneyRequest();
        withdrawMoneyRequest.setWdMerNo(userInfo.getPaymentAccount());
        withdrawMoneyRequest.setMerMerOrderNo(withdraw.getOrderNumber());
        withdrawMoneyRequest.setAmount(withdraw.getToAccountMoney().toString());
        withdrawMoneyRequest.setCardNo(withdraw.getBankNumber());
        withdrawMoneyRequest.setPhone(withdraw.getBankPhone());
        withdrawMoneyRequest.setSeqNo(withdraw.getSeqNo());
        withdrawMoneyRequest.setSmsCode(smsCode);
        PaymentResponse<WithdrawMoneyResponse> withdrawResponse = paymentService.withdrawMoney(withdrawMoneyRequest);
        if(null != withdrawResponse && withdrawResponse.getCode().equals(PaymentStateEnum.RESPONSE_COMMON_SUCCESS.getStateInfo())){
            if(withdrawResponse.getData().getMerMerOrderNo().equals(withdraw.getOrderNumber()) &&
                    withdraw.getToAccountMoney().compareTo(new BigDecimal(withdrawResponse.getData().getAmount())) == 0) {
                MoneyWithdraw update = new MoneyWithdraw();
                update.setId(withdraw.getId());
                update.setState(MoneyStateEnum.WITHDRAW_ACCOUNT_ING.getState());
                if(moneyWithdrawDAO.updateById(update) <= 0) {
                    return resultData.setMessageEnum(MessageEnum.WITHDRAW_UPDATE_STATE_ERROR);
                }
            } else
                return resultData.setMessageEnum(MessageEnum.WITHDRAW_PAYMENT_RETURN_NOT_MATCH);
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(withdrawResponse);
        } else if(null != withdrawResponse)
            return resultData.setData(withdrawResponse);
        return resultData;
    }


    @DataSource("read")
    @Override
    public void downloadExcel(Integer userId, PageSearch<MoneyWithdraw> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("提现列表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadUtil<>(moneyWithdrawDAO, search, MoneyWithdraw.class, downloadInfo)).start();
    }
}
