package com.sunmw.web.bean.helper;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;

public class InventoryAspect {
	// 方法执行结果
	public void doAfterReturning(JoinPoint jp, Map order, Map result) {
		// 判断订单是否执行成功
		String flag = (String) result.get("Flag");
		// 根据订单号判断是否已经扣除库存
		String orderNo = (String) result.get("OrderNo");
		if (WebUtil.isNull(orderNo))
			return;
		InventoryServices inventoryServices = (InventoryServices) GetBeanServlet
				.getBean("inventoryServices");
		inventoryServices.deductOrderInventory(orderNo);
	}

	// 方法环绕处理
	public Object doAround(ProceedingJoinPoint pjp, Map order) throws Throwable {

		Object retVal = pjp.proceed();
		return retVal;
	}

	// 方法调用之前
	public void doBefore(JoinPoint jp, Map order) {

	}

	// 方法异常处理
	public void doThrowing(JoinPoint jp, Throwable ex, Map order) {
		System.out.println("exception:"+order.get("Tid"));

	}
}
