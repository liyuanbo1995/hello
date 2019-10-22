package com.xinba.flow.entity;

import com.xinba.flow.resonse.RelationResponse;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (TcFirmPack)实体类
 *
 * @author tsw
 * @since 2019-07-25 16:17:56
 */
@Data
public class TcFirmPack implements Serializable {
    private static final long serialVersionUID = 684262533371796186L;
    //套餐id
    private Integer id;
    //套餐名
    private String name;
    //资费标准
    private Double cost;
    //周期，以月为单位
    private Integer cycle;
    //备注说明
    private String info;
    //套餐流量大小，以M为单位
    private Integer standard;

    private List<int[]>products;
    //套餐创建时间
    private Date createTime;
    //客户公司id,关联gc_firm
    private Integer firmId;
    private String firmName;
    //客户公司下产品id，关联gc_firm_product
    private Integer typeProductId;
    private String typeProductName;
    //产品类型，关联gc_firm_product_type
    private Integer firmTypeId;
    private String firmTypeName;
    //状态，默认为1表示正常，0表示停用，2删除
    private Integer status;
    //通讯计划，0为通常，1为分离计费，2为非分离计费
    private Integer tcType;
    //套餐类型，1是续费，2是加油
    private Integer type;
    private String content;
    private String strCycle;
    private String strStandard;
    private Integer packId;
    private String descInfo;

    //
    private List<RelationResponse> relationResponseList;



}