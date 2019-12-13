package com.xinba.active.mapper;

import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxActivityGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CxActivityGoodsMapper {

    // 新增活动商品
    int insertActivityGoods(CxActivityGoods cxActivityGoods);

    // 根据筛选条件 获取活动商品列表
    List<CxActivityGoods> selectActivityGoods(@Param("activityId") String activityId, @Param("goodsType") String goodsType, @Param("goodsName") String goodsName, @Param("goodsId") String goodsId, @Param("createTime") String createTime, @Param("status") Integer status, @Param("isInPool") Integer isInPool);

    List<CxActivityGoods> selectAcGoods(@Param("activityId") String activityId, @Param("status") Integer status, @Param("isInPool") Integer isInPool, @Param("count") Integer count);

    int updateSubGoodsAmount(@Param("goodsId") String goodsId);

    // 添加商品库存
    int updateAddGoodsAmount();

    CxActivityGoods getGoodsInfo(@Param("goodsId") String goodsId,@Param("goodsName") String goodsName);

    // 修改商品 权重和抽奖概率
    int updateActivityGoodsWeight(@Param("goodsId") String goodsId, @Param("goodsName") String goodsName, @Param("goodsWeight") Integer goodsWeight);

    // 修改商品 是否在奖池里面
    int updateActivityGoodsInPool(@Param("goodsId") String goodsId, @Param("goodsName") String goodsName, @Param("isInPool") Integer isInPool);

    // 修改商品 信息
    int updateActivityGoods(@Param("cxActivityGoods") CxActivityGoods cxActivityGoods);

    // 通过goodsId 查询商品信息
    CxActivityGoods getActivityGoodsByGoodsId(String goodsId);

    // 通过activityId 查询活动商品列表
    List<CxActivityGoods> listActivityGoodsByActivityId(String activityId);

    // 通过活动id和上下架状态 统计奖项数
    Integer countActivityGoodsByStatus(@Param("activityId") String activityId, @Param("status") Integer status);

    // 统计奖项数 避免重复调用数据库
    List<CommonDTO> countMultiActivityGoodsByStatus(Integer status);

    // 删除活动商品
    Integer deleteActivityGoods(String goodsId);
}
