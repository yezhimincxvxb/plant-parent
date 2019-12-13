package com.moguying.plant.core.entity.feedback;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackItem {

    private String _id;

    /**
     * 溯源类型
     */
    private String feedbackType;

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
