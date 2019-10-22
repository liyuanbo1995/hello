package com.xinba.isp.config;

import com.xinba.isp.util.BusinessException;
import feign.Request;
import feign.Response;
import feign.Retryer;
import feign.Util;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FeignClientConfiguration {

    @Value("${jasper.key}")
    private String consumerKey;

    @Value("${jasper.password}")
    private String consumerPassword;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(consumerKey, consumerPassword);
    }

    public static int connectTimeOutMillis = 150000;//超时时间
    public static int readTimeOutMillis = 150000;
    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean("FeignRetryer")
    @ConditionalOnMissingBean
    public Retryer feignRetryer() {
        return new Retryer.Default(TimeUnit.MINUTES.toMillis(5), TimeUnit.MINUTES.toMillis(10), 2);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    /**
     * 自定义错误解码器
     */
    public class UserErrorDecoder implements ErrorDecoder {
        private Logger logger = LoggerFactory.getLogger(getClass());
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                String json = Util.toString(response.body().asReader());
                if (StringUtils.isBlank(json)) {
                    exception = new BusinessException(response.status(),"response.body转换Json为空",json);
                }
                exception = new BusinessException(response.status(),json);
                // 业务异常包装成 HystrixBadRequestException，不进入熔断逻辑

            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }

//    public class UserErrorDecoder implements ErrorDecoder {
//        @Override
//        public Exception decode(String methodKey, Response response) {
//            return new MyFeignException(methodKey,response);
//        }
//    }
}
