package com.moguying.plant.constant;

public enum FieldEnum {
    AFFECT_MONEY("affect_money"),
    INVITE_AWARD("invite_award"),
    ACTIVITY_FERTILIZER("activity"),
    WINE_FERTILIZER("activity_2"), // 5折酒水满减券
    MALL_FOOL_FERTILIZER("activity_3"), // 商城食物满减券
    MONEY5_FERTILIZER("activity_4"), // 5元现金红包
    SHARE("share"),
    FRIEND_HELP("friendHelp"),
    HELP_FRIEND("helpFriend"),
    PLANT_AMOUNT("plant_amount");
    private String field;

    FieldEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
