package com.xinba.active.service.impl;

import com.xinba.active.entity.order.XfOrder;
import com.xinba.active.mapper.XfOrderMapper;
import com.xinba.active.service.XfOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class XfOrderServiceImpl implements XfOrderService {
    @Autowired
    private XfOrderMapper xfOrderMapper;

    @Override
    public List<XfOrder> selectMyOrder(String iccid, String payStatus, String orderStatus) {
        return xfOrderMapper.selectMyOrder(iccid, payStatus, orderStatus);
    }
}
