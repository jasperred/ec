package com.sunmw.web.bean.inventory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.entity.InvRule;
import com.sunmw.web.entity.Inventory;
import com.sunmw.web.entity.InventoryLog;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UnitSkuMaster;
import com.sunmw.web.entity.Warehouse;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class InventoryServicesImpl extends HibernateDaoSupport implements
		InventoryServices {
	static Logger logger = Logger.getLogger(InventoryServicesImpl.class);

	public void clearInventory(int storeId) {
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		session.createQuery(
				"delete from Inventory where WhId = '" + storeId + "'")
				.executeUpdate();
		session.createQuery(
				"delete from InventoryLog where WhId = '" + storeId + "'")
				.executeUpdate();
		tx.commit();
		session.close();
	}

	// 无库位情况,不考虑1个商品在同一仓库有2个库存情况
	public Map inventoryProcess(Map param) {
		Map result = new HashMap();
		try{
		List<Map> invList = (List) param.get("invList");
		String allowNegativeInv = (String) param.get("allowNegativeInv");
		// 默认不允许
		if (WebUtil.isNull(allowNegativeInv))
			allowNegativeInv = "N";
		//Session session = this.getSession();
		if (WebUtil.isNullForList(invList)) {
			result.put("Flag", "ERROR");
			result.put("Message", "无库存信息");
			return result;
		}
		// 日志类型库存变动来源
		String logType = (String) param.get("LogType");
		// 库存变动类型I-入库,O-出库,R-替换
		String ioType = (String) param.get("IoType");
		//Transaction tx = session.beginTransaction();
		List<Warehouse> wl = this.getHibernateTemplate().find(
				"from Warehouse where Status = 'ACTIVE'");
		Map<String, Warehouse> whMap = new HashMap();
		if (!WebUtil.isNullForList(wl)) {
			for (Warehouse w : wl) {
				whMap.put(w.getWhCode(), w);
			}
		}
		Date date = new Date();
		for (Map m : invList) {
			Integer whId = (Integer)m.get("WhId");
			String whCode = (String) m.get("WhCode");
			Warehouse w = whMap.get(whCode);
			if(whId==null&&w!=null)
				whId = whMap.get(whCode).getId();
			if(w!=null&&WebUtil.isNotNull(w.getAllowNegativeInv()))
				allowNegativeInv = w.getAllowNegativeInv();
			String skuCd = (String) m.get("SkuCd");
			if (whId == null) {
				logger.error("SKU[" + skuCd + "]未找到对应的仓库[" + whCode + "]");
				continue;
			}
			Integer qty = 0;
			if(WebUtil.isNotNull(m.get("Qty")))
				qty = new Integer(m.get("Qty").toString());
			// 库存类型A、B、C品
			String invType = (String) m.get("InvType");
			Inventory inv = null;
			Integer invId = null;
			// 查询库存,必须是A品
			List<Inventory> localInvList = this.getHibernateTemplate().find(
					"from Inventory where InvStatus = 'ACTIVE' and WhId = '"
							+ whId + "' and SkuCd = '" + skuCd
							+ "' and InvType = 'A'");
			// 无库存
			if (WebUtil.isNullForList(localInvList)) {
				inv = new Inventory();
				inv.setCtime(date);
				inv.setInvStatus("ACTIVE");
				inv.setInvType(invType);
				inv.setSkuCd(skuCd);
				inv.setWhId(whId);
				// 入库和替换
				if (ioType.equals("I") || ioType.equals("R")) {
					inv.setQuantity(new BigDecimal(qty));
					inv.setAvailableQuantity(new BigDecimal(qty));
					if (ioType.equals("R")) {

						inv.setUpdateFlag("N");
						inv.setUpdateTime(new Date());
						inv.setLocalQty(new BigDecimal(qty));
					}
				}
				// 出库
				else if (ioType.equals("O")) {
					// 不允许负库存
					if (allowNegativeInv.equals("N")) {
						result.put("FLAG", "ERROR");
						result.put("MESSAGE", "库存数量不足");
						logger.error("仓库[" + whId + "]商品[" + skuCd + "]库存数量不足");
						//tx.rollback();
						//session.close();
						return result;
					}
					inv.setAvailableQuantity(new BigDecimal(0 - qty));
					inv.setQuantity(new BigDecimal(0 - qty));
				}
				this.getHibernateTemplate().save(inv);
			}
			// 有库存
			else {
				inv = localInvList.get(0);
				inv.setMtime(date);
				// 入库
				if (ioType.equals("I")) {
					inv.setAvailableQuantity(new BigDecimal(inv
							.getAvailableQuantity().intValue()
							+ qty));
					inv.setQuantity(new BigDecimal(inv.getQuantity().intValue()
							+ qty));
				}
				// 替换
				else if (ioType.equals("R")) {
					// 暂时未考虑可用库存不足的情况
					inv.setQuantity(new BigDecimal(qty));
					inv.setAvailableQuantity(new BigDecimal(qty));
					inv.setUpdateFlag("N");
					inv.setUpdateTime(new Date());
					inv.setLocalQty(new BigDecimal(qty));
				}
				// 出库
				else if (ioType.equals("O")) {
					// 库存足够
					if (inv.getAvailableQuantity().intValue() >= qty) {
						inv.setAvailableQuantity(new BigDecimal(inv
								.getAvailableQuantity().intValue()
								- qty));
						inv.setQuantity(new BigDecimal(inv.getQuantity()
								.intValue()
								- qty));
					}
					// 库存不足
					else {
						// 不允许负库存
						if (allowNegativeInv.equals("N")) {
							result.put("FLAG", "ERROR");
							result.put("MESSAGE", "库存数量不足");
							logger.error("仓库[" + whId + "]商品[" + skuCd
									+ "]库存数量不足");
							//tx.rollback();
							//session.close();
							return result;
						}
						inv.setAvailableQuantity(new BigDecimal(inv
								.getAvailableQuantity().intValue()
								- qty));
						inv.setQuantity(new BigDecimal(inv.getQuantity()
								.intValue()
								- qty));
					}
				}
				this.getHibernateTemplate().update(inv);

			}
			invId = inv.getId();
			// 库存日志
			InventoryLog il = new InventoryLog();
			il.setBillNo((String) m.get("BillNo"));
			il.setCtime(date);
			il.setInvId(invId);
			il.setInvType(invType);
			il.setIoType(ioType);
			il.setLogType(logType);
			il.setQuantity(qty);
			il.setSkuCd(skuCd);
			il.setWhId(whId);
			this.getHibernateTemplate().save(il);
		}
		//tx.commit();
		//session.close();
		result.put("FLAG", "SUCCESS");
		}catch(RuntimeException e)
		{
			logger.error(e.getMessage());
		}
		return result;
	}

	public Integer getWhId(int storeId) {
		List<Warehouse> whList = this.getHibernateTemplate().find(
				"from Warehouse where Status = 'ACTIVE' and StoreId = "
						+ storeId);
		if (WebUtil.isNullForList(whList))
			return null;
		else
			return whList.get(0).getId();
	}

	public List<Map> getTbUpdateInventory(int storeId) {
		Integer whId = getWhId(storeId);
		if (WebUtil.isNull(whId)) {
			logger.error("店铺[" + storeId + "]没有对应的仓库");
			return null;
		}
		List<Inventory> invList = this.getHibernateTemplate().find(
				"from Inventory where InvStatus = 'ACTIVE' and WhId = '" + whId
						+ "' and InvType = 'A'");
		if (WebUtil.isNullForList(invList)) {
			logger.error("店铺[" + storeId + "]仓库[" + whId + "]没有可用库存");
			return null;
		}
		List<Map> result = new ArrayList();
		for (Inventory inv : invList) {
			Map m = new HashMap();
			m.put("SkuCd", inv.getSkuCd());
			m.put("Qty", inv.getAvailableQuantity());
			m.put("AllQty", inv.getQuantity());
			result.add(m);
		}
		return result;
	}

	public void tbOrderInventoryProcess(int storeId) {
		Integer whId = getWhId(storeId);
		if (WebUtil.isNull(whId)) {
			logger.error("店铺[" + storeId + "]没有对应的仓库");
			return;
		}
		String origOrderStatus = WebConfigProperties
				.getProperties("inventory.taobao.status");
		Session session = this.getSession();
		List<OrderHead> ohl = session
				.createQuery(
						"from OrderHead where OrderType = 'ORDER' and (InvStatus != 'INV' or InvStatus is null) and OrigOrderStatus in ("
								+ origOrderStatus
								+ ") and WhId = '"
								+ storeId
								+ "'").list();
		if (WebUtil.isNullForList(ohl))
			return;

		for (OrderHead oh : ohl) {
			// 查询订单的明细内容,根据订单明细来扣库存
			List<OrderItem> oil = session.createQuery(
					"from OrderItem where OrderHeadId = " + oh.getId()).list();
			if (WebUtil.isNullForList(oil)) {
				logger.error("订单[" + oh.getOrderNo() + "],淘宝订单号["
						+ oh.getOrigOrderNo() + "]没有明细内容");
				continue;
			}
			List<Map> invList = new ArrayList();
			for (OrderItem oi : oil) {
				Map im = new HashMap();
				im.put("WhId", whId);
				im.put("BillNo", oh.getOrderNo());
				im.put("SkuCd", oi.getSkuCd());
				im.put("Qty", oi.getQty().intValue());
				im.put("InvType", "A");
				im.put("LogType", "ORDER");
				im.put("IoType", "O");
				invList.add(im);
			}
			Map m = new HashMap();
			m.put("LogType", "ORDER");
			m.put("IoType", "O");
			m.put("invList", invList);
			Map<String, String> r = inventoryProcess(m);
			// 库存失败不做物流信息更新
			if (r.get("FLAG").equals("ERROR")) {
				// tx.rollback();
				continue;
			}
			Transaction tx = session.beginTransaction();
			// 更新订单的库存状态
			oh.setInvStatus("INV");
			session.update(oh);
			tx.commit();
		}
		session.close();
	}

	public Map searchInventory(Map param, int currentPage, int pageRow) {
		StringBuffer hql = new StringBuffer(
				" from Inventory inv,SkuMaster sm,ItemMaster im where inv.SkuCd = sm.SkuCd and sm.ItemId = im.id");
		String fields = "select im.ItemCd,im.ItemName,sm.SkuCd,sm.SkuProp1,sm.SkuProp2,inv.InvType,inv.InvStatus,inv.Quantity,inv.AvailableQuantity ";
		StringBuffer con = new StringBuffer();
		if (WebUtil.isNotNull(param.get("WhId"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" inv.WhId = '" + param.get("WhId") + "'");
		}
		if (WebUtil.isNotNull(param.get("ItemCd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.ItemCd = '" + param.get("ItemCd") + "'");
		}
		if (WebUtil.isNotNull(param.get("SkuCd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" sm.SkuCd = '" + param.get("SkuCd") + "'");
		}
		if (WebUtil.isNotNull(param.get("InvType"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" inv.InvType = '" + param.get("InvType") + "'");
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());
		hql.append(" order by inv.Mtime desc");
		List<Long> countList = this.getHibernateTemplate().find(
				"select count(*) " + hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(fields + hql.toString());
		if (pageRow > 0) {
			q.setFirstResult((currentPage - 1) * pageRow);
			q.setMaxResults(pageRow);
		}
		Map result = new HashMap();
		List<Object[]> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if (!WebUtil.isNullForList(l)) {
			for (Object[] o : l) {
				Map r = new HashMap();
				r.put("ItemCd", o[0]);
				r.put("ItemName", o[1]);
				r.put("SkuCd", o[2]);
				r.put("SkuProp1", o[3]);
				r.put("SkuProp2", o[4]);
				r.put("InvType", o[5]);
				r.put("InvStatus", o[6]);
				r.put("Quantity", o[7]);
				r.put("AvailableQuantity", o[8]);
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	// 此方法无法非常精确的更新库存，只能在订单数比较少的时段来运行，且认为仓库导入的库存已经把发给仓库的指示全部处理完。
	// 在计算库存时要减掉未发给仓库的订单，要扣掉安全库存，
	// 在减掉未发给仓库的订单时要根据参数判断是下单减库存还是支付减库存
	// 库存更新时间使用Inventory中的库存导入时间
	public Map updateInvToUnitSku(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		// 是否是下单支付
		String hasNoPay = (String) param.get("HasNoPay");
		if (hasNoPay == null)
			hasNoPay = "N";
		// 未支付状态
		String origOrderStatusOfNoPay = (String) param
				.get("OrigOrderStatusOfNoPay");
		if (origOrderStatusOfNoPay == null)
			origOrderStatusOfNoPay = "'DS_WAIT_BUYER_PAY','TRADE_NO_CREATE_PAY','WAIT_BUYER_PAY'";
		List<Object[]> whList = this
				.getHibernateTemplate()
				.find(
						"from Store s,Warehouse w where w.Status = 'ACTIVE' and s.WhId = w.id and s.id = "
								+ storeId);
		if (WebUtil.isNullForList(whList)) {
			result.put("Flag", "error");
			result.put("Message", "没找到店铺对应的仓库");
			return result;
		}
		Object[] obj = whList.get(0);
		Store s = (Store) obj[0];
		Warehouse w = (Warehouse) obj[1];
		// 安全库存，这个是总的安全库存，默认使用
		Integer reserveInv = w.getReserveInv();
		if (reserveInv == null)
			reserveInv = 0;
		// 库存更新时间判断
		int time = 60;// 以分钟为单位
		if (w.getValidTime() != null)
			time = w.getValidTime();
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime - time * 60 * 1000);
		List<Inventory> invList = this
				.getHibernateTemplate()
				.find(
						"from Inventory where UpdateFlag = 'N' and WhId = ? and UpdateTime >= ?",
						new Object[] {
								w.getId(),
								date });
		if (WebUtil.isNullForList(invList)) {
			result.put("Flag", "error");
			result.put("Message", "没有可更新的库存");
			return result;
		}
		List<Store> storeList = this.getHibernateTemplate().find(
				"from Store where WhId = ? order by Level", w.getId());
		Map<Integer, Store> storeMap = new HashMap();
		StringBuffer storeIdStr = new StringBuffer();// 用于查询未发给仓库的订单
		for (Store st : storeList) {
			storeMap.put(st.getId(), st);
			if (storeIdStr.length() > 0)
				storeIdStr.append(",");
			storeIdStr.append(st.getId());
		}
		List<InvRule> invRuleList = this.getHibernateTemplate().find(
				"from InvRule where WhId = ?", w.getId());
		// 商品安全库存查询
		List<Object[]> skuReserveInvList = this
				.getHibernateTemplate()
				.find(
						"select s.SkuCd,s.ReserveInv,i.ReserveInv from ItemMaster i left outer join i.skuMaster s ");
		Map<String, Integer> skuReserveInvMap = new HashMap();
		if (!WebUtil.isNullForList(skuReserveInvList)) {
			for (Object[] skus : skuReserveInvList) {
				// 如果Sku有安全库存则使用Sku的安全库存，否则使用Item的安全库存
				skuReserveInvMap.put((String) skus[0], WebUtil.isNull(skus[1])
						|| skus[1].equals(new Integer(0)) ? (Integer) skus[2]
						: (Integer) skus[1]);
			}
		}
		// 未发给仓库订单查询
		String orderSql = null;
		// 此处要传入各电商平台的未支付状态
		if (hasNoPay.equalsIgnoreCase("Y")) {
			orderSql = "select i.SkuCd,i.ReqQty from OrderHead h,OrderItem i where h.id = i.OrderHeadId and h.OrderType = 'ORDER' and (h.OrderStatus = 'START' or h.OrderStatus = 'AUDITED') and h.StoreId in ("
					+ storeIdStr.toString() + ")";
		} else {
			orderSql = "select i.SkuCd,i.ReqQty from OrderHead h,OrderItem i where h.id = i.OrderHeadId and h.OrderType = 'ORDER' and (h.OrderStatus = 'START' or h.OrderStatus = 'AUDITED') and h.OrigOrderStatus not in ("
					+ origOrderStatusOfNoPay
					+ ") and h.StoreId in ("
					+ storeIdStr.toString() + ")";
		}
		List<Object[]> orderList = this.getHibernateTemplate().find(orderSql);
		Map<String, Integer> orderInvMap = new HashMap();
		if (!WebUtil.isNullForList(orderList)) {
			for (Object[] oi : orderList) {
				orderInvMap.put((String) oi[0], WebUtil.isNull(oi[1]) ? 0
						: ((BigDecimal) oi[1]).intValue());
			}
		}
		// 无库存规则的商品，每个店使用相同的库存
		//Session session = this.getSession();
		try {
			if (WebUtil.isNullForList(invRuleList)
					|| WebUtil.isNull(invRuleList.get(0).getProportionStr())) {
				for (Inventory inv : invList) {
					//Transaction tx = session.beginTransaction();
					int qty = inv.getAvailableQuantity().intValue();
					// 此处库存是否要考虑未发给仓库的库存
					Integer orderInv = orderInvMap.get(inv.getSkuCd());
					if (orderInv != null)
						qty = qty - orderInv;
					// 如果库存数量小于店铺数量，则使用安全库存,安全库存需要考虑单个商品的安全库存
					Integer sri = skuReserveInvMap.get(inv.getSkuCd());
					if (sri != null && sri > 0)
						reserveInv = sri;
					// 库存数量不足时启用安全库存
					if (qty - reserveInv > 0)
						qty = qty - reserveInv;
					// 更新每个店铺的UnitSku
					for (Store store : storeList) {
						this.getHibernateTemplate().bulkUpdate(
								"update UnitSkuMaster set InvUpdateTime='"
										+ WebUtil.formatDateString(inv
												.getUpdateTime(),
												"yyyy-MM-dd HH:mm:ss")
										+ "',InvUpdateFlag='Y',SkuInv="
										+ (inv.getAvailableQuantity()
												.subtract(new BigDecimal(
														reserveInv)))
										+ " where SkuCd = '" + inv.getSkuCd()
										+ "' and StoreId = " + store.getId());
					}
					// 更新库存标记
					this.getHibernateTemplate().bulkUpdate(
							"update Inventory set UpdateFlag = 'Y',Mtime = '"
									+ WebUtil.formatDateString(new Date(),
											"yyyy-MM-dd HH:mm:ss")
									+ "' where SkuCd = '" + inv.getSkuCd()
									+ "' and WhId = " + w.getId());
					//tx.commit();
				}
			} else {
				InvRule ir = invRuleList.get(0);
				String[] ppStr = ir.getProportionStr().split(",");
				int total = 0;// 比例总数
				Map<Integer, Integer> stPpMap = new HashMap();// 店铺比例保存
				List<Map> stPpList = new ArrayList();// 按店铺比例排序
				for (String pp : ppStr) {
					String[] pps = pp.split(":");
					if (pps.length != 2) {
						result.put("Flag", "error");
						result.put("Message", "仓库【" + w.getWhName() + "-"
								+ w.getWhCode() + "】的比例参数不正确");
						//session.close();
						return result;
					}
					if (storeMap.get(new Integer(pps[0])) == null) {
						result.put("Flag", "error");
						result.put("Message", "仓库【" + w.getWhName() + "-"
								+ w.getWhCode() + "】的比例参数不正确,店铺不存在");
						//session.close();
						return result;
					}
					total += new Integer(pps[1]);
					if (new Integer(pps[1]) > 0) {
						stPpMap.put(new Integer(pps[0]), new Integer(pps[1]));
						stPpList.add(WebUtil.toMap("storeId", new Integer(
								pps[0]), "scale", new Integer(pps[1])));
					}
				}
				// 按店铺比例排序
				java.util.Collections.sort(stPpList, new Comparator<Map>() {

					@Override
					public int compare(Map o1, Map o2) {
						Integer s1 = (Integer) o1.get("scale");
						Integer s2 = (Integer) o1.get("scale");
						if (s1 > s2)
							return 1;
						else
							return 0;
					}
				});
				// 按比例分配库存
				for (Inventory inv : invList) {
					//Transaction tx = session.beginTransaction();
					int qty = inv.getAvailableQuantity().intValue();
					// 此处库存是否要考虑未发给仓库的库存
					Integer orderInv = orderInvMap.get(inv.getSkuCd());
					if (orderInv != null)
						qty = qty - orderInv;
					// 如果库存数量小于店铺数量，则使用安全库存,安全库存需要考虑单个商品的安全库存
					Integer sri = skuReserveInvMap.get(inv.getSkuCd());
					if (sri != null && sri > 0)
						reserveInv = sri;
					// 库存数量不足时启用安全库存
					if (qty - reserveInv > stPpMap.size())
						qty = qty - reserveInv;

					// 库存数量小于等于店铺数量，首先按店铺优先级排序分配库存，没分配到的店铺库存更新为0
					if (qty <= stPpMap.size()) {
						for (Store st : storeList) {
							// 没分配比例的店铺，库存更新为0
							if (stPpMap.get(st.getId()) == null) {
								this.getHibernateTemplate().bulkUpdate(
												"update UnitSkuMaster set InvUpdateTime='"
														+ WebUtil
																.formatDateString(
																		inv
																				.getUpdateTime(),
																		"yyyy-MM-dd HH:mm:ss")
														+ "',InvUpdateFlag='Y',SkuInv = 0 where SkuCd = '"
														+ inv.getSkuCd()
														+ "' and StoreId = "
														+ st.getId());
							}
						}
						// 按库存比例大小排定优先分配顺序，比例高优先分配
						for (int i = stPpList.size() - 1; i >= 0; i--) {
							Map m = stPpList.get(i);
							Integer stId = (Integer) m.get("storeId");
							// 库存已分配完的店铺，库存更新为0
							if (qty <= 0) {
								this.getHibernateTemplate().bulkUpdate(
												"update UnitSkuMaster set InvUpdateTime='"
														+ WebUtil
																.formatDateString(
																		inv
																				.getUpdateTime(),
																		"yyyy-MM-dd HH:mm:ss")
														+ "',InvUpdateFlag='Y',SkuInv = 0 where SkuCd = '"
														+ inv.getSkuCd()
														+ "' and StoreId = "
														+ stId);
							}
							// 库存更新为1
							else {
								this.getHibernateTemplate().bulkUpdate(
												"update UnitSkuMaster set InvUpdateTime='"
														+ WebUtil
																.formatDateString(
																		inv
																				.getUpdateTime(),
																		"yyyy-MM-dd HH:mm:ss")
														+ "',InvUpdateFlag='Y',SkuInv = 1 where SkuCd = '"
														+ inv.getSkuCd()
														+ "' and StoreId = "
														+ stId);
								qty--;
							}
						}
					}
					// 库存数量大于店铺数量，按比例分配库存，最小库存为1
					else {
						int tempQty = qty;
						for (Store st : storeList) {
							// 没分配比例的店铺，库存更新为0
							if (stPpMap.get(st.getId()) == null) {
								this.getHibernateTemplate().bulkUpdate(
												"update UnitSkuMaster set InvUpdateTime='"
														+ WebUtil
																.formatDateString(
																		inv
																				.getUpdateTime(),
																		"yyyy-MM-dd HH:mm:ss")
														+ "',InvUpdateFlag='Y',SkuInv = 0 where SkuCd = '"
														+ inv.getSkuCd()
														+ "' and StoreId = "
														+ st.getId());
							}
						}
						// 按库存比例分配库存，先分配比例小的，如果分配数大于1则取整，分配数小于1则分配1，最后剩余的库存分配给比例最高的。
						int r = 0;
						for (Map m : stPpList) {
							Integer stId = (Integer) m.get("storeId");
							Integer p = (Integer) m.get("scale");// 库存比例值
							BigDecimal scale = new BigDecimal(p).divide(
									new BigDecimal(total), 2,
									BigDecimal.ROUND_HALF_UP);// 库存比例计算
							BigDecimal skuInv = new BigDecimal(qty)
									.multiply(scale);// 可分配库存
							int skuinv = 0;
							if (skuInv.doubleValue() < 1)
								skuinv = 1;
							else
								skuinv = skuInv.intValue();
							// 剩余库存全部分配给比例大的
							if (r == stPpList.size() - 1) {
								skuinv = tempQty;
							}
							this.getHibernateTemplate().bulkUpdate(
									"update UnitSkuMaster set InvUpdateTime='"
											+ WebUtil.formatDateString(inv
													.getUpdateTime(),
													"yyyy-MM-dd HH:mm:ss")
											+ "',InvUpdateFlag='Y',SkuInv = "
											+ skuinv + " where SkuCd = '"
											+ inv.getSkuCd()
											+ "' and StoreId = " + stId);
							tempQty = tempQty - skuinv;
							r++;

						}
					}
					// 更新库存标记
					this.getHibernateTemplate().bulkUpdate(
							"update Inventory set UpdateFlag = 'Y',Mtime = '"
									+ WebUtil.formatDateString(new Date(),
											"yyyy-MM-dd HH:mm:ss")
									+ "' where SkuCd = '" + inv.getSkuCd()
									+ "' and WhId = " + w.getId());
					//tx.commit();
				}
			}
		} catch (Exception e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		}
		//session.close();
		return result;
	}

	@Override
	public Map getAvailableInvOfShop(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "StoreId is null");
			return result;
		}
		// 查询仓库及库存有效时间
		List<Object[]> wsList = this
				.getHibernateTemplate()
				.find(
						"select w.id,w.ValidTime from Store s,Warehouse w where w.Status = 'ACTIVE' and s.WhId = w.id and s.id = "
								+ storeId);
		if (WebUtil.isNullForList(wsList)) {
			result.put("Flag", "error");
			result.put("Message", "店铺及仓库信息配置问题，没找到相应信息");
			return result;
		}
		Integer validTime = (Integer) wsList.get(0)[1];
		// 默认有效时间为1小时
		if (validTime == null)
			validTime = 60;
		// 有效时间判断，库存的更新时间需要大于等于此时间
		Date date = new Date(System.currentTimeMillis() - 60 * 60 * 1000);
		List<Object[]> skuList = this
				.getHibernateTemplate()
				.find(
						"select SkuCd,SkuInv from UnitSkuMaster where InvUpdateFlag = 'N' and InvUpdateTime >= '"
								+ WebUtil.formatDateString(date,
										"yyyy-MM-dd HH:mm:ss") + "'");
		if (WebUtil.isNullForList(skuList)) {
			result.put("Flag", "error");
			result.put("Message", "没有可更新库存");
			return result;
		}
		List<Map> l = new ArrayList();
		for (Object[] o : skuList) {
			l.add(WebUtil.toMap("SkuCd", o[0], "Qty", ((BigDecimal) o[1])
					.intValue()));
		}
		result.put("Flag", "success");
		result.put("SkuInvList", l);
		return result;
	}

	public Map updateInvFlagOfShop(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "StoreId is null");
			return result;
		}
		this.getHibernateTemplate().bulkUpdate(
				"update UnitSkuMaster set InvUpdateFlag = 'Y' where StoreId = "
						+ storeId);
		result.put("Flag", "success");
		return result;
	}

	public Map deductOrderInventory(String orderNo) {
		Map result = new HashMap();
		result.put("Flag", "ERROR");
		String origOrderStatus = WebConfigProperties
				.getProperties("inventory.taobao.status");
		List<Object[]> orderList = this
				.getHibernateTemplate()
				.find(
						"select id,StoreId from OrderHead where OrderNo = ? and  OrderType = 'ORDER' and (InvStatus != 'INV' or InvStatus is null) and OrigOrderStatus in ("
								+ origOrderStatus + ")", orderNo);
		if (WebUtil.isNullForList(orderList))
			return result;
		Integer storeId = (Integer) orderList.get(0)[1];
		Integer orderHeadId = (Integer) orderList.get(0)[0];

		// 根据店铺配置判断是下单扣库存还是支付扣库存
		// 库存扣除
		List<Object[]> whList = this.getHibernateTemplate().find(
				"select id,AllowNegativeInv from Warehouse where StoreId = ?",
				storeId);
		if (WebUtil.isNullForList(whList))
			return result;
		Integer whId = (Integer) whList.get(0)[0];
		if (WebUtil.isNull(whId)) {
			logger.error("店铺[" + storeId + "]没有对应的仓库");
			return result;
		}
		String allowNegativeInv = (String) whList.get(0)[1];
		// 查询订单的明细内容,根据订单明细来扣库存
		List<OrderItem> oil = this.getHibernateTemplate().find(
				"from OrderItem where OrderHeadId = " + orderHeadId);
		if (WebUtil.isNullForList(oil)) {
			logger.error("订单[" + orderNo + "]没有明细内容");
			return result;
		}
		List<Map> invList = new ArrayList();
		for (OrderItem oi : oil) {
			Map im = new HashMap();
			im.put("WhId", whId);
			im.put("BillNo", orderNo);
			im.put("SkuCd", oi.getSkuCd());
			if(WebUtil.isNull(oi.getSkuCd()))
				im.put("SkuCd", oi.getItemCd());				
			im.put("Qty", oi.getQty().intValue());
			im.put("InvType", "A");
			im.put("LogType", "ORDER");
			im.put("IoType", "O");
			invList.add(im);
		}
		Map m = new HashMap();
		m.put("LogType", "ORDER");
		m.put("IoType", "O");
		m.put("invList", invList);
		m.put("allowNegativeInv", allowNegativeInv);
		Map<String, String> r = inventoryProcess(m);
		// 库存失败不做物流信息更新
		if (r.get("FLAG").equals("ERROR")) {
			return result;
		}
		this.getHibernateTemplate().bulkUpdate(
				"update OrderHead set InvStatus = 'INV' where OrderNo = '"
						+ orderNo + "'");
		logger.debug("订单[" + orderNo + "]扣除库存成功");
		result.put("Flag", "SUCCESS");
		return result;
	}
}
