package com.xinba.isp.api.v1;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.ErrorInfo;
import com.xinba.isp.entity.IspPushData;
import com.xinba.isp.entity.SimInfo;
import com.xinba.isp.entity.form.PoolGroup;
import com.xinba.isp.entity.charts.SimChangeInfo;
import com.xinba.isp.feign.JasperApiService;
import com.xinba.isp.service.IspEsbService;
import com.xinba.isp.service.IspPushDataService;
import com.xinba.isp.util.CombineBeans;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/devices")
public class DevicesApi {

    @Resource
    private JasperApiService jasperApiService;
    @Resource
    private IspPushDataService ispPushDataService;
    @Resource
    private IspEsbService ispEsbService;


    @RequestMapping(value = "/sim_info", method = RequestMethod.GET)
    public Wrapper getSimInfo(@RequestBody String str) {
        String res = jasperApiService.getSimInfo(str);
        JSONObject json = JSONObject.parseObject(res);
        String errorStatus = json.getString("errorStatus");
        if (StringUtils.isNotBlank(errorStatus)) {
            JSONObject rejson = JSONObject.parseObject(json.getString("result"));
            String errorMessage = rejson.getString("errorMessage");
            String errorCode = rejson.getString("errorCode");
            System.out.println(errorMessage + "~~~~~~~~~~~~" + errorCode);
        }
        SimInfo ispPoolData = json.toJavaObject(SimInfo.class);
        System.out.println(ispPoolData.toString());

        return WrapMapper.ok(res);
    }

