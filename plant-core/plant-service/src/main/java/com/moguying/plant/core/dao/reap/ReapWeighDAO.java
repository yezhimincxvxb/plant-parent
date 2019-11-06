package com.moguying.plant.core.dao.reap;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.reap.ReapWeigh;
import org.springframework.stereotype.Repository;

@Repository
public interface ReapWeighDAO  extends BaseDAO<ReapWeigh> {
   int incField(ReapWeigh reapWeigh);
}
