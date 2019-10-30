package com.moguying.plant.core.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatShare {


    public WeChatShare(String timestamp, String noncestr, String signature) {
        this.timestamp = timestamp;
        this.noncestr = noncestr;
        this.signature = signature;
    }

    private String timestamp;

    private String noncestr;

    private String signature;

    private String url;

}
