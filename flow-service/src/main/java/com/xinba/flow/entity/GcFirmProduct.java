package com.xinba.flow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (GcFirmProduct)实体类
 *
 * @author tsw
 * @since 2019-07-25 15:40:28
 */
@Data
public class GcFirmProduct implements Serializable {
    private static final long serialVersionUID = 261219007452959820L;
    
    private Integer id;
    //产品名称
    private String name;
    //类型id,关联gc_firm_product_type
    private Integer typeId;
    //类型名称
    private String typeName;
    //备注信息
    private String info;
    //车型上市时间
    private Object listTime;




}