package com.sunmw.web.bean.item.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.item.GenerateItemCodeServices;
import com.sunmw.web.entity.ItemGenerateCode;
import com.sunmw.web.entity.ItemGenerateType;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class GenerateItemCodeServicesImpl extends HibernateDaoSupport implements
		GenerateItemCodeServices {

	@Override
	// 此方法只能单线程使用
	public Map generateItemCode(Map param) {
		Map result = new HashMap();
		List messageList = new ArrayList();
		List<Map> itemList = (List) param.get("itemList");
		if (WebUtil.isNullForList(itemList)) {
			messageList.add("没有内容");
			result.put("messageList", messageList);
			return result;
		}

		// 得到附属信息
		List<ItemGenerateType> typeList = this.getHibernateTemplate().find(
				"from ItemGenerateType");
		if (WebUtil.isNull(typeList)) {
			messageList.add("没有类型信息");
			result.put("messageList", messageList);
			return result;
		}
		Map<String, Map> typeMap = new HashMap();
		for (ItemGenerateType t : typeList) {
			Map<String, String> tm = typeMap.get(t.getType());
			if (tm == null)
				tm = new HashMap();
			if (WebUtil.isNotNull(t.getParentPropName()))
				tm.put(t.getParentPropName() + t.getPropName(), t
						.getPropValue());
			else
				tm.put(t.getPropName(), t.getPropValue());
			typeMap.put(t.getType(), tm);
		}
		// 默认规则
		String[] rule = new String[] { "品牌", "子系列", "-", "年", "季节", "-", "性别",
				"细分" };
		// 检查类型是否完全
		for (String r : rule) {
			if (typeMap.get(r) == null && !r.equals("-")) {
				messageList.add("【" + r + "】没有类型信息");
				result.put("messageList", messageList);
				return result;
			}
		}
		messageList.add("开始导入，总记录" + itemList.size() + "条");
		Map<String, Integer> yearMap = new HashMap();// 按年保存序列号
		int r = 0;// 成功条数
		for (Map m : itemList) {
			// 检查数据完整性
			if (!checkItemInfo(m, rule, messageList))
				continue;
			// 检查编码是否已生成
			ItemGenerateCode item = new ItemGenerateCode();
			if (WebUtil.isNotNull(m.get("品牌")))
				item.setBrand(m.get("品牌").toString());
			if (WebUtil.isNotNull(m.get("系列")))
				item.setCat(m.get("系列").toString());
			if (WebUtil.isNotNull(m.get("子系列")))
				item.setSubCat(m.get("子系列").toString());
			if (WebUtil.isNotNull(m.get("季节")))
				item.setSeason(m.get("季节").toString());
			if (WebUtil.isNotNull(m.get("年")))
				item.setYear(m.get("年").toString());
			if (WebUtil.isNotNull(m.get("性别")))
				item.setSex(m.get("性别").toString());
			if (WebUtil.isNotNull(m.get("部门")))
				item.setDept(m.get("部门").toString());
			if (WebUtil.isNotNull(m.get("细分")))
				item.setDetail(m.get("细分").toString());
			List<ItemGenerateCode> il = this.getHibernateTemplate()
					.findByExample(item);
			if (!WebUtil.isNullForList(il)) {
				messageList.add("第" + m.get("ROW") + "行数据已经生成过编码");
				continue;
			}
			// 得到最大的序列号
			Integer serial = yearMap.get(m.get("年"));
			if (serial == null) {
				serial = getItemSerial((String) m.get("年"));
				yearMap.put((String) m.get("年"), serial);
			} else {
				serial++;
				yearMap.put((String) m.get("年"), serial);
			}
			// 根据规则生成商品编码
			item.setCtime(new Date());
			item.setSerial(serial);
			String code = generateCode(m, typeMap, rule, serial, messageList);
			if (code == null) {
				continue;
			}
			item.setItemCode(code);
			this.getHibernateTemplate().save(item);
			serial++;
			r++;
		}
		messageList.add("导入结束，成功执行" + r + "条");
		result.put("messageList", messageList);
		return result;
	}

	private boolean checkItemInfo(Map info, String[] rule, List messageList) {
		for (String r : rule) {
			if (WebUtil.isNull(info.get(r)) && !r.equals("-")) {
				messageList.add("第" + info.get("ROW") + "行数据【" + info.get(r)
						+ "】没填写");
				return false;
			}
		}
		return true;
	}

	private String generateCode(Map info, Map<String, Map> type, String[] rule,
			int serial, List messageList) {
		StringBuffer code = new StringBuffer();
		for (int i = 0; i < rule.length; i++) {
			String r = rule[i];
			if (r.equals("-")) {
				code.append("-");
				continue;
			}
			Map<String, String> t = type.get(r);
			String c = t.get(info.get(r)) == null ? t.get((String) info
					.get(rule[i - 1])
					+ info.get(r)) : t.get(info.get(r));
			if (WebUtil.isNull(c)) {
				messageList.add("第" + info.get("ROW") + "行数据【" + r
						+ "】没有匹配的类型【" + info.get(r) + "】");
				return null;
			}
			code.append(c);
		}
		code.append(WebUtil.addPrefix("" + serial, 3, "0"));
		return code.toString();
	}

	@Override
	public Map searchGenerateItem(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		String fields = "";
		StringBuffer hql = new StringBuffer(" from ItemGenerateCode");
		StringBuffer con = new StringBuffer();
		if (WebUtil.isNotNull(param.get("brand"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Brand like '%" + param.get("brand") + "%'");
		}
		if (WebUtil.isNotNull(param.get("cat"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Cat like '%" + param.get("cat") + "%'");
		}
		if (WebUtil.isNotNull(param.get("subCat"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" SubCat like '%" + param.get("subCat") + "%'");
		}
		if (WebUtil.isNotNull(param.get("year"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Year like '%" + param.get("year") + "%'");
		}
		if (WebUtil.isNotNull(param.get("season"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Season like '%" + param.get("season") + "%'");
		}
		if (WebUtil.isNotNull(param.get("sex"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Sex like '%" + param.get("sex") + "%'");
		}
		if (WebUtil.isNotNull(param.get("dept"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Dept like '%" + param.get("dept") + "%'");
		}
		if (WebUtil.isNotNull(param.get("detail"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Detail like '%" + param.get("detail") + "%'");
		}
		if (WebUtil.isNotNull(param.get("sel"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Sel = '" + param.get("sel") + "'");
		}
		if (WebUtil.isNotNull(param.get("fromDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Ctime >= '" + param.get("fromDate") + "'");
		}
		if (WebUtil.isNotNull(param.get("endDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Ctime < '" + param.get("endDate") + "'");
		}
		if (con.length() > 0)
			hql.append(" where " + con.toString());
		hql.append(" order by Ctime");
		List<Long> countList = this.getHibernateTemplate().find(
				"select count(*) " + hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		if (pageRow > 0) {
			q.setFirstResult((currentPage - 1) * pageRow);
			q.setMaxResults(pageRow);
		}
		List<ItemGenerateCode> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if (l != null && l.size() > 0) {
			//Map tm = getGenerateItemType();
			for (ItemGenerateCode o : l) {
				Map r = new HashMap();
				r.put("itemId", o.getId());
				r.put("brand", o.getBrand());
				//r.put("brandName", tm.get("品牌" + o.getBrand()));
				r.put("cat", o.getCat());
				//r.put("catName", tm.get("系列" + o.getBrand()));
				r.put("subCat", o.getSubCat());
				//r.put("subCatName", tm.get("子系列" + o.getBrand()));
				r.put("year", o.getYear());
				//r.put("yearName", tm.get("年" + o.getBrand()));
				r.put("season", o.getSeason());
				//r.put("seasonName", tm.get("季节" + o.getBrand()));
				r.put("sex", o.getSex());
				//r.put("sexName", tm.get("性别" + o.getBrand()));
				r.put("dept", o.getDept());
				//r.put("deptName", tm.get("部门" + o.getBrand()));
				r.put("detail", o.getDetail());
				//r.put("detailName", tm.get("细分" + o.getBrand()));
				r.put("serial", o.getSerial() != null ? WebUtil.addPrefix(o
						.getSerial().toString(), 3, "0") : "");
				r.put("itemCode", o.getItemCode());
				r.put("itemName", o.getItemName());
				r.put("sel", o.getSel());
				r.put("ctime", WebUtil.formatDateString(o.getCtime(),
						"yyyy-MM-dd HH:mm:ss"));
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	private Map getGenerateItemType() {
		Map r = new HashMap();
		List<ItemGenerateType> tl = this.getHibernateTemplate().find(
				"from ItemGenerateType");
		if (!WebUtil.isNullForList(tl)) {
			for (ItemGenerateType t : tl) {
				r.put(t.getType() + t.getPropValue(), t.getPropName());
			}
		}
		return r;
	}

	@Override
	public Map exportGenerateItem(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		String fields = "";
		StringBuffer hql = new StringBuffer(" from ItemGenerateCode");
		StringBuffer con = new StringBuffer();
		if (WebUtil.isNotNull(param.get("brand"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Brand like '%" + param.get("brand") + "%'");
		}
		if (WebUtil.isNotNull(param.get("cat"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Cat like '%" + param.get("cat") + "%'");
		}
		if (WebUtil.isNotNull(param.get("subCat"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" SubCat like '%" + param.get("subCat") + "%'");
		}
		if (WebUtil.isNotNull(param.get("year"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Year like '%" + param.get("year") + "%'");
		}
		if (WebUtil.isNotNull(param.get("season"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Season like '%" + param.get("season") + "%'");
		}
		if (WebUtil.isNotNull(param.get("sex"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Sex like '%" + param.get("sex") + "%'");
		}
		if (WebUtil.isNotNull(param.get("dept"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Dept like '%" + param.get("dept") + "%'");
		}
		if (WebUtil.isNotNull(param.get("detail"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Detail like '%" + param.get("detail") + "%'");
		}
		if (WebUtil.isNotNull(param.get("fromDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Ctime >= '" + param.get("fromDate") + "'");
		}
		if (WebUtil.isNotNull(param.get("endDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Ctime < '" + param.get("endDate") + "'");
		}
		if (WebUtil.isNotNull(param.get("sel"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" Sel = '" + param.get("sel") + "'");
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());
		hql.append(" order by Ctime");
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		List<ItemGenerateCode> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if (l != null && l.size() > 0) {
			Map tm = getGenerateItemType();
			// Map storeMap = getStoreMap();
			for (ItemGenerateCode o : l) {
				Map r = new HashMap();
				r.put("itemId", o.getId());
				r.put("brand", o.getBrand());
				r.put("brandName", tm.get("品牌" + o.getBrand()));
				r.put("cat", o.getCat());
				r.put("catName", tm.get("系列" + o.getBrand()));
				r.put("subCat", o.getSubCat());
				r.put("subCatName", tm.get("子系列" + o.getBrand()));
				r.put("year", o.getYear());
				r.put("yearName", tm.get("年" + o.getBrand()));
				r.put("season", o.getSeason());
				r.put("seasonName", tm.get("季节" + o.getBrand()));
				r.put("sex", o.getSex());
				r.put("sexName", tm.get("性别" + o.getBrand()));
				r.put("dept", o.getDept());
				r.put("deptName", tm.get("部门" + o.getBrand()));
				r.put("detail", o.getDetail());
				r.put("detailName", tm.get("细分" + o.getBrand()));
				r.put("serial", o.getSerial() != null ? WebUtil.addPrefix(o
						.getSerial().toString(), 3, "0") : "");
				r.put("itemCode", o.getItemCode());
				r.put("itemName", o.getItemName());
				r.put("ctime", WebUtil.formatDateString(o.getCtime(),
						"yyyy-MM-dd HH:mm:ss"));
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		session.close();
		return result;
	}

	@Override
	public Map deleteGenerateType(Map param) {
		Map result = new HashMap();
		Integer typeId = (Integer) param.get("typeId");
		if (typeId == null) {
			result.put("Flag", "error");
			result.put("Message", "id is null");
			return result;
		}
		ItemGenerateType t = new ItemGenerateType(typeId);
		this.getHibernateTemplate().delete(t);
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map getGenerateType(Map param) {
		Map result = new HashMap();
		String hql = "from ItemGenerateType ";
		StringBuffer con = new StringBuffer();
		if (WebUtil.isNotNull(param.get("type"))) {
			con.append("Type = '" + param.get("type") + "'");
		}
		if (WebUtil.isNotNull(param.get("name"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append("PropName like '%" + param.get("name") + "%'");
		}
		if (WebUtil.isNotNull(param.get("parent"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append("ParentPropName = '" + param.get("parent") + "'");
		}
		if (con.length() > 0)
			hql = hql + " where " + con.toString();
		List<ItemGenerateType> tl = this.getHibernateTemplate().find(hql);
		List<Map> l = new ArrayList();
		if (!WebUtil.isNullForList(tl)) {
			for (ItemGenerateType t : tl) {
				Map m = new HashMap();
				m.put("typeId", t.getId());
				m.put("name", t.getPropName());
				m.put("value", t.getPropValue());
				m.put("type", t.getType());
				m.put("parent", t.getParentPropName());
				l.add(m);
			}
		}
		result.put("typeList", l);
		return result;
	}

	@Override
	public Map saveGenerateType(Map param) {
		Map result = new HashMap();
		ItemGenerateType t = new ItemGenerateType();
		if (WebUtil.isNotNull(param.get("typeId")))
			t.setId((Integer) param.get("typeId"));
		if (WebUtil.isNotNull(param.get("name")))
			t.setPropName((String) param.get("name"));
		if (WebUtil.isNotNull(param.get("value")))
			t.setPropValue((String) param.get("value"));
		if (WebUtil.isNotNull(param.get("parent")))
			t.setParentPropName((String) param.get("parent"));
		if (WebUtil.isNotNull(param.get("type")))
			t.setType((String) param.get("type"));
		if (t.getId() == null)
			this.getHibernateTemplate().save(t);
		else
			this.getHibernateTemplate().update(t);
		result.put("typeId", t.getId());
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map saveItemCode(Map param) {
		Map result = new HashMap();
		// 商品已存在，只保存是否选用
		if (WebUtil.isNotNull(param.get("itemId"))) {
			this.getHibernateTemplate().bulkUpdate(
					"update ItemGenerateCode set Sel = '"
							+ param.get("sel") + "',ItemName = '"+param.get("itemName")+"' where id = "
							+ param.get("itemId"));
			result.put("itemId", param.get("itemId"));
		} else {
			ItemGenerateCode item = new ItemGenerateCode();
			item.setBrand((String) param.get("brand"));
			item.setCat((String) param.get("cat"));
			item.setSubCat((String) param.get("subCat"));
			item.setSeason((String) param.get("season"));
			item.setYear((String) param.get("year"));
			item.setSex((String) param.get("sex"));
			item.setDept((String) param.get("dept"));
			item.setDetail((String) param.get("detail"));
			item.setSel((String) param.get("sel"));
			item.setItemName((String) param.get("itemName"));
			// 按年查询序列号，无记录为000
			Integer serial = getItemSerial((String) param.get("year"));
			item.setSerial(serial);
			// 生成编码
			StringBuffer itemCode = new StringBuffer();
			itemCode.append(param.get("brandCode"));
			itemCode.append(param.get("subCatCode"));
			itemCode.append("-");
			itemCode.append(param.get("yearCode"));
			itemCode.append(param.get("seasonCode"));
			itemCode.append("-");
			itemCode.append(param.get("sexCode"));
			itemCode.append(param.get("detailCode"));
			itemCode.append(WebUtil.addPrefix("" + serial, 3, "0"));
			item.setItemCode(itemCode.toString());
			item.setCtime(new Date());
			this.getHibernateTemplate().save(item);
			result.put("itemId",item.getId());
		}
		result.put("Flag", "success");
		return result;
	}

	public Integer getItemSerial(String year) {
		Integer serial;
		List<Integer> sl = this.getHibernateTemplate().find(
				"select max(Serial) from ItemGenerateCode where Year = ?",
				year);
		if (WebUtil.isNullForList(sl)) {
			serial = 0;
		} else {
			serial = sl.get(0) + 1;
		}
		return serial;
	}

	@Override
	public Map saveItemImage(Map param) {
		Map result = new HashMap();

		if (WebUtil.isNull(param.get("itemId"))) {
			result.put("Flag", "error");
			result.put("Message", "itemId is null");
			return result;

		}
		this.getHibernateTemplate().bulkUpdate(
				"update ItemGenerateCode set ImageUrl = '"
						+ param.get("imageUrl") + "' where id = "
						+ param.get("itemId"));
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map getItemInfo(Map param) {
		Map result = new HashMap();

		if (WebUtil.isNull(param.get("itemId"))) {
			result.put("Flag", "error");
			result.put("Message", "itemId is null");
			return result;

		}
		List<ItemGenerateCode> itemList = this.getHibernateTemplate().find("from ItemGenerateCode where id = ?",param.get("itemId"));
		if(WebUtil.isNullForList(itemList))
		{
			result.put("Flag", "error");
			result.put("Message", "没找到商品");
			return result;
		}
		ItemGenerateCode item = itemList.get(0);
		result.put("itemId", item.getId());
		result.put("brand", item.getBrand());
		result.put("cat", item.getCat());
		result.put("subCat", item.getSubCat());
		result.put("year", item.getYear());
		result.put("season", item.getSeason());
		result.put("sex", item.getSex());
		result.put("dept", item.getDept());
		result.put("detail", item.getDetail());
		result.put("serial", item.getSerial() != null ? WebUtil.addPrefix(item
				.getSerial().toString(), 3, "0") : "");
		result.put("itemCode", item.getItemCode());
		result.put("itemName", item.getItemName());
		result.put("sel", item.getSel());
		result.put("imageUrl", item.getImageUrl());
		result.put("Flag", "success");
		return result;
	}

}
