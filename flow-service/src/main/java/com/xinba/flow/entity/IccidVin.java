package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (IccidVin)实体类
 *
 * @author tsw
 * @since 2019-08-23 17:58:54
 */
@Data
public class IccidVin implements Serializable {
    private static final long serialVersionUID = -11871713181857105L;
    
    private Integer id;
    
    private String iccid;
    
    private String phone;
    
    private String vin;
    
    private String orderTime;
    private Integer status;

    private PersonalAuthentication personalInfo;

}