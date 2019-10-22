package com.xinba.isp.api.v1;

import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.*;
import com.xinba.isp.service.IspPushDataService;
import com.xinba.isp.util.XmlBuild;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/receive/api")
public class IspPushApi {
    @Resource
    private IspPushDataService ispPushDataService;

    //获取流量池并更新
    @RequestMapping(value = "/dataUsage", method = RequestMethod.POST)
    public Wrapper receiveDataUsage(@RequestParam(value = "data")String string) throws Exception {
        IspPushData ispPushData = (IspPushData) XmlBuild.xmlStrToOject(IspPushData.class, string);
        ispPushDataService.insertPoolData(ispPushData);
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/ctdUsage",method = RequestMethod.POST)
    public Wrapper receiveCtdUsage(@RequestParam(value = "data")String string) throws Exception {
        System.out.println("ctdUsage++++++++"+string+"++++++++++");
        CtdUsage ispPushData = (CtdUsage) XmlBuild.xmlStrToOject(CtdUsage.class, string);
        ispPushDataService.updateCtdUsage(ispPushData.getIccid(),ispPushData.getDataUsage());
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/ctdZUsage",method = RequestMethod.POST)
    public Wrapper receiveCtdZUsage(@RequestParam(value = "data")String string) throws Exception {
        System.out.println("ctdZUsage++++++++"+string+"++++++++++");
        CtdZUsage ispPushData = (CtdZUsage) XmlBuild.xmlStrToOject(CtdZUsage.class, string);
        ispPushDataService.updateCtdZUsage(ispPushData.getIccid(),ispPushData.getDataUsage(),ispPushData.getZoneName());
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/simFieldChange",method = RequestMethod.POST)
    public Wrapper receiveSimFieldChange(@RequestParam(value = "data")String string) throws Exception {
        System.out.println("simFieldChange++++++++"+string+"++++++++++");
        SimFieldChange ispPushData = (SimFieldChange) XmlBuild.xmlStrToOject(SimFieldChange.class, string);
        ispPushDataService.updateSimFieldChange(ispPushData.getIccid(),ispPushData.getOldValue(),ispPushData.getNewValue(),ispPushData.getFieldName());
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/simStateChange",method = RequestMethod.POST)
    public Wrapper receiveSimStateChange(@RequestParam(value = "data")String string) throws Exception {
        System.out.println("simStateChange++++++++"+string+"++++++++++");
        SimStateChange ispPushData = (SimStateChange) XmlBuild.xmlStrToOject(SimStateChange.class, string);
        ispPushDataService.updateSimStateChange(ispPushData.getIccid(),ispPushData.getCurrentState(),ispPushData.getDateChanged());
        return WrapMapper.ok();
    }

}
