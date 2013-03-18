package com.sunmw.web.bean.login.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.login.LoginServices;
import com.sunmw.web.entity.Company;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class SimpleLoginServices extends HibernateDaoSupport implements LoginServices {
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public Map loginByUserName(String userNo,String password,String companyNo)
	{
		Map result = new HashMap();
		List<Company> cList = this.getHibernateTemplate().find("from Company where Status = 'NORMAL' and CompanyNo = ?",companyNo);
		if(WebUtil.isNullForList(cList))
		{
			result.put("LOGIN", "ERROR");
			result.put("LOGIN_MASSAGE", "公司未登记");
			return result;
		}
		Company c = cList.get(0);
		int companyId = c.getId();
		List<UserLogin> userList = this.getHibernateTemplate().find("from UserLogin where CompanyId = "+companyId+" and UserNo = ?",userNo);
		if(userList==null||userList.size()==0||userList.get(0)==null)
		{
			result.put("LOGIN", "ERROR");
			result.put("LOGIN_MASSAGE", "用户不存在");
			return result;
		}
		UserLogin ul = userList.get(0);
		if(ul.getEnabled()!=null&&"N".equals(ul.getEnabled()))
		{
			result.put("LOGIN", "ERROR");
			result.put("LOGIN_MASSAGE", "用户已停用");
			return result;
		}
		//调用之前传入
//		String hashType = WebConfigProperties.getProperties("web.password.hashtype");
//		if(hashType==null||hashType.trim().length()==0)
//			hashType = "SHA";
//		String p = WebUtil.getDigestHash(password, hashType);
		if(!password.equals(ul.getCurrentPassword()))
		{
			result.put("LOGIN", "ERROR");
			result.put("LOGIN_MASSAGE", "密码不正确");
			//ul.setSuccessiveFailedLogins(ul.getSuccessiveFailedLogins()==null?0:ul.getSuccessiveFailedLogins()+1);
		}
		else
		{
			result.put("LOGIN", "SUCCESS");
			result.put("LOGIN_MASSAGE", "登录成功");
			result.put("LOGIN_INFO", ul);
			result.put("COMPANY_INFO", c);
			//ul.setSuccessiveFailedLogins(0);
		}
		this.getHibernateTemplate().update(ul);
		
		return result;
	}
	
	

}
