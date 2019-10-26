package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.BannerEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.block.vo.BlockDetail;
import com.moguying.plant.core.entity.common.vo.*;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.entity.content.Article;
import com.moguying.plant.core.entity.content.Banner;
import com.moguying.plant.core.entity.device.DeviceGateway;
import com.moguying.plant.core.entity.device.vo.DeviceGatewayData;
import com.moguying.plant.core.entity.seed.vo.SeedDetail;
import com.moguying.plant.core.entity.seed.vo.SeedTypeInBlock;
import com.moguying.plant.core.entity.system.Apk;
import com.moguying.plant.core.service.block.BlockService;
import com.moguying.plant.core.service.content.ActivityService;
import com.moguying.plant.core.service.content.ArticleService;
import com.moguying.plant.core.service.content.BannerService;
import com.moguying.plant.core.service.device.DeviceService;
import com.moguying.plant.core.service.mall.MallProductService;
import com.moguying.plant.core.service.seed.SeedService;
import com.moguying.plant.core.service.system.ApkService;
import com.moguying.plant.utils.InterestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class AHomeController {


    @Autowired
    private BannerService bannerService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private SeedService seedService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private ApkService apkService;

    @Autowired
    private DeviceService deviceService;


    /**
     * 首页banner列表
     *
     * @return
     */
    @GetMapping(value = "/banner")
    public ResponseData<List<Banner>> bannerList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                bannerService.listForHome(BannerEnum.TYPE_MOBILE.getType()));
    }


    /**
     * 由参数获取不同Banner
     *
     * @return
     */
    @GetMapping("/banner/{typeName}")
    public ResponseData<List<Banner>> mallBannerList(@PathVariable String typeName) {

        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                bannerService.listForHome(BannerEnum.valueOf("TYPE_" + typeName.toUpperCase()).getType())
        );
    }


    /**
     * 活动页列表
     *
     * @return
     */
    @GetMapping("/activity")
    public PageResult<Activity> activityList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return activityService.activityListForHome(page, size, null, null);

    }


    /**
     * 活动详情
     *
     * @param id
     * @return
     */
    @GetMapping("/activity/{id}")
    public ResponseData<Activity> activityDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.activityDetail(id));
    }


    @GetMapping("/newest/activity")
    public ResponseData<List<Activity>> newestActivity() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.newestActivity());
    }


    /**
     * 公告列表
     *
     * @return
     */
    @GetMapping(value = "/annotation")
    @ResponseBody
    public ResponseData<List<Article>> annotationList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                articleService.articleForHome()
        );
    }

    /**
     * 棚区列表
     *
     * @return
     */
    @GetMapping(value = "/block")
    public ResponseData<List<HomeBlock>> blockList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                blockService.blockListForHome());
    }


    /**
     * 棚区详情
     *
     * @param id
     * @return
     */
    @GetMapping("/block/{id}")
    public ResponseData<BlockDetail> blockDetail(@PathVariable Integer id) {
        ResultData<Block> resultData = blockService.blockInfo(id);
        if (!resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        Block block = resultData.getData();
        if (!block.getIsShow())
            return new ResponseData<>(MessageEnum.BLOCK_NOT_EXISTS.getMessage(), MessageEnum.BLOCK_NOT_EXISTS.getState());
        BlockDetail blockDetail = new BlockDetail();
        blockDetail.setBlockId(block.getId());
        blockDetail.setBlockNumber(block.getNumber());
        blockDetail.setSeedTypeName(block.getSeedTypeName());
        blockDetail.setBlockAmount(block.getTotalAmount());
        blockDetail.setSeedGrowDays(block.getGrowDays());
        blockDetail.setSeedPrice(block.getPerPrice());
        blockDetail.setBlockLeftCount(block.getLeftCount());
        BigDecimal interest = InterestUtil.INSTANCE.calInterest(block.getPerPrice(), block.getInterestRates(), block.getGrowDays());
        blockDetail.setSaleAmount(interest.add(block.getPerPrice()));
        blockDetail.setMintPlant(block.getMinPlant());
        double percent = new Double(block.getHasCount()) / block.getTotalCount();
        blockDetail.setPlantPercent((int) (percent * 100));
        blockDetail.setPicUrl(block.getPicUrl());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), blockDetail);
    }


    /**
     * 菌包市场
     *
     * @return
     */
    @GetMapping(value = "/index/seed")
    public PageResult<HomeSeed> seedList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageResult<HomeSeed> pageResult = seedService.seedListForHome(page, size);
        List<HomeSeed> homeSeeds = pageResult.getData();
        //如果都已售罄，先一个售罄的显示
        if (page == 1 && homeSeeds.size() == 0) {
            homeSeeds.add(seedService.selectOneSaleDownSeed());
        }
        for (HomeSeed homeSeed : homeSeeds) {
            BigDecimal money = homeSeed.getPerPrice().multiply(new BigDecimal("100"));
            homeSeed.setPerHundredProfit(InterestUtil.INSTANCE.calInterest(money, homeSeed.getInterestRates(), homeSeed.getGrowDays()));
        }
        return pageResult;
    }

    /**
     * 菌包详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/index/seed/{id}")
    public ResponseData<SeedDetail> seedDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), seedService.seedDetail(id));
    }


    /**
     * 商城商品列表
     *
     * @param search
     * @return
     */
    @PostMapping("/index/mall")
    public PageResult<HomeProduct> productList(@RequestBody HomeProduct search) {
        return mallProductService.productListForHome(search.getPage(), search.getSize(), search);
    }


    /**
     * 商城商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")
    public ResponseData<HomeProductDetail> productDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                mallProductService.productDetailForAppMall(id));
    }

    /**
     * apk最新版本信息
     *
     * @return
     */
    @GetMapping("/apk/info")
    public ResponseData<Apk> newestApkInfo() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                apkService.newestApkInfo());
    }


    /**
     * 查询菌包种类可种大棚
     *
     * @param seedTypeInBlock
     * @return
     */
    @PostMapping("/plant/block")
    public ResponseData<Block> findSeedTypeInBlock(@RequestBody SeedTypeInBlock seedTypeInBlock) {
        Block block = blockService.findBlockBySeedType(seedTypeInBlock.getSeedTypeId());
        if (null == block.getId())
            return new ResponseData<>(MessageEnum.NO_BLOCK_CAN_PLANT.getMessage(), MessageEnum.NO_BLOCK_CAN_PLANT.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), block);
    }


    /**
     * 获取推荐菌包
     */
    @GetMapping("/recommend/seed")
    public ResponseData<List<HomeSeed>> recommendSeed() {
        List<HomeSeed> recommendSeed = seedService.recommendSeed();
        for (HomeSeed homeSeed : recommendSeed) {
            BigDecimal money = homeSeed.getPerPrice().multiply(new BigDecimal("100"));
            homeSeed.setPerHundredProfit(InterestUtil.INSTANCE.calInterest(money, homeSeed.getInterestRates(), homeSeed.getGrowDays()));
        }
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), recommendSeed);
    }


    /**
     * 计算金额
     *
     * @param calculation
     * @return
     */
    @PostMapping("/calculation")
    public ResponseData<Calculation> calculation(@RequestBody Calculation calculation) {
        if (null != calculation.getCount() && null != calculation.getPrice()) {
            calculation.setTotalAmount(InterestUtil.INSTANCE.calAmount(calculation.getCount(), calculation.getPrice()));
            if (calculation.getDays() != null && calculation.getRate() != null) {
                BigDecimal interest = InterestUtil.INSTANCE.calInterest(calculation.getTotalAmount(), calculation.getRate(), calculation.getDays());
                calculation.setTotalAmount(interest.add(calculation.getTotalAmount()));
            }
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), calculation);
        }
        return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
    }


    /**
     * 设备列表
     * @param pageSearch
     * @return
     */
    @PostMapping("/device/gateway")
    public PageResult<DeviceGateway> gatewayList(@RequestBody PageSearch<DeviceGateway> pageSearch){
        return deviceService.deviceGatewayList(pageSearch.getPage(),pageSearch.getSize(),pageSearch.getWhere());
    }


    /**
     * 指定设备信息
     * @param where
     * @return
     */
    @PostMapping("/device/data")
    public ResponseData<List<DeviceGatewayData>> deviceData(@RequestBody DeviceGatewayData where){
        ResultData<List<DeviceGatewayData>> resultData = deviceService.gatewayData(where.getGatewayLogo());

        ResponseData<List<DeviceGatewayData>> responseData = new ResponseData<>();
        responseData.setMessage(resultData.getMessageEnum().getMessage()).setState(resultData.getMessageEnum().getState());
        if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            //只取一组数据
            return responseData.setData(resultData.getData().stream().filter((x) -> x.getSensorName().equals("1")).collect(Collectors.toList()));
        }
        return responseData;
    }



}
