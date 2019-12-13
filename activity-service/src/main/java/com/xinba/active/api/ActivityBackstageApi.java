package com.xinba.active.api;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.active.constant.ActivityEnum;
import com.xinba.active.util.ActivityException;
import com.xinba.active.util.FileToFtp;
import com.xinba.active.util.ImageCompressUtil;
import com.xinba.active.vo.WinningRecordVO;
import com.xinba.active.vo.request.AddActivityGoodsRequest;
import com.xinba.active.vo.response.PageConsts;
import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxActivity;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.entity.CxWinningRecord;
import com.xinba.active.entity.draw.ActivityGoods;
import com.xinba.active.service.CxActivityGoodsService;
import com.xinba.active.service.CxActivityService;
import com.xinba.active.service.CxMemberService;
import com.xinba.active.service.CxWinningRecordService;
import com.xinba.active.util.TimeUtils;
import com.xinba.active.vo.ActivityVO;
import com.xinba.active.vo.response.PageResponse;
import com.xinba.active.vo.response.ResultEnum;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.common.util.utils.RandomUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@Api(value = "促销活动active运营平台服务", tags = {"活动促销后端接口-active"})
@RequestMapping("/promote/back")
public class ActivityBackstageApi {

    @Autowired
    private CxActivityService cxActivityService;

    @Autowired
    private CxActivityGoodsService cxActivityGoodsService;

    @Autowired
    private CxWinningRecordService cxWinningRecordService;

    @Autowired
    private CxMemberService cxMemberService;


    /* 活动 管理部分 */

    /**
     * 添加活动
     *
     * @return Wrapper
     */
    @ApiOperation(value = "新增活动")
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public Wrapper addActivity(@ApiParam(name = "活动对象", value = "需要添加的活动对象", required = true)
                               @RequestBody CxActivity cxActivity) {
        // todo 入参判断，status是否在有效范围内
        String activityId = "AC" + System.currentTimeMillis() + RandomUtil.createNumberCode(6);
        cxActivity.setActivityId(activityId);
        // 设置默认的status
        cxActivity.setStatus(ActivityEnum.ACTIVITY_STATUS_OFFLINE.getKey());
        cxActivityService.insertActivity(cxActivity);
        return WrapMapper.ok();
    }

