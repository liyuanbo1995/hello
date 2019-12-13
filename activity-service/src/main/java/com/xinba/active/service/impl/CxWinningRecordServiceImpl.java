package com.xinba.active.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.active.constant.ActivityConsts;
import com.xinba.active.dto.CxRecordOrderDTO;
import com.xinba.active.dto.CxWinningRecordDTO;
import com.xinba.active.entity.CxActivityGoods;
import com.xinba.active.entity.CxMember;
import com.xinba.active.entity.CxWinningRecord;
import com.xinba.active.mapper.CxActivityGoodsMapper;
import com.xinba.active.mapper.CxMemberMapper;
import com.xinba.active.mapper.CxWinningRecordMapper;
import com.xinba.active.mapper.XfOrderMapper;
import com.xinba.active.service.CxWinningRecordService;
import com.xinba.active.vo.WinningRecordVO;
import com.xinba.active.vo.response.PageConsts;
import com.xinba.active.vo.response.PageResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;

/**
 * (CxWinningRecord)表服务实现类
 *
 * @author tsw
 * @since 2019-11-25 10:37:07
 */
@Service("cxWinningRecordService")
public class CxWinningRecordServiceImpl implements CxWinningRecordService {
    @Resource
    private CxWinningRecordMapper cxWinningRecordMapper;
    @Resource
    private CxActivityGoodsMapper cxActivityGoodsMapper;
    @Autowired
    private XfOrderMapper xfOrderMapper;
    @Autowired
    private CxMemberMapper cxMemberMapper;


    public List<CxWinningRecord> selectWiningRecord(String activityId,String iccid, String vin,String status) {
        List<CxWinningRecord> winningRecordList = cxWinningRecordMapper.selectWiningRecord(activityId, iccid, vin, status, null,null);
        return winningRecordList;
    }

    @Override
    @Transactional
    public int getGoodsByExchangeCode(String iccid, String vin, String exchangeCode,String activityId) {
        CxWinningRecord cxWinningRecord = cxWinningRecordMapper.getWinningRecordByCode(exchangeCode,activityId);
        if (cxWinningRecord == null) {
            return 0;
        } else if (cxWinningRecord.getIccid().equals(iccid)) {
            return 1;
        }
        cxWinningRecordMapper.updateWinningRecord(cxWinningRecord.getIccid(), null, exchangeCode, "已兑换",null,null,null,null);//将奖品记录更新
        CxWinningRecord winningRecord = new CxWinningRecord();
        winningRecord.setActivityId(cxWinningRecord.getActivityId());
        winningRecord.setGoodsId(cxWinningRecord.getGoodsId());
        winningRecord.setIccid(iccid);
        winningRecord.setVin(vin);
        winningRecord.setCreateTime(new Date());
        winningRecord.setStatus("未使用");
        winningRecord.setPayStatus("未支付");
        winningRecord.setRedeemedId(cxWinningRecord.getId());
        winningRecord.setIsFirstUser("false");
        cxWinningRecordMapper.insertWinningRecord(winningRecord);//在中奖记录中新增一条
        return 2;
    }


    public boolean updateExpireWinningRecord() {
        cxWinningRecordMapper.updateExpireWinningRecord();
        return true;
    }

    public int cancleOrder(Date date){
       return cxWinningRecordMapper.cancleOrder(date);
    }

    @Override
    public Map getMyGoods(String activityId, String iccid, String vin, String status) {
        Map<String,Object>map=new HashMap<>();
        List<CxWinningRecord>canUseList=new LinkedList<>();
        List<CxWinningRecord>usedList=new LinkedList<>();
        List<CxWinningRecord>expireList=new LinkedList<>();
        List<CxWinningRecord>myWinningList=cxWinningRecordMapper.selectWiningRecordAndGoodsInfo(activityId,iccid,vin,status,null,null);
        for(CxWinningRecord myGoods:myWinningList) {
            if (myGoods.getStatus().equals("未使用")) {
                canUseList.add(myGoods);
            } else if (myGoods.getStatus().equals("已使用")) {
                usedList.add(myGoods);
            } else if (myGoods.getStatus().equals("已过期")) {
                expireList.add(myGoods);
            }
        }
        map.put("expireList",expireList);
        map.put("usedList",usedList);
        map.put("canUseList",canUseList);
        map.put("expireListSize",expireList.size());
        map.put("usedListSize",usedList.size());
        map.put("canUseListSize",canUseList.size());
        return map;
    }

    @Override
    public List<CxWinningRecord> selectMyGoods(String activityId, String iccid, String vin) {
        return cxWinningRecordMapper.selectWiningRecordAndGoodsInfo(activityId,iccid,vin,"未使用",null, null);
    }

