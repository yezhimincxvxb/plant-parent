package com.moguying.plant.core.entity.system;

import com.moguying.plant.core.entity.admin.AdminMenu;
import lombok.Data;

import java.util.List;

@Data
public class MenuTree {

    List<AdminMenu> tree;

}