    // 根据条件查询活动
    @ApiOperation(value = "根据条件查询活动", notes = "根据条件查询活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态，0正常，1停用"),
            @ApiImplicitParam(name = "activityType", value = "活动类型"),
            @ApiImplicitParam(name = "activityId", value = "活动编号"),
            @ApiImplicitParam(name = "activityName", value = "活动名称"),
            @ApiImplicitParam(name = "pageNum", value = "当前页")
    })
    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public Wrapper getActivity(@RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(value = "activityType", required = false) String activityType,
                               @RequestParam(value = "activityId", required = false) String activityId,
                               @RequestParam(value = "activityName", required = false) String activityName,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        // todo 入参判断

//        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, PageConsts.DEFAULT_PAGE_SIZE);//分页
        PageHelper.orderBy("id desc");
        List<CxActivity> activityList = cxActivityService.selectActivityInfo(status, activityType, activityId, activityName);
        PageInfo<CxActivity> pageInfo = new PageInfo<>(activityList);
        // 方便信息展示，利用DTO业务转换，或者VO直接展示
        // 避免重复连接数据库
        List<CommonDTO> memberCommonDTOList = cxMemberService.countMultiMember();
        List<CommonDTO> goodsCommonDTOList = cxActivityGoodsService.countMultiActivityGoodsByStatus(ActivityEnum.ACTIVITY_GOODS_STATUS_ONLINE.getKey());
        List<ActivityVO> activityVOList = new ArrayList<>();
        for(CxActivity activity: activityList){
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(activity, activityVO);
            // 活动状态 转化显示 str
            if(ActivityEnum.ACTIVITY_STATUS_ONLINE.getKey().equals(activity.getStatus())){
                activityVO.setActivityStatus(ActivityEnum.ACTIVITY_STATUS_ONLINE.getValue());
            }else if(ActivityEnum.ACTIVITY_STATUS_OFFLINE.getKey().equals(activity.getStatus())){
                activityVO.setActivityStatus(ActivityEnum.ACTIVITY_STATUS_OFFLINE.getValue());
            }else {
                activityVO.setActivityStatus(null);
            }
            // 时间转化
            activityVO.setBeginTimeStr(TimeUtils.dateToStr(activity.getBeginTime()));
            activityVO.setEndTimeStr(TimeUtils.dateToStr(activity.getEndTime()));
            // steam流操作，筛选数据
            memberCommonDTOList.stream().filter(m -> m.getKeyDTO().equals(activity.getActivityId())).findFirst().ifPresent(menberCommonDTO -> activityVO.setActivityMember(Integer.valueOf(String.valueOf(menberCommonDTO.getValueDTO()))));
            goodsCommonDTOList.stream().filter(g -> g.getKeyDTO().equals(activity.getActivityId())).findFirst().ifPresent(g -> activityVO.setActivityPrizeNum(Integer.valueOf(String.valueOf(g.getValueDTO()))));
            activityVOList.add(activityVO);
        }
//        map.put("活动条数", pageInfo.getTotal());
//        map.put("活动集合", activityList);
//        map.put("页数", pageInfo.getPages());
        PageResponse<ActivityVO> activityVOPageResponse = new PageResponse<>(pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum(), activityVOList);
        return WrapMapper.ok(activityVOPageResponse);
    }

    // 获取活动类型
    @RequestMapping(value = "/activityType", method = RequestMethod.GET)
    public Wrapper getActivityType(){
        String[] strings = {"抽奖活动","赠品活动"};
        List<String> stringList = Arrays.asList(strings);
        // 方便 result 是json对象格式
        Map<String, List<String>> map = new HashMap<>();
        map.put("activityType", stringList);
        return WrapMapper.ok(map);
    }

    // 获取活动范围
    @RequestMapping(value = "/activityRange", method = RequestMethod.GET)
    public Wrapper getActivityRange(){
        String[] strings = {"全体"};
        List<String> stringList = Arrays.asList(strings);
        Map<String, List<String>> map = new HashMap<>();
        map.put("activityRange", stringList);
        return WrapMapper.ok(map);
    }

    // 更新活动
    @RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
    public Wrapper putActivity(@RequestBody CxActivity cxActivity){
        // todo 入参判断，不接受修改 ac_id,status;ac_type是否在指定字段中
        // 是否添加 只有在下线状态才能更新活动 逻辑

        if(cxActivityService.updateActivity(cxActivity)){
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVITY_UPDATE_FAIL.getCode(), ResultEnum.ACTIVITY_UPDATE_FAIL.getMessage());
    }

    // 修改活动上下线状态
    @RequestMapping(value = "/activityStatus", method = RequestMethod.POST)
    public Wrapper putActivityStatus(@RequestBody CxActivity cxActivity){
        // todo 入参判断, ac_id是否为空，status是否在有效范围内
        boolean update = cxActivityService.updateActivityStatus(cxActivity.getActivityId(), cxActivity.getStatus());
        if(update){
          return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVITY_UPDATE_FAIL.getCode(), ResultEnum.ACTIVITY_UPDATE_FAIL.getMessage());
    }

    // 删除活动，利用get方法
    // 关于逻辑删除的问题
    @RequestMapping(value = "/deleteActivity", method = RequestMethod.GET)
    public Wrapper deleteActivity(@RequestParam String activityId){
        if(cxActivityService.deleteActivity(activityId)){
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVITY_UPDATE_FAIL.getCode(), ResultEnum.ACTIVITY_UPDATE_FAIL.getMessage());
    }

    // 批量删除活动
    @RequestMapping(value = "/deleteMultiActivity", method = RequestMethod.GET)
    public Wrapper deleteMultiActivity(@RequestParam(value = "list[]") List<String> activityIdList){
        if(cxActivityService.deleteMultiActivity(activityIdList)){
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVITY_UPDATE_FAIL.getCode(), ResultEnum.ACTIVITY_UPDATE_FAIL.getMessage());
    }




    /* 商品 管理部分 */

    /**
     * 包括搜索接口
     * @param activityId
     * @param goodsType
     * @param goodsName
     * @param createTime
     * @param status
     * @param isInPool
     * @param goodsId
     * @param pageNum
     * @return
     */
    @ApiOperation(value = "分页获取活动商品", notes = "分页获取活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动编号"),
            @ApiImplicitParam(name = "goodsType", value = "奖品类型"),
            @ApiImplicitParam(name = "goodsName", value = "奖品名称"),
            @ApiImplicitParam(name = "createTime", value = "状态，0正常，1停用"),
            @ApiImplicitParam(name = "status", value = "状态，0正常，1停用"),
            @ApiImplicitParam(name = "isInPool", value = "是否在奖池，0在，1不在"),
            @ApiImplicitParam(name = "goodsId", value = "奖品编号"),
            @ApiImplicitParam(name = "pageNum", value = "当前页")
    })
    @RequestMapping(value = "/goodsPage", method = RequestMethod.GET)
    public Wrapper getActivityGoods(@RequestParam(value = "activityId") String activityId,
                                    @RequestParam(value = "goodsType", required = false) String goodsType,
                                    @RequestParam(value = "goodsName", required = false) String goodsName,
                                    @RequestParam(value = "createTime", required = false) String createTime,
                                    @RequestParam(value = "status", required = false) Integer status,
                                    @RequestParam(value = "isInPool", required = false) Integer isInPool,
                                    @RequestParam(value = "goodsId", required = false) String goodsId,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        // todo 入参判断
//        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, PageConsts.DEFAULT_PAGE_SIZE);//分页
        PageHelper.orderBy("id desc");
        List<CxActivityGoods> activityGoodsList = cxActivityGoodsService.listActivityGoods(activityId, goodsType, goodsName, goodsId, createTime, status, isInPool);
        PageInfo<CxActivityGoods> pageInfo = new PageInfo<>(activityGoodsList);

        PageResponse<CxActivityGoods> cxActivityGoodsPageResponse = new PageResponse<>(pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum(), activityGoodsList);
//        map.put("h活动奖品条数", pageInfo.getTotal());
//        map.put("奖品集合", activityGoodsList);
//        map.put("页数", pageInfo.getPages());
        return WrapMapper.ok(cxActivityGoodsPageResponse);
    }

    // 接口连通性，不知道是不是能成功
    @ApiOperation(value = "添加活动奖品", notes = "添加活动奖品")
