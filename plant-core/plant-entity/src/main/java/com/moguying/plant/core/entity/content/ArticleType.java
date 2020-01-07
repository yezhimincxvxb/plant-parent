package com.moguying.plant.core.entity.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("plant_article_type")
public class ArticleType implements Serializable {

    private static final long serialVersionUID = -1035921877757550099L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private Integer state;

    /**
     * 分类名称
     */
    @TableField
    private String name;

    /**
     * 链接跳转名英文
     */
    @TableField
    private String urlName;

    /**
     * 排序
     */
    @TableField
    private Integer orderNumber;

}