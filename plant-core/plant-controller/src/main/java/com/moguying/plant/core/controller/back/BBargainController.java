package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.bargain.vo.BackBargainDetailVo;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bargain")
@Slf4j
public class BBargainController {

    @Autowired
    private BargainDetailService bargainDetailService;

    /**
     * 后台砍价详情列表
     */
    @PostMapping("/list")
    public PageResult<BackBargainDetailVo> bargainList(@RequestBody PageSearch<?> search) {
        return bargainDetailService.bargainList(search.getPage(), search.getSize());
    }
}
