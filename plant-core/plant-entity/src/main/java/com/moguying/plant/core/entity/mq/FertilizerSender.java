package com.moguying.plant.core.entity.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FertilizerSender {
    private Integer userId;

    private String event;

}
