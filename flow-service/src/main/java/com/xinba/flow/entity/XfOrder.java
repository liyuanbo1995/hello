package com.xinba.flow.entity;

import java.sql.Time;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.xinba.flow.entity.form.OrderChart;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

@Data
/**
 * (XfOrder)实体类
 *
 * @author tsw
 * @since 2019-07-19 14:05:46
 */
public class XfOrder implements Serializable {
    private static final long serialVersionUID = -38666008748828976L;
    //续费订单id
    private Long id;
    //SIM卡id
    private String simId;
    //订单状态0：未完成；1：完成
    private String status;
    //订单金额
    private Double amount;
    //订单中车厂套餐数量
    private int count;
    //订单生成日期
    private Date createTime;
    private String orderTime;
    //车厂套餐_资费标准
    private Double cost;
    //车厂套餐_周期
    private int cycle;
    //车厂套餐_套餐流量大小
    private int standard;
    //车厂套餐_车型名称
    private String carName;
    //内部车型系列
    private String innerCarName;

    private int month;

    //订单的标识号
    private String orderId;

    //订单金额
    private Double totalPrice;

    //订单状态
    private String orderStatus;

    //支付方法
    private String payMethod;

    //支付状态
    private String payStatus;

    //发货状态
    private String logisticsStatus;

    //物流状态
    private String salesReturnStatus;

    //开发票状态
    private String invoiceStatus;

    //订单过期时间
    private Double orderTimeExpired;

    //用户标识
    private String userIccid;
    private String vinCode;
    //产品详情
    private List<XfOrderDetails>products;
    //历史订单总金额
    private Double totalAmount;
    //支付时间
    private Date payTime;

    public void setOrderChart(OrderChart orderChart){
        this.orderId=orderChart.getOrderId();
        this.amount=orderChart.getTotalPrice();
        this.orderStatus=orderChart.getOrderStatus();
        this.payMethod=orderChart.getPayMethod();
        this.payStatus=orderChart.getPayStatus();
        this.logisticsStatus=orderChart.getLogisticsStatus();
        this.salesReturnStatus=orderChart.getSalesReturnStatus();
        this.invoiceStatus=orderChart.getInvoiceStatus();
        this.orderTimeExpired=orderChart.getOrderTimeExpired();
        this.userIccid=orderChart.getUser().getIccid();
        this.vinCode=orderChart.getUser().getVinCode();
        this.carName=orderChart.getUser().getCarName();
        if(StringUtils.isNotBlank(orderChart.getUser().getCarName())){
            this.innerCarName=orderChart.getUser().getCarName().split("_")[0];
        }
        this.createTime=orderChart.getCreateTime();
        this.payTime=orderChart.getPayTime();
    }

}