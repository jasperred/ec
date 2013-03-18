package com.sunmw.web.bean.address.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.address.AddressServices;
import com.sunmw.web.entity.UserAddress;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class AddressServicesImpl extends HibernateDaoSupport implements
		AddressServices {

	@Override
	public Map saveAddress(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("Flag", "ERROR");
			return result;
		}
		// 根据收货人、地址区分
		List<UserAddress> uaList = this
				.getHibernateTemplate()
				.find(
						"from UserAddress where Consignee = ? and Country = ? and Province = ? and City = ? and District = ? and Address = ?",
						new Object[] { param.get("Consignee"),
								param.get("Country"), param.get("Province"),
								param.get("City"), param.get("District"),
								param.get("Address") });
		if (WebUtil.isNullForList(uaList)) {
			UserAddress ua = new UserAddress();
			ua.setAddressName((String) param.get("AddressName"));
			ua.setUserId((Integer) param.get("UserId"));
			ua.setConsignee((String) param.get("Consignee"));
			ua.setEmail((String) param.get("Email"));
			ua.setCountry((String) param.get("Country"));
			ua.setProvince((String) param.get("Province"));
			ua.setCity((String) param.get("City"));
			ua.setDistrict((String) param.get("District"));
			ua.setAddress((String) param.get("Address"));
			ua.setZipcode((String) param.get("Zipcode"));
			ua.setTel((String) param.get("Tel"));
			ua.setMobile((String) param.get("Mobile"));
			ua.setUserId(0);
			Date date = new Date();
			ua.setCtime(date);
			ua.setCuser(ul.getUserName());
			ua.setMtime(date);
			ua.setMuser(ul.getUserName());
			this.getHibernateTemplate().save(ua);
		} else {
			UserAddress ua = uaList.get(0);
			ua.setEmail((String) param.get("Email"));
			ua.setZipcode((String) param.get("Zipcode"));
			ua.setTel((String) param.get("Tel"));
			ua.setMobile((String) param.get("Mobile"));
			ua.setMtime(new Date());
			ua.setMuser(ul.getUserName());
		}
		result.put("Flag", "ERROR");
		return result;
	}

	@Override
	public Map searchAddress(Map param) {
		Map result = new HashMap();
		StringBuffer hql = new StringBuffer(
				" from UserAddress");
		StringBuffer con = new StringBuffer();
		if (WebUtil.isNotNull(param.get("Consignee"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Consignee like '%" + param.get("Consignee") + "%'");
		}
		if (WebUtil.isNotNull(param.get("Email"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Email = '" + param.get("Email") + "'");
		}
		if (WebUtil.isNotNull(param.get("Tel"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Tel = '" + param.get("Tel") + "'");
		}
		if (WebUtil.isNotNull(param.get("Mobile"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Mobile = '" + param.get("Mobile") + "'");
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());
		hql.append(" order by Mtime desc");
		List<UserAddress> uaList = this.getHibernateTemplate().find(hql.toString());
		result.put("UserAddressList", uaList);
		return result;
	}

}
