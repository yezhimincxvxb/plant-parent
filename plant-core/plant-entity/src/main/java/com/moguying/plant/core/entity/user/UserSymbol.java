package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("plant_user_symbol")
public class UserSymbol {

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer userId;

    /**
     * 唯一标识
     */
    @JSONField(ordinal = 3)
    @TableField
    private String symbol;

    /**
     * 添加时间
     */
    @JSONField(ordinal = 4, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;
}
