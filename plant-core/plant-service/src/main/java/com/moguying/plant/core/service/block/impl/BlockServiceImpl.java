package com.moguying.plant.core.service.block.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moguying.plant.constant.BlockStateEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.block.vo.BlockDetail;
import com.moguying.plant.core.service.block.BlockService;
import com.moguying.plant.utils.InterestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BlockServiceImpl extends ServiceImpl<BlockDAO,Block> implements BlockService {
    
    @Autowired
    private BlockDAO blockDAO;


    @Override
    @DS("read")
    public PageResult<Block> blockList(Integer page, Integer size, Block where) {
        IPage<Block> pageResult = blockDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("write")
    public ResultData<Integer> addBlock(Block block) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,-1);
        Block useBlock = blockDAO.selectBlockByNumber(block.getNumber());
        if(useBlock != null)
            return resultData.setMessageEnum(MessageEnum.BLOCK_NUMBER_USED);
        block.setAddTime(new Date());

        BigDecimal[] result = block.getTotalAmount().divideAndRemainder(block.getPerPrice());
        if(result[1].compareTo(new BigDecimal("0")) != 0 )
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        if(block.getMinPlant() > block.getMaxPlant() || block.getMinPlant() <= 0 || block.getMaxPlant() > result[0].intValue())
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        block.setTotalCount(result[0].intValue());
        block.setLeftCount(result[0].intValue());

        if(blockDAO.insert(block) > 0) {
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(block.getId());
        }
        return resultData;
    }

    @Override
    @DS("write")
    public ResultData<Integer> updateBlock(Integer id, Block update) {

        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        //如果棚区已种植过，则不允许修改种植分类及生长周期
        int count = blockDAO.blockIsFree(id);
        if(count > 0){
            update.setSeedType(null);
            update.setGrowDays(null);
        }

        Block block = blockDAO.selectById(id);
        if(block == null)
            return resultData.setMessageEnum(MessageEnum.BLOCK_NOT_EXISTS);

        Block useBlock = blockDAO.selectBlockByNumber(update.getNumber());
        if(useBlock != null && !useBlock.getId().equals(id))
            return resultData.setMessageEnum(MessageEnum.BLOCK_NUMBER_USED);

        update.setId(block.getId());
        if(blockDAO.updateById(update) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(1);

        return resultData;
    }


    @Override
    @DS("write")
    public ResultData<Integer> deleteBlock(Integer id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        Block block = blockDAO.selectById(id);
        if(block == null)
            return resultData.setMessageEnum(MessageEnum.BLOCK_NOT_EXISTS);

        //存在已种植订单且未采摘的不可删除
        if(blockDAO.blockIsFree(id) > 0)
            return resultData.setMessageEnum(MessageEnum.BLOCK_CANNOT_DELETE);

        if(blockDAO.deleteById(id) > 0){
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(1);
        }
        return resultData;
    }

    @Override
    @DS("read")
    public ResultData<Block> blockInfo(Integer id) {
        ResultData<Block> resultData = new ResultData<>(MessageEnum.ERROR,null);
        Block block = blockDAO.selectById(id);
        if(block == null)
            return resultData.setMessageEnum(MessageEnum.BLOCK_NOT_EXISTS);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(block);
    }

    @Override
    @DS("write")
    public Boolean seeBlock(Integer id) {
        Block block = blockDAO.selectById(id);
        if(block == null)
            return false;
        Block update = new Block();
        update.setId(id);
        update.setIsShow(!block.getIsShow());
        if(blockDAO.updateById(update) > 0){
            return update.getIsShow();
        }
        return false;
    }


    @DS("read")
    @Override
    public List<BlockDetail> blockRecommend() {
        List<BlockDetail> blockDetails = blockDAO.blockRecommend();
        blockDetails.forEach((x)->{
            BigDecimal interest = InterestUtil.INSTANCE.calInterest(x.getSeedPrice(), x.getInterestRates(), x.getSeedGrowDays());
            x.setSaleAmount(x.getSeedPrice().add(interest));
            double percent = new Double(x.getHasCount()) / x.getTotalCount();
            x.setPlantPercent((int) (percent * 100));
        });
        return blockDetails;
    }

    @Override
    @DS("read")
    public Block findBlockBySeedType(Integer seedTypeId) {
        Block where = new Block();
        where.setSeedType(seedTypeId);
        where.setIsShow(true);
        where.setState(BlockStateEnum.OPEN.getState());
        List<Block> blocks = blockDAO.selectList(new QueryWrapper<>(where));
        if(null == blocks || blocks.isEmpty())
            return where;
        if(blocks.size() == 1)
            return blocks.get(0);
        where.setLeftCount(0);
        for(Block block : blocks){
            if(block.getLeftCount() > where.getLeftCount())
                where = block;
        }
        return where;
    }
}
