package com.xinba.isp.service;

import com.xinba.isp.entity.ErrorInfo;
import com.xinba.isp.entity.IspPushData;
import com.xinba.isp.entity.SimInfo;
import com.xinba.isp.entity.charts.ExpireDateForm;
import com.xinba.isp.entity.form.PoolGroup;

import java.util.List;
import java.util.Map;

/**
 * (IspPushData)表服务接口
 *
 * @author tsw
 * @since 2019-08-17 01:13:05
 */
public interface IspPushDataService {


    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<String> queryAllIccid();

    int updateSimInfo(SimInfo simInfo);
    int updateSimUsageInfo(SimInfo simInfo);

    //查询单卡信息
    SimInfo getSimInfoByIccid(String iccid);
    //错误卡信息
    int insertErrorInfo(String iccid,String errorCode,String errorMessage);
    //记录流量池
    int insertPoolData(IspPushData ispPushData);
    List<IspPushData> queryPoolData(Map<String,Object> map);

    void synSimInfo(List<String>iccidList);
    void synSimUsageInfo(List<String>iccidList);

    List<ErrorInfo>getFailSynSim();
    List<String>getAllFailSimIccid();
    int updateSimErrorStatus(String iccid);
    int updateSimError(String iccid,String errorCode,String errorMessage);


    List<PoolGroup> queryPoolGroup(String column);
    SimInfo selectSimInfo(String iccid);

    int updateCtdUsage(String iccid,String dataUsage);
    int updateCtdZUsage(String iccid,String dataUsage,String zoneName);
    int updateSimFieldChange(String iccid,String oldValue,String newValue,String fieldName);
    int updateSimStateChange(String iccid,String currentState,String dateChanged);

    List<ExpireDateForm> queryExpireSim();
    void pushExpireDate(List<ExpireDateForm>list,String url);



}