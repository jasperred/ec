package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_api_params table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_api_params"
 */

public abstract class BaseTbApiParams  implements Serializable {

	public static String REF = "TbApiParams";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_EXAMPLE = "Example";
	public static String PROP_DATA_TYPE = "DataType";
	public static String PROP_PARAM_VALUE = "ParamValue";
	public static String PROP_PARAM_TYPE = "ParamType";
	public static String PROP_DEFAULT_VALUE = "DefaultValue";
	public static String PROP_MUST = "Must";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTbApiParams () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbApiParams (com.sunmw.taobao.entity.TbApiParamsPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.sunmw.taobao.entity.TbApiParamsPK id;

	// fields
	private java.lang.String paramType;
	private java.lang.String paramValue;
	private boolean must;
	private java.lang.String defaultValue;
	private java.lang.String description;
	private java.lang.String dataType;
	private java.lang.String example;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.sunmw.taobao.entity.TbApiParamsPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.sunmw.taobao.entity.TbApiParamsPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: PARAM_TYPE
	 */
	public java.lang.String getParamType () {
		return paramType;
	}

	/**
	 * Set the value related to the column: PARAM_TYPE
	 * @param paramType the PARAM_TYPE value
	 */
	public void setParamType (java.lang.String paramType) {
		this.paramType = paramType;
	}



	/**
	 * Return the value associated with the column: PARAM_VALUE
	 */
	public java.lang.String getParamValue () {
		return paramValue;
	}

	/**
	 * Set the value related to the column: PARAM_VALUE
	 * @param paramValue the PARAM_VALUE value
	 */
	public void setParamValue (java.lang.String paramValue) {
		this.paramValue = paramValue;
	}



	/**
	 * Return the value associated with the column: IS_MUST
	 */
	public boolean isMust () {
		return must;
	}

	/**
	 * Set the value related to the column: IS_MUST
	 * @param must the IS_MUST value
	 */
	public void setMust (boolean must) {
		this.must = must;
	}



	/**
	 * Return the value associated with the column: DEFAULT_VALUE
	 */
	public java.lang.String getDefaultValue () {
		return defaultValue;
	}

	/**
	 * Set the value related to the column: DEFAULT_VALUE
	 * @param defaultValue the DEFAULT_VALUE value
	 */
	public void setDefaultValue (java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
	}



	/**
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * @param description the DESCRIPTION value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: DATA_TYPE
	 */
	public java.lang.String getDataType () {
		return dataType;
	}

	/**
	 * Set the value related to the column: DATA_TYPE
	 * @param dataType the DATA_TYPE value
	 */
	public void setDataType (java.lang.String dataType) {
		this.dataType = dataType;
	}



	/**
	 * Return the value associated with the column: EXAMPLE
	 */
	public java.lang.String getExample () {
		return example;
	}

	/**
	 * Set the value related to the column: EXAMPLE
	 * @param example the EXAMPLE value
	 */
	public void setExample (java.lang.String example) {
		this.example = example;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbApiParams)) return false;
		else {
			com.sunmw.taobao.entity.TbApiParams tbApiParams = (com.sunmw.taobao.entity.TbApiParams) obj;
			if (null == this.getId() || null == tbApiParams.getId()) return false;
			else return (this.getId().equals(tbApiParams.getId()));
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