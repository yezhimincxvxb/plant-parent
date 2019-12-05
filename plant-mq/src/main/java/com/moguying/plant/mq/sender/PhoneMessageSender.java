package com.moguying.plant.mq.sender;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PhoneMessageSender implements Serializable {

    private static final long serialVersionUID = 130223971585548958L;

    private String phone;

    private String content;
}
