package com.moguying.plant.core.entity.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user
 * @author 
 */
@Data
public class User implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 用户手机号
     */
    @Excel(name = "手机号")
    @JSONField(ordinal = 2)
    private String phone;

    /**
     * 用户姓名
     */
    @Excel(name = "姓名")
    @JSONField(ordinal = 3)
    private String realName;

    /**
     * 是否实名
     */
    @Excel(name = "是否实名",replace = {"是_1","否_2"})
    @JSONField(ordinal = 4)
    private Integer isRealName;


    /**
     * 是否长期有效
     */
    @JSONField(ordinal = 5)
    private Boolean isLongTime;


    /**
     * 身份证有效期
     */
    @JSONField(serialize = false,ordinal = 6)
    private Date idExpireTime;


    /**
     * 是否绑卡
     */
    @Excel(name = "是否绑卡",replace = {"是_true","否_false"})
    @JSONField(ordinal = 7)
    private Boolean isBindCard;

    /**
     * 登录密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 支付密码
     */
    @JSONField(serialize = false)
    private String payPassword;

    /**
     * 头像id
     */
    @JSONField(ordinal = 8)
    private Integer avatarsId;

    /**
     * 身份证号码
     */
    @JSONField(serializeUsing = IdCardSerialize.class,ordinal = 9)
    private String idCard;

    /**
     * 注册时间
     */
    @Excel(name = "注册时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(deserialize =  false,format = "yyyy-MM-dd HH:mm",ordinal = 10)
    private Date regTime;

    /**
     * 注册ip
     */
    @JSONField(deserialize = false,serialize =  false,ordinal = 11)
    private String regIp;

    /**
     * 注册来源
     */
    @JSONField(deserialize =  false,ordinal = 12)
    private String regSource;

    /**
     * 注册邀请码
     */
    @JSONField(serialize =  false,deserialize =  false,ordinal = 13)
    private String inviteCode;

    /**
     * 邀请人用户id
     */
    @JSONField(serialize =  false,deserialize =  false,ordinal = 14)
    private Integer inviteUid;

    /**
     * 邀请人用户名
     */
    @Excel(name = "邀请人")
    @JSONField(ordinal = 15)
    private String inviteName;

    /**
     * 用户状态[0已冻结，1已使用]
     */
    @JSONField(ordinal = 16)
    private Boolean userState;

    /**
     * 最后登录时间
     */
    @JSONField(serialize =  false,deserialize = false,ordinal = 17)
    private Date lastLoginTime;

    /**
     * 支付子商户号
     */
    @JSONField(ordinal = 18)
    private String paymentAccount;

    /**
     * 子商户号状态
     */
    @JSONField(ordinal = 19)
    private Integer paymentState;


    /**
     * 是否为渠道商
     */
    @JSONField(ordinal = 20)
    private Boolean isChannel;


    /**
     * 搜索辅助字段
     */
    private Date startTime;

    /**
     * 搜索辅助字段
     */
    private Date endTime;



    public User() {
    }

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