package com.xinba.isp.feign;

import com.xinba.isp.config.FeignClientConfiguration;
import com.xinba.isp.entity.charts.SimChangeInfo;
import com.xinba.isp.feign.fallback.JasperApiServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name="jasperApi", url="${jasper.url}",
        fallbackFactory = JasperApiServiceFallback.class,
        configuration = FeignClientConfiguration.class)
public interface JasperApiService {

    @RequestMapping(value = "/devices/{iccid}", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"},consumes={"application/json;charset=UTF-8"})
    String getSimInfo(@PathVariable("iccid") String iccid);

    @RequestMapping(value = "/devices/{iccid}", method = RequestMethod.PUT,produces = {"application/json;charset=UTF-8"},consumes={"application/json;charset=UTF-8"})
    String updateSimInfo(@PathVariable("iccid") String iccid,@RequestBody SimChangeInfo simChangeInfo);

    @RequestMapping(value = "/devices/{iccid}/smsMessages", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"},consumes={"application/json;charset=UTF-8"})
    String simUnbind(@PathVariable("iccid") String iccid,@RequestBody String messageText);

    @RequestMapping(value = "/devices/{iccid}/ctdUsages", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"},consumes={"application/json;charset=UTF-8"})
    String getSimUsages(@PathVariable("iccid") String iccid);


}

