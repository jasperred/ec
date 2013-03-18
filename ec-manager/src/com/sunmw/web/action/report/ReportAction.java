package com.sunmw.web.action.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.report.ReportServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ReportAction {
	static Logger logger = Logger.getLogger(ReportAction.class);

	private ReportServices reportServices;

	private int storeId;
	private Date fromDate;
	private Date endDate;
	private Date completeFromDate;
	private Date completeEndDate;

	private List fileList;
	private int rowCount;
	private String filePath;
	private String fileUrl;

	private String message;
	private boolean success;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
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

	public ReportServices getReportServices() {
		return reportServices;
	}

	public void setReportServices(ReportServices reportServices) {
		this.reportServices = reportServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCompleteFromDate() {
		return completeFromDate;
	}

	public void setCompleteFromDate(Date completeFromDate) {
		this.completeFromDate = completeFromDate;
	}

	public Date getCompleteEndDate() {
		return completeEndDate;
	}

	public void setCompleteEndDate(Date completeEndDate) {
		this.completeEndDate = completeEndDate;
	}

	// 日销售报表
	public String daySaleReport() {
		Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
		String d = WebUtil.formatDateString(date, "yyyy-MM-dd");
		List<Map> l = this.reportServices.searchSaleReport(storeId, d
				+ " 00:00:00", d + " 23:59:59", null, null,
				"'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(date, storeId, d, l);
		message = "";
		success = true;
		return "success";
	}

	// 周销售报表
	public String weekSaleReport() {
		Date fromDate = new Date(System.currentTimeMillis() - 24 * 60 * 60
				* 1000);
		Date endDate = new Date(System.currentTimeMillis() - 8 * 24 * 60 * 60
				* 1000);
		String d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
				+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices.searchSaleReport(storeId, WebUtil
				.formatDateString(fromDate, "yyyy-MM-dd")
				+ " 00:00:00", WebUtil.formatDateString(endDate, "yyyy-MM-dd")
				+ " 23:59:59", null, null, "'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(new Date(), storeId, d, l);
		message = "";
		success = true;
		return "success";
	}

	// 销售报表
	public String searchSaleReport() {
		String d = null;
		if (WebUtil.isNotNull(fromDate))
			d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		else
			d = WebUtil.formatDateString(completeFromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(completeEndDate, "yyyy-MM-dd");

		logger.info("报表开始执行，店铺【" + storeId + "】，日期【" + d + "】");
		Date date = new Date();
		List<Map> l = this.reportServices
				.searchSaleReport(storeId, fromDate == null ? null : WebUtil
						.formatDateString(fromDate, "yyyy-MM-dd")
						+ " 00:00:00", endDate == null ? null : WebUtil
						.formatDateString(endDate, "yyyy-MM-dd")
						+ " 23:59:59", completeFromDate == null ? null
						: WebUtil.formatDateString(completeFromDate,
								"yyyy-MM-dd")
								+ " 00:00:00", completeEndDate == null ? null
						: WebUtil.formatDateString(completeEndDate,
								"yyyy-MM-dd")
								+ " 23:59:59", "'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(date, storeId, d, l);
		logger.info("报表执行成功");
		message = "";
		success = true;
		return "success";
	}

	// 退款报表
	public String searchRefundReport() {
		String d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
				+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		if (WebUtil.isNull(fromDate))
			d = WebUtil.formatDateString(completeFromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(completeEndDate, "yyyy-MM-dd");
		logger.info("退款报表开始执行，店铺【" + storeId + "】，日期【" + d + "】");
		Date date = new Date();
		List<Map> l = this.reportServices.searchRefundReport(storeId,
				fromDate == null ? null : WebUtil
						.formatDateString(fromDate, "yyyy-MM-dd")
						+ " 00:00:00", endDate == null ? null : WebUtil
						.formatDateString(endDate, "yyyy-MM-dd")
						+ " 23:59:59", completeFromDate == null ? null
						: WebUtil.formatDateString(completeFromDate,
								"yyyy-MM-dd")
								+ " 00:00:00", completeEndDate == null ? null
						: WebUtil.formatDateString(completeEndDate,
								"yyyy-MM-dd")
								+ " 23:59:59",
				"'REFUND_COMPLETE'", "REFUND");
		exportRefundReport(date, storeId, d, l);
		logger.info("退款报表执行成功");
		message = "";
		success = true;
		return "success";
	}

	// 销售报表导出
	private void exportSaleReport(Date date, int storeId, String d, List<Map> l) {
		if (l == null || l.size() == 0)
			return;
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			// 文件名称
			String fileName = "sale_report_" + storeId + "_"
					+ WebUtil.formatDateString(date, "yyyyMMddHHmmss") + ".xls";
			String path = WebConfigProperties.getProperties("report.sale.path");
			// 分不同公司路径
			if (WebUtil.isNotNull(ul.getCompanyId())) {
				path = path + ul.getCompanyId() + "/";
			}
			// 模板文件
			String template = WebConfigProperties
					.getProperties("report.sale.template");
			File tem = new File(template);
			// 模板文件不存在
			if (!tem.exists()) {

			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);

			if (!file.exists())
				file.createNewFile();
			// 读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			// 写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file, wbtem);
			WritableSheet ws = wwb.getSheet(0);
			// 写日期
			ws.addCell(new jxl.write.Label(1, 0, d));
			int r = 2;// 从第3行开始写
			String orderNo = null;
			String[] keys = new String[] { "PayTime", "DeliveryDate",
					"OrderNo", "OrigOrderNo", "PostNo", "#淘宝", "#销售",
					"OrderAmt", "FreightAmt", "PaymentAmt", "OrderItem.ItemCd","OrderItem.SkuCd",
					"OrderItem.Name", "OrderItem.Price", "OrderItem.Qty",
					"OrderItem.SubTotal", "BUYER_MESSAGE", "BuyerNick",
					"ReceiverName", "ReceiverMobile", "ReceiverPhone",
					"ReceiverState", "ReceiverCity", "ReceiverDistrict",
					"ReceiverAddress", "ReceiverZip" };
			for (Map m : l) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i].indexOf(".") >= 0)
						continue;
					if (keys[i].indexOf("#") >= 0) {
						ws.addCell(new jxl.write.Label(i, r, keys[i]
								.replaceAll("#", "")));
					} else if (WebUtil.isNull(m.get(keys[i]))) {
						ws.addCell(new jxl.write.Label(i, r, ""));
					} else if (m.get(keys[i]) instanceof java.math.BigDecimal) {
						ws
								.addCell(new jxl.write.Number(i, r, m
										.get(keys[i]) == null ? 0 : Double
										.parseDouble(m.get(keys[i]).toString())));
					} else {
						ws.addCell(new jxl.write.Label(i, r,
								m.get(keys[i]) == null ? "" : m.get(keys[i])
										.toString()));
					}
				}
				List<Map> orderItemList = (List) m.get("OrderItem");
				if (!WebUtil.isNullForList(orderItemList)) {
					for (Map item : orderItemList) {
						for (int i = 0; i < keys.length; i++) {
							if (keys[i].indexOf(".") < 0)
								continue;
							String k = keys[i]
									.substring(keys[i].indexOf(".") + 1);
							if (keys[i].indexOf("#") >= 0) {
								ws.addCell(new jxl.write.Label(i, r, keys[i]
										.replaceAll("#", "")));
							} else if (WebUtil.isNull(item.get(k))) {
								ws.addCell(new jxl.write.Label(i, r, ""));
							} else if (item.get(k) instanceof java.math.BigDecimal) {
								ws.addCell(new jxl.write.Number(i, r, item
										.get(k) == null ? 0 : Double
										.parseDouble(item.get(k).toString())));
							} else {
								ws.addCell(new jxl.write.Label(i, r, item
										.get(k) == null ? "" : item.get(k)
										.toString()));
							}
						}
						r++;
					}
				} else
					r++;
			}
			// for (Map m : l) {
			// for(int i=0;i<keys.length;i++)
			// {
			// if(keys[i].equals("OrderAmt")||keys[i].equals("FreightAmt")||keys[i].equals("PaymentAmt"))
			// {
			// ws.addCell(new jxl.write.Number(i,
			// r,m.get(keys[i])==null?0:Double.parseDouble(m.get(keys[i]).toString())));
			// }
			// else
			// ws.addCell(new jxl.write.Label(i,
			// r,m.get(keys[i])==null?"":m.get(keys[i]).toString()));
			// }
			// List<Map> itemList = (List)m.get("OrderItem");
			// if(itemList!=null&&itemList.size()>0)
			// {
			// for(Map i:itemList)
			// {
			// ws.addCell(new jxl.write.Label(9,
			// r,i.get("SkuCd")==null?"":i.get("SkuCd").toString()));
			// ws.addCell(new jxl.write.Label(10,
			// r,i.get("Name")==null?"":i.get("Name").toString()));
			// ws.addCell(new jxl.write.Number(11,
			// r,i.get("Price")==null?0:Double.parseDouble(i.get("Price").toString())));
			// ws.addCell(new jxl.write.Number(12,
			// r,i.get("Qty")==null?0:Double.parseDouble(i.get("Qty").toString())));
			// ws.addCell(new jxl.write.Number(13,
			// r,i.get("SubTotal")==null?0:Double.parseDouble(i.get("SubTotal").toString())));
			// r++;
			// }
			// }
			// else
			// r++;
			// }
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
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
	}

	// 退款报表导出
	private void exportRefundReport(Date date, int storeId, String d,
			List<Map> l) {
		if (l == null || l.size() == 0)
			return;
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			// 文件名称
			String fileName = "refund_report_" + storeId + "_"
					+ WebUtil.formatDateString(date, "yyyyMMddHHmmss") + ".xls";
			String path = WebConfigProperties
					.getProperties("report.refund.path");
			// 分不同公司路径
			if (WebUtil.isNotNull(ul.getCompanyId())) {
				path = path + ul.getCompanyId() + "/";
			}
			// 模板文件
			String template = WebConfigProperties
					.getProperties("report.refund.template");
			File tem = new File(template);
			// 模板文件不存在
			if (!tem.exists()) {

			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);

			if (!file.exists())
				file.createNewFile();
			// 读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			// 写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file, wbtem);
			WritableSheet ws = wwb.getSheet(0);
			// 写日期
			ws.addCell(new jxl.write.Label(1, 0, d));
			int r = 2;// 从第4行开始写
			String orderNo = null;
			String[] keys = new String[] { "RequestDate", "CompleteDate",
					"OrderNo", "OrigOrderNo", "RefOrderNo", "OrigPostNo",
					"#淘宝", "#退货", "OrderAmt", "RefundAmt", "OrderItem.ItemCd","OrderItem.SkuCd",
					"OrderItem.Name", "OrderItem.Price", "OrderItem.Qty",
					"OrderItem.SubTotal", "REFUND_DESC", "BuyerNick",
					"ReceiverName", "ReceiverMobile", "ReceiverPhone",
					"ReceiverState", "ReceiverCity", "ReceiverDistrict",
					"ReceiverAddress", "ReceiverZip" };
			for (Map m : l) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i].indexOf(".") >= 0)
						continue;
					if (keys[i].indexOf("#") >= 0) {
						ws.addCell(new jxl.write.Label(i, r, keys[i]
								.replaceAll("#", "")));
					} else if (WebUtil.isNull(m.get(keys[i]))) {
						ws.addCell(new jxl.write.Label(i, r, ""));
					} else if (m.get(keys[i]) instanceof java.math.BigDecimal) {
						ws
								.addCell(new jxl.write.Number(i, r, m
										.get(keys[i]) == null ? 0 : Double
										.parseDouble(m.get(keys[i]).toString())));
					} else {
						ws.addCell(new jxl.write.Label(i, r,
								m.get(keys[i]) == null ? "" : m.get(keys[i])
										.toString()));
					}
				}
				List<Map> orderItemList = (List) m.get("OrderItem");
				if (!WebUtil.isNullForList(orderItemList)) {
					for (Map item : orderItemList) {
						for (int i = 0; i < keys.length; i++) {
							if (keys[i].indexOf(".") < 0)
								continue;
							String k = keys[i]
									.substring(keys[i].indexOf(".") + 1);
							if (keys[i].indexOf("#") >= 0) {
								ws.addCell(new jxl.write.Label(i, r, keys[i]
										.replaceAll("#", "")));
							} else if (WebUtil.isNull(item.get(k))) {
								ws.addCell(new jxl.write.Label(i, r, ""));
							} else if (item.get(k) instanceof java.math.BigDecimal) {
								ws.addCell(new jxl.write.Number(i, r, item
										.get(k) == null ? 0 : Double
										.parseDouble(item.get(k).toString())));
							} else {
								ws.addCell(new jxl.write.Label(i, r, item
										.get(k) == null ? "" : item.get(k)
										.toString()));
							}
						}
						r++;
					}
				} else
					r++;
			}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
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
	}

	// 份别销售报表
	public String searchSaleReportByState() {
		Date date = new Date();
		String d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
				+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices
				.searchSaleReportByState(storeId, fromDate == null ? null
						: WebUtil.formatDateString(fromDate, "yyyy-MM-dd")
								+ " 00:00:00", endDate == null ? null : WebUtil
						.formatDateString(endDate, "yyyy-MM-dd")
						+ " 23:59:59", completeFromDate == null ? null
						: WebUtil.formatDateString(completeFromDate,
								"yyyy-MM-dd")
								+ " 00:00:00", completeEndDate == null ? null
						: WebUtil.formatDateString(completeEndDate,
								"yyyy-MM-dd")
								+ " 23:59:59", "'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(date, d, new String[] { "State", "Order", "Qty",
				"OrderAmt", "Post", "PostFee" }, "sale_report_state_",
				"report.sale.path", "report.sale.state.template", l);
		message = "";
		success = true;
		return "success";
	}

	// 周省份别销售报表
	public String weekSaleReportByState() {
		Date fromDate = new Date(System.currentTimeMillis() - 24 * 60 * 60
				* 1000);
		Date endDate = new Date(System.currentTimeMillis() - 8 * 24 * 60 * 60
				* 1000);
		String d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
				+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices.searchSaleReportByState(storeId,
				WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + " 00:00:00",
				WebUtil.formatDateString(endDate, "yyyy-MM-dd") + " 23:59:59",
				null, null, "'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(new Date(), d, new String[] { "State", "Order", "Qty",
				"OrderAmt", "Post", "PostFee" }, "sale_report_state_",
				"report.sale.path", "report.sale.state.template", l);
		message = "";
		success = true;
		return "success";
	}

	// SKU别销售报表
	public String searchSaleReportBySku() {
		Date date = new Date();
		String d = null;
		if (WebUtil.isNotNull(fromDate))
			d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		else
			d = WebUtil.formatDateString(completeFromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(completeEndDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices
				.searchSaleReportBySku(storeId, fromDate == null ? null
						: WebUtil.formatDateString(fromDate, "yyyy-MM-dd")
								+ " 00:00:00", endDate == null ? null : WebUtil
						.formatDateString(endDate, "yyyy-MM-dd")
						+ " 23:59:59", completeFromDate == null ? null
						: WebUtil.formatDateString(completeFromDate,
								"yyyy-MM-dd")
								+ " 00:00:00", completeEndDate == null ? null
						: WebUtil.formatDateString(completeEndDate,
								"yyyy-MM-dd")
								+ " 23:59:59", "'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(date, d, new String[] {"ItemCd", "SkuCd", "SkuName", "Price",
				"Order", "Qty", "OrderAmt" }, "sale_report_sku_",
				"report.sale.path", "report.sale.sku.template", l);
		message = "";
		success = true;
		return "success";
	}

	// 周SKU别销售报表
	public String weekSaleReportBySku() {
		Date fromDate = new Date(System.currentTimeMillis() - 24 * 60 * 60
				* 1000);
		Date endDate = new Date(System.currentTimeMillis() - 8 * 24 * 60 * 60
				* 1000);
		String d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
				+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices.searchSaleReportBySku(storeId,
				WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + " 00:00:00",
				WebUtil.formatDateString(endDate, "yyyy-MM-dd") + " 23:59:59",
				null, null, "'DELIVERY','COMPLETE'", "ORDER");
		exportSaleReport(new Date(), d, new String[] {"ItemCd", "SkuCd", "SkuName",
				"Price", "Order", "Qty", "OrderAmt" }, "sale_report_sku_",
				"report.sale.path", "report.sale.sku.template", l);
		message = "";
		success = true;
		return "success";
	}

	// 周SKU别退款报表
	public String searchRefundReportBySku() {
		String d = null;
		if (WebUtil.isNotNull(fromDate))
			d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		else
			d = WebUtil.formatDateString(completeFromDate, "yyyy-MM-dd") + "~"
					+ WebUtil.formatDateString(completeEndDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices.searchRefundReportBySku(storeId,
				fromDate == null ? null
						: WebUtil.formatDateString(fromDate, "yyyy-MM-dd")
								+ " 00:00:00", endDate == null ? null : WebUtil
						.formatDateString(endDate, "yyyy-MM-dd")
						+ " 23:59:59", completeFromDate == null ? null
						: WebUtil.formatDateString(completeFromDate,
								"yyyy-MM-dd")
								+ " 00:00:00", completeEndDate == null ? null
						: WebUtil.formatDateString(completeEndDate,
								"yyyy-MM-dd")
								+ " 23:59:59",
				"'REFUND_COMPLETE'", "REFUND");
		exportSaleReport(new Date(), d, new String[] {"ItemCd", "SkuCd", "SkuName",
				"Price", "Order", "Qty", "OrderAmt" }, "refund_report_sku_",
				"report.refund.path", "report.refund.sku.template", l);
		message = "";
		success = true;
		return "success";
	}

	// 周SKU别退款报表
	public String weekRefundReportBySku() {
		Date fromDate = new Date(System.currentTimeMillis() - 24 * 60 * 60
				* 1000);
		Date endDate = new Date(System.currentTimeMillis() - 8 * 24 * 60 * 60
				* 1000);
		String d = WebUtil.formatDateString(fromDate, "yyyy-MM-dd") + "~"
				+ WebUtil.formatDateString(endDate, "yyyy-MM-dd");
		List<Map> l = this.reportServices.searchRefundReportBySku(storeId,
				WebUtil.formatDateString(fromDate, "yyyy-MM-dd"), WebUtil
						.formatDateString(endDate, "yyyy-MM-dd"), null, null,
				"'REFUND_COMPLETE'", "REFUND");
		exportSaleReport(new Date(), d, new String[] {"ItemCd", "SkuCd", "SkuName",
				"Price", "Order", "Qty", "OrderAmt" }, "refund_report_sku_",
				"report.refund.path", "report.refund.sku.template", l);
		message = "";
		success = true;
		return "success";
	}

	private void exportSaleReport(Date date, String d, String[] keys,
			String prefix, String filepath, String template, List<Map> l) {
		if (l == null || l.size() == 0)
			return;
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			// 文件名称
			String fileName = prefix
					+ WebUtil.formatDateString(date, "yyyyMMddHHmmss") + ".xls";
			String path = WebConfigProperties.getProperties(filepath);
			// 分不同公司路径
			if (WebUtil.isNotNull(ul.getCompanyId())) {
				path = path + ul.getCompanyId() + "/";
			}
			// 模板文件
			String t = WebConfigProperties.getProperties(template);
			File tem = new File(t);
			// 模板文件不存在
			if (!tem.exists()) {

			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);

			if (!file.exists())
				file.createNewFile();
			// 读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			// 写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file, wbtem);
			WritableSheet ws = wwb.getSheet(0);
			// 写日期
			ws.addCell(new jxl.write.Label(1, 0, d));
			int r = 2;// 从第3行开始写
			String orderNo = null;
			for (Map m : l) {
				for (int i = 0; i < keys.length; i++) {
					if (m.get(keys[i]) instanceof java.lang.Double
							|| m.get(keys[i]) instanceof java.lang.Integer) {
						ws
								.addCell(new jxl.write.Number(i, r, m
										.get(keys[i]) == null ? 0 : Double
										.parseDouble(m.get(keys[i]).toString())));
					} else {
						ws.addCell(new jxl.write.Label(i, r,
								m.get(keys[i]) == null ? "" : m.get(keys[i])
										.toString()));
					}
				}
				r++;
			}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
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
	}

	// 订单导出,WMS文件列表
	public String reportFileList() {
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		this.rowCount = 0;
		if (fileList == null)
			fileList = new ArrayList();
		fileList.clear();
		String dir = WebConfigProperties.getProperties(filePath);
		if (dir == null)
			return "success";

		// 分不同公司路径
		if (WebUtil.isNotNull(ul.getCompanyId())) {
			dir = dir + ul.getCompanyId() + "/";
		}
		File filedir = new File(dir);
		// 主目录列表
		File[] files = filedir.listFiles();
		if (files == null) {
			return "success";
		}
		String url = WebConfigProperties.getProperties(fileUrl);
		// 分不同公司路径
		if (WebUtil.isNotNull(ul.getCompanyId())) {
			url = url + ul.getCompanyId() + "/";
		}
		// 目录分类,DRIVER/TRACK/TEAM/HISTORY
		for (File file : files) {
			Map m = new HashMap();
			m.put("fileName", file.getName());
			m.put("fileDate", WebUtil.formatDateString(new Date(file
					.lastModified()), "yyyy-MM-dd HH:mm:ss"));
			m.put("filePath", file.getAbsolutePath());
			m.put("fileUrl", url + file.getName());
			fileList.add(m);
		}
		Collections.sort(fileList, new Comparator<Map<String, String>>() {

			public int compare(Map<String, String> o1, Map<String, String> o2) {
				if (o1.get("fileDate").compareTo(o2.get("fileDate")) < 0)
					return 1;
				return 0;
			}
		});
		this.rowCount = fileList.size();
		return "success";
	}

	public String viewReport() {
		return "success";
	}
}