//    consumes = "multipart/form-data" ,,
//            produces = { "application/json", "application/xml" }
    // @RequestParam(value = "file",required = false)MultipartFile file
    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    public Wrapper addActivityGoods(@ApiParam(name = "活动奖品对象", value = "需要添加的活动奖品对象", required = true)
                                    @RequestBody AddActivityGoodsRequest addActivityGoodsRequest
                                    ) throws Exception {
        // todo 入参判断，商品名称，商品权重是否在有效范围，商品总数不能为负，商品类型是否在有效范围内
        if (StringUtils.isBlank(addActivityGoodsRequest.getActivityId())) {
            return WrapMapper.wrap(201, "请填写奖品所属活动activityId");
        }
        // 新建存储ActivityGoods 存储对象
        CxActivityGoods activityGoods = new CxActivityGoods();
        BeanUtils.copyProperties(addActivityGoodsRequest, activityGoods);
        // goodsId生成逻辑
        String activityGoodsId = "GS" + System.currentTimeMillis() + RandomUtil.createNumberCode(6);
        activityGoods.setGoodsId(activityGoodsId);
        activityGoods.setCreateTime(new Date());
        activityGoods.setGoodsRemain(activityGoods.getGoodsTotal());
        // 设置默认的商品状态,默认下架
        activityGoods.setStatus(ActivityEnum.ACTIVITY_GOODS_STATUS_OFFLINE.getKey());
        // 设置默认的 商品是否在奖池
        activityGoods.setIsInPool(ActivityEnum.ACTIVITY_GOODS_IS_INPOOL.getKey());
        // 设置图片
        if(null != addActivityGoodsRequest.getFile()){
            String goodsImg = postActivityGoodsImg(addActivityGoodsRequest.getFile(), activityGoodsId);
            activityGoods.setGoodsImg(goodsImg);
        }
        cxActivityGoodsService.saveActivityGoods(activityGoods);
        return WrapMapper.ok();
    }

    // 上传 商品图片
