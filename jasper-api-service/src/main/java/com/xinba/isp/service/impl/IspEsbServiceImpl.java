package com.xinba.isp.service.impl;

import com.xinba.isp.entity.TboxChangeRecord;
import com.xinba.isp.entity.form.RequestData;
import com.xinba.isp.mapper.IspEsbServiceMapper;
import com.xinba.isp.service.IspEsbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TboxChangeRecord)表服务实现类
 *
 * @author tsw
 * @since 2019-09-09 11:28:42
 */
@Service("tboxChangeRecordService")
public class IspEsbServiceImpl implements IspEsbService {
    @Resource
    private IspEsbServiceMapper ispEsbServiceMapper;

    public int insertTboxChangeRecord(RequestData requestData){return ispEsbServiceMapper.insertTboxChangeRecord(requestData);}
    public int updateIccidVin(RequestData requestData){return ispEsbServiceMapper.updateIccidVin(requestData);}
    public int insertActiveOrUnbindRecord(String iccid,String status,String operationPeople){return ispEsbServiceMapper.insertActiveOrUnbindRecord(iccid,status,operationPeople);}
    public  int getActiveRecord(String iccid){return ispEsbServiceMapper.getActiveRecord(iccid);}
    public int setVinUnBind(String iccid){
        ispEsbServiceMapper.deletePerAuthByIccid(iccid);
        return ispEsbServiceMapper.deleteIccidVinByIccid(iccid);
    }
}