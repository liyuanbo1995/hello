package com.xinba.active.entity.order;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class XfOrder {
    private String orderId;
    private Double amount;
    private String orderStatus;
    private String payMethod;
    private String payStatus;
    private String vinCode;
    private Date payTime;
    private List<XfOrderDetails>products;
    private List<XfGift>giftList;
}