    @Override
    public Map getAddressAndGoodsInfo(Integer id, String iccid, String vin) {
        Map<String, Object> map = new HashMap<>();
        CxWinningRecord winningRecord = new CxWinningRecord();
        CxWinningRecord cxWinningRecord = cxWinningRecordMapper.selectRecordById(id);
        if (cxWinningRecord != null) {
            if (cxWinningRecord.getIccid().equals(iccid) && cxWinningRecord.getStatus().equals("未使用")) {
                CxActivityGoods activityGoods = cxActivityGoodsMapper.getGoodsInfo(cxWinningRecord.getGoodsId(), null);
                winningRecord.setGoodsName(activityGoods.getGoodsName());
                winningRecord.setPrice(activityGoods.getPrice());
                winningRecord.setGoodsDetail(activityGoods.getGoodsDetail());
                winningRecord.setGoodsInfo(activityGoods.getGoodsInfo());
                winningRecord.setGoodsType(activityGoods.getGoodsType());
                if (cxWinningRecord.getExchangeCode() == null || cxWinningRecord.getExchangeCode() == "") {
                    CxWinningRecord redeemedWinningRecord = cxWinningRecordMapper.selectRecordById(cxWinningRecord.getRedeemedId());
                    winningRecord.setExchangeCode(redeemedWinningRecord.getExchangeCode());
                } else {
                    winningRecord.setExchangeCode(cxWinningRecord.getExchangeCode());
                }
                if (activityGoods.getGoodsType() == 0) {
                    CxMember cxMember = cxMemberMapper.selectCxMemberInfo(iccid, vin);
                    map.put("memberAddress", cxMember);
                }
                map.put("winningRecord", winningRecord);
            }
        }
        return map;
    }

    @Override
    public List<CxWinningRecord> scrollWinnerRecord() {
        return cxWinningRecordMapper.selectScrollWinningRecord();
    }


    @Override
    public PageResponse<WinningRecordVO> getWinningRecordVOPage(Integer pageNum, String activityId, String iccid, String exchangeCode, String firstPhone, String userPhone) {
        // 封装分页对象
        PageHelper.startPage(pageNum,PageConsts.DEFAULT_PAGE_SIZE);
        List<CxWinningRecordDTO> cxWinningRecordDTOList = cxWinningRecordMapper.listWinningRecord2One(activityId, iccid, exchangeCode, firstPhone, userPhone);
        PageInfo<CxWinningRecordDTO> cxWinningRecordDTOPageInfo = new PageInfo<>(cxWinningRecordDTOList);

        // 先优先实现 two to one 逻辑
        List<WinningRecordVO> winningRecordVOList = new ArrayList<>();
        for(CxWinningRecordDTO cxWinningRecordDTO : cxWinningRecordDTOList){
            WinningRecordVO winningRecordVO = new WinningRecordVO();
            BeanUtils.copyProperties(cxWinningRecordDTO, winningRecordVO);
            if("false".equals(cxWinningRecordDTO.getIsFirstUser())){
                winningRecordVO.setOrderId(cxWinningRecordDTO.getUserOrderId());
                winningRecordVO.setStatus(cxWinningRecordDTO.getUserStatus());
            }else{
                winningRecordVO.setOrderId(cxWinningRecordDTO.getFirstOrderId());
                winningRecordVO.setStatus(cxWinningRecordDTO.getFirstOrderId());
            }
            winningRecordVOList.add(winningRecordVO);
        }

        // 获取orderId list
//        List<String> orderIdList = winningRecordVOList.stream().map(WinningRecordVO::getOrderId).collect(Collectors.toList());
//        // in 查询详细信息
//        cxWinningRecordMapper.listWinningRecordOrderDetails(orderIdList);

        // 循环 对于非空的订单号 查询并复制
        for(WinningRecordVO winningRecordVO : winningRecordVOList){
            if(null != winningRecordVO.getOrderId()){
                CxRecordOrderDTO winningRecordOrderDetail = cxWinningRecordMapper.getWinningRecordOrderDetails(winningRecordVO.getOrderId());
                winningRecordVO.setOrderDetail(winningRecordOrderDetail);
                // 对比 订单号和物流号 确定订单状态
                if(null != winningRecordVO.getLogisticsId()){
                    winningRecordVO.setLogisticsStatus(ActivityConsts.GIFT_SHIPPED_STATUS);
                }
                winningRecordVO.setLogisticsStatus(ActivityConsts.GIFT_UNDELIVERED_STATUS);
            }
        }

        // 封装对象
        PageResponse<WinningRecordVO> winningRecordVOPageResponse = new PageResponse<>(cxWinningRecordDTOPageInfo.getTotal(), cxWinningRecordDTOPageInfo.getPageSize(), cxWinningRecordDTOPageInfo.getPageNum(), winningRecordVOList);
        return winningRecordVOPageResponse;
    }
}