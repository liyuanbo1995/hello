package com.xinba.isp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (CompanyInfo)实体类
 *
 * @author tsw
 * @since 2019-08-20 15:05:18
 */
@Data
public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = -99702918910796207L;
    
    private Integer id;
    
    private String iccid;
    //车辆识车辆识别码
    private String vin;
    
    private String imei;
    //用户名
    private String userName;
    //证件类型
    private String certType;
    //证件号码
    private String certNo;
    //性别，男：M;女：F
    private String gender;
    //联系电话
    private String servNumber;
    //联系地址
    private String ownerContact;
    //企业名称
    private String comName;
    //企业地址
    private String comAddress;
    //营业执照号
    private String licenseCode;
    //审核结果，0:审核通过;1:审核不通过;2:审核中
    private String auditResult;
    //结果描述
    private String auditDesc;
    //审核意见
    private String auditComment;
    //业务渠道区分,当车厂为评驾科技，该项必须，业务渠道区分代码示例：pj_9999、ca_9999、wew_9999
    private String channelId;


}