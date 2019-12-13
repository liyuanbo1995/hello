package com.xinba.active.service;

import com.xinba.active.entity.CxWinningRecord;
import com.xinba.active.vo.WinningRecordVO;
import com.xinba.active.vo.response.PageResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (CxWinningRecord)表服务接口
 *
 * @author tsw
 * @since 2019-11-25 10:37:07
 */
public interface CxWinningRecordService {

    List<CxWinningRecord> selectWiningRecord(String activityId, String iccid, String vin, String status);

    int getGoodsByExchangeCode(String iccid, String vin, String exchangeCode, String activityId);

    boolean updateExpireWinningRecord();

    int cancleOrder(Date date);
    Map getMyGoods(String activityId,String iccid, String vin,String status);

    // 获取 中奖记录详情,向前传递展示,分页查询
    PageResponse<WinningRecordVO> getWinningRecordVOPage(Integer pageNum, String activityId, String iccid, String exchangeCode, String firstPhone,String userPhone);

    List<CxWinningRecord>selectMyGoods(String activityId,String iccid, String vin);
    Map getAddressAndGoodsInfo(Integer id,String iccid, String vin);
    List<CxWinningRecord>scrollWinnerRecord();
}