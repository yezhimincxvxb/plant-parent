package com.moguying.plant.core.dao.user;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.user.UserSymbol;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSymbolDAO extends BaseDAO<UserSymbol> {

    List<UserSymbol> findOnToday(@Param("us") UserSymbol userSymbol);
}
