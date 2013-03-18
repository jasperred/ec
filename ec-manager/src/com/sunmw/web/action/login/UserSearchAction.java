package com.sunmw.web.action.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.login.UserServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class UserSearchAction {
	private UserServices userServices;
	private int userId;
	private String userName;
	private String email;
	private String userNo;
	private String enabled;
	private int companyId;
	private String companyName;
	private String tecNo;

	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数

	private List<Map> resultList;// 查询结果

	private String isNext = "TRUE";// 是否有下一页

	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getTecNo() {
		return tecNo;
	}

	public void setTecNo(String tecNo) {
		this.tecNo = tecNo;
	}


	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map> resultList) {
		this.resultList = resultList;
	}

	public String getIsNext() {
		return isNext;
	}

	public void setIsNext(String isNext) {
		this.isNext = isNext;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String searchUser() {
		try {
			Map param = new HashMap();
			if (userId>0)
				param.put("UserId", userId);
			if (!WebUtil.isNull(userName))
				param.put("UserName", WebUtil.filterSpecialCharacters(userName));
			if (!WebUtil.isNull(userNo))
				param.put("UserNo", WebUtil.filterSpecialCharacters(userNo));
			if (this.companyId>0)
				param.put("CompanyId", companyId);
			if (!WebUtil.isNull(companyName))
					param.put("CompanyName", WebUtil.filterSpecialCharacters(companyName));
			if (!WebUtil.isNull(email))
				param.put("Email", WebUtil.filterSpecialCharacters(email));
			if (!WebUtil.isNull(enabled))
				param.put("Enabled", WebUtil.filterSpecialCharacters(enabled));
			if (!WebUtil.isNull(tecNo))
				param.put("TecNo", WebUtil.filterSpecialCharacters(tecNo));
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			param.put("UserLogin", ul);
			Map r = this.userServices.searchUser(param, page, limit);
			this.rowCount = (Integer) r.get("COUNT_ROW");
			this.resultList = (List) r.get("RESULT");
			countPage();
		} catch (Exception e) {
			if(this.resultList!=null)
				this.resultList.clear();
			this.rowCount = 0;
		}
		return "success";
	}

	private void countPage() {
		if (rowCount % this.limit == 0)
			pageCount = rowCount / this.limit;
		else
			pageCount = rowCount / this.limit + 1;// 总页数
		if (page < pageCount)
			isNext = "true";
		else
			isNext = "false";
		if (page > 1)
			isBack = "true";
		else
			isBack = "false";
	}

}
