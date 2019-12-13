package com.xinba.active.service.impl;

import com.xinba.active.entity.CxActivity;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.entity.CxMember;
import com.xinba.active.entity.CxWinningRecord;
import com.xinba.active.mapper.CxActivityGoodsMapper;
import com.xinba.active.mapper.CxActivityMapper;
import com.xinba.active.mapper.CxMemberMapper;
import com.xinba.active.mapper.CxWinningRecordMapper;
import com.xinba.active.service.CxActivityService;
import com.xinba.active.service.CxMemberService;
import com.xinba.active.util.ActiveAlgorithm;
import com.xinba.active.util.ActivityException;
import com.xinba.active.util.SbRandomUtil;
import com.xinba.active.vo.response.ResultEnum;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CxActivity)表服务实现类
 *
 * @author tsw
 * @since 2019-10-29 14:13:23
 */
@Service("cxActivityService")
@Slf4j
public class CxActivityServiceImpl implements CxActivityService {
    @Resource
    private CxActivityMapper cxActivityMapper;
    @Resource
    private CxActivityGoodsMapper cxActivityGoodsMapper;
    @Resource
    private CxWinningRecordMapper cxWinningRecordMapper;
    @Resource
    private CxMemberMapper cxMemberMapper;
    @Resource
    private CxMemberService cxMemberService;

    /**
     * 添加活动
     *
     * @param cxActivity
     * @return
     */
    public int insertActivity(CxActivity cxActivity) {

        return cxActivityMapper.insertActivity(cxActivity);
    }

    /**
     * 更新活动
     * @param cxActivity
     * @return
     */
    @Override
    public Boolean updateActivity(CxActivity cxActivity) {
        // 先判断activity_id 是否合法
        if(null == this.getActivityInfo(cxActivity.getActivityId())){
            log.info("[CxActivityService-updateActivity]活动业务异常，活动id[{}]不存在",cxActivity.getActivityId());
            throw new ActivityException(ResultEnum.ACTIVITY_NOT_EXIST);
        }
        return 1 == cxActivityMapper.updateActivity(cxActivity);
    }

    @Override
    public Boolean updateActivityStatus(String activityId, Integer status) {
        // 先判断activity_id 是否合法
        if(null == this.getActivityInfo(activityId)){
            log.info("[CxActivityService-updateActivityStatus]活动业务异常，活动id[{}]不存在",activityId);
            throw new ActivityException(ResultEnum.ACTIVITY_NOT_EXIST);
        }
        return 1 == cxActivityMapper.updateActivityStatus(activityId,status);
    }

    public List<CxActivity> selectActivityInfo(Integer status, String activityType, String activityId, String activityName) {
        // 将分页逻辑，移到service
        return cxActivityMapper.selectActivityInfo(status, activityType, activityId, activityName);
    }

    public List<CxActivityGoods> selectActivityGoods(String activityId, String goodsType, String goodsName, String goodsId, String createTime, Integer status, Integer isInPool) {
        return cxActivityGoodsMapper.selectActivityGoods(activityId, goodsType, goodsName, goodsId, createTime, status, isInPool);
    }

    public List<CxActivityGoods> selectAcGoods(String activityId, Integer status, Integer isInPool, Integer count) {
        return cxActivityGoodsMapper.selectAcGoods(activityId, status, isInPool, count);
    }

    public int insertActivityGoods(CxActivityGoods cxActivityGoods) {
        return cxActivityGoodsMapper.insertActivityGoods(cxActivityGoods);
    }


    @Override
    public CxActivityGoods getGoodsInfo(String goodsId, String goodsName) {
        return cxActivityGoodsMapper.getGoodsInfo(goodsId, goodsName);
    }

    @Override
    public CxActivity getActivityInfo(String activityId) {
        return cxActivityMapper.getActivityInfo(activityId);
    }


