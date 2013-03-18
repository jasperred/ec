package com.sunmw.web.action.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class FileUploadAction {
	private File[] uploadFile;
	private String[] uploadFileFileName;
	private String[] uploadFileContentType;
	private String filePath;
	private String fileUrl;
	private String[] deleteFileName;
	private String message;
	private boolean success;
	private String subDir;
	
	private List fileList;	

	public File[] getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File[] uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String[] getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String[] uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String[] getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String[] uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

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

	public String[] getDeleteFileName() {
		return deleteFileName;
	}

	public void setDeleteFileName(String[] deleteFileName) {
		this.deleteFileName = deleteFileName;
	}

	public String getSubDir() {
		return subDir;
	}

	public void setSubDir(String subDir) {
		this.subDir = subDir;
	}

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
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

	// 文件上传
	public String fileUpload() {
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		String realpath = WebConfigProperties.getProperties(filePath);
		if(subDir!=null&&subDir.trim().length()>0)
			realpath += subDir+"/";
		if(ul.getCompanyId()!=null)
			realpath = realpath+ul.getCompanyId()+"/";
		if (uploadFile != null) {
			File savedir = new File(realpath);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
			for (int i = 0; i < uploadFile.length; i++) {
				if(!uploadFile[i].exists())
					continue;
				System.out.println("imageContentType[" + i + "] = "
						+ uploadFileContentType[i]);
				File savefile = new File(savedir, uploadFileFileName[i]);
				try {
					FileUtils.copyFile(uploadFile[i], savefile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		message = "success";
		success = true;
		return "success";
	}
	
	//文件删除
	public String fileDelete()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		System.gc();
		System.gc();
		String realpath = WebConfigProperties.getProperties(filePath);
		for(String df:deleteFileName)
		{
			if(subDir!=null&&subDir.trim().length()>0)
				realpath += subDir+"/";
			File f = new File(realpath+df);
			if(f.exists())
			{
				f.delete();
			}
			else if(ul.getCompanyId()!=null)
			{
				f = new File(realpath+ul.getCompanyId()+"/"+df);
				if(f.exists())
					f.delete();
			}
		}
		message = "success";
		success = true;
		return "success";
	}
	
	//文件列表
	public String fileList() {
		if (fileList == null)
			fileList = new ArrayList();
		fileList.clear();
		String dir = WebConfigProperties.getProperties(filePath);
		if (dir == null)
		{
			message = "success";
			success = true;
			return "success";
		}
		if(subDir!=null&&subDir.trim().length()>0)
			dir += subDir+"/";
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		//分公司路径
		if(ul.getCompanyId()!=null)
			dir = dir + ul.getCompanyId()+"/";
		File filedir = new File(dir);
		// 主目录列表
		File[] files = filedir.listFiles();
		if(files==null)
		{
			message = "success";
			success = true;
			return "success";
		}
		String url = WebConfigProperties.getProperties(fileUrl);
		//分公司路径
		if(ul.getCompanyId()!=null)
			url = url + ul.getCompanyId()+"/";
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
		message = "success";
		success = true;
		return "success";
	}
}
