package com.moguying.plant.core.service.upload;


import com.moguying.plant.core.entity.upload.UploadTokenVo;
import com.moguying.plant.core.entity.upload.UploadVo;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {
    UploadVo upload(HttpServletRequest request);

    UploadTokenVo getToken();

}
