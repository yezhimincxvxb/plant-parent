package com.moguying.plant.core.entity.mall;

import lombok.Data;

import java.io.Serializable;

@Data
public class MallProductType implements Serializable {

    private static final long serialVersionUID = 5367086899627820318L;

    private Integer id;

    /**
     * 分类名称
     */
    private String typeName;

}