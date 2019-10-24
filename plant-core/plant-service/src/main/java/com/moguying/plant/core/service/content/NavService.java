package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Nav;

public interface NavService {
    PageResult<Nav> navList(Integer page, Integer size, Nav where);
    Integer addNav(Nav nav);
    Integer deleteNav(Integer id);
    Nav nav(Integer id);
    Integer updateNave(Integer id, Nav nav);
}
