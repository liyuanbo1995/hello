package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SimInfo)实体类
 *
 * @author tsw
 * @since 2019-08-07 15:40:37
 */
@Data
public class SimInfo implements Serializable {
    
    private Integer id;
    //卡的iccid
    private String iccid;
    //卡的imsi
    private String imsi;
    //卡的msisdn或电话号码
    private String msisdn;
    //卡的状态
    private String status;
    //资费计划
    private String ratePlan;
    //与卡关联的通信计划
    private String communicationPlan;
    
    private String customer;
    
    private String endCustomerId;
    //首次激活卡的日期
    private Date dateActivated;
    
    private Date dateAdded;

    private Date dateUpdated;

    //sim卡从运营商转移到企业账户日期
    private Date dateShipped;
    
    private String accountId;
    
    private String fixedIpAddress;
    //客户公司id，关联gc_firm
    private Integer firmId;
    //产品类型id，关联gc_firm_product_type
    private Integer firmTypeId;
    //系列产品id, 关联gc_firm_product
    private Integer typeProductId;
    //设备的IMEI
    private String imei;
    //运营商创建的任何自定义设备字段的值。该信息只适用于运营商角色
    private String operationCustom1;
    
    private String operationCustom2;
    
    private String operationCustom3;
    
    private String operationCustom4;
    
    private String operationCustom5;
    //企业在 Control Center 中创建的任何自定义设备字段的值。该信息只适用于账户角色
    private String accountCustom1;
    
    private String accountCustom2;
    
    private String accountCustom3;
    
    private String accountCustom4;
    
    private String accountCustom5;
    
    private String accountCustom6;
    
    private String accountCustom7;
    
    private String accountCustom8;
    
    private String accountCustom9;
    
    private String accountCustom10;
    //客户在 Control Center 中创建的任何自定义设备字段的值。该信息只适用于客户角色
    private String customerCustom1;
    
    private String customerCustom2;
    
    private String customerCustom3;
    
    private String customerCustom4;
    
    private String customerCustom5;
    //运营商添加的有关设备的信息
    private String simNotes;
    //账户或客户可为设备分配的可选标识符
    private String deviceId;
    //标识设备用于传输数据的调制解调器
    private String modemId;
    
    private String euIccid;
    
    private String globalSimType;
    //上次更新卡信息的时间
    private Date updateTime;
    //更新次数
    private Integer synNum;

    private String phone;
    private String vin;

    private String ctdDataUsage;
    private String ctdSMSUsage;
    private String ctdVoiceUsage;
    private String ctdSessionCount;
    private String overageLimitReached;
    private String overageLimitOverride;
    private String ctdZUsage;//娱乐流量使用量
    private String zoneName;

}