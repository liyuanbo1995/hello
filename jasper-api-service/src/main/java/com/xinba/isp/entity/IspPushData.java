package com.xinba.isp.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * (IspPushData)实体类
 *
 * @author tsw
 * @since 2019-08-17 01:13:03
 */

@XmlRootElement(name = "MonthlyDataUsage",namespace="http://api.jasperwireless.com/ws/schema" )
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IspPushData implements Serializable {
    private static final long serialVersionUID = -51197472049190175L;
    
    private Integer id;
    //账户名称
    @XmlElement(name = "accountName",namespace="http://api.jasperwireless.com/ws/schema")
    private String accountName;
    //区域名称,区域均指流量池
    @XmlElement(name = "zoneName",namespace="http://api.jasperwireless.com/ws/schema")
    private String zoneName;
    //资费计划名称
    @XmlElement(name = "ratePlanName",namespace="http://api.jasperwireless.com/ws/schema")
    private String ratePlanName;
    //流量池在本区域总流量
    @XmlElement(name = "totalIncludedZoneUsage",namespace="http://api.jasperwireless.com/ws/schema")
    private Double totalIncludedZoneUsage;
    //当前已耗流量
    @XmlElement(name = "totalActualZoneUsage",namespace="http://api.jasperwireless.com/ws/schema")
    private Double totalActualZoneUsage;
    //上次更新时间
    private Date updateTime;

    private String data;
    private String eventId;
    private String eventType;
    private String signature;
    private String signature2;
    private Date timestamp;


    @XmlElement(name = "previousState",namespace="http://api.jasperwireless.com/ws/schema")
    private String previousState;
    @XmlElement(name = "currentState",namespace="http://api.jasperwireless.com/ws/schema")
    private String currentState;
    @XmlElement(name = "dateChanged",namespace="http://api.jasperwireless.com/ws/schema")
    private String dateChanged;


}