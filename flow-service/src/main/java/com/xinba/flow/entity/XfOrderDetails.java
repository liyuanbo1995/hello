package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (XfOrderDetails)实体类
 *
 * @author tsw
 * @since 2019-07-26 16:47:34
 */
@Data
public class XfOrderDetails implements Serializable {
    private static final long serialVersionUID = 672524378987436469L;
    //关联订单的id
    private String orderId;
    
    private String Id;
    //车厂套餐id
    private String firmPackId;
    //车厂套餐名字
    private String name;
    //该套餐资费标准
    private Double price;
    //该套餐周期
    private Double amount;
    //备注说明
    private String desc;
    //套餐大小
    private Integer standard;
    //该套餐创建时间
    private Date time;
    //所属公司
    private String company;
    //订单类型
    private Integer type;
    //订单类型名称
    private String typeName;
    //套餐周期名
    private String cycle;
    //套餐包个数
    private Integer count;

    private  String content;


}