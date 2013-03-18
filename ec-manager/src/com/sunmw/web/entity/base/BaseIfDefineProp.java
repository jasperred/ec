package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the if_define_prop table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="if_define_prop"
 */

public abstract class BaseIfDefineProp  implements Serializable {

	public static String REF = "IfDefineProp";
	public static String PROP_STATUS = "Status";
	public static String PROP_PROP_VALUE = "PropValue";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_PROP_NAME = "PropName";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_PROP_ORDER = "PropOrder";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_IF_ID = "IfId";
	public static String PROP_MUSER = "Muser";
	public static String PROP_REMARK = "Remark";


	// constructors
	public BaseIfDefineProp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseIfDefineProp (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.Long ifId;
	private java.lang.String propName;
	private java.lang.String propValue;
	private java.lang.Integer propOrder;
	private java.lang.String remark;
	private java.lang.Short status;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="If_define_prop_id"
     */
	public java.lang.Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: If_id
	 */
	public java.lang.Long getIfId () {
		return ifId;
	}

	/**
	 * Set the value related to the column: If_id
	 * @param ifId the If_id value
	 */
	public void setIfId (java.lang.Long ifId) {
		this.ifId = ifId;
	}



	/**
	 * Return the value associated with the column: Prop_name
	 */
	public java.lang.String getPropName () {
		return propName;
	}

	/**
	 * Set the value related to the column: Prop_name
	 * @param propName the Prop_name value
	 */
	public void setPropName (java.lang.String propName) {
		this.propName = propName;
	}



	/**
	 * Return the value associated with the column: Prop_value
	 */
	public java.lang.String getPropValue () {
		return propValue;
	}

	/**
	 * Set the value related to the column: Prop_value
	 * @param propValue the Prop_value value
	 */
	public void setPropValue (java.lang.String propValue) {
		this.propValue = propValue;
	}



	/**
	 * Return the value associated with the column: Prop_order
	 */
	public java.lang.Integer getPropOrder () {
		return propOrder;
	}

	/**
	 * Set the value related to the column: Prop_order
	 * @param propOrder the Prop_order value
	 */
	public void setPropOrder (java.lang.Integer propOrder) {
		this.propOrder = propOrder;
	}



	/**
	 * Return the value associated with the column: Remark
	 */
	public java.lang.String getRemark () {
		return remark;
	}

	/**
	 * Set the value related to the column: Remark
	 * @param remark the Remark value
	 */
	public void setRemark (java.lang.String remark) {
		this.remark = remark;
	}



	/**
	 * Return the value associated with the column: Status
	 */
	public java.lang.Short getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: Status
	 * @param status the Status value
	 */
	public void setStatus (java.lang.Short status) {
		this.status = status;
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
		if (!(obj instanceof com.sunmw.web.entity.IfDefineProp)) return false;
		else {
			com.sunmw.web.entity.IfDefineProp ifDefineProp = (com.sunmw.web.entity.IfDefineProp) obj;
			if (null == this.getId() || null == ifDefineProp.getId()) return false;
			else return (this.getId().equals(ifDefineProp.getId()));
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