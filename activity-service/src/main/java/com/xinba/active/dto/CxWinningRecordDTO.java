package com.xinba.active.dto;

import lombok.Data;

/**
 * @createBy XiaoWu
 * @date 2019/12/4 16:15
 */
@Data
public class CxWinningRecordDTO {

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
    private String firstStatus;

    private String userStatus;

    // 使用该奖券的订单号，两张都记录，选择不为空的
    private String firstOrderId;

    // 使用者orderId
    private String userOrderId;

    // 名字
    private String firstName;

    // 使用者名字
    private String userName;

    // 是否是第一个用户
    private String isFirstUser;
}
