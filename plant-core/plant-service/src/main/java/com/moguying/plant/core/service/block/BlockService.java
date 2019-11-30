package com.moguying.plant.core.service.block;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.block.vo.BlockDetail;

import java.util.List;

public interface BlockService extends IService<Block> {

    PageResult<Block> blockList(Integer page, Integer limit, Block where);

    ResultData<Integer> addBlock(Block block);

    ResultData<Integer> updateBlock(Integer id, Block update);

    ResultData<Integer> deleteBlock(Integer id);

    ResultData<Block> blockInfo(Integer id);

    Boolean seeBlock(Integer id);

    List<BlockDetail> blockRecommend();

    Block findBlockBySeedType(Integer seedTypeId);

    Block getOne();
}
