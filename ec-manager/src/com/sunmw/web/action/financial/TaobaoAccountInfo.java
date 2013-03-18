package com.sunmw.web.action.financial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.financial.AlipayFinancialServices;
import com.sunmw.web.util.WebUtil;

public class TaobaoAccountInfo {
	private AlipayFinancialServices alipayFinancialServices;
	
	private String accountId;
	private String storeId;
	private String month;
	private Map taobaoAccountInfo;
	private List orderDetailList;
	private List alipayDetailList;
	private List refundList;
	
	private boolean success;
	private String message;
	
	public AlipayFinancialServices getAlipayFinancialServices() {
		return alipayFinancialServices;
	}

	public void setAlipayFinancialServices(
			AlipayFinancialServices alipayFinancialServices) {
		this.alipayFinancialServices = alipayFinancialServices;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Map getTaobaoAccountInfo() {
		return taobaoAccountInfo;
	}

	public void setTaobaoAccountInfo(Map taobaoAccountInfo) {
		this.taobaoAccountInfo = taobaoAccountInfo;
	}

	public List getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public List getAlipayDetailList() {
		return alipayDetailList;
	}

	public void setAlipayDetailList(List alipayDetailList) {
		this.alipayDetailList = alipayDetailList;
	}

	public List getRefundList() {
		return refundList;
	}

	public void setRefundList(List refundList) {
		this.refundList = refundList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String taobaoAccountHeadInfo()
	{
		if(taobaoAccountInfo==null)
			taobaoAccountInfo = new HashMap();
		taobaoAccountInfo.clear();
		Map param = new HashMap();
		if(WebUtil.isNotNull(accountId))
		{
			param.put("AccountId", new Integer(accountId));
		}
		if(WebUtil.isNotNull(storeId))
		{
			param.put("StoreId", new Integer(storeId));
		}
		param.put("Month", month);
		Map m = this.alipayFinancialServices.taobaoAccountHeadInfo(param);
		if(m.get("Flag").equals("ERROR"))
		{
			success = false;
			message = (String)m.get("Message");
			return "success";
		}
		Map info = (Map) m.get("TaobaoAccountInfo");
		List receivedList = new ArrayList();
		List paidList = new ArrayList();
		taobaoAccountInfo.put("AccountId", info.get("AccountId"));
		taobaoAccountInfo.put("StoreName", info.get("StoreName"));
		taobaoAccountInfo.put("Month", info.get("Month"));
		taobaoAccountInfo.put("PriceAmount", info.get("PriceAmount"));
		taobaoAccountInfo.put("PriceDiscount", info.get("PriceDiscount")!=null?((BigDecimal)info.get("PriceDiscount")).multiply(new BigDecimal(100)).doubleValue()+"%":"0%");
		Map m0 = WebUtil.toMap("AmoutName","","Amount", "","DiscountName","","Discount","");
		Map m1 = WebUtil.toMap("AmoutName","销售金额（含运费）","Amount", info.get("ReceivedAmount"),"DiscountName","销售数量（单）","Discount",info.get("OrderNum"));
		receivedList.add(m1);
		Map m2 = WebUtil.toMap("AmoutName","销售金额（不含运费）","Amount", info.get("SaleAmountNotPost"),"DiscountName","销售数量（件）","Discount",info.get("OrderItemNum"));
		receivedList.add(m2);
		Map m3 = WebUtil.toMap("AmoutName","商品成交金额","Amount", info.get("TotalFeeAmount"),"DiscountName","平均客单（件/单）","Discount",info.get("AverageOrderItem"));
		receivedList.add(m3);
		Map m4 = WebUtil.toMap("AmoutName","运费收入","Amount", info.get("ReceivePostFeeAmount"),"DiscountName","平均客单价","Discount",info.get("AverageOrderFee"));
		receivedList.add(m4);
		receivedList.add(m0);
		Map m5 = WebUtil.toMap("AmoutName","退货金额","Amount", info.get("RefundFee"),"DiscountName","退货数量（件）","Discount",info.get("RefundItemNum"));
		receivedList.add(m5);
		Map m6 = WebUtil.toMap("AmoutName","当月实际完成商品销售","Amount", info.get("SaleAmount"),"DiscountName","当月实际销售数量","Discount",info.get("SaleOrderItemNum"));
		receivedList.add(m6);
		receivedList.add(m0);
		Map m7 = WebUtil.toMap("AmoutName","支付宝已到帐金额","Amount", info.get("AlipayReceivedAmount"),"DiscountName","支付宝到帐率","Discount",info.get("AlipayPercentage")!=null?((BigDecimal)info.get("AlipayPercentage")).multiply(new BigDecimal(100)).doubleValue()+"%":"0%");
		receivedList.add(m7);
		receivedList.add(m0);
		Map m8 = WebUtil.toMap("AmoutName","商品生产成本","Amount", "","DiscountName","商品毛利率","Discount","");
		receivedList.add(m8);
		receivedList.add(m0);
		Map m9 = WebUtil.toMap("AmoutName","销售毛利","Amount", "","DiscountName","销售毛利率","Discount","");
		receivedList.add(m9);
//		List<Map> otherReceiveList = (List)info.get("OtherReceiveList");
//		if(!WebUtil.isNullForList(otherReceiveList))
//		{
//			for(Map mm:otherReceiveList)
//				receivedList.add(WebUtil.toMap("AmoutName",mm.get("AccountType"),"Amount", mm.get("Amount"),"DiscountName","","Discount",""));
//		}
		taobaoAccountInfo.put("ReceivedList", receivedList);
		Map p1 = WebUtil.toMap("AmoutName","赠顾客积分","Amount", info.get("PointAmount"),"DiscountName","费率","Discount","0.5%");
		paidList.add(p1);
		Map p2 = WebUtil.toMap("AmoutName","淘宝佣金","Amount", info.get("TbCommissionAmount"),"DiscountName","费率","Discount","5%");
		paidList.add(p2);
		Map p3 = WebUtil.toMap("AmoutName","信用卡手续费","Amount", info.get("CreditCardAmount"),"DiscountName","手续费率","Discount",info.get("CreditCardPercentage")!=null?((BigDecimal)info.get("CreditCardPercentage")).multiply(new BigDecimal(100)).doubleValue()+"%":"0%");
		paidList.add(p3);
		Map p4 = WebUtil.toMap("AmoutName","物流宝费用","Amount", info.get("物流宝"),"DiscountName","","Discount","");
		paidList.add(p4);
		Map p5 = WebUtil.toMap("AmoutName","支付快递费用","Amount", info.get("PostFeeAmount"),"DiscountName","","Discount","");
		paidList.add(p5);
		Map p6 = WebUtil.toMap("AmoutName","耗材","Amount", info.get("ConsumablesAmount"),"DiscountName","","Discount","");
		paidList.add(p6);
		paidList.add(m0);
		Map p7 = WebUtil.toMap("AmoutName","淘宝客代理佣金","Amount", info.get("TbkeCommissionAmount"),"DiscountName","淘宝客比例（订单数）","Discount","");
		paidList.add(p7);
		Map p8 = WebUtil.toMap("AmoutName","其它优惠折扣（满减）","Amount", info.get("OtherPromotion"),"DiscountName","","Discount","");
		paidList.add(p8);
		paidList.add(m0);
		paidList.add(m0);
		paidList.add(m0);
		Map p9 = WebUtil.toMap("AmoutName","销售支出合计","Amount", "","DiscountName","","Discount","");
		paidList.add(p9);
//		List<Map> otherPaidList = (List)info.get("OtherPaidList");
//		if(!WebUtil.isNullForList(otherPaidList))
//		{
//			for(Map mm:otherPaidList)
//				paidList.add(WebUtil.toMap("AmoutName",mm.get("AccountType"),"Amount", mm.get("Amount"),"DiscountName","","Discount",""));
//		}
		taobaoAccountInfo.put("PaidList", paidList);
		success = true;
		return "success";
	}
	
	public String saleOrderList()
	{
		if(WebUtil.isNull(accountId))
		{
			success = false;
			message = "没有accountId";
			return "success";
		}
		Map m = this.alipayFinancialServices.searchTaobaoAccountDetail(WebUtil.toMap("AccountId", new Integer(accountId)));
		this.orderDetailList = (List) m.get("TaobaoAccountDetailList");		
		return "success";
	}
	
	public String alipayDetailList()
	{
		if(WebUtil.isNull(accountId))
		{
			success = false;
			message = "没有accountId";
			return "success";
		}
		Map m = this.alipayFinancialServices.searchTaobaoAccountDetail(WebUtil.toMap("AccountId", new Integer(accountId)));
		this.alipayDetailList = (List) m.get("AlipayFinancialDetailList");	
		return "success";
	}
	
	public String refundOrderList()
	{
		if(WebUtil.isNull(accountId))
		{
			success = false;
			message = "没有accountId";
			return "success";
		}
		Map m = this.alipayFinancialServices.searchTaobaoAccountDetail(WebUtil.toMap("AccountId", new Integer(accountId)));
		this.refundList = (List) m.get("TaobaoAccountRefundList");	
		return "success";
	}
}
