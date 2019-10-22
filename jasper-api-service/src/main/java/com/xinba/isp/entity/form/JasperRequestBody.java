package com.xinba.isp.entity.form;

import lombok.Data;

@Data
public class JasperRequestBody {
    private String serial_number;
    private String timestamp;
    private String ret_type;
    private String service_name;
    private String platform_type;
    private String token;
    private RequestData request_data;

}
