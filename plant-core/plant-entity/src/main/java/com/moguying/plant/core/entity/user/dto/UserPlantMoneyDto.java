package com.moguying.plant.core.entity.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserPlantMoneyDto implements Serializable {

    private static final long serialVersionUID = -7822462863857700794L;

    private String phone;

    private Date startTime;

    private Date endTime;
}
