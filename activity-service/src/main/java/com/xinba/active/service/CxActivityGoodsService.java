package com.xinba.active.service;

import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxActivityGoods;

import java.util.List;

/**
 * 活动商品
 *
 * @createBy XiaoWu
 * @date 2019/11/26 10:05
 */
public interface CxActivityGoodsService {

    // 功能点单独抽出来
    // 修改商品 权重和抽奖概率,goodsName可能会有多个
    Boolean updateActivityGoodsWeight(String goodsId, String goodsName, Integer goodsWeight);

    // 修改商品 是否在奖池里面
    Boolean updateActivityGoodsInPool(String goodsId, String goodsName, Integer isInPool);

    // 修改商品 信息
    Boolean updateActivityGoods(CxActivityGoods cxActivityGoods);

    // 添加商品信息
    CxActivityGoods saveActivityGoods(CxActivityGoods cxActivityGoods);

    // 通过activityId 查询活动商品列表
    List<CxActivityGoods> listActivityGoodsByActivityId(String activityId);

    // 通过条件筛选，活动商品列表
    List<CxActivityGoods> listActivityGoods(String activityId, String goodsType, String goodsName, String goodsId, String createTime, Integer status, Integer isInPool);

    // 通过条件筛选
//    List<CxActivityGoods> listActivityGoodsByConditions(String activityId, String goodsType, String goodsName, String goodsId, String createTime, Integer status, Integer isInPool);

    // 通过goodsId 删除活动商品

    // 通过实例对象，通过goodId 查询活动商品
    CxActivityGoods getActivityGoodsByGoodsId(String goodsId);

    // 通过活动id和上下架状态 统计奖项数
    Integer countActivityGoodsByStatus(String activityId, Integer status);

    // 统计奖项数 避免重复调用数据库
    List<CommonDTO> countMultiActivityGoodsByStatus(Integer status);

    // 删除活动商品
    Boolean removeActivityGoods(String goodsId);
}
