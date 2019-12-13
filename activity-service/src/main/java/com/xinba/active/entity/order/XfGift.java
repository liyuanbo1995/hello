package com.xinba.active.entity.order;

import lombok.Data;

@Data
public class XfGift {
    private String orderId;
    private String giftUrl;
    private String giftName;
    private Double detailsPrice;
    private String detailsName;
    private String detailsUrl;
    private String salesId;
    private String logisticsId;
}
