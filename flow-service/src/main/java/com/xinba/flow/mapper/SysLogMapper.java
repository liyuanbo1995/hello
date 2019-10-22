package com.xinba.flow.mapper;

import com.xinba.flow.entity.SysLog;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysLog)表数据库访问层
 *
 * @author tsw
 * @since 2019-09-19 11:13:56
 */
public interface SysLogMapper {

    void saveSysLog(SysLog sysLog);

}