package com.xinba.flow.service.impl;

import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.entity.TboxChangeRecord;
import com.xinba.flow.mapper.VinCarMapper;
import com.xinba.flow.service.VinCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (IccidVin)表服务实现类
 *
 * @author tsw
 * @since 2019-09-10 14:24:05
 */
@Service("iccidVinService")
public class VinCarServiceImpl implements VinCarService {
    @Resource
    private VinCarMapper vinCarMapper;

    public List<IccidVin>selectIccidVin(String iccid,String vin,Integer status){return vinCarMapper.selectIccidVin(iccid,vin,status);}
    public List<TboxChangeRecord>getTboxChangeRecord(String vin){return vinCarMapper.getTboxChangeRecord(vin);}
}