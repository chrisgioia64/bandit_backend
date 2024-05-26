package org.example.strategy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ExperimentRunnerAspect {

    @Pointcut("execution(* org.example.strategy.ExperimentRunner.*(..))")
    public void loggingPointCut() {
    }

    @Around("loggingPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime);
        log.info("Method " + joinPoint.getSignature() + " , elapased time : " + elapsedTime);
        return obj;
    }

}
