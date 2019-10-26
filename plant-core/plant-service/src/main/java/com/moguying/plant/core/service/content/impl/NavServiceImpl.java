package com.moguying.plant.core.service.content.impl;

import com.moguying.plant.core.dao.content.NavDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Nav;
import com.moguying.plant.core.service.content.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavServiceImpl implements NavService {

    @Autowired
    NavDAO navDAO;

    @Override
    public PageResult<Nav> navList(Integer page, Integer size, Nav where) {
        navDAO.selectSelective(where);
        return null;
    }


    @Override
    public Integer addNav(Nav nav) {
        if(navDAO.insert(nav) > 0)
            return nav.getId();
        return -1;
    }


    @Override
    public Integer deleteNav(Integer id) {
        return  navDAO.deleteById(id);
    }

    @Override
    public Nav nav(Integer id) {
        return navDAO.selectById(id);
    }

    @Override
    public Integer updateNave(Integer id, Nav update) {
        Nav nav = navDAO.selectById(id);

        if(nav != null){
            update.setId(id);
            return navDAO.updateById(update);
        }

        return -1;
    }
}
