package com.moguying.plant.core.entity.mall.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class TraceData implements Serializable {

    private static final long serialVersionUID = 5153617082793407529L;

    /**
     * 消息
     */
    private String message;

    /**
     * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投 等7个状态
     */
    private String state;

    /**
     * 通讯状态
     */
    private Integer status;

    /**
     * 快递单明细状态标记
     */
    private String condition;

    /**
     * 是否签收标记
     */
    private Integer ischeck;

    /**
     * 快递公司编码
     */
    private String com;

    /**
     * 快递单号
     */
    private String nu;

    /**
     * 对象数组
     */
    private List<TraceDataInfo> data;

    public String getState() {

        if (StringUtils.isEmpty(this.state)) {
            return "";
        }

        int i = Integer.parseInt(this.state);
        switch (i) {
            case 0:
                return "在途";
            case 1:
                return "揽收";
            case 2:
                return "疑难";
            case 3:
                return "签收";
            case 4:
                return "退签";
            case 5:
                return "派发";
            case 6:
                return "退回";
            default:
                return "转投";
        }
    }
}
