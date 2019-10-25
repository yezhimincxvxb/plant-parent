package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserMoneyDate implements Serializable {

    private static final long serialVersionUID = -2798833303054364692L;

    /**
     * 日期
     */
    @JSONField(ordinal = 1)
    private String day;

    /**
     * 用户资金
     */
    @JSONField(ordinal = 2)
    private List<UserMoneyDetail> userMonies;

    public UserMoneyDate(String day, List<UserMoneyDetail> userMonies) {
        this.day = day;
        this.userMonies = userMonies;
    }
}
