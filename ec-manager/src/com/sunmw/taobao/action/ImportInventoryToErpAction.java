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

import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ImportInventoryToErpAction {
	static Logger logger = Logger.getLogger(ImportInventoryToErpAction.class);
	private InventoryServices inventoryServices;
	private int storeId;
	private String flag;//是否清空库存
	
	public InventoryServices getInventoryServices() {
		return inventoryServices;
	}

	public void setInventoryServices(InventoryServices inventoryServices) {
		this.inventoryServices = inventoryServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void importInventoryToErp()
	{
		Integer whId = inventoryServices.getWhId(storeId);
		if(WebUtil.isNull(whId))
		{
			logger.error("店铺["+storeId+"]没有对应的仓库");
			return;
		}
		String path = WebConfigProperties.getProperties("inventory.import.path");
		String bakpath = WebConfigProperties.getProperties("inventory.bak.path");
		File filedir = new File(path);
		if(!filedir.exists())
			filedir.mkdirs();
		File bakdir = new File(bakpath);
		if(!bakdir.exists())
			bakdir.mkdirs();
		if(WebUtil.isNotNull(flag)&&flag.equals("CLEAR"))
			inventoryServices.clearInventory(storeId);
		File[] files = filedir.listFiles();
		if(files==null)return;
		for(File f:files)
		{
			if(f==null||!f.exists())
				continue;
			//读文件
			List<Map> list = readFile(f,whId);
			if(list==null)
				continue;
			//导入到ERP
			Map im = new HashMap();
			im.put("invList", list);
			im.put("LogType", "IMPORT");
			im.put("IoType", "R");	
			inventoryServices.inventoryProcess(im);
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
	
	private List readFile(File f,Integer whId) {
		try {
			List<Map> list = new ArrayList();
			InputStream is = new FileInputStream(f.getAbsolutePath());
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			Cell[] field = rs.getRow(0);
			if(field==null)
				return null;
			//找SKU和库存的位置
			int skuCd = -1;
			int qty = -1;
			for(int i=0;i<field.length;i++)
			{
				if(field[i].getContents().trim().equals("条码（唯一编号）"))
					skuCd = i;
				if(field[i].getContents().trim().equals("可用库存"))
					qty = i;
			}
			int rows = rs.getRows();
			if (field != null && field.length > 0 && rows > 1)
				for (int i = 1; i < rows; i++) {
					Map m = new HashMap();
					String sku = rs.getCell(skuCd,i).getContents();
					String invQty = rs.getCell(qty,i).getContents();
					//无SKU或库存不更新
					if(WebUtil.isNull(sku))
						continue;
					if(WebUtil.isNull(invQty))
						continue;
					m.put("WhId", whId);
					m.put("InvType", "A");
					m.put("LogType", "FILE_IMPORT");
					m.put("IoType", "R");
					m.put("SkuCd", sku);
					m.put("Qty", new Integer(invQty));
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
