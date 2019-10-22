package com.moguying.plant.core.entity.dto;

import java.io.Serializable;

public class SeedReview  implements Serializable {

    private Boolean state;

    private String mark;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
