package com.moguying.plant.core.service.user;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerDot;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerSearch;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.user.vo.UserFertilizerInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserFertilizerService {


    /**
     * 用户券中心
     *
     * @param page
     * @param size
     * @param userId
     * @return
     */
    PageResult<UserFertilizerInfo> userFertilizers(Integer page, Integer size, Integer userId, FertilizerSearch search);

    /**
     * 在指定条件下可使用的券
     *
     * @return
     */
    List<UserFertilizerInfo> canUseFertilizers(FertilizerUseCondition condition);

    PageResult<UserFertilizer> userFertilizerList(Integer page, Integer size, UserFertilizer where);

    void downloadExcel(Integer userId, PageSearch<UserFertilizer> search, HttpServletRequest request);

    ResultData<Integer> addUserFertilizer(UserFertilizer fertilizer);

    UserFertilizer getUserFertilizer(Integer userId, Integer id, Integer type);

    Boolean redPackageSuccess(UserFertilizer userFertilizer);

    UserFertilizer userFertilizer(Integer userId, String orderNumber);

    FertilizerDot fertilizerDot(Integer userId);

    Boolean cancelFertilizerDot(Integer userId);

}
