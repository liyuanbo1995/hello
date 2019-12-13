package com.xinba.active.service.impl;

import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.mapper.CxActivityGoodsMapper;
import com.xinba.active.service.CxActivityGoodsService;
import com.xinba.active.util.ActivityException;
import com.xinba.active.vo.response.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @createBy XiaoWu
 * @date 2019/11/26 10:06
 */
@Service
public class CxActivityGoodsServiceImpl implements CxActivityGoodsService {

    @Autowired
    private CxActivityGoodsMapper cxActivityGoodsMapper;

    @Override
    public Boolean updateActivityGoodsWeight(String goodsId, String goodsName, Integer goodsWeight) {
        // 将校验 参数合法性工作 放在controller层
        int i = cxActivityGoodsMapper.updateActivityGoodsWeight(goodsId, goodsName, goodsWeight);
        return 1 == i;
    }

    @Override
    public Boolean updateActivityGoodsInPool(String goodsId, String goodsName, Integer isInPool) {
        int i = cxActivityGoodsMapper.updateActivityGoodsInPool(goodsId, goodsName, isInPool);
        return 1 == i;
    }

    @Override
    public Boolean updateActivityGoods(CxActivityGoods cxActivityGoods) {
        // 判断 goodsId 不存在
        if(null == getActivityGoodsByGoodsId(cxActivityGoods.getGoodsId())){
            throw new ActivityException(ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getCode(), ResultEnum.ACTIVE_GOODS_UPDATE_FAIL.getMessage());
        }
        int i = cxActivityGoodsMapper.updateActivityGoods(cxActivityGoods);
        return 1 == i;
    }

    @Override
    public CxActivityGoods saveActivityGoods(CxActivityGoods cxActivityGoods) {
        int i = cxActivityGoodsMapper.insertActivityGoods(cxActivityGoods);
        if (1 == i) {
            return cxActivityGoodsMapper.getActivityGoodsByGoodsId(cxActivityGoods.getGoodsId());
        }
        return null;
    }

    @Override
    public List<CxActivityGoods> listActivityGoodsByActivityId(String activityId) {
        return cxActivityGoodsMapper.listActivityGoodsByActivityId(activityId);
    }

    @Override
    public List<CxActivityGoods> listActivityGoods(String activityId, String goodsType, String goodsName, String goodsId, String createTime, Integer status, Integer isInPool) {
        return cxActivityGoodsMapper.selectActivityGoods(activityId, goodsType, goodsName, goodsId, createTime, status, isInPool);
    }

    @Override
    public CxActivityGoods getActivityGoodsByGoodsId(String goodsId) {
        return cxActivityGoodsMapper.getActivityGoodsByGoodsId(goodsId);
    }

    @Override
    public Integer countActivityGoodsByStatus(String activityId, Integer status) {
        Integer count = cxActivityGoodsMapper.countActivityGoodsByStatus(activityId,status);
        return null;
    }

    @Override
    public List<CommonDTO> countMultiActivityGoodsByStatus(Integer status) {
        return cxActivityGoodsMapper.countMultiActivityGoodsByStatus(status);
    }

    @Override
    public Boolean removeActivityGoods(String goodsId) {
        return 1 == cxActivityGoodsMapper.deleteActivityGoods(goodsId);
    }
}
