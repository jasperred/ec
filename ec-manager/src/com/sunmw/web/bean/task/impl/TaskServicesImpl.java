package com.sunmw.web.bean.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.task.TaskServices;
import com.sunmw.web.entity.IfDefine;
import com.sunmw.web.entity.IfDefineProp;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.TaskDefine;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class TaskServicesImpl extends HibernateDaoSupport implements TaskServices {

	public List getIfDefine()
	{
		List<IfDefine> ifdList = this.getHibernateTemplate().find("from IfDefine where Status = 1");
		return ifdList;
	}
	public List getIfDefineByTree()
	{
		List<IfDefine> ifdList = this.getHibernateTemplate().find("from IfDefine where Status = 1");
		Map<String,List> treeMap = new HashMap();
		for(IfDefine ifd:ifdList)
		{
			List l = treeMap.get(ifd.getIfCat());
			if(l==null)
				l = new ArrayList();
			l.add(ifd);
			treeMap.put(ifd.getIfCat(), l);
		}
		List treeList = new ArrayList();
		Iterator<String> iter = treeMap.keySet().iterator();
		while(iter.hasNext())
		{
			String k = iter.next();
			IfDefine tif = new IfDefine();
			tif.setIfName(k);
			Map tree = new HashMap();
			tree.put("info", tif);
			tree.put("subList", treeMap.get(k));
			treeList.add(tree);
		}
		return treeList;
	}
	@Override
	public Map searchIfDefine(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer(" from IfDefine");
		StringBuffer con = new StringBuffer();
		if(WebUtil.isNotNull(param.get("IfCode")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" IfCode = '"+param.get("IfCode")+"'");
		}
		if(WebUtil.isNotNull(param.get("IfName")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" IfName like '%"+param.get("IfName")+"%'");
		}
		if(WebUtil.isNotNull(param.get("Status")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" Status = '"+param.get("Status")+"'");
		}
		if(WebUtil.isNotNull(param.get("IfType")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" IfType = '"+param.get("IfType")+"'");
		}
		if(con.length()>0)
			hql.append(" where "+con.toString());
		if(WebUtil.isNotNull(param.get("OrderBy")))
		{
			hql.append(" order by "+param.get("OrderBy"));
			if(WebUtil.isNotNull(param.get("isDesc")))
				hql.append(" desc");				
		}
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		List<IfDefine> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(l!=null&&l.size()>0)
		{
			for(IfDefine o:l)
			{
				Map r = new HashMap();
				r.put("IfDefineId", o.getId());
				r.put("ClassName", o.getClassName());
				r.put("ClassType", o.getClassType());
				r.put("ErrorRetryTimes", o.getErrorRetryTimes());
				r.put("IfCode", o.getIfCode());
				r.put("IfName", o.getIfName());
				r.put("IfType", o.getIfType());
				r.put("RetryWaitSeconds", o.getRetryWaitSeconds());
				r.put("RunMethod", o.getRunMethod());
				r.put("Status", o.getStatus());
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}
	
	@Override
	public Map saveIfDefine(Map param) {
		Map result = new HashMap();
		Long ifDefineId = (Long) param.get("IfDefineId");
		IfDefine ifd = null;
		if(ifDefineId!=null)
		{
			List<IfDefine> ifdList = this.getHibernateTemplate().find("from IfDefine where id = ?",ifDefineId);
			if(WebUtil.isNullForList(ifdList))
			{
				result.put("Flag", "error");
				return result;
			}
			ifd = ifdList.get(0);
		}
		Date date = new Date();
		if(ifd==null)
		{
			ifd = new IfDefine();
			ifd.setCtime(date);
		}
		else
		{
			ifd.setMtime(date);
		}
		if(WebUtil.isNotNull(param.get("IfCode")))
			ifd.setIfCode((String)param.get("IfCode"));
		if(WebUtil.isNotNull(param.get("IfName")))
			ifd.setIfName((String)param.get("IfName"));

		if(WebUtil.isNotNull(param.get("IfCat")))
			ifd.setIfCat((String)param.get("IfCat"));
		if(WebUtil.isNotNull(param.get("Status")))
			ifd.setStatus((Integer)param.get("Status"));
		if(WebUtil.isNotNull(param.get("ErrorRetryTimes")))
			ifd.setErrorRetryTimes((Integer)param.get("ErrorRetryTimes"));
		if(WebUtil.isNotNull(param.get("RetryWaitSeconds")))
			ifd.setRetryWaitSeconds((Integer)param.get("RetryWaitSeconds"));
		if(WebUtil.isNotNull(param.get("RunMethod")))
			ifd.setRunMethod((String)param.get("RunMethod"));
		if(WebUtil.isNotNull(param.get("ClassName")))
			ifd.setClassName((String)param.get("ClassName"));
		if(WebUtil.isNotNull(param.get("IfType")))
			ifd.setIfType((String)param.get("IfType"));
		if(WebUtil.isNotNull(param.get("ClassType")))
			ifd.setClassType((String)param.get("ClassType"));
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		if(ifd.getId()==null)
			session.save(ifd);
		else
			session.update(ifd);
		List<Map> ifPropList = (List) param.get("IfPropList");
		if(!WebUtil.isNullForList(ifPropList))
		{
			session.createQuery("delete IfDefineProp where IfId = "+ifd.getId()).executeUpdate();
			for(Map m:ifPropList)
			{
				IfDefineProp p = new IfDefineProp();
				p.setCtime(date);
				p.setIfId(ifd.getId());
				p.setPropName((String)param.get("PropName"));
				p.setPropOrder((Integer)param.get("PropOrder"));
				p.setPropValue((String)param.get("PropValue"));
				p.setRemark((String)param.get("Remark"));
				p.setStatus((Short)param.get("Status"));
				session.save(p);
			}
		}
		tx.commit();
		session.close();
		result.put("Flag", "success");
		return result;
	}
	
	@Override
	public String deleteIfDefine(int ifDefineId) {
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		int i = session.createQuery("delete IfDefine where id = "+ifDefineId).executeUpdate();
		if(i==0)
		{
			tx.rollback();
			session.close();
			return "error";
		}
		session.createQuery("delete IfDefineProp where IfId = "+ifDefineId).executeUpdate();
		tx.commit();
		session.close();
		return "success";
	}

	@Override
	public Map searchTaskDefine(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer(" from TaskDefine td,IfDefine i,Store s where td.IfId = i.id and td.StoreId = s.id ");
		StringBuffer con = new StringBuffer();
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" s.CompanyId = "+ul.getCompanyId());
		}
		if(WebUtil.isNotNull(param.get("Status")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" td.Status = '"+param.get("Status")+"'");
		}
		if(WebUtil.isNotNull(param.get("StoreId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" td.StoreId = '"+param.get("StoreId")+"'");
		}
		if(con.length()>0)
			hql.append("and "+con.toString());
		if(WebUtil.isNotNull(param.get("OrderBy")))
		{
			hql.append(" order by "+param.get("OrderBy"));
			if(WebUtil.isNotNull(param.get("isDesc")))
				hql.append(" desc");				
		}
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		List<Object[] > l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(l!=null&&l.size()>0)
		{
			for(Object[] obj:l)
			{
				TaskDefine td = (TaskDefine) obj[0];
				IfDefine i = (IfDefine) obj[1];
				Store s = (Store) obj[2];
				Map r = new HashMap();
				r.put("TaskDefineId", td.getId());
//				r.put("Cycle", td.getCycle());
//				r.put("CycleType", td.getCycleType());
//				if(td.getDayCycleType().equals("O"))
//					r.put("DayCycle", WebUtil.formatDateString(td.getDayCycle(), "HH:mm:ss"));
//				if(td.getDayCycleType().equals("H"))
//					r.put("DayCycle", WebUtil.formatDateString(td.getDayCycle(), "H"));
//				if(td.getDayCycleType().equals("M"))
//					r.put("DayCycle", WebUtil.formatDateString(td.getDayCycle(), "m"));
//				if(td.getDayCycleType().equals("S"))
//					r.put("DayCycle", WebUtil.formatDateString(td.getDayCycle(), "s"));
//				r.put("DayCycleType", td.getDayCycleType());
//				r.put("DayEndTime", WebUtil.formatDateString(td.getDayEndTime(), "HH:mm:ss"));
//				r.put("DayStartTime", WebUtil.formatDateString(td.getDayStartTime(), "HH:mm:ss"));
//				r.put("EndDate", WebUtil.formatDateString(td.getEndDate(), "yyyy-MM-dd"));
				r.put("IfId", td.getIfId());
				r.put("LastEndTime", WebUtil.formatDateString(td.getLastEndTime(), "yyyy-MM-dd HH:mm:ss"));
				r.put("LastStartTime", WebUtil.formatDateString(td.getLastStartTime(), "yyyy-MM-dd HH:mm:ss"));
				r.put("NextExecTime",WebUtil.formatDateString( td.getNextExecTime(), "yyyy-MM-dd HH:mm:ss"));
				r.put("StartDate", WebUtil.formatDateString(td.getStartDate(), "yyyy-MM-dd"));
				r.put("Status", td.getStatus());
				r.put("StoreId", td.getStoreId());
				r.put("IsLastSuccess", td.getLastSuccess());
				r.put("IfName", i.getIfName());
				r.put("IfCode", i.getIfCode());
				r.put("StoreName", s.getStoreName());
				r.put("CronExpression", td.getCronExpression());
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	@Override
	public Map saveTaskDefine(Map param) {
		Map result = new HashMap();
		Long taskDefineId = (Long) param.get("TaskDefineId");
		TaskDefine td = null;
		if(taskDefineId!=null)
		{
			List<TaskDefine> ifdList = this.getHibernateTemplate().find("from TaskDefine where id = ?",taskDefineId);
			if(WebUtil.isNullForList(ifdList))
			{
				result.put("Flag", "error");
				return result;
			}
			td = ifdList.get(0);
		}
		Date date = new Date();
		if(td==null)
		{
			td = new TaskDefine();
			td.setCtime(date);
		}
		else
		{
			td.setMtime(date);
		}
		if(WebUtil.isNotNull(param.get("IfId")))
			td.setIfId((Long)param.get("IfId"));
		if(WebUtil.isNotNull(param.get("StartDate")))
			td.setStartDate((Date)param.get("StartDate"));
		if(WebUtil.isNotNull(param.get("EndDate")))
			td.setEndDate((Date)param.get("EndDate"));
		if(WebUtil.isNotNull(param.get("Status")))
			td.setStatus((Integer)param.get("Status"));
		if(WebUtil.isNotNull(param.get("StoreId")))
			td.setStoreId((Integer)param.get("StoreId"));
		if(WebUtil.isNotNull(param.get("LastStartTime")))
			td.setLastStartTime((Date)param.get("LastStartTime"));
		if(WebUtil.isNotNull(param.get("LastEndTime")))
			td.setLastEndTime((Date)param.get("LastEndTime"));
		if(WebUtil.isNotNull(param.get("LastSuccess")))
			td.setLastSuccess((String)param.get("LastSuccess"));
		if(WebUtil.isNotNull(param.get("CycleType")))
			td.setCycleType((String)param.get("CycleType"));
		if(WebUtil.isNotNull(param.get("Cycle")))
			td.setCycle((Integer)param.get("Cycle"));
		if(WebUtil.isNotNull(param.get("NextExecTime")))
			td.setNextExecTime((Date)param.get("NextExecTime"));
		if(WebUtil.isNotNull(param.get("DayCycleType")))
			td.setDayCycleType((String)param.get("DayCycleType"));
		if(WebUtil.isNotNull(param.get("DayCycle")))
			td.setDayCycle((Date)param.get("DayCycle"));
		if(WebUtil.isNotNull(param.get("DayStartTime")))
			td.setDayStartTime((Date)param.get("DayStartTime"));
		if(WebUtil.isNotNull(param.get("DayEndTime")))
			td.setDayEndTime((Date)param.get("DayEndTime"));
		if(WebUtil.isNotNull(param.get("CronExpression")))
			td.setCronExpression((String)param.get("CronExpression"));
		td.setNextExecTime(null);
		td.setIsLoad("N");
		if(td.getId()==null)
			this.getHibernateTemplate().save(td);
		else
			this.getHibernateTemplate().update(td);
		result.put("Flag", "success");
		return result;
	}
	
	@Override
	public String deleteTaskDefine(long taskDefineId) {
		Session session = this.getSession();
		int i = session.createQuery("delete TaskDefine where id = "+taskDefineId).executeUpdate();
		if(i==0)
		{
			session.close();
			return "error";
		}
		session.close();
		return "success";
	}

	@Override
	public String deleteTaskQueue(long taskQueueId) {
		Session session = this.getSession();
		int i = session.createQuery("delete TaskQueue where id = "+taskQueueId).executeUpdate();
		if(i==0)
		{
			session.close();
			return "error";
		}
		session.close();
		return "success";
	}

	@Override
	public Map saveTaskQueue(Map param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map searchTaskQueue(Map param, int currentPage, int pageRow) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map searchTaskLog(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer("from IfDefine i,TaskDefine t,Store s,TaskLog l where i.id = t.IfId and t.id = l.TaskId and s.id = t.StoreId ");
		StringBuffer con = new StringBuffer();
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" s.CompanyId = "+ul.getCompanyId());
		}
		if(WebUtil.isNotNull(param.get("IfId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" i.id = "+param.get("IfId"));
		}
		if(WebUtil.isNotNull(param.get("StoreId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" s.id = "+param.get("StoreId"));
		}
		if(con.length()>0)
			hql.append(" and "+con.toString());
		if(WebUtil.isNotNull(param.get("OrderBy")))
		{
			hql.append(" order by "+param.get("OrderBy"));
			if(WebUtil.isNotNull(param.get("isDesc")))
				hql.append(" desc");				
		}
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery("select i.id,i.IfName,t.StoreId,s.StoreName,t.id,l.id,l.LogStartTime,l.LogEndTime,l.LogContent "+hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		List<Object[]> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(l!=null&&l.size()>0)
		{
			for(Object[] o:l)
			{
				Map r = new HashMap();
				r.put("IfDefineId", o[0]);
				r.put("IfName", o[1]);
				r.put("StoreId", o[2]);
				r.put("StoreName", o[3]);
				r.put("TaskId", o[4]);
				r.put("TaskLogId", o[5]);
				r.put("LogStartTime", WebUtil.formatDateString((Date)o[6], "yyyy-MM-dd HH:mm:ss"));
				r.put("LogEndTime", WebUtil.formatDateString((Date)o[7], "yyyy-MM-dd HH:mm:ss"));
				r.put("LogContent", o[8]);
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}}
