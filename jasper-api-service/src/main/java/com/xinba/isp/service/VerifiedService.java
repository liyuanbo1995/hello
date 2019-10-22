package com.xinba.isp.service;

import com.xinba.isp.entity.SimInfo;
import com.xinba.isp.entity.form.RequestData;

import java.util.List;

/**
 * (CompanyInfo)表服务接口
 *
 * @author tsw
 * @since 2019-08-20 15:05:20
 */
public interface VerifiedService {

    int insertCompanyInfo(RequestData requestData);

    int insertPersonalInfo(RequestData requestData);
    int updateAuditResult(RequestData requestData);
    List<RequestData> queryPersonalInfo(String iccid, String vin, String msisdn, String auditresult);
    RequestData getSubmitInfo(String iccid,String vin);
    int updatePersonalInfo(RequestData requestData);
    int updateRetMsg(String iccid,String retMsg,Integer auditresult);
    int insertIccidVin(String iccid,String vin,String phone);
    int updateIccidVinStatus(RequestData requestData);

}