package com.moguying.plant.core.entity.feedback;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackType {

    /**
     * 溯源分类标题
     */
    private String type;

    /**
     * 是否为流程显示，否为图片轮播
     */
    @JSONField(name = "isFlow")
    private boolean isFlow;

    /**
     * 资源列表
     */
    private List<ItemContent> itemContents;
}
