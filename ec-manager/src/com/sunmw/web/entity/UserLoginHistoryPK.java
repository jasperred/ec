package com.sunmw.web.entity;

import com.sunmw.web.entity.base.BaseUserLoginHistoryPK;

public class UserLoginHistoryPK extends BaseUserLoginHistoryPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public UserLoginHistoryPK () {}
	
	public UserLoginHistoryPK (
		java.lang.Integer userId,
		java.util.Date fromDate) {

		super (
			userId,
			fromDate);
	}
/*[CONSTRUCTOR MARKER END]*/


}