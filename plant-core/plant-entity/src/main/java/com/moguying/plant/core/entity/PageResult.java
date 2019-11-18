package com.moguying.plant.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MessageEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: PageResult</p>
 * <p>Description: </p>
 *
 * @author Qinhir
 * @date 2018-11-05 17:13
 */
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 5827582072360440719L;

    @JSONField(name = "state", ordinal = 1)
    private Integer status = null;
    @JSONField(name = "msg", ordinal = 2)
    private String message = null;

    @JSONField(name = "count", ordinal = 3)
    private Integer count;

    @JSONField(name = "data", ordinal = 4)
    private List<T> data;

    public PageResult() {
    }

    public PageResult(Integer status, String message, long count, List<T> data) {
        this.status = status;
        this.message = message;
        this.count = new Long(count).intValue();
        this.data = data;
    }

    public PageResult(long count, List<T> data) {
        this.count = new Long(count).intValue();
        ;
        this.data = data;
        this.message = MessageEnum.SUCCESS.getMessage();
        this.status = MessageEnum.SUCCESS.getState();
    }


}
