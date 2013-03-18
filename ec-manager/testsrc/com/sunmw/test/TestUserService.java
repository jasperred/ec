package com.sunmw.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sunmw.web.bean.login.UserServices;
import com.sunmw.web.entity.UserLogin;

@RunWith(SpringJUnit4ClassRunner.class)  
//@ContextConfiguration 注解有以下两个常用的属性：  
//locations：可以通过该属性手工指定 Spring 配置文件所在的位置,可以指定一个或多个 Spring 配置文件  
//inheritLocations：是否要继承父测试类的 Spring 配置文件，默认为 true  
@ContextConfiguration(locations={"classpath:com/sunmw/web/cfg/spring/applicationContext.xml"}) 

public class TestUserService {

	@Resource 
	private UserServices userServices;
	
	@Test
	public void testCreateUserLogin()
	{
		UserLogin ul = new UserLogin();
		ul.setCompanyId(1);
		ul.setUserNo("test");
		Map param = new HashMap();
		param.put("UserLogin", ul);
		Map m = userServices.searchUser(param, 1, 10);
		Assert.assertNotNull(m.get("RESULT"));
	}
}
