package com.xinba.flow.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.TboxChangeRecord;
import com.xinba.flow.service.VinCarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vin")
public class VinCarApi {
    @Resource
    private VinCarService vinCarService;

    @RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
    public Wrapper getTboxChangeRecord(@RequestParam(value = "iccid", required = false) String iccid,
                                       @RequestParam(value = "vin", required = false) String vin,
                                       @RequestParam(value = "status",required = false)Integer status,
                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("id desc");
        List<IccidVin>iccidVinList=vinCarService.selectIccidVin(iccid,vin,status);
        PageInfo<IccidVin> pageInfo=new PageInfo(iccidVinList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",iccidVinList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    @RequestMapping(value = "/tboxChangeRecord",method = RequestMethod.GET)
    public Wrapper getTboxChangeRecord(@RequestParam(value = "vin", required = false) String vin,
                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("change_time desc");
        List<TboxChangeRecord>tboxChangeRecordList=vinCarService.getTboxChangeRecord(vin);
        PageInfo<TboxChangeRecord> pageInfo=new PageInfo(tboxChangeRecordList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",tboxChangeRecordList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }
}

