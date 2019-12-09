package com.moguying.plant.core.entity.feedback;

import lombok.Data;

@Data
public class DescribeInfo {

    /**
     * 标题
     */
    private String title;

    /**
     * 大棚数
     */
    private String blockNum;

    /**
     * 面积
     */
    private String areaSize;
    /**
     * 位置地点
     */
    private String siteInfo;
    /**
     * 媒体素材链接
     */
    private String mediaUrl;

}
