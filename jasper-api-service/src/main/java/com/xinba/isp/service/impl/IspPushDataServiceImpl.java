package com.xinba.isp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.ErrorInfo;
import com.xinba.isp.entity.IspPushData;
import com.xinba.isp.entity.SimInfo;
import com.xinba.isp.entity.charts.ExpireDateForm;
import com.xinba.isp.entity.form.PoolGroup;
import com.xinba.isp.feign.JasperApiService;
import com.xinba.isp.mapper.IspPushDataMapper;
import com.xinba.isp.service.IspPushDataService;
import com.xinba.isp.util.CombineBeans;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (IspPushData)表服务实现类
 *
 * @author tsw
 * @since 2019-08-17 01:13:06
 */
@Service
public class IspPushDataServiceImpl implements IspPushDataService {
    @Resource
    private IspPushDataMapper ispPushDataMapper;
    @Resource
    private JasperApiService jasperApiService;
    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<String> queryAllIccid() {
        return this.ispPushDataMapper.queryAllIccid();
    }

    public int updateSimInfo(SimInfo simInfo) {
        simInfo.setSynNum(getSimInfoByIccid(simInfo.getIccid()).getSynNum() + 1);
        return ispPushDataMapper.updateSimInfo(simInfo);
    }
    public int updateSimUsageInfo(SimInfo simInfo) {
        return ispPushDataMapper.updateSimInfo(simInfo);
    }

    //查询单卡信息
    public SimInfo getSimInfoByIccid(String iccid) {
        return ispPushDataMapper.getSimInfoByIccid(iccid);
    }

    //错误卡信息
    public int insertErrorInfo(String iccid, String errorCode, String errorMessage) {
        return ispPushDataMapper.insertErrorInfo(iccid, errorCode, errorMessage);
    }

    //记录流量池
    @Transactional(propagation=Propagation.REQUIRED)
    public int insertPoolData(IspPushData ispPushData) throws RuntimeException {
        ispPushDataMapper.updateLastIspPoolData(ispPushData.getRatePlanName(), ispPushData.getZoneName());
        return ispPushDataMapper.insertPoolData(ispPushData);
    }
    //查询流量池
    public List<IspPushData> queryPoolData(Map<String, Object> map){
        return ispPushDataMapper.queryPoolData(map);
    }

    //同步卡信息
    public void synSimInfo(List<String> iccidList) {
//        int count=0;
        for (String iccid : iccidList) {
//            count++;
//            if (count == 30) {
//                try {
//                    Thread.sleep(2000);
//                    count = 0;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            String res = jasperApiService.getSimInfo(iccid);
            JSONObject json;
            try {
                json = JSONObject.parseObject(res);
            }catch (Exception e){
                e.printStackTrace();
                insertErrorInfo(iccid, "500", res);
                continue;
            }
            String errorStatus = json.getString("errorStatus");
            String errorCode = json.getString("errorCode");
            if (StringUtils.isNotBlank(errorStatus)) {
                JSONObject rejson = JSONObject.parseObject(json.getString("result"));
                insertErrorInfo(iccid,  rejson.getString("errorCode"), rejson.getString("errorMessage"));
                continue;
            }
            if(StringUtils.isNotBlank(errorCode)){
                insertErrorInfo(iccid, json.getString("errorCode"), json.getString("errorMessage"));
                continue;
            }
            SimInfo simInfo = json.toJavaObject(SimInfo.class);
            updateSimInfo(simInfo);
        }
    }

    public void synSimUsageInfo(List<String> iccidList) {
        for (String iccid : iccidList) {
            String resUsage = jasperApiService.getSimUsages(iccid);
            JSONObject usageJson;
            try {
                usageJson = JSONObject.parseObject(resUsage);
            }catch (Exception e){
                e.printStackTrace();
                insertErrorInfo(iccid, "500", resUsage);
                continue;
            }
            String usageErrorStatus = usageJson.getString("errorStatus");
            String usageErrorCode = usageJson.getString("errorCode");
            if (StringUtils.isNotBlank(usageErrorStatus)) {
                JSONObject rejson = JSONObject.parseObject(usageJson.getString("result"));
                insertErrorInfo(iccid,  rejson.getString("errorCode"), rejson.getString("errorMessage"));
                continue;
            }
            if(StringUtils.isNotBlank(usageErrorCode)){
                insertErrorInfo(iccid, usageJson.getString("errorCode"), usageJson.getString("errorMessage"));
                continue;
            }
            SimInfo usageSimInfo=usageJson.toJavaObject(SimInfo.class);
            updateSimUsageInfo(usageSimInfo);
        }
    }

    public List<ErrorInfo>getFailSynSim(){
        return ispPushDataMapper.getFailSynSim();
    }
    public List<String>getAllFailSimIccid(){return ispPushDataMapper.getAllFailSimIccid(); }
    public int updateSimErrorStatus(String iccid){return ispPushDataMapper.updateSimErrorStatus(iccid);}
    public int updateSimError(String iccid,String errorCode,String errorMessage){return ispPushDataMapper.updateSimError(iccid,errorCode,errorMessage);}

    @Override
    public List<PoolGroup> queryPoolGroup(String column) {
        return ispPushDataMapper.queryPoolGroup(column);
    }

    public SimInfo selectSimInfo(String iccid){return ispPushDataMapper.selectSimInfo(iccid);}

    public int updateCtdUsage(String iccid,String dataUsage){return ispPushDataMapper.updateCtdUsage(iccid,dataUsage);}
    public int updateCtdZUsage(String iccid,String dataUsage,String zoneName){return ispPushDataMapper.updateCtdZUsage(iccid,dataUsage,zoneName);}
    public int updateSimFieldChange(String iccid,String oldValue,String newValue,String fieldName){return ispPushDataMapper.updateSimFieldChange(iccid, oldValue, newValue, fieldName);}
    public int updateSimStateChange(String iccid,String currentState,String dateChanged){return ispPushDataMapper.updateSimStateChange(iccid, currentState, dateChanged);}

    public List<ExpireDateForm> queryExpireSim(){return ispPushDataMapper.queryExpireSim();}
    public void pushExpireDate(List<ExpireDateForm>list,String url){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map result = new HashMap<String,String>();
        result.put("sim",list);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(WrapMapper.ok(result)),headers);
        restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
    }
}