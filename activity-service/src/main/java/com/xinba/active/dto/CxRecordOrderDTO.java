package com.xinba.active.dto;

import lombok.Data;

/**
 * @createBy XiaoWu
 * @date 2019/12/5 9:35
 */
@Data
public class CxRecordOrderDTO {

    // 订单号
    private String orderId;

    // 订单省市县
    private String province;

    private String city;

    private String area;

    private String addressDetails;

    // 订单详情
    private String orderDetails;

    // 淘宝订单号
    private String salesId;

    // 物流号
    private String logisticsId;

    // 礼物名称
    private String giftName;

}
