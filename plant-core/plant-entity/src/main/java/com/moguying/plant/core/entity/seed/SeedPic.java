package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@TableName("plant_seed_pic")
@Data
@NoArgsConstructor
public class SeedPic implements Serializable {

    private static final long serialVersionUID = -8906441674900791421L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @JSONField(name = "name")
    @TableField
    @NonNull
    private String picName;

    @JSONField(name = "url")
    @TableField
    @NonNull
    private String picUrl;

    @TableField
    private String picUrlThumb;

    @TableField
    private Byte isDelete;

}