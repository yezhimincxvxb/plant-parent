package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.dto.Adv;
import com.moguying.plant.core.entity.dto.AdvType;
import com.moguying.plant.core.service.content.AdvService;
import com.moguying.plant.core.service.content.AdvTypeService;
import com.moguying.plant.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/backEnd/")
@Slf4j
public class BAdvController {


    @Autowired
    AdvTypeService advTypeService;

    @Autowired
    AdvService advService;

    @Value("${upload.host}")
    private String uploadHost;

    @Value("${editor.upload.host}")
    private String editorUploadHost;

    private static Map<String,String> pathMap = new HashMap<>();

    static {
        pathMap.put("adv","/upload/adv/");
        pathMap.put("banner","/upload/banner/");
        pathMap.put("article","/upload/article/");
        pathMap.put("editor","/upload/editor/");
        pathMap.put("mall","/upload/mall/");
        pathMap.put("block","/upload/block/");
        pathMap.put("seed","/upload/seed/");
        pathMap.put("activity","/upload/activity/");
        pathMap.put("contract","/upload/contract/");
        pathMap.put("apk","/upload/apk/");
    }

    /**
     * 添加广告位置
     * @param advType
     * @return
     */
    @PostMapping(value = "/adv/type")
    @ResponseBody
    public ResponseData<Integer> addAdvType(@RequestBody AdvType advType){
        if(advTypeService.addAdvType(advType) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),advType.getId());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 获取广告位置列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/adv/type/list")
    @ResponseBody
    public PageResult<AdvType> advTypeList(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return advTypeService.advTypeList(page,size,null);
    }


    /**
     * 获取单个广告位置信息
     * @param id
     * @return
     */
    @GetMapping(value = "/adv/type/{id}")
    @ResponseBody
    public ResponseData<AdvType> getAdvType(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(), advTypeService.advType(id));
    }

    /**
     * 更新指定id的广告位置信息
     * @param id
     * @param advType
     * @return
     */
    @PutMapping(value = "/adv/type/{id}")
    @ResponseBody
    public ResponseData<Integer> updateAdvType(@PathVariable Integer id, @RequestBody AdvType advType){
        Integer result = advTypeService.updateAdvType(id,advType);
        if(result > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), result);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 添加广告信息
     * @param adv
     * @return
     */
    @PostMapping(value = "/adv")
    @ResponseBody
    public ResponseData<Integer> addAdv(@RequestBody Adv adv){
        if(advService.addAdv(adv) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),adv.getId());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 获取广告信息列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/adv/list")
    @ResponseBody
    public PageResult<Adv> advList(@RequestParam(value = "page",defaultValue = "1" ,required = false) Integer page,
                                   @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){
        return advService.advList(page,size,null);
    }


    /**
     * 删除广告信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "/adv/{id}")
    @ResponseBody
    public ResponseData<Integer> deleteAdv(@PathVariable Integer id){
        Integer result =  advService.deleteAdv(id);
        if(result > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 获取指定id的广告信息
     * @param id
     * @return
     */
    @GetMapping(value = "/adv/{id}")
    @ResponseBody
    public ResponseData<Adv> getAdv(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),advService.adv(id));
    }


    /**
     * 更新一个广告信息
     * @param id
     * @param adv
     * @return
     */
    @PutMapping(value = "/adv/{id}")
    @ResponseBody
    public ResponseData<Integer> updateAdv(@PathVariable Integer id, @RequestBody Adv adv){
        if(null == adv)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(),MessageEnum.PARAMETER_ERROR.getState());
        Integer result = advService.updateAdv(id,adv);
        if(result > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),result);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    @PostMapping(value = "/upload/{savePath}")
    @ResponseBody
    public ResponseData<Map> uploadAdvPic(@PathVariable String savePath, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(!pathMap.containsKey(savePath))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(),MessageEnum.PARAMETER_ERROR.getState());

        String path = request.getServletContext().getRealPath(pathMap.get(savePath));
        Map<String,String> result = new HashMap<>();
        String host = "";

        if(null != uploadHost && !StringUtils.isBlank(uploadHost))
            host = request.getScheme() + "://" + uploadHost;
        else if(savePath.equals("editor"))
            host = "https://" + editorUploadHost;

        try {
            String subFix = "";
            if(savePath.equals("apk")){
                subFix = "apk";
            } else {
               subFix = file.getContentType().split("/")[1];
            }

            String fileName = PasswordUtil.INSTANCE.encode((System.currentTimeMillis()+"").getBytes()) + "." + subFix;

            File saveFile = new File(path + fileName);
            if(!saveFile.exists()){
                saveFile.mkdirs();
            }

            file.transferTo(saveFile);
            if(!savePath.equals("apk")) {
                String thumbFileName = "thumb_" + fileName;
                File thumbFile = new File(path + thumbFileName);
                Thumbnails.of(saveFile).scale(0.5f).toFile(thumbFile);
                result.put("pic_url", host + pathMap.get(savePath) + fileName);
                result.put("thumb_pic_url", host + pathMap.get(savePath) + thumbFileName);
            } else {
                result.put("file_url", host + pathMap.get(savePath) + fileName);
            }

            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),result);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
        }
    }





}
