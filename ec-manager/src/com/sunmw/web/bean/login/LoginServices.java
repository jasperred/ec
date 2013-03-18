package com.sunmw.web.bean.login;

import java.util.Map;

public interface LoginServices {
	public Map<String,String> loginByUserName(String userNo,String password,String companyNo);
}
