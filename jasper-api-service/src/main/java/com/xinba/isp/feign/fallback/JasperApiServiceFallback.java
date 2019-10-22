package com.xinba.isp.feign.fallback;

import com.xinba.isp.entity.charts.SimChangeInfo;
import com.xinba.isp.feign.JasperApiService;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class JasperApiServiceFallback implements FallbackFactory<JasperApiService> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public JasperApiService create(Throwable throwable) {
        String msg = throwable.toString();
        if (!StringUtils.isEmpty(msg)) {
            logger.error(msg);
        }
        return new JasperApiService() {
            @Override
            public String getSimInfo(String iccid) {
                return msg;
            }

            @Override
            public String updateSimInfo(String iccid, SimChangeInfo simChangeInfo) {
                return msg;
            }

            @Override
            public String simUnbind(String iccid, String messageText){return msg;}

            @Override
            public String getSimUsages(String iccid) {
                return msg;
            }

        };
    }
}