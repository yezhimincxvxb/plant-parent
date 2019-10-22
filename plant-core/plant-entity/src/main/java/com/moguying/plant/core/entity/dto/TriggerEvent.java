package com.moguying.plant.core.entity.dto;

import java.io.Serializable;

/**
 * plant_trigger_event
 * @author 
 */
public class TriggerEvent implements Serializable {
    /**
     * 触发事件英文
     */
    private String triggerEvent;

    /**
         * 触发事件中文
     */
    private String mark;

    private static final long serialVersionUID = 1L;

    public String getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(String triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}