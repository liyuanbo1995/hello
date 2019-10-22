package com.xinba.flow.algorithm;

import com.xinba.flow.entity.IccidVin;
import com.xinba.flow.mapper.SimInfoMapper;

import java.util.List;

public class IccidThread implements Runnable {


    private List<IccidVin> iccidVinList;
    private SimInfoMapper simInfoMapper;

    public IccidThread(List<IccidVin> iccidVinList,SimInfoMapper simInfoMapper) {
        this.iccidVinList = iccidVinList;
        this.simInfoMapper=simInfoMapper;
    }

    @Override
    public void run() {
        simInfoMapper.addSimVinInfo(iccidVinList);
    }
}
