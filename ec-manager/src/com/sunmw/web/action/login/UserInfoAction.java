package com.sunmw.web.action.login;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.login.UserServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class UserInfoAction {

	private UserServices userServices;
	private int userId;
	private String userNo;
	private String userName;
	private int companyId;
	private String companyNo;
	private String companyName;
	private String companyStatus;
	private String email;
	private String enabled;
	private String password;
	private String newPassword;
	private String tecNo;
	private String userType;
	private String message;
	private boolean success;
	private String crumb;

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public String getTecNo() {
		return tecNo;
	}

	public void setTecNo(String tecNo) {
		this.tecNo = tecNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String updateUserInfo() {
		try {

			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map m = new HashMap();
			m.put("UserId", userId);
			m.put("Email", WebUtil.filterSpecialCharacters(email));
			m.put("UserName", WebUtil.filterSpecialCharacters(userName));
			m.put("Enabled", WebUtil.filterSpecialCharacters(enabled));
			if(companyId==0)
				companyId = ul.getCompanyId();
			m.put("CompanyId", companyId);
			m.put("TecNo", WebUtil.filterSpecialCharacters(tecNo));
			String userNo = ul.getUserNo();
			if (crumb == null || !VerifyRequest.verifyCrumb(userNo, crumb)) {
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			m.put("UserLogin", ul);
			Map<String, String> r = userServices.updateUserLogin(m);
			String flag = r.get("FLAG");
			if (flag.equals("SUCCESS")) {

				message = r.get("UserId");
				success = true;
				return "success";
			} else {
				message = "操作失败";
				success = false;
				return "success";
			}
		} catch (Exception e) {
			message = e.getMessage();
			success = false;
			return "success";
		}
	}

	public String getUserInfo() {
		try {
			if (userId <= 0) {
				userNo = null;
				userName = null;
				email = null;
				companyId = -1;
				companyNo = null;
				companyName = null;
				companyStatus = null;
				tecNo = null;
				userType = null;
				return "success";
			}
			Map session = ActionContext.getContext().getSession();
			UserLogin ul2 = (UserLogin) session.get("LOGIN_INFO");
			Map m = this.userServices.getUserInfo(userId);
			if (m != null) {
				this.email = WebUtil.replaceSpecialCharacters((String) m
						.get("Email"));
				this.userName = WebUtil.replaceSpecialCharacters((String) m
						.get("UserName"));
				this.enabled = WebUtil.replaceSpecialCharacters((String) m
						.get("Enabled"));
				this.companyId = (Integer) m.get("CompanyId");
				this.companyNo = WebUtil.replaceSpecialCharacters((String) m
						.get("CompanyNo"));
				this.companyName = WebUtil.replaceSpecialCharacters((String) m
						.get("CompanyName"));
				this.companyStatus = WebUtil
						.replaceSpecialCharacters((String) m
								.get("CompanyStatus"));
				this.userNo = WebUtil.replaceSpecialCharacters((String) m
						.get("UserNo"));
				this.tecNo = WebUtil.replaceSpecialCharacters((String) m
						.get("TecNo"));
				this.userType = ul2.getUserType();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	public String newUser() {
		try {
			Map m = new HashMap();
			m.put("UserName", WebUtil.filterSpecialCharacters(userName));
			m.put("Email", WebUtil.filterSpecialCharacters(email));
			m.put("UserNo", WebUtil.filterSpecialCharacters(userNo));
			m.put("CompanyId", companyId);
			if (WebUtil.isNotNull(enabled))
				m.put("Enabled", WebUtil
						.filterSpecialCharacters(enabled.trim()));
			m.put("password", password);
			m.put("TecNo", WebUtil.filterSpecialCharacters(tecNo));
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			String userNo = ul.getUserNo();
			if (crumb == null || !VerifyRequest.verifyCrumb(userNo, crumb)) {
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			Map<String, String> r = userServices.createUserLogin(m);
			String flag = r.get("FLAG");
			if (flag.equals("SUCCESS")) {

				message = r.get("UserId");
				success = true;
				return "success";
			} else {
				message = "操作失败";
				success = false;
				return "success";
			}
		} catch (Exception e) {
			message = e.getMessage();
			success = false;
			return "success";
		}
	}

	public String modifyPassword() {
		try {
			Map m = new HashMap();
			m.put("UserId", userId);
			m.put("NewPassword", newPassword);
			m.put("Password", password);
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map<String, String> r = userServices.modifyPassowd(m);
			String flag = r.get("FLAG");
			if (flag.equals("SUCCESS")) {

				message = null;
				success = true;
				return "success";
			} else {
				message = r.get("MESSAGE");
				success = false;
				return "success";
			}
		} catch (Exception e) {
			message = e.getMessage();
			success = false;
			return "success";
		}
	}

	// 用户修改密码
	public String modifyPasswordByUser() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map m = new HashMap();
			m.put("UserId", ul.getId());
			m.put("NewPassword", newPassword);
			m.put("Password", password);
			Map<String, String> r = userServices.modifyPassowd(m);

			message = r.get("MESSAGE");
			if (r.get("FLAG").equals("SUCCESS")) {
				message = null;
				success = true;
				session.put("MODIFY_PWD", "N");
			} else {
				message = "操作错误";
				success = false;
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}

	// 用户修改密码
	public String modifyPasswordByUserNo() {
		try {
			Map m = new HashMap();
			m.put("CompanyNo", companyNo);
			m.put("UserNo", userNo);
			m.put("NewPassword", newPassword);
			m.put("Password", password);
			Map<String, String> r = userServices.modifyPassowdByUserNo(m);

			message = r.get("MESSAGE");
			if (r.get("FLAG").equals("SUCCESS")) {
				message = null;
				success = true;
			} else {
				message = "操作错误";
				success = false;
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}

	public String resetPassword() {

		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map m = new HashMap();
			m.put("UserId", userId);
			m.put("Password", WebConfigProperties
					.getProperties("web.password.reset"));
			Map<String, String> r = userServices.resetPassowd(m);

			message = r.get("MESSAGE");
			if (r.get("FLAG").equals("SUCCESS")) {
				message = null;
				success = true;
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";

	}

	// 得到登录用户信息
	public String getUserLoginInfo() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			this.email = WebUtil.replaceSpecialCharacters(ul.getEmail());
			this.userName = WebUtil.replaceSpecialCharacters(ul.getUserName());
			this.enabled = WebUtil.replaceSpecialCharacters(ul.getEnabled());
			this.companyId = ul.getCompanyId();
			this.companyNo = null;
			this.companyName = null;
			this.companyStatus = null;
			this.userNo = WebUtil.replaceSpecialCharacters(ul.getUserNo());
			this.tecNo = WebUtil.replaceSpecialCharacters(ul.getTecNo());
			this.userType = ul.getUserType();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	public String viewModifyPassword() {
		message = null;
		return "success";
	}
}
