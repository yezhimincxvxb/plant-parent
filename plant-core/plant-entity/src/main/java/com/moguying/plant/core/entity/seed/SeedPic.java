package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * plant_seed_pic
 * @author 
 */
public class SeedPic implements Serializable {
    private Long id;

    @JSONField(name = "name")
    private String picName;

    @JSONField(name = "url")
    private String picUrl;

    private String picUrlThumb;

    private Byte isDelete;

    public SeedPic() {
    }

    public SeedPic( String picName, String picUrl) {
        this.picName = picName;
        this.picUrl = picUrl;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getPicUrlThumb() {
        return picUrlThumb;
    }

    public void setPicUrlThumb(String picUrlThumb) {
        this.picUrlThumb = picUrlThumb;
    }
}