package ru.nvkz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect  {

    @Pointcut("execution(public * ru.nvkz.service.*.*(..))")
    private void publicMethodsFromLoggingPackage() {
    }

    @Before(value = "publicMethodsFromLoggingPackage()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info(">> {}() - {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "publicMethodsFromLoggingPackage()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("<< {}() - {}", methodName, result);
    }

    @AfterThrowing(pointcut = "publicMethodsFromLoggingPackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("<< {}() - {}", methodName, exception.getMessage());
    }

}
