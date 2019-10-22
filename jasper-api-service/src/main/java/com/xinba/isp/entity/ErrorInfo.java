package com.xinba.isp.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (ErrorInfo)实体类
 *
 * @author tsw
 * @since 2019-08-19 10:07:09
 */
@Data
public class ErrorInfo implements Serializable {
    private static final long serialVersionUID = -70125904473417394L;
    
    private Integer id;
    
    private String iccid;
    //错误码
    private String errorCode;
    //错误时间
    private Date errorDate;
    //错误状态，初次默认0未处理，1已处理
    private Integer errorStatus;
    //错误信息
    private String errorMessage;

}