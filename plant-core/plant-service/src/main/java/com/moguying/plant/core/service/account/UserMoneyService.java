package com.moguying.plant.core.service.account;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.mall.vo.ProductInfo;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.entity.user.vo.UserMoneyDetail;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface UserMoneyService {

    PageResult<UserMoney> userMoneyList(Integer page, Integer size, UserMoney where);

    UserMoneyOperator updateAccount(UserMoneyOperator operator);

    UserMoney userMoneyInfo(Integer userId);

    void downloadExcel(Integer userId, PageSearch<UserMoney> search, HttpServletRequest request);

    /**
     * 获取用户资金明细
     */
    PageResult<UserMoneyDetail> findUserMoney(Integer page, Integer size, Integer userId, String dateTime, List<Integer> list);

    /**
     * 根据ID获取资金详情
     */
    UserMoneyDetail findUserMoneyById(Integer id);

    /**
     * 根据商品详情ID获取商品详情
     */
    List<ProductInfo> findProducts(String type, String detailId);

    /**
     * 收入/支出
     */
    BigDecimal getTotal(Integer userId, String dateTime, List<Integer> list);

    /**
     * 更新资金
     */
    int updateUserMoney(UserMoney userMoney);

}
