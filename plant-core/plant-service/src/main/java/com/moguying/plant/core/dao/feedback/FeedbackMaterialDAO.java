package com.moguying.plant.core.dao.feedback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.feedback.FeedbackMaterial;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * FeedbackResourceDAO继承基类
 */
@Repository
public interface FeedbackMaterialDAO extends BaseDAO<FeedbackMaterial> {
    IPage<FeedbackMaterial> selectSelective(Page<FeedbackMaterial> page, @Param("wq") FeedbackMaterial search);
}