package com.uestc.getthecourse.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.uestc.getthecourse.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void methodInvokeParam(JoinPoint joinPoint) {
        logger.info("----Before method {} invoke ,param:{}----", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "pointcut()", returning = "resultVal")
    public void methodInvokeResult(JoinPoint joinpoint, Object resultVal) {
        logger.info("----After method {} invoke,result:{}----", joinpoint.getSignature().toShortString(), resultVal);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "exception")
    public void methodInvokeException(JoinPoint joinpoint, Exception exception) {
        logger.info("----method {} invoke exception:{}----", joinpoint.getSignature().toShortString(), exception.getMessage());
    }
}
