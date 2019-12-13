package com.xinba.active.mapper;

import com.xinba.active.entity.CxActivity;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.entity.CxWinningRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CxActivity)表数据库访问层
 *
 * @author tsw
 * @since 2019-10-29 14:13:21
 */
public interface CxActivityMapper {

    int insertActivity(CxActivity cxActivity);

    // 通过条件筛选 活动列表
    List<CxActivity> selectActivityInfo(@Param("status") Integer status, @Param("activityType") String activityType, @Param("activityId") String activityId, @Param("activityName") String activityName);

    CxActivity getActivityInfo(@Param("activityId") String activityId);

    // 设置商品记录表 状态为已过期
    int updateExpireWinningRecord();

    CxActivity selectSingleActivity(@Param("status") Integer status, @Param("activityType") String activityType, @Param("activityId") String activityId, @Param("activityName") String activityName);

    // 更新活动信息
    Integer updateActivity(CxActivity cxActivity);

    Integer updateActivityStatus(@Param("activityId") String activityId, @Param("status") Integer status);

    // 删除活动
    Integer deleteActivity(String activityId);

    // 批量删除活动
    Integer deleteMultiActivity(List<String> activityIdList);
}