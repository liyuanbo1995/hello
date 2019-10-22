package com.xinba.flow.entity;

import java.io.Serializable;

/**
 * (SimUsedInfo)实体类
 *
 * @author tsw
 * @since 2019-07-25 14:01:35
 */
public class SimUsedInfo implements Serializable {
    private static final long serialVersionUID = -91936873451804454L;
    //卡的iccid
    private String iccid;
    //卡的资费计划，即套餐id
    private Integer ratePlan;
    //自计费周期开始后使用的流量，以字节计
    private Double ctdDataUsage;
    //是否达到资费计划设置的流量上限，0为false，1为true
    private Object overRageLimitReached;


    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public Integer getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(Integer ratePlan) {
        this.ratePlan = ratePlan;
    }

    public Double getCtdDataUsage() {
        return ctdDataUsage;
    }

    public void setCtdDataUsage(Double ctdDataUsage) {
        this.ctdDataUsage = ctdDataUsage;
    }

    public Object getOverRageLimitReached() {
        return overRageLimitReached;
    }

    public void setOverRageLimitReached(Object overRageLimitReached) {
        this.overRageLimitReached = overRageLimitReached;
    }

}