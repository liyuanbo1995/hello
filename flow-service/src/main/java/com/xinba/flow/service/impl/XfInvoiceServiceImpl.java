package com.xinba.flow.service.impl;

import com.xinba.flow.entity.XfInvoice;
import com.xinba.flow.mapper.XfInvoiceMapper;
import com.xinba.flow.service.XfInvoiceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (XfInvoice)表服务实现类
 *
 * @author tsw
 * @since 2019-08-08 15:28:14
 */
@Service("xfInvoiceService")
public class XfInvoiceServiceImpl implements XfInvoiceService {
    @Resource
    private XfInvoiceMapper xfInvoiceMapper;

    public int addInvoice(XfInvoice xfInvoice){
        return xfInvoiceMapper.addInvoice(xfInvoice);
    }
    public List<XfInvoice> queryInvioce(String orderId,String beginTime,String endTime,String invoiceStatus){
        return xfInvoiceMapper.queryInvioce(orderId,beginTime,endTime,invoiceStatus);
    }
    public int changeInvoiceStatus(String orderId, String status, Date invoiceTime){return xfInvoiceMapper.changeInvoiceStatus(orderId,status,invoiceTime);}
}