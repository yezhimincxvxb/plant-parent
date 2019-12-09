package com.moguying.plant.core.service.upload.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class QiniuManager {

    @Value("${qiniu.access-key}")
    private String accessKey;
    @Value("${qiniu.secret-key}")
    private String secretKey;
    @Value("${qiniu.bucket-name}")
    private String bucketName;
    @Value("${qiniu.file-domain}")
    private String fileDomain;

    private Auth auth;
    private UploadManager uploadManager;

    private BucketManager bucket;

    /**
     * 构造一个带指定Region对象的配置类
     */
    private Configuration config;


    @PostConstruct
    public void init() {
        config = new Configuration(Region.region2());
        auth = Auth.create(accessKey, secretKey);
        uploadManager = new UploadManager(config);
        bucket = new BucketManager(auth,config);
    }



    /**
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     */
    public String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    public String getUpToken(String fileName) {
        return auth.uploadToken(bucketName, fileName);
    }

    public String upload(String filePath, String key) throws IOException {
        try {
            /**调用put方法上传*/
            String token = getUpToken();
            Response res = uploadManager.put(filePath, key, token);
            log.info("Qiniu upload success ret={}", res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            log.info("Qiniu upload QiniuException response={}", r.toString());
            return null;
        }
        return key;
    }

    public String upload(byte[] data, String key) throws IOException {
        try {
            /** 调用put方法上传 */
            String token = getUpToken();
            Response res = uploadManager.put(data, key, token);
            log.info("Qiniu upload success ret={}", res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            log.info("Qiniu upload QiniuException response={}", r.toString());
            return null;
        }
        return key;
    }

    public String download(String key) {
        /** 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间 */
        String url = null;
        if (fileDomain.endsWith("/")) {
            url = fileDomain + key;
        } else {
            url = fileDomain + File.separator + key;
        }
        String downloadRUL = auth.privateDownloadUrl(url, 3 * 3600);
        return downloadRUL;
    }


    /**
     * 根据key值删除指定空间的文件
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        try {
            Response delete = bucket.delete(bucketName,key);
            delete.close();
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key值删除指定空间的文件
     * @param key
     * @return
     */
    public Boolean delete(String bucketName,String key) {
        try {
            Response delete = bucket.delete(bucketName,key);
            delete.close();
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

}
