package com.xinba.active.util;

/**
 * 成功0，通用错误码1-99 , 业务服务器项目错误码范围1001 - 1999 ,每一个功能或子服务范围 x1-x19
 * @author
 */
public enum ErrorCodeEnum {
    // 0 success
    SUCCESS("0", "success"),
    // 1-99
    FAILED("1", "failed"),

    COMMON_TOKEN_ERROR("2", "token已过期"),
    COMMON_PARAM_EMPTY("3", "必选参数为空"),
    COMMON_PARAM_ERROR("4", "参数格式错误"),
    COMMON_RESOURCE_NOT_FIND("5","查询资源失败"),

    //1001-1019
    ROBOT_NOT_EXIST("1001","机器不存在"),
    ROBOT_TYPE_NOT_EXIST("1002", "机器型号不存在"),
    ROBOT_VOICE_RECORD_EMPTY("1003","录音失败或太少"),
    ROBOT_VOICE_UNDERSTAND_FAIL("1004", "语义理解失败"),

    //1021-1039
    USER_NOT_EXIST("1021","用户不存在"),
    USER_ACCOUNT_ERROR("1022","用户账号异常"),
    USER_MODEL_TYPE_NOT_EXIST("1023","model类型不存在"),
    USER_COLLECTION_LIST_MAX_LIMIT("1024","收藏列表上限500条"),
    USER_HAD_BIND_ROBOT("1025","用户已绑定机器"),

    //1041-1059
    SMS_REGISTE_CODE_OVER_TIME("1041","短信验证码过期"),
    SMS_REGISTE_CODE_ERROR("1042","短信验证码错误"),
    SMS_REGISTE_ACCOUNT_EXIST("1043","账号已存在"),
    SMS_GET_FREQUENT("1044","短信获取频繁"),

    //1061-1079
    SMALLTALK_GROUP_NO_MESSAGE("1061","没有新的微聊消息"),
    SMALLTALK_GROUP_USER_NOT_ADMIN("1062","不是微聊组管理员"),
    SMALLTALK_GROUP_INVITED_USER_NOT_EXIST("1063", "微聊组被邀请用户不存在"),
    SMALLTALK_GROUP_MEMBER_MAX_LIMIT("1064", "超出微聊组成员上限"),

    //1081-1099
    MQTT_NOT_ONLINE("1081", "mqtt不在线"),

    //1101-1119
    PLAYLIST_CONTENT_MAX_LIMIT("1101", "超出播放列表内容数量上限"),
    PLAYLIST_TAG_CONTENT_EMPTY("1102", "tag专辑内容空"),

    //...
    SYSTEM_UNKNOWN_ERROR("9999", "系统繁忙，请稍后再试....");

    private String code;
    private String desc;

    ErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "ErrorCodeEnum{" + "code='" + code + '\'' + ", desc='" + desc + '\'' + '}';
    }
}