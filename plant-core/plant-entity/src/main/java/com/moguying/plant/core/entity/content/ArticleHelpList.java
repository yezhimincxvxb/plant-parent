package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleHelpList implements Serializable {

    private static final long serialVersionUID = 8924130506405513194L;

    @JSONField(ordinal = 1)
    private String title;

    @JSONField(ordinal = 2)
    private List<ArticleHelp> list;

}