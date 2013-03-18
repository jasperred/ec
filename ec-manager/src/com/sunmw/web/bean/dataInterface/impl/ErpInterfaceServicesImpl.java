package com.sunmw.web.bean.dataInterface.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.web.bean.dataInterface.ErpInterfaceServices;
import com.sunmw.web.bean.product.ProductServices;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.dao.ReportServicesMapper;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ErpInterfaceServicesImpl implements ErpInterfaceServices {

	static Logger logger = Logger.getLogger(ErpInterfaceServicesImpl.class);
	@Override
	public Map dailySaleAndRefundReport(Map param) {
		Map result = new HashMap();
		String date = WebUtil.formatDateString(new Date(System.currentTimeMillis()-24*60*60*1000), "yyyy-MM-dd");
		ReportServicesMapper reportServicesMapper = (ReportServicesMapper) GetBeanServlet.getBean("reportServicesMapper");
		List<Map> srList = reportServicesMapper.selectDailySaleAndRefundReportByExample(WebUtil.toMap("StoreId", param.get("StoreId"),"StartDate",date+" 00:00:00","EndDate",date+" 23:59:59"));
		String fileName = "EOD-"+param.get("StoreId")+"-"+WebUtil.formatDateString(new Date(System.currentTimeMillis()-24*60*60*1000), "yyyyMMdd")+".csv";
		String path = (String) param.get("FilePath");
		if(WebUtil.isNull(path))
			path = WebConfigProperties.getProperties("report.sale.daily.path");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
				path = path + storeInfo.get("CompanyId") + "/";
			}
		}
		WebUtil.exportCSV(new String[]{"Sell Date","Order No","Item","Qty","Amount" }, new String[]{"orderDate","origOrderNo","skuCd","qty","amt"}, srList, path, fileName);
		result.put("Flag", "success");
		return result;
	}
	
	@Override
	public Map refreshDailySaleAndRefundReport(Map param)
	{
		Map result = new HashMap();
		result.put("Flag", "success");
		String startDate = (String) param.get("startDate");
		String endDate = (String) param.get("endDate");
		if(WebUtil.isNull(startDate))
			return result;
		if(WebUtil.isNull(endDate))
			endDate = WebUtil.formatDateString(new Date(System.currentTimeMillis()), "yyyy-MM-dd");
		ReportServicesMapper reportServicesMapper = (ReportServicesMapper) GetBeanServlet.getBean("reportServicesMapper");
		String path = (String) param.get("FilePath");
		if(WebUtil.isNull(path))
			path = WebConfigProperties.getProperties("report.sale.daily.path");
		// 分不同公司路径
		if (WebUtil.isNotNull(param.get("StoreId"))) {
			StoreServices storeServices = (StoreServices) GetBeanServlet
					.getBean("storeServices");
			Map storeInfo = (Map) storeServices.storeInfo(param).get(
					"StoreInfo");
			if (storeInfo != null
					&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
				path = path + storeInfo.get("CompanyId") + "/";
			}
		}
		while(endDate.compareToIgnoreCase(startDate)>0)
		{
			List<Map> srList = reportServicesMapper.selectDailySaleAndRefundReportByExample(WebUtil.toMap("StoreId", param.get("StoreId"),"StartDate",startDate+" 00:00:00","EndDate",startDate+" 23:59:59"));
			String fileName = "EOD-"+param.get("StoreId")+"-"+startDate.replaceAll("-", "")+".csv";
			
			WebUtil.exportCSV(new String[]{"Sell Date","Order No","Item","Qty","Amount" }, new String[]{"orderDate","origOrderNo","skuCd","qty","amt"}, srList, path, fileName);
			startDate = WebUtil.formatDateString(new Date(WebUtil.toDateForString(startDate, "yyyy-MM-dd").getTime()+24*60*60*1000), "yyyy-MM-dd");
		}
		
		return result;
	}

	@Override
	public Map importItemPrice(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【导入价格】开始执行");
		Map result = new HashMap();
		result.put("Flag", "success");
		if (WebUtil.isNull(param.get("StoreId"))) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID没有传入");
			return result;
		}
		String path = WebConfigProperties.getProperties("price.import.path");
		if (WebUtil.isNotNull(param.get("FilePath")))
			path = (String) param.get("FilePath");
		String bakpath = WebConfigProperties
				.getProperties("price.bak.path");
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
		//统一价格文件，不含店铺
		File directory = new File(path);
		if (!directory.isDirectory()) {
			result.put("Flag", "error");
			result.put("Message", "价格目录不存在");
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
		List<File> fileList = new ArrayList();

		if(files!=null)
		{
			for(File f:files)
			{
				if(f!=null)
					fileList.add(f);
			}
		}
		//店铺价格文件
		File directory2 = new File(path+param.get("StoreId")+"/");
		if (directory2.isDirectory()) {
			File[] files2 = directory.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if (pathname.getName().endsWith(".csv"))
						return true;
					return false;
				}
			});
			if(files2!=null)
			{
				for(File f:files2)
				{
					if(f!=null)
						fileList.add(f);
				}
			}
		}
		ProductServices productServices = (ProductServices) GetBeanServlet
				.getBean("productServices");
		for (File file : files) {
			List<Map> l = WebUtil.readerCsv(file, true);
			if (WebUtil.isNullForList(l)) {
				logger.info(file.getName() + ":文件没有内容");
				continue;
			}	
			productServices.importItemPrice(WebUtil.toMap("StoreId", param.get("StoreId"),"PriceList",l));
			// 备份文件
			File bakFile = new File(bakpath + WebUtil.formatDateString(new Date(), "yyyyMMddHHmmss")+" "+file.getName());
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
		logger.info("接口【导入价格】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map syncItemFromTb(Map param) {
		long ctm = System.currentTimeMillis();
		Map result = new HashMap();
		logger.info("接口【从淘宝同步商品】开始执行");
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		TbItemServices tbItemServices = (TbItemServices) GetBeanServlet
		.getBean("tbItemServices");
		Map r = tbItemServices.getTbItemAll(storeId);
		ProductServices productServices = (ProductServices)GetBeanServlet.getBean("productServices");
		productServices.synchronizationTbItemToErp(r);
		result.put("Flag", "success");
		logger.info("接口【从淘宝同步商品】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

}
