package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_cat_prop table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_cat_prop"
 */

public abstract class BaseTbCatProp  implements Serializable {

	public static String REF = "TbCatProp";
	public static String PROP_MULTI = "Multi";
	public static String PROP_SORT_ORDER = "SortOrder";
	public static String PROP_IS_INPUT_PROP = "IsInputProp";
	public static String PROP_MUST = "Must";
	public static String PROP_IS_KEY_PROP = "IsKeyProp";
	public static String PROP_NAME = "Name";
	public static String PROP_CHILD_TEMPLATE = "ChildTemplate";
	public static String PROP_STATUS = "Status";
	public static String PROP_IS_ITEM_PROP = "IsItemProp";
	public static String PROP_IS_ENUM_PROP = "IsEnumProp";
	public static String PROP_ID = "Id";
	public static String PROP_IS_COLOR_PROP = "IsColorProp";
	public static String PROP_IS_ALLOW_ALIAS = "IsAllowAlias";
	public static String PROP_PARENT_PID = "ParentPid";
	public static String PROP_PARENT_VID = "ParentVid";
	public static String PROP_PID = "Pid";
	public static String PROP_IS_SALE_PROP = "IsSaleProp";


	// constructors
	public BaseTbCatProp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbCatProp (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Long pid;
	private java.lang.Long parentPid;
	private java.lang.Long parentVid;
	private java.lang.String name;
	private java.lang.String isKeyProp;
	private java.lang.String isSaleProp;
	private java.lang.String isColorProp;
	private java.lang.String isEnumProp;
	private java.lang.String isInputProp;
	private java.lang.String isItemProp;
	private java.lang.String must;
	private java.lang.String multi;
	private java.lang.String status;
	private java.lang.Long sortOrder;
	private java.lang.String childTemplate;
	private java.lang.String isAllowAlias;



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
	 * Return the value associated with the column: parent_pid
	 */
	public java.lang.Long getParentPid () {
		return parentPid;
	}

	/**
	 * Set the value related to the column: parent_pid
	 * @param parentPid the parent_pid value
	 */
	public void setParentPid (java.lang.Long parentPid) {
		this.parentPid = parentPid;
	}



	/**
	 * Return the value associated with the column: parent_vid
	 */
	public java.lang.Long getParentVid () {
		return parentVid;
	}

	/**
	 * Set the value related to the column: parent_vid
	 * @param parentVid the parent_vid value
	 */
	public void setParentVid (java.lang.Long parentVid) {
		this.parentVid = parentVid;
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
	 * Return the value associated with the column: is_key_prop
	 */
	public java.lang.String getIsKeyProp () {
		return isKeyProp;
	}

	/**
	 * Set the value related to the column: is_key_prop
	 * @param isKeyProp the is_key_prop value
	 */
	public void setIsKeyProp (java.lang.String isKeyProp) {
		this.isKeyProp = isKeyProp;
	}



	/**
	 * Return the value associated with the column: is_sale_prop
	 */
	public java.lang.String getIsSaleProp () {
		return isSaleProp;
	}

	/**
	 * Set the value related to the column: is_sale_prop
	 * @param isSaleProp the is_sale_prop value
	 */
	public void setIsSaleProp (java.lang.String isSaleProp) {
		this.isSaleProp = isSaleProp;
	}



	/**
	 * Return the value associated with the column: is_color_prop
	 */
	public java.lang.String getIsColorProp () {
		return isColorProp;
	}

	/**
	 * Set the value related to the column: is_color_prop
	 * @param isColorProp the is_color_prop value
	 */
	public void setIsColorProp (java.lang.String isColorProp) {
		this.isColorProp = isColorProp;
	}



	/**
	 * Return the value associated with the column: is_enum_prop
	 */
	public java.lang.String getIsEnumProp () {
		return isEnumProp;
	}

	/**
	 * Set the value related to the column: is_enum_prop
	 * @param isEnumProp the is_enum_prop value
	 */
	public void setIsEnumProp (java.lang.String isEnumProp) {
		this.isEnumProp = isEnumProp;
	}



	/**
	 * Return the value associated with the column: is_input_prop
	 */
	public java.lang.String getIsInputProp () {
		return isInputProp;
	}

	/**
	 * Set the value related to the column: is_input_prop
	 * @param isInputProp the is_input_prop value
	 */
	public void setIsInputProp (java.lang.String isInputProp) {
		this.isInputProp = isInputProp;
	}



	/**
	 * Return the value associated with the column: is_item_prop
	 */
	public java.lang.String getIsItemProp () {
		return isItemProp;
	}

	/**
	 * Set the value related to the column: is_item_prop
	 * @param isItemProp the is_item_prop value
	 */
	public void setIsItemProp (java.lang.String isItemProp) {
		this.isItemProp = isItemProp;
	}



	/**
	 * Return the value associated with the column: must
	 */
	public java.lang.String getMust () {
		return must;
	}

	/**
	 * Set the value related to the column: must
	 * @param must the must value
	 */
	public void setMust (java.lang.String must) {
		this.must = must;
	}



	/**
	 * Return the value associated with the column: multi
	 */
	public java.lang.String getMulti () {
		return multi;
	}

	/**
	 * Set the value related to the column: multi
	 * @param multi the multi value
	 */
	public void setMulti (java.lang.String multi) {
		this.multi = multi;
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



	/**
	 * Return the value associated with the column: child_template
	 */
	public java.lang.String getChildTemplate () {
		return childTemplate;
	}

	/**
	 * Set the value related to the column: child_template
	 * @param childTemplate the child_template value
	 */
	public void setChildTemplate (java.lang.String childTemplate) {
		this.childTemplate = childTemplate;
	}



	/**
	 * Return the value associated with the column: is_allow_alias
	 */
	public java.lang.String getIsAllowAlias () {
		return isAllowAlias;
	}

	/**
	 * Set the value related to the column: is_allow_alias
	 * @param isAllowAlias the is_allow_alias value
	 */
	public void setIsAllowAlias (java.lang.String isAllowAlias) {
		this.isAllowAlias = isAllowAlias;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbCatProp)) return false;
		else {
			com.sunmw.taobao.entity.TbCatProp tbCatProp = (com.sunmw.taobao.entity.TbCatProp) obj;
			if (null == this.getId() || null == tbCatProp.getId()) return false;
			else return (this.getId().equals(tbCatProp.getId()));
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