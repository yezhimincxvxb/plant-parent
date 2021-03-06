package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.admin.AdminMenuMeta;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AdminMenuMetaDAO继承基类
 */
@Repository
public interface AdminMenuMetaDAO extends BaseDAO<AdminMenuMeta> {
    int deleteByMenuIds(List<Integer> ids);
}