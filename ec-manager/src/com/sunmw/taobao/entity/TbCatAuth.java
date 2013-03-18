package com.sunmw.taobao.entity;

import com.sunmw.taobao.entity.base.BaseTbCatAuth;



public class TbCatAuth extends BaseTbCatAuth {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TbCatAuth () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TbCatAuth (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TbCatAuth (
		java.lang.String id,
		java.lang.Long cid) {

		super (
			id,
			cid);
	}

/*[CONSTRUCTOR MARKER END]*/


}