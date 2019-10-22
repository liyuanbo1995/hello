package com.xinba.isp.util;

import lombok.Data;

@Data
public class BusinessException extends Exception {

    private Integer errorStatus;
    private String message;
    private String result;
    public BusinessException() {
    }
    public BusinessException(int errorStatus,String message,String result) {
        super(message);
        this.errorStatus=errorStatus;
        this.message=message;
        this.result=result;
    }
    public BusinessException(int errorStatus,String result) {
        super(result);
        this.errorStatus=errorStatus;
        this.result=result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"errorStatus\":" + errorStatus +
                ", \"message\":\"" + message + "\"" +
                ", \"result\":" + result +"}";
    }
}
