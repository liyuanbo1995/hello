package com.xinba.active.api;

import com.xinba.active.util.ActivityException;
import com.xinba.active.util.ExceptionUtils;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @createBy XiaoWu
 * @date 2019/12/2 9:37
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 业务异常处理
    @ExceptionHandler(value = ActivityException.class)
    public Wrapper sellExceptionHandler(ActivityException e){
        // 业务异常，在抛出异常处及时记录log.info
        e.printStackTrace();
        return WrapMapper.wrap(e);
    }

//	 //在上线时加上，拦截各类异常情况
//	@ExceptionHandler(value = Exception.class)
//	public Wrapper allExceptionHandler(Exception e){
//		// 用log打印出来，就不需要e.printStackTrace
//	    // 抛异常处可以不用记录
//	    log.error(ExceptionUtils.getMessage(e));
//		return WrapMapper.error();
//	}
}
