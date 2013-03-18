package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_store table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_store"
 */

public abstract class BaseTbStore  implements Serializable {

	public static String REF = "TbStore";
	public static String PROP_STORE_NICK = "StoreNick";
	public static String PROP_SANDBOX_URL = "SandboxUrl";
	public static String PROP_PHONE = "Phone";
	public static String PROP_APP_SERCET = "AppSercet";
	public static String PROP_AREA_ID = "AreaId";
	public static String PROP_SESSION_URL = "SessionUrl";
	public static String PROP_SESSION_KEY = "SessionKey";
	public static String PROP_USE_NOTIFY = "UseNotify";
	public static String PROP_STATUS = "Status";
	public static String PROP_MEMO = "Memo";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_STORE_TYPE = "StoreType";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_ID = "Id";
	public static String PROP_ZIP = "Zip";
	public static String PROP_APP_KEY = "AppKey";


	// constructors
	public BaseTbStore () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbStore (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String storeNick;
	private java.lang.String appKey;
	private java.lang.String status;
	private java.lang.String appSercet;
	private java.lang.String sessionKey;
	private java.lang.String storeType;
	private java.lang.String sandboxUrl;
	private java.lang.String sessionUrl;
	private java.lang.Long areaId;
	private java.lang.String address;
	private java.lang.String zip;
	private java.lang.String phone;
	private java.lang.String mobile;
	private java.lang.String memo;
	private java.lang.String useNotify;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="store_id"
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
	 * Return the value associated with the column: store_nick
	 */
	public java.lang.String getStoreNick () {
		return storeNick;
	}

	/**
	 * Set the value related to the column: store_nick
	 * @param storeNick the store_nick value
	 */
	public void setStoreNick (java.lang.String storeNick) {
		this.storeNick = storeNick;
	}



	/**
	 * Return the value associated with the column: app_key
	 */
	public java.lang.String getAppKey () {
		return appKey;
	}

	/**
	 * Set the value related to the column: app_key
	 * @param appKey the app_key value
	 */
	public void setAppKey (java.lang.String appKey) {
		this.appKey = appKey;
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
	 * Return the value associated with the column: app_sercet
	 */
	public java.lang.String getAppSercet () {
		return appSercet;
	}

	/**
	 * Set the value related to the column: app_sercet
	 * @param appSercet the app_sercet value
	 */
	public void setAppSercet (java.lang.String appSercet) {
		this.appSercet = appSercet;
	}



	/**
	 * Return the value associated with the column: session_key
	 */
	public java.lang.String getSessionKey () {
		return sessionKey;
	}

	/**
	 * Set the value related to the column: session_key
	 * @param sessionKey the session_key value
	 */
	public void setSessionKey (java.lang.String sessionKey) {
		this.sessionKey = sessionKey;
	}



	/**
	 * Return the value associated with the column: store_type
	 */
	public java.lang.String getStoreType () {
		return storeType;
	}

	/**
	 * Set the value related to the column: store_type
	 * @param storeType the store_type value
	 */
	public void setStoreType (java.lang.String storeType) {
		this.storeType = storeType;
	}



	/**
	 * Return the value associated with the column: sandbox_url
	 */
	public java.lang.String getSandboxUrl () {
		return sandboxUrl;
	}

	/**
	 * Set the value related to the column: sandbox_url
	 * @param sandboxUrl the sandbox_url value
	 */
	public void setSandboxUrl (java.lang.String sandboxUrl) {
		this.sandboxUrl = sandboxUrl;
	}



	/**
	 * Return the value associated with the column: session_url
	 */
	public java.lang.String getSessionUrl () {
		return sessionUrl;
	}

	/**
	 * Set the value related to the column: session_url
	 * @param sessionUrl the session_url value
	 */
	public void setSessionUrl (java.lang.String sessionUrl) {
		this.sessionUrl = sessionUrl;
	}



	/**
	 * Return the value associated with the column: area_id
	 */
	public java.lang.Long getAreaId () {
		return areaId;
	}

	/**
	 * Set the value related to the column: area_id
	 * @param areaId the area_id value
	 */
	public void setAreaId (java.lang.Long areaId) {
		this.areaId = areaId;
	}



	/**
	 * Return the value associated with the column: address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * @param address the address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
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



	/**
	 * Return the value associated with the column: phone
	 */
	public java.lang.String getPhone () {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * @param phone the phone value
	 */
	public void setPhone (java.lang.String phone) {
		this.phone = phone;
	}



	/**
	 * Return the value associated with the column: mobile
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * @param mobile the mobile value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
	}



	/**
	 * Return the value associated with the column: memo
	 */
	public java.lang.String getMemo () {
		return memo;
	}

	/**
	 * Set the value related to the column: memo
	 * @param memo the memo value
	 */
	public void setMemo (java.lang.String memo) {
		this.memo = memo;
	}



	/**
	 * Return the value associated with the column: use_notify
	 */
	public java.lang.String getUseNotify () {
		return useNotify;
	}

	/**
	 * Set the value related to the column: use_notify
	 * @param useNotify the use_notify value
	 */
	public void setUseNotify (java.lang.String useNotify) {
		this.useNotify = useNotify;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbStore)) return false;
		else {
			com.sunmw.taobao.entity.TbStore tbStore = (com.sunmw.taobao.entity.TbStore) obj;
			if (null == this.getId() || null == tbStore.getId()) return false;
			else return (this.getId().equals(tbStore.getId()));
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