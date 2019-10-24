package com.moguying.plant.core.entity.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * plant_admin_role
 * @author 
 */
@Data
public class AdminRole implements Serializable {
    private Integer id;

    private String roleName;

    private List<Integer> actionIds;

    private String actionCode;

    private List<AdminMenu> tree;



}