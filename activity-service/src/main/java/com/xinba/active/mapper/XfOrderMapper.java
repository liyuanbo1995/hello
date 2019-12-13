package com.xinba.active.mapper;

import com.xinba.active.entity.order.XfOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XfOrderMapper {
    List<XfOrder>selectMyOrder(@Param("iccid")String iccid,@Param("payStatus")String payStatus,@Param("orderStatus")String orderStatus);
    XfOrder selectSingleOrder(@Param("orderId")String orderId,@Param("iccid")String iccid);
}
