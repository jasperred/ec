package com.sunmw.web.entity;

import com.sunmw.web.entity.base.BaseOrderNotePK;

public class OrderNotePK extends BaseOrderNotePK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public OrderNotePK () {}
	
	public OrderNotePK (
		java.lang.String noteType,
		java.lang.Integer orderHeadId) {

		super (
			noteType,
			orderHeadId);
	}
/*[CONSTRUCTOR MARKER END]*/


}