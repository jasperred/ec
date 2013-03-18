package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_api table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_api"
 */

public abstract class BaseTbApi  implements Serializable {

	public static String REF = "TbApi";
	public static String PROP_API_NAME = "ApiName";
	public static String PROP_API_TYPE = "ApiType";
	public static String PROP_API_CALL = "ApiCall";
	public static String PROP_ID = "Id";
	public static String PROP_API_DESCRIPTION = "ApiDescription";


	// constructors
	public BaseTbApi () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbApi (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String apiName;
	private java.lang.String apiType;
	private java.lang.String apiDescription;
	private java.lang.String apiCall;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="ID"
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
	 * Return the value associated with the column: API_NAME
	 */
	public java.lang.String getApiName () {
		return apiName;
	}

	/**
	 * Set the value related to the column: API_NAME
	 * @param apiName the API_NAME value
	 */
	public void setApiName (java.lang.String apiName) {
		this.apiName = apiName;
	}



	/**
	 * Return the value associated with the column: API_TYPE
	 */
	public java.lang.String getApiType () {
		return apiType;
	}

	/**
	 * Set the value related to the column: API_TYPE
	 * @param apiType the API_TYPE value
	 */
	public void setApiType (java.lang.String apiType) {
		this.apiType = apiType;
	}



	/**
	 * Return the value associated with the column: API_DESCRIPTION
	 */
	public java.lang.String getApiDescription () {
		return apiDescription;
	}

	/**
	 * Set the value related to the column: API_DESCRIPTION
	 * @param apiDescription the API_DESCRIPTION value
	 */
	public void setApiDescription (java.lang.String apiDescription) {
		this.apiDescription = apiDescription;
	}



	/**
	 * Return the value associated with the column: API_CALL
	 */
	public java.lang.String getApiCall () {
		return apiCall;
	}

	/**
	 * Set the value related to the column: API_CALL
	 * @param apiCall the API_CALL value
	 */
	public void setApiCall (java.lang.String apiCall) {
		this.apiCall = apiCall;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbApi)) return false;
		else {
			com.sunmw.taobao.entity.TbApi tbApi = (com.sunmw.taobao.entity.TbApi) obj;
			if (null == this.getId() || null == tbApi.getId()) return false;
			else return (this.getId().equals(tbApi.getId()));
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