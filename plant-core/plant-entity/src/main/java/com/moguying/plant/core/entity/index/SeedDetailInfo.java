package com.moguying.plant.core.entity.index;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SeedDetailInfo {

    /*** 菌包id */
    @JSONField(serialize = false)
    private Integer id;

    /*** 菌包类型 */
    @JSONField(serialize = false)
    private Integer type;

    /*** 购买数量 */
    @JSONField(ordinal = 1)
    private Integer buyCount;

    /*** 种植数量 */
    @JSONField(ordinal = 2)
    private Integer plantCount;

    /*** 采摘数量 */
    @JSONField(ordinal = 3)
    private Integer pickCount;

    /*** 出售数量 */
    @JSONField(ordinal = 4)
    private Integer sellCount;
}