    //查询流量池
    @RequestMapping(value = "/pool_page",method = RequestMethod.GET)
    public Wrapper  queryPoolPageInfo(@RequestParam (value = "pageNum",defaultValue = "1") int pageNum,
                                      @RequestParam (value = "search") String search,
                                      @RequestParam (value = "accountName") String accountName,
                                      @RequestParam (value = "zoneName") String zoneName,
                                      @RequestParam (value = "ratePlanName") String ratePlanName){
        Map<String, Object> map = new HashMap<>();
        map.put("search",search);
        map.put("accountName",accountName);
        map.put("zoneName",zoneName);
        map.put("ratePlanName",ratePlanName);
        int pageSize = 15;
        PageHelper.startPage(pageNum,pageSize);//分页
        List<IspPushData> poolList = ispPushDataService.queryPoolData(map);
        PageInfo<ErrorInfo> pageInfo=new PageInfo(poolList,pageSize);
        map.clear();
        map.put("items", pageInfo.getTotal());
        map.put("list",poolList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    //查询流量池
    @RequestMapping(value = "/pool_charts",method = RequestMethod.GET)
    public Wrapper  queryPoolCharts(@RequestParam (value = "account") String account){
        Map<String,Object> map = new HashMap<>();
        map.put("accountName", account);
        map.put("isLast", "ok");
        List<IspPushData> poolList = ispPushDataService.queryPoolData(map);
        return WrapMapper.ok(poolList);
    }

    //查询流量池
    @RequestMapping(value = "/pool_group",method = RequestMethod.GET)
    public Wrapper  poolGroup(@RequestParam (value = "column") String column){
        List<PoolGroup> poolGroupList = ispPushDataService.queryPoolGroup(column);
        return WrapMapper.ok(poolGroupList);
    }

    //获取未成功同步信息的卡
    @RequestMapping(value = "/failSyn",method = RequestMethod.GET)
    public Wrapper getFailSynSim(@RequestParam (value = "pageNum",defaultValue = "1") int pageNum){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("error_date desc");
        List<ErrorInfo>errorInfoList= ispPushDataService.getFailSynSim();
        PageInfo<ErrorInfo> pageInfo=new PageInfo(errorInfoList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",errorInfoList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    //同步错误列列表中的卡
    @RequestMapping(value = "/synErrorSim",method = RequestMethod.PUT)
    public Wrapper synErrorSimInfo() {
        List<String> iccidList = ispPushDataService.getAllFailSimIccid();
        int errorItems=0;
        for (String iccid : iccidList) {
            String res = jasperApiService.getSimInfo(iccid);
            String resUsage=jasperApiService.getSimUsages(iccid);
            JSONObject json,usageJson;
            try {
                json = JSONObject.parseObject(res);
            } catch (Exception e) {
                e.printStackTrace();
                ispPushDataService.updateSimError(iccid, "500", res);
                errorItems++;
                continue;
            }
            try {
                usageJson = JSONObject.parseObject(resUsage);
            } catch (Exception e) {
                e.printStackTrace();
                ispPushDataService.updateSimError(iccid, "500", resUsage);
                errorItems++;
                continue;
            }
            String errorStatus = json.getString("errorStatus");
            String errorCode = json.getString("errorCode");
            String usageErrorStatus = json.getString("errorStatus");
            String usageErrorCode = json.getString("errorCode");
            if (StringUtils.isNotBlank(errorStatus)) {
                JSONObject rejson = JSONObject.parseObject(json.getString("result"));
                ispPushDataService.updateSimError(iccid,  rejson.getString("errorCode"), rejson.getString("errorMessage"));
                errorItems++;
                continue;
            }
            if(StringUtils.isNotBlank(errorCode)){
                ispPushDataService.updateSimError(iccid, json.getString("errorCode"), json.getString("errorMessage"));
                errorItems++;
                continue;
            }
            if (StringUtils.isNotBlank(usageErrorStatus)) {
                JSONObject rejson = JSONObject.parseObject(usageJson.getString("result"));
                ispPushDataService.updateSimError(iccid,  rejson.getString("errorCode"), rejson.getString("errorMessage"));
                errorItems++;
                continue;
            }
            if(StringUtils.isNotBlank(usageErrorCode)){
                ispPushDataService.updateSimError(iccid, usageJson.getString("errorCode"), usageJson.getString("errorMessage"));
                errorItems++;
                continue;
            }
            SimInfo simInfo = json.toJavaObject(SimInfo.class);
            simInfo.setUpdateTime(new Date());
            SimInfo usageSimInfo = usageJson.toJavaObject(SimInfo.class);
           CombineBeans.combineSydwCore(usageSimInfo,simInfo);
            ispPushDataService.updateSimInfo(simInfo);
            ispPushDataService.updateSimErrorStatus(iccid);
        }
        if(errorItems==0){
            return WrapMapper.wrap(200,"成功"+iccidList.size()+"条;失败"+errorItems+"条");
        }else {
            return WrapMapper.wrap(500,"成功"+(iccidList.size()-errorItems)+"条;失败"+errorItems+"条");
        }
    }

    //单卡同步信息
    @RequestMapping(value = "/single/synSim",method = RequestMethod.POST)
    public Wrapper synSingleSim(@RequestParam("iccid")String iccid){
        String res = jasperApiService.getSimInfo(iccid);
        String resUsage=jasperApiService.getSimUsages(iccid);
        try {
            JSONObject json = JSONObject.parseObject(res);
            JSONObject json1 = JSONObject.parseObject(resUsage);
            SimInfo simInfo = json.toJavaObject(SimInfo.class);
            simInfo.setUpdateTime(new Date());
            SimInfo simUsage = json1.toJavaObject(SimInfo.class);
            CombineBeans.combineSydwCore(simUsage,simInfo);
            ispPushDataService.updateSimInfo(simInfo);
            SimInfo simInfo1= ispPushDataService.selectSimInfo(iccid);
            return WrapMapper.ok(simInfo1);
        } catch (Exception e) {
            return WrapMapper.wrap(500,"同步失败，"+e.getMessage());
        }
    }

    //修改卡的信息，例如 SIM 卡状态、资费计划、通信计划和自定义字段
    @RequestMapping( value = "/changeInfo",method = RequestMethod.PUT)
    public Wrapper changeSimInfo(@RequestBody SimInfo simInfo) {
        SimChangeInfo simChangeInfo = new SimChangeInfo();
        simChangeInfo.setChangeInfo(simInfo, null);
        String res = jasperApiService.updateSimInfo(simInfo.getIccid(), simChangeInfo);
        JSONObject json = JSONObject.parseObject(res);
        String errorCode = json.getString("errorCode");
        String errorMessage = json.getString("errorMessage");
        if (errorCode != null) {
            return WrapMapper.wrap(Integer.valueOf(errorCode), errorMessage);
        } else {
            ispPushDataService.updateSimInfo(simInfo);
            return WrapMapper.ok();
        }
    }


    //激活卡
    @RequestMapping( value = "/active",method = RequestMethod.PUT)
    public Wrapper simActive(@RequestParam(value = "iccid")String iccid) {
        int items=ispEsbService.getActiveRecord(iccid);
        if(items>0){
            return WrapMapper.wrap(500,"该卡已激活");
        }
        SimChangeInfo simChangeInfo = new SimChangeInfo();
        String newdate;
        simChangeInfo.setStatus("Active");
        if (DateUtil.thisDayOfMonth() > 26) {
            if (DateUtil.thisMonth() < 10) {
                newdate = String.valueOf(DateUtil.thisYear() + 3) + "0" + (DateUtil.thisMonth() + 1) + "26";
            } else {
                newdate = String.valueOf(DateUtil.thisYear() + 3) + (DateUtil.thisMonth() + 1) + "26";
            }
        } else {
            newdate = String.valueOf(DateUtil.thisYear() + 3) + DateUtil.thisMonth() + "26";
        }
        simChangeInfo.setAccountCustom1(newdate);
        simChangeInfo.setAccountCustom2("6000");
        String res = jasperApiService.updateSimInfo(iccid, simChangeInfo);
        JSONObject json = JSONObject.parseObject(res);
        String errorStatus = json.getString("errorStatus");
        if (errorStatus !=null) {
            JSONObject jsonResult = JSONObject.parseObject(json.getString("result"));
            return WrapMapper.wrap(Integer.valueOf(errorStatus), jsonResult.getString("errorMessage"));
        } else {
            ispEsbService.insertActiveOrUnbindRecord(iccid, "Active", null);
            return WrapMapper.ok();
        }
    }

    //解绑
    @RequestMapping( value = "/Unbind",method = RequestMethod.PUT)
    public Wrapper simUnbind(@RequestParam(value = "iccid")String iccid) {
//        MessageText messageText=new MessageText();
//        messageText.setMessageText("JB123456");
        String messageText="{\"messageText\":\"JB123456\"}";
        String res=jasperApiService.simUnbind(iccid,messageText);
        JSONObject json = JSONObject.parseObject(res);
        String errorStatus = json.getString("errorStatus");
        if (errorStatus !=null) {
            JSONObject jsonResult = JSONObject.parseObject(json.getString("result"));
            return WrapMapper.wrap(Integer.valueOf(errorStatus), jsonResult.getString("errorMessage"));
        } else {
            ispEsbService.insertActiveOrUnbindRecord(iccid, "Unbind", null);
            return WrapMapper.ok();
        }
    }
}

