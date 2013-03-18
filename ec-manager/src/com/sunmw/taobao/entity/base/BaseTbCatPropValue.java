package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_cat_prop_value table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_cat_prop_value"
 */

public abstract class BaseTbCatPropValue  implements Serializable {

	public static String REF = "TbCatPropValue";
	public static String PROP_NAME = "Name";
	public static String PROP_STATUS = "Status";
	public static String PROP_NAME_ALIAS = "NameAlias";
	public static String PROP_PROP_NAME = "PropName";
	public static String PROP_IS_PARENT = "IsParent";
	public static String PROP_VID = "Vid";
	public static String PROP_SORT_ORDER = "SortOrder";
	public static String PROP_CID = "Cid";
	public static String PROP_ID = "Id";
	public static String PROP_PID = "Pid";


	// constructors
	public BaseTbCatPropValue () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbCatPropValue (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long cid;
	private java.lang.Long pid;
	private java.lang.String propName;
	private java.lang.Long vid;
	private java.lang.String name;
	private java.lang.String nameAlias;
	private java.lang.String isParent;
	private java.lang.String status;
	private java.lang.Long sortOrder;



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
	 * Return the value associated with the column: name_alias
	 */
	public java.lang.String getNameAlias () {
		return nameAlias;
	}

	/**
	 * Set the value related to the column: name_alias
	 * @param nameAlias the name_alias value
	 */
	public void setNameAlias (java.lang.String nameAlias) {
		this.nameAlias = nameAlias;
	}



	/**
	 * Return the value associated with the column: is_parent
	 */
	public java.lang.String getIsParent () {
		return isParent;
	}

	/**
	 * Set the value related to the column: is_parent
	 * @param isParent the is_parent value
	 */
	public void setIsParent (java.lang.String isParent) {
		this.isParent = isParent;
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
	 * Return the value associated with the column: sort_order
	 */
	public java.lang.Long getSortOrder () {
		return sortOrder;
	}

	/**
	 * Set the value related to the column: sort_order
	 * @param sortOrder the sort_order value
	 */
	public void setSortOrder (java.lang.Long sortOrder) {
		this.sortOrder = sortOrder;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbCatPropValue)) return false;
		else {
			com.sunmw.taobao.entity.TbCatPropValue tbCatPropValue = (com.sunmw.taobao.entity.TbCatPropValue) obj;
			if (null == this.getId() || null == tbCatPropValue.getId()) return false;
			else return (this.getId().equals(tbCatPropValue.getId()));
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