//    @RequestMapping(value = "/goodsImg", method = RequestMethod.POST)
//    public Wrapper postActivityGoodsImg(@RequestParam(value = "file")MultipartFile file,
//                                        @RequestParam(required = false) String goodsId) throws Exception {
    private String postActivityGoodsImg(MultipartFile file, String goodsId) throws Exception {
        if(file.isEmpty()){
//            return WrapMapper.wrap(ResultEnum.FILE_IS_EMPTY.getCode(), ResultEnum.FILE_IS_EMPTY.getMessage());
            throw new ActivityException(ResultEnum.FILE_IS_EMPTY.getCode(),ResultEnum.FILE_IS_EMPTY.getMessage());
        }

        // 保存时的文件名字
        String originFileName = file.getOriginalFilename();
        String newFileName = null;
        if(null == goodsId){
            newFileName = "NEW" + RandomUtil.createNumberCode(6) + DateUtil.format(new Date(),"yyyyMMddHHmmss");
        } else {
            newFileName = goodsId + DateUtil.format(new Date(),"yyyyMMddHHmmss");
        }
        //获取后缀名
        String fileSuffix = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
        String ftpFileName = newFileName + fileSuffix;
        return FileToFtp.uploadFile(ftpFileName, ImageCompressUtil.saveMinPhoto(file.getInputStream(), 1080, 0.8));
    }

    // 测试图片上传
    @RequestMapping(value = "/goodsImg",method = RequestMethod.POST)
    public Wrapper postActivityGoodsImage(@RequestParam(value = "file")MultipartFile file,
                                        @RequestParam(required = false) String goodsId) throws Exception {
        if(file.isEmpty()){
            throw new ActivityException(ResultEnum.FILE_IS_EMPTY.getCode(),ResultEnum.FILE_IS_EMPTY.getMessage());
        }

        // 保存时的文件名字
        String originFileName = file.getOriginalFilename();
        String newFileName = null;
        if(null == goodsId){
            newFileName = "NEW" + RandomUtil.createNumberCode(6) + "_" + DateUtil.format(new Date(),"yyyyMMddHHmmss");
        } else {
            newFileName = goodsId + "_" + DateUtil.format(new Date(),"yyyyMMddHHmmss");
        }
        //获取后缀名
        String fileSuffix = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
        String ftpFileName = newFileName + fileSuffix;
//        System.out.println(ftpFileName);
        // 封装结果对象
        Map<String,String> map = new HashMap<>();
        String uploadFile = FileToFtp.uploadFile(ftpFileName, ImageCompressUtil.saveMinPhoto(file.getInputStream(), 1080, 0.8));
        map.put("goodsImg", uploadFile);
        return WrapMapper.ok(map);
    }

    // 获取奖品类型
    @RequestMapping(value = "/goodsType", method = RequestMethod.GET)
    public Wrapper getGoodsType(){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "实物");
        map1.put("value", 0);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "奖券");
        map2.put("value", 1);

        // 封装对象
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map1);
        mapList.add(map2);
        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("goodsType", mapList);
        return WrapMapper.ok(mapResult);
    }

    // 删除 活动商品
    @RequestMapping(value = "/deleteGoods", method = RequestMethod.GET)
    public Wrapper deleteGoods(@RequestParam String goodsId){

        if(cxActivityGoodsService.removeActivityGoods(goodsId)){
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getCode(), ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getMessage());
    }

    // 批量删除 活动商品
    @RequestMapping(value = "/deleteMultiGoods", method = RequestMethod.GET)
    public Wrapper deleteMultiGoods(@RequestParam(value = "list[]")List<String> goodsIdList){
        return WrapMapper.ok();
    }

    // 更新商品基本信息
    @RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
    public Wrapper putGoods(@RequestBody AddActivityGoodsRequest addActivityGoodsRequest) throws Exception {
        // todo 入参判断
        // 新建存储对象
        CxActivityGoods activityGoods = new CxActivityGoods();
        BeanUtils.copyProperties(addActivityGoodsRequest, activityGoods);
        // 如果文件不是空
        if(null != addActivityGoodsRequest.getFile()){
            String goodsImg = postActivityGoodsImg(addActivityGoodsRequest.getFile(), addActivityGoodsRequest.getGoodsId());
            activityGoods.setGoodsImg(goodsImg);
        }

        if(cxActivityGoodsService.updateActivityGoods(activityGoods)){
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getCode(), ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getMessage());
    }

    // 通过商品id 修改抽奖商品的权重
    @RequestMapping(value = "/goodsWeight", method = RequestMethod.POST)
    public Wrapper putActiveGoodsWeight(@RequestParam(value = "goodsId") String goodsId,
                                        @RequestParam(value = "goodsWeight") Integer goodsWeight) {
        // todo 入参判断，goodsId是否合法, 权重是否在有效区间

        if (cxActivityGoodsService.updateActivityGoodsWeight(goodsId, null, goodsWeight)) {
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getCode(), ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getMessage());
    }

    // 通过商品id 修改抽奖商品是否在奖池中
    @RequestMapping(value = "/goodsInPool", method = RequestMethod.POST)
    public Wrapper putActiveGoodsInPool(@RequestParam(value = "goodsId") String goodsId,
                                        @RequestParam(value = "isInPool") Integer isInPool) {
        // todo 入参判断，goodsId是否合法，是否在奖池是否在有效区间
        // 1表示是，0表示否，这里 0表示在，1表示不在

        if (cxActivityGoodsService.updateActivityGoodsInPool(goodsId, null, isInPool)) {
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getCode(), ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getMessage());
    }

    /* 中奖记录 管理部分 */

    /**
     * 通过iccid与vin查询中奖纪录
     *
     * @return Wrapper
     */
    // 查询逻辑 可以根据抽中人iccid,兑换码，订单号查询，使用者的手机号
