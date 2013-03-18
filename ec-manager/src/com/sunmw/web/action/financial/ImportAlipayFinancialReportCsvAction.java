package com.sunmw.web.action.financial;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.financial.AlipayFinancialServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ImportAlipayFinancialReportCsvAction {
	Logger logger = Logger.getLogger(ImportAlipayFinancialReportCsvAction.class);
	private AlipayFinancialServices alipayFinancialServices;
	private String storeId;
	private String message;
	private boolean success;
	private String crumb;

	public AlipayFinancialServices getAlipayFinancialServices() {
		return alipayFinancialServices;
	}

	public void setAlipayFinancialServices(
			AlipayFinancialServices alipayFinancialServices) {
		this.alipayFinancialServices = alipayFinancialServices;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
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

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String importAlipayFinancialReportCsv() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			String userNo = ul.getUserNo();
			if (crumb == null || !VerifyRequest.verifyCrumb(userNo, crumb)) {
				message = MessageInfo.Verify;
				success = false;
				return "success";
			}
			success = true;
			String path = WebConfigProperties
					.getProperties("financial.import.path");
			String bak = WebConfigProperties
					.getProperties("financial.import.bak.path");
			if(ul.getCompanyId()!=null)
			{
				path = path + ul.getCompanyId()+"/";
				bak = bak + ul.getCompanyId()+"/";
			}
			File dir = new File(path);
			if (!dir.exists())
				return "success";
			File[] files = dir.listFiles();
			if (files == null || files.length == 0 || files[0] == null)
				return "success";
			for (File file : files) {
				List l = WebUtil.readerCsv(file, false, "GBK");
				//从第五行开始是正式数据
				if (!WebUtil.isNullForList(l)&&l.size()>5) {
					Map param = WebUtil.toMap(
							"AlipayFinancialReportList", l.subList(5, l.size()));
					if(WebUtil.isNotNull(storeId))
					{
						param.put("StoreId", new Integer(storeId));
					}
					param.put("CompanyId", ul.getCompanyId());
					Map m = this.alipayFinancialServices
							.importAlipayFinancialReportCsv(param);
					if(m.get("Flag").equals("ERROR"))
					{
						success = false;
						message = (String) m.get("Message");
						break;
					}
					else
					{
						logger.info("文件："+file.getName()+"，执行："+m.get("row"));
					}
				}
				File bakDir = new File(bak);
				if(!bakDir.exists())
					bakDir.mkdirs();
				File bakFile = new File(bak + file.getName());
				if(!bakFile.exists())
					bakFile.createNewFile();
				try {
					WebUtil.fileCopy(file, bakFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.gc();
				System.gc();
				file.delete();
			}
			message = "";
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
			success = false;
		}
		return "success";
	}

}
