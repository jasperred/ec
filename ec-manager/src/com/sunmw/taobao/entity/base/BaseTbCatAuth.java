package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TbCatAuth table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TbCatAuth"
 */

public abstract class BaseTbCatAuth  implements Serializable {

	public static String REF = "TbCatAuth";
	public static String PROP_PARENT_ID = "ParentId";
	public static String PROP_STORE_ID = "StoreId";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_CID = "Cid";
	public static String PROP_ID = "Id";
	public static String PROP_AUTH_TYPE = "AuthType";


	// constructors
	public BaseTbCatAuth () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbCatAuth (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTbCatAuth (
		java.lang.String id,
		java.lang.Long cid) {

		this.setId(id);
		this.setCid(cid);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.Integer companyId;
	private java.lang.Integer storeId;
	private java.lang.Long cid;
	private java.lang.Integer authType;
	private java.lang.Long parentId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="local_id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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
	 * Return the value associated with the column: store_id
	 */
	public java.lang.Integer getStoreId () {
		return storeId;
	}

	/**
	 * Set the value related to the column: store_id
	 * @param storeId the store_id value
	 */
	public void setStoreId (java.lang.Integer storeId) {
		this.storeId = storeId;
	}



	/**
	 * Return the value associated with the column: cid
	 */
	public java.lang.Long getCid () {
		return cid;
	}

	/**
	 * Set the value related to the column: cid
	 * @param cid the cid value
	 */
	public void setCid (java.lang.Long cid) {
		this.cid = cid;
	}



	/**
	 * Return the value associated with the column: auth_type
	 */
	public java.lang.Integer getAuthType () {
		return authType;
	}

	/**
	 * Set the value related to the column: auth_type
	 * @param authType the auth_type value
	 */
	public void setAuthType (java.lang.Integer authType) {
		this.authType = authType;
	}



	/**
	 * Return the value associated with the column: parent_id
	 */
	public java.lang.Long getParentId () {
		return parentId;
	}

	/**
	 * Set the value related to the column: parent_id
	 * @param parentId the parent_id value
	 */
	public void setParentId (java.lang.Long parentId) {
		this.parentId = parentId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbCatAuth)) return false;
		else {
			com.sunmw.taobao.entity.TbCatAuth tbCatAuth = (com.sunmw.taobao.entity.TbCatAuth) obj;
			if (null == this.getId() || null == tbCatAuth.getId()) return false;
			else return (this.getId().equals(tbCatAuth.getId()));
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