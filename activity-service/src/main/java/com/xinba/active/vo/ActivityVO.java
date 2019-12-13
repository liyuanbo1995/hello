package com.xinba.active.vo;

import lombok.Data;

/**
 * @createBy XiaoWu
 * @date 2019/12/2 14:39
 */
@Data
public class ActivityVO {

    // 活动id
    private String activityId;

    // 活动名称
    private String activityName;

    // 活动范围
    private String activityRange;

    // 活动状态, 启用，未启用
    private String activityStatus;

    // 活动类型
    private String activityType;

    // 活动开始时间
    private String beginTimeStr;

    // 活动结束时间
    private String endTimeStr;

    // 活动详情
    private String activityDetail;

    // 参与人数
    private Integer activityMember;

    // 奖项数
    private Integer activityPrizeNum;

    // 活动状态
    private Integer status;
}
