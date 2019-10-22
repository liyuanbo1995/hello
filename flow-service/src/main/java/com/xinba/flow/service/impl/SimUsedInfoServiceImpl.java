package com.xinba.flow.service.impl;

import com.xinba.flow.entity.SimUsedInfo;
import com.xinba.flow.mapper.SimUsedInfoMapper;
import com.xinba.flow.service.SimUsedInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SimUsedInfo)表服务实现类
 *
 * @author tsw
 * @since 2019-07-25 14:01:35
 */
@Service("simUsedInfoService")
public class SimUsedInfoServiceImpl implements SimUsedInfoService {
    @Resource
    private SimUsedInfoMapper simUsedInfoMapper;
    //单卡绑定套餐
    public void bindPack(String iccid,int ratePlan){ simUsedInfoMapper.bindPack(iccid,ratePlan); }
}