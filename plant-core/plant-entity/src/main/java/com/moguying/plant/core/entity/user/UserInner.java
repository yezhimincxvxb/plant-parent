package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_user_inner
 * @author 
 */
@TableName("plant_user_inner")
@Data
public class UserInner implements Serializable {

    private static final long serialVersionUID = -7473416402392681744L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @JSONField(name = "name")
    @TableField
    private String userName;

    @JSONField(name = "phone")
    @TableField
    private String userPhone;

    /**
     * 辅助字段
     * 内购份数
     */
    @JSONField(name = "count")
    @TableField(exist = false)
    private Integer innerCount;

}