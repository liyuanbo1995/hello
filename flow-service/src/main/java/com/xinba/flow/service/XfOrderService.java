package com.xinba.flow.service;

import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.charts.*;
import com.xinba.flow.entity.form.OrderChart;
import com.xinba.flow.entity.User;
import com.xinba.flow.entity.XfOrderDetails;
import com.xinba.flow.entity.XfOrder;

import java.util.List;
import java.util.Map;

/**
 * (XfOrder)表服务接口
 *
 * @author tsw
 * @since 2019-07-19 14:05:46
 */
public interface XfOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    XfOrder queryById(Long id);

    TotalCount totalCount();

    TotalCount totalSim();

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<XfOrder> queryAllByLimit(int offset, int limit);

    /**
     * 根据条件查询续费订单
     *
     * @param param 查询条件
     * @return List<XfOrder> 续费订单List
     */
    List<XfOrder> queryListBySeach(Map<String, Object> param);

    /**
     * 根据条件查询续费订单
     *
     * @param param 查询条件
     * @return List<XfOrder> 续费订单List
     */
    List<XfOrderDetails> queryDetailListBySeach(Map<String, Object> param);

    /**
     * 获取订单类型数据
     *
     * @param type 类型
     * @return List<XfOrder> 续费订单List
     */
    List<TypeCharts> typeCharts(String type);


    /**
     * 最新10条交易订单
     *
     * @return List<XfOrder>
     */
    List<XfOrder> queryTransTable();

    /**
     * 按照订单日期统计续费订单
     *
     * @return List<OrderCount> 统计续费订单List
     */
    List<OrderCount> chartOrderCount();

    /**
     * 查询sim卡用户
     *
     * @return List<SimCharts>
     */
    List<SimCharts> findAllsim();

    /**
     * 统计车型订单占比
     *
     * @return List<ChartsCar>
     */
    List<ChartsCar> chartCar();

    /**
     * 厂商所有车型
     *
     * @return List<GcFirmProduct>
     */
    List<GcFirmProduct> getAllFirmProduct();

    /**
     * 获取三维数组
     *
     * @return List<int[]>
     */
    Map<String, Object> entireChart();

    /**
     * 新增数据
     *
     * @param xfOrder 实例对象
     * @return 实例对象
     */
    XfOrder insert(XfOrder xfOrder);

    /**
     * 修改数据
     *
     * @param xfOrder 实例对象
     * @return 实例对象
     */
    XfOrder update(XfOrder xfOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    long insertOrder(XfOrder xfOrder);
    void insertProducts(XfOrderDetails xfOrderDetails);
    //查询订单
    List<XfOrder> selectOrderInfo(String orderId,String beginTime,String endTime,String payStatus,String payMethod,String userIccid,String company,String typeName);
    List<XfOrderDetails>selectOrderDetails(String orderId);
    Double totalAmount(String orderId,String beginTime,String endTime,String payStatus,String payMethod,String userIccid,String company,String typeName);
    //修改订单信息
    void updateOrderInfo(XfOrder xfOrder);
}