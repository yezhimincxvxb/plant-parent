package com.moguying.plant.core.constant;

public enum SeedEnum {

    SEED_IS_FULL(2),//菌包已售罄


    NEED_REVIEW(0), //新添加菌包
    REVIEWED(1),    //菌包已审核并通过,筹集中
    PLANTED(2),     //菌包已种植
    REAP(3),        //菌包已收获
    CANCEL(4),      //菌包已取消

    SEED_ORDER_BUY(0), // 菌包订单未完成
    SEED_ORDER_PLANTED(1), // 菌包已完成

    SEED_PLANT_NOW(1), //成交日
    SEED_PLANT_NEXT_DAY(2), //T+1
    SEED_PLANT_DAY_AFTER_DAY(3), //T+2

    SEED_ORDER_DETAIL_NEED_PAY(0),
    SEED_ORDER_DETAIL_HAS_PAY(1),
    SEED_ORDER_DETAIL_HAS_CLOSE(2);
    private Integer state;

    SeedEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
