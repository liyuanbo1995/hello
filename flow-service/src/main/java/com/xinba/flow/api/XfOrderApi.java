package com.xinba.flow.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.XfOrderDetails;
import com.xinba.flow.entity.charts.*;
import com.xinba.flow.entity.XfOrder;
import com.xinba.flow.entity.form.OrderChart;
import com.xinba.flow.service.XfOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (XfOrder)表控制层
 *
 * @author tsw
 * @since 2019-07-19 14:05:46
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
@Api(value = "订单服务")
public class XfOrderApi {
    /**
     * 服务对象
     */
    @Resource
    private XfOrderService xfOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public XfOrder selectOne(Long id) {
        return this.xfOrderService.queryById(id);
    }

    /**
     * 查询所有续费订单
     *
     * @param searchStr 查询条件
     * @return Wrapper<Map<String,Object>>
     */
    @GetMapping("xf_chart")
    public Wrapper<Map<String,Object>> xfChart(@PathParam("searchStr") String searchStr) {
        Map<String, Object> param = new HashMap<>();
        param.put("payStatus","已支付");
        List<XfOrderDetails> xfOrderList= this.xfOrderService.queryDetailListBySeach(param);
        List<OrderCount> orderCountList= this.xfOrderService.chartOrderCount();
        Map<String,Object> map = new HashMap<>();
        map.put("xfOrderList",xfOrderList);
        map.put("orderCountList",orderCountList);
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",map);
    }

    /**
     * 查询所有续费订单
     *
     * @return Wrapper<Map<String,Object>>
     */
    @GetMapping("total_count")
    public Wrapper<Map<String,Object>> totalCount() {
        TotalCount totalSim= this.xfOrderService.totalSim();
        TotalCount totalCount= this.xfOrderService.totalCount();
        totalCount.setTotalSim(totalSim.getTotalSim());
        Map<String,Object> map = new HashMap<>();
        map.put("total",totalCount);
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",map);
    }


    /**
     * 查询所有续费订单
     *
     * @return Wrapper<Map<String,Object>>
     */
    @GetMapping("total_sim")
    public Wrapper<Map<String,Object>> totalSim() {
        List<SimCharts> simCountList= this.xfOrderService.findAllsim();
        Map<String,Object> map = new HashMap<>();
        map.put("simCountList",simCountList);
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",map);
    }

    /**
     * 统计车型订单占比
     *
     * @return Wrapper<List<ChartsCar>>
     */
    @GetMapping("car_chart")
    public Wrapper<List<ChartsCar>> carChart() {
        List<ChartsCar> orderCountList= this.xfOrderService.chartCar();
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",orderCountList);
    }

    /**
     * 最新10条交易订单
     *
     * @return Wrapper<List<XfOrder>>
     */
    @GetMapping("transtable")
    public Wrapper<List<XfOrder>> transTable() {
        List<XfOrder> transList= this.xfOrderService.queryTransTable();
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",transList);
    }

    /**
     * 获取三维数组
     *
     * @return Wrapper<Map<String,Object>>
     */
    @GetMapping("entire_chart")
    public Wrapper<Map<String,Object>> entireChart() {
        Map<String,Object> map = new HashMap<>();
        Map<String, Object> params = this.xfOrderService.entireChart();
        map.put("entireList", params.get("arList"));
        map.put("carList", params.get("gfList"));
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",map);
    }

    /**
     * 获取订单类型数据
     *
     * @return Wrapper<Map<String,Object>>
     */
    @GetMapping("type_charts")
    public Wrapper<Map<String,Object>> typeCharts() {
        Map<String,Object> map = new HashMap<>();
        List<TypeCharts> xfList = this.xfOrderService.typeCharts("1");
        List<TypeCharts> llList = this.xfOrderService.typeCharts("2");
        List<TypeCharts> xyList = this.xfOrderService.typeCharts("3");
        map.put("xfList", xfList);
        map.put("llList", llList);
        map.put("xyList", xyList);
        return WrapMapper.warp(Wrapper.SUCCESS_CODE,"操作成功",map);
    }

    //订单数据插入
    @ResponseBody
    @RequestMapping(value = "/add/info",method = RequestMethod.POST)
    public Wrapper setOrderInfo(@RequestBody(required = false)String string){
        JSONObject jsonObject= JSON.parseObject(string);
        OrderChart orderChart =JSONObject.toJavaObject(jsonObject,OrderChart.class);
        XfOrder xfOrder=new XfOrder();
        xfOrder.setOrderChart(orderChart);
        try{
           xfOrderService.insertOrder(xfOrder);
        }catch (Exception e){
            return WrapMapper.wrap(500,"订单order_id重复");
        }
        List<XfOrderDetails>productList=orderChart.getProducts();
        for(int i=0;i<productList.size();i++){
            productList.get(i).setOrderId(orderChart.getOrderId());
            xfOrderService.insertProducts(productList.get(i));
        }
        return WrapMapper.ok();
    }

