package com.moguying.plant.core.service.upload.qiniu;

import com.moguying.plant.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Slf4j
public class QiNiuUploadThread implements Runnable {

    private QiniuManager qiniuManager = ApplicationContextUtil.getBean(QiniuManager.class);
    private StringRedisTemplate redisTemplate = ApplicationContextUtil.getBean(StringRedisTemplate.class);
    private static Logger logger = LoggerFactory.getLogger(QiNiuUploadThread.class);
    private MultipartFile file;
    private String temPath;
    private String fileName;

    public QiNiuUploadThread(MultipartFile file, String temPath, String fileName) {
        this.file = file;
        this.temPath = temPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        if (file != null) {
            int pre = (int) System.currentTimeMillis();
            String myFileName = file.getOriginalFilename();
            if (myFileName.trim() != "") {
                logger.info("QiNiuUploadThread upload file name={}", myFileName);
                String path = null;
                if (!temPath.endsWith("/")) {
                    path = temPath + "/" + fileName;
                } else {
                    path = temPath + fileName;
                }
                try {
                    File localFile = new File(path);
                    file.transferTo(localFile);
                    //记录上传该文件后的时间
                    int finaltime = (int) System.currentTimeMillis();
                    logger.info("QiNiuUploadThread write file cost time={}", finaltime - pre);
                    uploadFile(localFile, path, fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void uploadFile(File localFile, String path, String fileKey) {
        try {
            long pre = System.currentTimeMillis();
            redisTemplate.opsForValue().set(fileKey, String.valueOf(pre));
            String str = qiniuManager.upload(path, fileKey);
            if (StringUtils.isNotEmpty(str) && fileKey.equals(str)) {
                redisTemplate.delete(str);
                localFile.delete();
            }
            long finaltime = System.currentTimeMillis();
            logger.info("QiNiuUploadThread upload file cost time={}", finaltime - pre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
