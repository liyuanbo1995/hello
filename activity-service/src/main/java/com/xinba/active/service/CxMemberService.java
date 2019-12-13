package com.xinba.active.service;

import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxMember;
import java.util.List;

/**
 * (CxMember)表服务接口
 *
 * @author tsw
 * @since 2019-11-25 09:12:39
 */
public interface CxMemberService {

    CxMember viewMemberRecord(String iccid, String vin, String activityId);

    boolean payCallBack(String activityId, String iccid, String vin, String exchangeCode);

    boolean selectMemberRecord(String iccid, String vin, String activityId);

    // 活动参与 人数
    Integer countMember(String activityId);

    // 活动参与 人数，多活动一起查出来
    List<CommonDTO> countMultiMember();

    int addAdress(String activityId,String iccid,String vin,String name,String phone,String province,String city,String area,String details);
    CxMember selectCxMemberInfo(String iccid,String vin);

    int insertGiftByGoodsId(String goodsId,String orderId);
}