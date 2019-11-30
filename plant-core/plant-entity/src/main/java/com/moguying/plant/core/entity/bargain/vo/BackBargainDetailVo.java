package com.moguying.plant.core.entity.bargain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MallEnum;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.DateUtil;
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
    @Excel(name = "订单详情id")
    @JSONField(ordinal = 1)
    private Integer orderId;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @JSONField(ordinal = 2)
    private String phone;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    @JSONField(ordinal = 3)
    private String productName;

    /**
     * 商品价格
     */
    @Excel(name = "商品价格")
    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productPrice;

    /**
     * 已砍价格
     */
    @JSONField(ordinal = 5, serialize = false, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal bargainPrice;

    /**
     * 已砍进度
     */
    @Excel(name = "已砍进度")
    @JSONField(ordinal = 6)
    private String bargainProgress;

    /**
     * 砍成人数
     */
    @Excel(name = "砍成人数")
    @JSONField(ordinal = 7)
    private Integer bargainCount;

    /**
     * 参与人数
     */
    @Excel(name = "参与人数")
    @JSONField(ordinal = 8)
    private Integer bargainNumber;

    /**
     * 剩余时间
     */
    @Excel(name = "剩余时间")
    @JSONField(ordinal = 9)
    private String leftTime;

    /**
     * 开始砍价时间
     */
    @Excel(name = "开始砍价时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 10, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 砍成时间
     */
    @Excel(name = "砍成时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss")
    private Date bargainTime;

    /**
     * 结束时间
     */
    @JSONField(ordinal = 12, serialize = false, format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    @JSONField(ordinal = 13)
    private String address;

    /**
     * 状态
     */
    @JSONField(ordinal = 14, serialize = false)
    private Integer state;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    @JSONField(ordinal = 15)
    private String orderState;

    /**
     * 发货状态
     */
    @Excel(name = "发货状态")
    @JSONField(ordinal = 16)
    private String sendState;

    /**
     * 商品添加时间
     */
    @Excel(name = "商品添加时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 17, format = "yyyy-MM-dd HH:mm:ss")
    private Date productTime;

    /**
     * 帮砍用户
     */
    @ExcelCollection(name = "帮砍用户", type = BargainVo.class)
    @JSONField(ordinal = 18)
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
        } else if (state.equals(MallEnum.ORDER_HAS_DONE.getState())) {
            return MallEnum.ORDER_HAS_DONE.getStateStr();
        } else if (state.equals(MallEnum.ORDER_HAS_CLOSE.getState())) {
            return MallEnum.ORDER_HAS_CLOSE.getStateStr();
        } else if (state.equals(MallEnum.ORDER_HAS_CANCEL.getState())) {
            return MallEnum.ORDER_HAS_CANCEL.getStateStr();
        }
        return "无";
    }

    public Date getBargainTime() {
        if (Objects.nonNull(state)) {
            return bargainTime;
        }
        return null;
    }
}
