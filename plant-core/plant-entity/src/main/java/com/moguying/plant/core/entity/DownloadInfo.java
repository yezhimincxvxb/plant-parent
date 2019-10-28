package com.moguying.plant.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.ServletContext;

@Data
@AllArgsConstructor
public class DownloadInfo {
    private String fileName;

    private ServletContext context;

    private Integer userId;

    private String savePath;
}
