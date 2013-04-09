package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the warehouse table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="warehouse"
 */

public abstract class BaseWarehouse  implements Serializable {

	public static String REF = "Warehouse";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_WH_NAME = "WhName";
	public static String PROP_ALLOW_NEGATIVE_INV = "AllowNegativeInv";
	public static String PROP_WH_TYPE = "WhType";
	public static String PROP_STATUS = "Status";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_ID = "Id";
	public static String PROP_MUSER = "Muser";
	public static String PROP_VALID_TIME = "ValidTime";
	public static String PROP_RESERVE_INV = "ReserveInv";
	public static String PROP_WH_CODE = "WhCode";


	// constructors
	public BaseWarehouse () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseWarehouse (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String whName;
	private java.lang.String whType;
	private java.lang.String status;
	private java.lang.Integer reserveInv;
	private java.lang.String allowNegativeInv;
	private java.lang.String whCode;
	private java.lang.Integer validTime;
	private java.lang.Integer companyId;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="wh_id"
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
	 * Return the value associated with the column: wh_name
	 */
	public java.lang.String getWhName () {
		return whName;
	}

	/**
	 * Set the value related to the column: wh_name
	 * @param whName the wh_name value
	 */
	public void setWhName (java.lang.String whName) {
		this.whName = whName;
	}



	/**
	 * Return the value associated with the column: wh_type
	 */
	public java.lang.String getWhType () {
		return whType;
	}

	/**
	 * Set the value related to the column: wh_type
	 * @param whType the wh_type value
	 */
	public void setWhType (java.lang.String whType) {
		this.whType = whType;
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
	 * Return the value associated with the column: reserve_inv
	 */
	public java.lang.Integer getReserveInv () {
		return reserveInv;
	}

	/**
	 * Set the value related to the column: reserve_inv
	 * @param reserveInv the reserve_inv value
	 */
	public void setReserveInv (java.lang.Integer reserveInv) {
		this.reserveInv = reserveInv;
	}



	/**
	 * Return the value associated with the column: allow_negative_inv
	 */
	public java.lang.String getAllowNegativeInv () {
		return allowNegativeInv;
	}

	/**
	 * Set the value related to the column: allow_negative_inv
	 * @param allowNegativeInv the allow_negative_inv value
	 */
	public void setAllowNegativeInv (java.lang.String allowNegativeInv) {
		this.allowNegativeInv = allowNegativeInv;
	}



	/**
	 * Return the value associated with the column: wh_code
	 */
	public java.lang.String getWhCode () {
		return whCode;
	}

	/**
	 * Set the value related to the column: wh_code
	 * @param whCode the wh_code value
	 */
	public void setWhCode (java.lang.String whCode) {
		this.whCode = whCode;
	}



	/**
	 * Return the value associated with the column: valid_time
	 */
	public java.lang.Integer getValidTime () {
		return validTime;
	}

	/**
	 * Set the value related to the column: valid_time
	 * @param validTime the valid_time value
	 */
	public void setValidTime (java.lang.Integer validTime) {
		this.validTime = validTime;
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
		if (!(obj instanceof com.sunmw.web.entity.Warehouse)) return false;
		else {
			com.sunmw.web.entity.Warehouse warehouse = (com.sunmw.web.entity.Warehouse) obj;
			if (null == this.getId() || null == warehouse.getId()) return false;
			else return (this.getId().equals(warehouse.getId()));
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