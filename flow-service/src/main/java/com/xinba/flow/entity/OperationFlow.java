package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (OperationFlow)实体类
 *
 * @author makejava
 * @since 2019-07-16 16:27:31
 */
@Data
public class OperationFlow implements Serializable {
    private static final long serialVersionUID = 785436247214778003L;
    //运营商套餐id

    private Integer id;
    //关联运营商套餐id
    private String code;
    //1联通，2移动，3电信，默认为1
    private Integer type;
    //套餐名
    private String name;
    //套餐资费标准
    private Double extend;
    //套餐周期,单位为月
    private Integer cycle;
    //套餐层级，1为主套餐，2为流量包，默认为1
    private Integer level;
    //套餐大小，单位M
    private Integer standard;
    //添加或者更改套餐时间
    private Date time;
    //套餐的备注信息
    private String info;
    private Integer status;



}