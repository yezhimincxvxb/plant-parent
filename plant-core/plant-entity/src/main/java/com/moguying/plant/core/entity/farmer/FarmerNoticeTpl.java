package com.moguying.plant.core.entity.farmer;

import lombok.Data;

import java.io.Serializable;

/**
 * plant_farmer_notice_tpl
 * @author 
 */
@Data
public class FarmerNoticeTpl implements Serializable {
    private Integer id;

    private String triggerEvent;

    private String messageTpl;
}