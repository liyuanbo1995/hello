package com.xinba.flow.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.GcFirm;
import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.GcFirmType;
import com.xinba.flow.entity.SimInfo;
import com.xinba.flow.service.GcFirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class GcApi {
    @Autowired
    private GcFirmService gcFirmService;

    //添加客户及其产品的基础信息
    @RequestMapping(value = "/firm",method = RequestMethod.POST)
    public Wrapper addFrimInfo(@RequestBody GcFirm gcFirm){
        gcFirmService.addFrimInfo(gcFirm);
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/firm/contact",method = RequestMethod.POST)
    public Wrapper addContactPhone(@RequestBody String str ){
        JSONObject json = JSONObject.parseObject(str);
        Integer id= json.getIntValue("id");
        String contact=json.getString("contact");
        String phone=json.getString("phone");
        gcFirmService.addContactPhone(Integer.valueOf(id),contact,phone);
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/type",method = RequestMethod.POST)
    public Wrapper addFirmType(@RequestBody GcFirmType gcFirmType){
        gcFirmService.addFirmType(gcFirmType);
        return WrapMapper.ok();
    }

    @RequestMapping(value = "product",method = RequestMethod.POST)
    public Wrapper addFirmProduct(@RequestBody GcFirmProduct gcFirmProduct){
        gcFirmService.addFirmProduct(gcFirmProduct);
        return WrapMapper.ok();
    }

    //查询客户公司以及客户公司下的产品类型以及类型下的产品系列
    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Wrapper selectFirmProduct(){
        List<GcFirm> gcFirmList=gcFirmService.selectFirm("");//查询所有客户公司
        for(int i=0;i<gcFirmList.size();i++){
            List<GcFirmType> gcFirmTypeList =gcFirmService.selectProductType(gcFirmList.get(i).getId(),"");
            gcFirmList.get(i).setChildren(gcFirmTypeList);
            for(int j = 0; j< gcFirmTypeList.size(); j++){
                List<GcFirmProduct> gcFirmProductList =gcFirmService.selectProduct(gcFirmTypeList.get(j).getId(),"");
                gcFirmTypeList.get(j).setChildren(gcFirmProductList);
            }
        }
        return WrapMapper.ok(gcFirmList);
    }

    //查询客户
    @ResponseBody
    @RequestMapping(value = "/firm",method = RequestMethod.GET)
    public Wrapper getFirmInfo(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                               @RequestParam(value = "firmName",required = false)String firmName){
        Map<Object,Object>map=new HashMap<>();
        Map<String,String>map1=new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("id desc");
        List<GcFirm>gcFirmList=gcFirmService.selectFirm(firmName);
        PageInfo<GcFirm> pageInfo=new PageInfo(gcFirmList,pageSize);
        map.put("items",pageInfo.getTotal());
        for(int i=0;i<gcFirmList.size();i++){
            if(gcFirmList.get(i).getPhone()!=null) {
                String[] phone = gcFirmList.get(i).getPhone().split("_");
                String[] contact = gcFirmList.get(i).getContact().split("_");
                gcFirmList.get(i).setContactArray(contact);
                gcFirmList.get(i).setPhoneArray(phone);
                gcFirmList.get(i).setContact(contact[0]);
                gcFirmList.get(i).setPhone(phone[0]);
            }
        }
        map.put("list",gcFirmList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public Wrapper getFirmTypeInfo(@RequestParam("firmId")Integer firmId,
                                   @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                   @RequestParam(value = "typeName",required = false)String typeName){
        Map<String,Object>map=new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("id desc");
        List<GcFirmType>gcFirmTypeList=gcFirmService.selectProductType(firmId,typeName);
        PageInfo<GcFirmType> pageInfo=new PageInfo(gcFirmTypeList,pageSize);
        GcFirm gcFirmInfo=gcFirmService.getFirmInfo(firmId);
        if(gcFirmInfo.getPhone()!=null) {
            String[] phone = gcFirmInfo.getPhone().split("_");
            String[] contact = gcFirmInfo.getContact().split("_");
            gcFirmInfo.setContactArray(contact);
            gcFirmInfo.setPhoneArray(phone);
            gcFirmInfo.setContact(contact[0]);
            gcFirmInfo.setPhone(phone[0]);
        }
        map.put("firmInfo",gcFirmInfo);
        map.put("typeList",gcFirmTypeList);
        map.put("items",pageInfo.getTotal());
        return WrapMapper.ok(map);
    }

    @RequestMapping(value = "/product",method = RequestMethod.GET)
    public Wrapper getTypeProductInfo(@RequestParam("typeId")Integer typeId,
                                      @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                      @RequestParam(value = "productName",required = false)String productName){
        Map<String,Object>map=new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("id desc");
        List<GcFirmProduct>gcProductList=gcFirmService.selectProduct(typeId,productName);
        PageInfo<GcFirmProduct> pageInfo=new PageInfo(gcProductList,pageSize);
        GcFirmType gcTypeInfo=gcFirmService.getTypeInfo(typeId);
        map.put("typeInfo",gcTypeInfo);
        map.put("productList",gcProductList);
        map.put("items",pageInfo.getTotal());
        return WrapMapper.ok(map);
    }

}
