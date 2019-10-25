package com.moguying.plant.core.entity.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CapitalPage implements Serializable {

    private static final long serialVersionUID = 8602294761168228663L;
    /**
     * 第几页
     */
    private Integer page;

    /**
     * 每页数据量
     */
    private Integer size;

    /**
     * 搜索时间
     */
    private String dateTime;
}
