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

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.web.bean.data.OrderTaskServices;
import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ImportWmsLogisticsAction {
	
	private OrderTaskServices orderTaskServices;
	private TBOrderServices tbOrderServices;
	private int storeId;

	public OrderTaskServices getOrderTaskServices() {
		return orderTaskServices;
	}

	public void setOrderTaskServices(OrderTaskServices orderTaskServices) {
		this.orderTaskServices = orderTaskServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public TBOrderServices getTbOrderServices() {
		return tbOrderServices;
	}

	public void setTbOrderServices(TBOrderServices tbOrderServices) {
		this.tbOrderServices = tbOrderServices;
	}

	public void importWmsLogistics()
	{
		String path = WebConfigProperties.getProperties("order.to_erp.path");
		String bakpath = WebConfigProperties.getProperties("order.to_erp.bak.path");
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
			orderTaskServices.updateOrderLogistics(list);	
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
		//得到需要上传到淘宝的订单信息
		List<Map> logisticsList = orderTaskServices.getToTbLogisticsOrder(storeId);
		//上传到淘宝
//		String taobaoNos = tbOrderServices.taobaoDeliverySend(logisticsList, storeId);
//		//更新ERP订单状态
//		orderTaskServices.updateOrderLogisticsStatus(taobaoNos);
		
		
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
					m.put("TaobaoNo", rs.getCell(22, i).getContents());
					m.put("company", rs.getCell(20, i).getContents());
					m.put("sid", rs.getCell(21, i).getContents());
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
