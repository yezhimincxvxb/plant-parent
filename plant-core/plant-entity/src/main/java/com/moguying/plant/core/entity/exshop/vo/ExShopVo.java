package com.moguying.plant.core.entity.exshop.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.exshop.ExShopPic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class ExShopVo implements Serializable {

    private static final long serialVersionUID = 6640680081823188570L;

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 体验店名称
     */
    @JSONField(ordinal = 2)
    private String shopName;

    /**
     * 体验店地址
     */
    @JSONField(ordinal = 3)
    private String address;

    /**
     * 城市
     */
    @JSONField(ordinal = 4)
    private String city;

    /**
     * 体验店电话
     */
    @JSONField(ordinal = 5)
    private String phone;

    /**
     * 体验店经纬度
     */
    @JSONField(ordinal = 6)
    private String location;

    /**
     * 经度
     */
    @JSONField(ordinal = 7)
    private String log;

    /**
     * 纬度
     */
    @JSONField(ordinal = 8)
    private String lat;

    /**
     * 体验店介绍
     */
    @JSONField(ordinal = 9)
    private String content;

    /**
     * 体验店营业时间
     */
    @JSONField(ordinal = 10)
    private String openTime;

    /**
     * 添加时间
     */
    @JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 体验店图片
     */
    @JSONField(ordinal = 12)
    private List<ExShopPic> pics;

    public String getLog() {
        if (Objects.nonNull(location)) {
            return location.split(",")[0];
        }
        return log;
    }

    public String getLat() {
        if (Objects.nonNull(location)) {
            return location.split(",")[1];
        }
        return lat;
    }
}
