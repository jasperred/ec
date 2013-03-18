package com.sunmw.web.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the company table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="company"
 */

public abstract class BaseCompany  implements Serializable {

	public static String REF = "Company";
	public static String PROP_PHONE = "Phone";
	public static String PROP_RCN_NO = "RcnNo";
	public static String PROP_CONTACT = "Contact";
	public static String PROP_COMPANY_NO = "CompanyNo";
	public static String PROP_PROVINCE_CD = "ProvinceCd";
	public static String PROP_COMPANY_NAME = "CompanyName";
	public static String PROP_CITY = "City";
	public static String PROP_STATUS = "Status";
	public static String PROP_CATEGORY = "Category";
	public static String PROP_MTIME = "Mtime";
	public static String PROP_CUSER = "Cuser";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_ID = "Id";
	public static String PROP_CTIME = "Ctime";
	public static String PROP_MUSER = "Muser";
	public static String PROP_ZIP = "Zip";


	// constructors
	public BaseCompany () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCompany (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String companyNo;
	private java.lang.String companyName;
	private java.lang.String category;
	private java.lang.String rcnNo;
	private java.lang.String provinceCd;
	private java.lang.String city;
	private java.lang.String address;
	private java.lang.String zip;
	private java.lang.String phone;
	private java.lang.String contact;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String cuser;
	private java.lang.String muser;
	private java.lang.String status;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="company_id"
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
	 * Return the value associated with the column: company_no
	 */
	public java.lang.String getCompanyNo () {
		return companyNo;
	}

	/**
	 * Set the value related to the column: company_no
	 * @param companyNo the company_no value
	 */
	public void setCompanyNo (java.lang.String companyNo) {
		this.companyNo = companyNo;
	}



	/**
	 * Return the value associated with the column: company_name
	 */
	public java.lang.String getCompanyName () {
		return companyName;
	}

	/**
	 * Set the value related to the column: company_name
	 * @param companyName the company_name value
	 */
	public void setCompanyName (java.lang.String companyName) {
		this.companyName = companyName;
	}



	/**
	 * Return the value associated with the column: category
	 */
	public java.lang.String getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * @param category the category value
	 */
	public void setCategory (java.lang.String category) {
		this.category = category;
	}



	/**
	 * Return the value associated with the column: rcn_no
	 */
	public java.lang.String getRcnNo () {
		return rcnNo;
	}

	/**
	 * Set the value related to the column: rcn_no
	 * @param rcnNo the rcn_no value
	 */
	public void setRcnNo (java.lang.String rcnNo) {
		this.rcnNo = rcnNo;
	}



	/**
	 * Return the value associated with the column: province_cd
	 */
	public java.lang.String getProvinceCd () {
		return provinceCd;
	}

	/**
	 * Set the value related to the column: province_cd
	 * @param provinceCd the province_cd value
	 */
	public void setProvinceCd (java.lang.String provinceCd) {
		this.provinceCd = provinceCd;
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
	 * Return the value associated with the column: contact
	 */
	public java.lang.String getContact () {
		return contact;
	}

	/**
	 * Set the value related to the column: contact
	 * @param contact the contact value
	 */
	public void setContact (java.lang.String contact) {
		this.contact = contact;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.sunmw.web.entity.Company)) return false;
		else {
			com.sunmw.web.entity.Company company = (com.sunmw.web.entity.Company) obj;
			if (null == this.getId() || null == company.getId()) return false;
			else return (this.getId().equals(company.getId()));
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