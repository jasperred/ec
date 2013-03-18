package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the user_address table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="user_address"
 */

public abstract class BaseUserAddress  implements Serializable {

	public static String REF = "UserAddress";
	public static String PROP_USER_ID = "UserId";
	public static String PROP_CONSIGNEE = "Consignee";
	public static String PROP_COUNTRY = "Country";
	public static String PROP_PROVINCE = "Province";
	public static String PROP_ZIPCODE = "Zipcode";
	public static String PROP_CITY = "City";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_EMAIL = "Email";
	public static String PROP_DISTRICT = "District";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_ADDRESS_NAME = "AddressName";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_TEL = "Tel";


	// constructors
	public BaseUserAddress () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserAddress (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseUserAddress (
		java.lang.Integer id,
		java.lang.String addressName,
		java.lang.Integer userId,
		java.lang.String consignee,
		java.lang.String email,
		java.lang.String country,
		java.lang.String province,
		java.lang.String city,
		java.lang.String district,
		java.lang.String address,
		java.lang.String zipcode,
		java.lang.String tel,
		java.lang.String mobile) {

		this.setId(id);
		this.setAddressName(addressName);
		this.setUserId(userId);
		this.setConsignee(consignee);
		this.setEmail(email);
		this.setCountry(country);
		this.setProvince(province);
		this.setCity(city);
		this.setDistrict(district);
		this.setAddress(address);
		this.setZipcode(zipcode);
		this.setTel(tel);
		this.setMobile(mobile);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String addressName;
	private java.lang.Integer userId;
	private java.lang.String consignee;
	private java.lang.String email;
	private java.lang.String country;
	private java.lang.String province;
	private java.lang.String city;
	private java.lang.String district;
	private java.lang.String address;
	private java.lang.String zipcode;
	private java.lang.String tel;
	private java.lang.String mobile;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="address_id"
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
	 * Return the value associated with the column: address_name
	 */
	public java.lang.String getAddressName () {
		return addressName;
	}

	/**
	 * Set the value related to the column: address_name
	 * @param addressName the address_name value
	 */
	public void setAddressName (java.lang.String addressName) {
		this.addressName = addressName;
	}



	/**
	 * Return the value associated with the column: user_id
	 */
	public java.lang.Integer getUserId () {
		return userId;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param userId the user_id value
	 */
	public void setUserId (java.lang.Integer userId) {
		this.userId = userId;
	}



	/**
	 * Return the value associated with the column: consignee
	 */
	public java.lang.String getConsignee () {
		return consignee;
	}

	/**
	 * Set the value related to the column: consignee
	 * @param consignee the consignee value
	 */
	public void setConsignee (java.lang.String consignee) {
		this.consignee = consignee;
	}



	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: country
	 */
	public java.lang.String getCountry () {
		return country;
	}

	/**
	 * Set the value related to the column: country
	 * @param country the country value
	 */
	public void setCountry (java.lang.String country) {
		this.country = country;
	}



	/**
	 * Return the value associated with the column: province
	 */
	public java.lang.String getProvince () {
		return province;
	}

	/**
	 * Set the value related to the column: province
	 * @param province the province value
	 */
	public void setProvince (java.lang.String province) {
		this.province = province;
	}



	/**
	 * Return the value associated with the column: city
	 */
	public java.lang.String getCity () {
		return city;
	}

	/**
	 * Set the value related to the column: city
	 * @param city the city value
	 */
	public void setCity (java.lang.String city) {
		this.city = city;
	}



	/**
	 * Return the value associated with the column: district
	 */
	public java.lang.String getDistrict () {
		return district;
	}

	/**
	 * Set the value related to the column: district
	 * @param district the district value
	 */
	public void setDistrict (java.lang.String district) {
		this.district = district;
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
	 * Return the value associated with the column: zipcode
	 */
	public java.lang.String getZipcode () {
		return zipcode;
	}

	/**
	 * Set the value related to the column: zipcode
	 * @param zipcode the zipcode value
	 */
	public void setZipcode (java.lang.String zipcode) {
		this.zipcode = zipcode;
	}



	/**
	 * Return the value associated with the column: tel
	 */
	public java.lang.String getTel () {
		return tel;
	}

	/**
	 * Set the value related to the column: tel
	 * @param tel the tel value
	 */
	public void setTel (java.lang.String tel) {
		this.tel = tel;
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
	 * Return the value associated with the column: ctime
	 */
	public java.util.Date getCtime () {
		return ctime;
	}

	/**
	 * Set the value related to the column: ctime
	 * @param ctime the ctime value
	 */
	public void setCtime (java.util.Date ctime) {
		this.ctime = ctime;
	}



	/**
	 * Return the value associated with the column: mtime
	 */
	public java.util.Date getMtime () {
		return mtime;
	}

	/**
	 * Set the value related to the column: mtime
	 * @param mtime the mtime value
	 */
	public void setMtime (java.util.Date mtime) {
		this.mtime = mtime;
	}



	/**
	 * Return the value associated with the column: cuser
	 */
	public java.lang.String getCuser () {
		return cuser;
	}

	/**
	 * Set the value related to the column: cuser
	 * @param cuser the cuser value
	 */
	public void setCuser (java.lang.String cuser) {
		this.cuser = cuser;
	}



	/**
	 * Return the value associated with the column: muser
	 */
	public java.lang.String getMuser () {
		return muser;
	}

	/**
	 * Set the value related to the column: muser
	 * @param muser the muser value
	 */
	public void setMuser (java.lang.String muser) {
		this.muser = muser;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.UserAddress)) return false;
		else {
			com.sunmw.web.entity.UserAddress userAddress = (com.sunmw.web.entity.UserAddress) obj;
			if (null == this.getId() || null == userAddress.getId()) return false;
			else return (this.getId().equals(userAddress.getId()));
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