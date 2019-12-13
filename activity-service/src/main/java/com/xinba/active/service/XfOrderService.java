package com.xinba.active.service;

import com.xinba.active.entity.order.XfOrder;
import org.springframework.stereotype.Service;

import java.util.List;


public interface XfOrderService {
    List<XfOrder> selectMyOrder(String iccid, String payStatus, String orderStatus);
}
