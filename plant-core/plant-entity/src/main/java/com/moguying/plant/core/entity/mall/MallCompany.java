package com.moguying.plant.core.entity.mall;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class MallCompany implements Serializable {

    private static final long serialVersionUID = -1410608887952119443L;

    /**
     * 公司ID
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 公司名称
     */
    @JSONField(ordinal = 2)
    private String companyName;

    /**
     * 公司编码
     */
    @JSONField(ordinal = 3)
    private String companyCode;

    /**
     * 公司电话
     */
    @JSONField(ordinal = 4)
    private String companyPhone;
}
