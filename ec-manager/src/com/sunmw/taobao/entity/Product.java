package com.sunmw.taobao.entity;

import com.sunmw.taobao.entity.base.BaseProduct;



public class Product extends BaseProduct {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Product () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Product (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Product (
		java.lang.Integer id,
		java.lang.String action,
		java.lang.String publishErrorMsg) {

		super (
			id,
			action,
			publishErrorMsg);
	}

/*[CONSTRUCTOR MARKER END]*/


}