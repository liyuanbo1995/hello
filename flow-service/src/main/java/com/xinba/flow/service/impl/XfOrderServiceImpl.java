package com.xinba.flow.service.impl;

import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.charts.*;
import com.xinba.flow.entity.form.OrderChart;
import com.xinba.flow.entity.User;
import com.xinba.flow.entity.XfOrderDetails;
import com.xinba.flow.entity.XfOrder;
import com.xinba.flow.mapper.XfOrderMapper;
import com.xinba.flow.service.XfOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (XfOrder)表服务实现类
 *
 * @author tsw
 * @since 2019-07-19 14:05:46
 */
@Service("xfOrderService")
public class XfOrderServiceImpl implements XfOrderService {
    @Resource
    private XfOrderMapper xfOrderMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public XfOrder queryById(Long id) {
        return this.xfOrderMapper.queryById(id);
    }

    /**
     * 统计total
     *
     * @return 实例对象
     */
    @Override
    public TotalCount totalCount() {
        TotalCount totalCount = xfOrderMapper.totalCount();
        return totalCount;
    }

    /**
     * 统计total
     *
     * @return 实例对象
     */
    @Override
    public TotalCount totalSim() {
        TotalCount totalCount = xfOrderMapper.totalSim();
        return totalCount;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<XfOrder> queryAllByLimit(int offset, int limit) {
        return this.xfOrderMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 根据条件查询续费订单
     *
     * @param param 查询条件
     * @return List<XfOrder> 续费订单List
     */
    @Override
    public List<XfOrder> queryListBySeach(Map<String, Object> param) {
        return this.xfOrderMapper.queryListBySeach(param);
    }

    /**
     * 根据条件查询续费订单详情
     *
     * @param param 查询条件
     * @return List<XfOrderDetails> 续费订单List
     */
    @Override
    public List<XfOrderDetails> queryDetailListBySeach(Map<String, Object> param) {
        return this.xfOrderMapper.queryDetailListBySeach(param);
    }

    /**
     * 按照订单日期统计续费订单
     *
     * @return List<OrderCount> 统计续费订单List
     */
    @Override
    public List<OrderCount> chartOrderCount() {
        return this.xfOrderMapper.chartOrderCount();
    }

    /**
     * 统计车型订单占比
     *
     * @return List<ChartsCar>
     */
    @Override
    public List<ChartsCar> chartCar() {
        return this.xfOrderMapper.chartCar();
    }

    /**
     * 厂商所有车型
     *
     * @return List<GcFirmProduct>
     */
    @Override
    public List<GcFirmProduct> getAllFirmProduct(){
        return this.xfOrderMapper.getAllFirmProduct();
    }

    /**
     * 查询sim卡用户
     *
     * @return List<SimCharts>
     */
    @Override
    public List<SimCharts> findAllsim() {
        return this.xfOrderMapper.findAllsim();
    }

    /**
     * 获取三维数组
     *
     * @return List<int [ ]>
     */
    @Override
    public Map<String, Object> entireChart() {
        Map<String, Object> params = new HashMap<>();
        List<XfOrder> xfOrders = this.xfOrderMapper.queryYearOrder();
        List<ChartsCar> gfList = this.xfOrderMapper.chartCar();
        /*List<GcFirmProduct> gfList = xfOrderMapper.getAllFirmProduct();*/
        List<int[]> arList = new ArrayList<>();
        if (xfOrders == null) {
            return null;
        }
        int am = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j<gfList.size(); j++) {
                for (int k = 0; k < xfOrders.size(); k++) {
                    XfOrder o = xfOrders.get(k);
                    if (o.getMonth() == i + 1 && gfList.get(j).getName().equals(o.getInnerCarName())) {
                        am += o.getAmount();
                    }
                    if (k == xfOrders.size()-1) {
                        arList.add(new int[]{j, i, am});
                        am = 0;

                    }
                }
            }
        }
        params.put("arList", arList);
        params.put("gfList", gfList);
        return params;
    }

    @Override
    public List<TypeCharts> typeCharts(String type){
        return this.xfOrderMapper.queryTypeCharts(type);
    }

    /**
     * 最新10条交易订单
     *
     * @return List<XfOrder>
     */
    @Override
    public List<XfOrder> queryTransTable() {
        return this.xfOrderMapper.queryTransTable();
    }

    /**
     * 新增数据
     *
     * @param xfOrder 实例对象
     * @return 实例对象
     */
    @Override
    public XfOrder insert(XfOrder xfOrder) {
        this.xfOrderMapper.insert(xfOrder);
        return xfOrder;
    }

    /**
     * 修改数据
     *
     * @param xfOrder 实例对象
     * @return 实例对象
     */
    @Override
    public XfOrder update(XfOrder xfOrder) {
        this.xfOrderMapper.update(xfOrder);
        return this.queryById(xfOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.xfOrderMapper.deleteById(id) > 0;
    }

    public long insertOrder(XfOrder xfOrder){xfOrderMapper.insertOrder(xfOrder);return xfOrder.getId();}
    public void insertProducts(XfOrderDetails xfOrderDetails){xfOrderMapper.insertProducts(xfOrderDetails);}
    //查询订单
    public List<XfOrder> selectOrderInfo(String orderId,String beginTime,String endTime,String payStatus,String payMethod,String userIccid,String company,String typeName){
        List<XfOrder>list=xfOrderMapper.selectOrderInfo(orderId,beginTime,endTime,payStatus,payMethod,userIccid,company,typeName);
        return list;
    }
    //查询订单详情
    public List<XfOrderDetails>selectOrderDetails(String orderId){return xfOrderMapper.selectOrderDetails(orderId);}

    //总金额
    public Double totalAmount(String orderId,String beginTime,String endTime,String payStatus,String payMethod,String userIccid,String company,String typeName){
        return xfOrderMapper.totalAmount(orderId,beginTime,endTime,payStatus,payMethod,userIccid,company,typeName);
    }
    //修改订单信息
    public void updateOrderInfo(XfOrder xfOrder){
        xfOrderMapper.updateOrderInfo(xfOrder);
    }
}