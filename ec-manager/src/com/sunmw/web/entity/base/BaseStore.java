package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the store table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="store"
 */

public abstract class BaseStore  implements Serializable {

	public static String REF = "Store";
	public static String PROP_STATUS = "Status";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_STORE_NAME = "StoreName";
	public static String PROP_STORE_TYPE = "StoreType";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";


	// constructors
	public BaseStore () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseStore (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String storeName;
	private java.lang.String storeType;
	private java.lang.String status;
	private java.lang.Integer companyId;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="store_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: store_name
	 */
	public java.lang.String getStoreName () {
		return storeName;
	}

	/**
	 * Set the value related to the column: store_name
	 * @param storeName the store_name value
	 */
	public void setStoreName (java.lang.String storeName) {
		this.storeName = storeName;
	}



	/**
	 * Return the value associated with the column: store_type
	 */
	public java.lang.String getStoreType () {
		return storeType;
	}

	/**
	 * Set the value related to the column: store_type
	 * @param storeType the store_type value
	 */
	public void setStoreType (java.lang.String storeType) {
		this.storeType = storeType;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: company_id
	 */
	public java.lang.Integer getCompanyId () {
		return companyId;
	}

	/**
	 * Set the value related to the column: company_id
	 * @param companyId the company_id value
	 */
	public void setCompanyId (java.lang.Integer companyId) {
		this.companyId = companyId;
	}



	/**
	 * Return the value associated with the column: ctime
	 */
	public java.util.Date getCtime () {
		return ctime;
	}

	/**
	 * Set the value related to the column: ctime
	 * @param ctime the ctime value
	 */
	public void setCtime (java.util.Date ctime) {
		this.ctime = ctime;
	}



	/**
	 * Return the value associated with the column: cuser
	 */
	public java.lang.String getCuser () {
		return cuser;
	}

	/**
	 * Set the value related to the column: cuser
	 * @param cuser the cuser value
	 */
	public void setCuser (java.lang.String cuser) {
		this.cuser = cuser;
	}



	/**
	 * Return the value associated with the column: mtime
	 */
	public java.util.Date getMtime () {
		return mtime;
	}

	/**
	 * Set the value related to the column: mtime
	 * @param mtime the mtime value
	 */
	public void setMtime (java.util.Date mtime) {
		this.mtime = mtime;
	}



	/**
	 * Return the value associated with the column: muser
	 */
	public java.lang.String getMuser () {
		return muser;
	}

	/**
	 * Set the value related to the column: muser
	 * @param muser the muser value
	 */
	public void setMuser (java.lang.String muser) {
		this.muser = muser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.Store)) return false;
		else {
			com.sunmw.web.entity.Store store = (com.sunmw.web.entity.Store) obj;
			if (null == this.getId() || null == store.getId()) return false;
			else return (this.getId().equals(store.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}