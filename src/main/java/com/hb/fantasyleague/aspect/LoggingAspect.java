package com.hb.fantasyleague.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Before("@annotation(com.hb.fantasyleague.aspect.LoggingMethod)")
	public void logMethodName(JoinPoint joinPoint) {
	    String method = joinPoint.getSignature().getName();
	    String params = Arrays.toString(joinPoint.getArgs());
	    System.out.println("Method [" + method + "] gets called with parameters " + params);
	  }
}
