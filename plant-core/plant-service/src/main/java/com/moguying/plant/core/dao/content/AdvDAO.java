package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.Adv;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdvDAO继承基类
 */
@Repository
public interface AdvDAO extends BaseMapper<Adv> {
    List<Adv> selectSelection(Adv adv);
}