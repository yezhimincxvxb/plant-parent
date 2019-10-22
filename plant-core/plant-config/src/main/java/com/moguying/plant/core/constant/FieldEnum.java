package com.moguying.plant.core.constant;

public enum FieldEnum {
    AFFECT_MONEY("affect_money"),
    INVITE_AWARD("invite_award"),
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
