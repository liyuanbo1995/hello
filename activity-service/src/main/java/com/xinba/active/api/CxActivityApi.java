package com.xinba.active.api;


import com.xinba.active.common.ApiJsonObject;
import com.xinba.active.common.ApiJsonProperty;
import com.xinba.active.entity.CxActivity;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.entity.CxMember;
import com.xinba.active.entity.CxWinningRecord;
import com.xinba.active.entity.order.XfOrder;
import com.xinba.active.service.CxActivityService;
import com.xinba.active.service.CxMemberService;
import com.xinba.active.service.CxWinningRecordService;
import com.xinba.active.service.XfOrderService;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * (CxActivity)表控制层
 *
 * @author tsw
 * @since 2019-10-29 14:13:23
 */
@RestController
@Api(value = "促销活动active服务", tags = {"活动促销接口-active"})
@RequestMapping("/promote")
public class CxActivityApi {
    /**
     * 服务对象
     */
    @Autowired
    private CxActivityService cxActivityService;
    @Autowired
    private CxMemberService cxMemberService;
    @Autowired
    private CxWinningRecordService cxWinningRecordService;
    @Autowired
    private XfOrderService xfOrderService;


    /**
     * 活动抽取奖品接口
     *
     * @return Wrapper
     */
    @ApiOperation(value = "活动抽取奖品接口", notes = "活动抽取奖品接口")
    @RequestMapping(value = "/lottery",method = RequestMethod.POST)
    public Wrapper getLotteryGoods(
            @ApiJsonObject(name = "paramlg", value = {
                    @ApiJsonProperty(key = "activityId", example = "AC60619110038291671", description = "活动编号"),
                    @ApiJsonProperty(key = "iccid", example = "89860619110038291671", description = "Sim卡Iccid"),
                    @ApiJsonProperty(key = "vin", example = "LVVDB11B1HD110308", description = "车架号"),
                    @ApiJsonProperty(key = "phone", example = "18895376455", description = "手机号")
            })
            @RequestBody Map<String, Object> paramlg){
        String iccid = (String)paramlg.get("iccid");
        String vin = (String)paramlg.get("vin");
        String phone = (String)paramlg.get("phone");
        String activityId=(String)paramlg.get("activityId");
        if(activityId==null||activityId==""){return WrapMapper.wrap(106,"活动编号不能为空");}
        if(iccid==null||iccid==""){return WrapMapper.wrap(106,"iccid不能为空");}
        if(phone==null||phone==""){return WrapMapper.wrap(106,"phone不能为空");}
        return  cxActivityService.getLotteryGoods(activityId,iccid,vin,phone);
    }

