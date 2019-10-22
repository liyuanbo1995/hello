package com.xinba.flow.service;

import com.xinba.flow.entity.SimUsedInfo;
import java.util.List;

/**
 * (SimUsedInfo)表服务接口
 *
 * @author tsw
 * @since 2019-07-25 14:01:35
 */
public interface SimUsedInfoService {
    //单卡绑定套餐
    void bindPack(String iccid,int ratePlan);

}