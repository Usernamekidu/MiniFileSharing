package com.dxc.msf.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.xml.dtm.DTMDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.msf.dao.DownloadFileDAO;
import com.dxc.msf.dao.FileDAO;
import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.FileDTO;

@Service
public class FileServiceImpl implements FileService {

	ServletContext servletContext;
	@Autowired
	FileDAO fileDao;
	@Autowired
	DownloadFileDAO downloadFileDAO;

	public ServletContext getContext() {
		return servletContext;
	}

	@Autowired
	public void setContext(ServletContext context) {
		this.servletContext = context;
	}

	private static final String uploadFilePath = "resources" + File.separator
			+ "uploaded_files";

	@Override
	public Boolean uploadFile(MultipartFile file) {
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			try {
				byte[] fileBytes = file.getBytes();

				// generate rootPath and check folder existence. If not, create
				// new folder based
				// on rootPath
				String rootPath = getContext().getRealPath("");
				File folder = new File(rootPath + uploadFilePath);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				// upload file on server
				File uploadFile = new File(folder.getAbsolutePath()
						+ File.pathSeparator + fileName);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(uploadFile));
				bos.write(fileBytes);
				bos.close();
				// AddUploadFile(fileDto);
				return true;

			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	// this function using for user download files from Server based on File
	// Name
	@Override
	public Boolean downloadFile(HttpServletResponse response, String fileId) {
		int fileSize = getFileSize(fileId);
		String fileName = getFileName(fileId);
		String rootPath = getContext().getRealPath("") + uploadFilePath
				+ fileName;

		// Refine file name
		String regexPattern = "(\\D+)";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(fileName);
		String refinedFileName = matcher.group(0).substring(0,
				matcher.group(0).length() - 1);

		File downloadFile = new File(rootPath);
		if (downloadFile.exists() && !downloadFile.isDirectory()) {
			try {
				OutputStream os = response.getOutputStream();
				// set content type and header for response
				response.setHeader("Content-Disposition",
						"attachment; filename=" + refinedFileName);
				response.setContentType("text/html");
				response.setContentType("APPLICATION/OCTET-STREAM");

				// start to write file to Stream
				FileInputStream fis = new FileInputStream(downloadFile);
				byte[] buffer = new byte[fileSize];
				int length;
				while ((length = fis.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				fis.close();
				os.flush();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	private String getFileName(String fileId) {
		// implement DAO to get file name from DB base on fileID
		return "";
	}

	private int getFileSize(String fileId) {
		// implement DAO to get file size from DM based on fileID
		return 0;
	}

	@Override
	public Boolean addUploadFile(FileDTO file) {
		if (!file.getFileName().isEmpty()) {
			boolean susscess = fileDao.AddUploadFile(file);
			if (susscess) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public List<FileDTO> getListFile() {
		return fileDao.getListFile();
	}

	@Override
	public boolean addDownloadFile(DownloadDTO download) {
		boolean susscess = downloadFileDAO.AddDownloadFile(download);
		if (susscess) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<DownloadDTO> getListDownloadFile() {
		return downloadFileDAO.getListDownload();
	}

	// update file
	@Override
	public boolean UpdateFile(FileDTO file) {
		boolean susscess = fileDao.UpdateFile(file);
		if (susscess) {
			return true;
		} else {
			return false;
		}
	}

	// delete file
	@Override
	public boolean DeleteFile(FileDTO file) {
		boolean susscess = fileDao.DeleteFile(file);
		if (susscess) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void autoUpRank(int userID) {
		// TODO Auto-generated method stub
		fileDao.autoUpRank(userID);
	}

	@Override
	public boolean checkFileSizeUpload(int userID, double currentFileSize) {
		// TODO Auto-generated method stub
		return fileDao.checkFileSizeUpload(userID, currentFileSize);
	}

	@Override
	public boolean checkSizeFilesDownload(int userID, double currentFileSize) {
		// TODO Auto-generated method stub
		return downloadFileDAO.checkSizeFilesDownload(userID, currentFileSize);
	}

	@Override
	public List<FileDTO> searchFiles(String searchByFileName,
			String searchByUploader) {
		return fileDao.searchFiles(searchByFileName, searchByUploader);
	}

	@Override
	public FileDTO getFileByID(int fileID) {
		return fileDao.getFileByID(fileID);

	}

}
