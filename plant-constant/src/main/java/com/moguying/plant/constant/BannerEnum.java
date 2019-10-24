package com.moguying.plant.constant;

public enum  BannerEnum {
    TYPE_MOBILE(1,"MOBILE"),//首页
    TYPE_PC(2,"PC"),      //PC
    TYPE_MALL(3,"MALL"), //商城
    TYPE_NAV(4,"NAV"),  //移动引导页
    TYPE_OP(5,"OP"),    //移动开屏
    TYPE_ADV(6,"ADV"),  //移动广告
    TYPE_COT(7,"COT");  //合同范本

    private Integer type;
    private String typeName;

    BannerEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
