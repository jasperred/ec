package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer"
 */

public abstract class BaseCustomer  implements Serializable {

	public static String REF = "Customer";
	public static String PROP_CUST_NO = "CustNo";
	public static String PROP_COMPANY_ID = "CompanyId";
	public static String PROP_BIRTH_DAY = "BirthDay";
	public static String PROP_PROVINCE = "Province";
	public static String PROP_ZIPCODE = "Zipcode";
	public static String PROP_CITY = "City";
	public static String PROP_CUST_NAME = "CustName";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_CUST_NO1 = "CustNo1";
	public static String PROP_EMAIL = "Email";
	public static String PROP_DISTRICT = "District";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_TEL = "Tel";
	public static String PROP_SEX = "Sex";


	// constructors
	public BaseCustomer () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomer (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String custNo;
	private java.lang.String custNo1;
	private java.lang.String custName;
	private java.lang.String mobile;
	private java.lang.String tel;
	private java.lang.String birthDay;
	private java.lang.String email;
	private java.lang.String sex;
	private java.lang.String province;
	private java.lang.String city;
	private java.lang.String district;
	private java.lang.String address;
	private java.lang.String zipcode;
	private java.lang.Integer companyId;
	private java.util.Date ctime;
	private java.lang.String cuser;
	private java.util.Date mtime;
	private java.lang.String muser;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="cust_id"
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
	 * Return the value associated with the column: cust_no
	 */
	public java.lang.String getCustNo () {
		return custNo;
	}

	/**
	 * Set the value related to the column: cust_no
	 * @param custNo the cust_no value
	 */
	public void setCustNo (java.lang.String custNo) {
		this.custNo = custNo;
	}



	/**
	 * Return the value associated with the column: cust_no1
	 */
	public java.lang.String getCustNo1 () {
		return custNo1;
	}

	/**
	 * Set the value related to the column: cust_no1
	 * @param custNo1 the cust_no1 value
	 */
	public void setCustNo1 (java.lang.String custNo1) {
		this.custNo1 = custNo1;
	}



	/**
	 * Return the value associated with the column: cust_name
	 */
	public java.lang.String getCustName () {
		return custName;
	}

	/**
	 * Set the value related to the column: cust_name
	 * @param custName the cust_name value
	 */
	public void setCustName (java.lang.String custName) {
		this.custName = custName;
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
	 * Return the value associated with the column: birth_day
	 */
	public java.lang.String getBirthDay () {
		return birthDay;
	}

	/**
	 * Set the value related to the column: birth_day
	 * @param birthDay the birth_day value
	 */
	public void setBirthDay (java.lang.String birthDay) {
		this.birthDay = birthDay;
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
	 * Return the value associated with the column: sex
	 */
	public java.lang.String getSex () {
		return sex;
	}

	/**
	 * Set the value related to the column: sex
	 * @param sex the sex value
	 */
	public void setSex (java.lang.String sex) {
		this.sex = sex;
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
	 * Return the value associated with the column: company_id
	 */
	public java.lang.Integer getCompanyId () {
		return companyId;
	}

	/**
	 * Set the value related to the column: company_id
	 * @param companyId the company_id value
	 */
	public void setCompanyId (java.lang.Integer companyId) {
		this.companyId = companyId;
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
		if (!(obj instanceof com.sunmw.web.entity.Customer)) return false;
		else {
			com.sunmw.web.entity.Customer customer = (com.sunmw.web.entity.Customer) obj;
			if (null == this.getId() || null == customer.getId()) return false;
			else return (this.getId().equals(customer.getId()));
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