package com.xinba.isp.feign;

import com.xinba.isp.entity.form.JasperRequestBody;
import com.xinba.isp.entity.form.RequestData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RequestBodyTemplate {
    @Resource
    private GetTokenService getTokenService;
    @Resource
    private RedisTemplate redisTemplate;


    public JasperRequestBody setRequestBody(String service_name,RequestData requestData){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2=new SimpleDateFormat("yyyyMMddHHmmss");
        if(redisTemplate.opsForValue().get("token")==null){
            getTokenService.getToken();
        }
        JasperRequestBody RequestBody=new JasperRequestBody();
        RequestBody.setSerial_number(df2.format(new Date())+"simba"+((int)((Math.random()*9+1)*1000000)));
        RequestBody.setTimestamp(df.format(new Date()));
        RequestBody.setRet_type("json");
        RequestBody.setService_name(service_name);
        RequestBody.setPlatform_type("CUNT");
        RequestBody.setToken(redisTemplate.opsForValue().get("token").toString());
        RequestBody.setRequest_data(requestData);
        return RequestBody;
    }
}
