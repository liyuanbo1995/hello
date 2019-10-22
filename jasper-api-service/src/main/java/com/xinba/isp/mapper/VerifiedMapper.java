package com.xinba.isp.mapper;

import com.xinba.isp.entity.form.RequestData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CompanyInfo)表数据库访问层
 *
 * @author tsw
 * @since 2019-08-20 15:05:18
 */
public interface VerifiedMapper {

   int insertCompanyInfo(RequestData requestData);
    int insertPersonalInfo(RequestData requestData);
    int updateAuditResult(RequestData requestData);
    List<RequestData>queryPersonalInfo(@Param("iccid") String iccid, @Param("vin") String vin, @Param("msisdn") String msisdn, @Param("auditresult") String auditresult);
    RequestData getSubmitInfo(@Param("iccid") String iccid,@Param("vin") String vin);
    int updatePersonalInfo(RequestData requestData);
    int updateRetMsg(@Param("iccid")String iccid,@Param("retMsg")String retMsg,@Param("auditresult")Integer auditresult);
    int insertIccidVin(@Param("iccid")String iccid,@Param("vin")String vin,@Param("phone")String phone);
    int updateIccidVinStatus(RequestData requestData);

}