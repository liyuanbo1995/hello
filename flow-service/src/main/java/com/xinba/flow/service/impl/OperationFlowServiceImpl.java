package com.xinba.flow.service.impl;

import com.xinba.common.entity.WrapMapper;
import com.xinba.flow.entity.*;
import com.xinba.flow.entity.form.FirmTypeProduct;
import com.xinba.flow.mapper.OperationFlowMapper;
import com.xinba.flow.service.OperationFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (OperationFlow)表服务实现类
 *
 * @author makejava
 * @since 2019-07-16 09:03:25
 */
@Service
public class OperationFlowServiceImpl implements OperationFlowService {
    @Resource
    private OperationFlowMapper operationFlowMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OperationFlow queryById(Integer id) {
        return this.operationFlowMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<OperationFlow> queryAllByLimit(int offset, int limit) {
        return this.operationFlowMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param operationFlow 实例对象
     * @return 实例对象
     */
    @Override
    public void insertBasePack(OperationFlow operationFlow) {
        operationFlowMapper.insertBasePack(operationFlow);
    }

    /**
     * 修改数据
     *
     * @param operationFlow 实例对象
     * @return 实例对象
     */
    @Override
    public OperationFlow update(OperationFlow operationFlow) {
        this.operationFlowMapper.update(operationFlow);
        return this.queryById(operationFlow.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.operationFlowMapper.deleteById(id) > 0;
    }
    //查询所有运营商套餐
    public List<OperationFlow>selectAll(){ return operationFlowMapper.selectAll(); }
    public List<OperationFlow>selectOperationPack(String name,String code,Integer type){return operationFlowMapper.selectOperationPack(name,code,type);}
    public OperationFlow selectPackById(int id){return operationFlowMapper.selectPackById(id);}
    //删除某个运营商套餐
    public void deleteByCode(int id){operationFlowMapper.deleteByCode(id);}
  //更改某个套餐状态
    public  void updateStatus(int id){operationFlowMapper.updateStatus(id);}
    //添加车厂套餐
    public int insertFirmPack(TcFirmPack tcFirmPack){ operationFlowMapper.insertFirmPack(tcFirmPack);return tcFirmPack.getId();}
    //查询所有车厂套餐
    public List<TcFirmPack>selectAllFirmPa(String name,String info,Integer[] products){
        if (products.length==0){
            return operationFlowMapper.selectAllFirmPa(name,info,null,null,null);
        } else if(products.length==2){
            return operationFlowMapper.selectAllFirmPa(name,info,products[0],products[1],null);
        }else if(products.length==3){
            return operationFlowMapper.selectAllFirmPa(name,info,products[0],products[1],products[2]);
        }
        return operationFlowMapper.selectAllFirmPa(name,info,products[0],null,null);
    }
    //添加车厂运营商关系
    public void insertRelation(TcPackRelate tcPackRelate){operationFlowMapper.insertRelation(tcPackRelate);}
    //查询某个车厂套餐对应运营商套餐
    public List<TcPackRelate>selectPack(Integer packId){return operationFlowMapper.selectPack(packId);}
    //添加模板套餐
    public int addPack(TcPack tcPack){operationFlowMapper.addPack(tcPack); return tcPack.getPackId();}
    //查询模板套餐
    public List<TcPack>getPack(String name,Integer cycle){return operationFlowMapper.getPack(name,cycle);}
    //根据车型查套餐
    public List<TcFirmPack> selectPackByCar(String carType){return operationFlowMapper.selectPackByCar(carType);}
    //根据id查pack
    public TcPack getPackById(Integer packId){return operationFlowMapper.getPackById(packId);}
    //根据三级id查询出相应名字
   public FirmTypeProduct getCustomerName(Integer firmId, Integer firmTypeId, Integer typeProductId){
       FirmTypeProduct firmTypeProduct=new FirmTypeProduct();
       firmTypeProduct.setFirmName(operationFlowMapper.getFirmName(firmId));
       if(firmTypeId!=null){
           firmTypeProduct.setFirmTypeName(operationFlowMapper.getFirmTypeName(firmTypeId));
       }
       if(typeProductId!=null){
           firmTypeProduct.setTypeProductName(operationFlowMapper.getTypeProductName(typeProductId));
       }
       return firmTypeProduct;
    }
    //查询实例套餐是否存在
    public int  countItems(Integer firmId,Integer firmTypeId,Integer typeProductId,Integer packId){return operationFlowMapper.countItems(firmId,firmTypeId,typeProductId, packId);}

    //下架实例套餐
    public int deleteFirmPack(Integer id){return operationFlowMapper.deleteFirmPack(id);}
    //下架模板套餐同时下架对应的实例套餐
    public int deletePack(Integer packId){
        operationFlowMapper.deletePack(packId);
        List<Integer>firmIdList=getFirmPackByPackId(packId);
        for(Integer firmId:firmIdList){
            deleteFirmPack(firmId);
        }
        return firmIdList.size();
    }
    //根据模板套餐packId查询对应哪些实例套餐
    public List<Integer>getFirmPackByPackId(Integer packId){return operationFlowMapper.getFirmPackByPackId(packId);}
    //更新实例套餐
    public int updateFirmPack(TcFirmPack tcFirmPack){return operationFlowMapper.updateFirmPack(tcFirmPack);}
}