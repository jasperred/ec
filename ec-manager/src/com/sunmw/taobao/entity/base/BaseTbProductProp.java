package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_product_prop table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_product_prop"
 */

public abstract class BaseTbProductProp  implements Serializable {

	public static String REF = "TbProductProp";
	public static String PROP_PROP_VALUES = "PropValues";
	public static String PROP_PROP_NAME = "PropName";
	public static String PROP_TB_PRODUCT_ID = "TbProductId";
	public static String PROP_CID = "Cid";
	public static String PROP_ID = "Id";
	public static String PROP_PROP_NAMES = "PropNames";
	public static String PROP_PID = "Pid";


	// constructors
	public BaseTbProductProp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbProductProp (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long tbProductId;
	private java.lang.String pid;
	private java.lang.String cid;
	private java.lang.String propName;
	private java.lang.String propValues;
	private java.lang.String propNames;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="local_id"
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
	 * Return the value associated with the column: tb_product_id
	 */
	public java.lang.Long getTbProductId () {
		return tbProductId;
	}

	/**
	 * Set the value related to the column: tb_product_id
	 * @param tbProductId the tb_product_id value
	 */
	public void setTbProductId (java.lang.Long tbProductId) {
		this.tbProductId = tbProductId;
	}



	/**
	 * Return the value associated with the column: pid
	 */
	public java.lang.String getPid () {
		return pid;
	}

	/**
	 * Set the value related to the column: pid
	 * @param pid the pid value
	 */
	public void setPid (java.lang.String pid) {
		this.pid = pid;
	}



	/**
	 * Return the value associated with the column: cid
	 */
	public java.lang.String getCid () {
		return cid;
	}

	/**
	 * Set the value related to the column: cid
	 * @param cid the cid value
	 */
	public void setCid (java.lang.String cid) {
		this.cid = cid;
	}



	/**
	 * Return the value associated with the column: prop_name
	 */
	public java.lang.String getPropName () {
		return propName;
	}

	/**
	 * Set the value related to the column: prop_name
	 * @param propName the prop_name value
	 */
	public void setPropName (java.lang.String propName) {
		this.propName = propName;
	}



	/**
	 * Return the value associated with the column: prop_values
	 */
	public java.lang.String getPropValues () {
		return propValues;
	}

	/**
	 * Set the value related to the column: prop_values
	 * @param propValues the prop_values value
	 */
	public void setPropValues (java.lang.String propValues) {
		this.propValues = propValues;
	}



	/**
	 * Return the value associated with the column: prop_names
	 */
	public java.lang.String getPropNames () {
		return propNames;
	}

	/**
	 * Set the value related to the column: prop_names
	 * @param propNames the prop_names value
	 */
	public void setPropNames (java.lang.String propNames) {
		this.propNames = propNames;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbProductProp)) return false;
		else {
			com.sunmw.taobao.entity.TbProductProp tbProductProp = (com.sunmw.taobao.entity.TbProductProp) obj;
			if (null == this.getId() || null == tbProductProp.getId()) return false;
			else return (this.getId().equals(tbProductProp.getId()));
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