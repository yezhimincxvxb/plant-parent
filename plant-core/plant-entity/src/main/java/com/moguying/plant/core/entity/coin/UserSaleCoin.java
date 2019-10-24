package com.moguying.plant.core.entity.coin;

public class UserSaleCoin extends SaleCoinLog {

    private static final long serialVersionUID = 7372838098237390645L;

    /**
     * 用户蘑菇币详情
     */
    private SaleCoin saleCoin;

    public SaleCoin getSaleCoin() {
        return saleCoin;
    }

    public void setSaleCoin(SaleCoin saleCoin) {
        this.saleCoin = saleCoin;
    }
}
