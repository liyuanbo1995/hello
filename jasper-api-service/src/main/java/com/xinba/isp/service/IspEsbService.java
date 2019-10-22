package com.xinba.isp.service;

import com.xinba.isp.entity.TboxChangeRecord;
import com.xinba.isp.entity.form.RequestData;

import java.util.List;

/**
 * (TboxChangeRecord)表服务接口
 *
 * @author tsw
 * @since 2019-09-09 11:28:42
 */
public interface IspEsbService {

    int insertTboxChangeRecord(RequestData requestData);
    int updateIccidVin(RequestData requestData);
    int insertActiveOrUnbindRecord(String iccid,String status,String operationPeople);
    int getActiveRecord(String iccid);
    int setVinUnBind(String iccid);
}