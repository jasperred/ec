package com.sunmw.web.action.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.sunmw.web.bean.data.OrderTaskServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class OrderTaskViewAction {
	private List fileList;

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

	// 订单导出,WMS文件列表
	public void erpToWMSList() {
		if (fileList == null)
			fileList = new ArrayList();
		fileList.clear();
		String dir = WebConfigProperties.getProperties("order.to_wms.path");
		if (dir == null)
			return;
		File filedir = new File(dir);
		// 主目录列表
		File[] files = filedir.listFiles();
		if(files==null)
			return;
		String url = WebConfigProperties.getProperties("order.to_wms.url");
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
	}
	
	// 订单导入,物流信息文件列表
	public void wmsToErpFileList() {
		if (fileList == null)
			fileList = new ArrayList();
		fileList.clear();
		String dir = WebConfigProperties.getProperties("order.to_erp.path");
		if (dir == null)
			return;
		File filedir = new File(dir);
		// 主目录列表
		File[] files = filedir.listFiles();
		if(files==null)
			return;
		String url = WebConfigProperties.getProperties("order.to_erp.url");
		// 目录分类
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
	}

}