//    @ApiOperation(value = "中奖纪录查询", notes = "通过iccid与vin查询中奖纪录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "iccid", value = "sim卡iccid号"),
//            @ApiImplicitParam(name = "vin", value = "车架号")
//    })
//    @RequestMapping(value = "/active/winning", method = RequestMethod.GET)
//    public Wrapper getMyAward(@RequestParam(value = "activityId") String activityId,
//                              @RequestParam(value = "iccid") String iccid,
//                              @RequestParam(value = "vin") String vin,
//                              @RequestParam(value = "userPhoneNum")String userPhoneNum) {
//        List<CxWinningRecord> winningRecordList = cxWinningRecordService.selectWiningRecord(activityId, iccid, vin,null);
//        List<ActivityGoods> activityGoodsList = new LinkedList<>();
//        for (CxWinningRecord winningRecord : winningRecordList) {
//            ActivityGoods activityGoods = new ActivityGoods();
//            CxActivityGoods activityGoodsInfo = cxActivityService.getGoodsInfo(winningRecord.getGoodsId(),null);
//            CxActivity activityInfo = cxActivityService.getActivityInfo(winningRecord.getActivityId());
//            activityGoods.setActivityName(activityInfo.getActivityName());
//            activityGoods.setEndTime(activityInfo.getEndTime());
//            activityGoods.setGoodsName(activityGoodsInfo.getGoodsName());
//            activityGoods.setGoodsImg(activityGoodsInfo.getGoodsImg());
//            activityGoods.setGoodsInfo(activityGoodsInfo.getGoodsInfo());
//            activityGoods.setGoodsDetail(activityGoodsInfo.getGoodsDetail());
//            activityGoodsList.add(activityGoods);
//        }
//        return WrapMapper.ok(activityGoodsList);
//    }

    // 修改整体逻辑 获取奖券记录
    @RequestMapping(value = "/activityRecordPage", method = RequestMethod.GET)
    public Wrapper getActivityRecord(@RequestParam(required = false) String activityId,
                                     @RequestParam(required = false) String iccid,
                                     @RequestParam(required = false) String exchangeCode,
                                     @RequestParam(required = false) String firstPhone,
                                     @RequestParam(required = false) String userPhone,
                                     @RequestParam(defaultValue = "1") Integer pageNum){
        PageResponse<WinningRecordVO> winningRecordVOPage = cxWinningRecordService.getWinningRecordVOPage(pageNum, activityId,iccid, exchangeCode, firstPhone, userPhone);
        return WrapMapper.ok(winningRecordVOPage);
    }

    // 订单详情，买了具体项

    // 奖券分发逻辑

}
