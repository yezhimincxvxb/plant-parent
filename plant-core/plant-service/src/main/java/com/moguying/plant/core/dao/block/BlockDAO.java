package com.moguying.plant.core.dao.block;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.common.vo.HomeBlock;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BlockDAO继承基类
 */
@Repository
public interface BlockDAO extends BaseMapper<Block> {
    List<Block> selectSelective(Block where);
    Block selectBlockByNumber(String number);
    Integer blockIsFree(Integer id);
    List<HomeBlock> selectBlockListForHome();
    Block selectById(Integer id);
    Block selectBySeedType(Integer seedType);
}