    @Override
    @Transactional
    public Wrapper getLotteryGoods(String activityId, String iccid, String vin, String phone) {
        Map<String,Object> map=new HashMap<>();
        Date nowTime = new Date();
        CxActivity activity = cxActivityMapper.selectSingleActivity(0, null, activityId, null);
        if(activity==null){
            return WrapMapper.wrap(101, "暂无该活动");
        }
        if (nowTime.after(activity.getBeginTime()) && nowTime.before(activity.getEndTime())) {
            if (cxMemberService.selectMemberRecord(iccid, vin, activity.getActivityId()) == false) {
                return WrapMapper.wrap(101, "你没资格抽奖");
            }
            //抽奖
            //奖品属性，实物情况下检查是否中过实物奖品
            //更新记录
            List<CxActivityGoods> activityGoodsList = cxActivityGoodsMapper.selectActivityGoods(activityId, null, null, null, null, 0, 0);//将所有满足条件的奖品选出来
            if (activityGoodsList.size() == 0) {
                return WrapMapper.wrap(201, activity.getActivityName() + "暂无奖品");
            } else {
                CxActivityGoods activityGoods = ActiveAlgorithm.getActivityGoodsIndex(activityGoodsList);
                if (activityGoods.getGoodsType()==0) {//看是否已抽过了实物
                    CxWinningRecord winningRecord = cxWinningRecordMapper.selectWinningRecordByGoodsType(activityId, iccid, vin, 0);
                    if (winningRecord!=null) {//抽过了实物
                        activityGoods = cxActivityGoodsMapper.getGoodsInfo(null, "续费立减30元");
                    }
                }else if (activityGoods.getGoodsRemain() == 0 ) {
                    activityGoods = cxActivityGoodsMapper.getGoodsInfo(null, "续费立减30元");
                }
                cxActivityGoodsMapper.updateSubGoodsAmount(activityGoods.getGoodsId());
                CxWinningRecord cxWinningRecord = new CxWinningRecord();//插入中奖记录
                cxWinningRecord.setActivityId(activity.getActivityId());
                cxWinningRecord.setGoodsId(activityGoods.getGoodsId());
                cxWinningRecord.setIccid(iccid);
                cxWinningRecord.setVin(vin);
                cxWinningRecord.setPayStatus("未支付");
                cxWinningRecord.setStatus("未使用");
                cxWinningRecord.setIsFirstUser("true");
                cxWinningRecord.setCreateTime(new Date());
                int count = 1;
                String exchangeCode = null;
                while (count != 0) {
                    exchangeCode = SbRandomUtil.randomString(6);
                    count = cxWinningRecordMapper.getExchangeCode(exchangeCode);
                }
                cxWinningRecord.setExchangeCode(exchangeCode);
                cxWinningRecordMapper.insertWinningRecord(cxWinningRecord);
                cxMemberMapper.updateMemberRecord(iccid, vin,phone,true,false);//减少一次用户表中的参与活动次数
                CxActivityGoods returnGoods = cxActivityGoodsMapper.getGoodsInfo(activityGoods.getGoodsId(), null);
                CxMember cxMember=cxMemberMapper.viewMemberRecord(iccid,vin,activityId);
                map.put("returnGoods",returnGoods);
                map.put("activityTimes",cxMember.getActivityTimes());
                map.put("phone",cxMember.getPhone());

                return WrapMapper.ok(map);
            }
        } else {
            return WrapMapper.wrap(500, "该活动已过期");
        }
    }

    @Override
    public Boolean deleteActivity(String activityId) {
        // 判断 activityId 是否合法
        if(null == this.getActivityInfo(activityId)){
            log.info("[CxActivityService-deleteActivity]活动业务异常，活动id[{}]不存在",activityId);
            throw new ActivityException(ResultEnum.ACTIVITY_NOT_EXIST);
        }
        return 1 == cxActivityMapper.deleteActivity(activityId);
    }

    @Override
    public Boolean deleteMultiActivity(List<String> activityIdList) {
        return cxActivityMapper.deleteMultiActivity(activityIdList) > 0;
    }

}