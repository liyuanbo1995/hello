package com.xinba.isp.job;

import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.charts.ExpireDateForm;
import com.xinba.isp.feign.JasperApiService;
import com.xinba.isp.service.IspPushDataService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@Component
@Configuration
@EnableScheduling

@RestController
@RequestMapping("/syn")
public class SynInfoJob {
    @Resource
    private IspPushDataService ispPushDataService;

    /*@Scheduled(cron = "30 30 23 26 * ?")*/
    //@Scheduled(cron = "0 45 13 * * ?")
    public Wrapper synSimFromJasper() throws Exception{
        List<String> iccidList = ispPushDataService.queryAllIccid();
        ispPushDataService.synSimInfo(iccidList);
        return WrapMapper.ok();
    }

    /*@Scheduled(cron = "30 30 23 27 * ?")*/
    //@Scheduled(cron = "0 45 13 * * ?")
    public Wrapper synSimUsageFromJasper() throws Exception{
        List<String> iccidList = ispPushDataService.queryAllIccid();
        ispPushDataService.synSimUsageInfo(iccidList);
        return WrapMapper.ok();
    }

    @Scheduled(cron = "30 09 15 * * ?")
    public Wrapper pushExpireDate(){
        String url="http://localhost:8083/isp/file/test";
        List<ExpireDateForm>expireDateList=ispPushDataService.queryExpireSim();
        ispPushDataService.pushExpireDate(expireDateList,url);
        return WrapMapper.ok();
    }
}