    /**
     * 抽奖资格查询
     *
     * @return Wrapper
     */
    @ApiOperation(value = "抽奖资格查询", notes = "通过iccid与vin查询是否有抽奖资格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "sim卡iccid号"),
            @ApiImplicitParam(name = "vin", value = "车架号")
    })
    @RequestMapping(value = "/qualify",method = RequestMethod.GET)
    public Wrapper viewMyLuckyDraw(@RequestParam(value = "iccid")String iccid,
                                   @RequestParam(value = "vin",required = false)String vin,
                                   @RequestParam(value = "activityId")String activityId){
        CxMember cxMember=cxMemberService.viewMemberRecord(iccid,vin,activityId);
        Map<String,Object>map=new HashMap<>();
        if(cxMember!=null){
            map.put("activityTimes",cxMember.getActivityTimes());
            map.put("phone",cxMember.getPhone());
            return WrapMapper.ok(map);
        }
        return WrapMapper.wrap(102,"你没资格抽奖");
    }


    @ApiOperation(value = "进入页面，将iccid、vin插入人员记录", notes = "插入iccid与vin，生成一条用户记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "sim卡iccid号"),
            @ApiImplicitParam(name = "vin", value = "车架号"),
            @ApiImplicitParam(name = "activityId", value = "活动编号")
    })
    @RequestMapping(value = "/memberRecord",method = RequestMethod.GET)
    public Wrapper viewMemberRecord(@RequestParam(value = "iccid")String iccid,
                                    @RequestParam(value = "vin",required = false)String vin,
                                    @RequestParam(value = "activityId")String activityId){
        CxMember cxMember=cxMemberService.viewMemberRecord(iccid,vin,activityId);
        Map<String,Object>map=new HashMap<>();
        if(cxMember!=null){
            map.put("activityTimes",cxMember.getActivityTimes());
            return WrapMapper.ok(map);
        }
        return WrapMapper.wrap(108,"无抽奖次数");
    }

    @ApiOperation(value = "根据兑奖码兑换", notes = "根据兑奖码兑换")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "sim卡iccid号"),
            @ApiImplicitParam(name = "vin", value = "车架号"),
            @ApiImplicitParam(name = "exchangeCode", value = "兑奖码"),
            @ApiImplicitParam(name = "activityId", value = "活动编码")
    })
    @RequestMapping(value = "/getGoodsByCode", method = RequestMethod.GET)
    public Wrapper getGoodsByCode(@RequestParam(value = "iccid") String iccid,
                                  @RequestParam(value = "vin",required = false) String vin,
                                  @RequestParam(value = "exchangeCode") String exchangeCode,
                                  @RequestParam(value = "activityId") String activityId) {
        int result=cxWinningRecordService.getGoodsByExchangeCode(iccid, vin, StringUtils.lowerCase(exchangeCode),activityId);
        if(result==2){
            return WrapMapper.ok("兑换成功");
        }else if(result==1){
            return WrapMapper.wrap(104,"兑换方式无效");
        }
        return WrapMapper.wrap(104,"兑奖码无效");
    }

    @ApiOperation(value = "我的奖券", notes = "我的奖券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "sim卡iccid号"),
            @ApiImplicitParam(name = "vin", value = "车架号"),
            @ApiImplicitParam(name = "activityId", value = "活动编号"),
    })
    @RequestMapping(value = "/getMyGoods", method = RequestMethod.GET)
    public Wrapper getMyGoods(@RequestParam(value = "iccid") String iccid,
                              @RequestParam(value = "vin",required = false) String vin,
                              @RequestParam(value = "activityId",required = false) String activityId,
                              @RequestParam(value = "status",required = false) String status) {
       Map<String,Object>map=cxWinningRecordService.getMyGoods(activityId,iccid,vin,status);
        return WrapMapper.ok(map);
    }

    @ApiOperation(value = "获取活动商品", notes = "获取活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动编号"),
