package com.sunmw.web.action.financial;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.financial.AlipayFinancialServices;
import com.sunmw.web.domain.report.AlipayFinancialDetail;
import com.sunmw.web.domain.report.TaobaoAccountDetail;
import com.sunmw.web.domain.report.TaobaoAccountRefund;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ExportFinancialReport {
	static Logger logger = Logger.getLogger(ExportFinancialReport.class);

	private AlipayFinancialServices alipayFinancialServices;

	private String accountId;
	private String exportUrl;
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

	public String getExportUrl() {
		return exportUrl;
	}

	public void setExportUrl(String exportUrl) {
		this.exportUrl = exportUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String exportFinancialReport() {
		long time = System.currentTimeMillis();
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		exportUrl = null;
		message = null;
		if (WebUtil.isNull(accountId)) {
			message = "无报表ID";
			return "success";
		}
		Map rtn = this.alipayFinancialServices
				.taobaoAccountHeadInfo(WebUtil.toMap("AccountId",new Integer(accountId)));
		long time2 = System.currentTimeMillis();
		logger.info("search hear:"+(time2-time));
		time = time2;
		if (WebUtil.isNull(rtn) || rtn.get("Flag").equals("ERROR")) {
			message = "无报表信息";
			return "success";
		}
		Map info = (Map) rtn.get("TaobaoAccountInfo");
		Map detail = this.alipayFinancialServices
				.searchTaobaoAccountDetail(WebUtil.toMap("AccountId",
						new Integer(accountId)));
		time2 = System.currentTimeMillis();
		logger.info("search detail:"+(time2-time));
		time = time2;
		if (WebUtil.isNull(detail)
				|| WebUtil.isNullForList((List) detail
						.get("TaobaoAccountDetailList"))) {
			message = "无报表明细";
			return "success";
		}
		Map alipay = this.alipayFinancialServices
				.searchAlipayFinancialDetail(WebUtil.toMap("StoreId",
						(Integer) info.get("StoreId"), "AlipayFromDate",
						(Date) info.get("AlipayFromDate"), "AlipayEndDate",
						(Date) info.get("AlipayEndDate")));
		time2 = System.currentTimeMillis();
		logger.info("search alipay:"+(time2-time));
		time = time2;
//		if (WebUtil.isNull(alipay)
//				|| WebUtil.isNullForList((List) alipay
//						.get("AlipayFinancialDetailList"))) {
//			message = "无支付宝信息";
//			return "success";
//		}
		Map refund = this.alipayFinancialServices
				.searchTaobaoAccountRefund(WebUtil.toMap("AccountId",
						new Integer(accountId)));
		time2 = System.currentTimeMillis();
		logger.info("search refund:"+(time2-time));
		time = time2;
		List<TaobaoAccountDetail> tadList = (List) detail
				.get("TaobaoAccountDetailList");
//		List<AlipayFinancialDetail> afdList = (List) alipay
//				.get("AlipayFinancialDetailList");
		List<TaobaoAccountRefund> tarList = (List) refund
				.get("TaobaoAccountRefundList");
		String path = WebConfigProperties.getProperties("file.export.path");
		String url = WebConfigProperties.getProperties("file.export.url");
		if (ul.getCompanyId() != null) {
			path = path + ul.getCompanyId() + "/";
			url = url + ul.getCompanyId() + "/";
		}
		// 模板文件
		String template = WebConfigProperties
				.getProperties("financial.export.templete");
		File tem = new File(template);
		// 模板文件不存在
		if (!tem.exists()) {
			message = "模板文件不存在";
			return "success";
		}
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		// File file = new File(path + "tb_financial_export.xls");

		try {
			// 读模板文件
			XSSFWorkbook wbtem = new XSSFWorkbook(new FileInputStream(tem));
			XSSFSheet infows = wbtem.getSheetAt(0);
			XSSFSheet orderws = wbtem.getSheetAt(1);
			XSSFSheet refundws = wbtem.getSheetAt(2);
			// 汇总信息
			{
				XSSFRow titleRow = infows.getRow(0);
				titleRow.getCell(1).setCellValue(
						info.get("Month") + "月份" + info.get("StoreName")
								+ "销售汇总");
				Map<String, int[]> site = new HashMap();
				// 淘宝原价成交额
				site.put("PriceAmount", new int[] { 2, 2 });
				//整体折扣率
				site.put("PriceDiscount", new int[] { 2, 4 });
				//销售金额（含运费）	 
				site.put("ReceivedAmount", new int[] { 5, 2 });
				//销售数量（单）
				site.put("OrderNum", new int[] { 5, 4 });
				//销售金额（不含运费）	 
				site.put("SaleAmountNotPost", new int[] { 6, 2 });
				//销售数量（件）
				site.put("OrderItemNum", new int[] { 6, 4 });
				//商品成交金额	 
				site.put("TotalFeeAmount", new int[] { 7, 2 });
				//平均客单（件/单）
				site.put("AverageOrderItem", new int[] { 7, 4 });
				//运费收入	 
				site.put("ReceivePostFeeAmount", new int[] { 8, 2 });
				//平均客单价		
				site.put("AverageOrderFee", new int[] { 8, 4 });
				//退货金额	
				site.put("RefundFee", new int[] { 10, 2 });
				//退货数量（件）
				site.put("RefundItemNum", new int[] { 10, 4 });
				//当月实际完成商品销售	
				site.put("SaleAmount", new int[] { 11, 2 });
				//当月实际销售数量		
				site.put("SaleOrderItemNum", new int[] { 11, 4 });
				//支付宝已到帐金额	 
				site.put("AlipayReceivedAmount", new int[] { 13, 2 });
				//支付宝到帐率		
				site.put("AlipayPercentage", new int[] { 13, 4 });
				//商品生产成本	 
				//site.put("TotalFeeAmount", new int[] { 5, 2 });
				//商品毛利率		
				//site.put("TotalFeeAmount", new int[] { 5, 2 });
				//销售毛利	 
				//site.put("TotalFeeAmount", new int[] { 5, 2 });
				//销售毛利率
				//site.put("TotalFeeAmount", new int[] { 5, 2 });
				
				//赠顾客积分	 
				site.put("PointAmount", new int[] { 5, 7 });
				//费率
				//site.put("TotalFeeAmount", new int[] { 5, 9 });
				//淘宝佣金	 
				site.put("TbCommissionAmount", new int[] { 6, 7 });
				//费率
				//site.put("TotalFeeAmount", new int[] { 6, 9 });
				//信用卡手续费	 
				site.put("CreditCardAmount", new int[] { 7, 7 });
				//手续费率
				site.put("CreditCardPercentage", new int[] { 7, 9 });
				//物流宝费用	 
				site.put("物流宝", new int[] { 8, 7 });
				//支付快递费用		
				//site.put("TotalFeeAmount", new int[] { 9, 7 });
				//耗材		
				//site.put("TotalFeeAmount", new int[] { 10, 7 });
				//淘宝客代理佣金	 
				//site.put("TotalFeeAmount", new int[] { 12, 7 });
				//淘宝客比例（订单数）
				//site.put("TotalFeeAmount", new int[] { 12, 9 });
				//其它优惠折扣（满减）	
				site.put("OtherPromotion", new int[] { 13, 7 });	
				//销售支出合计
				//site.put("TotalFeeAmount", new int[] { 17, 7 });
				

				Iterator<String> iter = site.keySet().iterator();
				while (iter.hasNext()) {
					String k = iter.next();
					if (WebUtil.isNotNull(info.get(k))) {
						int[] st = site.get(k);
						if(info.get(k)==null)
						{
							infows.getRow(st[0]).getCell(st[1]).setCellValue(0);
						}
						else if(info.get(k) instanceof BigDecimal)
						{
							infows.getRow(st[0]).getCell(st[1]).setCellValue(((BigDecimal) info
											.get(k)).doubleValue());
						}
						else if(info.get(k) instanceof Integer)
						{
							infows.getRow(st[0]).getCell(st[1]).setCellValue(((Integer) info
											.get(k)));
						}		
					}
				}
				// 支付宝最晚到帐时间
				infows.getRow(19).getCell(2).setCellValue(
						info.get("AlipayEndTime") == null ? "" : info.get(
								"AlipayEndTime").toString());
			}
			time2 = System.currentTimeMillis();
			logger.info("write head:"+(time2-time));
			time = time2;
			// 销售明细
			orderws.getRow(0).getCell(1).setCellValue(
					(String) info.get("Month"));
			int r = 4;
			String orderNo = null;
			// 销售单数合计
			int orderNums = 0;
			// 销售原价合计
			BigDecimal priceAmount = new BigDecimal(0);
			// 运费合计
			BigDecimal postAmount = new BigDecimal(0);
			// 买家实际支付合计
			BigDecimal paymentAmount = new BigDecimal(0);
			// 数量合计
			Integer qty = 0;
			// 金额合计
			BigDecimal subTotalAmount = new BigDecimal(0);
			// 当月退款合计
			BigDecimal currentMonthRefund = new BigDecimal(0);
			// 次月退款合计
			BigDecimal otherMonthRefund = new BigDecimal(0);
			// 到账合计
			BigDecimal alipayAmount = new BigDecimal(0);
			// 积分合计
			BigDecimal pointAmount = new BigDecimal(0);
			// 佣金合计
			BigDecimal tbCommissionAmount = new BigDecimal(0);
			// 最晚到账时间
			Date alipayTime = null;
			// 实际运费合计
			BigDecimal wmsPostAmount = new BigDecimal(0);
			// 运费差异合计
			BigDecimal postDiffAmount = new BigDecimal(0);
			// 成本合计
			BigDecimal costAmount = new BigDecimal(0);
			// 毛利合计
			BigDecimal grossMargin = new BigDecimal(0);
			for (TaobaoAccountDetail tad : tadList) {
				XSSFRow row = orderws.createRow(r);
				if (orderNo == null || !orderNo.equals(tad.getOrderNo())) {
					row.createCell(0).setCellValue(
							WebUtil.formatDateString(tad.getOrderDate(),
									"yyyy-MM-dd HH:mm:ss"));
					row.createCell(1).setCellValue(
							WebUtil.formatDateString(tad.getDeliveryDate(),
									"yyyy-MM-dd HH:mm:ss"));
					row.createCell(2).setCellValue(tad.getOrderNo());
					row.createCell(3).setCellValue(tad.getTid());
					row.createCell(4).setCellValue(tad.getPostNo());
					row.createCell(5).setCellValue(tad.getPlatform());
					row.createCell(6).setCellValue(tad.getOrderType());
					row.createCell(7).setCellValue(
							tad.getOrderAmt() == null ? 0 : tad.getOrderAmt()
									.doubleValue());
					if (tad.getOrderAmt() != null)
						priceAmount = priceAmount.add(tad.getOrderAmt());
					row.createCell(8).setCellValue(
							tad.getPostFee() == null ? 0 : tad.getPostFee()
									.doubleValue());
					if (tad.getPostFee() != null)
						postAmount = postAmount.add(tad.getPostFee());
					row.createCell(9).setCellValue(
							tad.getPayment() == null ? 0 : tad.getPayment()
									.doubleValue());
					if (tad.getPayment() != null)
						paymentAmount = paymentAmount.add(tad.getPayment());

					row.createCell(17).setCellValue(
							tad.getCurrentMonthRefund() == null ? 0 : tad
									.getCurrentMonthRefund().doubleValue());
					if (tad.getCurrentMonthRefund() != null)
						currentMonthRefund = currentMonthRefund.add(tad
								.getCurrentMonthRefund());
					row.createCell(18).setCellValue(
							tad.getOtherMonthRefund() == null ? 0 : tad
									.getOtherMonthRefund().doubleValue());
					if (tad.getOtherMonthRefund() != null)
						otherMonthRefund = otherMonthRefund.add(tad
								.getOtherMonthRefund());
					row.createCell(20).setCellValue(
							tad.getAlipayFee() == null ? 0 : tad.getAlipayFee()
									.doubleValue());
					if (tad.getAlipayFee() != null)
						alipayAmount = alipayAmount.add(tad.getAlipayFee());
					row.createCell(21).setCellValue(
							tad.getAlipayFeeDiff() == null ? 0 : tad
									.getAlipayFeeDiff().doubleValue());
					row.createCell(22).setCellValue(
							tad.getPointFee() == null ? 0 : tad.getPointFee()
									.doubleValue());
					if (tad.getPointFee() != null)
						pointAmount = pointAmount.add(tad.getPointFee());
					
					row.createCell(24).setCellValue(
							WebUtil.formatDateString(tad.getAlipayTime(),
									"yyyy-MM-dd HH:mm:ss"));
					if (alipayTime == null)
						alipayTime = tad.getAlipayTime();
					else if (tad.getAlipayTime() != null
							&& alipayTime.before(tad.getAlipayTime()))
						alipayTime = tad.getAlipayTime();

					row.createCell(26).setCellValue(
							tad.getWmsPostFee() == null ? 0 : tad
									.getWmsPostFee().doubleValue());
					if (tad.getWmsPostFee() != null)
						wmsPostAmount = wmsPostAmount.add(tad.getWmsPostFee());
					row.createCell(27).setCellValue(
							tad.getPostFeeDiff() == null ? 0 : tad
									.getPostFeeDiff().doubleValue());
					if (tad.getPostFeeDiff() != null)
						postDiffAmount = postDiffAmount.add(tad
								.getPostFeeDiff());
					orderNo = tad.getOrderNo();
					orderNums++;
				}
				
				row.createCell(10).setCellValue(tad.getItemCd());
				row.createCell(11).setCellValue(tad.getSkuCd());
				row.createCell(12).setCellValue(tad.getTitle());
				row.createCell(13).setCellValue(
						tad.getPrice() == null ? 0 : tad.getPrice()
								.doubleValue());
				row.createCell(14).setCellValue(
						tad.getNum() == null ? 0 : tad.getNum().intValue());
				if (tad.getNum() != null)
					qty = qty + tad.getNum();
				row.createCell(15).setCellValue(
						tad.getSubtotal() == null ? 0 : tad.getSubtotal()
								.doubleValue());
				if (tad.getSubtotal() != null)
					subTotalAmount = subTotalAmount.add(tad.getSubtotal());
				//淘宝佣金
				row.createCell(23).setCellValue(
						tad.getTbCommissionFee() == null ? 0 : tad
								.getTbCommissionFee().doubleValue());
				if (tad.getTbCommissionFee() != null)
					tbCommissionAmount = tbCommissionAmount.add(tad
							.getTbCommissionFee());
				row.createCell(29).setCellValue(
						tad.getCostPrice() == null ? 0 : tad.getCostPrice()
								.doubleValue());
				row.createCell(30).setCellValue(
						tad.getCostAmt() == null ? 0 : tad.getCostAmt()
								.doubleValue());
				if (tad.getCostAmt() != null)
					costAmount = costAmount.add(tad.getCostAmt());
				row.createCell(31).setCellValue(
						tad.getGrossMargin() == null ? 0 : tad.getGrossMargin()
								.doubleValue());
				if (tad.getGrossMargin() != null)
					grossMargin = grossMargin.add(tad.getGrossMargin());
				r++;
			}
			time2 = System.currentTimeMillis();
			logger.info("write detail:"+(time2-time));
			time = time2;
			// 销售合计信息
			XSSFRow saleAmountRow = orderws.getRow(2);
			saleAmountRow.getCell(0).setCellValue(orderNums);
			saleAmountRow.getCell(7).setCellValue(priceAmount.doubleValue());
			saleAmountRow.getCell(8).setCellValue(postAmount.doubleValue());
			saleAmountRow.getCell(9).setCellValue(paymentAmount.doubleValue());
			saleAmountRow.getCell(14).setCellValue(qty);
			saleAmountRow.getCell(15)
					.setCellValue(subTotalAmount.doubleValue());
			saleAmountRow.getCell(17).setCellValue(
					currentMonthRefund.doubleValue());
			saleAmountRow.getCell(18).setCellValue(
					otherMonthRefund.doubleValue());
			saleAmountRow.getCell(20).setCellValue(alipayAmount.doubleValue());
			saleAmountRow.getCell(22).setCellValue(pointAmount.doubleValue());
			saleAmountRow.getCell(23).setCellValue(
					tbCommissionAmount.doubleValue());
			if (alipayTime != null)
				saleAmountRow.getCell(24).setCellValue(
						WebUtil.formatDateString(alipayTime,
								"yyyy-MM-dd HH:mm:ss"));
			saleAmountRow.getCell(26).setCellValue(wmsPostAmount.doubleValue());
			saleAmountRow.getCell(27)
					.setCellValue(postDiffAmount.doubleValue());
			saleAmountRow.getCell(30).setCellValue(costAmount.doubleValue());
			saleAmountRow.getCell(31).setCellValue(grossMargin.doubleValue());
			// 退款明细
			r = 4;
			refundws.getRow(0).getCell(1).setCellValue(
					(String) info.get("Month"));
			if (!WebUtil.isNullForList(tarList)) {
				// 退货单合计
				int refundNums = tarList.size();
				// 退货金额合计
				BigDecimal refundAmount = new BigDecimal(0);
				// 退货数量合计
				int refundQty = 0;
				for (TaobaoAccountRefund tar : tarList) {
					XSSFRow row = refundws.createRow(r);
					row.createCell(0).setCellValue(
							WebUtil.formatDateString(
									tar.getRefundRequestDate(),
									"yyyy-MM-dd HH:mm:ss"));
					row.createCell(1).setCellValue(
							WebUtil.formatDateString(tar
									.getRefundCompleteDate(),
									"yyyy-MM-dd HH:mm:ss"));
					row.createCell(2).setCellValue(tar.getOrderNo());
					row.createCell(3).setCellValue(tar.getRefundId());
					row.createCell(4).setCellValue(tar.getTid());
					row.createCell(5).setCellValue(tar.getPostNo());
					row.createCell(6).setCellValue(tar.getPlatform());
					row.createCell(7).setCellValue(tar.getOrderType());
					row.createCell(8).setCellValue(
							tar.getOrderAmt() == null ? 0 : tar.getOrderAmt()
									.doubleValue());
					row.createCell(9).setCellValue(
							tar.getRefundAmt() == null ? 0 : tar.getRefundAmt()
									.doubleValue());
					if (tar.getRefundAmt() != null)
						refundAmount = refundAmount.add(tar.getRefundAmt());
					row.createCell(10).setCellValue(tar.getItemCd());
					row.createCell(11).setCellValue(tar.getSkuCd());
					row.createCell(12).setCellValue(tar.getTitle());
					row.createCell(13).setCellValue(
							tar.getPrice() == null ? 0 : tar.getPrice()
									.doubleValue());
					row.createCell(14).setCellValue(
							tar.getNum() == null ? 0 : tar.getNum().intValue());
					if (tar.getNum() != null)
						refundQty = refundQty + tar.getNum();
					row.createCell(15).setCellValue(
							tar.getSubTotal() == null ? 0 : tar.getSubTotal()
									.doubleValue());
					r++;
				}
				// 退款合计信息
				XSSFRow row = refundws.getRow(2);
				row.getCell(0).setCellValue(refundNums);
				row.getCell(9).setCellValue(refundAmount.doubleValue());
				row.getCell(14).setCellValue(refundQty);
			}
			time2 = System.currentTimeMillis();
			logger.info("write refund:"+(time2-time));
			time = time2;
			FileOutputStream fileOut = new FileOutputStream(path
					+ "tb_financial_export.xlsx");
			BufferedOutputStream bos = new BufferedOutputStream(fileOut,1024);  
			wbtem.write(bos);
			fileOut.close();
			exportUrl = url + "tb_financial_export.xlsx";
			time2 = System.currentTimeMillis();
			logger.info("write end:"+(time2-time));
			time = time2;
			return "success";
		} catch (IndexOutOfBoundsException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}catch (RuntimeException e) {
			logger.error(e.getCause());
		}
		return "success";
	}

	public String exportFinancialReportbak() {

		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		exportUrl = null;
		message = null;
		if (WebUtil.isNull(accountId)) {
			message = "无报表ID";
			return "success";
		}
		Map rtn = this.alipayFinancialServices
				.taobaoAccountHeadInfo(WebUtil.toMap("AccountId",new Integer(accountId)));
		if (WebUtil.isNull(rtn) || rtn.get("Flag").equals("ERROR")) {
			message = "无报表信息";
			return "success";
		}
		Map info = (Map) rtn.get("TaobaoAccountInfo");
		Map detail = this.alipayFinancialServices
				.searchTaobaoAccountDetail(WebUtil.toMap("AccountId",
						new Integer(accountId)));
		if (WebUtil.isNull(detail)
				|| WebUtil.isNullForList((List) detail
						.get("TaobaoAccountDetailList"))) {
			message = "无报表明细";
			return "success";
		}
		Map alipay = this.alipayFinancialServices
				.searchAlipayFinancialDetail(WebUtil.toMap("StoreId",
						(Integer) info.get("StoreId"), "AlipayFromDate",
						(Date) info.get("AlipayFromDate"), "AlipayEndDate",
						(Date) info.get("AlipayEndDate")));
		if (WebUtil.isNull(alipay)
				|| WebUtil.isNullForList((List) alipay
						.get("AlipayFinancialDetailList"))) {
			message = "无支付宝信息";
			return "success";
		}
		Map refund = this.alipayFinancialServices
				.searchTaobaoAccountRefund(WebUtil.toMap("AccountId",
						new Integer(accountId)));
		List<TaobaoAccountDetail> tadList = (List) detail
				.get("TaobaoAccountDetailList");
		List<AlipayFinancialDetail> afdList = (List) alipay
				.get("AlipayFinancialDetailList");
		List<TaobaoAccountRefund> tarList = (List) refund
				.get("TaobaoAccountRefundList");
		String path = WebConfigProperties.getProperties("file.export.path");
		String url = WebConfigProperties.getProperties("file.export.url");
		if (ul.getCompanyId() != null) {
			path = path + ul.getCompanyId() + "/";
			url = url + ul.getCompanyId() + "/";
		}
		// 模板文件
		String template = WebConfigProperties
				.getProperties("financial.export.templete");
		File tem = new File(template);
		// 模板文件不存在
		if (!tem.exists()) {
			message = "模板文件不存在";
			return "success";
		}
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(path + "tb_financial_export.xls");

		try {
			if (!file.exists())
				file.createNewFile();
			// 读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			// 写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file, wbtem);
			WritableSheet infows = wwb.getSheet(0);
			WritableSheet orderws = wwb.getSheet(1);
			WritableSheet alipayws = wwb.getSheet(2);
			WritableSheet refundws = wwb.getSheet(3);
			// 汇总信息
			{
				infows.addCell(new jxl.write.Label(1, 0, info.get("Month")
						+ "月份" + info.get("StoreName") + "销售汇总"));
				Map<String, int[]> site = new HashMap();
				site.put("PriceAmount", new int[] { 2, 2 });
				site.put("PriceDiscount", new int[] { 2, 4 });
				site.put("ReceivedAmount", new int[] { 5, 2 });
				site.put("AlipayReceivedAmount", new int[] { 6, 2 });
				site.put("TotalAlipayReceivedPercentage", new int[] { 6, 4 });
				site.put("AlipayDiff", new int[] { 7, 2 });
				site.put("ReceivePostFeeAmount", new int[] { 9, 2 });
				site.put("TotalFeeAmount", new int[] { 11, 2 });
				site.put("AlipayTotalFeeAmount", new int[] { 12, 2 });
				site.put("TotalAlipayReceivedPercentage", new int[] { 12, 2 });
				site.put("SaleItemCost", new int[] { 14, 2 });
				site.put("ItemGrossMarginPercentage", new int[] { 14, 4 });
				site.put("SaleCost", new int[] { 15, 2 });
				site.put("GrossMargin", new int[] { 16, 2 });
				site.put("SaleGrossMarginPercentage", new int[] { 16, 4 });
				site.put("PointAmount", new int[] { 5, 7 });
				site.put("PointPercentage", new int[] { 5, 9 });
				site.put("TbCommissionAmount", new int[] { 6, 7 });
				site.put("TbCommissionPercentage", new int[] { 6, 9 });
				site.put("CreditCardAmount", new int[] { 7, 7 });
				site.put("CreditCardPercentage", new int[] { 7, 9 });
				site.put("PostFeeAmount", new int[] { 9, 7 });
				site.put("ConsumablesAmount", new int[] { 10, 7 });
				site.put("TbkeCommissionAmount", new int[] { 11, 7 });
				site.put("TbkeCommissionPercentage", new int[] { 11, 9 });
				Iterator<String> iter = site.keySet().iterator();
				while (iter.hasNext()) {
					String k = iter.next();
					if (WebUtil.isNotNull(info.get(k))) {
						int[] st = site.get(k);
						infows.addCell(new jxl.write.Number(st[1], st[0],
								((BigDecimal) info.get(k)).doubleValue()));
					}
				}
				if (WebUtil.isNotNull(info.get("OtherReceiveList"))) {
					List<Map> otherReceiveList = (List) info
							.get("OtherReceiveList");
					int r = 18;
					for (Map m : otherReceiveList) {
						infows.addCell(new jxl.write.Label(1, r, (String) m
								.get("AccountType")));
						infows.addCell(new jxl.write.Number(2, r,
								((BigDecimal) m.get("Amount")).doubleValue()));
						r++;
					}
				}
				if (WebUtil.isNotNull(info.get("OtherPaidList"))) {
					List<Map> OtherPaidList = (List) info.get("OtherPaidList");
					int r = 18;
					for (Map m : OtherPaidList) {
						infows.addCell(new jxl.write.Label(6, r, (String) m
								.get("AccountType")));
						infows.addCell(new jxl.write.Number(7, r,
								((BigDecimal) m.get("Amount")).doubleValue()));
						r++;
					}
				}
			}
			// 销售明细
			orderws.addCell(new jxl.write.Label(1, 0, (String) info
					.get("Month")));
			int r = 4;
			for (TaobaoAccountDetail tad : tadList) {
				orderws.addCell(new jxl.write.Label(0, r, WebUtil
						.formatDateString(tad.getOrderDate(),
								"yyyy-MM-dd HH:mm:ss")));
				orderws.addCell(new jxl.write.Label(1, r, WebUtil
						.formatDateString(tad.getDeliveryDate(),
								"yyyy-MM-dd HH:mm:ss")));
				orderws.addCell(new jxl.write.Label(2, r, tad.getTid()));
				orderws.addCell(new jxl.write.Label(3, r, tad.getPlatform()));
				orderws.addCell(new jxl.write.Label(4, r, tad.getOrderType()));
				orderws.addCell(new jxl.write.Number(5, r,
						tad.getOrderAmt() == null ? 0 : tad.getOrderAmt()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(6, r,
						tad.getPostFee() == null ? 0 : tad.getPostFee()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(7, r,
						tad.getPayment() == null ? 0 : tad.getPayment()
								.doubleValue()));
				orderws.addCell(new jxl.write.Label(8, r, tad.getItemCd()));
				orderws.addCell(new jxl.write.Label(9, r, tad.getSkuCd()));
				orderws.addCell(new jxl.write.Label(10, r, tad.getTitle()));
				orderws.addCell(new jxl.write.Number(11, r,
						tad.getPrice() == null ? 0 : tad.getPrice()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(12, r,
						tad.getNum() == null ? 0 : tad.getNum().intValue()));
				orderws.addCell(new jxl.write.Number(13, r,
						tad.getSubtotal() == null ? 0 : tad.getSubtotal()
								.doubleValue()));
				orderws.addCell(new jxl.write.Label(14, r, tad.getMemo()));
				orderws.addCell(new jxl.write.Label(15, r, tad.getBuyerNick()));
				orderws.addCell(new jxl.write.Label(16, r, tad
						.getReceiverName()));
				orderws.addCell(new jxl.write.Number(17, r,
						tad.getAlipayFee() == null ? 0 : tad.getAlipayFee()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(18, r, tad
						.getAlipayFeeDiff() == null ? 0 : tad
						.getAlipayFeeDiff().doubleValue()));
				orderws.addCell(new jxl.write.Number(19, r,
						tad.getPointFee() == null ? 0 : tad.getPointFee()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(20, r, tad
						.getTbCommissionFee() == null ? 0 : tad
						.getTbCommissionFee().doubleValue()));
				orderws.addCell(new jxl.write.Label(21, r, WebUtil
						.formatDateString(tad.getAlipayTime(),
								"yyyy-MM-dd HH:mm:ss")));
				orderws.addCell(new jxl.write.Number(22, r, tad
						.getTbkeCommissionFee() == null ? 0 : tad
						.getTbkeCommissionFee().doubleValue()));
				orderws.addCell(new jxl.write.Number(23, r,
						tad.getWmsPostFee() == null ? 0 : tad.getWmsPostFee()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(24, r, tad
						.getPostFeeDiff() == null ? 0 : tad.getPostFeeDiff()
						.doubleValue()));
				orderws.addCell(new jxl.write.Number(25, r,
						tad.getCostPrice() == null ? 0 : tad.getCostPrice()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(26, r,
						tad.getCostAmt() == null ? 0 : tad.getCostAmt()
								.doubleValue()));
				orderws.addCell(new jxl.write.Number(27, r, tad
						.getGrossMargin() == null ? 0 : tad.getGrossMargin()
						.doubleValue()));
				r++;
			}
			// 支付宝明细
			r = 2;
			alipayws.addCell(new jxl.write.Label(1, 0, WebUtil
					.formatDateString((Date) info.get("AlipayFromDate"),
							"yyyy-MM-dd")
					+ "~"
					+ WebUtil.formatDateString(
							(Date) info.get("AlipayEndDate"), "yyyy-MM-dd")));
			for (AlipayFinancialDetail afd : afdList) {
				alipayws
						.addCell(new jxl.write.Label(0, r, afd.getFinancialNo()));
				alipayws
						.addCell(new jxl.write.Label(1, r, afd.getBusinessNo()));
				alipayws.addCell(new jxl.write.Label(2, r, afd
						.getMerchantOrderNo()));
				alipayws.addCell(new jxl.write.Label(3, r, afd.getItemName()));
				alipayws.addCell(new jxl.write.Label(4, r, WebUtil
						.formatDateString(afd.getAlipayTime(),
								"yyyy-MM-dd HH:mm:ss")));
				alipayws.addCell(new jxl.write.Label(5, r, afd.getAlipayNo()));
				alipayws.addCell(new jxl.write.Number(6, r, afd
						.getAmountReceived() == null ? 0 : afd
						.getAmountReceived().doubleValue()));
				alipayws.addCell(new jxl.write.Number(7, r,
						afd.getAmountPaid() == null ? 0 : afd.getAmountPaid()
								.doubleValue()));
				alipayws.addCell(new jxl.write.Number(8, r,
						afd.getBalance() == null ? 0 : afd.getBalance()
								.doubleValue()));
				alipayws.addCell(new jxl.write.Label(9, r, afd
						.getTradeChannel()));
				alipayws.addCell(new jxl.write.Label(10, r, afd
						.getBusinessType()));
				alipayws.addCell(new jxl.write.Label(11, r, afd.getMemo()));
				r++;
			}
			// 退款明细
			r = 2;
			refundws.addCell(new jxl.write.Label(1, 0, (String) info
					.get("Month")));
			if (!WebUtil.isNullForList(tarList))
				for (TaobaoAccountRefund tar : tarList) {
					alipayws.addCell(new jxl.write.Label(0, r, WebUtil
							.formatDateString(tar.getRefundRequestDate(),
									"yyyy-MM-dd HH:mm:ss")));
					alipayws.addCell(new jxl.write.Label(1, r, WebUtil
							.formatDateString(tar.getRefundCompleteDate(),
									"yyyy-MM-dd HH:mm:ss")));
					alipayws.addCell(new jxl.write.Label(2, r, tar
							.getRefundId()));
					alipayws.addCell(new jxl.write.Label(3, r, tar.getTid()));
					alipayws.addCell(new jxl.write.Label(4, r, tar
							.getPlatform()));
					alipayws.addCell(new jxl.write.Label(5, r, tar
							.getOrderType()));
					alipayws.addCell(new jxl.write.Number(6, r, tar
							.getOrderAmt() == null ? 0 : tar.getOrderAmt()
							.doubleValue()));
					alipayws.addCell(new jxl.write.Number(7, r, tar
							.getRefundAmt() == null ? 0 : tar.getRefundAmt()
							.doubleValue()));
					alipayws.addCell(new jxl.write.Label(8, r, tar.getSkuCd()));
					alipayws.addCell(new jxl.write.Label(9, r, tar.getTitle()));
					alipayws.addCell(new jxl.write.Number(10, r,
							tar.getPrice() == null ? 0 : tar.getPrice()
									.doubleValue()));
					alipayws
							.addCell(new jxl.write.Number(11, r,
									tar.getNum() == null ? 0 : tar.getNum()
											.intValue()));
					alipayws.addCell(new jxl.write.Number(12, r, tar
							.getSubTotal() == null ? 0 : tar.getSubTotal()
							.doubleValue()));
					alipayws.addCell(new jxl.write.Label(13, r, tar.getMemo()));
					alipayws.addCell(new jxl.write.Label(14, r, tar
							.getBuyerNick()));
					alipayws.addCell(new jxl.write.Label(15, r, tar
							.getReceiverName()));
					r++;
				}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
			exportUrl = url + "tb_financial_export.xls";
			return "success";
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
}
