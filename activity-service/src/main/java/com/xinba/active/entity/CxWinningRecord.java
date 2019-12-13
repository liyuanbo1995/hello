package com.xinba.active.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (CxWinningRecord)实体类
 *
 * @author tsw
 * @since 2019-11-25 10:37:06
 */
@Data
public class CxWinningRecord implements Serializable {
    private static final long serialVersionUID = 752745564420112043L;

    private Integer id;

    private String activityId;

    private String goodsId;

    private String iccid;

    private String vin;

    private String phone;

    //中奖人姓名
    private String name;

    //地址
    private String address;

    private Date createTime;

    //状态：已使用，未使用，已过期
    private String status;

    //支付状态：已支付，未支付
    private String payStatus;

    //随机码
    private String exchangeCode;

    //是否是奖品的第一个拥有者，true或者false
    private String isFirstUser;

    //下单时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    //被兑换人的id
    private Integer redeemedId;

    //订单号
    private String orderId;

    private CxMember memberInfo;

    private String goodsName;
    private Integer goodsType;
    private String goodsImg;
    private String goodsDetail;
    private String goodsInfo;
    private Double price;
    private Date endTime;


}