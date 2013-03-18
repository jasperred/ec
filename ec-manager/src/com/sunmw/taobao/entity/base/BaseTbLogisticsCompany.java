package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_logistics_company table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_logistics_company"
 */

public abstract class BaseTbLogisticsCompany  implements Serializable {

	public static String REF = "TbLogisticsCompany";
	public static String PROP_NAME = "Name";
	public static String PROP_WH_LOGISTICS_NAME = "WhLogisticsName";
	public static String PROP_ID = "Id";
	public static String PROP_CODE = "Code";


	// constructors
	public BaseTbLogisticsCompany () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbLogisticsCompany (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String code;
	private java.lang.String name;
	private java.lang.String whLogisticsName;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="id"
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
	 * Return the value associated with the column: code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: code
	 * @param code the code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
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
	 * Return the value associated with the column: wh_logistics_name
	 */
	public java.lang.String getWhLogisticsName () {
		return whLogisticsName;
	}

	/**
	 * Set the value related to the column: wh_logistics_name
	 * @param whLogisticsName the wh_logistics_name value
	 */
	public void setWhLogisticsName (java.lang.String whLogisticsName) {
		this.whLogisticsName = whLogisticsName;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbLogisticsCompany)) return false;
		else {
			com.sunmw.taobao.entity.TbLogisticsCompany tbLogisticsCompany = (com.sunmw.taobao.entity.TbLogisticsCompany) obj;
			if (null == this.getId() || null == tbLogisticsCompany.getId()) return false;
			else return (this.getId().equals(tbLogisticsCompany.getId()));
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