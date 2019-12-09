package com.moguying.plant.core.entity.feedback;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackType {

    /**
     * 溯源类型 seedPlant/checkInto/ofdcInfo
     */
    private String type;
    /**
     * 资源列表
     */
    private List<ItemContent> itemContents;
}
