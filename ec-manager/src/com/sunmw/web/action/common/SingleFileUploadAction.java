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

public class SingleFileUploadAction {
	private File uploadFile;
	private String uploadFileFileName;
	private String uploadFileContentType;
	private String newFileName;
	private String filePath;
	private String fileUrl;// 相對地址
	private String serverUrl;
	private String message;
	private boolean success;
	private String subDir;

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

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getSubDir() {
		return subDir;
	}

	public void setSubDir(String subDir) {
		this.subDir = subDir;
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
		String realpath = WebConfigProperties.getProperties("system.file.path");
		this.serverUrl = WebConfigProperties.getProperties("system.file.url");
		fileUrl = "";
		if (subDir != null && subDir.trim().length() > 0) {
			realpath += subDir + "/";
			fileUrl += subDir + "/";
		}
		if (ul.getCompanyId() != null) {
			realpath = realpath + ul.getCompanyId() + "/";
			fileUrl = fileUrl + ul.getCompanyId() + "/";
		}
		if (uploadFile != null) {
			File savedir = new File(realpath);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
			if (!uploadFile.exists()) {
				message = "文件不存在";
				success = false;
				return "success";
			}
			File savefile = null;
			if (WebUtil.isNotNull(newFileName)) {
				if (newFileName.indexOf(".") < 0)
					newFileName = newFileName
							+ uploadFileFileName.substring(uploadFileFileName
									.lastIndexOf("."));
				savefile = new File(savedir, newFileName);
			} else
				savefile = new File(savedir, uploadFileFileName);
			try {
				FileUtils.copyFile(uploadFile, savefile);
				fileUrl += savefile.getName();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		message = "success";
		success = true;
		return "success";
	}
}
