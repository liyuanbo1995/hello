package com.xinba.flow.mapper;

import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.TboxChangeRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (IccidVin)表数据库访问层
 *
 * @author tsw
 * @since 2019-09-10 14:24:29
 */
public interface VinCarMapper {

    List<IccidVin>selectIccidVin(@Param("iccid")String iccid,@Param("vin")String vin,@Param("status")Integer status);
    List<TboxChangeRecord>getTboxChangeRecord(@Param("vin")String vin);

}