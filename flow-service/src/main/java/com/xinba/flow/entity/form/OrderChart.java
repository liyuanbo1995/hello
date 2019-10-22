package com.xinba.flow.entity.form;

import com.xinba.flow.entity.User;
import com.xinba.flow.entity.XfOrderDetails;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (OrderChart)实体类
 *
 * @author tsw
 * @since 2019-07-26 15:15:54
 */
@Data
public class OrderChart implements Serializable {
    private static final long serialVersionUID = 631446230090009603L;

    private int idIccid;
    
    private String Id;
    
    private String orderId;

    private List<XfOrderDetails> products;
    
    private Double totalPrice;
    
    private String orderStatus;
    
    private String payMethod;
    
    private String payStatus;
    
    private String logisticsStatus;
    
    private String salesReturnStatus;
    
    private String invoiceStatus;
    
    private Double orderTimeExpired;
    
    private Date createTime;
    
    private User user;

    private String carName;
    private Date payTime;




}