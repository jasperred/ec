package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_generate_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_generate_type"
 */

public abstract class BaseItemGenerateType  implements Serializable {

	public static String REF = "ItemGenerateType";
	public static String PROP_PROP_VALUE = "PropValue";
	public static String PROP_PROP_NAME = "PropName";
	public static String PROP_TYPE = "Type";
	public static String PROP_ID = "Id";
	public static String PROP_PARENT_PROP_NAME = "ParentPropName";


	// constructors
	public BaseItemGenerateType () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemGenerateType (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String type;
	private java.lang.String propName;
	private java.lang.String propValue;
	private java.lang.String parentPropName;



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
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
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
	 * Return the value associated with the column: prop_value
	 */
	public java.lang.String getPropValue () {
		return propValue;
	}

	/**
	 * Set the value related to the column: prop_value
	 * @param propValue the prop_value value
	 */
	public void setPropValue (java.lang.String propValue) {
		this.propValue = propValue;
	}



	/**
	 * Return the value associated with the column: parent_prop_name
	 */
	public java.lang.String getParentPropName () {
		return parentPropName;
	}

	/**
	 * Set the value related to the column: parent_prop_name
	 * @param parentPropName the parent_prop_name value
	 */
	public void setParentPropName (java.lang.String parentPropName) {
		this.parentPropName = parentPropName;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.ItemGenerateType)) return false;
		else {
			com.sunmw.web.entity.ItemGenerateType itemGenerateType = (com.sunmw.web.entity.ItemGenerateType) obj;
			if (null == this.getId() || null == itemGenerateType.getId()) return false;
			else return (this.getId().equals(itemGenerateType.getId()));
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