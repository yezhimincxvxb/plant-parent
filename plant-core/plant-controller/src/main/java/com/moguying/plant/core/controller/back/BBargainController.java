package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.bargain.BargainRate;
import com.moguying.plant.core.entity.bargain.vo.BackBargainDetailVo;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.mall.MallProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/bargain")
@Slf4j
@Api(tags = "砍价管理")
public class BBargainController {

    @Autowired
    private BargainDetailService bargainDetailService;

    @Autowired
    private MallProductService productService;

    @Value("${excel.download.dir}")
    private String downloadDir;

    /**
     * 商品推送到砍价列表
     */
    @PostMapping("/push")
    @ApiOperation("商品推送到砍价列表")
    public ResponseData<Integer> productToBargain(@RequestBody BargainRate bargain) {
        ResponseData<Integer> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        if (Objects.isNull(bargain) || Objects.isNull(bargain.getProductId()))
            return responseData;
        Integer result = productService.updateProductToBargain(bargain);
        if (result > 0)
            return responseData
                    .setMessage(MessageEnum.SUCCESS.getMessage())
                    .setState(MessageEnum.SUCCESS.getState());
        return responseData;
    }


    @PostMapping("/list")
    @ApiOperation("砍价详情列表")
    public PageResult<BackBargainDetailVo> bargainList(@RequestBody PageSearch<BargainVo> search) {
        return bargainDetailService.bargainList(search.getPage(), search.getSize(), search.getWhere());
    }


    @PostMapping("/excel")
    @ApiOperation("砍价详情列表下载")
    public ResponseData<Integer> bargainListExcel(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                                  @RequestBody PageSearch<BargainVo> search,
                                                  HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("砍价列表", request.getServletContext(), user.getId(), downloadDir);
        PageResult<BackBargainDetailVo> pageResult = bargainDetailService.bargainList(search.getPage(), search.getSize(), search.getWhere());
        new Thread(new DownloadService<>(pageResult.getData(), BackBargainDetailVo.class, downloadInfo)).start();
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }
}
