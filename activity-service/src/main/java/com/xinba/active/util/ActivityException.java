package com.xinba.active.util;

import com.xinba.active.vo.response.ResultEnum;

/**
 * @createBy XiaoWu
 * @date 2019/12/2 9:31
 */
public class ActivityException extends RuntimeException{
    private  Integer code;

    public ActivityException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ActivityException(int code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    /**
     * 方便堆栈信息的打印
     * @return
     */
    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                "\"message\":" + this.getMessage() +
                "}";
    }
}
