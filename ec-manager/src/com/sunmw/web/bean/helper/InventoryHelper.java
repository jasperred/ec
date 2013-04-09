package com.sunmw.web.bean.helper;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

@Aspect
public class InventoryHelper implements MethodBeforeAdvice,AfterReturningAdvice {

	@Pointcut(value="execution(* *.search*(..,java.util.Map,int,int,..))",argNames="param, currentPage, pageRow")   
    public void invpoint(Map param, int currentPage, int pageRow){
		System.out.println("fffff");
	}  
	
	@Before(value="invpoint()&&args(param, currentPage, pageRow)",argNames="param, currentPage, pageRow")   
    public void beforeSleep(Map param, int currentPage, int pageRow){   
        System.out.println("睡觉前要脱衣服!"+currentPage);   
    }

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		System.out.println("arg1:"+arg1);
		System.out.println("arg2:"+arg2);
		
	}

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		// TODO Auto-generated method stub
		
	} 
	
//	@AfterReturning(pointcut = "invpoint()", returning = "result")   
//    public void afterSleep(Map result){   
//        System.out.println("睡醒了要穿衣服！"+result);   
//    }  
}
