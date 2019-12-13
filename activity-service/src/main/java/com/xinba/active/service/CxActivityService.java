package com.xinba.active.service;

import com.xinba.active.entity.CxActivity;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.common.entity.Wrapper;
import java.util.List;

/**
 * (CxActivity)表服务接口
 * 只维护 实现过程，不修改接口参数和返回
 * @author tsw
 * @since 2019-10-29 14:13:23
 */
public interface CxActivityService {

    // 添加活动
    int insertActivity(CxActivity cxActivity);

    // 更新活动
    Boolean updateActivity(CxActivity cxActivity);

    // 更新活动状态
    Boolean updateActivityStatus(String activityId, Integer status);

    // 获取活动信息，不包括活动商品列表
    List<CxActivity> selectActivityInfo(Integer status,String activityType,String activityId,String activityName);

    // 逻辑迁移到 CxActivityGoodsService
    // 获取活动 商品信息
    List<CxActivityGoods> selectActivityGoods(String activityId, String goodsType, String goodsName, String goodsId, String createTime, Integer status, Integer isInPool);

    // 前端 获取活动商品
    List<CxActivityGoods> selectAcGoods(String activityId, Integer status, Integer isInPool, Integer count);

    // 添加活动 商品
    int insertActivityGoods(CxActivityGoods cxActivityGoods);

    // 获取商品详情
    CxActivityGoods getGoodsInfo(String goodsId,String goodsName);

    // 获取活动详情
    CxActivity getActivityInfo(String activityId);


    Wrapper getLotteryGoods(String activityId,String iccid,String vin,String phone);

    // 删除活动
    Boolean deleteActivity(String activityId);

    // 批量删除活动
    Boolean deleteMultiActivity(List<String> activityIdList);
}