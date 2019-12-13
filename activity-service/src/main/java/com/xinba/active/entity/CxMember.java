package com.xinba.active.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (CxMember)实体类
 *
 * @author tsw
 * @since 2019-11-25 09:12:38
 */
@Data
public class CxMember implements Serializable {
    private static final long serialVersionUID = -98303246265740010L;
    
    private Integer id;
    
    private String iccid;
    
    private String vin;
    //用户姓名
    private String name;
    //手机号
    private String phone;
    //省
    private String province;
    //市
    private String city;
    //区
    private String area;
    //地址详情
    private String details;
    //活动资格，true或者false
    private String activityQualification;
    //允许参与活动次数
    private Integer activityTimes;
    //活动编号
    private String activityId;
    //时间
    private Date createTime;


}