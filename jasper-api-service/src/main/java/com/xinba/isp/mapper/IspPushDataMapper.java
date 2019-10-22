package com.xinba.isp.mapper;

import com.xinba.isp.entity.ErrorInfo;
import com.xinba.isp.entity.IspPushData;
import com.xinba.isp.entity.SimInfo;
import com.xinba.isp.entity.charts.ExpireDateForm;
import com.xinba.isp.entity.form.PoolGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (IspPushData)表数据库访问层
 *
 * @author tsw
 * @since 2019-08-17 01:13:04
 */
public interface IspPushDataMapper {

    List<String> queryAllIccid();

    int updateSimInfo(SimInfo simInfo);

    int updateLastIspPoolData(String ratePlanName,String zoneName);

    SimInfo getSimInfoByIccid(@Param("iccid") String iccid);

    //错误卡信息
    int insertErrorInfo(@Param("iccid") String iccid,@Param("errorCode") String errorCode,@Param("errorMessage")String errorMessage);
    //记录流量池
    int insertPoolData(IspPushData ispPushData);

    List<IspPushData> queryPoolData(Map<String, Object> param);

    List<PoolGroup> queryPoolGroup(@Param("column")String column);

    List<ErrorInfo>getFailSynSim();
    List<String>getAllFailSimIccid();
    int updateSimErrorStatus(@Param("iccid") String iccid);
    int updateSimError(@Param("iccid")String iccid,@Param("errorCode")String errorCode,@Param("errorMessage")String errorMessage);
    SimInfo selectSimInfo(@Param("iccid")String iccid);

    int updateCtdUsage(@Param("iccid")String iccid,@Param("dataUsage")String dataUsage);
    int updateCtdZUsage(@Param("iccid")String iccid,@Param("dataUsage")String dataUsage,@Param("zoneName")String zoneName);
    int updateSimFieldChange(@Param("iccid")String iccid,@Param("oldValue")String oldValue,@Param("newValue")String newValue,@Param("fieldName")String fieldName);
    int updateSimStateChange(@Param("iccid")String iccid,@Param("currentState")String currentState,@Param("dateChanged")String dateChanged);

    List<ExpireDateForm> queryExpireSim();

}