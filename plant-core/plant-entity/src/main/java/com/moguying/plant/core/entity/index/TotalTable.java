package com.moguying.plant.core.entity.index;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.BigDecimalSignSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TotalTable {
    /*** 注册人数 */
    @JSONField(ordinal = 1)
    private Integer registerNum;
    /*** 实名人数 */
    @JSONField(ordinal = 2)
    private Integer realNameNum;
    /*** 注册转化率：实名数/注册数 */
    @JSONField(ordinal = 3, serializeUsing = BigDecimalSignSerialize.class)
    private BigDecimal registerRate;
    /*** 总购买菌包人数 */
    @JSONField(ordinal = 4)
    private Integer buySeedNum;
    /*** 总购买菌包份数 */
    @JSONField(ordinal = 5)
    private Integer buySeedCount;
    /*** 购买转化率：购买菌包人数/实名数 */
    @JSONField(ordinal = 6, serializeUsing = BigDecimalSignSerialize.class)
    private BigDecimal buyRate;
    /*** 总购买菌包价格 */
    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalBuyPrice;
    /*** 平均购买菌包价格：总购买菌包价值/总购买菌包人数 */
    @JSONField(ordinal = 8, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal avgBuyPrice;
    /*** 总种植人数 */
    @JSONField(ordinal = 9)
    private Integer plantNum;
    /*** 种植转化率：种植数/购买菌包数 */
    @JSONField(ordinal = 10, serializeUsing = BigDecimalSignSerialize.class)
    private BigDecimal plantRate;
    /*** 总支付分红(利息) */
    @JSONField(ordinal = 11, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal interest;
    /*** 种植中的资金 */
    @JSONField(ordinal = 12, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantAmount;
    /*** 总可用余额 */
    @JSONField(ordinal = 13, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal availableMoney;
    /*** 冻结金额 */
    @JSONField(ordinal = 14, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal freezeAmount;
    /*** 流动金额 */
    @JSONField(ordinal = 15, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal flowAmount;
    /*** 提现总额(审核成功) */
    @JSONField(ordinal = 16, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal withdrawalSuccess;
    /*** 提现总额(待审核) */
    @JSONField(ordinal = 17, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal withdrawalWait;
    /*** 资产总额：用户可用金额 + 种植中本金 + 利息 + 冻结资金 + 提现待审金额 */
    @JSONField(ordinal = 18, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal userAmount;
    /*** 电商平台订单数量 */
    @JSONField(ordinal = 19)
    private Integer mallOrderNum;
    /*** 电商平台订单人数 */
    @JSONField(ordinal = 20)
    private Integer mallOrderUserNum;
    /*** 电商平台下单金额 */
    @JSONField(ordinal = 21, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal mallOrderAmount;

}
