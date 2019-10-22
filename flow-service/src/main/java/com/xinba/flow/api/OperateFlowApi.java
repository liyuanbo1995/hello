package com.xinba.flow.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.algorithm.Algorithm;
import com.xinba.flow.entity.*;
import com.xinba.flow.entity.form.CarData;
import com.xinba.flow.entity.form.CarName;
import com.xinba.flow.entity.form.FirmTypeProduct;
import com.xinba.flow.resonse.RelationResponse;
import com.xinba.flow.service.OperationFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pack")
public class OperateFlowApi {
    @Autowired
    private OperationFlowService operationFlowService;

    //添加运营商套餐
    @ResponseBody
    @RequestMapping(value = "/base",method = RequestMethod.POST)
    public Wrapper addOperationPackage(@RequestBody(required = false) OperationFlow operationFlow){
        try {
            operationFlowService.insertBasePack(operationFlow);
        }catch(Exception e){
            return WrapMapper.wrap(500,"套餐标识已存在");
        }
        return WrapMapper.ok();
    }

    //查询所有运营商套餐
    @ResponseBody
    @RequestMapping(value = "/base",method = RequestMethod.GET)
    public Wrapper<Map<String,Object>> selectAllPa(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum ,
                                                   @RequestParam(value = "name",required = false)String name,
                                                   @RequestParam(value = "code",required = false)String code,
                                                   @RequestParam(value = "type",required = false)Integer type){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("time desc");
        List<OperationFlow>operationFlowList=operationFlowService.selectOperationPack(name,code,type);
        PageInfo<OperationFlow> pageInfo=new PageInfo(operationFlowList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",operationFlowList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

//    //按id查询运营商套餐
//    @ResponseBody
//    @RequestMapping(value = "/operation/package",method = RequestMethod.GET)
//    public OperationFlow selectPackById(@PathParam("id")int id){
//        return operationFlowService.selectPackById(id);
//    }

    //删除某个运营商套餐
    @ResponseBody
    @RequestMapping(value = "/base",method = RequestMethod.DELETE)
    public Wrapper deletePack(@PathParam("id")int id){
        operationFlowService.deleteByCode(id);
        return WrapMapper.ok();
    }
    //更改某个套餐状态
    @ResponseBody
    @RequestMapping(value = "/base/status",method = RequestMethod.PUT)
    public Wrapper updateStatus(@PathParam("id")int id){
        operationFlowService.updateStatus(id);
        return WrapMapper.ok();
    }

    //添加车厂实例套餐关系
    @ResponseBody
    @RequestMapping(value = "/instance",method = RequestMethod.POST)
    public Wrapper  addCarPa(@RequestBody CarData carData){
        Integer[] products=carData.getProducts();
        Integer[] pack=carData.getPack();
        for(int j=0;j<pack.length;j++){
            int count=0;
            if(products.length==1){
                count=operationFlowService.countItems(products[0],null,null,pack[j]);
            }else if(products.length==2){
                count=operationFlowService.countItems(products[0],products[1],null,pack[j]);
            }else {
                count=operationFlowService.countItems(products[0],products[1],products[2],pack[j]);
            }
            if(count!=0){
                return WrapMapper.wrap(50,"该客户与"+operationFlowService.getPackById(pack[j]).getName()+"的套餐已存在");
            }
        }
         for(int i=0;i<pack.length;i++){
             TcFirmPack tcFirmPack=new TcFirmPack();
             tcFirmPack.setPackId(pack[i]);
             tcFirmPack.setFirmId(products[0]);
             if(products.length==2){
                 tcFirmPack.setFirmTypeId(products[1]);
             }else if(products.length==3){
                 tcFirmPack.setFirmTypeId(products[1]);
                 tcFirmPack.setTypeProductId(products[2]);
             }
             TcPack tcPack=operationFlowService.getPackById(pack[i]);
             tcFirmPack.setName(tcPack.getName());
             tcFirmPack.setContent(tcPack.getContent());
             tcFirmPack.setCost(tcPack.getCost());
             tcFirmPack.setCycle(tcPack.getCycle());
             tcFirmPack.setStrCycle(tcPack.getStrCycle());
             tcFirmPack.setStandard(tcPack.getStandard());
             tcFirmPack.setStrStandard(tcPack.getStrStandard());
             tcFirmPack.setType(tcPack.getType());
             operationFlowService.insertFirmPack(tcFirmPack);
         }
       return WrapMapper.ok();
    }

    //查询所有车厂实例套餐
    @ResponseBody
    @RequestMapping(value = "/instance",method = RequestMethod.GET)
    public Wrapper<Map<String,Object>> selectAllFirmPa(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                       @RequestParam(value = "name",required = false)String name,
                                                       @RequestParam(value = "info",required = false)String info,
                                                       @RequestParam(value = "products",required = false)Integer[] products){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("create_time desc");
        List<TcFirmPack>tcFirmPackList=operationFlowService.selectAllFirmPa(name,info,products);
        for(int i=0;i<tcFirmPackList.size();i++){
            FirmTypeProduct firmTypeProduct=operationFlowService.getCustomerName(tcFirmPackList.get(i).getFirmId(), tcFirmPackList.get(i).getFirmTypeId(),tcFirmPackList.get(i).getTypeProductId());
            tcFirmPackList.get(i).setFirmName(firmTypeProduct.getFirmName());
            tcFirmPackList.get(i).setFirmTypeName(firmTypeProduct.getFirmTypeName());
            tcFirmPackList.get(i).setTypeProductName(firmTypeProduct.getTypeProductName());
        }
        PageInfo<TcFirmPack> pageInfo=new PageInfo(tcFirmPackList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",tcFirmPackList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok(map);
    }

    //查询某个模板套餐对应运营商套餐
    @ResponseBody
    @RequestMapping(value = "/modelPart",method = RequestMethod.GET)
    public Wrapper selectCarPack(@PathParam("packId")Integer packId){
        List<TcPackRelate>packRelateList=operationFlowService.selectPack(packId);//查出关系
        ArrayList<RelationResponse>list=new ArrayList<>();
        ArrayList<OperationFlow> basePackList=new ArrayList<>();
        for(int i=0;i<packRelateList.size();i++){
            basePackList.add(operationFlowService.selectPackById(packRelateList.get(i).getBasePackId()));
        }
        for(int i=0;i<packRelateList.size();i++){
            RelationResponse relationResponse=new RelationResponse();
            relationResponse.setBasePackName(basePackList.get(i).getName());
            relationResponse.setBasePackCode(basePackList.get(i).getCode());
            relationResponse.setNum(packRelateList.get(i).getNum());
            list.add(relationResponse);
        }
        return WrapMapper.ok(list);
    }

    //计算车厂套餐的成本
    @ResponseBody
    @RequestMapping(value = "/instance/cost",method = RequestMethod.POST)//给出套餐包大小
    public Wrapper countCost(@PathParam("total")int total){
        Algorithm algorithm=new Algorithm();
        double cost=0;
        List<OperationFlow>operationFlowList=operationFlowService.selectAll();
        int[] item=algorithm.countPackage(operationFlowList,total);
        for(int i=0;i<item.length;i++){
            while(item[i]!=0){
                cost+=operationFlowList.get(i).getExtend();
                item[i]--;
            }
        }
        return WrapMapper.ok(cost);
    }


    //添加模板套餐并添加套餐与运营商套餐关系
    @ResponseBody
    @RequestMapping(value = "/model",method = RequestMethod.POST)
    public Wrapper addPack(@RequestBody(required = false)TcPack tcPack){
        Algorithm algorithm=new Algorithm();
        if(tcPack.getOperationType()==1) {
            List<OperationFlow> operationFlowList = operationFlowService.selectOperationPack("", "", tcPack.getOperationType());
            int[] item = algorithm.countPackage(operationFlowList, tcPack.getStandard());
            int packId = operationFlowService.addPack(tcPack);
            for (int i = 0; i < item.length; i++) {
                if (item[i] != 0) {
                    TcPackRelate tcPackRelate = new TcPackRelate();
                    tcPackRelate.setPackId(packId);
                    tcPackRelate.setBasePackId(operationFlowList.get(i).getId());
                    tcPackRelate.setNum(item[i]);
                    operationFlowService.insertRelation(tcPackRelate);
                }
            }
        }else {
            int packId=operationFlowService.addPack(tcPack);
            TcPackRelate tcPackRelate=new TcPackRelate();
            tcPackRelate.setPackId(packId);
            tcPackRelate.setBasePackId(tcPack.getOperationPackId());
            tcPackRelate.setNum(1);
            operationFlowService.insertRelation(tcPackRelate);
        }
        return WrapMapper.ok();
    }
    //得到基础讯飞信源包
    @RequestMapping(value = "/xunfei",method = RequestMethod.GET)
    public Wrapper getXunFeiPack(@RequestParam(value = "operationType")Integer operationType){
        if(operationType==2){
            List<OperationFlow>operationFlowList=operationFlowService.selectOperationPack("","",operationType);
            return WrapMapper.ok(operationFlowList);
        }
        return WrapMapper.ok();
    }


    //查询出模板套餐
    @ResponseBody
    @RequestMapping(value = "/model",method = RequestMethod.GET)
    public Wrapper<Map<String, Object>> getPack(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                                @RequestParam(value = "name",required = false)String name,
                                                @RequestParam(value = "cycle",required = false)Integer cycle) {
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);//分页
        PageHelper.orderBy("pack_id desc");
        List<TcPack> tcPackList = operationFlowService.getPack(name, cycle);
        PageInfo<TcPack> pageInfo = new PageInfo(tcPackList, pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list", tcPackList);
        map.put("pages", pageInfo.getPages());
        return WrapMapper.ok(map);
    }


    //根据车型查实例套餐
    @ResponseBody
        @RequestMapping(value = "/relation/carName",method = RequestMethod.GET)
    public Wrapper getFirmProduct(@RequestBody(required = false)CarName carName){
        List<TcFirmPack> tcFirmPackList=operationFlowService.selectPackByCar(carName.getCarName());
        for(int i=0;i<tcFirmPackList.size();i++){
            List<TcPackRelate>packRelateList=operationFlowService.selectPack(tcFirmPackList.get(i).getPackId());//查出关系
            ArrayList<OperationFlow> basePackList=new ArrayList<>();
            for(int j=0;j<packRelateList.size();j++){
                basePackList.add(operationFlowService.selectPackById(packRelateList.get(j).getBasePackId()));
            }
            ArrayList<RelationResponse>list=new ArrayList<>();
            for(int j=0;j<packRelateList.size();j++){
                RelationResponse relationResponse=new RelationResponse();
                relationResponse.setBasePackName(basePackList.get(j).getName());
                relationResponse.setBasePackCode(basePackList.get(j).getCode());
                relationResponse.setNum(packRelateList.get(j).getNum());
                list.add(relationResponse);
            }
            tcFirmPackList.get(i).setRelationResponseList(list);
        }
        return WrapMapper.ok(tcFirmPackList);
    }




//    //测试添加关系
//    @ResponseBody
//    @RequestMapping(value = "/test")
//    public Wrapper test(){
//        Algorithm algorithm= new Algorithm();
//        List<TcPack> tcPackList=operationFlowService.getPack("联通",null);
//        for(int i=0;i<tcPackList.size();i++){
//            List<OperationFlow>operationFlowList=operationFlowService.selectName("","",tcPackList.get(i).getOperationType());
//            int[] item=algorithm.countPackage(operationFlowList,tcPackList.get(i).getStandard());
//            for(int j=0;j<item.length;j++){
//                if(item[j]!=0){
//                    TcPackRelate tcPackRelate=new TcPackRelate();
//                    tcPackRelate.setPackId(tcPackList.get(i).getPackId());
//                    tcPackRelate.setBasePackId(operationFlowList.get(j).getId());
//                    tcPackRelate.setNum(item[j]);
//                    operationFlowService.insertRelation(tcPackRelate);
//                }
//            }
//        }
//        return  WrapMapper.ok();
//    }

    //下架模板套餐
    @RequestMapping(value = "/model/affect",method = RequestMethod.GET)
    public Wrapper getPackAffect(@RequestParam("packId")Integer packId){
        Map<String,Object>map=new HashMap<>();
        int items=operationFlowService.getFirmPackByPackId(packId).size();
        map.put("影响实例",items);
        return WrapMapper.ok(map);
    }

    @RequestMapping(value = "/model",method = RequestMethod.DELETE)
    public Wrapper deletePack(@RequestParam("packId")Integer packId){
        operationFlowService.deletePack(packId);
        return WrapMapper.ok();
    }

    //下架实例套餐
    @RequestMapping(value = "/instance",method = RequestMethod.DELETE)
    public Wrapper deleteFirmPack(@RequestParam("id")Integer id){
        operationFlowService.deleteFirmPack(id);
        return WrapMapper.ok();
    }

    //更新实例套餐信息
    @RequestMapping(value = "/instance",method = RequestMethod.PUT)
    public Wrapper updateFirmPack(@RequestBody TcFirmPack tcFirmPack){
        operationFlowService.updateFirmPack(tcFirmPack);
        return WrapMapper.ok();
    }

}
