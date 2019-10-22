package com.xinba.isp.service.impl;

import com.xinba.isp.entity.form.RequestData;
import com.xinba.isp.mapper.VerifiedMapper;
import com.xinba.isp.service.VerifiedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CompanyInfo)表服务实现类
 *
 * @author tsw
 * @since 2019-08-20 15:05:20
 */
@Service("companyInfoService")
public class VerifiedServiceImpl implements VerifiedService {
    @Resource
    private VerifiedMapper verifiedMapper;

    public int insertCompanyInfo(RequestData requestData){return verifiedMapper.insertCompanyInfo(requestData);}
    public int insertPersonalInfo(RequestData requestData){return verifiedMapper.insertPersonalInfo(requestData);}
    public int updateAuditResult(RequestData requestData){return verifiedMapper.updateAuditResult(requestData);}
    public List<RequestData> queryPersonalInfo(String iccid, String vin, String msisdn, String auditresult){
        return verifiedMapper.queryPersonalInfo(iccid,vin,msisdn,auditresult);
    }
    public RequestData getSubmitInfo(String iccid,String vin){return verifiedMapper.getSubmitInfo(iccid,vin);}
    public int updatePersonalInfo(RequestData requestData){return verifiedMapper.updatePersonalInfo(requestData);}
    public int updateRetMsg(String iccid,String retMsg,Integer auditresult){return verifiedMapper.updateRetMsg(iccid,retMsg,auditresult);}
    public int insertIccidVin(String iccid,String vin,String phone){
        return verifiedMapper.insertIccidVin(iccid,vin,phone);}
    public int updateIccidVinStatus(RequestData requestData){return verifiedMapper.updateIccidVinStatus(requestData);}
}