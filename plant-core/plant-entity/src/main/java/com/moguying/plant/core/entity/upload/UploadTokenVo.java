package com.moguying.plant.core.entity.upload;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadTokenVo  implements Serializable {
    /**
     * 前端上传Token
     */
    private String uploadToken;

    /**
     * 外链访问域名
     */
    private String fileDomain;

    /**
     * 七牛上传地址（根据区域取-华南）
     */
    private String uploadDomain;
}
