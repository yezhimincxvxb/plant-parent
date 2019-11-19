package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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
     * 手机号
     */
    @JSONField(ordinal = 2)
    private String phone;

    /**
     * 邀请人手机号
     */
    @JSONField(ordinal = 3)
    private String invitePhone;

    /**
     * 奖品名称
     */
    @JSONField(ordinal = 4)
    private String prizeName;

    /**
     * 发奖状态[0-未发奖、1-已发奖]
     */
    @JSONField(ordinal = 5)
    private Boolean state;

    /**
     * 领奖时间
     */
    @JSONField(ordinal = 6, format = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    // 辅助字段

    /**
     * 菌包名称
     */
    @JSONField(serialize = false)
    private String seedName;

    /**
     * 商品名称
     */
    @JSONField(serialize = false)
    private String productName;

    /**
     * 券名称
     */
    @JSONField(serialize = false)
    private String fertilizerName;

    /**
     * 券的最小使用金额
     */
    @JSONField(serialize = false, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMin;

    /**
     * 券的满减金额
     */
    @JSONField(serialize = false, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal fertilizerAmount;

    /**
     * 开始时间
     */
    @JSONField(serialize = false, format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JSONField(serialize = false, format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public String getPrizeName() {
        if (Objects.nonNull(seedName)) {
            if (Objects.isNull(invitePhone)) {
                return "登录：" + seedName;
            }
            return "邀请：" + seedName;
        }

        if (Objects.nonNull(productName)) {
            return productName;
        }

        if (Objects.nonNull(fertilizerName)) {
            return "满" + amountMin + "减" + fertilizerAmount + fertilizerName;
        }
        return prizeName;
    }
}
