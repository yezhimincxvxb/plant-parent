package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.seed.SeedPic;
import com.moguying.plant.core.service.seed.SeedPicService;
import com.moguying.plant.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 菌包图片管理
 */
@RestController
@RequestMapping("/seedPic")
@Slf4j
@Api(tags = "菌包图片管理")
public class BSeedPicController {

    @Autowired
    private SeedPicService seedPicService;

    @Value("${upload.host}")
    private String uploadHost;

    @Value("${upload.save-path}")
    private String uploadSavePath;

    /**
     * 菌包图片列表
     *
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("菌包图片列表")
    public PageResult<SeedPic> seedPicList(@RequestBody PageSearch search) {
        return seedPicService.seedPicList(search.getPage(), search.getSize());
    }

    /**
     * 菌包图片添加
     *
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("菌包图片添加")
    public ResponseData<String> seedPicAdd(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String path = uploadSavePath.concat("/images/");
        SeedPic seedPic = new SeedPic();
        seedPic.setIsDelete(new Byte("0"));
        byte[] bytes = new byte[1024 * 2]; //1M
        String fileName;
        String subFix = file.getOriginalFilename().split("\\.")[1];
        String host = uploadHost;
        try {
            file.getInputStream().read(bytes);
            fileName = PasswordUtil.INSTANCE.encode(bytes);
            seedPic.setPicName(fileName);
            if (seedPicService.seedPic(seedPic).size() > 0)
                return new ResponseData<>(MessageEnum.FILE_EXISTS.getMessage(), MessageEnum.FILE_EXISTS.getState());
            File saveFile = new File(path, fileName + "." + subFix);
            File thumbFile = new File(path, "thumb_" + fileName + "." + subFix);
            if (!saveFile.exists())
                saveFile.mkdirs();
            file.transferTo(saveFile);
            seedPic.setPicUrl(host + "/images/" + fileName + "." + subFix);
            Thumbnails.of(saveFile)
                    .sourceRegion(Positions.CENTER, 216, 216)
                    .scale(1)
                    .toFile(thumbFile);
            seedPic.setPicUrlThumb(host + "/images/thumb_" + fileName + "." + subFix);

        } catch (IOException e) {
            return new ResponseData<>(MessageEnum.FILE_UPLOAD_ERROR.getMessage(), MessageEnum.FILE_UPLOAD_ERROR.getState());
        }


        if (seedPicService.seePicAdd(seedPic) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());

        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 菌包图片删除
     *
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    @ApiOperation("菌包图片删除")
    public ResponseData<Long> seedPicDelete(@PathVariable Long id, HttpServletRequest request) {
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
