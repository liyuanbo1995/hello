package com.xinba.active.vo;

import com.xinba.active.dto.CxRecordOrderDTO;
import lombok.Data;

/**
 * @createBy XiaoWu
 * @date 2019/12/5 10:36
 */
@Data
public class WinningRecordVO {
    // 活动id
    private String activityId;

    // 活动商品id
    private String goodsId;

    // 奖项名称
    private String goodsName;

    // 奖项图片
    private String goodsImg;

    // 抽中人iccid
    private String firstIccid;

    // 使用人iccid
    private String userIccid;

    // 兑换码
    private String exchangeCode;

    // 抽中人手机号
    private String firstPhone;

    // 使用人手机号
    private String userPhone;

    // 奖券状态 默认设置为 第一张券的状态，因为第一个永远有值
//    private String firstStatus;
//
//    private String userStatus;

    private String status;

    // 名字
    private String firstName;

    // 使用者名字
    private String userName;

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

    // 物流状态 分为已发货，未发货
    private String logisticsStatus;

    // 礼物名称
    private String giftName;

    public void setOrderDetail(CxRecordOrderDTO winningRecordOrderDetail) {
        this.province = winningRecordOrderDetail.getProvince();
        this.city = winningRecordOrderDetail.getCity();
        this.area = winningRecordOrderDetail.getArea();
        this.addressDetails = winningRecordOrderDetail.getAddressDetails();
        this.orderDetails = winningRecordOrderDetail.getOrderDetails();
        this.salesId = winningRecordOrderDetail.getSalesId();
        this.logisticsId = winningRecordOrderDetail.getLogisticsId();
        this.giftName = winningRecordOrderDetail.getGiftName();
    }
}
