package com.xinba.flow.mapper;

import com.xinba.flow.entity.XfInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (XfInvoice)表数据库访问层
 *
 * @author tsw
 * @since 2019-08-08 15:28:14
 */
public interface XfInvoiceMapper {

    int addInvoice(XfInvoice xfInvoice);
    List<XfInvoice>queryInvioce(@Param("orderId") String orderId,@Param("beginTime") String beginTime,@Param("endTime")String endTime,@Param("invoiceStatus")String invoiceStatus);
    int changeInvoiceStatus(@Param("orderId") String orderId, @Param("status") String status, @Param("invoiceTime")Date invoiceTime);

}