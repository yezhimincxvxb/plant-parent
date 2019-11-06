package com.moguying.plant.core.entity.seed.vo;

import com.moguying.plant.core.entity.mall.vo.BuyProduct;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubmitOrder implements Serializable {

    private Integer addressId;

    private List<BuyProduct> products;

    private String mark;

    /**
     * 状态：1.使用现金支付；2.使用蘑菇币兑换
     */
    private Integer state;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 券id
     */
    private Integer fertilizerId;
}
