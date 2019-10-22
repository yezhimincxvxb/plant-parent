package com.moguying.plant.core.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户-蘑菇币日志类
 */
@Data
public class SaleCoinLog implements Serializable {

    private static final long serialVersionUID = -4454040989808070649L;

    /**
     * 日志ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 蘑菇币
     */
    private Integer affectCoin;

    /**
     * 类型
     */
    private Integer affectType;

    /**
     * ID集合
     */
    private String affectDetailId;
}
