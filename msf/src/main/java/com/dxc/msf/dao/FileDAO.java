package com.dxc.msf.dao;

import java.util.List;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.FileDTO;

public interface FileDAO {
	
	public boolean AddUploadFile(FileDTO file);
	public boolean UpdateFile(FileDTO file);// Nhut Lam
	public boolean DeleteFile(FileDTO file);// Nhut Lam
	public FileDTO getFileByID(int fileID);	

	public List<FileDTO> getListFile();
	
	//Vu Ngo
	public Double sumSizeFileUploaded(int userID);
	public void autoUpRank(int userID);
	public boolean checkFileSizeUpload(int userID,double currentFileSize);
	public List<FileDTO> searchFiles(String searchByFileName,String searchByUploader);
	//-----------------//
}
