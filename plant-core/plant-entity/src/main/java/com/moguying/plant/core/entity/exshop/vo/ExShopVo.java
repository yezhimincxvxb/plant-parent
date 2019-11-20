package com.moguying.plant.core.entity.exshop.vo;

import com.moguying.plant.core.entity.exshop.ExShopPic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ExShopVo {

    private Integer id;

    private List<ExShopPic> pics;
}
