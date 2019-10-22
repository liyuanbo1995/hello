package com.xinba.flow.service;

import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.TboxChangeRecord;

import java.util.List;

/**
 * (IccidVin)表服务接口
 *
 * @author tsw
 * @since 2019-09-10 14:24:05
 */
public interface VinCarService {

    List<IccidVin>selectIccidVin(String iccid,String vin,Integer status);
    List<TboxChangeRecord>getTboxChangeRecord(String vin);
}