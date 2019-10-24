package com.moguying.plant.core.entity.common.vo;

import lombok.Data;

@Data
public class CapitalPage {

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
