package com.xinba.flow.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (GcFirmType)实体类
 *
 * @author tsw
 * @since 2019-07-25 15:40:07
 */
@Data
public class GcFirmType implements Serializable {
    private static final long serialVersionUID = -29465709814512396L;
    //客户产品类型id
    private Integer id;
    //产品类型名称
    private String name;
    //所属客户id
    private Integer firmId;
    private String firmName;
    //备注说明
    private String info;
    private List<GcFirmProduct>children;

}