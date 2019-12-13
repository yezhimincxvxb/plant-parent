package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.feedback.FeedbackMaterial;
import com.moguying.plant.core.service.feedback.material.FeedbackMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/material")
@Api(tags = "溯源素材管理")
public class BFeedbackMaterialController {

    @Autowired
    private FeedbackMaterialService resourceService;

    /**
     * 素材列表
     *
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("素材列表")
    @NoLogin
    public PageResult<FeedbackMaterial> findMaterialList(@RequestBody PageSearch<FeedbackMaterial> search) {
        return resourceService.findMaterialList(search.getPage(), search.getSize(), search.getWhere());
    }

    /**
     * 更新素材
     *
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("更新素材")
    @NoLogin
    public ResponseData<Integer> addMaterial(@RequestBody FeedbackMaterial feedbackMaterial) {
        ResultData<Integer> resultData = resourceService.addMaterial(feedbackMaterial);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 删除素材
     *
     * @return
     */
    @DeleteMapping("/param/{id}")
    @ApiOperation("删除素材")
    @NoLogin
    public ResponseData<Integer> deleteMaterial(@PathVariable Integer id) {
        if (null == id)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        FeedbackMaterial material = new FeedbackMaterial();
        material.setId(id);
        material.setIsDelete(true);
        ResultData<Boolean> resultData = resourceService.deleteMaterial(material);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


}
