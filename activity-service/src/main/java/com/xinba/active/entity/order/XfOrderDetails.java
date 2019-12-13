package com.xinba.active.entity.order;

import lombok.Data;

@Data
public class XfOrderDetails {
    private String orderId;
    private String name;
    private String price;
    private String desc;
    private String typeName;
    private String cycle;
    private String categoryName;
    private Double off;
    private String superValue;
}
