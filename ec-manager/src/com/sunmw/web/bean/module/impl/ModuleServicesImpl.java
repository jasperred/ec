package com.sunmw.web.bean.module.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.module.ModuleServices;
import com.sunmw.web.entity.ModuleConfig;
import com.sunmw.web.entity.SecurityGroupPermission;
import com.sunmw.web.entity.SecurityPermission;
import com.sunmw.web.util.WebUtil;

public class ModuleServicesImpl extends HibernateDaoSupport implements
		ModuleServices {

	public Map deleteModule(int moduleId, String userId) {
		Map result = new HashMap();
		Session session = this.getSession();
		try {
			Transaction tx = session.beginTransaction();
			List<ModuleConfig> pl = this.getHibernateTemplate().find(
					"from ModuleConfig where ParentModuleId = " + moduleId);
			if (!WebUtil.isNullForList(pl))
			{
				result.put("flag", "error");
				result.put("message", "请先删除子菜单");
				return result;
			}
			List<ModuleConfig> l = this.getHibernateTemplate().find(
					"from ModuleConfig where id = " + moduleId);
			if (WebUtil.isNullForList(l))
			{
				result.put("flag", "error");
				result.put("message", "该菜单项已不存在");
				return result;
			}
			ModuleConfig mc = l.get(0);
			session.delete(mc);
			// 需要删除权限和权限组
			if (mc.getPermissionId() != null
					&& mc.getPermissionId().trim().length() > 0) {
				List<SecurityPermission> l2 = this.getHibernateTemplate().find(
						"from SecurityPermission where id = '"
								+ mc.getPermissionId().trim() + "'");
				if (!WebUtil.isNullForList(l2))
					session.delete(l2.get(0));
				List<SecurityGroupPermission> l3 = this.getHibernateTemplate()
						.find(
								"from SecurityGroupPermission where id.PermissionId = '"
										+ mc.getPermissionId().trim() + "'");
				if (l3 != null) {
					for (SecurityGroupPermission s : l3)
						session.delete(s);
				}
				// Query q1 =
				// session.createQuery("delete SecurityPermission where id = '"+mc.getPermissionId().trim()+"'");
				// Query q2 =
				// session.createQuery("delete SecurityGroupPermission where id.PermissionId = '"+mc.getPermissionId().trim()+"'");
				// q1.executeUpdate();
				// q2.executeUpdate();
			}

			tx.commit();
			result.put("flag", "success");
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return result;
	}

	public String saveModule(Map param, String userId) {
		Session session = this.getSession();
		try {
			Transaction tx = session.beginTransaction();
			int moduleId = (Integer) param.get("moduleId");
			ModuleConfig mc = null;
			if (moduleId == 0) {
				// 菜单信息
				mc = new ModuleConfig();
				mc.setCtime(new Date());
				mc.setCuser(userId);
				mc.setModuleName((String) param.get("moduleName"));
				mc.setModuleAlias((String) param.get("moduleAlias"));
				mc.setModuleType("WEB");
				mc.setParentModuleId((Integer) param.get("parentModuleId"));
				mc.setSequence((Integer) param.get("sequence"));
				mc.setShowMenu((String) param.get("showMenu"));
				mc.setUrl((String) param.get("url"));
				session.save(mc);
				// 生成权限,前缀(MENU_)+菜单ID
				SecurityPermission sp = new SecurityPermission();
				sp.setCtime(new Date());
				sp.setCuser(userId);
				sp.setId("MENU_" + mc.getId());
				// 有别名
				if (WebUtil.isNotNull(mc.getModuleAlias()))
					sp.setDescription(mc.getModuleAlias());
				// 无别名
				else
					sp.setDescription(mc.getModuleName());
				sp.setPermissionType("MODULE");
				//根菜单
				if(mc.getParentModuleId()==null||mc.getParentModuleId()==0)
				{
					sp.setParentPermissionId(null);
				}
				//非根菜单
				else
				{
					List<ModuleConfig> mcList = this.getHibernateTemplate().find("from ModuleConfig where id = ?",mc.getParentModuleId());
					if(!WebUtil.isNullForList(mcList))
					{
						sp.setParentPermissionId(mcList.get(0).getPermissionId());
					}
				}
				session.save(sp);
				mc.setPermissionId("MENU_" + mc.getId());
				session.merge(mc);
			} else {
				List<ModuleConfig> l = this.getHibernateTemplate().find(
						"from ModuleConfig where id = " + moduleId);
				if (l == null)
					return "error";
				mc = l.get(0);
				mc.setMtime(new Date());
				mc.setMuser(userId);
				if (param.get("moduleName") != null)
					mc.setModuleName(param.get("moduleName").toString());
				if (param.get("moduleAlias") != null)
					mc.setModuleAlias(param.get("moduleAlias").toString());
				if (param.get("parentModuleId") != null)
					mc.setParentModuleId((Integer) param.get("parentModuleId"));
				if (param.get("sequence") != null)
					mc.setSequence((Integer) param.get("sequence"));
				if (param.get("url") != null)
					mc.setUrl(param.get("url").toString());
				if (param.get("showMenu") != null)
					mc.setShowMenu(param.get("showMenu").toString());
				session.update(mc);
				List<SecurityPermission> spList = this.getHibernateTemplate().find("from SecurityPermission where id = ?",mc.getPermissionId());
				if(!WebUtil.isNullForList(spList))
				{
					SecurityPermission sp = spList.get(0);
					// 有别名
					if (WebUtil.isNotNull(mc.getModuleAlias()))
						sp.setDescription(mc.getModuleAlias());
					// 无别名
					else
						sp.setDescription(mc.getModuleName());
					sp.setMtime(new Date());
					sp.setMuser(userId);
					session.update(sp);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return "success";
	}

	public ModuleConfig getModuleConfigInfo(int moduleId) {
		List<ModuleConfig> l = this.getHibernateTemplate().find(
				"from ModuleConfig where id = " + moduleId);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}

	public List getMenuList() {
		String hql = "from ModuleConfig  order by ParentModuleId,Sequence";
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
}
