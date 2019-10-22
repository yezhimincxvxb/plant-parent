package com.moguying.plant.core.constant;

public enum MoneyOpEnum {

    RECHARGE(1,"充值冻结"),
    RECHARGE_DONE(2,"充值成功"),
    RECHARGE_FAILED(10,"充值失败"),
    WITHDRAW(3,"提现冻结"),
    WITHDRAW_DONE(4,"提现成功"), //支出

    BUY_SEED(5,"支付成功"),
    PLANTED_SEED(6,"订单种植"),

    SALE_REAP_SEED(15,"出售菌包"), //收入
    SALE_REAP_SEED_PROFIT(17,"出售收入"), //收入

    REAP_SEED_CAPITAL(7,"成本到账"),
    REAP_SEED_PROFIT(8,"收益到账"),

    BUY_CANCEL(9,"订单取消"),
    WITHDRAW_FAILED(11,"提现失败"),
    INVITE_AWARD(12,"邀请奖励"),        //收入
    BUY_MALL_PRODUCT(13,"购买商城商品"),  //支出
    BUY_SEED_ORDER(14,"购买菌包"),       //支出
    PANT_SEED_FERTILIZER(16,"种植券到账"), //收入

    MUSHROOM_COIN(18,"兑换蘑菇币"),
    RED_PACKAGE(19,"现金红包"); // 收入


    MoneyOpEnum(Integer type, String typeStr) {
        this.type = type;
        this.typeStr = typeStr;
    }

    private Integer type;

    private String typeStr;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
