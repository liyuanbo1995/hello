package com.xinba.flow.mapper;

import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.SimActiveUnbind;
import com.xinba.flow.entity.SimInfo;
import com.xinba.flow.entity.TcFirmPack;
import com.xinba.flow.entity.form.FlowPol;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SimInfo)表数据库访问层
 *
 * @author tsw
 * @since 2019-07-25 14:01:07
 */
public interface SimInfoMapper {

    //根据iccid查询卡
    SimInfo selectByIccid(@Param("iccid")String iccid);
    //更新卡的信息
    int updateSim(SimInfo simInfo);
    //插入卡的信息
    void addSim(@Param("simInfoList") List<SimInfo> simInfoList);
    //查询卡对应哪些套餐
    List<TcFirmPack>selectPack(@Param("firmId")Integer firmId, @Param("firmTypeId")Integer firmTypeId, @Param("TypeProductId")Integer TypeProductId);
    //查询卡的信息
    List<SimInfo>selectSimInfo(@Param("iccid")String iccid,@Param("updateTime")String updateTime,@Param("phone")String phone,@Param("vin")String vin,@Param("msisdn")String msisdn,@Param("status")String status,@Param("ratePlan")String ratePlan,@Param("communicationPlan")String communicationPlan);
   //得到流量池
    FlowPol getFlowPol();

    void addSimVinInfo(@Param("iccidVinList") List<IccidVin>iccidVinList);

    List<String>groupByRatePlan();
    List<String>groupByCommunicationPlan();
    List<SimActiveUnbind>getSimActiveOrUnbindRecord(@Param("iccid") String iccid, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("operation") String status);
    void addSimActiveRecord(@Param("simActiveRecordList") List<SimActiveUnbind>simActiveRecordList);

}