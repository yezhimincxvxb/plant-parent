package com.moguying.plant.core.entity.upload;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadTokenVo  implements Serializable {
    private String uploadToken;

    private String fileDomain;
}
