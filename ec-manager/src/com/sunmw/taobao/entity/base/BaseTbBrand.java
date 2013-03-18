package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_brand table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_brand"
 */

public abstract class BaseTbBrand  implements Serializable {

	public static String REF = "TbBrand";
	public static String PROP_NAME = "Name";
	public static String PROP_PROP_NAME = "PropName";
	public static String PROP_VID = "Vid";
	public static String PROP_ID = "Id";
	public static String PROP_PID = "Pid";


	// constructors
	public BaseTbBrand () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbBrand (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long vid;
	private java.lang.String name;
	private java.lang.Long pid;
	private java.lang.String propName;



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
	 * Return the value associated with the column: vid
	 */
	public java.lang.Long getVid () {
		return vid;
	}

	/**
	 * Set the value related to the column: vid
	 * @param vid the vid value
	 */
	public void setVid (java.lang.Long vid) {
		this.vid = vid;
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
	 * Return the value associated with the column: pid
	 */
	public java.lang.Long getPid () {
		return pid;
	}

	/**
	 * Set the value related to the column: pid
	 * @param pid the pid value
	 */
	public void setPid (java.lang.Long pid) {
		this.pid = pid;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbBrand)) return false;
		else {
			com.sunmw.taobao.entity.TbBrand tbBrand = (com.sunmw.taobao.entity.TbBrand) obj;
			if (null == this.getId() || null == tbBrand.getId()) return false;
			else return (this.getId().equals(tbBrand.getId()));
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