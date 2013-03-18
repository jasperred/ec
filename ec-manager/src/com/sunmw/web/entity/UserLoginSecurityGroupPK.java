package com.sunmw.web.entity;

import com.sunmw.web.entity.base.BaseUserLoginSecurityGroupPK;

public class UserLoginSecurityGroupPK extends BaseUserLoginSecurityGroupPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public UserLoginSecurityGroupPK () {}
	
	public UserLoginSecurityGroupPK (
		java.lang.String groupId,
		java.lang.Integer userId,
		java.util.Date fromDate) {

		super (
			groupId,
			userId,
			fromDate);
	}
/*[CONSTRUCTOR MARKER END]*/


}