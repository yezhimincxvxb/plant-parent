package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Adv;

public interface AdvService {
    Integer addAdv(Adv adv);

    PageResult<Adv> advList(Integer page, Integer size, Adv adv);

    Integer deleteAdv(Integer id);

    Adv adv(Integer id);

    Integer updateAdv(Integer id, Adv adv);
}
