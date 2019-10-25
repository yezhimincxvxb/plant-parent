package com.moguying.plant.core.entity.mall;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_express_com")
@Data
public class MallCompany implements Serializable {

    private static final long serialVersionUID = -1410608887952119443L;

    /**
     * 公司ID
     */
    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 公司名称
     */
    @TableField
    @JSONField(ordinal = 2)
    private String companyName;

    /**
     * 公司编码
     */
    @TableField
    @JSONField(ordinal = 3)
    private String companyCode;

    /**
     * 公司电话
     */
    @TableField
    @JSONField(ordinal = 4)
    private String companyPhone;
}
