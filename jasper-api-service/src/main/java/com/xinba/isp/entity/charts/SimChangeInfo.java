package com.xinba.isp.entity.charts;

import com.xinba.isp.entity.SimInfo;
import lombok.Data;

import java.util.Date;

@Data
public class SimChangeInfo {
    private Date effectiveDate;
    //卡的状态
    private String status;
    //资费计划
    private String ratePlan;
    //与卡关联的通信计划
    private String communicationPlan;
    //账户或客户可为设备分配的可选标识符
    private String deviceID;
    //标识设备用于传输数据的调制解调器
    private String modemID;
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
    public void setChangeInfo(SimInfo simInfo,Date effectiveDate){
        this.effectiveDate=effectiveDate;
        this.status=simInfo.getStatus();
        this.ratePlan=simInfo.getRatePlan();
        this.communicationPlan=simInfo.getCommunicationPlan();
        this.deviceID=simInfo.getDeviceId();
        this.modemID=simInfo.getModemId();
        this.accountCustom1=simInfo.getAccountCustom1();
        this.accountCustom2=simInfo.getAccountCustom2();
        this.accountCustom3=simInfo.getAccountCustom3();
        this.accountCustom4=simInfo.getAccountCustom4();
        this.accountCustom5=simInfo.getAccountCustom5();
        this.accountCustom6=simInfo.getAccountCustom6();
        this.accountCustom7=simInfo.getAccountCustom7();
        this.accountCustom8=simInfo.getAccountCustom8();
        this.accountCustom9=simInfo.getAccountCustom9();
        this.accountCustom10=simInfo.getAccountCustom10();

    }

}
