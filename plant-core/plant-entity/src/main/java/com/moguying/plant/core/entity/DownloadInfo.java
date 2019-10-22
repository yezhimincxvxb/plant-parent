package com.moguying.plant.core.entity;

import javax.servlet.ServletContext;

public class DownloadInfo {
    private String fileName;

    private ServletContext context;

    private Integer userId;

    private String savePath;

    public DownloadInfo(String fileName, ServletContext context, Integer userId, String savePath) {
        this.fileName = fileName;
        this.context = context;
        this.userId = userId;
        this.savePath = savePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
