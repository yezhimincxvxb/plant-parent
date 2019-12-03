package com.moguying.plant.core.service.upload;


import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.upload.GetTokenRequest;
import com.moguying.plant.core.entity.upload.UploadTokenVo;
import com.moguying.plant.core.entity.upload.UploadVo;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {
    ResultData<UploadVo> upload(HttpServletRequest request);

    ResultData<UploadTokenVo> getToken(GetTokenRequest request);
}
