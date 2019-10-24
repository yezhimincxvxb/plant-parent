package com.moguying.plant.core.entity.mall.vo;

import java.io.Serializable;

public class CancelOrder implements Serializable {

    public CancelOrder() {
    }

    public CancelOrder(Integer id, String cancelReason) {
        this.id = id;
        this.cancelReason = cancelReason;
    }

    private Integer id;

    private String cancelReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