    //订单数据插入
    @ResponseBody
    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public Wrapper setOrderInfo() throws Exception {
        FileReader reader = new FileReader("F://fsdownload/data_order_201908080932.json");
        BufferedReader br = new BufferedReader(reader);
        String str;
        while ((str = br.readLine()) != null) {
            JSONObject jsonObject = JSON.parseObject(str);
            OrderChart orderChart = JSONObject.toJavaObject(jsonObject, OrderChart.class);
            XfOrder xfOrder = new XfOrder();
            xfOrder.setOrderChart(orderChart);
            try {
                xfOrderService.insertOrder(xfOrder);
            } catch (Exception e) {
                return WrapMapper.wrap(500, "订单order_id重复");
            }
            List<XfOrderDetails> productList = orderChart.getProducts();
            for (int i = 0; i < productList.size(); i++) {
                productList.get(i).setOrderId(orderChart.getOrderId());
                xfOrderService.insertProducts(productList.get(i));
            }
        }
        return WrapMapper.ok();
    }


    //订单数据插入
    @ResponseBody
    @RequestMapping(value = "/update/file_all",method = RequestMethod.POST)
    public Wrapper updateOrderInfo() throws Exception {
        FileReader reader = new FileReader("F://fsdownload/data_order_201908080932.json");
        BufferedReader br = new BufferedReader(reader);
        String str;
        while ((str = br.readLine()) != null) {
            JSONObject jsonObject = JSON.parseObject(str);
            OrderChart orderChart = JSONObject.toJavaObject(jsonObject, OrderChart.class);
            XfOrder xfOrder = new XfOrder();
            xfOrder.setOrderChart(orderChart);
            try {
                xfOrderService.updateOrderInfo(xfOrder);
            } catch (Exception e) {
                return WrapMapper.wrap(500, "订单order_id重复");
            }
        }
        return WrapMapper.ok();
    }



    //查询订单及详情
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Wrapper<Map<String,Object>> getOrderInfoByCondition(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                                    @RequestParam(value = "orderId",required = false)String orderId,
                                                    @RequestParam(value = "beginTime",required = false)String beginTime,
                                                    @RequestParam(value = "endTime",required = false)String endTime,
                                                    @RequestParam(value = "payStatus",required = false)String payStatus,
                                                    @RequestParam(value = "iccid",required = false)String iccid,
                                                    @RequestParam(value = "payMethod",required = false)String payMethod,
                                                    @RequestParam(value = "company",required = false)String company,
                                                    @RequestParam(value = "typeName",required = false)String typeName) {
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("create_time desc");
        List<XfOrder>list2=xfOrderService.selectOrderInfo(orderId,beginTime,endTime,payStatus,payMethod,iccid,company,typeName);
        PageInfo<XfOrder> pageInfo=new PageInfo(list2,pageSize);
        Double totalAmount=xfOrderService.totalAmount(orderId,beginTime,endTime,payStatus,payMethod,iccid,company,typeName);
        map.put("totalAmount",totalAmount);
        map.put("订单条数", pageInfo.getTotal());
        map.put("套餐集合",list2);
        map.put("页数",pageInfo.getPages());
        return WrapMapper.ok(map);
    }



    //按分类查询订单及详情
    @ApiOperation("按分类查询订单及详情")
    @RequestMapping(value = "/byType",method = RequestMethod.GET)
    public Wrapper<Map<String,Object>> getOrderInfoByType(@RequestParam(value = "beginTime",required = false)String beginTime,
                                                          @RequestParam(value = "endTime",required = false)String endTime,
                                                          @RequestParam(value = "payStatus",required = false)String payStatus,
                                                          @RequestParam(value = "payMethod",required = false)String payMethod,
                                                          @RequestParam(value = "company",required = false)String company) {
        Map<String, Object> map = new HashMap<>();
        List<XfOrder> list1 = xfOrderService.selectOrderInfo(null, beginTime, endTime, payStatus, payMethod, null, company, "1");
        List<XfOrder> list2 = xfOrderService.selectOrderInfo("", beginTime, endTime, payStatus, payMethod, "", company, "2");
        List<XfOrder> list3 = xfOrderService.selectOrderInfo("", beginTime, endTime, payStatus, payMethod, "", company, "3");
        map.put("续费", list1);
        map.put("流量", list2);
        map.put("信源", list3);
        return WrapMapper.ok(map);
    }
//    //查询订单详情
//    @ResponseBody
//    @RequestMapping(value = "/details/select")
//    public Wrapper getOrderDetails(@PathParam("orderId")String orderId){
//        List<XfOrderDetails>DetailsList=xfOrderService.selectOrderDetails(orderId);
//        return WrapMapper.ok(DetailsList);
//    }


    //修改订单
    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Wrapper updateOrderInfo(@RequestBody(required = false)String string){
        JSONObject jsonObject= JSON.parseObject(string);
        OrderChart orderChart =JSONObject.toJavaObject(jsonObject,OrderChart.class);
        XfOrder xfOrder=new XfOrder();
        xfOrder.setOrderChart(orderChart);
        xfOrderService.updateOrderInfo(xfOrder);
        return WrapMapper.ok();
    }
}