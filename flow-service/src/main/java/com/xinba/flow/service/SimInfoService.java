package com.xinba.flow.service;

import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.SimActiveUnbind;
import com.xinba.flow.entity.SimInfo;
import com.xinba.flow.entity.TcFirmPack;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * (SimInfo)表服务接口
 *
 * @author tsw
 * @since 2019-07-25 14:01:09
 */
public interface SimInfoService {

    //excel导入ka
    Wrapper addSimAuto(String fileName, MultipartFile file)throws Exception;
    //查询单卡信息
    SimInfo selectByIccid(String iccid);
    //查询卡对应套餐
    List<TcFirmPack>selectPack(Integer firmId,Integer firmTypeId,Integer typeProductId);
    //更新卡
    void updateSim(SimInfo simInfo);
    //按条件查询卡信息
    List<SimInfo>selectSimInfo(String iccid,String updateTime,String phone,String vin,String msisdn,String status,String ratePlan,String communicationPlan);
    //查询流量池
    Map<String,Integer> getFlowPol();

    Wrapper addSimVinInfo(String fileName,MultipartFile file)throws Exception;

    List<String>groupByRatePlan();
    List<String>groupByCommunicationPlan();
    List<SimActiveUnbind>getSimActiveOrUnbindRecord(String iccid,String beginTime,String endTime,String status);

    Wrapper addSimActiveRecord(String fileName, MultipartFile file)throws Exception;
}