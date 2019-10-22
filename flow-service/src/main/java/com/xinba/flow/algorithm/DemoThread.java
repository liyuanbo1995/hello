package com.xinba.flow.algorithm;

import com.xinba.flow.entity.SimInfo;
import com.xinba.flow.mapper.SimInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

public class DemoThread implements Runnable {


    private List<SimInfo> simInfoList;
    private SimInfoMapper simInfoMapper;

    public DemoThread(List<SimInfo> simInfoList,SimInfoMapper simInfoMapper) {
        this.simInfoList = simInfoList;
        this.simInfoMapper=simInfoMapper;
    }

    @Override
    public void run() {
        simInfoMapper.addSim(simInfoList);
    }
}
