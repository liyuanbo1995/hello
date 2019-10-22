package com.xinba.isp.feign;

import com.xinba.isp.entity.form.JasperRequestBody;
import com.xinba.isp.entity.form.JasperResponseBody;
import com.xinba.isp.entity.form.RequestData;
import com.xinba.isp.entity.form.ResponseData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
@Service
public class GetTokenService {
    @Resource
    private JasperVerifyService jasperVerifyService;
    @Resource
    private RedisTemplate redisTemplate;

    public void getToken(){
        JasperRequestBody tokenJasperRequestBody =new JasperRequestBody();
        RequestData tokenRequestData=new RequestData();
        ResponseData tokenResponseData=new ResponseData();
        JasperResponseBody tokenJasperResponseBody=new JasperResponseBody();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2=new SimpleDateFormat("yyyyMMddHHmmss");
        tokenRequestData.setUsername("simba");
        tokenRequestData.setPassword("dce3f0c9f7c73cc09b1e88355f30cc82");
        tokenJasperRequestBody.setRequest_data(tokenRequestData);
        tokenJasperRequestBody.setSerial_number(df2.format(new Date())+"simba"+((int)((Math.random()*9+1)*1000000)));
        tokenJasperRequestBody.setTimestamp(df.format(new Date()));
        tokenJasperRequestBody.setRet_type("json");
        tokenJasperRequestBody.setService_name("verify.login");
        tokenJasperRequestBody.setPlatform_type("CUNT");
        tokenJasperRequestBody.setToken("String");
        tokenJasperResponseBody.setResponse_data(tokenResponseData);
         tokenJasperResponseBody =jasperVerifyService.getJasperVerify(tokenJasperRequestBody);
        redisTemplate.opsForValue().set("token", tokenJasperResponseBody.getResponse_data().getToken(),120000, TimeUnit.MILLISECONDS);
    }

}
