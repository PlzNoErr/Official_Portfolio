package com.bodyfit.restapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Around("execution(* com.bodyfit.restapi.controller.*.*(..))")
    public Object logController(ProceedingJoinPoint pjp) throws Throwable{
        Object result = pjp.proceed();
        log.info(pjp.getSignature().toShortString());
        return result;
    }
}
