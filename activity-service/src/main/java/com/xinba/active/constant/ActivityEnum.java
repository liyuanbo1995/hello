package com.xinba.active.constant;

import lombok.Getter;

/**
 * @createBy XiaoWu
 * @date 2019/12/2 15:28
 */
@Getter
public enum ActivityEnum {
    ACTIVITY_GOODS_STATUS_ONLINE(0,"上架，正常"),
    ACTIVITY_GOODS_STATUS_OFFLINE(1,"下架，停用"),
    ACTIVITY_STATUS_ONLINE(0,"启用"),
    ACTIVITY_STATUS_OFFLINE(1,"未启用"),
    ACTIVITY_GOODS_IS_INPOOL(0, "在奖池"),
    ACTIVITY_GOODS_NOT_INPOOL(1, "不在奖池")
    ;

    private Integer key;

    private String value;

    ActivityEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
