package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inv_rule_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inv_rule_detail"
 */

public abstract class BaseInvRuleDetail  implements Serializable {

	public static String REF = "InvRuleDetail";
	public static String PROP_PROPORTION_STR = "ProportionStr";
	public static String PROP_RULE_ID = "RuleId";
	public static String PROP_ID = "Id";
	public static String PROP_SKU_CODE = "SkuCode";


	// constructors
	public BaseInvRuleDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInvRuleDetail (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer ruleId;
	private java.lang.String skuCode;
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
	 * Return the value associated with the column: rule_id
	 */
	public java.lang.Integer getRuleId () {
		return ruleId;
	}

	/**
	 * Set the value related to the column: rule_id
	 * @param ruleId the rule_id value
	 */
	public void setRuleId (java.lang.Integer ruleId) {
		this.ruleId = ruleId;
	}



	/**
	 * Return the value associated with the column: sku_code
	 */
	public java.lang.String getSkuCode () {
		return skuCode;
	}

	/**
	 * Set the value related to the column: sku_code
	 * @param skuCode the sku_code value
	 */
	public void setSkuCode (java.lang.String skuCode) {
		this.skuCode = skuCode;
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
		if (!(obj instanceof com.sunmw.web.entity.InvRuleDetail)) return false;
		else {
			com.sunmw.web.entity.InvRuleDetail invRuleDetail = (com.sunmw.web.entity.InvRuleDetail) obj;
			if (null == this.getId() || null == invRuleDetail.getId()) return false;
			else return (this.getId().equals(invRuleDetail.getId()));
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