package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("plant_user_activity_log")
public class UserActivityLog implements Serializable {

    private static final long serialVersionUID = 8504984809825864414L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 流水号
     */
    @JSONField(ordinal = 2)
    @TableField
    private String number;

    /**
     * 名称
     */
    @JSONField(ordinal = 3)
    @TableField
    private String name;

    /**
     * 用户id
     */
    @JSONField(ordinal = 4)
    @TableField
    private Integer userId;

    /**
     * 好友id
     */
    @JSONField(ordinal = 5)
    @TableField
    private Integer friendId;

    /**
     * 商品id
     */
    @JSONField(ordinal = 6)
    @TableField
    private Integer productId;

    /**
     * 菌包id
     */
    @JSONField(ordinal = 7)
    @TableField
    private Integer seedTypeId;

    /**
     * 券id
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer fertilizerId;

    /**
     * 发奖状态[0-未发奖、1-已发奖]
     */
    @JSONField(ordinal = 9)
    @TableField
    private Boolean state;

    /**
     * 添加时间
     */
    @JSONField(ordinal = 10, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    /**
     * 发奖时间
     */
    @JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date receiveTime;

    /**
     * 手机号
     */
    @JSONField(ordinal = 12, serializeUsing = IdCardSerialize.class)
    @TableField(exist = false)
    private String phone;
}
