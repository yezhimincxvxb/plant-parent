package com.moguying.plant.mq.sender;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneMessageSender {
    private String phone;

    private String content;
}
