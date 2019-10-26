package com.moguying.plant.core.service.block;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.common.vo.HomeBlock;

import java.util.List;

public interface BlockService {

    PageResult<Block> blockList(Integer page, Integer limit, Block where);

    ResultData<Integer> addBlock(Block block);

    ResultData<Integer> updateBlock(Integer id, Block update);

    ResultData<Integer> deleteBlock(Integer id);

    ResultData<Block> blockInfo(Integer id);

    Boolean seeBlock(Integer id);

    List<HomeBlock> blockListForHome();

    Block findBlockBySeedType(Integer seedTypeId);
}
