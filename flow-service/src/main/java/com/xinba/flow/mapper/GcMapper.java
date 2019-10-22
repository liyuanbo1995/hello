package com.xinba.flow.mapper;

import com.xinba.flow.entity.GcFirm;
import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.GcFirmType;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

public interface   GcMapper {
    void addFrimInfo(GcFirm gcFirm);
    int addContactPhone(Integer id,String contact,String phone);
    void addFirmType(GcFirmType gcFirmType);
    void addFirmProduct(GcFirmProduct gcFirmProduct);
    List<GcFirm>selectFirm(@Param("firmName")String firmName);
    List<GcFirmType>selectProductType(@Param("firmId")int firmId,@Param("typeName")String typeName);
    List<GcFirmProduct>selectProduct(@Param("typeId")int typeId,@Param("productName")String productName);

    GcFirm getFirmInfo(@Param("firmId") Integer firmId);
    GcFirmType getTypeInfo(@Param("typeId") Integer typeId);
    GcFirmProduct getProductInfo(@Param("productId") Integer productId);
}
