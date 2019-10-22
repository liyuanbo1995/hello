package com.xinba.isp.util;

import feign.Response;

public class MyFeignException extends RuntimeException {
    private final String methodKey;
    private Response response;


    public MyFeignException(String methodKey, Response response) {
        this.methodKey = methodKey;
        this.response = response;
    }


    public Response getResponse() {
        return response;
    }

    public String getMethodKey() {
        return methodKey;
    }
}
