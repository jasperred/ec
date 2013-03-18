package com.sunmw.web.entity;

import com.sunmw.web.entity.base.BaseSecurityGroupPermissionPK;

public class SecurityGroupPermissionPK extends BaseSecurityGroupPermissionPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SecurityGroupPermissionPK () {}
	
	public SecurityGroupPermissionPK (
		java.lang.String groupId,
		java.util.Date fromDate,
		java.lang.String permissionId) {

		super (
			groupId,
			fromDate,
			permissionId);
	}
/*[CONSTRUCTOR MARKER END]*/


}