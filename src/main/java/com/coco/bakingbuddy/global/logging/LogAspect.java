package com.coco.bakingbuddy.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.coco.bakingbuddy..*(..))")
    public void all() {
    }

    @Pointcut("execution(* com.coco.bakingbuddy..*Controller.*(..))")
    public void controller() {
    }

    @Pointcut("execution(* com.coco.bakingbuddy..*Service.*(..))")
    public void service() {
    }

    @Around("all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("Execution time for {}: {} ms", joinPoint.getSignature(), timeMs);
        }
    }

    @Before("controller() || service()")
    public void beforeLogic(JoinPoint joinPoint) throws Throwable {
        log.info("Entering method: {}", getMethodName(joinPoint));
        logArgs(joinPoint);
    }

    @AfterReturning(pointcut = "controller() || service()", returning = "result")
    public void afterReturningLogic(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("Exiting method: {}", getMethodName(joinPoint));
        logArgs(joinPoint);
        log.info("Method result: {}", result);
    }

    @AfterThrowing(pointcut = "controller() || service()", throwing = "ex")
    public void afterThrowingLogic(JoinPoint joinPoint, Throwable ex) throws Throwable {
        log.error("Exception in method {}: {}", getMethodName(joinPoint), ex.getMessage());
    }

    private String getMethodName(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getName();
    }

    private void logArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                log.info("Argument type: {}, value: {}", arg.getClass().getSimpleName(), arg);
            }
        }
    }
}
