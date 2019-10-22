package com.xinba.flow.mapper;

import com.xinba.flow.entity.SimUsedInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SimUsedInfo)表数据库访问层
 *
 * @author tsw
 * @since 2019-07-25 14:01:35
 */
public interface SimUsedInfoMapper {

    //单卡绑定套餐
    void bindPack(@Param("iccid") String iccid,@Param("ratePlan") int ratePlan);

}