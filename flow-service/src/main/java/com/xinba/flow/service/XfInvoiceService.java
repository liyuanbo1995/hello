package com.xinba.flow.service;

import com.xinba.flow.entity.XfInvoice;

import java.util.Date;
import java.util.List;

/**
 * (XfInvoice)表服务接口
 *
 * @author tsw
 * @since 2019-08-08 15:28:14
 */
public interface XfInvoiceService {

    int addInvoice(XfInvoice xfInvoice);
    List<XfInvoice>queryInvioce(String orderId,String beginTime,String endTime,String invoiceStatus);
    int changeInvoiceStatus(String orderId, String status, Date invoiceTime);

}