package com.xinba.flow.service.impl;

import com.xinba.flow.entity.SysLog;
import com.xinba.flow.mapper.SysLogMapper;
import com.xinba.flow.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (SysLog)表服务实现类
 *
 * @author tsw
 * @since 2019-09-19 11:13:57
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;


    public void saveSysLog(SysLog sysLog){
        sysLogMapper.saveSysLog(sysLog);}
}