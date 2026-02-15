package com.example.L10_Annotation_RestFul_AOP_demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class SimpleAspect {

    private Logger LOGGER = LoggerFactory.getLogger(SimpleAspect.class);


    @Before("execution(* com.example.L10_Annotation_RestFul_AOP_demo.ProductService.getByID(..))")
    public void beforeMethod(){
        LOGGER.info("Executing before actual method");
    }


    @Around("@annotation(com.example.L10_Annotation_RestFul_AOP_demo.aop.LogExecutionTime)")
    public Object recordExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("Executing {} time: {} ", proceedingJoinPoint.getTarget(), end-start);
        return result;
    }

}
