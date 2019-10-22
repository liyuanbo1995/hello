package com.xinba.flow.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.XfInvoice;
import com.xinba.flow.entity.XfOrder;
import com.xinba.flow.entity.form.OrderChart;
import com.xinba.flow.entity.form.ReceiveChart;
import com.xinba.flow.service.XfInvoiceService;
import com.xinba.flow.service.XfOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;;import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (XfInvoice)表控制层
 *
 * @author tsw
 * @since 2019-08-08 15:28:14
 */
@RestController
@RequestMapping("/invoice")
public class XfInvoiceApi {
    /**
     * 服务对象
     */
    @Resource
    private XfInvoiceService xfInvoiceService;
    @Resource
    private XfOrderService xfOrderService;

    @RequestMapping(method = RequestMethod.POST)
    public Wrapper addInvoice(@RequestBody(required = false)String string){
        JSONObject jsonObject= JSON.parseObject(string);
        XfInvoice xfInvoice =JSONObject.toJavaObject(jsonObject,XfInvoice.class);
        xfInvoice.setTotalPrice(xfOrderService.selectOrderInfo(xfInvoice.getOrderId(),"","","","","","","").get(0).getTotalPrice());
        xfInvoiceService.addInvoice(xfInvoice);
       return WrapMapper.ok();
   }

   @RequestMapping(method = RequestMethod.GET)
    public Wrapper queryInvioce(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                @RequestParam(value="orderId",required = false)String orderId,
                                @RequestParam(value = "beginTime",required = false)String beginTime,
                                @RequestParam(value = "endTime",required = false)String endTime,
                                @RequestParam(value = "invoiceStatus",required = false)String invoiceStatus){
           Map<String, Object> map = new HashMap<>();
           int pageSize = 10;
           PageHelper.startPage(pageNum,pageSize);//分页
           List<XfInvoice>xfInvoiceList=xfInvoiceService.queryInvioce(orderId,beginTime,endTime,invoiceStatus);
           PageInfo<XfInvoice> pageInfo=new PageInfo(xfInvoiceList,pageSize);
           map.put("items", pageInfo.getTotal());
           map.put("list",xfInvoiceList);
           map.put("pages",pageInfo.getPages());
           return WrapMapper.ok(map);
   }

   @RequestMapping(value = "/status",method = RequestMethod.POST)
    public Wrapper changeInvoiceStatus(@RequestBody(required = false) String strjson){
        JSONObject json = JSONObject.parseObject(strjson);
        String orderId =  json.getString("orderId");
        String invoiceStatus =  json.getString("invoiceStatus");
        int count=xfInvoiceService.changeInvoiceStatus(orderId,invoiceStatus,new Date());
        if(count==0){
            return WrapMapper.wrap(500,"开票不成功,不存在此订单或未支付");
        }
        return WrapMapper.ok();
   }

    //按分类查询发票
    @RequestMapping(value = "/byType",method = RequestMethod.GET)
    public Wrapper<Map<String,Object>> getInvoiceByType() {
        Map<String, Object> map = new HashMap<>();
        List<XfInvoice>xfInvoiceList1=xfInvoiceService.queryInvioce(null,null,null,"已开发票");
        List<XfInvoice>xfInvoiceList2=xfInvoiceService.queryInvioce(null,null,null,"未开发票");
        map.put("已开发票", xfInvoiceList1);
        map.put("未开发票", xfInvoiceList2);
        return WrapMapper.ok(map);
    }
}