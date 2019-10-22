package com.xinba.flow.mapper;

import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.charts.*;
import com.xinba.flow.entity.XfOrderDetails;
import com.xinba.flow.entity.XfOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (XfOrder)表数据库访问层
 *
 * @author tsw
 * @since 2019-07-19 14:05:46
 */
public interface XfOrderMapper {

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
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<XfOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据条件查询续费订单
     *
     * @param map 查询条件
     * @return List<XfOrder> 续费订单List
     */
    List<XfOrder> queryListBySeach(Map<String, Object> map);

    /**
     * 根据条件查询续费订单详情
     *
     * @param map 查询条件
     * @return List<XfOrderDetails> 续费订单详情List
     */
    List<XfOrderDetails> queryDetailListBySeach(Map<String, Object> map);

    /**
     * 查询sim卡用户
     *
     * @return List<SimCharts>
     */
    List<SimCharts> findAllsim();

    /**
     * 查询当年所有订单
     * @return List<XfOrder>
     */
    List<XfOrder> queryYearOrder();


    /**
     * 最新10条交易订单
     *
     * @return List<XfOrder>
     */
    List<XfOrder> queryTransTable();

    List<TypeCharts> queryTypeCharts(@Param("type") String type);


    /**
     * 按订单日期统计订单
     *
     * @return List<OrderCount> 统计订单实体类
     */
    List<OrderCount> chartOrderCount();

    /**
     * 统计车型订单占比
     *
     * @return List<ChartsCar>
     */
    List<ChartsCar> chartCar();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param xfOrder 实例对象
     * @return 对象列表
     */
    List<XfOrder> queryAll(XfOrder xfOrder);


    List<GcFirmProduct> getAllFirmProduct ();

    /**
     * 新增数据
     *
     * @param xfOrder 实例对象
     * @return 影响行数
     */
    int insert(XfOrder xfOrder);

    /**
     * 修改数据
     *
     * @param xfOrder 实例对象
     * @return 影响行数
     */
    int update(XfOrder xfOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    void insertOrder(XfOrder xfOrder);
    void insertProducts(XfOrderDetails xfOrderDetails);
    List<XfOrder> selectOrderInfo(@Param("orderId")String orderId,@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("payStatus") String payStatus,@Param("payMethod")String payMethod,@Param("userIccid")String userIccid,@Param("company")String company,@Param("typeName")String typeName);
    List<XfOrderDetails>selectOrderDetails(@Param("orderId")String orderId);
    Double totalAmount(@Param("orderId")String orderId,@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("payStatus") String payStatus,@Param("payMethod")String payMethod,@Param("userIccid")String userIccid,@Param("company")String company,@Param("typeName")String typeName);
    void updateOrderInfo(XfOrder xfOrder);
}