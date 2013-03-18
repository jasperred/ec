package com.sunmw.web.dao.hb;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.entity.ItemGenerateType;

/**
 * A data access object (DAO) providing persistence and search support for
 * ItemGenerateType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sunmw.web.dao.hb.ItemGenerateType
 * @author MyEclipse Persistence Tools
 */

public class ItemGenerateTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ItemGenerateTypeDAO.class);
	// property constants
	public static final String TYPE = "type";
	public static final String PROP_NAME = "propName";
	public static final String PROP_VALUE = "propValue";
	public static final String PARENT_PROP_NAME = "parentPropName";

	public void save(ItemGenerateType transientInstance) {
		log.debug("saving ItemGenerateType instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ItemGenerateType persistentInstance) {
		log.debug("deleting ItemGenerateType instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ItemGenerateType findById(java.lang.Integer id) {
		log.debug("getting ItemGenerateType instance with id: " + id);
		try {
			ItemGenerateType instance = (ItemGenerateType) getSession().get(
					"com.sunmw.web.dao.hb.ItemGenerateType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ItemGenerateType instance) {
		log.debug("finding ItemGenerateType instance by example");
		try {
			List results = getSession().createCriteria(
					"com.sunmw.web.dao.hb.ItemGenerateType").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ItemGenerateType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ItemGenerateType as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByPropName(Object propName) {
		return findByProperty(PROP_NAME, propName);
	}

	public List findByPropValue(Object propValue) {
		return findByProperty(PROP_VALUE, propValue);
	}

	public List findByParentPropName(Object parentPropName) {
		return findByProperty(PARENT_PROP_NAME, parentPropName);
	}

	public List findAll() {
		log.debug("finding all ItemGenerateType instances");
		try {
			String queryString = "from ItemGenerateType";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ItemGenerateType merge(ItemGenerateType detachedInstance) {
		log.debug("merging ItemGenerateType instance");
		try {
			ItemGenerateType result = (ItemGenerateType) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ItemGenerateType instance) {
		log.debug("attaching dirty ItemGenerateType instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ItemGenerateType instance) {
		log.debug("attaching clean ItemGenerateType instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}