package com.moguying.plant.constant;

public enum FieldEnum {
    AFFECT_MONEY("affect_money"),
    INVITE_AWARD("invite_award"),
    ACTIVITY_FERTILIZER("activity"),
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
