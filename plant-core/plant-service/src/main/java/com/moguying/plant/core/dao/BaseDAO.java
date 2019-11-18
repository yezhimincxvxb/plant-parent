package com.moguying.plant.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface BaseDAO<T> extends BaseMapper<T> {
    List<T> selectSelective(T where);
}
