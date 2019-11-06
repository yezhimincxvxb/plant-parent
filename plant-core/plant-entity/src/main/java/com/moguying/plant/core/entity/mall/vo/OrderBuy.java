package com.moguying.plant.core.entity.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderBuy {

    /**
     * 商品集合
     */
    private List<BuyProduct> products;

    /**
     * 券id
     */
    private Integer fertilizerId;

}
