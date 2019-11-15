package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserActivityLogVo implements Serializable {

    private static final long serialVersionUID = 8504984809825864414L;

    /**
     * id
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 发奖状态[0-未发奖、1-已发奖]
     */
    @JSONField(ordinal = 2)
    private Boolean state;

}
