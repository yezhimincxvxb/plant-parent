package com.moguying.plant.core.dao.block;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.block.vo.BlockDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BlockDAO继承基类
 */
@Repository
public interface BlockDAO extends BaseDAO<Block> {
    IPage<Block> selectSelective(Page<Block> page, @Param("wq") Block where);

    List<Block> selectSelective(@Param("wq") Block where);

    Block selectBlockByNumber(String number);

    Integer blockIsFree(Integer id);

    Block selectById(Integer id);

    Block selectBySeedType(Integer seedType);

    List<BlockDetail> blockRecommend();
}