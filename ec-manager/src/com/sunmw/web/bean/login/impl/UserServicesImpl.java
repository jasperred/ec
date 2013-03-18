package com.sunmw.web.bean.login.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.login.UserServices;
import com.sunmw.web.entity.Company;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class UserServicesImpl extends HibernateDaoSupport implements
		UserServices {
	
	public Map searchUser(Map param, int currentPage, int pageRow)
	{
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		//无用户公司不能查询
		if(ul.getCompanyId()==null)
		{
			return result;
		}
		StringBuffer hql = new StringBuffer();
		hql.append("from UserLogin u,Company c where u.CompanyId = c.id ");
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
			hql.append(" and u.CompanyId = "+ul.getCompanyId());
//		String companyIds = getCompanyByUser(ul);
//		if (companyIds != null)
//			hql.append(" and u.CompanyId in (" + companyIds + ")");
		StringBuffer con = new StringBuffer();
		List conList = new ArrayList();
		if(param.get("UserId")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.id = :Condition"+conList.size());
			conList.add(param.get("UserId"));
		}
		if(param.get("UserName")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.UserName like :Condition"+conList.size());
			conList.add("%"+param.get("UserName")+"%");
		}
		if(param.get("UserNo")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.UserNo = :Condition"+conList.size());
			conList.add(param.get("UserNo"));
		}
		if(param.get("Email")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.Email like :Condition"+conList.size());
			conList.add("%"+param.get("Email")+"%");
		}
		if(param.get("Enabled")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.Enabled = :Condition"+conList.size());
			conList.add(param.get("Enabled"));
		}
		if(param.get("CompanyId")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.CompanyId = :Condition"+conList.size());
			conList.add(param.get("CompanyId"));
		}
		if(param.get("CompanyName")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" c.CompanyName like :Condition"+conList.size());
			conList.add("%"+param.get("CompanyName")+"%");
		}
		if(param.get("TecNo")!=null)
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" u.TecNo = :Condition"+conList.size());
			conList.add(param.get("TecNo"));
		}
		if(con.length()>0)
			hql.append(" and "+con.toString());
		Session session = this.getSession();
		Query q1 = session.createQuery("select count(*) "+hql.toString());
		for(int i=0;i<conList.size();i++)
		{
			q1.setParameter("Condition"+i, conList.get(i));
		}
		List<Long> countList = q1.list();
		int count = countList.get(0).intValue();
		hql.append(" order by u.id");
		Query q = session.createQuery(hql.toString());
		for(int i=0;i<conList.size();i++)
		{
			q.setParameter("Condition"+i, conList.get(i));
		}
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		List<Object[]> l = q.list();
		List<Map> r = new ArrayList();
		for(Object[] obj:l)
		{
			UserLogin u = (UserLogin)obj[0];
			Company c = (Company) obj[1];
			Map m = new HashMap();
			m.put("UserId", u.getId());
			m.put("UserNo", u.getUserNo());
			m.put("Email", u.getEmail());
			m.put("UserName", u.getUserName());
			m.put("Enabled", u.getEnabled()==null?"Y":u.getEnabled());
			m.put("EnabledName", u.getEnabled()==null||u.getEnabled().trim().equals("Y")?"有效":"失效");
			m.put("CompanyId", c.getId());
			m.put("CompanyName", c.getCompanyName());
			m.put("CompanyStatus", c.getStatus());
			r.add(m);
		}
		result.put("RESULT", r);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}
	
	private String getCompanyByUser(UserLogin ul) {
		List<Company> cl = this.getHibernateTemplate().find(
				"from Company where Status = 'NORMAL' and id = ?",
				ul.getCompanyId());
		if (WebUtil.isNullForList(cl))
			return "0";
		Company c = cl.get(0);
		if (WebUtil.isNull(c.getCategory()))
			return "0";
		if (c.getCategory().equals("SHOP"))
			return c.getId().toString();
		else {
			List<String> areaList = this
					.getHibernateTemplate()
					.find(
							"select sga.id.AreaCd from UserLoginSecurityGroup ulsg,SecurityGroupArea sga where ulsg.id.GroupId = sga.id.GroupId and ulsg.id.UserId = ?",
							ul.getId());
			if (WebUtil.isNullForList(areaList))
				return null;
			StringBuffer areaCds = new StringBuffer();
			for (String area : areaList) {
				if (areaCds.length() > 0)
					areaCds.append(",");
				areaCds.append("'" + area + "'");
			}
			// from Company where ProvinceCd in (select apr.ProvinceCd from
			// UserLoginSecurityGroup ulsg,SecurityGroupArea sga,AreaProvinceRef
			// apr where ulsg.id.GroupId = sga.id.GroupId and sga.id.AreaCd =
			// apr.AreaCd and ulsg.UserId = ?)
			List<Integer> companyList = this
					.getHibernateTemplate()
					.find("select c.id from Company c,AreaProvinceRef apr where c.ProvinceCd = apr.ProvinceCd and apr.AreaCd in ("+areaCds.toString()+")");
			if (WebUtil.isNullForList(companyList))
				return "0";
			StringBuffer companyIds = new StringBuffer();
			for (Integer companyId : companyList) {
				if (companyIds.length() > 0)
					companyIds.append(",");
				companyIds.append(companyId);
			}
			return companyIds.toString();
		}
	}

	/**
	 * 新建用户
	 * 
	 * @param userLogin
	 * @return
	 */
	public Map<String, String> createUserLogin(Map<String, Object> userLogin) {
		Map<String, String> result = new HashMap<String, String>();
		UserLogin usr = (UserLogin) userLogin.get("UserLogin");
		String userNo = (String)userLogin.get("UserNo");
		if (userNo != null && userNo.trim().length() > 0) {
			List l = this.getHibernateTemplate().find(
					"from UserLogin where UserNo = ?",userNo);
			if (l != null && l.size() > 0 && l.get(0) != null) {
				result.put("FLAG", "ERROR");
				result.put("MESSAGE", "该用户已存在");
				return result;
			}

		}
		String email = (String)userLogin.get("Email");
		if (email != null && email.trim().length() > 0) {
			List l = this.getHibernateTemplate().find(
					"from UserLogin where Email = ?",email);
			if (l != null && l.size() > 0 && l.get(0) != null) {
				result.put("FLAG", "ERROR");
				result.put("MESSAGE", "邮件已被注册");
				return result;
			}

		}
		UserLogin ul = new UserLogin();
		Date date = new Date();
		ul.setUserNo(userNo);
		ul.setCtime(date);
		ul.setEmail(email);
		ul.setUserName((String)userLogin.get("UserName"));
		ul.setEnabled((String)userLogin.get("Enabled"));
		//无公司传入则使用创建者所在公司
		if(WebUtil.isNull(userLogin.get("CompanyId")))
			ul.setCompanyId(usr.getCompanyId());
		else
			ul.setCompanyId((Integer)userLogin.get("CompanyId"));
		ul.setTecNo((String)userLogin.get("TecNo"));
		//此处都是普通用户
		ul.setUserType("NORMAL");
		String hashType = WebConfigProperties
				.getProperties("web.password.hashtype");
		if (hashType == null || hashType.trim().length() == 0)
			hashType = "SHA";
		ul.setCurrentPassword(WebUtil.getDigestHash((String)userLogin.get("password"),
				hashType));
		this.getHibernateTemplate().save(ul);
		result.put("UserId", ul.getId().toString());
		result.put("FLAG", "SUCCESS");
		result.put("MESSAGE", "用户注册成功");
		return result;
	}

	/**
	 * 用户信息修改
	 * 
	 * @param userLogin
	 * @return
	 */
	public Map<String, String> updateUserLogin(Map<String, Object> userLogin) {
		Map<String, String> result = new HashMap<String, String>();
		int userId = (Integer) userLogin.get("UserId");
		List<UserLogin> l = this.getHibernateTemplate().find(
				"from UserLogin where id = ?",userId);
		if (l == null || l.size() == 0 || l.get(0) == null) {
			result.put("FLAG", "ERROR");
			result.put("MESSAGE", "用户没找到");
		}
		UserLogin ul = l.get(0);
		if (WebUtil.isNotNull(userLogin.get("Enabled")))
			ul.setEnabled((String)userLogin.get("Enabled"));
		if (WebUtil.isNotNull(userLogin.get("CompanyId")))
			ul.setCompanyId((Integer)userLogin.get("CompanyId"));
		//if (WebUtil.isNotNull(userLogin.get("Email")))
			ul.setEmail((String)userLogin.get("Email"));
		if (WebUtil.isNotNull(userLogin.get("UserName")))
			ul.setUserName((String)userLogin.get("UserName"));
		//if (WebUtil.isNotNull(userLogin.get("TecNo")))
			ul.setTecNo((String)userLogin.get("TecNo"));
		this.getHibernateTemplate().update(ul);
		result.put("UserId", ul.getId().toString());
		result.put("FLAG", "SUCCESS");
		result.put("MESSAGE", "用户修改成功");
		return result;
	}
	
	//用户信息
	public Map getUserInfo(int userId)
	{
		List<Object[]> l = this.getHibernateTemplate().find("from UserLogin u,Company c where u.CompanyId = c.id and u.id = ?",userId);
		if(WebUtil.isNullForList(l))
			return null;
		Map result = new HashMap();
		Object[] obj = l.get(0);
		UserLogin u = (UserLogin) obj[0];
		Company c = (Company) obj[1];
		result.put("UserName", u.getUserName());
		result.put("Email", u.getEmail());
		result.put("UserNo", u.getUserNo());
		result.put("Enabled", u.getEnabled());
		result.put("CompanyId", u.getCompanyId());
		result.put("CompanyNo", c.getCompanyNo());
		result.put("CompanyName", c.getCompanyName());
		result.put("CompanyStatus", c.getStatus());
		result.put("TecNo", u.getTecNo());
		result.put("UserType", u.getUserType());
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, String> modifyPassowd(Map param) {

		Map result = new HashMap();
		int userId = (Integer) param.get("UserId");
		String password = (String) param.get("Password");
		String newPassword = (String) param.get("NewPassword");
		String email = (String) param.get("Email");
		List userList = this.getHibernateTemplate().find(
				"from UserLogin where id = ?",userId);
		String hashType = WebConfigProperties
				.getProperties("web.password.hashtype");
		if (hashType == null || hashType.trim().length() == 0)
			hashType = "SHA";
		password = WebUtil.getDigestHash(password, hashType);
		newPassword = WebUtil.getDigestHash(newPassword, hashType);
		if (userList.size() > 0) {
			UserLogin login = (UserLogin) userList.get(0);
			// 判断密码是否正确
			if (!login.getCurrentPassword().equals(password.trim())) {
				result.put("FLAG", "ERROR");
				result.put("MESSAGE", "密码不正确!");
				return result;
			}
			// 修改密码
			login.setCurrentPassword(newPassword);
			login.setPasswordModifyTime(new Date());
			this.getHibernateTemplate().update(login);
			result.put("FLAG", "SUCCESS");
			result.put("MESSAGE", "密码修改成功!");
			return result;

		}
		result.put("FLAG", "ERROR");
		result.put("MESSAGE", "用户不存在!");
		return result;

	}
	
	/**
	 * 重设密码
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, String> resetPassowd(Map param) {

		Map result = new HashMap();
		int userId = (Integer) param.get("UserId");
		String password = (String) param.get("Password");
		List userList = this.getHibernateTemplate().find(
				"from UserLogin where id = ?",userId);
		String hashType = WebConfigProperties
				.getProperties("web.password.hashtype");
		if (hashType == null || hashType.trim().length() == 0)
			hashType = "SHA";
		password = WebUtil.getDigestHash(password, hashType);
		if (userList.size() > 0) {
			UserLogin login = (UserLogin) userList.get(0);
			// 修改密码
			login.setCurrentPassword(password);
			//重设密码后，密码修改时间设为空，要求登录必须修改密码
			login.setPasswordModifyTime(null);
			this.getHibernateTemplate().update(login);
			result.put("FLAG", "SUCCESS");
			result.put("MESSAGE", "密码修改成功!");
			return result;

		}
		result.put("FLAG", "ERROR");
		result.put("MESSAGE", "用户不存在!");
		return result;

	}
	
	/**
	 * 修改密码
	 * 按用户名
	 * @param param
	 * @return
	 */
	public Map<String, String> modifyPassowdByUserNo(Map param) {

		Map result = new HashMap();
		String companyNo = (String) param.get("CompanyNo");
		List<Company> cList = this.getHibernateTemplate().find("from Company where Status = 'NORMAL' and CompanyNo = ?",companyNo);
		if(WebUtil.isNullForList(cList))
		{
			result.put("LOGIN", "ERROR");
			result.put("LOGIN_MASSAGE", "公司未登记");
			return result;
		}
		Company c = cList.get(0);
		int companyId = c.getId();
		String userNo = (String) param.get("UserNo");
		String password = (String) param.get("Password");
		String newPassword = (String) param.get("NewPassword");
		String email = (String) param.get("Email");
		List userList = this.getHibernateTemplate().find(
				"from UserLogin where UserNo = ? and CompanyId = "+companyId,userNo);
		String hashType = WebConfigProperties
				.getProperties("web.password.hashtype");
		if (hashType == null || hashType.trim().length() == 0)
			hashType = "SHA";
		password = WebUtil.getDigestHash(password, hashType);
		newPassword = WebUtil.getDigestHash(newPassword, hashType);
		if (userList.size() > 0) {
			UserLogin login = (UserLogin) userList.get(0);
			// 判断密码是否正确
			if (!login.getCurrentPassword().equals(password.trim())) {
				result.put("FLAG", "ERROR");
				result.put("MESSAGE", "密码不正确!");
				return result;
			}
			// 修改密码
			login.setCurrentPassword(newPassword);
			login.setPasswordModifyTime(new Date());
			this.getHibernateTemplate().update(login);
			result.put("FLAG", "SUCCESS");
			result.put("MESSAGE", "密码修改成功!");
			return result;

		}
		result.put("FLAG", "ERROR");
		result.put("MESSAGE", "用户不存在!");
		return result;

	}

	public List navigateListByUser(Map param)
	{
		//MENU_7--维修受理
		//MENU_9--维修申请审核
		//MENU_19--SCM发货管理
		//MENU_39--CSPC发货管理
		//MENU_22--回访
		//MENU_11--费用结算
		UserLogin ul = (UserLogin) param.get("UserLogin");
		List<String> usl = this.getHibernateTemplate().find("select sgp.id.PermissionId from UserLoginSecurityGroup usg,SecurityGroupPermission sgp where usg.id.GroupId = sgp.id.GroupId and usg.id.UserId = ? and sgp.id.PermissionId in ('MENU_7','MENU_9','MENU_11','MENU_19','MENU_22','MENU_39') order by sgp.id.PermissionId",ul.getId());
		if(WebUtil.isNullForList(usl))
			return null;
		List result = new ArrayList();
		for(String p:usl)
		{
			Map m = new HashMap();
			//特约店
			if(p.equals("MENU_7"))
			{
				//修理中
				//已申请
				//未申请
				List<Long> noCompleteList = this.getHibernateTemplate().find("select count(*) from ServiceHead where CompanyId = ?",ul.getCompanyId());
				//List<Long> completeList = this.getHibernateTemplate().find("");
			}
			//RCN
			if(p.equals("MENU_9"))
			{
				
			}
			//费用结算
			if(p.equals("MENU_11"))
			{
				
			}
			//SCM
			if(p.equals("MENU_19"))
			{
				
			}
			//CC
			if(p.equals("MENU_22"))
			{
				
			}
			//CSPC
			if(p.equals("MENU_39"))
			{
				
			}
			result.add(m);
		}
		return result;
	}
}
