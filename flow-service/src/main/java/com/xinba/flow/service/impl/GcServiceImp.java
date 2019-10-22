package com.xinba.flow.service.impl;

import com.xinba.flow.entity.GcFirm;
import com.xinba.flow.entity.GcFirmProduct;
import com.xinba.flow.entity.GcFirmType;
import com.xinba.flow.mapper.GcMapper;
import com.xinba.flow.service.GcFirmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GcServiceImp implements GcFirmService {

    @Resource
    private GcMapper gcMapper;

    public void addFrimInfo(GcFirm gcFirm){gcMapper.addFrimInfo(gcFirm);}
    public int addContactPhone(Integer id,String contact,String phone){return gcMapper.addContactPhone(id,contact, phone);}
    public void addFirmType(GcFirmType gcFirmType){gcMapper.addFirmType(gcFirmType);}
    public void addFirmProduct(GcFirmProduct gcFirmProduct){gcMapper.addFirmProduct(gcFirmProduct);}
    //查询所有客户公司
    public List<GcFirm> selectFirm(String firmName){return gcMapper.selectFirm(firmName);}
    //查询客户公司下所有产品
    public List<GcFirmType>selectProductType(int firmId,String typeName){return gcMapper.selectProductType(firmId,typeName);}
    //查询产品类型下产品系列
    public List<GcFirmProduct>selectProduct(int typeId,String productName){return gcMapper.selectProduct(typeId,productName);}

    public GcFirm getFirmInfo(Integer firmId){return gcMapper.getFirmInfo(firmId);}
    public GcFirmType getTypeInfo(Integer typeId){return gcMapper.getTypeInfo(typeId);}
    public GcFirmProduct getProductInfo(Integer productId){return gcMapper.getProductInfo(productId);}
}
