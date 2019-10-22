package com.xinba.isp.entity.form;

import lombok.Data;

@Data
public class ResponseData {

    private String token;
    private long expiredMills;

    private int code;
    private String messsage;

    private Vcbacks[] vcbacks;

}
