package com.xinba.isp.mapper;

import com.xinba.isp.entity.TboxChangeRecord;
import com.xinba.isp.entity.form.RequestData;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TboxChangeRecord)表数据库访问层
 *
 * @author tsw
 * @since 2019-09-09 11:28:41
 */
public interface IspEsbServiceMapper {

    int insertTboxChangeRecord(RequestData requestData);
    int updateIccidVin(RequestData requestData);
    int insertActiveOrUnbindRecord(@Param("iccid") String iccid,@Param("status") String status,@Param("operationPeople") String operationPeople);
    int getActiveRecord(@Param("iccid") String iccid);
    int deleteIccidVinByIccid(@Param("iccid") String iccid);
    int deletePerAuthByIccid(@Param("iccid") String iccid);
}