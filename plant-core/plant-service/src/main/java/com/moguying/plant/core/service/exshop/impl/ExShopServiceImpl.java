package com.moguying.plant.core.service.exshop.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.exshop.ExShopContentDAO;
import com.moguying.plant.core.dao.exshop.ExShopDAO;
import com.moguying.plant.core.dao.exshop.ExShopPicDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.ExShopPic;
import com.moguying.plant.core.entity.exshop.vo.ExShopVo;
import com.moguying.plant.core.service.exshop.ExShopService;
import com.moguying.plant.utils.CFCARAUtil;
import com.moguying.plant.utils.CurlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExShopServiceImpl implements ExShopService {

    @Value("${lbs.map.key}")
    private String lbsMapKey;

    @Autowired
    private ExShopDAO exShopDAO;

    @Autowired
    private ExShopPicDAO exShopPicDAO;

    @Autowired
    private ExShopContentDAO exShopContentDAO;

    @Override
    @DS("read")
    public PageResult<ExShopVo> exShopPageResult(Integer page, Integer size, ExShop search, Boolean showPic) {
        IPage<ExShopVo> iPage = exShopDAO.exShopList(new Page<>(page, size), search);

        List<ExShopVo> records = iPage.getRecords();
        if (Objects.isNull(records) || records.isEmpty()) {
            return new PageResult<>(0, new ArrayList<>());
        }

        // 是否需要显示地址图片
        if (showPic) {
            List<Integer> idList = records.stream().map(ExShopVo::getId).collect(Collectors.toList());
            List<ExShopPic> shopPics = exShopPicDAO.getPicByIdList(idList);

            records.forEach(exShopVo -> {
                List<ExShopPic> list = Collections.synchronizedList(new ArrayList<>());
                shopPics.stream()
                        .filter(exShopPic -> exShopVo.getId().equals(exShopPic.getShopId()))
                        .forEach(list::add);
                exShopVo.setPics(list);
            });
        }

        return new PageResult<>(iPage.getTotal(), records);
    }

    @Override
    @DS("read")
    public ExShopVo getExShopById(Integer shopId) {
        PageResult<ExShopVo> result = exShopPageResult(1, 1, new ExShop().setId(shopId), true);
        return result.getData().size() > 0 ? result.getData().get(0) : null;
    }

    @Override
    @DS("write")
    @Transactional
    public ResultData<String> saveExShop(ExShop exShop) {
        ResultData<String> resultData = new ResultData<>(MessageEnum.ERROR, null);

        if (null != exShop.getAddress()) {
            JSONObject jsonObject = lbsLocation(exShop.getAddress());
            if ("OK".equals(jsonObject.getString("info"))) {
                String location = jsonObject.getJSONArray("geocodes").getJSONObject(0).getString("location");
                if (null != location) exShop.setLocation(location);
            }
        }

        if (null == exShop.getId()) {
            exShop.setAddTime(new Date());
            if (exShopDAO.insert(exShop) > 0) {
                if (null != exShop.getContent()) {
                    exShop.getContent().setShopId(exShop.getId());
                    if (exShopContentDAO.insert(exShop.getContent()) < 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return resultData;
                    }
                }
                if (null != exShop.getPics() && exShop.getPics().size() > 0) {
                    exShop.getPics().forEach((x) -> {
                        x.setShopId(exShop.getId());
                    });
                    if (exShopPicDAO.insertBatch(exShop.getPics()) < 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return resultData;
                    }
                }
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
            }
        } else {
            if (exShopDAO.updateById(exShop) > 0) {
                if (null != exShop.getContent()) {
                    exShop.getContent().setShopId(exShop.getId());
                    if (exShopContentDAO.updateById(exShop.getContent()) < 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return resultData;
                    }
                }
                if (null != exShop.getPics() && exShop.getPics().size() > 0) {
                    for (ExShopPic pic : exShop.getPics()) {
                        if (exShopPicDAO.updateById(pic) < 0) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return resultData;
                        }
                    }
                }
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
            }
        }
        return resultData;
    }

    @Override
    @DS("read")
    public List<ExShopPic> showPicList(ExShopPic search) {
        return exShopPicDAO.selectSelective(search);
    }

    /**
     * 查询地理编码
     */
    private JSONObject lbsLocation(String address) {
        String url = "https://restapi.amap.com/v3/geocode/geo";
        Map<String, Object> param = new HashMap<>();
        param.put("key", lbsMapKey);
        param.put("address", address);
        param.put("city", null);
        String query = CFCARAUtil.joinMapValue(param, '&');
        String json = CurlUtil.INSTANCE.httpRequest(url, query, "GET");
        return JSON.parseObject(json);
    }

}