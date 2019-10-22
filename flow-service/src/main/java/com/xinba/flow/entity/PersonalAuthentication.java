package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (PersonalAuthentication)实体类
 *
 * @author tsw
 * @since 2019-09-10 13:29:32
 */
@Data
public class PersonalAuthentication implements Serializable {
    private static final long serialVersionUID = -66511653312723173L;
    
    private Integer id;
    //车辆识别码,当为车辆实名认证时，VIN 不能为 NULL
    private String vin1;
    
    private String iccid1;
    
    private String imsi;
    //号码
    private String msisdn;
    //当为设备实名认证时，imei 和 deviceID 至少有一项不为 NULL
    private String imei;
    //当为设备实名认证时，imei 和 deviceID 至少有一项不为 NULL
    private String deviceid;
    //姓名
    private String username;
    //男：M;女：F
    private String gender;
    //证件类型
    private String ownercerttype;
    //证件号码
    private String ownercertid;
    //所有人证件地址
    private String ownercertaddr;
    //证件正面
    private String pic1;
    //证件背面
    private String pic2;
    //手持证件照
    private String facepic;
    //联系电话
    private String servnumber;
    //联系地址
    private String ownercontact;
    //邮箱
    private String email;
    //品牌编码
    private String brandid;
    //车型名称，当车厂为保时捷，该项必须
    private String linenameen;
    //业务区分，当车厂为评驾科技，该项必须
    private String channelid;
    //请求认证时间
    private Date requestTime;
    //回复时间
    private Date responseTime;
    //审核结果，0：审核通过；1：审核不通过；2：审核中
    private Integer auditresult;
    //结果描述
    private String auditdesc;
    //审核意见
    private String auditcomment;
    //步骤
    private Integer step;
    
    private String retMsg;

}