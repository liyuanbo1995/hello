package com.xinba.active.vo.response;

import lombok.Data;
import lombok.Getter;

/**
 * @createBy XiaoWu
 * @date 2019/11/26 14:09
 */
@Getter
public enum ResultEnum {
    SUCCESS(200,"success"),

    PARAM_EXCEPTION(400,"fail"),
    ACTIVITY_NOT_EXIST(450, "活动不存在"),
    FILE_IS_EMPTY(451, "文件为空"),
    ACTIVITY_GOODS_NOT_EXIST(452, "活动商品不存在"),

    SYSTEM_EXCEPTION(500,"error"),
    ACTIVE_GOODS_UPDATE_FAIL(550, "活动商品更新失败"),
    ACTIVITY_UPDATE_FAIL(551, "活动更新失败")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
