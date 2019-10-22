package com.xinba.flow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (TboxChangeRecord)实体类
 *
 * @author tsw
 * @since 2019-09-09 11:22:54
 */
@Data
public class TboxChangeRecord implements Serializable {
    private static final long serialVersionUID = -71143392404726898L;
    
    private String vin;
    
    private String newiccid;
    
    private String oldiccid;
    
    private String imsi;
    
    private String msisdn;
    
    private String imei;




}