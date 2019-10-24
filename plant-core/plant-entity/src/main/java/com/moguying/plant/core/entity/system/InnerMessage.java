package com.moguying.plant.core.entity.system;

import lombok.Data;

/**
 * 内部信息
 */
@Data
public class InnerMessage {

    /**
     * 站内信所属
     */
    private Integer userId;

    private String phone;

    private String seedTypeName;

    private String amount;

    private String time;

    private String count;

    private String blockNumber;
}
