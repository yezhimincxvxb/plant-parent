package com.moguying.plant.core.service.upload.impl;

import com.moguying.plant.core.entity.upload.UploadTokenVo;
import com.moguying.plant.core.entity.upload.UploadVo;
import com.moguying.plant.core.service.upload.UploadService;
import com.moguying.plant.core.service.upload.qiniu.QiNiuUploadThread;
import com.moguying.plant.core.service.upload.qiniu.QiniuManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private QiniuManager qiniuManager;
    private ExecutorService pool = Executors.newSingleThreadExecutor();
    @Value("${qiniu.file-domain}")
    private String fileDomain;
    @Value("${qiniu.upload-domain}")
    private String uploadDomain;
    @Value("${qiniu.tem-path}")
    private String temPath;

    @Override
    public UploadVo upload(HttpServletRequest request) {
        UploadVo uploadVo = new UploadVo();
        List<String> data = new ArrayList<>();
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileKey = System.currentTimeMillis() + "_";
                String myFileName = file.getOriginalFilename();
                String[] arrs = myFileName.split("\\.");
                fileKey = fileKey + "." + arrs[arrs.length - 1];
                Runnable runnable = new QiNiuUploadThread(file, temPath, fileKey);
                pool.submit(runnable);
                data.add(fileKey);
            }
            uploadVo.setData(data);
        }
        return uploadVo;
    }


    /**
     * 获取上传Token
     *
     * @return
     */
    @Override
    public UploadTokenVo getToken() {
        UploadTokenVo uploadTokenRlt = new UploadTokenVo();
        String uploadToken = qiniuManager.getUpToken();
        uploadTokenRlt.setUploadToken(uploadToken);
        uploadTokenRlt.setFileDomain(fileDomain);
        uploadTokenRlt.setUploadDomain(uploadDomain);
        return uploadTokenRlt;
    }



}
