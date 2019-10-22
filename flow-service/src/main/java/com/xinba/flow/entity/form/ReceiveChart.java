package com.xinba.flow.entity.form;

import lombok.Data;

@Data
public class ReceiveChart {
    private Integer pageNum=1;
    private String beginTime;
    private String endTime;
    private String orderId;
    private String iccid;
    private String invoiceStatus;
    private String payStatus;
    private String userIccid;
    private String payMethod;
    private String company;
    private String typeName;
}
