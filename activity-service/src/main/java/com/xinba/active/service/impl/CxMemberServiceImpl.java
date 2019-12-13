package com.xinba.active.service.impl;

import com.xinba.active.dto.CommonDTO;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.entity.CxMember;
import com.xinba.active.entity.CxWinningRecord;
import com.xinba.active.entity.order.XfGift;
import com.xinba.active.mapper.CxActivityGoodsMapper;
import com.xinba.active.mapper.CxMemberMapper;
import com.xinba.active.mapper.CxWinningRecordMapper;
import com.xinba.active.mapper.XfGiftMapper;
import com.xinba.active.service.CxMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * (CxMember)表服务实现类
 *
 * @author tsw
 * @since 2019-11-25 09:12:39
 */
@Service("cxMemberService")
public class CxMemberServiceImpl implements CxMemberService {
    @Resource
    private CxMemberMapper cxMemberMapper;
    @Autowired
    private CxWinningRecordMapper cxWinningRecordMapper;
    @Resource
    private CxActivityGoodsMapper cxActivityGoodsMapper;
    @Autowired
    private XfGiftMapper xfGiftMapper;


    public CxMember viewMemberRecord(String iccid, String vin,String activityId){
        CxMember memberRecord=cxMemberMapper.viewMemberRecord(iccid,vin,activityId);
        if(memberRecord==null){
            CxMember cxMember=new CxMember();
            cxMember.setIccid(iccid);
            cxMember.setVin(vin);
            cxMember.setActivityQualification("true");
            cxMember.setActivityTimes(1);
            cxMember.setActivityId(activityId);
            cxMember.setCreateTime(new Date());
            cxMemberMapper.insertMemberRecord(cxMember);
            return cxMember;
        }else {
            if(memberRecord.getActivityQualification().equals("true")&&memberRecord.getActivityTimes()>0){
                return memberRecord;
            }else {
                return null;
            }
        }

    }

    @Override
    public boolean selectMemberRecord(String iccid, String vin, String activityId) {
        CxMember memberRecord=cxMemberMapper.viewMemberRecord(iccid,vin,activityId);
        if(memberRecord==null||memberRecord.getActivityTimes()==0)
        {
            return false;
        }
        return true;
    }



    @Override
    @Transactional
    public boolean payCallBack(String activityId,String iccid, String vin, String exchangeCode) {
        List<CxWinningRecord> winningRecordList = cxWinningRecordMapper.selectWiningRecord(activityId, iccid, vin, "已使用", null,"待支付");
        if (winningRecordList.size() == 0) {
            return false;
        }
        for (CxWinningRecord winningRecord : winningRecordList) {
            if (winningRecord.getExchangeCode() != null) {
                if(winningRecord.getExchangeCode().equals(exchangeCode)) {
                    cxWinningRecordMapper.updateWinningRecord(iccid, vin, exchangeCode, "已使用", "已支付", null, null, null);
                    cxMemberMapper.updateAddMemberTimes(iccid, vin);
                    CxActivityGoods activityGoods=cxActivityGoodsMapper.getGoodsInfo(winningRecord.getGoodsId(),null);
                    if(activityGoods.getGoodsType()==0) {
                        insertGiftByGoodsId(winningRecord.getGoodsId(), winningRecord.getOrderId());
                    }
                    return true;
                }
            } else {
                CxWinningRecord cxWinningRecord = cxWinningRecordMapper.selectRecordById(winningRecord.getRedeemedId());
                if(cxWinningRecord.getExchangeCode().equals(exchangeCode)) {
                    cxWinningRecordMapper.updateWinningRecord(iccid, vin, null, "已使用", "已支付", null, null, winningRecord.getRedeemedId());
                    cxMemberMapper.updateAddMemberTimes(iccid, vin);
                    cxMemberMapper.updateAddMemberTimes(cxWinningRecord.getIccid(), cxWinningRecord.getVin());
                    CxActivityGoods activityGoods=cxActivityGoodsMapper.getGoodsInfo(winningRecord.getGoodsId(),null);
                    if(activityGoods.getGoodsType()==0) {
                        insertGiftByGoodsId(winningRecord.getGoodsId(),winningRecord.getOrderId());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Integer countMember(String activityId) {
        return cxMemberMapper.countMember(activityId);
    }

    @Override
    public List<CommonDTO> countMultiMember() {
        return cxMemberMapper.countMultiMember();
    }

    @Override
    public int addAdress(String activityId, String iccid, String vin, String name, String phone, String province, String city, String area, String details) {
        return cxMemberMapper.addAdress(activityId, iccid, vin, name, phone, province, city, area, details);
    }

    @Override
    public CxMember selectCxMemberInfo(String iccid, String vin) {
        CxMember cxMember=cxMemberMapper.selectCxMemberInfo(iccid, vin);
        return cxMember;
    }

    @Override
    public int insertGiftByGoodsId(String goodsId, String orderId) {
        CxActivityGoods activityGoods=cxActivityGoodsMapper.getGoodsInfo(goodsId,null);
        XfGift xfGift=new XfGift();
        xfGift.setOrderId(orderId);
        xfGift.setGiftName(activityGoods.getGoodsName());
        xfGift.setGiftUrl(activityGoods.getGoodsImg());
        xfGift.setDetailsName(activityGoods.getGoodsDetail());
        xfGift.setDetailsPrice(activityGoods.getPrice());
        return  xfGiftMapper.insertGift(xfGift);
    }


}