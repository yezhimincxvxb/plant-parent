package com.moguying.plant.core.entity.feedback;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_feedback_material")
@Data
public class FeedbackMaterial implements Serializable {
    private static final long serialVersionUID = 5724805967700643470L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 文件名称
     */
    @JSONField(ordinal = 2)
    @TableField
    private String materialName;
    /**
     * 访问地址
     */
    @JSONField(ordinal = 3)
    @TableField
    private String materialPath;
    /**
     * 文件类型 [1视频，2图片]
     */
    @JSONField(ordinal = 4)
    @TableField
    private Integer materialType;
    /**
     * 文件后缀名
     */
    @JSONField(ordinal = 5)
    @TableField
    private String materialSuffix;
    /**
     * 上传时间
     */
    @JSONField(ordinal = 6,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date uploadTime;
    /**
     * 是否删除[0未删除，1已删除]
     */
    @JSONField(ordinal = 7)
    @TableField
    @TableLogic
    private Boolean isDelete;

}
