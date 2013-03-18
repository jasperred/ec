package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the employee table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="employee"
 */

public abstract class BaseEmployee  implements Serializable {

	public static String REF = "Employee";
	public static String PROP_NAME = "Name";
	public static String PROP_CREATED_STAMP = "CreatedStamp";
	public static String PROP_LAST_UPDATED_USER = "LastUpdatedUser";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_DISCOUNT = "Discount";
	public static String PROP_MUSER = "Muser";
	public static String PROP_CREATED_USER = "CreatedUser";
	public static String PROP_LAST_UPDATED_STAMP = "LastUpdatedStamp";


	// constructors
	public BaseEmployee () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseEmployee (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String name;
	private java.lang.Double discount;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;
	private java.util.Date lastUpdatedStamp;
	private java.lang.String lastUpdatedUser;
	private java.util.Date createdStamp;
	private java.lang.String createdUser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="employee_id"
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
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: discount
	 */
	public java.lang.Double getDiscount () {
		return discount;
	}

	/**
	 * Set the value related to the column: discount
	 * @param discount the discount value
	 */
	public void setDiscount (java.lang.Double discount) {
		this.discount = discount;
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



	/**
	 * Return the value associated with the column: LAST_UPDATED_STAMP
	 */
	public java.util.Date getLastUpdatedStamp () {
		return lastUpdatedStamp;
	}

	/**
	 * Set the value related to the column: LAST_UPDATED_STAMP
	 * @param lastUpdatedStamp the LAST_UPDATED_STAMP value
	 */
	public void setLastUpdatedStamp (java.util.Date lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}



	/**
	 * Return the value associated with the column: LAST_UPDATED_USER
	 */
	public java.lang.String getLastUpdatedUser () {
		return lastUpdatedUser;
	}

	/**
	 * Set the value related to the column: LAST_UPDATED_USER
	 * @param lastUpdatedUser the LAST_UPDATED_USER value
	 */
	public void setLastUpdatedUser (java.lang.String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}



	/**
	 * Return the value associated with the column: CREATED_STAMP
	 */
	public java.util.Date getCreatedStamp () {
		return createdStamp;
	}

	/**
	 * Set the value related to the column: CREATED_STAMP
	 * @param createdStamp the CREATED_STAMP value
	 */
	public void setCreatedStamp (java.util.Date createdStamp) {
		this.createdStamp = createdStamp;
	}



	/**
	 * Return the value associated with the column: CREATED_USER
	 */
	public java.lang.String getCreatedUser () {
		return createdUser;
	}

	/**
	 * Set the value related to the column: CREATED_USER
	 * @param createdUser the CREATED_USER value
	 */
	public void setCreatedUser (java.lang.String createdUser) {
		this.createdUser = createdUser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.Employee)) return false;
		else {
			com.sunmw.web.entity.Employee employee = (com.sunmw.web.entity.Employee) obj;
			if (null == this.getId() || null == employee.getId()) return false;
			else return (this.getId().equals(employee.getId()));
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