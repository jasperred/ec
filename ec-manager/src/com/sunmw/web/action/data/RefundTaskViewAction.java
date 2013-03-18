package com.sunmw.web.action.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class RefundTaskViewAction {
	private List exportFileList;
	private List importFileList;

	public List getExportFileList() {
		return exportFileList;
	}

	public void setExportFileList(List exportFileList) {
		this.exportFileList = exportFileList;
	}

	public List getImportFileList() {
		return importFileList;
	}

	public void setImportFileList(List importFileList) {
		this.importFileList = importFileList;
	}

	// 退货导出,WMS文件列表
	public void refundExportFileList() {
		if (exportFileList == null)
			exportFileList = new ArrayList();
		exportFileList.clear();
		String dir = WebConfigProperties.getProperties("refund.export.path");
		if (dir == null)
			return;
		File filedir = new File(dir);
		// 主目录列表
		File[] files = filedir.listFiles();
		if(files==null)
			return;
		String url = WebConfigProperties.getProperties("refund.export.url");
		// 目录分类,DRIVER/TRACK/TEAM/HISTORY
		for (File file : files) {
			Map m = new HashMap();
			m.put("fileName", file.getName());
			m.put("fileDate", WebUtil.formatDateString(new Date(file
					.lastModified()), "yyyy-MM-dd HH:mm:ss"));
			m.put("filePath", file.getAbsolutePath());
			m.put("fileUrl", url + file.getName());
			exportFileList.add(m);
		}
		Collections.sort(exportFileList, new Comparator<Map<String, String>>() {

			public int compare(Map<String, String> o1, Map<String, String> o2) {
				if (o1.get("fileDate").compareTo(o2.get("fileDate")) < 0)
					return 1;
				return 0;
			}
		});
	}
	
	// 退货导入列表
	public void refundImportFileList() {
		if (importFileList == null)
			importFileList = new ArrayList();
		importFileList.clear();
		String dir = WebConfigProperties.getProperties("refund.import.path");
		if (dir == null)
			return;
		File filedir = new File(dir);
		// 主目录列表
		File[] files = filedir.listFiles();
		if(files==null)
			return;
		String url = WebConfigProperties.getProperties("refund.import.url");
		// 目录分类,DRIVER/TRACK/TEAM/HISTORY
		for (File file : files) {
			Map m = new HashMap();
			m.put("fileName", file.getName());
			m.put("fileDate", WebUtil.formatDateString(new Date(file
					.lastModified()), "yyyy-MM-dd HH:mm:ss"));
			m.put("filePath", file.getAbsolutePath());
			m.put("fileUrl", url + file.getName());
			importFileList.add(m);
		}
		Collections.sort(importFileList, new Comparator<Map<String, String>>() {

			public int compare(Map<String, String> o1, Map<String, String> o2) {
				if (o1.get("fileDate").compareTo(o2.get("fileDate")) < 0)
					return 1;
				return 0;
			}
		});
	}

}
