package com.xinba.active.job;

import com.xinba.active.service.CxWinningRecordService;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;

@Component
@Configuration
@EnableScheduling
public class TimeJob {
    @Resource
    private CxWinningRecordService cxWinningRecordService;

    //每天去看一次中奖记录中奖品是否有过期的，刷新奖品状态
    @Scheduled(cron = "01 00 00 * * ?")
    public Wrapper updateExpireWinningRecord(){
        cxWinningRecordService.updateExpireWinningRecord();
        return WrapMapper.ok();
    }

    //15分钟后将待支付订单取消
//   @Scheduled(cron = "01 * * * * ?")
    public Wrapper cancleOrder(){
        cxWinningRecordService.cancleOrder(new Date());
        return WrapMapper.ok();
    }
}
