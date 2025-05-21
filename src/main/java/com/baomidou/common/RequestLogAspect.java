package com.baomidou.common;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;

@Aspect
@Component
public class RequestLogAspect {


    private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    @Around("@within(com.baomidou.common.LogRequest)")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        // 打印入参
        logger.info("接口入参: {}", args);

        // 打印当前方法名
        logger.info("当前方法名: {}", joinPoint.getSignature().getName());
        // 打印当前方法 注解的内容
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的 @ApiOperation 注解
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation != null) {
            RequestContextUtils.setApiOperation(annotation.value());
            logger.info("当前方法注解内容: {}", annotation.value());
        }
        // 继续执行原方法
        return joinPoint.proceed();
    }
}
