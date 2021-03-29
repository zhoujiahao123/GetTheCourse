package com.uestc.getthecourse.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class TimeAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.uestc.getthecourse.controller.*.*(..))")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object methodInvokeExpireTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("----method {} invoke", joinPoint.getSignature().toShortString());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        logger.info("----method {} invoke , expire time:{}ms----", joinPoint.getSignature().toShortString(), stopWatch.getTotalTimeMillis());
        return result;

    }
}
