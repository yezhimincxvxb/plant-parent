package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.service.block.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/backEnd/block")
public class BBlockController {

    @Autowired
    BlockService blockService;

    @GetMapping(value = "/list")
    @ResponseBody
    public PageResult<Block> list(HttpServletRequest request) {
        int page = 1;
        int size = 10;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        if (request.getParameter("size") != null)
            size = Integer.parseInt(request.getParameter("size"));

        Block where = new Block();

        return blockService.blockList(page, size, where);
    }

    /**
     * 添加棚区
     * @param block
     * @return
     */
    @PostMapping(produces = "application/json")
    @ResponseBody
    public ResponseData<Integer> addBlock(@RequestBody Block block) {
        ResultData<Integer> resultData = blockService.addBlock(block);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 更新棚区
     * @param update
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<Integer> updateBlock(@RequestBody Block update, @PathVariable Integer id){
        ResultData<Integer> resultData = blockService.updateBlock(id,update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 删除一个棚区
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<Integer> deleteBlock(@PathVariable Integer id){
        ResultData<Integer> resultData = blockService.deleteBlock(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 获取棚区信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<Block> blockInfo(@PathVariable Integer id){
        ResultData<Block> resultData = blockService.blockInfo(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 设置是否显示
     * @param id
     * @return
     */
    @PutMapping(value = "/show/{id}")
    @ResponseBody
    public ResponseData<String> showBlock(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),blockService.seeBlock(id).toString());
    }







}
