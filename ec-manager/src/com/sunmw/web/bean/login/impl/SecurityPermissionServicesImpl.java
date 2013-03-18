package com.sunmw.web.bean.login.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.login.SecurityPermissionServices;
import com.sunmw.web.entity.ModuleConfig;
import com.sunmw.web.entity.SecurityGroup;
import com.sunmw.web.entity.SecurityGroupPermission;
import com.sunmw.web.entity.SecurityGroupPermissionPK;
import com.sunmw.web.entity.SecurityPermission;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.entity.UserLoginSecurityGroup;
import com.sunmw.web.entity.UserLoginSecurityGroupPK;
import com.sunmw.web.util.WebUtil;

public class SecurityPermissionServicesImpl extends HibernateDaoSupport
		implements SecurityPermissionServices {

	/**
	 * 根据用户ID得到菜单列表 支持多级菜单
	 * 
	 * @param userLoginId
	 * @return
	 */
	public List getLoginMenu(Map param) {
		UserLogin ul = (UserLogin) param.get("UserLogin");
		//判断用户是否有效
		List<UserLogin> userList = this.getHibernateTemplate().find("from UserLogin where id = ? and Enabled = 'Y'",ul.getId());
		if(WebUtil.isNullForList(userList))
			return null;
		//UserLogin ul = userList.get(0);
		//得到用户的安全组,2012-08-17 用户可得到SHARE安全组和本公司的安全组
		List<Object[]> securityGroupList = this.getHibernateTemplate().find("from UserLoginSecurityGroup ulsg,SecurityGroup sg where ulsg.id.GroupId = sg.id and (sg.GroupType = 'SHARE' or sg.CompanyId = "+ul.getCompanyId()+") and ulsg.id.UserId = ?",ul.getId());
		if(WebUtil.isNullForList(securityGroupList))
			return null;
		StringBuffer groupIds = new StringBuffer();
		for(Object[] obj:securityGroupList)
		{
			SecurityGroup sg = (SecurityGroup) obj[1];
			if(groupIds.length()>0)
				groupIds.append(",");
			groupIds.append("'"+sg.getId()+"'");
		}
		//得到安全组的权限
		List<Object[]> permissionList = this.getHibernateTemplate().find("from SecurityGroupPermission sgp,SecurityPermission sp where sgp.id.PermissionId = sp.id and sgp.id.GroupId in ("+groupIds.toString()+")");
		if(WebUtil.isNullForList(permissionList))
			return null;
		StringBuffer permissionIds = new StringBuffer();
		for(Object[] obj:permissionList)
		{
			SecurityPermission sp = (SecurityPermission) obj[1];
			if(permissionIds.length()>0)
				permissionIds.append(",");
			permissionIds.append("'"+sp.getId()+"'");
		}
		//对应权限的菜单
		String hql = "from ModuleConfig where ShowMenu = 'Y' and PermissionId in ("+permissionIds.toString()+") order by ParentModuleId,Sequence";
		List<ModuleConfig> l = this.getHibernateTemplate().find(hql);
		if (l == null || l.size() == 0)
			return null;
		List<Map> menuList = getModuleList(l, 0);
		return menuList;
	}

	// 迭代方式得到菜单
	private List getModuleList(List<ModuleConfig> l, int parentId) {
		List<Map> ml = new ArrayList();
		for (ModuleConfig mc : l) {
			if (mc == null)
				continue;
			if (parentId == 0) {
				if (mc.getParentModuleId() != null
						&& mc.getParentModuleId() > 0)
					continue;
			} else {
				if (mc.getParentModuleId() == null
						|| mc.getParentModuleId() != parentId)
					continue;
			}
			Map m = new HashMap();
			m.put("info", mc);
			List subList = getModuleList(l, mc.getId());
			m.put("subList", subList);
			ml.add(m);
		}
		Collections.sort(ml, new Comparator() {

			public int compare(Object o1, Object o2) {
				ModuleConfig m1 = (ModuleConfig) ((Map) o1).get("info");
				ModuleConfig m2 = (ModuleConfig) ((Map) o2).get("info");
				if (m1.getSequence() > m2.getSequence())
					return 1;
				return 0;
			}
		});
		return ml;
	}

	/**
	 * 检查用户权限
	 * 
	 * @param userLoginId
	 * @param permissionId
	 * @return
	 */
	public String checkPermission(int userId, String permissionId) {
		String hql = "from SecurityGroupPermission sgp,UserLoginSecurityGroup ulsg where sgp.id.GroupId = ulsg.id.GroupId and sgp.id.PermissionId = ? and ulsg.id.UserId = ?";
		List l = this.getHibernateTemplate().find(hql,
				new Object[] { permissionId, userId });
		if (l != null && l.size() > 0 && l.get(0) != null)
			return "SUCCESS";
		else
			return "ERROR";
	}

	public Map searchSecurityGroup(Map param, int currentPage, int pageRow) {
		UserLogin ul = (UserLogin) param.get("UserLogin");
		StringBuffer hql = new StringBuffer();
		hql.append("from SecurityGroup");
		StringBuffer con = new StringBuffer();
		List conList = new ArrayList();
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
		{
			if (con.length() > 0)
				con.append(" and ");
			con.append(" CompanyId = :Condition" + conList.size());
			conList.add(ul.getCompanyId());
		}
		if (!WebUtil.isNull(param.get("groupId"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" id = :Condition" + conList.size());
			conList.add(param.get("groupId"));
		}
		if (!WebUtil.isNull(param.get("groupName"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Description like :Condition" + conList.size());
			conList.add("%" + param.get("groupName") + "%");
		}
		if (con.length() > 0)
			hql.append(" where " + con.toString());

		Session session = this.getSession();
		Map result = new HashMap();
		try {
			Query q1 = session.createQuery("select count(*) " + hql.toString());
			// 查询条件
			for (int i = 0; i < conList.size(); i++) {
				q1.setParameter("Condition" + i, conList.get(i));
			}
			List<Long> countList = q1.list();
			int count = countList.get(0).intValue();
			Query q = session.createQuery(hql.toString());
			// 查询条件
			for (int i = 0; i < conList.size(); i++) {
				q.setParameter("Condition" + i, conList.get(i));
			}
			if (pageRow > 0) {
				q.setFirstResult((currentPage - 1) * pageRow);
				q.setMaxResults(pageRow);
			}
			List<SecurityGroup> l = q.list();
			List<Map> r = new ArrayList();
			for (SecurityGroup s : l) {
				Map m = new HashMap();
				m.put("GroupId", s.getId());
				m.put("GroupName", s.getDescription());
				m.put("CompanyId", s.getCompanyId());
				r.add(m);
			}
			result.put("RESULT", r);
			result.put("COUNT_ROW", count);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			result.put("Flag", "ERROR");
			result.put("Message", e.getMessage());
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return result;
	}

	public List<SecurityGroup> securityGroupList(UserLogin ul) {
		List<SecurityGroup> l = this.getHibernateTemplate().find("from SecurityGroup where GroupType = 'SHARE' or CompanyId = "+ul.getCompanyId());
		return l;
	}
	
	public List<SecurityPermission> securityPermissionListByModule(Map param)
	{
		UserLogin ul = (UserLogin) param.get("UserLogin");
		List<SecurityPermission> spList = null;
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
			spList = this.getHibernateTemplate().find("from SecurityPermission where PermissionType=? order by ParentPermissionId,DispIndex","MODULE");
		else
			spList = this.getHibernateTemplate().find("from SecurityPermission order by ParentPermissionId,DispIndex");
		return spList;
	}

	public List<SecurityPermission> securityPermissionList() {
		List<Object[]> spList = this
				.getHibernateTemplate()
				.find(
						"from SecurityPermission sp,ModuleConfig mc where sp.id = mc.PermissionId order by mc.ParentModuleId");
		Map m = new HashMap();
		for (Object[] obj : spList) {
			SecurityPermission sp = (SecurityPermission) obj[0];
			ModuleConfig mc = (ModuleConfig) obj[1];
			if (mc.getParentModuleId() == null || mc.getParentModuleId() == 0) {
				if (mc.getShowMenu() == null || mc.getShowMenu().equals("N"))
					continue;
				Map mm = new HashMap();
				mm.put("Index", mc.getSequence());
				mm.put("PermissionName", mc.getModuleName());
				mm.put("PermissionId", sp.getId());
				m.put(mc.getId(), mm);
			} else {
				Map mm = (Map) m.get(mc.getParentModuleId());
				if (mm == null)
					continue;
				List subList = (List) mm.get("SubList");
				if (subList == null)
					subList = new ArrayList();
				Map subm = new HashMap();
				subm.put("Index", mc.getSequence());
				subm.put("PermissionName", mc.getModuleName());
				subm.put("PermissionId", sp.getId());
				subList.add(subm);
				mm.put("SubList", subList);
				m.put(mc.getParentModuleId(), mm);
			}
		}
		List l = new ArrayList();
		Iterator iter = m.keySet().iterator();
		while (iter.hasNext()) {
			Map mm = (Map) m.get(iter.next());
			List sl = (List) mm.get("SubList");
			if (WebUtil.isNullForList(sl))
				l.add(mm);
			else {
				Collections.sort(sl, new Comparator<Map>() {

					public int compare(Map o1, Map o2) {
						if ((Integer) o1.get("Index") > (Integer) o2
								.get("Index"))
							return 1;
						return 0;
					}
				});
				mm.put("SubList", sl);
				l.add(mm);
			}
		}
		Collections.sort(l, new Comparator<Map>() {

			public int compare(Map o1, Map o2) {
				if ((Integer) o1.get("Index") > (Integer) o2.get("Index"))
					return 1;
				return 0;
			}
		});
		return l;
	}

	public List<Map> securityGroupPermissionList(String groupId) {
		List<Object[]> l = this
				.getHibernateTemplate()
				.find(
						"from SecurityGroupPermission sgp,SecurityPermission sp where sgp.id.PermissionId = sp.id and sgp.id.GroupId = ?",
						groupId);
		if (l == null)
			return null;
		List<Map> r = new ArrayList();
		for (Object[] o : l) {
			SecurityGroupPermission sgp = (SecurityGroupPermission) o[0];
			SecurityPermission sp = (SecurityPermission) o[1];
			Map m = new HashMap();
			m.put("groupId", sgp.getId().getGroupId());
			m.put("permissionId", sgp.getId().getPermissionId());
			m.put("permissionName", sp.getDescription());
			r.add(m);
		}
		return r;
	}

	public List<Map> userLoginSecurityGroupList(int userId) {
		List<Object[]> l = this
				.getHibernateTemplate()
				.find(
						"from UserLoginSecurityGroup ulsg,SecurityGroup sg where ulsg.id.GroupId = sg.id and ulsg.id.UserId = ?",
						userId);
		if (l == null)
			return null;
		List<Map> r = new ArrayList();
		for (Object[] o : l) {
			UserLoginSecurityGroup ulsg = (UserLoginSecurityGroup) o[0];
			SecurityGroup sg = (SecurityGroup) o[1];
			Map m = new HashMap();
			m.put("userId", ulsg.getId().getUserId());
			m.put("groupId", ulsg.getId().getGroupId());
			m.put("groupName", sg.getDescription());
			r.add(m);
		}
		return r;
	}

	public String saveUserSecurityGroup(int userId, String groupIds) {
		Session session = this.getSession();
		// String g = "'"+groupIds.replaceAll(",", "','")+"'";
		try {
			Query q = session
					.createQuery("from UserLoginSecurityGroup where id.UserId = :UserId");
			q.setParameter("UserId", userId);
			List<UserLoginSecurityGroup> l = q.list();
			Map m = new HashMap();
			if (l != null) {
				for (UserLoginSecurityGroup u : l) {
					m.put(u.getId().getGroupId(), u);
				}
			}
			String[] gs = groupIds.split(",");
			Date date = new Date();
			Transaction tx = session.beginTransaction();
			for (String s : gs) {
				s = WebUtil.filterSpecialCharacters(s);
				if (m.get(s) != null) {
					m.remove(s);
					continue;
				}
				UserLoginSecurityGroup ulsg = new UserLoginSecurityGroup();
				UserLoginSecurityGroupPK pk = new UserLoginSecurityGroupPK();
				pk.setFromDate(date);
				pk.setGroupId(s);
				pk.setUserId(userId);
				ulsg.setId(pk);
				ulsg.setCtime(date);
				session.save(ulsg);
			}
			if (m.size() > 0) {
				Iterator<String> iter = m.keySet().iterator();
				while (iter.hasNext()) {
					session.delete(m.get(iter.next()));
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return "success";
	}

	public String saveSecurityGroup(Map param) {
		UserLogin ul = (UserLogin) param.get("UserLogin");
		String groupId = (String) param.get("GroupId");
		String groupName = (String) param.get("GroupName");
		List<SecurityGroup> l = this.getHibernateTemplate().find(
				"from SecurityGroup where id = ?", groupId);
		SecurityGroup sg = null;
		if (l != null && l.size() > 0)
			sg = l.get(0);
		if (sg == null) {
			sg = new SecurityGroup();
			sg.setId(groupId);
			sg.setDescription(groupName);
			if(WebUtil.isNotNull(param.get("CompanyId")))
				sg.setCompanyId((Integer)param.get("CompanyId"));
			else
				sg.setCompanyId(ul.getCompanyId());
			sg.setGroupType("PRIVATE");
			sg.setCtime(new Date());
			this.getHibernateTemplate().save(sg);
		} else {
			sg.setDescription(groupName);
			sg.setMtime(new Date());
			if(WebUtil.isNotNull(param.get("CompanyId")))
				sg.setCompanyId((Integer)param.get("CompanyId"));
			this.getHibernateTemplate().update(sg);
		}

		return "success";
	}

	public String saveSecurityGroupPermission(String groupId,
			String permissionIds) {
		Session session = this.getSession();
		// String g = "'"+permissionIds.replaceAll(",", "','")+"'";
		try {
			Query q = session
					.createQuery("from SecurityGroupPermission where id.GroupId = :GroupId");
			q.setParameter("GroupId", groupId);
			List<SecurityGroupPermission> l = q.list();
			Map<String, SecurityGroupPermission> m = new HashMap();
			if (l != null) {
				for (SecurityGroupPermission s : l) {
					m.put(s.getId().getPermissionId(), s);
				}
			}
			String[] gs = permissionIds.split(",");
			Date date = new Date();
			Transaction tx = session.beginTransaction();
			for (String s : gs) {
				if (m.get(s) != null) {
					m.remove(s);
					continue;
				}
				SecurityGroupPermission sgp = new SecurityGroupPermission();
				SecurityGroupPermissionPK pk = new SecurityGroupPermissionPK();
				pk.setFromDate(date);
				pk.setGroupId(groupId);
				pk.setPermissionId(s);
				sgp.setId(pk);
				sgp.setCtime(date);
				session.save(sgp);
			}
			if (m.size() > 0) {
				Iterator<String> iter = m.keySet().iterator();
				while (iter.hasNext()) {
					session.delete(m.get(iter.next()));
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return "success";
	}

}
