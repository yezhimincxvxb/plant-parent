package com.moguying.plant.core.entity.user;

import java.io.Serializable;

/**
 * plant_user_message_tpl
 * @author 
 */
public class UserMessageTpl implements Serializable {
    /**
     * 模版方法值 
     */
    private String actionCode;

    private String messageTitle;

    /**
     * 消息模板
     */
    private String messageTpl;

    private Boolean isOpen;

    private static final long serialVersionUID = 1L;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageTpl() {
        return messageTpl;
    }

    public void setMessageTpl(String messageTpl) {
        this.messageTpl = messageTpl;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}