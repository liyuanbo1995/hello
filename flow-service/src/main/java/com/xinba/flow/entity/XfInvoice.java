package com.xinba.flow.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
@Data
/**
 * (XfInvoice)实体类
 *
 * @author tsw
 * @since 2019-08-08 15:28:14
 */
public class XfInvoice implements Serializable {
    private static final long serialVersionUID = 121625720620857755L;
    
    private String orderId;
    
    private String content;
    
    private String email;
    
    private String invoiceStatus;
    
    private String nnum;
    
    private String payStatus;
    
    private String phone;
    
    private String title;
    
    private Double totalPrice;
    
    private Integer type;
    
    private Date createTime;

    private Date invoiceTime;
    private String createPeople;
    private String updatePeople;

}