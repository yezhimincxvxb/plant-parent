package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.dto.SeedPic;
import com.moguying.plant.core.service.seed.SeedPicService;
import com.moguying.plant.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 菌包图片管理
 */
@Controller
@RequestMapping("/backEnd/seedPic")
@Slf4j
public class BSeedPicController {

    @Autowired
    private SeedPicService seedPicService;

    @Value("${upload.host}")
    private String uploadHost;

    /**
     * 菌包图片列表
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public PageResult<SeedPic> seedPicList(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                           @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){

        return  seedPicService.seedPicList(page,size);
    }

    /**
     * 菌包图片添加
     * @return
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseData<String> seedPicAdd(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String path = request.getServletContext().getRealPath("upload");
        SeedPic seedPic = new SeedPic();
        seedPic.setIsDelete(new Byte("0"));
        byte[] bytes = new byte[1024*2]; //1M
        String fileName;
        String subFix = file.getOriginalFilename().split("\\.")[1];
        String host = "";
        if(null != uploadHost && !StringUtils.isEmpty(uploadHost))  host = request.getScheme() + "://" + uploadHost;
        try {
            file.getInputStream().read(bytes);
            fileName = PasswordUtil.INSTANCE.encode(bytes);
            seedPic.setPicName(fileName);
            if(seedPicService.seedPic(seedPic).size() > 0)
                return new ResponseData<>(MessageEnum.FILE_EXISTS.getMessage(),MessageEnum.FILE_EXISTS.getState());
            File saveFile = new File(path,fileName+"."+subFix);
            File thumbFile = new File(path,"thumb_" + fileName + "." + subFix);
            if(!saveFile.exists())
                saveFile.mkdirs();
            file.transferTo(saveFile);
            seedPic.setPicUrl(host + "/upload/" + fileName + "." + subFix);
            Thumbnails.of(saveFile)
                    .sourceRegion(Positions.CENTER,216,216)
                    .scale(1)
                    .toFile(thumbFile);
            seedPic.setPicUrlThumb(host + "/upload/thumb_" + fileName + "." + subFix );

        } catch (IOException e) {
            return new ResponseData<>(MessageEnum.FILE_UPLOAD_ERROR.getMessage(),MessageEnum.FILE_UPLOAD_ERROR.getState());
        }


        if(seedPicService.seePicAdd(seedPic) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());

        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 菌包图片删除
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseData<Long> seedPicDelete(@PathVariable Long id, HttpServletRequest request){
        if (id == null || id < 0 || id == 0)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState(), id);

        SeedPic seedPic;
        if ((seedPic = seedPicService.seedPicDelete(id)) != null) {
            File file = new File(request.getServletContext().getRealPath("") + seedPic.getPicUrl());
            if (file.exists())
                file.delete();
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), id);
        }


        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), id);
    }


}
