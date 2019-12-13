package com.xinba.active.mapper;

import com.xinba.active.dto.CxRecordOrderDTO;
import com.xinba.active.dto.CxWinningRecordDTO;
import com.xinba.active.entity.CxWinningRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (CxWinningRecord)表数据库访问层
 *
 * @author tsw
 * @since 2019-11-25 10:37:07
 */
public interface CxWinningRecordMapper {

    int insertWinningRecord(CxWinningRecord cxWinningRecord);

    List<CxWinningRecord> selectWiningRecord(@Param("activityId") String activityId, @Param("iccid") String iccid, @Param("vin") String vin, @Param("status") String status, @Param("exchangeCode") String exchangeCode, @Param("payStatus") String payStatus);

    CxWinningRecord selectSingleWiningRecord(@Param("activityId") String activityId, @Param("iccid") String iccid, @Param("vin") String vin, @Param("status") String status, @Param("exchangeCode") String exchangeCode);

    CxWinningRecord selectWinningRecordByOrderId(@Param("iccid") String iccid, @Param("vin") String vin, @Param("orderId") String orderId);
    // 通过活动id,iccid，vin 查询中奖记录，包括详情
//    List<CxWinningRecord> selectWiningRecord(@Param("activityId") String activityId, @Param("iccid") String iccid, @Param("vin") String vin);

    int getExchangeCode(@Param("exchangeCode") String exchangeCode);

    CxWinningRecord getWinningRecordByCode(@Param("exchangeCode") String exchangeCode, @Param("activityId") String activityId);

    int updateWinningRecord(@Param("iccid") String iccid, @Param("vin") String vin, @Param("exchangeCode") String exchangeCode, @Param("status") String status, @Param("payStatus") String payStatus, @Param("orderTime") Date orderTime, @Param("orderId") String orderId, @Param("redeemedId") Integer redeemedId);

    int updateWinningRecordByOrderId(@Param("iccid") String iccid, @Param("vin") String vin, @Param("orderId") String orderId, @Param("status") String status, @Param("payStatus") String payStatus, @Param("orderTime") Date orderTime);

    // 设置商品记录表 状态为已过期
    int updateExpireWinningRecord();

    int cancleOrder(@Param("dateTime") Date dateTime);

    CxWinningRecord selectWinningRecordByGoodsType(@Param("activityId") String activityId, @Param("iccid") String iccid, @Param("vin") String vin, @Param("goodsType") Integer goodsType);

    CxWinningRecord selectRecordById(@Param("id") Integer id);
    List<CxWinningRecord>selectWiningRecordAndGoodsInfo(@Param("activityId")String activityId,@Param("iccid") String iccid, @Param("vin") String vin,@Param("status") String status,@Param("exchangeCode") String exchangeCode,@Param("payStatus") String payStatus);
    List<CxWinningRecord>selectScrollWinningRecord();
//    CxWinningRecord getWinningRecordByCode(@Param("exchangeCode") String exchangeCode);

    // 更新奖券 状态int updateWinningRecord(@Param("iccid") String iccid, @Param("vin") String vin, @Param("status") String status);
//    int updateWinningRecord(@Param("iccid")String iccid,@Param("vin")String vin,@Param("exchangeCode")String exchangeCode,@Param("status")String status);

    // 联合查询，获得奖券信息
    // 查询逻辑 可以根据抽中人iccid,兑换码，订单号查询，使用者的手机号
    List<CxWinningRecordDTO> listWinningRecord2One(@Param("activityId")String activityId,@Param("iccid")String iccid,@Param("exchangeCode") String exchangeCode,@Param("firstPhone")String firstPhone, @Param("userPhone") String userPhone);

    // 订单地址，订单详情，订单状态（未发货，已发货）, 通过订单号
    // 订单是多，中奖记录是少，通过in查询
    // 但是有可能 订单号为null,还没有被支付订单
    List<CxRecordOrderDTO> listWinningRecordOrderDetails(@Param("orderIdList")List<String> orderIdList);

    // 循环 单条查询 数据库
    CxRecordOrderDTO getWinningRecordOrderDetails(String orderId);
}