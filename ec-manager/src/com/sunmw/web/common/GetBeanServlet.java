package com.sunmw.web.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class GetBeanServlet extends HttpServlet  {
	
	private static WebApplicationContext context;
	   
    public void init() throws ServletException
    {
        context=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    }
   
    public static Object getBean(String id)
    {
        Object bean = context.getBean(id);
       
        return bean;
    } 

}
