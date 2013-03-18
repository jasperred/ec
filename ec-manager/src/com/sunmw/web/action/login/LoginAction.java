package com.sunmw.web.action.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.login.LoginServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.JsonUtil;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class LoginAction {

	private String companyNo;
	private String userName;
	private String password;
	private String rand;
	private String message;
	private boolean success;

	private LoginServices loginServices;

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public LoginServices getLoginServices() {
		return loginServices;
	}

	public void setLoginServices(LoginServices loginServices) {
		this.loginServices = loginServices;
	}

	// 用户登录
	public String login() {
		//Map jsonMap = new HashMap();
		try {
			Map session = ActionContext.getContext().getSession();
			// 取得session保存中的字符串
			String arandom = (String) (session.get("random"));
			if (arandom == null || rand == null) {
				message = "验证码错误";
				success = false;
				return "success";
			}
			if (!arandom.equals(rand)) {
				message = "验证码错误";
				success = false;
				return "success";
			}
			// 密码加密
			String hashType = WebConfigProperties
					.getProperties("web.password.hashtype");
			if (hashType == null || hashType.trim().length() == 0)
				hashType = "SHA";
			String p = WebUtil.getDigestHash(password, hashType);
			// 登录
			Map m = loginServices.loginByUserName(userName, p, companyNo);
			String r = (String) m.get("LOGIN");
			if ("ERROR".equals(r)) {
				// message = (String) m.get("LOGIN_MASSAGE");
				message = "登录信息错误";
				success = false;
				return "success";
			}
			// 重建SESSION,处理会话固定的问题
			HttpServletRequest httpRequest = ServletActionContext.getRequest();
			HttpSession httpSession = httpRequest.getSession();
			if (httpSession != null) {
				httpSession.invalidate();
				httpRequest.getSession(true);
			}
			SessionMap sessionMap = new SessionMap(httpRequest);
			ActionContext.getContext().setSession(sessionMap);
			session = ActionContext.getContext().getSession();
			message = null;
			UserLogin ul = (UserLogin) m.get("LOGIN_INFO");

			session.put("LOGIN_INFO", m.get("LOGIN_INFO"));
			session.put("COMPANY_INFO", m.get("COMPANY_INFO"));
			// 判断是否需要修改密码
			String isModifyPwd = WebConfigProperties
					.getProperties("web.password.modify");
			if (WebUtil.isNotNull(isModifyPwd)
					&& isModifyPwd.trim().equals("Y")) {
				String start = WebConfigProperties
						.getProperties("web.password.modify.start");
				String day = WebConfigProperties
						.getProperties("web.password.modify.day");
				Date lastTime = ul.getPasswordModifyTime();
				// 如果用户的最后密码修改时间为空则必须修改密码
				if (lastTime == null) {
					session.put("MODIFY_PWD", "Y");
					message = "MODIFY_PWD";
					//success = true;
					//return "success";
				}
				if (WebUtil.isNull(start) || WebUtil.isNull(day))
					session.put("MODIFY_PWD", "N");
				else {
					Date date = WebUtil.toDateForString(WebUtil
							.formatDateString(new Date(), "yyyy-MM-dd"),
							"yyyy-MM-dd");
					Date startDate = WebUtil.toDateForString(start,
							"yyyy-MM-dd");
					long time = (date.getTime() - startDate.getTime())
							/ (1000 * 60 * 60 * 24);// 距离开始时间经过的天数
					long dd = new Long(day);// 周期天数
					long pp = time / dd;// 经过的周期
					// 如果密码修改时间大于等于开始时间加周期时间则不需要修改密码
					if (lastTime.getTime() >= startDate.getTime() + pp * dd) {
						session.put("MODIFY_PWD", "N");
					} else {
						session.put("MODIFY_PWD", "Y");
						message = "MODIFY_PWD";
					}
				}
			}
		} catch (NumberFormatException e) {
			message = e.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		success = true;
		return "success";
	}

	// 用户退出
	public String logout() {
		try {
			Map session = ActionContext.getContext().getSession();
			session.remove("LOGIN_INFO");
			session.remove("MODIFY_PWD");
			session.remove("COMPANY_INFO");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	public String index() {
		Map session = ActionContext.getContext().getSession();
		if (session.get("LOGIN_INFO") != null)
			return "success";
		else
			return "login";
	}
	
	public String noAction()
	{
		return "success";
	}

}
