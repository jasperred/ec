package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_cat table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_cat"
 */

public abstract class BaseTbCat  implements Serializable {

	public static String REF = "TbCat";
	public static String PROP_NAME = "Name";
	public static String PROP_STATUS = "Status";
	public static String PROP_IS_PARENT = "IsParent";
	public static String PROP_SORT_ORDER = "SortOrder";
	public static String PROP_PARENT_CID = "ParentCid";
	public static String PROP_CID = "Cid";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTbCat () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbCat (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long cid;
	private java.lang.Long parentCid;
	private java.lang.String name;
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
	 * Return the value associated with the column: parent_cid
	 */
	public java.lang.Long getParentCid () {
		return parentCid;
	}

	/**
	 * Set the value related to the column: parent_cid
	 * @param parentCid the parent_cid value
	 */
	public void setParentCid (java.lang.Long parentCid) {
		this.parentCid = parentCid;
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
		if (!(obj instanceof com.sunmw.taobao.entity.TbCat)) return false;
		else {
			com.sunmw.taobao.entity.TbCat tbCat = (com.sunmw.taobao.entity.TbCat) obj;
			if (null == this.getId() || null == tbCat.getId()) return false;
			else return (this.getId().equals(tbCat.getId()));
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