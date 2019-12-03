package com.moguying.plant.core.entity.upload;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UploadVo implements Serializable {
    /**
     * 上传地址列表
     */
    private List<String> data;
}
