package com.moguying.plant.core.entity.feedback;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackItem {

    private String id;

    /**
     * 溯源类型
     */
    private String feedbackType;


    /**
     * 溯源具体类型id[类型为大棚时，记录大棚id,其他类推]
     */
    private String feedbackTypeId;


    /**
     * 图片列表
     */
    private List<String> banners;

    /**
     * 简述
     */
    private DescribeInfo describeInfo;

    /**
     * 溯源列表
     */
    private List<FeedbackType> feedbackTypes;
}
