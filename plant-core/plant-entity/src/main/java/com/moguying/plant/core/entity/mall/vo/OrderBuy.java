package com.moguying.plant.core.entity.mall.vo;

import com.moguying.plant.core.entity.mall.vo.BuyProduct;

import java.util.List;

public class OrderBuy {

    List<BuyProduct> products;

    public List<BuyProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BuyProduct> products) {
        this.products = products;
    }
}
