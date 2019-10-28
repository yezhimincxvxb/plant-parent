package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.content.Nav;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NavDAO继承基类
 */
@Repository
public interface NavDAO extends BaseDAO<Nav> {
    List<Nav> selectSelective(Nav where);
}