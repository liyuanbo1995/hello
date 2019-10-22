package com.xinba.isp.feign;

import com.xinba.isp.entity.form.JasperRequestBody;
import com.xinba.isp.entity.form.JasperResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="jasperVerifyApi", url="${jasper.url2}", decode404 = true)
public interface JasperVerifyService{
    @RequestMapping(method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"},consumes={"application/json;charset=UTF-8"})
    JasperResponseBody getJasperVerify(@RequestBody JasperRequestBody jasperRequestBody);

}
