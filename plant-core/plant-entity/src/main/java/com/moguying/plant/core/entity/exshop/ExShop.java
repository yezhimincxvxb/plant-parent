package com.moguying.plant.core.entity.exshop;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * plant_ex_shop
 * @author qinhir
 */
@Data
@TableName("plant_ex_shop")
@Accessors(chain = true)
public class ExShop implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 体验店名称
     */
    @TableField
    private String shopName;

    /**
     * 体验店地址
     */
    @TableField
    private String address;

    /**
     * 体验店城市
     */
    @TableField
    private String city;

    /**
     * 体验店电话
     */
    @TableField
    private String phone;

    /**
     * 体验店营业时间
     */
    @TableField
    private String openTime;

    /**
     * 添加时间
     */
    @TableField
    @JSONField(serialize = false)
    private Date addTime;

    /**
     * 体验店经纬度
     */
    @TableField
    private String location;

    /**
     * 体验店介绍
     */
    @TableField(exist = false)
    private ExShopContent content;


    /**
     * 体验店图片
     */
    @TableField(exist = false)
    private List<ExShopPic> pics;

}