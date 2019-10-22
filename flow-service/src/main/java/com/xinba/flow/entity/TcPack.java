package com.xinba.flow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (TcPack)实体类
 *
 * @author tsw
 * @since 2019-08-03 13:32:52
 */
@Data
public class TcPack implements Serializable {
    private static final long serialVersionUID = 318912270790354425L;
    //主键
    private Integer packId;
    //套餐名
    private String name;
    //资费标准
    private Double cost;
    //套餐周期
    private Integer cycle;
    //显示套餐周期
    private String strCycle;
    //套餐大小
    private Integer standard;
    //显示套餐流量大小
    private String strStandard;
    //通讯计划，0为通用，1为分离计费，2为非分离计费
    private Integer tcType;
    //套餐类型，0初始套包，1加油包，2续费包，3信源续费包（通用），4信源续费包（T1A车型专用）
    private Integer type;
    //套餐内容
    private String content;
    //对应tc_base_pack中type字段，运营商类型字段
    private Integer operationType;

    private String descInfo;

    //运营商套餐id
    private Integer operationPackId;


}