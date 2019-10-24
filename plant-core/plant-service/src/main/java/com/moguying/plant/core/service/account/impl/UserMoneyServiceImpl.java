package com.moguying.plant.core.service.account.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.account.UserMoneyLogDAO;
import com.moguying.plant.core.dao.user.UserMoneyDetailDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.system.ProductInfo;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.user.UserMoneyDetail;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.common.DownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class UserMoneyServiceImpl implements UserMoneyService {

    Logger log = LoggerFactory.getLogger(UserMoneyServiceImpl.class);

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Autowired
    private UserMoneyDAO userMoneyDAO;

    @Autowired
    private UserMoneyDetailDAO userMoneyDetailDAO;

    @Autowired
    private UserMoneyLogDAO userMoneyLogDAO;

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<UserMoney> userMoneyList(Integer page, Integer size, UserMoney where) {
        userMoneyDAO.selectSelective(where);
        return null;
    }

    @Override
    @DataSource("write")
    public UserMoneyOperator updateAccount(UserMoneyOperator operator) {

        UserMoney operateMoney = operator.getUserMoney();
        Integer userId = operator.getUserMoney().getUserId();
        UserMoney userMoney;


        if ((userMoney = userMoneyDAO.selectById(userId)) == null) {
            if (initAccount(userId) > 0) {
                return updateAccount(operator);
            } else
                return operator;
        }


        if (operateMoney.getAvailableMoney().compareTo(new BigDecimal("0.00")) != 0) {
            userMoney.setAvailableMoney(userMoney.getAvailableMoney().add(operateMoney.getAvailableMoney()));
            if (operator.getAffectMoney() == null) {
                operator.setAffectMoney(operateMoney.getAvailableMoney());
            }
        }

        if (operateMoney.getFreezeMoney().compareTo(new BigDecimal("0.00")) != 0) {
            userMoney.setFreezeMoney(userMoney.getFreezeMoney().add(operateMoney.getFreezeMoney()));
            if (operator.getAffectMoney() == null) {
                operator.setAffectMoney(operateMoney.getFreezeMoney());
            }
        }

        BigDecimal collectCapital = operateMoney.getCollectCapital();
        if (collectCapital.compareTo(BigDecimal.ZERO) != 0) {
            // 实物兑换后，该用户的总待收本金需减去实物的预计回收本金
            if (collectCapital.compareTo(BigDecimal.ZERO) < 0 && collectCapital.negate().compareTo(userMoney.getCollectCapital()) > 0) {
                return null;
            }
            userMoney.setCollectCapital(userMoney.getCollectCapital().add(collectCapital));
            operator.setAffectMoney(collectCapital);
        }

        BigDecimal collectInterest = operateMoney.getCollectInterest();
        if (collectInterest.compareTo(BigDecimal.ZERO) != 0) {
            if (collectInterest.compareTo(BigDecimal.ZERO) < 0 && collectInterest.negate().compareTo(userMoney.getCollectInterest()) > 0) {
                return null;
            }
            userMoney.setCollectInterest(userMoney.getCollectInterest().add(collectInterest));
            operator.setAffectMoney(collectInterest);
        }

        if (userMoneyDAO.updateByPrimaryKeySelective(userMoney) > 0) {
            operator.setUserMoney(userMoney);
            return operator;
        }

        return null;
    }


    private Integer initAccount(Integer userId) {
        return userMoneyDAO.insert(new UserMoney(userId));
    }


    @Override
    @DataSource("read")
    public UserMoney userMoneyInfo(Integer userId) {
        return userMoneyDAO.selectById(userId);
    }

    @Override
    @DataSource("read")
    public void downloadExcel(Integer userId, PageSearch<UserMoney> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("资金账户", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(userMoneyDAO, search, UserMoney.class, downloadInfo)).start();
    }

    @Pagination
    @DataSource("read")
    @Override
    public PageResult<UserMoneyDetail> findUserMoney(Integer page, Integer size, Integer userId, String dateTime, List<Integer> list) {
        userMoneyDetailDAO.findUserMoney(userId, dateTime, list);
        return null;
    }

    @DataSource("read")
    @Override
    public UserMoneyDetail findUserMoneyById(Integer id) {
        return userMoneyDetailDAO.findUserMoneyById(id);
    }

    @DataSource("read")
    @Override
    public List<ProductInfo> findProducts(String type, String detailId) {
        if ("购买商城商品".equals(type)) {
            return userMoneyDetailDAO.findProducts(detailId);
        } else if ("购买菌包".equals(type)) {
            return userMoneyDetailDAO.findInSeeds(detailId);
        } else if ("出售菌包".equals(type)) {
            return userMoneyDetailDAO.findOutSeeds(detailId);
        } else if ("邀请奖励".equals(type)) {
            return userMoneyDetailDAO.findInvitation(detailId);
        } else {
            return new ArrayList<>();
        }
    }

    @DataSource("read")
    @Override
    public BigDecimal getTotal(Integer userId, String dateTime, List<Integer> list) {
        return userMoneyDetailDAO.getTotal(userId, dateTime, list);
    }

    @Override
    @DataSource("write")
    public int updateUserMoney(UserMoney userMoney) {
        return userMoneyDAO.updateByPrimaryKeySelective(userMoney);
    }
}
