package com.moguying.plant.core.entity.taste;

import lombok.Data;

import java.util.List;

@Data
public class PopMessage {

    private String id;

    /**
     * 主题名
     */
    private String theme;

    /**
     * 主题icon
     */
    private String icon;


    /**
     * 主题信息
     */
    private List<String> popMessages;


    /**
     * 是否选用此弹幕
     */
    private boolean isUsed;
}
