package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SimActiveUnbind)实体类
 *
 * @author tsw
 * @since 2019-09-27 14:05:54
 */
@Data
public class SimActiveUnbind implements Serializable {
    private static final long serialVersionUID = 275507898253535534L;
    
    private Integer id;
    
    private String iccid;
    
    private String createTime;
    
    private String status;
    
    private String operationPeople;


}