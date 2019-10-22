package com.xinba.isp.entity.form;

import lombok.Data;

@Data
public class JasperResponseBody {
    private String ret_code;
    private String ret_msg;
    private String serial_number;
    private String timestamp;
    private ResponseData response_data;

}


