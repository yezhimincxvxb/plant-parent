package com.moguying.plant.core.entity.feedback;

import lombok.Data;

import java.util.Date;

@Data
public class ItemContent {
    /**
     * 操作类型
     */
    private Date plantTime;
    /**
     * 标题
     */
    private String title;
    /**
     * 操作描述
     */
    private String operationDescribe;
    /**
     * 媒体素材链接
     */
    private String mediaUrl;
}
