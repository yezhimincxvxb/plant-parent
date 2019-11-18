package com.moguying.plant.core.service.content.impl;

import com.moguying.plant.core.dao.content.AdvDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Adv;
import com.moguying.plant.core.service.content.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvServiceImpl implements AdvService {

    @Autowired
    AdvDAO advDAO;

    @Override
    public Integer addAdv(Adv adv) {
        if (advDAO.insert(adv) > 0)
            return adv.getId();
        return -1;
    }


    @Override
    public PageResult<Adv> advList(Integer page, Integer size, Adv adv) {
        advDAO.selectSelection(adv);
        return null;
    }

    @Override
    public Integer deleteAdv(Integer id) {
        return advDAO.deleteById(id);
    }

    @Override
    public Adv adv(Integer id) {
        return advDAO.selectById(id);
    }

    @Override
    public Integer updateAdv(Integer id, Adv update) {
        Adv adv = advDAO.selectById(id);
        if (adv != null) {
            update.setId(id);
            return advDAO.updateById(update);
        }
        return -1;
    }
}
