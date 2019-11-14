package com.moguying.plant.core.entity.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user
 * @author 
 */
@TableName("plant_user")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 6900803451538227091L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户手机号
     */
    @Excel(name = "手机号")
    @JSONField(ordinal = 2)
    @TableField
    private String phone;

    /**
     * 用户姓名
     */
    @Excel(name = "姓名")
    @JSONField(ordinal = 3)
    @TableField
    private String realName;

    /**
     * 是否实名
     */
    @Excel(name = "是否实名",replace = {"是_1","否_2"})
    @JSONField(ordinal = 4)
    @TableField
    private Integer isRealName;


    /**
     * 是否长期有效
     */
    @JSONField(ordinal = 5)
    @TableField
    private Boolean isLongTime;


    /**
     * 身份证有效期
     */
    @JSONField(serialize = false,ordinal = 6)
    @TableField
    private Date idExpireTime;


    /**
     * 是否绑卡
     */
    @Excel(name = "是否绑卡",replace = {"是_true","否_false"})
    @JSONField(ordinal = 7)
    @TableField
    private Boolean isBindCard;

    /**
     * 登录密码
     */
    @JSONField(serialize = false)
    @TableField
    private String password;

    /**
     * 支付密码
     */
    @JSONField(serialize = false)
    @TableField
    private String payPassword;

    /**
     * 头像id
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer avatarsId;

    /**
     * 身份证号码
     */
    @JSONField(serializeUsing = IdCardSerialize.class,ordinal = 9)
    @TableField
    private String idCard;

    /**
     * 注册时间
     */
    @Excel(name = "注册时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(deserialize =  false,format = "yyyy-MM-dd HH:mm",ordinal = 10)
    @TableField
    private Date regTime;

    /**
     * 注册ip
     */
    @JSONField(deserialize = false,serialize =  false,ordinal = 11)
    @TableField
    private String regIp;

    /**
     * 注册来源
     */
    @JSONField(deserialize =  false,ordinal = 12)
    @TableField
    private String regSource;

    /**
     * 注册邀请码
     */
    @JSONField(ordinal = 13)
    @TableField
    private String inviteCode;

    /**
     * 邀请人用户id
     */
    @JSONField(serialize =  false,deserialize =  false,ordinal = 14)
    @TableField
    private Integer inviteUid;

    /**
     * 邀请人用户名
     */
    @Excel(name = "邀请人")
    @JSONField(ordinal = 15)
    @TableField(exist = false)
    private String inviteName;

    /**
     * 用户状态[0已冻结，1已使用]
     */
    @JSONField(ordinal = 16)
    @TableField
    private Boolean userState;

    /**
     * 最后登录时间
     */
    @JSONField(serialize =  false,deserialize = false,ordinal = 17)
    @TableField
    private Date lastLoginTime;

    /**
     * 支付子商户号
     */
    @JSONField(ordinal = 18)
    @TableField
    private String paymentAccount;

    /**
     * 子商户号状态
     */
    @JSONField(ordinal = 19)
    @TableField
    private Integer paymentState;


    /**
     * 是否为渠道商
     */
    @JSONField(ordinal = 20)
    @TableField
    private Boolean isChannel;


    /**
     * 搜索辅助字段
     */
    @TableField(exist = false)
    private Date startTime;

    /**
     * 搜索辅助字段
     */
    @TableField(exist = false)
    private Date endTime;

    public User(Integer id) {
        this.id = id;
    }

    public User(String phone, String realName) {
        this.phone = phone;
        this.realName = realName;
    }

    public User(String phone){
        this(phone,null);
    }
}