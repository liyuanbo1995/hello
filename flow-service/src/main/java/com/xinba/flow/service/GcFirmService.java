package com.xinba.flow.service;

import com.xinba.flow.entity.GcFirm;
import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.GcFirmType;

import java.util.List;

public interface GcFirmService {
    void addFrimInfo(GcFirm gcFirm);
    int addContactPhone(Integer id,String contact,String phone);
    void addFirmType(GcFirmType gcFirmType);
    void addFirmProduct(GcFirmProduct gcFirmProduct);
    //查询所有客户公司
    List<GcFirm> selectFirm(String firmName);
    //查询客户公司下所有产品类型
    List<GcFirmType>selectProductType(int firmId,String typeName);
    //查询产品类型下产品系列
    List<GcFirmProduct>selectProduct(int typeId,String productName);

    GcFirm getFirmInfo(Integer firmId);
    GcFirmType getTypeInfo(Integer typeId);
    GcFirmProduct getProductInfo(Integer productId);
}
