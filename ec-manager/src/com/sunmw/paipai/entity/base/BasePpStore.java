package com.sunmw.paipai.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the pp_store table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="pp_store"
 */

public abstract class BasePpStore  implements Serializable {

	public static String REF = "PpStore";
	public static String PROP_STORE_NICK = "StoreNick";
	public static String PROP_SPID = "Spid";
	public static String PROP_UIN = "Uin";
	public static String PROP_SECRET_KEY = "SecretKey";
	public static String PROP_ID = "Id";
	public static String PROP_TOKEN = "Token";


	// constructors
	public BasePpStore () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePpStore (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String storeNick;
	private java.lang.String uin;
	private java.lang.String spid;
	private java.lang.String token;
	private java.lang.String secretKey;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: uin
	 */
	public java.lang.String getUin () {
		return uin;
	}

	/**
	 * Set the value related to the column: uin
	 * @param uin the uin value
	 */
	public void setUin (java.lang.String uin) {
		this.uin = uin;
	}



	/**
	 * Return the value associated with the column: spid
	 */
	public java.lang.String getSpid () {
		return spid;
	}

	/**
	 * Set the value related to the column: spid
	 * @param spid the spid value
	 */
	public void setSpid (java.lang.String spid) {
		this.spid = spid;
	}



	/**
	 * Return the value associated with the column: token
	 */
	public java.lang.String getToken () {
		return token;
	}

	/**
	 * Set the value related to the column: token
	 * @param token the token value
	 */
	public void setToken (java.lang.String token) {
		this.token = token;
	}



	/**
	 * Return the value associated with the column: secret_key
	 */
	public java.lang.String getSecretKey () {
		return secretKey;
	}

	/**
	 * Set the value related to the column: secret_key
	 * @param secretKey the secret_key value
	 */
	public void setSecretKey (java.lang.String secretKey) {
		this.secretKey = secretKey;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.paipai.entity.PpStore)) return false;
		else {
			com.sunmw.paipai.entity.PpStore ppStore = (com.sunmw.paipai.entity.PpStore) obj;
			if (null == this.getId() || null == ppStore.getId()) return false;
			else return (this.getId().equals(ppStore.getId()));
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