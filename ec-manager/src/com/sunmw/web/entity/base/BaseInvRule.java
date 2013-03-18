package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inv_rule table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inv_rule"
 */

public abstract class BaseInvRule  implements Serializable {

	public static String REF = "InvRule";
	public static String PROP_PROPORTION_STR = "ProportionStr";
	public static String PROP_IS_ALL = "IsAll";
	public static String PROP_ID = "Id";
	public static String PROP_WH_ID = "WhId";


	// constructors
	public BaseInvRule () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInvRule (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer whId;
	private java.lang.String isAll;
	private java.lang.String proportionStr;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="id"
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
	 * Return the value associated with the column: wh_id
	 */
	public java.lang.Integer getWhId () {
		return whId;
	}

	/**
	 * Set the value related to the column: wh_id
	 * @param whId the wh_id value
	 */
	public void setWhId (java.lang.Integer whId) {
		this.whId = whId;
	}



	/**
	 * Return the value associated with the column: is_all
	 */
	public java.lang.String getIsAll () {
		return isAll;
	}

	/**
	 * Set the value related to the column: is_all
	 * @param isAll the is_all value
	 */
	public void setIsAll (java.lang.String isAll) {
		this.isAll = isAll;
	}



	/**
	 * Return the value associated with the column: proportion_str
	 */
	public java.lang.String getProportionStr () {
		return proportionStr;
	}

	/**
	 * Set the value related to the column: proportion_str
	 * @param proportionStr the proportion_str value
	 */
	public void setProportionStr (java.lang.String proportionStr) {
		this.proportionStr = proportionStr;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.InvRule)) return false;
		else {
			com.sunmw.web.entity.InvRule invRule = (com.sunmw.web.entity.InvRule) obj;
			if (null == this.getId() || null == invRule.getId()) return false;
			else return (this.getId().equals(invRule.getId()));
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