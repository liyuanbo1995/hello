package com.xinba.isp.entity.form;

import lombok.Data;

@Data
public class Vcbacks {
    private String vin;
    private String iccid;
    private Integer retcode;
    private String message;
}
