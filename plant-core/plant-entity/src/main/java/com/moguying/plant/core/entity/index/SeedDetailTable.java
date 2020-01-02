package com.moguying.plant.core.entity.index;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SeedDetailTable {

    /*** 菌包id */
    @JSONField(ordinal = 1)
    private Integer id;

    /*** 菌包名称 */
    @JSONField(ordinal = 2)
    private String name;

    /*** 菌包类型 */
    @JSONField(serialize = false)
    private Integer type;

    /*** 菌包详情 */
    @JSONField(ordinal = 3)
    private SeedDetailInfo info;
}
