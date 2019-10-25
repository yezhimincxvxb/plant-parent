package com.moguying.plant.core.entity.payment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_payment_info")
@Data
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = -7330965500749750087L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 请求方法
     */
    @TableField
    private String requestAction;

    /**
     * 请求参数
     */
    @TableField
    private String paymentRequest;

    /**
     * 请求响应
     */
    @TableField
    private String paymentResponse;

    /**
     * 异步请求响应
     */
    @TableField
    private String notifyResponse;

    /**
     * 签名数据
     */
    @TableField
    private String signData;

    /**
     * 处理状态[1已处理，0未处理]
     */
    @TableField
    private Integer state;

    /**
     * 支付流水号
     */
    @TableField
    private String orderNumber;

    /**
     * 添加时间
     */
    @TableField
    private Date addTime;


}