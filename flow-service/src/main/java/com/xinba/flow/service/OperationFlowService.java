package com.xinba.flow.service;


import com.xinba.flow.entity.*;
import com.xinba.flow.entity.form.FirmTypeProduct;

import java.util.List;

/**
 * (OperationFlow)表服务接口
 *
 * @author makejava
 * @since 2019-07-16 09:03:25
 */
public interface OperationFlowService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OperationFlow queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OperationFlow> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param operationFlow 实例对象
     * @return 实例对象
     */
   void insertBasePack(OperationFlow operationFlow);

    /**
     * 修改数据
     *
     * @param operationFlow 实例对象
     * @return 实例对象
     */
    OperationFlow update(OperationFlow operationFlow);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);
    //查询所有运营商套餐
    List<OperationFlow>selectAll();
    List<OperationFlow>selectOperationPack(String name,String code,Integer type);
    //按id查询运营商套餐
    OperationFlow selectPackById(int id);
    //删除某个套餐
    void deleteByCode(int id);
    //更改某个套餐状态
    void updateStatus(int id);
    //添加车厂套餐
    int insertFirmPack(TcFirmPack tcFirmPack);
    //查询所有车厂套餐
    List<TcFirmPack> selectAllFirmPa(String name,String info,Integer[] products);
    //添加运营商车厂关系
    void insertRelation(TcPackRelate tcPackRelate);
    //查询某个车厂套餐对应运营商套餐
    List<TcPackRelate>selectPack(Integer packId);
    //添加模板套餐
    int addPack(TcPack tcPack);
    //查询模板套餐
    List<TcPack>getPack(String name,Integer cycle);
    //根据车型查套餐
    List<TcFirmPack> selectPackByCar(String carType);
    //根据id查pack
    TcPack getPackById(Integer packId);
    //根据三级id查询出相应名字
    FirmTypeProduct getCustomerName(Integer firmId,Integer firmTypeId,Integer typeProductId);
    //查询实例套餐是否存在
    int  countItems(Integer firmId,Integer firmTypeId,Integer typeProductId,Integer packId);
    //下架实例套餐
    int deleteFirmPack(Integer id);
    //下架模板套餐
    int deletePack(Integer packId);
    //根据模板套餐packId查询对应哪些实例套餐
    List<Integer>getFirmPackByPackId(Integer packId);
    //更新实例套餐
    int updateFirmPack(TcFirmPack tcFirmPack);
}