package com.moguying.plant.core.constant;

public enum PaymentRequestUrlEnum {

    REALNAME_URL("/realName/noPage"),
    AUTH_AND_SIGN_URL("/interface/fastPayXy/authAndSign"),
    SEND_PAY_SMS_URL("/interface/fastPayXy/sendSmsCode"),
    PAY_URL("/interface/fastPayXy/pay"),
    IMAGE_UPLOAD_URL("/upload/img"),
    SEND_BIND_CARD_SMS("/bindcard/sendSmsCode"),
    BIND_CARD_URL("/bindcard/addForPer"),
    QUERY_BANK_CARD_BIN("https://qyfquery.95epay.com/query/bank/bankcardbin"),
    BIND_CARD_DELETE_URL("/bindcard/delete"),
    PAY_SIGN_QUERY_URL("/interface/fastPayXy/signQuery"),
    REGISTER_SEND_SMS("/account/sendSmsCode"),
    REGISTER_URL("/account/registerSpeedy"),
    MODIFY_PAY_PASSWORD("/pay/toSetPayPassword"),
    WITHDRAW_MONEY_TO_ACCOUNT_PAGE("/withdraw/index"),
    WITHDRAW_MONEY_TO_ACCOUNT("/withdraw/intfc"),
    TRANSFER_URL("/platformTransfer/toAcc"),
    SEND_WITHDRAW_SMS_URL("/withdraw/sendSms"),
    PAY_TO_PAY_HTML("/pay/toPayHtml");


//    private final String baseUrl = "http://shoudan.95epay.com:9000/api/api";
    private final String baseUrl = "https://qyfapi.95epay.com/api/api";
    private String url;

    PaymentRequestUrlEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return baseUrl + url;
    }

    public String getUrlWithoutBaseUrl(){
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
