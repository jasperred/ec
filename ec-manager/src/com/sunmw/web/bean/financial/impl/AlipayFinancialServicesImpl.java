package com.sunmw.web.bean.financial.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunmw.web.bean.financial.AlipayFinancialServices;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.dao.ReportServicesMapper;
import com.sunmw.web.dao.report.AlipayFinancialDetailMapper;
import com.sunmw.web.dao.report.FinancialServicesMapper;
import com.sunmw.web.dao.report.TaobaoAccountDetailMapper;
import com.sunmw.web.dao.report.TaobaoAccountHeadMapper;
import com.sunmw.web.dao.report.TaobaoAccountOtherMapper;
import com.sunmw.web.dao.report.TaobaoAccountRefundMapper;
import com.sunmw.web.domain.report.AlipayFinancialDetail;
import com.sunmw.web.domain.report.AlipayFinancialDetailExample;
import com.sunmw.web.domain.report.ExpandTaobaoAccountHeadExample;
import com.sunmw.web.domain.report.TaobaoAccountDetail;
import com.sunmw.web.domain.report.TaobaoAccountDetailExample;
import com.sunmw.web.domain.report.TaobaoAccountHead;
import com.sunmw.web.domain.report.TaobaoAccountOther;
import com.sunmw.web.domain.report.TaobaoAccountOtherExample;
import com.sunmw.web.domain.report.TaobaoAccountRefund;
import com.sunmw.web.domain.report.TaobaoAccountRefundExample;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class AlipayFinancialServicesImpl implements AlipayFinancialServices {
	static Logger logger = Logger.getLogger(AlipayFinancialServicesImpl.class);
	private AlipayFinancialDetailMapper alipayFinancialDetailMapper;
	private TaobaoAccountHeadMapper taobaoAccountHeadMapper;
	private TaobaoAccountDetailMapper taobaoAccountDetailMapper;
	private TaobaoAccountOtherMapper taobaoAccountOtherMapper;
	private TaobaoAccountRefundMapper taobaoAccountRefundMapper;
	private FinancialServicesMapper financialServicesMapper;

	public AlipayFinancialDetailMapper getAlipayFinancialDetailMapper() {
		return alipayFinancialDetailMapper;
	}

	public void setAlipayFinancialDetailMapper(
			AlipayFinancialDetailMapper alipayFinancialDetailMapper) {
		this.alipayFinancialDetailMapper = alipayFinancialDetailMapper;
	}

	public TaobaoAccountHeadMapper getTaobaoAccountHeadMapper() {
		return taobaoAccountHeadMapper;
	}

	public void setTaobaoAccountHeadMapper(
			TaobaoAccountHeadMapper taobaoAccountHeadMapper) {
		this.taobaoAccountHeadMapper = taobaoAccountHeadMapper;
	}

	public TaobaoAccountDetailMapper getTaobaoAccountDetailMapper() {
		return taobaoAccountDetailMapper;
	}

	public void setTaobaoAccountDetailMapper(
			TaobaoAccountDetailMapper taobaoAccountDetailMapper) {
		this.taobaoAccountDetailMapper = taobaoAccountDetailMapper;
	}

	public TaobaoAccountOtherMapper getTaobaoAccountOtherMapper() {
		return taobaoAccountOtherMapper;
	}

	public void setTaobaoAccountOtherMapper(
			TaobaoAccountOtherMapper taobaoAccountOtherMapper) {
		this.taobaoAccountOtherMapper = taobaoAccountOtherMapper;
	}

	public TaobaoAccountRefundMapper getTaobaoAccountRefundMapper() {
		return taobaoAccountRefundMapper;
	}

	public void setTaobaoAccountRefundMapper(
			TaobaoAccountRefundMapper taobaoAccountRefundMapper) {
		this.taobaoAccountRefundMapper = taobaoAccountRefundMapper;
	}

	public FinancialServicesMapper getFinancialServicesMapper() {
		return financialServicesMapper;
	}

	public void setFinancialServicesMapper(
			FinancialServicesMapper financialServicesMapper) {
		this.financialServicesMapper = financialServicesMapper;
	}

	@Override
	public Map importAlipayFinancialReportCsv(Map param) {
		Map result = new HashMap();
		result.put("Flag", "SUCCESS");
		List<String[]> afrList = (List) param.get("AlipayFinancialReportList");
		if (WebUtil.isNullForList(afrList)) {
			result.put("Flag", "ERROR");
			result.put("Message", "无导入内容");
			return result;
		}
		Integer storeId = (Integer) param.get("StoreId");
		if(WebUtil.isNull(storeId))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无店铺ID");
			return result;
		}
		Integer companyId = (Integer) param.get("CompanyId");
		if(WebUtil.isNull(companyId))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无公司ID");
			return result;
		}
		int r = 0;
		//记录交易付款的业务号和订单号
		Map<String,String> bNoMap = new HashMap();
		for (String[] afr : afrList) {
			if (afr.length != 12)
				continue;
			if(afr[10]!=null&&afr[10].trim().equalsIgnoreCase("交易付款"))
			{
				bNoMap.put(afr[1].trim(), afr[2].trim().substring(5));
			}
		}
		for (String[] afr : afrList) {
			if (afr.length != 12||(WebUtil.isNull(afr[0])||WebUtil.isNull(afr[1])||WebUtil.isNull(afr[2])))
				continue;
			AlipayFinancialDetail afd = new AlipayFinancialDetail();
			// 账务流水号
			if (WebUtil.isNotNull(afr[0]))
				afd.setFinancialNo(afr[0].trim());
			// 业务流水号
			if (WebUtil.isNotNull(afr[1]))
				afd.setBusinessNo(afr[1].trim());
			// 商户订单号
			if (WebUtil.isNotNull(afr[2]))
				afd.setMerchantOrderNo(afr[2].trim());
			// 商品名称
			if (WebUtil.isNotNull(afr[3]))
				afd.setItemName(afr[3].trim());
			// 发生时间
			if (WebUtil.isNotNull(afr[4]))
				afd.setAlipayTime(WebUtil.toDateForString(afr[4].trim(),
						"yyyy-MM-dd HH:mm:ss"));
			// 对方账号
			if (WebUtil.isNotNull(afr[5]))
				afd.setAlipayNo(afr[5].trim());
			// 收入金额（+元）
			if (WebUtil.isNotNull(afr[6]))
				afd.setAmountReceived(new BigDecimal(afr[6].trim()));
			// 支出金额（-元）
			if (WebUtil.isNotNull(afr[7]))
				afd.setAmountPaid(new BigDecimal(afr[7].trim()));
			// 账户余额（元）
			if (WebUtil.isNotNull(afr[8]))
				afd.setBalance(new BigDecimal(afr[8].trim()));
			// 交易渠道
			if (WebUtil.isNotNull(afr[9]))
				afd.setTradeChannel(afr[9].trim());
			// 业务类型
			if (WebUtil.isNotNull(afr[10]))
				afd.setBusinessType(afr[10].trim());
			// 备注
			if (WebUtil.isNotNull(afr[11]))
				afd.setMemo(afr[11].trim());
			AlipayFinancialDetailExample ex = new AlipayFinancialDetailExample();
			AlipayFinancialDetailExample.Criteria c = ex.createCriteria();
			if (WebUtil.isNotNull(afr[0]))
				c.andFinancialNoEqualTo(afr[0].trim());
//			if (WebUtil.isNotNull(afr[1]))
//				c.andBusinessNoEqualTo(afr[1].trim());
			if (WebUtil.isNotNull(afr[4]))
				c.andAlipayTimeEqualTo(WebUtil.toDateForString(afr[4].trim(),
						"yyyy-MM-dd HH:mm:ss"));
			afd.setFlag("N");
			afd.setStoreId(storeId);
			afd.setCompanyId(companyId);
			checkTid(afd,bNoMap);
			int count = this.alipayFinancialDetailMapper.updateByExampleSelective(afd, ex);
			if (count == 0)
			{
				afd.setCtime(new Date());
				this.alipayFinancialDetailMapper.insertSelective(afd);
			}
			else
			{
				afd.setMtime(new Date());
				this.alipayFinancialDetailMapper.updateByExampleSelective(afd,ex);
			}
			r++;
		}
		result.put("row", r);
		return result;
	}
	
	private void checkTid(AlipayFinancialDetail afd,Map<String,String> bNoMap)
	{
		try {
			String bType = afd.getBusinessType();
			if(WebUtil.isNull(bType))
				return;
			if(bType.equalsIgnoreCase("红包退回"))
			{
				afd.setAccountType("红包退回");
			}
			else if(bType.equalsIgnoreCase("交易付款"))
			{
				afd.setAccountType("交易付款");
				//T200P+淘宝订单号
				afd.setTid(afd.getMerchantOrderNo().substring(5));
			}
			else if(bType.equalsIgnoreCase("收费"))
			{
				afd.setAccountType("收费");
				//T200P+淘宝订单号
				if(afd.getMerchantOrderNo().indexOf("T200P")>=0)
					afd.setTid(afd.getMerchantOrderNo().substring(5));
				//商户订单号=【交易付款】的业务流水号
				else
					afd.setTid(bNoMap.get(afd.getMerchantOrderNo()));
			}
			else if(bType.equalsIgnoreCase("提现"))
			{
				afd.setAccountType("提现");
			}
			else if(bType.equalsIgnoreCase("转账"))
			{
				if(WebUtil.isNull(afd.getMemo()))
				{
					afd.setAccountType("其它转账");
				}
				else if(afd.getMemo().indexOf("返点积分")>=0)
				{
					afd.setAccountType("返点积分");
					//备注中含有淘宝订单号
					//取‘返点积分’后的字符
					String m = afd.getMemo().substring(afd.getMemo().indexOf("返点积分")+4);
					//用逗号和右括号来分隔，数组的第一位就是Tid
					String[] tids = null;
					if(m.indexOf("）")>=0)
						tids = m.split("）");
					if(m.indexOf(")")>=0)
						tids = m.split(")");
					if(tids[0].indexOf("，")>=0)
						tids =tids[0].split("，");
					if(tids[0].indexOf(",")>=0)
						tids = tids[0].split(",");
					afd.setTid(tids[0]);
				}
				else if(afd.getMemo().indexOf("淘宝客佣金")>=0)
				{
					afd.setAccountType("淘宝客佣金");
					//4.10以后可在备注中得到淘宝订单号
					if(afd.getMemo().indexOf("[")>=0)
						afd.setTid(afd.getMemo().substring(afd.getMemo().indexOf("[")+1,afd.getMemo().indexOf("]")));
				}
				else if(afd.getMemo().indexOf("B2COnLine服务")>=0||afd.getMemo().indexOf("新online佣金服务")>=0||afd.getMemo().indexOf("天猫佣金")>=0)
				{
					afd.setAccountType("B2COnLine服务");
					//商户订单号=*=+淘宝OID
					if(afd.getMerchantOrderNo().lastIndexOf("=")>=0)
						afd.setTid(afd.getMerchantOrderNo().substring(afd.getMerchantOrderNo().lastIndexOf("=")+1));
				}
				else if(afd.getMemo().indexOf("保证金理赔")>=0)
				{
					afd.setAccountType("保证金理赔");
					
				}
				else if(afd.getMemo().indexOf("物流宝")>=0)
				{
					afd.setAccountType("物流宝");
					
				}
				else if(afd.getMemo().indexOf("交易充退接口转账")>=0)
				{
					afd.setAccountType("交易充退接口转账");
					
				}
				else if(afd.getMemo().indexOf("技术年费退款")>=0)
				{
					afd.setAccountType("技术年费退款");
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map taobaoAccountReport_bak(Map param)
	{
		Map result = new HashMap();
		result.put("Flag", "SUCCESS");
		Integer storeId = (Integer) param.get("StoreId");
		if(WebUtil.isNull(storeId))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无店铺ID");
			return result;
		}
		Integer companyId = (Integer) param.get("CompanyId");
		if(WebUtil.isNull(companyId))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无公司ID");
			return result;
		}
		String month = (String) param.get("Month");
//		Date alipayFromDate = (Date) param.get("AlipayFromDate");
//		Date alipayEndDate = (Date) param.get("AlipayEndDate");
		long time1 = System.currentTimeMillis();
		//查询当前月份的订单
		ReportServicesMapper reportServicesMapper = (ReportServicesMapper) GetBeanServlet.getBean("reportServicesMapper");
		Map m = new HashMap();
		m.put("StoreId", storeId);
		String startDate  = WebUtil.formatDateString(WebUtil.toDateForString(month, "yyyy-MM"),"yyyy-MM")+"-01 00:00:00";
		String endDate = WebUtil.formatDateString(WebUtil.toDateForString(month, "yyyy-MM"),"yyyy-MM")+"-31 24:00:00";
		
		m.put("StartDate",startDate);
		m.put("EndDate", endDate);
		List<Map> saleOrderList = reportServicesMapper.selectSaleOrderByExample(m);
		if(WebUtil.isNullForList(saleOrderList))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无销售信息");
			return result;
		}

		//删除当前店铺当前月份的对账报表
		ExpandTaobaoAccountHeadExample tae = new ExpandTaobaoAccountHeadExample();
		ExpandTaobaoAccountHeadExample.Criteria tac = tae.createCriteria();
		tac.andCompanyIdEqualTo(companyId);
		tac.andStoreIdEqualTo(storeId);
		tac.andMonthEqualTo(month);
		List<TaobaoAccountHead> tahList = null;
		try {
			tahList = this.taobaoAccountHeadMapper.selectByExample(tae);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		TaobaoAccountHead tah = null;
		if(!WebUtil.isNullForList(tahList))
		{
			tah = tahList.get(0);
			//删除明细表
			TaobaoAccountDetailExample tade = new TaobaoAccountDetailExample();
			TaobaoAccountDetailExample.Criteria tadc = tade.createCriteria();
			tadc.andAccountIdEqualTo(tah.getAccountId());
			this.taobaoAccountDetailMapper.deleteByExample(tade);
			//删除未匹配金额表
			TaobaoAccountOtherExample taoe = new TaobaoAccountOtherExample();
			TaobaoAccountOtherExample.Criteria taoc = taoe.createCriteria();
			taoc.andAccountIdEqualTo(tah.getAccountId());
			this.taobaoAccountOtherMapper.deleteByExample(taoe);
			//删除退货表
			TaobaoAccountRefundExample tare = new TaobaoAccountRefundExample();
			TaobaoAccountRefundExample.Criteria tarc = tare.createCriteria();
			tarc.andAccountIdEqualTo(tah.getAccountId());
			this.taobaoAccountRefundMapper.deleteByExample(tare);
		}
		else
		{
			//建立对账主表信息
			tah = new TaobaoAccountHead();
			tah.setCompanyId(companyId);
			tah.setStoreId(storeId);
			tah.setMonth(month);
			tah.setCtime(new Date());
			this.taobaoAccountHeadMapper.insertSelective(tah);
			tahList = this.taobaoAccountHeadMapper.selectByExample(tae);
			tah = tahList.get(0);
		}
		long time2 = System.currentTimeMillis();
		Integer accountId = tah.getAccountId();
		//保存到对账明细表
		for(Map saleOrder:saleOrderList)
		{
			TaobaoAccountDetail tad = new TaobaoAccountDetail();
			tad.setAccountId(accountId);
			tad.setCompanyId(companyId);		
			tad.setStoreId(storeId);	
			tad.setCtime(new Date());
			tad.setPlatform("淘宝");
			tad.setOrderType("销售");
			tad.setOrderNo((String)saleOrder.get("orderNo"));
			tad.setTid((String)saleOrder.get("origOrderNo"));
			tad.setPostNo((String)saleOrder.get("postNo"));
			tad.setOid((String)saleOrder.get("origOrderItemNo"));
			tad.setBuyerNick((String)saleOrder.get("buyerNick"));
			tad.setDeliveryDate((Date)saleOrder.get("deliveryDate"));
			tad.setMemo((String)saleOrder.get("buyerMessage"));
			tad.setOrderAmt((BigDecimal)saleOrder.get("orderAmt"));
			tad.setOrderDate((Date)saleOrder.get("payTime"));
			tad.setPayment((BigDecimal)saleOrder.get("payment"));
			tad.setPostFee((BigDecimal)saleOrder.get("postFee"));
			tad.setReceiverName((String)saleOrder.get("receiverName"));
			tad.setTitle((String)saleOrder.get("name"));
			tad.setItemCd((String)saleOrder.get("itemCd"));
			tad.setPrice((BigDecimal)saleOrder.get("price"));
			if(WebUtil.isNotNull(saleOrder.get("qty")))
				tad.setNum(new Integer(saleOrder.get("qty").toString()));
			tad.setSkuCd((String)saleOrder.get("skuCd"));
			tad.setSubtotal((BigDecimal)saleOrder.get("realPriceAmt"));
			tad.setPostFee((BigDecimal)saleOrder.get("postFee"));
			tad.setWmsPostFee(new BigDecimal(0));
			tad.setAlipayFee(new BigDecimal(0));
			tad.setAlipayFeeDiff(new BigDecimal(0));
			tad.setCostAmt(new BigDecimal(0));
			tad.setCostPrice(new BigDecimal(0));
			tad.setGrossMargin(new BigDecimal(0));
			tad.setPointFee(new BigDecimal(0));
			tad.setPostFeeDiff(new BigDecimal(0));
			tad.setTbCommissionFee(new BigDecimal(0));
			tad.setTbkeCommissionFee(new BigDecimal(0));
			tad.setCreditCardFee(new BigDecimal(0));
			tad.setCurrentMonthRefund(new BigDecimal(0));
			tad.setOtherMonthRefund(new BigDecimal(0));
			this.taobaoAccountDetailMapper.insertSelective(tad);
		}
		//保存退货明细
		//ReportServices reportServices = (ReportServices) GetBeanServlet.getBean("reportServices");
		//List<Map> refundList = reportServices.searchRefundReport(storeId,null,null, startDate, endDate, "'REFUND_COMPLETE'","REFUND");
		List<Map> refundList = reportServicesMapper.selectRefundOrderByExample(m);
		if(!WebUtil.isNullForList(refundList))
		{
			for(Map rm:refundList)
			{
				TaobaoAccountRefund tar = new TaobaoAccountRefund();
				tar.setAccountId(accountId);
				tar.setCtime(new Date());
				tar.setOrderType("退款");
				tar.setPlatform("淘宝");
				tar.setOrderNo((String)rm.get("orderNo"));
				tar.setRefundId((String)rm.get("origOrderNo"));
				tar.setPostNo((String)rm.get("postNo"));
				tar.setTid((String)rm.get("refOrderNo"));
				tar.setBuyerNick((String)rm.get("buyerNick"));
				tar.setItemCd((String)rm.get("itemCd"));
				//tar.setMemo((String)rm.get("REFUND_DESC"));
				tar.setOrderAmt((BigDecimal)rm.get("orderAmt"));
				tar.setReceiverName((String)rm.get("receiverName"));
				tar.setRefundAmt((BigDecimal)rm.get("refundAmt"));
				if(WebUtil.isNotNull(rm.get("completeDate")))
				tar.setRefundCompleteDate(WebUtil.toDateForString(rm.get("completeDate").toString(), "yyyy-MM-dd HH:mm:ss"));
				if(WebUtil.isNotNull(rm.get("requestDate")))
				tar.setRefundRequestDate(WebUtil.toDateForString(rm.get("requestDate").toString(), "yyyy-MM-dd HH:mm:ss"));
				tar.setSkuCd((String)rm.get("skuCd"));
				tar.setPrice((BigDecimal)rm.get("price"));
				if(rm.get("qty")!=null)
				tar.setNum(new Double(rm.get("qty").toString()).intValue());
				tar.setSubTotal((BigDecimal)rm.get("subTotal"));
				tar.setTitle((String)rm.get("name"));
				try {
					this.taobaoAccountRefundMapper.insertSelective(tar);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		long time3 = System.currentTimeMillis();
		logger.info("detail:"+(time3-time2));
		//更新明细中的金额
		Map am = new HashMap();
		am.put("StoreId", storeId);
		am.put("AlipayFromDate", startDate);
		am.put("AlipayEndDate", endDate);
		am.put("AccountId", accountId);
		this.financialServicesMapper.updateTaobaoAccountDetailAlipayFee(am);
		long time4 = System.currentTimeMillis();
		logger.info("AlipayFee:"+(time4-time3));
		this.financialServicesMapper.updateTaobaoAccountDetailCreditCardFee(am);
		long time5 = System.currentTimeMillis();
		logger.info("CreditCardFee:"+(time5-time4));
		this.financialServicesMapper.updateTaobaoAccountDetailPointFee(am);
		long time6 = System.currentTimeMillis();
		logger.info("PointFee:"+(time6-time5));
		this.financialServicesMapper.updateTaobaoAccountDetailTbCommissionFee(am);
		long time7 = System.currentTimeMillis();
		logger.info("TbCommissionFee:"+(time7-time6));
		//this.financialServicesMapper.updateTaobaoAccountDetailTbkeCommissionFee(am);
		this.financialServicesMapper.updateTaobaoAccountDetailCurrentMonthRefund(am);
		long time8 = System.currentTimeMillis();
		logger.info("CurrentMonthRefund:"+(time8-time7));
		this.financialServicesMapper.updateTaobaoAccountDetailOtherMonthRefund(am);
		long time9 = System.currentTimeMillis();
		logger.info("OtherMonthRefund:"+(time9-time8));
		//查询支付宝信息计算对账信息
		Map amtMap = this.financialServicesMapper.selectTaobaoAccountDetailAmount(WebUtil.toMap("AccountId", accountId));
		if(amtMap!=null)
		{
			TaobaoAccountHead th = new TaobaoAccountHead();
			th.setAccountId(accountId);
			//订单原价金额
			if(WebUtil.isNotNull(amtMap.get("orderAmt")))
				th.setPriceAmount((BigDecimal)amtMap.get("orderAmt"));
			else
				th.setPriceAmount(new BigDecimal(0));
			//淘宝佣金总额
			if(WebUtil.isNotNull(amtMap.get("tbCommissionFee")))
				th.setTbCommissionAmount((BigDecimal)amtMap.get("tbCommissionFee"));
			else
				th.setTbCommissionAmount(new BigDecimal(0));
			//买家运费总额
			if(WebUtil.isNotNull(amtMap.get("postFee")))
				th.setReceivePostFeeAmount((BigDecimal)amtMap.get("postFee"));
			else
				th.setReceivePostFeeAmount(new BigDecimal(0));
			//订单支付总额
			if(WebUtil.isNotNull(amtMap.get("payment")))
				th.setReceivedAmount((BigDecimal)amtMap.get("payment"));
			else
				th.setReceivedAmount(new BigDecimal(0));
			//支付宝到账总额
			if(WebUtil.isNotNull(amtMap.get("alipayFee")))
				th.setAlipayReceivedAmount((BigDecimal)amtMap.get("alipayFee"));
			else
				th.setAlipayReceivedAmount(new BigDecimal(0));
			//积分支付总额
			if(WebUtil.isNotNull(amtMap.get("pointFee")))
				th.setPointAmount((BigDecimal)amtMap.get("pointFee"));
			else
				th.setPointAmount(new BigDecimal(0));
			//淘宝客佣金总额
			if(WebUtil.isNotNull(amtMap.get("tbkeCommissionFee")))
				th.setTbkeCommissionAmount((BigDecimal)amtMap.get("tbkeCommissionFee"));
			else
				th.setTbkeCommissionAmount(new BigDecimal(0));
			//信用卡费用总额
			if(WebUtil.isNotNull(amtMap.get("creditCardFee")))
				th.setCreditCardAmount((BigDecimal)amtMap.get("creditCardFee"));
			else
				th.setCreditCardAmount(new BigDecimal(0));
			//支付运费
			if(WebUtil.isNotNull(amtMap.get("wmsPostFee")))
				th.setPostFeeAmount((BigDecimal)amtMap.get("wmsPostFee"));
			else
				th.setPostFeeAmount(new BigDecimal(0));
			//销售商品成本
			if(WebUtil.isNotNull(amtMap.get("costAmt")))
				th.setSaleItemCost((BigDecimal)amtMap.get("costAmt"));
			else
				th.setSaleItemCost(new BigDecimal(0));
			//商品总额
			if(WebUtil.isNotNull(amtMap.get("subtotal")))
				th.setTotalFeeAmount((BigDecimal)amtMap.get("subtotal"));
			else
				th.setTotalFeeAmount(new BigDecimal(0));
			//销售成本=销售商品成本+积分支付总额+淘宝佣金总额+信用卡费用总额+支付运费+淘宝客佣金总额
			th.setSaleCost(th.getSaleItemCost().add(th.getPointAmount()).add(th.getTbCommissionAmount()).add(th.getCreditCardAmount()).add(th.getPostFeeAmount()).add(th.getTbkeCommissionAmount()));
			//对账时间
			th.setAlipayFromDate(WebUtil.toDateForString(startDate, "yyyy-MM-dd HH:mm:ss"));
			th.setAlipayEndDate(WebUtil.toDateForString(endDate, "yyyy-MM-dd HH:mm:ss"));
			//当月退款
			if(WebUtil.isNotNull(amtMap.get("currentMonthRefund")))
				th.setCurrentRefundFee((BigDecimal)amtMap.get("currentMonthRefund"));
			else
				th.setCurrentRefundFee(new BigDecimal(0));
			//次月退款
			if(WebUtil.isNotNull(amtMap.get("otherMonthRefund")))
				th.setOtherRefundFee((BigDecimal)amtMap.get("otherMonthRefund"));
			else
				th.setOtherRefundFee(new BigDecimal(0));
			//支付宝最晚到账时间
			if(WebUtil.isNotNull(amtMap.get("alipayEndTime")))
				th.setAlipayEndTime((Date)amtMap.get("alipayEndTime"));
			//销售单数量
			if(WebUtil.isNotNull(amtMap.get("orderNum")))
				th.setOrderNum(new Double(amtMap.get("orderNum").toString()).intValue());
			else
				th.setOrderNum(new Integer(0));
			//退货单数量
			if(WebUtil.isNotNull(amtMap.get("refundNum")))
				th.setRefundNum(new Double(amtMap.get("refundNum").toString()).intValue());
			else
				th.setRefundNum(new Integer(0));
			//销售单商品数量
			if(WebUtil.isNotNull(amtMap.get("orderItemNum")))
				th.setOrderItemNum(new Double(amtMap.get("orderItemNum").toString()).intValue());
			else
				th.setOrderItemNum(new Integer(0));
			//退货单商品数量
			if(WebUtil.isNotNull(amtMap.get("refundItemNum")))
				th.setRefundItemNum(new Double(amtMap.get("refundItemNum").toString()).intValue());
			else
				th.setRefundItemNum(new Integer(0));
			//支付宝应收合计（含运费）:【销售明细】的买家实际支付货款 - 【销售明细】当月退款合计 - 【销售明细】次月退款合计
			th.setAlipayReceivableFee(th.getReceivedAmount().subtract(th.getCurrentRefundFee()).subtract(th.getOtherRefundFee()));
			
			this.taobaoAccountHeadMapper.updateByPrimaryKeySelective(th);
		}
		//支付宝中未匹配到订单的金额
		List<Map> otherAmtList = this.financialServicesMapper.selectAlipayOtherAmount(am);
		if(!WebUtil.isNullForList(otherAmtList))
		{
			for(Map oa:otherAmtList)
			{
				if(WebUtil.isNull(oa.get("amountReceived"))&&WebUtil.isNull(oa.get("amountPaid")))
					continue;
				TaobaoAccountOther tao = new TaobaoAccountOther();
				tao.setAccountId(accountId);
				tao.setAccountType((String)oa.get("accountType"));
				BigDecimal received = (BigDecimal)oa.get("amountReceived");
				if(WebUtil.isNotNull(received)&&received.doubleValue()!=0)
				{
					tao.setAmount(received);
					tao.setIsPaid("N");
					tao.setIsRefTrade("N");
				}
				BigDecimal paid = (BigDecimal)oa.get("amountPaid");
				if(WebUtil.isNotNull(paid)&&paid.doubleValue()!=0)
				{
					tao.setAmount(paid);
					tao.setIsPaid("Y");
					tao.setIsRefTrade("N");
				}
				this.taobaoAccountOtherMapper.insertSelective(tao);
			}
		}
		long time10 = System.currentTimeMillis();
		logger.info("Amount:"+(time10-time9));
		return result;
	}
	
	public Map taobaoAccountReport(Map param)
	{
		Map result = new HashMap();
		result.put("Flag", "SUCCESS");
		Integer storeId = (Integer) param.get("StoreId");
		if(WebUtil.isNull(storeId))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无店铺ID");
			return result;
		}
		StoreServices storeServices = (StoreServices) GetBeanServlet.getBean("storeServices");
		Map storeInfo = storeServices.storeInfo(param);
		if(storeInfo.get("Flag").equals("ERROR"))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无店铺信息");
			return result;
		}
		Integer companyId = (Integer) ((Map)storeInfo.get("StoreInfo")).get("CompanyId");
		if(WebUtil.isNull(companyId))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无公司ID");
			return result;
		}
		String month = (String) param.get("Month");
//		Date alipayFromDate = (Date) param.get("AlipayFromDate");
//		Date alipayEndDate = (Date) param.get("AlipayEndDate");
		long time1 = System.currentTimeMillis();
		//查询当前月份的订单
		ReportServicesMapper reportServicesMapper = (ReportServicesMapper) GetBeanServlet.getBean("reportServicesMapper");
		Map m = new HashMap();
		m.put("StoreId", storeId);
		String startDate  = WebUtil.formatDateString(WebUtil.toDateForString(month, "yyyy-MM"),"yyyy-MM")+"-01 00:00:00";
		String endDate = WebUtil.formatDateString(WebUtil.toDateForString(month, "yyyy-MM"),"yyyy-MM")+"-31 24:00:00";
		
		m.put("StartDate",startDate);
		m.put("EndDate", endDate);
		List<Map> saleOrderList = reportServicesMapper.selectSaleOrderByExample(m);
		if(WebUtil.isNullForList(saleOrderList))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "无销售信息");
			return result;
		}

		//删除当前店铺当前月份的对账报表
		ExpandTaobaoAccountHeadExample tae = new ExpandTaobaoAccountHeadExample();
		ExpandTaobaoAccountHeadExample.Criteria tac = tae.createCriteria();
		tac.andCompanyIdEqualTo(companyId);
		tac.andStoreIdEqualTo(storeId);
		tac.andMonthEqualTo(month);
		List<TaobaoAccountHead> tahList = null;
		try {
			tahList = this.taobaoAccountHeadMapper.selectByExample(tae);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		TaobaoAccountHead tah = null;
		Map<String,Integer> detailMap = new HashMap();
		Map<String,Integer> otherMap = new HashMap();
		Map<String,Integer> refundMap = new HashMap();
		if(!WebUtil.isNullForList(tahList))
		{
			tah = tahList.get(0);
			//查找明细表
			TaobaoAccountDetailExample tade = new TaobaoAccountDetailExample();
			TaobaoAccountDetailExample.Criteria tadc = tade.createCriteria();
			tadc.andAccountIdEqualTo(tah.getAccountId());
			List<TaobaoAccountDetail> detailList = this.taobaoAccountDetailMapper.selectByExample(tade);
			if(!WebUtil.isNullForList(detailList))
			{
				for(TaobaoAccountDetail d:detailList)
					detailMap.put(d.getTid()+d.getItemCd()+d.getSkuCd(), d.getId());
			}
			//查找未匹配金额表
			TaobaoAccountOtherExample taoe = new TaobaoAccountOtherExample();
			TaobaoAccountOtherExample.Criteria taoc = taoe.createCriteria();
			taoc.andAccountIdEqualTo(tah.getAccountId());
			List<TaobaoAccountOther> otherList = this.taobaoAccountOtherMapper.selectByExample(taoe);
			if(!WebUtil.isNullForList(otherList))
			{
				for(TaobaoAccountOther o:otherList)
					otherMap.put(o.getAccountType(), o.getId());
			}
			//查找退货表
			TaobaoAccountRefundExample tare = new TaobaoAccountRefundExample();
			TaobaoAccountRefundExample.Criteria tarc = tare.createCriteria();
			tarc.andAccountIdEqualTo(tah.getAccountId());
			List<TaobaoAccountRefund> refundList = this.taobaoAccountRefundMapper.selectByExample(tare);
			if(!WebUtil.isNullForList(refundList))
			{
				for(TaobaoAccountRefund r:refundList)
					refundMap.put(r.getRefundId()+r.getItemCd()+r.getSkuCd(), r.getId());
			}
		}
		else
		{
			//建立对账主表信息
			tah = new TaobaoAccountHead();
			tah.setCompanyId(companyId);
			tah.setStoreId(storeId);
			tah.setMonth(month);
			tah.setCtime(new Date());
			this.taobaoAccountHeadMapper.insertSelective(tah);
			tahList = this.taobaoAccountHeadMapper.selectByExample(tae);
			tah = tahList.get(0);
		}
		//对账单ID
		Integer accountId = tah.getAccountId();
		//保存Tid对应的退款金额
		Map<String,Map> refundAmtMap = new HashMap();
		//保存退货明细
		//ReportServices reportServices = (ReportServices) GetBeanServlet.getBean("reportServices");
		//List<Map> refundList = reportServices.searchRefundReport(storeId,null,null, startDate, endDate, "'REFUND_COMPLETE'","REFUND");
		List<Map> refundList = reportServicesMapper.selectRefundOrderByExample(m);
		if(!WebUtil.isNullForList(refundList))
		{
			for(Map rm:refundList)
			{
				TaobaoAccountRefund tar = new TaobaoAccountRefund();
				tar.setAccountId(accountId);
				tar.setCtime(new Date());
				tar.setOrderType("退款");
				tar.setPlatform("淘宝");
				tar.setOrderNo((String)rm.get("orderNo"));
				tar.setRefundId((String)rm.get("origOrderNo"));
				tar.setPostNo((String)rm.get("postNo"));
				tar.setTid((String)rm.get("refOrderNo"));
				tar.setBuyerNick((String)rm.get("buyerNick"));
				tar.setItemCd((String)rm.get("itemCd"));
				//tar.setMemo((String)rm.get("REFUND_DESC"));
				tar.setOrderAmt((BigDecimal)rm.get("orderAmt"));
				tar.setReceiverName((String)rm.get("receiverName"));
				tar.setRefundAmt((BigDecimal)rm.get("refundAmt"));
				if(WebUtil.isNotNull(rm.get("completeDate")))
				tar.setRefundCompleteDate(WebUtil.toDateForString(rm.get("completeDate").toString(), "yyyy-MM-dd HH:mm:ss"));
				if(WebUtil.isNotNull(rm.get("requestDate")))
				tar.setRefundRequestDate(WebUtil.toDateForString(rm.get("requestDate").toString(), "yyyy-MM-dd HH:mm:ss"));
				tar.setSkuCd((String)rm.get("skuCd"));
				tar.setPrice((BigDecimal)rm.get("price"));
				if(rm.get("qty")!=null)
				tar.setNum(new Double(rm.get("qty").toString()).intValue());
				tar.setSubTotal((BigDecimal)rm.get("subTotal"));
				tar.setTitle((String)rm.get("name"));
				try {
					Integer id = refundMap.get((String)rm.get("origOrderNo")+rm.get("itemCd")+rm.get("skuCd"));
					if(id!=null)
					{
						tar.setId(id);
						this.taobaoAccountRefundMapper.updateByPrimaryKeySelective(tar);
					}
					else
						this.taobaoAccountRefundMapper.insertSelective(tar);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//查询明细对应的退货
		List<String> tidList = new ArrayList();
		for(Map saleOrder:saleOrderList)
		{
			tidList.add((String)saleOrder.get("origOrderNo"));
		}
		TaobaoAccountRefundExample tarex = new TaobaoAccountRefundExample();
		TaobaoAccountRefundExample.Criteria tarc = tarex.createCriteria();
		tarc.andTidIn(tidList);
		List<TaobaoAccountRefund> tarList = this.taobaoAccountRefundMapper.selectByExample(tarex);
		if(!WebUtil.isNullForList(tarList))
		{
			for(TaobaoAccountRefund tar:tarList)
			{
				//金额累加，区别当月退款还是次月退款
				Map<String,BigDecimal> rm = refundAmtMap.get(tar.getTid());
				if(rm==null)
					rm = new HashMap();
				BigDecimal currentRefundFee = rm.get("currentRefundFee");
				if(currentRefundFee==null)
					currentRefundFee = new BigDecimal(0);
				BigDecimal otherRefundFee = rm.get("otherRefundFee");
				if(otherRefundFee==null)
					otherRefundFee = new BigDecimal(0);
				//当月退款
				if(tar.getRefundCompleteDate()!=null&&tar.getRefundCompleteDate().compareTo(WebUtil.toDateForString(startDate, "yyyy-MM-dd HH:mm:ss"))>=0&&tar.getRefundCompleteDate().compareTo(WebUtil.toDateForString(endDate, "yyyy-MM-dd HH:mm:ss"))<0)
				{
					if(tar.getRefundAmt()!=null)
						currentRefundFee = currentRefundFee.add(tar.getRefundAmt());
				}
				//次月退款
				else
				{
					if(tar.getRefundAmt()!=null)
						otherRefundFee = otherRefundFee.add(tar.getRefundAmt());
				}
				rm.put("currentRefundFee", currentRefundFee);
				rm.put("otherRefundFee", otherRefundFee);
				refundAmtMap.put(tar.getTid(), rm);
			}
		}
		//查询5个月内的支付宝情况
		AlipayFinancialDetailExample afdex = new AlipayFinancialDetailExample();
		AlipayFinancialDetailExample.Criteria afdc = afdex.createCriteria();
		afdc.andStoreIdEqualTo(storeId);
		afdc.andCompanyIdEqualTo(companyId);
		afdc.andAlipayTimeGreaterThanOrEqualTo(WebUtil.toDateForString(WebUtil.beforeMonthDate(WebUtil.toDateForString(month+"-01", "yyyy-MM-dd"), 2)+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		afdc.andAlipayTimeLessThan(WebUtil.toDateForString(WebUtil.ofterMonthDate(WebUtil.toDateForString(month+"-31", "yyyy-MM-dd"), 2)+" 24:00:00", "yyyy-MM-dd HH:mm:ss"));
		List<AlipayFinancialDetail> alipayList = this.alipayFinancialDetailMapper.selectByExample(afdex);
		
		//整理支付宝信息
		Map<String,Map> alipayMap = new HashMap();//可以匹配订单的信息
		Map<String,BigDecimal> currentMonthAlipayMap = new HashMap();//当月未匹配支付宝金额
		if(!WebUtil.isNullForList(alipayList))
		{
			for(AlipayFinancialDetail afd:alipayList)
			{
				if(WebUtil.isNull(afd.getAccountType()))
					continue;
				if(WebUtil.isNull(afd.getTid()))
				{
					//当月支付宝金额统计
					if(afd.getAlipayTime()!=null&&afd.getAlipayTime().compareTo(WebUtil.toDateForString(startDate, "yyyy-MM-dd HH:mm:ss"))>=0&&afd.getAlipayTime().compareTo(WebUtil.toDateForString(endDate, "yyyy-MM-dd HH:mm:ss"))<0)
					{
						BigDecimal caf = currentMonthAlipayMap.get(afd.getAccountType());
						if(caf==null)
							caf = new BigDecimal(0);
						if(afd.getAmountPaid()!=null)
							caf = caf.add(afd.getAmountPaid());
						if(afd.getAmountReceived()!=null)
							caf = caf.add(afd.getAmountReceived());
						currentMonthAlipayMap.put(afd.getAccountType(), caf);
					}
					continue;
				}
				Map<String,Object> am = alipayMap.get(afd.getTid());
				if(am==null)
					am = new HashMap();
				BigDecimal apfee = (BigDecimal)am.get(afd.getAccountType());
				if(apfee==null)
					apfee = new BigDecimal(0);
				if(afd.getAmountPaid()!=null)
					apfee = apfee.add(afd.getAmountPaid().multiply(new BigDecimal(-1)));
				if(afd.getAmountReceived()!=null)
					apfee = apfee.add(afd.getAmountReceived());
				am.put(afd.getAccountType(), apfee);
				if(afd.getAccountType().equals("交易付款"))
					am.put("alipayTime", afd.getAlipayTime());
				alipayMap.put(afd.getTid(), am);
			}
		}
		//保存到对账明细表
		for(Map saleOrder:saleOrderList)
		{
			TaobaoAccountDetail tad = new TaobaoAccountDetail();
			tad.setAccountId(accountId);
			tad.setCompanyId(companyId);		
			tad.setStoreId(storeId);	
			tad.setCtime(new Date());
			tad.setPlatform("淘宝");
			tad.setOrderType("销售");
			tad.setOrderNo((String)saleOrder.get("orderNo"));
			tad.setTid((String)saleOrder.get("origOrderNo"));
			tad.setPostNo((String)saleOrder.get("postNo"));
			tad.setOid((String)saleOrder.get("origOrderItemNo"));
			tad.setBuyerNick((String)saleOrder.get("buyerNick"));
			tad.setDeliveryDate((Date)saleOrder.get("deliveryDate"));
			tad.setMemo((String)saleOrder.get("buyerMessage"));
			tad.setOrderAmt((BigDecimal)saleOrder.get("orderAmt"));
			tad.setOrderDate((Date)saleOrder.get("payTime"));
			tad.setPayment((BigDecimal)saleOrder.get("payment"));
			tad.setPostFee((BigDecimal)saleOrder.get("postFee"));
			tad.setReceiverName((String)saleOrder.get("receiverName"));
			tad.setTitle((String)saleOrder.get("name"));
			tad.setItemCd((String)saleOrder.get("itemCd"));
			tad.setPrice((BigDecimal)saleOrder.get("price"));
			if(WebUtil.isNotNull(saleOrder.get("qty")))
				tad.setNum(new Double(saleOrder.get("qty").toString()).intValue());
			tad.setSkuCd((String)saleOrder.get("skuCd"));
			tad.setSubtotal((BigDecimal)saleOrder.get("subTotal"));
			tad.setPostFee((BigDecimal)saleOrder.get("postFee"));
			//可以匹配到订单的费用
			Map<String,Object> am = alipayMap.get((String)saleOrder.get("origOrderNo"));
			if(am!=null)
			{
				if(am.get("交易付款")!=null)
				{
					tad.setAlipayFee((BigDecimal)am.get("交易付款"));
					if(tad.getPayment()!=null)
						tad.setAlipayFeeDiff(tad.getAlipayFee().subtract(tad.getPayment()));
					else
						tad.setAlipayFeeDiff(tad.getAlipayFee());
				}
				else
				{
					tad.setAlipayFee(new BigDecimal(0));
					if(tad.getPayment()==null)
						tad.setAlipayFeeDiff(new BigDecimal(0));
					else
						tad.setAlipayFeeDiff(tad.getPayment().max(new BigDecimal(-1)));
				}
				if(am.get("alipayTime")!=null)
					tad.setAlipayTime((Date)am.get("alipayTime"));
				else
					tad.setAlipayTime(null);
				if(am.get("收费")!=null)
					tad.setCreditCardFee((BigDecimal)am.get("收费"));
				else
					tad.setCreditCardFee(new BigDecimal(0));
				if(am.get("返点积分")!=null)
					tad.setPointFee((BigDecimal)am.get("返点积分"));
				else
					tad.setPointFee(new BigDecimal(0));
//				if(am.get("B2COnLine服务")!=null)
//					tad.setTbCommissionFee((BigDecimal)am.get("B2COnLine服务"));
//				else
//					tad.setTbCommissionFee(new BigDecimal(0));
				if(am.get("淘宝客佣金")!=null)
					tad.setTbkeCommissionFee((BigDecimal)am.get("淘宝客佣金"));
				else
					tad.setTbkeCommissionFee(new BigDecimal(0));
			}
			else
			{
					tad.setAlipayFee(new BigDecimal(0));
					tad.setAlipayFeeDiff(new BigDecimal(0));
					tad.setPointFee(new BigDecimal(0));
					tad.setPostFeeDiff(new BigDecimal(0));
					//tad.setTbCommissionFee(new BigDecimal(0));
					tad.setTbkeCommissionFee(new BigDecimal(0));
					tad.setCreditCardFee(new BigDecimal(0));				
				
			}
			//淘宝佣金以OID为主键
			am = alipayMap.get((String)saleOrder.get("origOrderItemNo"));
			if(am!=null)
			{
				if(am.get("B2COnLine服务")!=null)
					tad.setTbCommissionFee((BigDecimal)am.get("B2COnLine服务"));
				else
					tad.setTbCommissionFee(new BigDecimal(0));
			}
			else
				tad.setTbCommissionFee(new BigDecimal(0));
			tad.setWmsPostFee(new BigDecimal(0));
			tad.setCostAmt(new BigDecimal(0));
			tad.setCostPrice(new BigDecimal(0));
			tad.setGrossMargin(new BigDecimal(0));
			//退款
			Map<String,BigDecimal> rm = refundAmtMap.get((String)saleOrder.get("origOrderNo"));
			if(rm!=null)
			{
				if(rm.get("currentRefundFee")!=null)
					tad.setCurrentMonthRefund(rm.get("currentRefundFee"));
				else
					tad.setCurrentMonthRefund(new BigDecimal(0));
				if(rm.get("otherRefundFee")!=null)
					tad.setOtherMonthRefund(rm.get("otherRefundFee"));
				else
					tad.setOtherMonthRefund(new BigDecimal(0));
			}
			else
			{
				tad.setCurrentMonthRefund(new BigDecimal(0));
				tad.setOtherMonthRefund(new BigDecimal(0));
			}
			
			try {
				Integer id = detailMap.get((String)saleOrder.get("origOrderNo")+saleOrder.get("itemCd")+saleOrder.get("skuCd"));
				if(id!=null)
				{
					tad.setId(id);
					this.taobaoAccountDetailMapper.updateByPrimaryKeySelective(tad);
				}
				else
					this.taobaoAccountDetailMapper.insertSelective(tad);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//查询支付宝信息计算对账信息
		long tt = System.currentTimeMillis();
		Map amtMap = this.financialServicesMapper.selectTaobaoAccountDetailAmount(WebUtil.toMap("AccountId", accountId));
		logger.info("查询支付宝信息计算对账信息:"+(System.currentTimeMillis()-tt));
		if(amtMap!=null)
		{
			TaobaoAccountHead th = new TaobaoAccountHead();
			th.setAccountId(accountId);
			//订单原价金额
			if(WebUtil.isNotNull(amtMap.get("orderAmt")))
				th.setPriceAmount((BigDecimal)amtMap.get("orderAmt"));
			else
				th.setPriceAmount(new BigDecimal(0));
			//淘宝佣金总额
			if(WebUtil.isNotNull(amtMap.get("tbCommissionFee")))
				th.setTbCommissionAmount((BigDecimal)amtMap.get("tbCommissionFee"));
			else
				th.setTbCommissionAmount(new BigDecimal(0));
			//买家运费总额
			if(WebUtil.isNotNull(amtMap.get("postFee")))
				th.setReceivePostFeeAmount((BigDecimal)amtMap.get("postFee"));
			else
				th.setReceivePostFeeAmount(new BigDecimal(0));
			//订单支付总额
			if(WebUtil.isNotNull(amtMap.get("payment")))
				th.setReceivedAmount((BigDecimal)amtMap.get("payment"));
			else
				th.setReceivedAmount(new BigDecimal(0));
			//支付宝到账总额
			if(WebUtil.isNotNull(amtMap.get("alipayFee")))
				th.setAlipayReceivedAmount((BigDecimal)amtMap.get("alipayFee"));
			else
				th.setAlipayReceivedAmount(new BigDecimal(0));
			//积分支付总额
			if(WebUtil.isNotNull(amtMap.get("pointFee")))
				th.setPointAmount((BigDecimal)amtMap.get("pointFee"));
			else
				th.setPointAmount(new BigDecimal(0));
			//淘宝客佣金总额
			if(WebUtil.isNotNull(amtMap.get("tbkeCommissionFee")))
				th.setTbkeCommissionAmount((BigDecimal)amtMap.get("tbkeCommissionFee"));
			else
				th.setTbkeCommissionAmount(new BigDecimal(0));
			//信用卡费用总额
			if(WebUtil.isNotNull(amtMap.get("creditCardFee")))
				th.setCreditCardAmount((BigDecimal)amtMap.get("creditCardFee"));
			else
				th.setCreditCardAmount(new BigDecimal(0));
			//支付运费
			if(WebUtil.isNotNull(amtMap.get("wmsPostFee")))
				th.setPostFeeAmount((BigDecimal)amtMap.get("wmsPostFee"));
			else
				th.setPostFeeAmount(new BigDecimal(0));
			//销售商品成本
			if(WebUtil.isNotNull(amtMap.get("costAmt")))
				th.setSaleItemCost((BigDecimal)amtMap.get("costAmt"));
			else
				th.setSaleItemCost(new BigDecimal(0));
			//商品总额
			if(WebUtil.isNotNull(amtMap.get("subtotal")))
				th.setTotalFeeAmount((BigDecimal)amtMap.get("subtotal"));
			else
				th.setTotalFeeAmount(new BigDecimal(0));
			//销售成本=销售商品成本+积分支付总额+淘宝佣金总额+信用卡费用总额+支付运费+淘宝客佣金总额
			th.setSaleCost(th.getSaleItemCost().add(th.getPointAmount()).add(th.getTbCommissionAmount()).add(th.getCreditCardAmount()).add(th.getPostFeeAmount()).add(th.getTbkeCommissionAmount()));
			//对账时间
			th.setAlipayFromDate(WebUtil.toDateForString(startDate, "yyyy-MM-dd HH:mm:ss"));
			th.setAlipayEndDate(WebUtil.toDateForString(endDate, "yyyy-MM-dd HH:mm:ss"));
			//当月匹配退款
			if(WebUtil.isNotNull(amtMap.get("currentMonthRefund")))
				th.setCurrentRefundFee((BigDecimal)amtMap.get("currentMonthRefund"));
			else
				th.setCurrentRefundFee(new BigDecimal(0));
			//次月匹配退款
			if(WebUtil.isNotNull(amtMap.get("otherMonthRefund")))
				th.setOtherRefundFee((BigDecimal)amtMap.get("otherMonthRefund"));
			else
				th.setOtherRefundFee(new BigDecimal(0));
			//当月全部退款
			if(WebUtil.isNotNull(amtMap.get("refundFee")))
				th.setRefundFee((BigDecimal)amtMap.get("refundFee"));
			else
				th.setRefundFee(new BigDecimal(0));
			//支付宝最晚到账时间
			if(WebUtil.isNotNull(amtMap.get("alipayEndTime")))
				th.setAlipayEndTime((Date)amtMap.get("alipayEndTime"));
			//销售单数量
			if(WebUtil.isNotNull(amtMap.get("orderNum")))
				th.setOrderNum(new Double(amtMap.get("orderNum").toString()).intValue());
			else
				th.setOrderNum(new Integer(0));
			//退货单数量
			if(WebUtil.isNotNull(amtMap.get("refundNum")))
				th.setRefundNum(new Double(amtMap.get("refundNum").toString()).intValue());
			else
				th.setRefundNum(new Integer(0));
			//销售单商品数量
			if(WebUtil.isNotNull(amtMap.get("orderItemNum")))
				th.setOrderItemNum(new Double(amtMap.get("orderItemNum").toString()).intValue());
			else
				th.setOrderItemNum(new Integer(0));
			//退货单商品数量
			if(WebUtil.isNotNull(amtMap.get("refundItemNum")))
				th.setRefundItemNum(new Double(amtMap.get("refundItemNum").toString()).intValue());
			else
				th.setRefundItemNum(new Integer(0));
			//支付宝应收合计（含运费）:【销售明细】的买家实际支付货款 - 【销售明细】当月退款合计 - 【销售明细】次月退款合计
			th.setAlipayReceivableFee(th.getReceivedAmount().subtract(th.getCurrentRefundFee()).subtract(th.getOtherRefundFee()));
			
			this.taobaoAccountHeadMapper.updateByPrimaryKeySelective(th);
		}
		//支付宝中未匹配到订单的金额
		
		Iterator<String> iter = currentMonthAlipayMap.keySet().iterator();
		while(iter.hasNext())
		{
			String at = iter.next();
			BigDecimal fee = currentMonthAlipayMap.get(at);
			if(fee==null)
				fee = new BigDecimal(0);
			TaobaoAccountOther tao = new TaobaoAccountOther();
			tao.setAccountId(accountId);
			tao.setAccountType(at);
			tao.setAmount(fee);
			if(fee.doubleValue()<=0)
				tao.setIsPaid("Y");
			else
				tao.setIsPaid("N");
			tao.setIsRefTrade("N");
			try {
				Integer id = otherMap.get(at);
				if(id!=null)
				{
					tao.setId(id);
					this.taobaoAccountOtherMapper.updateByPrimaryKeySelective(tao);
				}
				else
					this.taobaoAccountOtherMapper.insertSelective(tao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		logger.info("Amount:"+(System.currentTimeMillis()-time1));
		return result;
	}
	
	public Map searchTaobaoAccountHead(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		ExpandTaobaoAccountHeadExample example = new ExpandTaobaoAccountHeadExample();
		ExpandTaobaoAccountHeadExample.Criteria ec = example.createCriteria();
		if(WebUtil.isNotNull(ul.getCompanyId()))
			ec.andCompanyIdEqualTo(ul.getCompanyId());
		if(WebUtil.isNotNull(param.get("StoreId")))
			ec.andStoreIdEqualTo((Integer)param.get("StoreId"));
		if(WebUtil.isNotNull(param.get("Month")))
			ec.andMonthEqualTo((String)param.get("Month"));
		Integer count = this.taobaoAccountHeadMapper.countByExample(example);
		String limit = null;
		if (pageRow > 0) {
		 limit = ((currentPage - 1) * pageRow)+","+pageRow;
		}
		example.setLimit(limit);
		example.setOrderByClause("month desc");
		List<TaobaoAccountHead> l = this.financialServicesMapper.selectTaobaoAccountHeadByExample(example);		
		result.put("RESULT", l);
		result.put("COUNT_ROW", count);
		return result;
	}
	
	public Map taobaoAccountHeadInfo(Map param)
	{
		Map result = new HashMap();
		result.put("Flag", "SUCCESS");
		Integer accountId = (Integer) param.get("AccountId");
		Integer storeId = (Integer) param.get("StoreId");
		String month = (String) param.get("Month");
		ExpandTaobaoAccountHeadExample ex = new ExpandTaobaoAccountHeadExample();
		ExpandTaobaoAccountHeadExample.Criteria ec = ex.createCriteria();
		if(accountId!=null)
		ec.andAccountIdEqualTo(accountId);
		if(storeId!=null)
		ec.andStoreIdEqualTo(storeId);
		if(WebUtil.isNotNull(month))
		ec.andMonthEqualTo(month);
		List<TaobaoAccountHead> tahList = this.taobaoAccountHeadMapper.selectByExample(ex);
		
		if(WebUtil.isNullForList(tahList))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "没有查到信息");
		}
		else
		{
			TaobaoAccountHead tah = tahList.get(0);
			//店铺信息
			StoreServices storeServices = (StoreServices) GetBeanServlet.getBean("storeServices");
			Map r = storeServices.storeInfo(WebUtil.toMap("StoreId", tah.getStoreId()));
			
			Map info = new HashMap();
			if(WebUtil.isNotNull(r.get("StoreInfo")))
			{
				Map storeInfo = (Map) r.get("StoreInfo");
				info.put("StoreName", storeInfo.get("StoreName"));
			}
			info.put("AccountId", tah.getAccountId());
			info.put("StoreId", tah.getStoreId());
			info.put("AlipayFromDate", tah.getAlipayFromDate());
			info.put("AlipayEndDate", tah.getAlipayEndDate());
			info.put("Month", tah.getMonth());
			//淘宝原价成交额
			info.put("PriceAmount", tah.getPriceAmount());
			//整体折扣率
			if(WebUtil.isNotNull(tah.getPriceAmount())&&WebUtil.isNotNull(tah.getTotalFeeAmount()))
				info.put("PriceDiscount", tah.getTotalFeeAmount().divide(tah.getPriceAmount(),2,BigDecimal.ROUND_HALF_UP));			
			
			//订单支付总额
			info.put("ReceivedAmount", tah.getReceivedAmount());
			//支付宝已到帐总额
			info.put("AlipayReceivedAmount", tah.getAlipayReceivedAmount());
			//差额（订单部分退款引起）
			if(WebUtil.isNotNull(tah.getReceivedAmount())&&WebUtil.isNotNull(tah.getAlipayReceivedAmount()))
			info.put("AlipayDiff", tah.getReceivedAmount().subtract(tah.getAlipayReceivedAmount()));
			//买家支付运费
			info.put("ReceivePostFeeAmount", tah.getReceivePostFeeAmount());
			//商品总额（不含运费）,明细小计合计
			info.put("TotalFeeAmount", tah.getTotalFeeAmount());
			//销售金额（不含运费）
			if(tah.getReceivePostFeeAmount()!=null)
			{
				if(tah.getReceivedAmount()!=null)
					info.put("SaleAmountNotPost", tah.getReceivedAmount().subtract(tah.getReceivePostFeeAmount()));
			}
			else
			{
				info.put("SaleAmountNotPost", tah.getReceivedAmount());
			}
			//支付宝已到帐（不含运费）
			if(WebUtil.isNotNull(tah.getAlipayReceivedAmount())&&WebUtil.isNotNull(tah.getReceivePostFeeAmount()))
			info.put("AlipayTotalFeeAmount", tah.getAlipayReceivedAmount().subtract(tah.getReceivePostFeeAmount()));
			//支付宝到帐金额比
			if(WebUtil.isNotNull(tah.getAlipayReceivedAmount())&&WebUtil.isNotNull(tah.getReceivePostFeeAmount())&&WebUtil.isNotNull(tah.getTotalFeeAmount()))
			info.put("TotalAlipayReceivedPercentage", tah.getAlipayReceivedAmount().subtract(tah.getReceivePostFeeAmount()).divide(tah.getTotalFeeAmount(),2,BigDecimal.ROUND_HALF_UP));
			//销售商品成本
			info.put("SaleItemCost", tah.getSaleItemCost());
			//商品毛利率
			if(WebUtil.isNotNull(tah.getSaleItemCost())&&WebUtil.isNotNull(tah.getAlipayReceivedAmount())&&WebUtil.isNotNull(tah.getReceivePostFeeAmount())&&tah.getReceivePostFeeAmount().doubleValue()>0&&tah.getAlipayReceivedAmount().doubleValue()>0&&tah.getSaleItemCost().doubleValue()>0)
				info.put("ItemGrossMarginPercentage", new BigDecimal(1).subtract(tah.getSaleItemCost().divide(tah.getAlipayReceivedAmount().subtract(tah.getReceivePostFeeAmount()),2,BigDecimal.ROUND_HALF_UP)));
			//销售成本（不含管理费用）
			BigDecimal saleCost = tah.getSaleItemCost();
			if(saleCost==null)
				saleCost = new BigDecimal(0);
			if(tah.getPointAmount()!=null)
				saleCost = saleCost.add(tah.getPointAmount());
			if(tah.getTbCommissionAmount()!=null)
				saleCost = saleCost.add(tah.getTbCommissionAmount());
			if(tah.getCreditCardAmount()!=null)
				saleCost = saleCost.add(tah.getCreditCardAmount());
			if(tah.getPostFeeAmount()!=null)
				saleCost = saleCost.add(tah.getPostFeeAmount());
			if(tah.getConsumablesAmount()!=null)
				saleCost = saleCost.add(tah.getConsumablesAmount());
			if(tah.getTbkeCommissionAmount()!=null)
				saleCost = saleCost.add(tah.getTbkeCommissionAmount());
			info.put("SaleCost", saleCost);
			//销售毛利
			if(WebUtil.isNotNull(info.get("SaleCost"))&&WebUtil.isNotNull(tah.getTotalFeeAmount()))
				info.put("GrossMargin", tah.getTotalFeeAmount().subtract((BigDecimal)info.get("SaleCost")));

			//赠顾客积分
			info.put("PointAmount", tah.getPointAmount());
			//淘宝佣金
			info.put("TbCommissionAmount", tah.getTbCommissionAmount());
			//信用卡手续费
			info.put("CreditCardAmount", tah.getCreditCardAmount()); 
			//手续费率
			if(WebUtil.isNotNull(tah.getCreditCardAmount())&&WebUtil.isNotNull(tah.getTotalFeeAmount())&&tah.getTotalFeeAmount().doubleValue()>0&&tah.getCreditCardAmount().doubleValue()>0)
				info.put("CreditCardPercentage", tah.getCreditCardAmount().divide(tah.getTotalFeeAmount(),4,BigDecimal.ROUND_HALF_UP));
			//支付快递费用
			info.put("PostFeeAmount", tah.getPostFeeAmount());
			//淘宝客代理佣金
			//info.put("TbkeCommissionAmount", tah.getTbkeCommissionAmount());
			//耗材
			info.put("ConsumablesAmount", tah.getConsumablesAmount());
			//当月订单数
			info.put("OrderNum", tah.getOrderNum());
			//当月退货单数
			info.put("RefundNum", tah.getRefundNum()==null?0:tah.getRefundNum()*-1);
			//当月订单商品数
			info.put("OrderItemNum", tah.getOrderItemNum());
			//当月退货单商品数
			info.put("RefundItemNum", tah.getRefundItemNum()==null?0:tah.getRefundItemNum()*-1);
			//当月成交订单:当月订单数-当月退货单数
			info.put("SaleOrderNum", (tah.getOrderNum()==null?0:tah.getOrderNum())-(tah.getRefundNum()==null?0:tah.getRefundNum()));
			//当月成交订单商品:当月订单商品数-当月退货单商品数
			info.put("SaleOrderItemNum", (tah.getOrderItemNum()==null?0:tah.getOrderItemNum())-(tah.getRefundItemNum()==null?0:tah.getRefundItemNum()));
			//当月退货金额
			info.put("CurrentRefundFee", tah.getCurrentRefundFee()==null?new BigDecimal(0):tah.getCurrentRefundFee().multiply(new BigDecimal(-1)));
			//次月退货金额
			info.put("OtherRefundFee", tah.getOtherRefundFee()==null?new BigDecimal(0):tah.getOtherRefundFee().multiply(new BigDecimal(-1)));
			//退货金额
			info.put("RefundFee", tah.getRefundFee()==null?new BigDecimal(0):tah.getRefundFee().multiply(new BigDecimal(-1)));
			//其它优惠折扣（满减）
			if(tah.getTotalFeeAmount()!=null&&info.get("SaleAmountNotPost")!=null)
			info.put("OtherPromotion", tah.getTotalFeeAmount().subtract((BigDecimal)info.get("SaleAmountNotPost")));			
			
			//当月实际销售（不含运费）:当月销售（不含运费）-当月退货
			info.put("SaleAmount", (tah.getTotalFeeAmount()==null?new BigDecimal(0):tah.getTotalFeeAmount()).subtract(tah.getCurrentRefundFee()==null?new BigDecimal(0):tah.getCurrentRefundFee()));
			//销售毛利率
			if(WebUtil.isNotNull(info.get("GrossMargin"))&&WebUtil.isNotNull(info.get("SaleAmount"))&&((BigDecimal)info.get("SaleAmount")).doubleValue()>0&&((BigDecimal)info.get("GrossMargin")).doubleValue()>0)
				info.put("SaleGrossMarginPercentage",((BigDecimal)info.get("GrossMargin")).divide((BigDecimal)info.get("SaleAmount"),2,BigDecimal.ROUND_HALF_UP));
			//支付宝应收合计（含运费）
			info.put("AlipayReceivableFee", tah.getAlipayReceivableFee());
			//支付宝到帐率:支付宝已到帐总额/支付宝应收合计（含运费）
			if(tah.getAlipayReceivedAmount()!=null&&tah.getAlipayReceivableFee()!=null)
			info.put("AlipayPercentage", tah.getAlipayReceivedAmount().divide(tah.getAlipayReceivableFee(),2,BigDecimal.ROUND_HALF_UP));
			//支付宝最晚到帐时间
			info.put("AlipayEndTime", tah.getAlipayEndTime()!=null?WebUtil.formatDateString(tah.getAlipayEndTime(), "yyyy-MM-dd HH:mm:ss"):"");
			//平均客单
			if(tah.getOrderNum()!=null&&tah.getOrderItemNum()!=null)
			{
				info.put("AverageOrderItem", new BigDecimal(tah.getOrderItemNum()).divide(new BigDecimal(tah.getOrderNum()),2,BigDecimal.ROUND_HALF_UP));
			}
			//平均客单价格
			if(tah.getOrderNum()!=null&&tah.getTotalFeeAmount()!=null)
			{
				info.put("AverageOrderFee", tah.getTotalFeeAmount().divide(new BigDecimal(tah.getOrderNum()),2,BigDecimal.ROUND_HALF_UP));
			}
			List otherReceiveList = new ArrayList();
			List otherPaidList = new ArrayList();
			TaobaoAccountOtherExample taoe = new TaobaoAccountOtherExample();
			TaobaoAccountOtherExample.Criteria taoc = taoe.createCriteria();
			taoc.andAccountIdEqualTo(tah.getAccountId());
			List<TaobaoAccountOther> taoList = this.taobaoAccountOtherMapper.selectByExample(taoe);
			if(!WebUtil.isNullForList(taoList))
			{
				for(TaobaoAccountOther tao:taoList)
				{
					//如果是已匹配淘宝订单的，暂时不打算把匹配的金额放到这里
					if(WebUtil.isNotNull(tao.getIsRefTrade())&&tao.getIsRefTrade().equals("Y"))
						continue;
					if(WebUtil.isNotNull(tao.getIsPaid())&&tao.getIsPaid().equals("Y")&&WebUtil.isNotNull(tao.getAmount()))
					{
						info.put("other-"+tao.getAccountType(), tao.getAmount().multiply(new BigDecimal(-1)));
						Map m = new HashMap();
						m.put("AccountType",tao.getAccountType());
						m.put("Amount", tao.getAmount().multiply(new BigDecimal(-1)));
						otherPaidList.add(m);
						info.put(tao.getAccountType(), tao.getAmount().multiply(new BigDecimal(-1)));
					}
					else if(WebUtil.isNotNull(tao.getAmount()))
					{
						info.put("other-"+tao.getAccountType(), tao.getAmount());	
						Map m = new HashMap();
						m.put("AccountType",tao.getAccountType());
						m.put("Amount", tao.getAmount());
						otherReceiveList.add(m);
						info.put(tao.getAccountType(), tao.getAmount());
					}
				}
			}
			info.put("OtherReceiveList", otherReceiveList);
			info.put("OtherPaidList", otherPaidList);
			result.put("TaobaoAccountInfo", info);
		}
		return result;
	}
	
	public Map searchTaobaoAccountDetail(Map param) {
		Map result = new HashMap();
		Integer accountId = (Integer) param.get("AccountId");
		TaobaoAccountDetailExample example = new TaobaoAccountDetailExample();
		TaobaoAccountDetailExample.Criteria ec = example.createCriteria();
		ec.andAccountIdEqualTo(accountId);
		example.setOrderByClause("delivery_date,tid");
		List<TaobaoAccountDetail> l = this.taobaoAccountDetailMapper.selectByExample(example);		
		result.put("TaobaoAccountDetailList", l);
		return result;
	}
	
	public Map searchTaobaoAccountRefund(Map param) {
		Map result = new HashMap();
		Integer accountId = (Integer) param.get("AccountId");
		TaobaoAccountRefundExample example = new TaobaoAccountRefundExample();
		TaobaoAccountRefundExample.Criteria ec = example.createCriteria();
		ec.andAccountIdEqualTo(accountId);
		example.setOrderByClause("refund_complete_date");
		List<TaobaoAccountRefund> l = this.taobaoAccountRefundMapper.selectByExample(example);		
		result.put("TaobaoAccountRefundList", l);
		return result;
	}
	
	public Map searchAlipayFinancialDetail(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		Date alipayFromDate = (Date) param.get("AlipayFromDate");
		Date alipayEndDate = (Date) param.get("AlipayEndDate");
		AlipayFinancialDetailExample example = new AlipayFinancialDetailExample();
		AlipayFinancialDetailExample.Criteria ec = example.createCriteria();
		ec.andStoreIdEqualTo(storeId);
		ec.andAlipayTimeGreaterThanOrEqualTo(alipayFromDate);
		ec.andAlipayTimeLessThan(alipayEndDate);
		example.setOrderByClause("alipay_time");
		List<AlipayFinancialDetail> l = this.alipayFinancialDetailMapper.selectByExample(example);		
		result.put("AlipayFinancialDetailList", l);
		return result;
	}

}
