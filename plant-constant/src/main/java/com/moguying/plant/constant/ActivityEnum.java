package com.moguying.plant.constant;

public enum ActivityEnum {

    FREE_SEED_30DAY(18,"30天菌包"),
    WINE_FERTILIZER(1,"5折酒水满减券"),
    MALL_FOOL_FERTILIZER(1,"商城食品满减券"),

    START_ACTIVITY(1,"2019-11-18 00:00:00"),
    MONEY_5RMB(1,"5元现金红包券"),
    PLANT_FERTILIZER_95RMB(1,"95元种植券"),

    LOTTERY_KEY_PRE(1,"plant:lottery:");

    private Integer state;
    private String message;

    ActivityEnum(Integer state,String message) {
        this.state = state;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
