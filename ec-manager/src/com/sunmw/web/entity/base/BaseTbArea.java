package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_area table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_area"
 */

public abstract class BaseTbArea  implements Serializable {

	public static String REF = "TbArea";
	public static String PROP_NAME = "Name";
	public static String PROP_PARENT_ID = "ParentId";
	public static String PROP_TYPE = "Type";
	public static String PROP_ID = "Id";
	public static String PROP_ZIP = "Zip";


	// constructors
	public BaseTbArea () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbArea (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer type;
	private java.lang.String name;
	private java.lang.Integer parentId;
	private java.lang.String zip;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="tb_area_id"
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
	 * Return the value associated with the column: type
	 */
	public java.lang.Integer getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.Integer type) {
		this.type = type;
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
	 * Return the value associated with the column: parent_id
	 */
	public java.lang.Integer getParentId () {
		return parentId;
	}

	/**
	 * Set the value related to the column: parent_id
	 * @param parentId the parent_id value
	 */
	public void setParentId (java.lang.Integer parentId) {
		this.parentId = parentId;
	}



	/**
	 * Return the value associated with the column: zip
	 */
	public java.lang.String getZip () {
		return zip;
	}

	/**
	 * Set the value related to the column: zip
	 * @param zip the zip value
	 */
	public void setZip (java.lang.String zip) {
		this.zip = zip;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.TbArea)) return false;
		else {
			com.sunmw.web.entity.TbArea tbArea = (com.sunmw.web.entity.TbArea) obj;
			if (null == this.getId() || null == tbArea.getId()) return false;
			else return (this.getId().equals(tbArea.getId()));
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