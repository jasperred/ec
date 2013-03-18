package com.sunmw.web.entity;

import com.sunmw.web.entity.base.BaseUserAddress;



public class UserAddress extends BaseUserAddress {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public UserAddress () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public UserAddress (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public UserAddress (
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

		super (
			id,
			addressName,
			userId,
			consignee,
			email,
			country,
			province,
			city,
			district,
			address,
			zipcode,
			tel,
			mobile);
	}

/*[CONSTRUCTOR MARKER END]*/


}