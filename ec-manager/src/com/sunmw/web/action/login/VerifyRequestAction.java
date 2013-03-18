package com.sunmw.web.action.login;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;

public class VerifyRequestAction {
	
	private String crumb;
	
	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public void genericVerify()
	{

		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin)session.get("LOGIN_INFO");
		if(ul==null)
			return;
		String userNo = ul.getUserNo();		
		crumb = VerifyRequest.issueCrumb(userNo);
	}

}
