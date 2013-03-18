package com.sunmw.taobao.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tb_shipping_address table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tb_shipping_address"
 */

public abstract class BaseTbShippingAddress  implements Serializable {

	public static String REF = "TbShippingAddress";
	public static String PROP_ADDRESS_ID = "AddressId";
	public static String PROP_PHONE = "Phone";
	public static String PROP_CREATED = "Created";
	public static String PROP_IS_DEFAULT = "IsDefault";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_ID = "Id";
	public static String PROP_RECEIVER_NAME = "ReceiverName";


	// constructors
	public BaseTbShippingAddress () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTbShippingAddress (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer addressId;
	private java.lang.String receiverName;
	private java.lang.String phone;
	private java.lang.String mobile;
	private boolean isDefault;
	private java.util.Date created;



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
	 * Return the value associated with the column: address_id
	 */
	public java.lang.Integer getAddressId () {
		return addressId;
	}

	/**
	 * Set the value related to the column: address_id
	 * @param addressId the address_id value
	 */
	public void setAddressId (java.lang.Integer addressId) {
		this.addressId = addressId;
	}



	/**
	 * Return the value associated with the column: receiver_name
	 */
	public java.lang.String getReceiverName () {
		return receiverName;
	}

	/**
	 * Set the value related to the column: receiver_name
	 * @param receiverName the receiver_name value
	 */
	public void setReceiverName (java.lang.String receiverName) {
		this.receiverName = receiverName;
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
	 * Return the value associated with the column: is_default
	 */
	public boolean isIsDefault () {
		return isDefault;
	}

	/**
	 * Set the value related to the column: is_default
	 * @param isDefault the is_default value
	 */
	public void setIsDefault (boolean isDefault) {
		this.isDefault = isDefault;
	}



	/**
	 * Return the value associated with the column: created
	 */
	public java.util.Date getCreated () {
		return created;
	}

	/**
	 * Set the value related to the column: created
	 * @param created the created value
	 */
	public void setCreated (java.util.Date created) {
		this.created = created;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.taobao.entity.TbShippingAddress)) return false;
		else {
			com.sunmw.taobao.entity.TbShippingAddress tbShippingAddress = (com.sunmw.taobao.entity.TbShippingAddress) obj;
			if (null == this.getId() || null == tbShippingAddress.getId()) return false;
			else return (this.getId().equals(tbShippingAddress.getId()));
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