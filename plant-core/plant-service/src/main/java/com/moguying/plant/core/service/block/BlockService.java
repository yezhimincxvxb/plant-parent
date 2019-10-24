package com.moguying.plant.core.service.block;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.common.vo.HomeBlock;

import java.util.List;

public interface BlockService {

    @DataSource("read")
    PageResult<Block> blockList(Integer page, Integer limit, Block where);

    @DataSource("write")
    ResultData<Integer> addBlock(Block block);

    @DataSource("write")
    ResultData<Integer> updateBlock(Integer id, Block update);

    @DataSource("write")
    ResultData<Integer> deleteBlock(Integer id);

    @DataSource("read")
    ResultData<Block> blockInfo(Integer id);

    @DataSource("write")
    Boolean seeBlock(Integer id);

    @DataSource("read")
    List<HomeBlock> blockListForHome();

    @DataSource("read")
    Block findBlockBySeedType(Integer seedTypeId);
}
