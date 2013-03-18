package com.sunmw.web.bean.dataInterface.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunmw.web.bean.dataInterface.WmsInterfaceServices;
import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class WmsInterfaceServicesImpl implements WmsInterfaceServices {

	static Logger logger = Logger.getLogger(WmsInterfaceServicesImpl.class);

	@Override
	// 需要在接口中配置FilePath、FileName参数
	public Map exportDeliveryRequest(Map param) {

		long ctm = System.currentTimeMillis();
		logger.info("接口【导出发货指示】开始执行");
		Map result = new HashMap();
		result.put("Flag", "success");
		OrderServices orderServices = (OrderServices) GetBeanServlet
				.getBean("orderServices");
		Map m = orderServices.exportDeliveryRequest(param);
		List orderList = (List) m.get("OrderList");
		if (WebUtil.isNullForList(orderList)) {
			result.put("Message", "无需要导出的出库指示");
			logger.info("无需要导出的出库指示");
			return result;
		}
		String path = WebConfigProperties.getProperties("order.to_wms.path");
		if (WebUtil.isNotNull(param.get("FilePath")))
			path = (String) param.get("FilePath");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId")))
				path = path + storeInfo.get("CompanyId") + "/";
		}
		String fileName = "DeliveryRequest";
		if (WebUtil.isNotNull(param.get("FileName")))
			fileName = (String) param.get("FileName");
		fileName = fileName
				+ WebUtil.formatDateString(new Date(), "yyyyMMddHHmm") + ".csv";
		String[] fields = new String[] { "StoreId", "Flag", "OrderNo",
				"OrigOrderNo", "BuyerNick", "TotalFee", "PostFee",
				"PaymentPoint", "OrderStatus", "ReceiverName", "ReceiverState",
				"ReceiverCity", "ReceiverDistrict", "ReceiverZip",
				"ReceiverAddress", "PostMethod", "ReceiverMobile",
				"ReceiverTel", "OrderDate", "Memo", "OrderItemId", "Title",
				"SkuCd", "ColorName", "SizeName", "Price", "Qty", "SubTotal" };
		WebUtil.exportCSV(fields, fields, orderList, path, fileName);
		logger.info("接口【导出发货指示】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map exportItemMaster(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【导出商品信息】开始执行");
		Map result = new HashMap();
		logger.info("接口【导出商品信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map exportReturnRequest(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【导出退货指示】开始执行");
		Map result = new HashMap();
		result.put("Flag", "success");
		OrderServices orderServices = (OrderServices) GetBeanServlet
				.getBean("orderServices");
		Map m = orderServices.exportReturnRequest(param);
		List orderList = (List) m.get("ReturnResultList");
		if (WebUtil.isNullForList(orderList)) {
			result.put("Message", "无需要导出的退货指示");
			return result;
		}
		String path = WebConfigProperties.getProperties("refund.export.path");
		if (WebUtil.isNotNull(param.get("FilePath")))
			path = (String) param.get("FilePath");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId")))
				path = path + storeInfo.get("CompanyId") + "/";
		}
		String fileName = "ReturnRequest";
		if (WebUtil.isNotNull(param.get("FileName")))
			fileName = (String) param.get("FileName");
		fileName = fileName
				+ WebUtil.formatDateString(new Date(), "yyyyMMddHHmm") + ".csv";
		String[] fields = new String[] { "StoreId", "Flag", "OrderNo",
				"OrigOrderNo", "BuyerNick", "TotalFee", "PostFee",
				"PaymentPoint", "OrderStatus", "ReceiverName", "ReceiverState",
				"ReceiverCity", "ReceiverDistrict", "ReceiverZip",
				"ReceiverAddress", "PostMethod", "ReceiverMobile",
				"ReceiverTel", "OrderDate", "Memo", "OrderItemId", "Title",
				"SkuCd", "ColorName", "SizeName", "Price", "Qty", "SubTotal" };
		WebUtil.exportCSV(fields, fields, orderList, path, fileName);
		logger.info("接口【导出退货指示】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	// 需要在接口中配置FilePath参数
	public Map importDeliveryResult(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【导入发货实绩】开始执行");
		Map result = new HashMap();
		result.put("Flag", "success");
		if (WebUtil.isNull(param.get("StoreId"))) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID没有传入");
			logger.info("店铺ID没有传入");
			return result;
		}
		String path = WebConfigProperties.getProperties("order.to_erp.path");
		String bakpath = WebConfigProperties
				.getProperties("order.to_erp.bak.path");
		if (WebUtil.isNotNull(param.get("FilePath")))
			path = (String) param.get("FilePath");
		if (WebUtil.isNotNull(param.get("BakFilePath")))
			bakpath = (String) param.get("BakFilePath");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
				path = path + storeInfo.get("CompanyId") + "/";
				bakpath = bakpath + storeInfo.get("CompanyId") + "/";
			}
		}
		File directory = new File(path);
		if (!directory.isDirectory()) {
			result.put("Flag", "error");
			result.put("Message", "出库实绩目录不存在");
			logger.info("出库实绩目录不存在");
			return result;
		}
		File[] files = directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().toLowerCase().endsWith(".csv"))
					return true;
				return false;
			}
		});
		if (files == null || files.length == 0 || files[0] == null) {
			result.put("Flag", "success");
			result.put("Message", "无出库实绩文件");
			logger.info("无出库实绩文件" + path);
			return result;
		}
		OrderServices orderServices = (OrderServices) GetBeanServlet
				.getBean("orderServices");
		for (File file : files) {
			List<Map> l = WebUtil.readerCsv(file, true);
			if (WebUtil.isNullForList(l)) {
				logger.info(file.getName() + ":文件没有内容");
				continue;
			}
			// 导入出库实绩
			orderServices.importWmsDelivery(WebUtil.toMap("StoreId", param
					.get("StoreId"), "DeliveryResultList", l));
			// 备份文件
			File bakFile = new File(bakpath + file.getName());
			File bakDir = new File(bakpath);
			try {
				if(!bakDir.exists())
					bakDir.mkdirs();
				WebUtil.fileCopy(file, bakFile);
				System.gc();
				System.gc();
				file.delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		logger.info("接口【导入发货实绩】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map importInventory(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【导入库存】开始执行");
		Map result = new HashMap();
		result.put("Flag", "success");
		if (WebUtil.isNull(param.get("StoreId"))) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID没有传入");
			return result;
		}
		String path = WebConfigProperties.getProperties("inventory.import.path");
		if (WebUtil.isNotNull(param.get("FilePath")))
			path = (String) param.get("FilePath");
		String bakpath = WebConfigProperties
				.getProperties("inventory.bak.path");
		if (WebUtil.isNotNull(param.get("BakFilePath")))
			bakpath = (String) param.get("BakFilePath");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
				path = path + storeInfo.get("CompanyId") + "/";
				bakpath = bakpath + storeInfo.get("CompanyId") + "/";
			}
		}
		File directory = new File(path);
		if (!directory.isDirectory()) {
			result.put("Flag", "error");
			result.put("Message", "库存目录不存在");
			return result;
		}
		File[] files = directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith(".csv"))
					return true;
				return false;
			}
		});
		if (files == null || files.length == 0 || files[0] == null) {
			result.put("Message", "无库存文件");
			return result;
		}
		InventoryServices inventoryServices = (InventoryServices) GetBeanServlet
				.getBean("inventoryServices");
		for (File file : files) {
			List<Map> l = WebUtil.readerCsv(file, true);
			if (WebUtil.isNullForList(l)) {
				logger.info(file.getName() + ":文件没有内容");
				continue;
			}
			// 导入库存
			Map im = new HashMap();
			im.put("invList", l);
			im.put("LogType", "IMPORT");
			im.put("IoType", "R");			
			inventoryServices.inventoryProcess(im);
			//分配库存
			inventoryServices.updateInvToUnitSku(param);
			// 备份文件
			File bakFile = new File(bakpath + file.getName());
			File bakDir = new File(bakpath);
			try {

				if(!bakDir.exists())
					bakDir.mkdirs();
				WebUtil.fileCopy(file, bakFile);
				System.gc();
				System.gc();
				file.delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("接口【导入库存】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map importReturnResult(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【导入发货实绩】开始执行");
		Map result = new HashMap();
		result.put("Flag", "success");
		if (WebUtil.isNull(param.get("StoreId"))) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID没有传入");
			return result;
		}
		String path = WebConfigProperties.getProperties("refund.import.path");
		if (WebUtil.isNotNull(param.get("FilePath")))
			path = (String) param.get("FilePath");
		String bakpath = WebConfigProperties
				.getProperties("refund.export.bak.path");
		if (WebUtil.isNotNull(param.get("BakFilePath")))
			bakpath = (String) param.get("BakFilePath");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
				path = path + storeInfo.get("CompanyId") + "/";
				bakpath = bakpath + storeInfo.get("CompanyId") + "/";
			}
		}
		File directory = new File(path);
		if (!directory.isDirectory()) {
			result.put("Flag", "error");
			result.put("Message", "退货实绩目录不存在");
			return result;
		}
		File[] files = directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith(".csv"))
					return true;
				return false;
			}
		});
		if (files == null || files.length == 0 || files[0] == null) {
			result.put("Message", "无退货实绩文件");
			return result;
		}
		OrderServices orderServices = (OrderServices) GetBeanServlet
				.getBean("orderServices");
		for (File file : files) {
			List<Map> l = WebUtil.readerCsv(file, true);
			if (WebUtil.isNullForList(l)) {
				logger.info(file.getName() + ":文件没有内容");
				continue;
			}
			// 导入出库实绩
			orderServices.importWmsReturn(WebUtil.toMap("StoreId", param
					.get("StoreId"), "ReturnResultList", l));
			// 备份文件
			File bakFile = new File(bakpath + file.getName());
			File bakDir = new File(bakpath);
			try {

				if(!bakDir.exists())
					bakDir.mkdirs();
				WebUtil.fileCopy(file, bakFile);
				System.gc();
				System.gc();
				file.delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("接口【导入发货实绩】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}
	
	

}
