package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysLog)实体类
 *
 * @author tsw
 * @since 2019-09-19 10:53:54
 */
@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = -35788019681491945L;
    
    private Integer id;
    
    private String userName;
    
    private String operation;
    
    private String method;
    
    private String params;
    
    private String ip;
    
    private Date createTime;

}