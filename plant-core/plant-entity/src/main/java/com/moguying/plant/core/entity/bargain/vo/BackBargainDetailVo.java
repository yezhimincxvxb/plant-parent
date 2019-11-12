package com.moguying.plant.core.entity.bargain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MallEnum;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.DateUtil;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class BackBargainDetailVo {

    /**
     * 订单详情id
     */
    @JSONField(ordinal = 1)
    private Integer orderId;

    /**
     * 手机号
     */
    @JSONField(ordinal = 2, serializeUsing = IdCardSerialize.class)
    private String phone;

    /**
     * 商品名称
     */
    @JSONField(ordinal = 3)
    private String productName;

    /**
     * 商品价格
     */
    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productPrice;

    /**
     * 已砍价格
     */
    @JSONField(ordinal = 5, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal bargainPrice;

    /**
     * 已砍进度
     */
    @JSONField(ordinal = 6)
    private String bargainProgress;

    /**
     * 砍成人数
     */
    @JSONField(ordinal = 7)
    private Integer bargainCount;

    /**
     * 参与人数
     */
    @JSONField(ordinal = 8)
    private Integer bargainNumber;

    /**
     * 结束时间
     */
    @JSONField(ordinal = 9, format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 剩余时间
     */
    @JSONField(ordinal = 10)
    private String leftTime;

    /**
     * 收货地址
     */
    @JSONField(ordinal = 11)
    private String address;

    /**
     * 状态
     */
    @JSONField(ordinal = 12)
    private Integer state;

    /**
     * 订单状态
     */
    @JSONField(ordinal = 13)
    private String orderState;

    /**
     * 发货状态
     */
    @JSONField(ordinal = 14)
    private String sendState;

    /**
     * 帮砍用户
     */
    @JSONField(ordinal = 15)
    private List<BargainVo> users;

    public String getBargainProgress() {
        if (Objects.nonNull(productPrice) && Objects.nonNull(bargainPrice)) {
            DecimalFormat decimalFormat = new DecimalFormat("#0%");
            BigDecimal decimal = bargainPrice.divide(productPrice, 2, BigDecimal.ROUND_DOWN);
            return decimalFormat.format(decimal);
        }
        return bargainProgress;
    }

    public String getLeftTime() {
        if (Objects.nonNull(closeTime)) {
            if (closeTime.getTime() - System.currentTimeMillis() > 0) {
                long millis = closeTime.getTime() - System.currentTimeMillis();
                return DateUtil.INSTANCE.longToString(millis);
            } else {
                return "0";
            }
        }
        return leftTime;
    }

    public String getAddress() {
        if (Objects.isNull(address)) {
            return "无";
        }
        return address;
    }

    public String getOrderState() {
        if (Objects.nonNull(state) && state >= MallEnum.ORDER_HAS_PAY.getState()) {
            return "已提交";
        }
        return "未提交";
    }

    public String getSendState() {
        if (Objects.isNull(state)) {
            return "无";
        }

        if (state.equals(MallEnum.ORDER_HAS_PAY.getState())) {
            return MallEnum.ORDER_HAS_PAY.getStateStr();
        } else if (state.equals(MallEnum.ORDER_HAS_SEND.getState())) {
            return MallEnum.ORDER_HAS_SEND.getStateStr();
        }
        return "无";
    }
}
