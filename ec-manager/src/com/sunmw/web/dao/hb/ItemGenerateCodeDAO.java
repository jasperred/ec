package com.sunmw.web.dao.hb;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.entity.ItemGenerateCode;

/**
 * A data access object (DAO) providing persistence and search support for
 * ItemGenerateCode entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sunmw.web.dao.hb.ItemGenerateCode
 * @author MyEclipse Persistence Tools
 */

public class ItemGenerateCodeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ItemGenerateCodeDAO.class);
	// property constants
	public static final String BRAND = "brand";
	public static final String CAT = "cat";
	public static final String SUB_CAT = "subCat";
	public static final String YEAR = "year";
	public static final String SEASON = "season";
	public static final String SEX = "sex";
	public static final String DEPT = "dept";
	public static final String DETAIL = "detail";
	public static final String SERIAL = "serial";
	public static final String ITEM_CODE = "itemCode";
	public static final String CUSER = "cuser";
	public static final String MUSER = "muser";

	public void save(ItemGenerateCode transientInstance) {
		log.debug("saving ItemGenerateCode instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ItemGenerateCode persistentInstance) {
		log.debug("deleting ItemGenerateCode instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ItemGenerateCode findById(java.lang.Integer id) {
		log.debug("getting ItemGenerateCode instance with id: " + id);
		try {
			ItemGenerateCode instance = (ItemGenerateCode) getSession().get(
					"com.sunmw.web.dao.hb.ItemGenerateCode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ItemGenerateCode instance) {
		log.debug("finding ItemGenerateCode instance by example");
		try {
			List results = getSession().createCriteria(
					"com.sunmw.web.dao.hb.ItemGenerateCode").add(
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
		log.debug("finding ItemGenerateCode instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ItemGenerateCode as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBrand(Object brand) {
		return findByProperty(BRAND, brand);
	}

	public List findByCat(Object cat) {
		return findByProperty(CAT, cat);
	}

	public List findBySubCat(Object subCat) {
		return findByProperty(SUB_CAT, subCat);
	}

	public List findByYear(Object year) {
		return findByProperty(YEAR, year);
	}

	public List findBySeason(Object season) {
		return findByProperty(SEASON, season);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findByDept(Object dept) {
		return findByProperty(DEPT, dept);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findBySerial(Object serial) {
		return findByProperty(SERIAL, serial);
	}

	public List findByItemCode(Object itemCode) {
		return findByProperty(ITEM_CODE, itemCode);
	}

	public List findByCuser(Object cuser) {
		return findByProperty(CUSER, cuser);
	}

	public List findByMuser(Object muser) {
		return findByProperty(MUSER, muser);
	}

	public List findAll() {
		log.debug("finding all ItemGenerateCode instances");
		try {
			String queryString = "from ItemGenerateCode";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ItemGenerateCode merge(ItemGenerateCode detachedInstance) {
		log.debug("merging ItemGenerateCode instance");
		try {
			ItemGenerateCode result = (ItemGenerateCode) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ItemGenerateCode instance) {
		log.debug("attaching dirty ItemGenerateCode instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ItemGenerateCode instance) {
		log.debug("attaching clean ItemGenerateCode instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}