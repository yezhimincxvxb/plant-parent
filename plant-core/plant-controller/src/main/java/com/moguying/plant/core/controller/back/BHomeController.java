package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.common.vo.BHomeTopTotal;
import com.moguying.plant.core.entity.index.TotalTable;
import com.moguying.plant.core.service.account.MoneyWithdrawService;
import com.moguying.plant.core.service.account.UserMoneyLogService;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.core.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/home")
@Api(tags = "首页统计")
public class BHomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private MallOrderService mallOrderService;
    @Autowired
    private ReapService reapService;
    @Autowired
    private SeedOrderDetailService seedOrderDetailService;
    @Autowired
    private MoneyWithdrawService moneyWithdrawService;
    @Autowired
    private UserMoneyLogService userMoneyLogService;

    /**
     * 首页顶部统计
     */
    @GetMapping("/top/total")
    @ApiOperation("顶部统计")
    public ResponseData<BHomeTopTotal> homeTopTotal() {
        BHomeTopTotal homeTopTotal = new BHomeTopTotal();
        homeTopTotal.setRegNum(userService.regUserTotal());
        homeTopTotal.setOrderNum(mallOrderService.getMallOrderNum(null));
        homeTopTotal.setPlantLines(reapService.getPlantLines());
        homeTopTotal.setPlantProfits(reapService.getPlantProfits());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), homeTopTotal);
    }

    /**
     * 后台总表
     */
    @GetMapping("/total/table")
    @ApiOperation("后台总表")
    public ResponseData<TotalTable> totalTable(@RequestParam("state") Integer state) {
        Integer registerNum = userService.getRegisterNum(state);
        Integer realNameNum = userService.getRealNameNum(state);
        Integer buySeedNum = seedOrderDetailService.getBuySeedNum(state);
        TotalTable countAndPrice = seedOrderDetailService.getBuyCountAndPrice(state);
        Integer plantNum = reapService.getPlantNum(state);
        TotalTable userMoney = userMoneyLogService.getUserMoney(state);
        BigDecimal withdrawalSuccess = moneyWithdrawService.getWithdrawalSuccess(state);
        BigDecimal withdrawalWait = moneyWithdrawService.getWithdrawalWait(state);
        Integer mallOrderNum = mallOrderService.getMallOrderNum(state);
        Integer mallOrderUserNum = mallOrderService.getMallOrderUserNum(state);
        BigDecimal mallOrderAmount = mallOrderService.getMallOrderAmount(state);
        TotalTable table = new TotalTable()
                .setRegisterNum(registerNum)
                .setRealNameNum(realNameNum)
                .setRegisterRate(registerNum == 0 ? BigDecimal.ZERO
                        : BigDecimal.valueOf(realNameNum).divide(BigDecimal.valueOf(registerNum), 2, BigDecimal.ROUND_DOWN))
                .setBuySeedNum(buySeedNum)
                .setBuySeedCount(countAndPrice.getBuySeedCount())
                .setBuyRate(realNameNum == 0 ? BigDecimal.ZERO
                        : BigDecimal.valueOf(buySeedNum).divide(BigDecimal.valueOf(realNameNum), 2, BigDecimal.ROUND_DOWN))
                .setTotalBuyPrice(countAndPrice.getTotalBuyPrice())
                .setAvgBuyPrice(countAndPrice.getBuySeedCount() == 0 ? BigDecimal.ZERO
                        : countAndPrice.getTotalBuyPrice().divide(BigDecimal.valueOf(countAndPrice.getBuySeedCount()), 2, BigDecimal.ROUND_DOWN))
                .setPlantNum(plantNum)
                .setPlantRate(buySeedNum == 0 ? BigDecimal.ZERO
                        : BigDecimal.valueOf(plantNum).divide(BigDecimal.valueOf(buySeedNum), 2, BigDecimal.ROUND_DOWN))
                .setInterest(userMoney.getInterest())
                .setPlantAmount(userMoney.getPlantAmount())
                .setAvailableMoney(userMoney.getAvailableMoney())
                .setFreezeAmount(userMoney.getFreezeAmount())
                .setFlowAmount(userMoney.getFlowAmount())
                .setWithdrawalSuccess(withdrawalSuccess)
                .setWithdrawalWait(withdrawalWait)
                .setUserAmount(userMoney.getInterest().add(userMoney.getPlantAmount())
                        .add(userMoney.getAvailableMoney()).add(userMoney.getFreezeAmount()).add(withdrawalWait))
                .setMallOrderNum(mallOrderNum)
                .setMallOrderUserNum(mallOrderUserNum)
                .setMallOrderAmount(mallOrderAmount);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), table);
    }

}
