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
import com.moguying.plant.core.entity.mall.MallProductType;
import com.moguying.plant.core.entity.seed.vo.SeedDetail;
import com.moguying.plant.core.entity.seed.vo.SeedTypeInBlock;
import com.moguying.plant.core.entity.system.Apk;
import com.moguying.plant.core.service.block.BlockService;
import com.moguying.plant.core.service.content.ActivityService;
import com.moguying.plant.core.service.content.ArticleService;
import com.moguying.plant.core.service.content.BannerService;
import com.moguying.plant.core.service.device.DeviceService;
import com.moguying.plant.core.service.mall.MallProductService;
import com.moguying.plant.core.service.mall.MallProductTypeService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.seed.SeedService;
import com.moguying.plant.core.service.system.ApkService;
import com.moguying.plant.utils.InterestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Api(tags = "首页接口")
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

    @Autowired
    private MallProductTypeService mallProductTypeService;
    

    /**
     * 首页banner列表
     *
     * @return
     */
    @ApiOperation("首页banner列表")
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
    @ApiOperation("由参数获取不同Banner")
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
    @ApiOperation("活动页列表")
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
    @ApiOperation("活动详情")
    @GetMapping("/activity/{id}")
    public ResponseData<Activity> activityDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.activityDetail(id));
    }


    @ApiOperation("最新活动")
    @GetMapping("/newest/activity")
    public ResponseData<List<Activity>> newestActivity() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.newestActivity());
    }


    /**
     * 公告列表
     *
     * @return
     */
    @ApiOperation("公告列表")
    @GetMapping(value = "/annotation")
    public ResponseData<List<Article>> annotationList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                articleService.articleForHome()
        );
    }

    /**
     * 推荐棚区列表
     *
     * @return
     */
    @ApiOperation("推荐棚区列表")
    @GetMapping(value = "/block")
    public ResponseData<List<BlockDetail>> recommendBlock() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                blockService.blockRecommend());
    }

    /**
     * 所有棚区
     */
    @ApiOperation("所有棚区")
    @PostMapping(value = "/block/list")
    public PageResult<Block> blockList(@RequestBody PageSearch<Block> search) {
        //必须是上架的大棚
        if(null == search.getWhere()) {
            Block where = new Block();
            where.setIsShow(true);
            search.setWhere(where);
        } else {
            search.getWhere().setIsShow(true);
        }
         return blockService.blockList(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 棚区详情
     *
     * @param id
     * @return
     */
    @ApiOperation("棚区详情")
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
    @ApiOperation("菌包市场")
    @PostMapping(value = "/index/seed")
    public PageResult<HomeSeed> seedList(@RequestBody PageSearch<HomeSeed> search) {
        if(null == search.getWhere())  search.setWhere(new HomeSeed());
        PageResult<HomeSeed> pageResult = seedService.seedListForHome(search.getPage(), search.getSize(),search.getWhere());
        List<HomeSeed> homeSeeds = pageResult.getData();
        //如果都已售罄，先一个售罄的显示
        if (search.getPage() == 1 && homeSeeds.size() == 0) {
            //由于IPage默认底层实名的list中不包含add操作,所以如果page返回的list要添加项时应注意
            homeSeeds = new ArrayList<>();
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
    @ApiOperation("菌包详情")
    @GetMapping("/index/seed/{id}")
    public ResponseData<SeedDetail> seedDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), seedService.seedDetail(id));
    }


    /**
     * 商城商品列表
     *
     * @param search
     * @return
     */
    @ApiOperation("商城商品列表")
    @PostMapping("/index/mall")
    public PageResult<HomeProduct> productList(@RequestBody HomeProduct search) {
        return mallProductService.productListForHome(search.getPage(), search.getSize(), search);
    }


    /**
     * 商品类型列表
     * @return
     */
    @ApiOperation("商品类型列表")
    @GetMapping("/index/mall/types")
    public ResponseData<List<MallProductType>> productTypeList(){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),mallProductTypeService.typeList(null));
    }



    /**
     * 商城商品详情
     *
     * @param id
     * @return
     */
    @ApiOperation("商城商品详情")
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
    @ApiOperation("apk最新版本信息")
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
    @ApiOperation("查询菌包种类可种大棚")
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
    @ApiOperation("获取推荐菌包")
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
    @ApiOperation("计算金额")
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
    @ApiOperation("设备列表")
    @PostMapping("/device/gateway")
    public PageResult<DeviceGateway> gatewayList(@RequestBody PageSearch<DeviceGateway> pageSearch){
        return deviceService.deviceGatewayList(pageSearch.getPage(),pageSearch.getSize(),pageSearch.getWhere());
    }


    /**
     * 指定设备信息
     * @param where
     * @return
     */
    @ApiOperation("指定设备信息")
    @PostMapping("/device/data")
    public ResponseData<List<DeviceGatewayData>> deviceData(@RequestBody DeviceGatewayData where){
        ResultData<List<DeviceGatewayData>> resultData = deviceService.gatewayData(where.getGatewayLogo());

        ResponseData<List<DeviceGatewayData>> responseData = new ResponseData<>();
        responseData.setMessage(resultData.getMessageEnum().getMessage()).setState(resultData.getMessageEnum().getState());
        if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            //只取一组数据
            return responseData.setData(resultData.getData().stream().filter((x) -> x.getSensorName().equals("3")
                    || x.getSensorName().equals("6")).collect(Collectors.toList()));
        }
        return responseData;
    }



}
