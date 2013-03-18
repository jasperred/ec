package com.sunmw.taobao.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.sunmw.web.bean.data.RefundTaskServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ImportErpRefundAction {
	
	private RefundTaskServices refundTaskServices;
	
	public RefundTaskServices getRefundTaskServices() {
		return refundTaskServices;
	}

	public void setRefundTaskServices(RefundTaskServices refundTaskServices) {
		this.refundTaskServices = refundTaskServices;
	}

	public void importErpRefund()
	{
		String path = WebConfigProperties.getProperties("refund.import.path");
		String bakpath = WebConfigProperties.getProperties("refund.import.bak.path");
		File filedir = new File(path);
		if(!filedir.exists())
			filedir.mkdirs();
		File bakdir = new File(bakpath);
		if(!bakdir.exists())
			bakdir.mkdirs();
		File[] files = filedir.listFiles();
		if(files==null)return;
		for(File f:files)
		{
			if(f==null||!f.exists())
				continue;
			//读文件
			List<Map> list = readFile(f);
			if(list==null)
				continue;
			//导入到ERP
			refundTaskServices.updateRefundErpStatus(list);	
			//移动文件
			try {
				File bak = new File(bakpath+f.getName());
				if(!bak.exists())
					bak.createNewFile();
				WebUtil.fileCopy(f, bak);
				System.gc();
				f.delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private List readFile(File f) {
		try {
			List<Map> list = new ArrayList();
			InputStream is = new FileInputStream(f.getAbsolutePath());
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			Cell[] field = rs.getRow(0);
			int rows = rs.getRows();
			if (field != null && field.length > 0 && rows > 1)
				for (int i = 1; i < rows; i++) {
					Map m = new HashMap();
					String origin = rs.getCell(22,i).getContents();
					//淘宝下载的退货单不导入
					if(WebUtil.isNotNull(origin)&&origin.equals("TAOBAO"))
						continue;
					String orderNo = rs.getCell(22,i).getContents();
					String s = rs.getCell(22,i).getContents();
					String refundAmt = rs.getCell(22,i).getContents();
					String skuCd = rs.getCell(22,i).getContents();
					String refundQty = rs.getCell(22,i).getContents();
					m.put("OrderNo", orderNo);
					m.put("OrderStatus", s.equals("退货成功")?"REFUND_COMPLETE":"REFUND_CLOSED");
					m.put("RefundAmt", WebUtil.isNotNull(refundAmt)?Double.parseDouble(refundAmt.trim()):0);
					m.put("SkuCd", skuCd);
					m.put("RefundQty", WebUtil.isNotNull(refundQty)?Integer.parseInt(refundQty):0);
					list.add(m);
				}
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
