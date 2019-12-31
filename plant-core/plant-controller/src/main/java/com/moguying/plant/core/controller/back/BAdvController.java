package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.content.Adv;
import com.moguying.plant.core.entity.content.AdvType;
import com.moguying.plant.core.service.content.AdvService;
import com.moguying.plant.core.service.content.AdvTypeService;
import com.moguying.plant.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "内容管理")
public class BAdvController {


    @Autowired
    AdvTypeService advTypeService;

    @Autowired
    AdvService advService;

    @Value("${upload.host}")
    private String uploadHost;

    @Value("${upload.editor.host}")
    private String editorUploadHost;

    @Value("${upload.save-path}")
    private String uploadSavePath;

    private static Map<String, String> pathMap = new HashMap<>();

    static {
        pathMap.put("adv", "/images/adv/");
        pathMap.put("banner", "/images/banner/");
        pathMap.put("article", "/images/article/");
        pathMap.put("editor", "/images/editor/");
        pathMap.put("mall", "/images/mall/");
        pathMap.put("block", "/images/block/");
        pathMap.put("seed", "/images/seed/");
        pathMap.put("activity", "/images/activity/");
        pathMap.put("contract", "/images/contract/");
        pathMap.put("apk", "/images/apk/");
    }

    /**
     * 添加广告位置
     *
     * @param advType
     * @return
     */
    @PostMapping(value = "/adv/type")
    @ApiOperation("添加广告位置")
    public ResponseData<Integer> addAdvType(@RequestBody AdvType advType) {
        if (advTypeService.addAdvType(advType) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), advType.getId());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 获取广告位置列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/adv/type/list")
    @ApiOperation("获取广告位置列表")
    public PageResult<AdvType> advTypeList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return advTypeService.advTypeList(page, size, null);
    }


    /**
     * 获取单个广告位置信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/adv/type/{id}")
    @ApiOperation("获取单个广告位置信息")
    public ResponseData<AdvType> getAdvType(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), advTypeService.advType(id));
    }

    /**
     * 更新指定id的广告位置信息
     *
     * @param id
     * @param advType
     * @return
     */
    @PutMapping(value = "/adv/type/{id}")
    @ApiOperation("更新指定id的广告位置信息")
    public ResponseData<Integer> updateAdvType(@PathVariable Integer id, @RequestBody AdvType advType) {
        Integer result = advTypeService.updateAdvType(id, advType);
        if (result > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), result);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 添加广告信息
     *
     * @param adv
     * @return
     */
    @PostMapping(value = "/adv")
    @ApiOperation("添加广告信息")
    public ResponseData<Integer> addAdv(@RequestBody Adv adv) {
        if (advService.addAdv(adv) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), adv.getId());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 获取广告信息列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/adv/list")
    @ApiOperation("获取广告信息列表")
    public PageResult<Adv> advList(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                   @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        return advService.advList(page, size, null);
    }


    /**
     * 删除广告信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/adv/{id}")
    @ApiOperation("删除广告信息")
    public ResponseData<Integer> deleteAdv(@PathVariable Integer id) {
        Integer result = advService.deleteAdv(id);
        if (result > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 获取指定id的广告信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/adv/{id}")
    @ApiOperation("获取指定id的广告信息")
    public ResponseData<Adv> getAdv(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), advService.adv(id));
    }


    /**
     * 更新一个广告信息
     *
     * @param id
     * @param adv
     * @return
     */
    @PutMapping(value = "/adv/{id}")
    @ApiOperation("更新一个广告信息")
    public ResponseData<Integer> updateAdv(@PathVariable Integer id, @RequestBody Adv adv) {
        if (null == adv)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        Integer result = advService.updateAdv(id, adv);
        if (result > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), result);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    @PostMapping(value = "/upload/{savePath}")
    @ApiOperation("上传文件")
    public ResponseData<Map> uploadAdvPic(@PathVariable String savePath, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!pathMap.containsKey(savePath))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        String path = uploadSavePath.concat(pathMap.get(savePath));
        Map<String, String> result = new HashMap<>();
        String host = uploadHost;

        try {
            String subFix = "";
            if (savePath.equals("apk")) {
                subFix = "apk";
            } else {
                subFix = file.getContentType().split("/")[1];
            }

            String fileName = PasswordUtil.INSTANCE.encode((System.currentTimeMillis() + "").getBytes()) + "." + subFix;

            File saveFile = new File(path + fileName);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }

            file.transferTo(saveFile);
            if (!savePath.equals("apk")) {
                String thumbFileName = "thumb_" + fileName;
                File thumbFile = new File(path + thumbFileName);
                Thumbnails.of(saveFile).scale(0.5f).toFile(thumbFile);
                result.put("pic_url", host + pathMap.get(savePath) + fileName);
                result.put("thumb_pic_url", host + pathMap.get(savePath) + thumbFileName);
            } else {
                result.put("file_url", host + pathMap.get(savePath) + fileName);
            }

            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), result);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }
    }


}
