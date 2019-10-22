package com.moguying.plant.core.service.user;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.UserFertilizer;
import com.moguying.plant.core.entity.vo.FertilizerSearch;
import com.moguying.plant.core.entity.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.vo.UserFertilizerInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserFertilizerService {


    /**
     * 用户券中心
     * @param page
     * @param size
     * @param userId
     * @return
     */
    @DataSource("write")
    PageResult<UserFertilizerInfo> userFertilizers(Integer page, Integer size, Integer userId, FertilizerSearch search);

    /**
     * 在指定条件下可使用的券
     * @return
     */
    @DataSource("read")
    List<UserFertilizerInfo> canUseFertilizers(FertilizerUseCondition condition);

    @DataSource("read")
    PageResult<UserFertilizer> userFertilizerList(Integer page, Integer size, UserFertilizer where);

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<UserFertilizer> search, HttpServletRequest request);

    @DataSource
    ResultData<Integer> addUserFertilizer(UserFertilizer fertilizer);

    @DataSource("read")
    UserFertilizer getUserFertilizer(Integer userId, Integer id, Integer type);

    @DataSource("write")
    Boolean redPackageSuccess(UserFertilizer userFertilizer);

}
