package com.xinba.flow.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.SimActiveUnbind;
import com.xinba.flow.entity.SimInfo;
import com.xinba.flow.entity.XfOrder;
import com.xinba.flow.entity.form.LabelValue;
import com.xinba.flow.service.SimInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * (SimInfo)表控制层
 *
 * @author tsw
 * @since 2019-07-25 14:01:09
 */
@RestController
@RequestMapping("/sim")
public class SimInfoApi {
    /**
     * 服务对象
     */
    @Resource
    private SimInfoService simInfoService;

    //excel文件批量导入卡
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Wrapper importSimInfoByFile(@RequestParam("file") MultipartFile file){
        String fileName=file.getOriginalFilename();
        try {
            return simInfoService.addSimAuto(fileName,file);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error();
        }
    }

    //按条件查询卡数据
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Wrapper getSimInfoByConditon(@RequestParam(value = "updateTime",required = false)String updateTime,
                                    @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                    @RequestParam(value = "iccid",required = false)String iccid,
                                        @RequestParam(value = "phone",required = false)String phone,
                                        @RequestParam(value = "vin",required = false)String vin,
                                        @RequestParam(value = "msisdn",required = false)String msisdn,
                                        @RequestParam(value = "status",required = false)String status,
                                        @RequestParam(value = "ratePlan",required = false)String ratePlan,
                                        @RequestParam(value = "communicationPlan",required = false)String communicationPlan){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("update_time desc");
        List<SimInfo>simInfoList=simInfoService.selectSimInfo(iccid,updateTime,phone,vin,msisdn,status,ratePlan,communicationPlan);
        PageInfo<SimInfo> pageInfo=new PageInfo(simInfoList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",simInfoList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    //excel文件导入卡、vin、time
    @ResponseBody
    @RequestMapping(value = "/vin",method = RequestMethod.POST)
    public Wrapper importSimVinInfo(@RequestParam("file") MultipartFile file){
        String fileName=file.getOriginalFilename();
        try {
            return simInfoService.addSimVinInfo(fileName,file);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error();
        }
    }

    //给出资费计划和通讯计划
    @RequestMapping(value = "/plan",method = RequestMethod.GET)
    public Wrapper getSimPlan(){
        Map<String,Object>map=new HashMap<>();
        List<String>ratePlanList=simInfoService.groupByRatePlan();
        List<String>communicationPlanList=simInfoService.groupByCommunicationPlan();
        List<LabelValue>list1=new LinkedList<>();
        List<LabelValue>list2=new LinkedList<>();
        LabelValue labelValue1=new LabelValue();
        labelValue1.setLabel("所有");
        list1.add(labelValue1);
        for(int i=0;i<ratePlanList.size();i++){
            if(ratePlanList.get(i)!=null){
                LabelValue labelValue=new LabelValue();
                labelValue.setLabel(ratePlanList.get(i));
                labelValue.setValue(ratePlanList.get(i));
                list1.add(labelValue);
            }
        }
        LabelValue labelValue2=new LabelValue();
        labelValue2.setLabel("所有");
        list2.add(labelValue2);
        for(int i=0;i<ratePlanList.size();i++){
            if(communicationPlanList.get(i)!=null){
                LabelValue labelValue=new LabelValue();
                labelValue.setLabel(communicationPlanList.get(i));
                labelValue.setValue(communicationPlanList.get(i));
                list2.add(labelValue);
            }
        }
        map.put("ratePlan",list1);
        map.put("communicationPlan",list2);
        return WrapMapper.ok(map);
    }

    //查询激活卡操作记录
    @RequestMapping(value = "/activeOrUnbind",method = RequestMethod.GET)
    public Wrapper getSimActiveOrUnbindRecord(@RequestParam(value = "iccid",required = false)String iccid,
                                              @RequestParam(value = "beginTime",required = false)String beginTime,
                                              @RequestParam(value = "endTime",required = false)String endTime,
                                              @RequestParam(value = "operation",required = false)String status,
                                              @RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("create_time desc");
        List<SimActiveUnbind>recordList=simInfoService.getSimActiveOrUnbindRecord(iccid,beginTime,endTime,status);
        PageInfo<XfOrder> pageInfo=new PageInfo(recordList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",recordList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    @RequestMapping(value = "/activeRecord",method = RequestMethod.POST)
    public Wrapper importSimActiveRecordByFile(@RequestParam("file") MultipartFile file){
        String fileName=file.getOriginalFilename();
        try {
            return simInfoService.addSimActiveRecord(fileName,file);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error();
        }
    }

}