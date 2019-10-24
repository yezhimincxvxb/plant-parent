package com.moguying.plant.constant;

public enum PaymentStateEnum {

    //支付认证
    PAY_AUTH_TYPE("01",1),
    //支付签约
    PAY_SIGN_TYPE("02",2),
    //即时支付
    PAY_IN_TIME_TYPE("1",1),
    //预授权支付
    PAY_PRE_AUTH_TYPE("3",3),




    RESPONSE_DELETE_SUCCESS("16",16),
    RESPONSE_COMMON_SUCCESS("88",88),
    RESPONSE_REGISTER_SUCCESS("04",4),
    RESPONSE_USER_UPDATE_ERROR("响应数据更新失败",5),
    RESPONSE_VERIFY_ERROR("验签失败",3),
    RESPONSE_REGISTER_CUSTOMERNO_NOT_EXISTS("响应用户不存在",4),


    ORDER_HAS_PAY("1",1),
    ORDER_NEED_PAY("0",0);




    private String stateInfo;
    private Integer state;

    PaymentStateEnum(String stateInfo, Integer state) {
        this.stateInfo = stateInfo;
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
