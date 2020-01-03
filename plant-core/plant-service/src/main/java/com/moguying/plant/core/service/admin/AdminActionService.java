package com.moguying.plant.core.service.admin;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminAction;

import java.util.List;
import java.util.Map;

public interface AdminActionService {

    ResultData<Boolean> generaAction();



    ResultData<Map<String, List<AdminAction>>> actionTree();

}
