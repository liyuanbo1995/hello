package com.xinba.active.mapper;


import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CxMember)表数据库访问层
 *
 * @author tsw
 * @since 2019-11-25 09:12:38
 */
public interface CxMemberMapper {

    CxMember viewMemberRecord(@Param("iccid") String iccid, @Param("vin") String vin, @Param("activityId") String activityId);

    int insertMemberRecord(CxMember cxMember);

    int updateSubMemberTimes(@Param("iccid") String iccid, @Param("vin") String vin);

    int updateAddMemberTimes(@Param("iccid") String iccid, @Param("vin") String vin);

    int updateMemberRecord(@Param("iccid") String iccid, @Param("vin") String vin, @Param("phone") String phone, @Param("subTimes") boolean subTimes, @Param("addTimes") boolean addTimes);

    // 活动参与人数统计
    Integer countMember(String activityId);

    // 活动参与 人数，多活动一起查出来
    List<CommonDTO> countMultiMember();

    int addAdress(@Param("activityId")String activityId,@Param("iccid")String iccid,@Param("vin")String vin,@Param("name")String name,@Param("phone")String phone,@Param("province")String province,@Param("city")String city,@Param("area")String area,@Param("details")String details);
    CxMember selectCxMemberInfo(@Param("iccid")String iccid,@Param("vin")String vin);
}