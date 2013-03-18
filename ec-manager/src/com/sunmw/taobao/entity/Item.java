package com.sunmw.taobao.entity;

import com.sunmw.taobao.entity.base.BaseItem;



public class Item extends BaseItem {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Item () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Item (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Item (
		java.lang.Long id,
		java.lang.String title,
		java.lang.String type,
		java.lang.Long cid,
		java.lang.String saleProps,
		java.lang.String inputPids,
		java.lang.String inputStr,
		java.lang.String picUrl,
		java.lang.Long num,
		java.lang.Long validThru,
		java.lang.String stuffStatus,
		java.lang.Integer productId) {

		super (
			id,
			title,
			type,
			cid,
			saleProps,
			inputPids,
			inputStr,
			picUrl,
			num,
			validThru,
			stuffStatus,
			productId);
	}

/*[CONSTRUCTOR MARKER END]*/


}