//            @ApiImplicitParam(name = "status", value = "状态，0正常，1停用"),
//            @ApiImplicitParam(name = "isInPool", value = "是否在奖池，0在，1不在"),
            @ApiImplicitParam(name = "count", value = "需要获取几个奖品")
    })
    @RequestMapping(value = "/getGoods", method = RequestMethod.GET)
    public Wrapper getGoods(@RequestParam(value = "activityId") String activityId,
//                            @RequestParam(value = "status", required = false) Integer status,
//                            @RequestParam(value = "isInPool", required = false) Integer isInPool,
                            @RequestParam(value = "count") Integer count) {
        Map<String, Object> map = new HashMap<>();
        List<CxActivityGoods> activityGoodsList = cxActivityService.selectAcGoods(activityId, 0, 0, 8);
        map.put("goods", activityGoodsList);
        return WrapMapper.ok(map);
    }


    @ApiOperation(value = "添加地址", notes = "添加地址")
    @RequestMapping(value = "/addAddress",method = RequestMethod.POST)
    public Wrapper addAdress(
            @ApiJsonObject(name = "map", value = {
                    @ApiJsonProperty(key = "activityId", example = "AC125412154", description = "活动编号"),
                    @ApiJsonProperty(key = "iccid", example = "89860619110038291671", description = "Sim卡Iccid"),
                    @ApiJsonProperty(key = "vin", example = "LVVDB11B1HD110308", description = "车架号"),
                    @ApiJsonProperty(key = "name", example = "夏青青", description = "姓名"),
                    @ApiJsonProperty(key = "phone", example = "15461316565", description = "电话号码"),
                    @ApiJsonProperty(key = "province", example = "江苏省", description = "省份"),
                    @ApiJsonProperty(key = "city", example = "南京市", description = "城市"),
                    @ApiJsonProperty(key = "area", example = "栖霞区", description = "区域"),
                    @ApiJsonProperty(key = "details", example = "汇智科技园", description = "详情")
            })
            @RequestBody Map<String, Object> map) {
        String iccid = (String) map.get("iccid");
        String vin = (String) map.get("vin");
        String activityId = (String) map.get("activityId");
        String name = (String) map.get("name");
        String phone = (String) map.get("phone");
        String province = (String) map.get("province");
        String city = (String) map.get("city");
        String area = (String) map.get("area");
        String details = (String) map.get("details");
        if(activityId==null||activityId==""){return WrapMapper.wrap(106,"活动编号不能为空");}
        if(iccid==null||iccid==""){return WrapMapper.wrap(106,"iccid不能为空");}
        cxMemberService.addAdress(activityId,iccid,vin,name,phone,province,city,area,details);
        return WrapMapper.ok();
    }


    @ApiOperation(value = "回传地址和选中奖券详情", notes = "回传地址和选中奖券详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "iccid"),
            @ApiImplicitParam(name = "vin", value = "vin"),
            @ApiImplicitParam(name = "id", value = "id")
    })
    @RequestMapping(value = "/getAddressAndGoodsInfo", method = RequestMethod.GET)
    public Wrapper getAddressAndGoodsInfo(@RequestParam(value = "iccid") String iccid,
                            @RequestParam(value = "id") Integer id,
                            @RequestParam(value = "vin",required = false) String vin){
        Map<String,Object>map=cxWinningRecordService.getAddressAndGoodsInfo(id,iccid,vin);
        return WrapMapper.ok(map);
    }

    @ApiOperation(value = "获取我的订单", notes = "获取我的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "iccid"),
            @ApiImplicitParam(name = "vin", value = "vin")
    })
    @RequestMapping(value = "/getMyOrder", method = RequestMethod.GET)
    public Wrapper getMyOrder(@RequestParam(value = "iccid") String iccid,
                              @RequestParam(value = "payStatus",required = false) String payStatus,
                              @RequestParam(value = "orderStatus",required = false) String orderStatus){
        Map<String,Object>map=new HashMap<>();
        List<XfOrder>myOrderList=xfOrderService.selectMyOrder(iccid,payStatus,orderStatus);
        map.put("myOrderList",myOrderList);
        return WrapMapper.ok(map);

    }

    @ApiOperation(value = "选择奖券", notes = "选择奖券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iccid", value = "sim卡iccid号"),
            @ApiImplicitParam(name = "vin", value = "车架号"),
            @ApiImplicitParam(name = "activityId", value = "活动编号")
    })
    @RequestMapping(value = "/selectMyGoods", method = RequestMethod.GET)
    public Wrapper selectMyGoods(@RequestParam(value = "iccid") String iccid,
                              @RequestParam(value = "vin",required = false) String vin,
                              @RequestParam(value = "activityId",required = false) String activityId) {
        Map<String,Object>map=new HashMap<>();
        List<CxWinningRecord>canUseList=cxWinningRecordService.selectMyGoods(activityId,iccid,vin);
        map.put("canUseList",canUseList);
        return WrapMapper.ok(map);
    }

    @ApiOperation(value = "滚动中奖记录", notes = "滚动中奖记录")
    @RequestMapping(value = "/scrollWinner", method = RequestMethod.GET)
    public Wrapper scrollWinnerRecord(){
        Map<String,Object>map=new HashMap<>();
        List<CxWinningRecord>winningRecordList=cxWinningRecordService.scrollWinnerRecord();
        map.put("winningRecordList",winningRecordList);
        return WrapMapper.ok(map);
    }


//    @ApiOperation(value = "获取中奖名单", notes = "获取中奖名单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "activityId", value = "活动编号"),
////            @ApiImplicitParam(name = "status", value = "状态，0正常，1停用"),
////            @ApiImplicitParam(name = "isInPool", value = "是否在奖池，0在，1不在"),
//            @ApiImplicitParam(name = "count", value = "需要获取几个奖品")
//    })
//    @RequestMapping(value = "/getWinningRecord", method = RequestMethod.GET)
//    public Wrapper getWinningRecord(@RequestParam(value = "activityId") String activityId){
//
//    }

}