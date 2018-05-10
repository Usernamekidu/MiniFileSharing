package com.dxc.msf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;
import com.dxc.msf.service.FileService;
import com.google.gson.Gson;

@Controller
public class FileController {

	@Autowired
	FileService fileService;

	// download file

	@RequestMapping(value = "/file/download", method = RequestMethod.POST)
	public @ResponseBody String downloadFile(HttpServletResponse response,
			@RequestBody String fileId) {
		boolean downloadSuccess = fileService.downloadFile(response, fileId);
		if (downloadSuccess) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/file/adddownloadfile", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String downloadFile(@RequestBody DownloadDTO download) {
		try {
			boolean uploadSuccess = fileService.addDownloadFile(download);
			if (uploadSuccess) {
				return "{\"status\": \"OK\"}";
			}

		} catch (Exception e) {
			return "{\"status\": \"Failed\"}";
		}
		return "{\"status\": \"Failed\"}";
	}

	@RequestMapping(value = "/file/listDownload", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getListDownloadFile() {
		List<DownloadDTO> list = fileService.getListDownloadFile();
		String json = new Gson().toJson(list);
		return json;
	}

	// upload file

	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(HttpServletRequest request,
			HttpServletResponse response, @RequestBody FileDTO file) {
		MultipartHttpServletRequest mRequest;
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();
			Iterator<String> iterator = mRequest.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile mFile = mRequest.getFile(iterator.next());
				boolean uploadSuccess = fileService.uploadFile(mFile);
				if (uploadSuccess) {
					return "{\"status\": \"OK\"}";
				}
			}
		} catch (Exception e) {
			return "{\"status\": \"Failed\"}";
		}
		return "{\"status\": \"Failed\"}";
	}

	@RequestMapping(value = "/file/adduploadfile", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String uploadFile(@RequestBody FileDTO file) {
		try {
			boolean uploadSuccess = fileService.addUploadFile(file);
			if (uploadSuccess) {
				return "{\"status\": \"OK\"}";
			}

		} catch (Exception e) {
			return "{\"status\": \"Failed\"}";
		}
		return "{\"status\": \"Failed\"}";
	}

	// Nhut Lam update file
	@RequestMapping(value = "/file/update", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventUpdate(@RequestBody FileDTO file) {
		
		boolean success = fileService.UpdateFile(file);
		System.out.println(success);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}

	}

	// Nhut Lam delete file
	@RequestMapping(value = "/file/delete", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventDelete(@RequestBody FileDTO file) {
		boolean success = fileService.DeleteFile(file);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}

	}

	@RequestMapping(value = "/file/list", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getListFile() {
		List<FileDTO> list = fileService.getListFile();
		String json = new Gson().toJson(list);
		return json;
	}
	
	//vungo
	@RequestMapping(value = "/file/searchfile/{fileName}&&{uploader}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String searchFile(@PathVariable("fileName") String fileName,@PathVariable("uploader") String uploader) {
		List<FileDTO> list = fileService.searchFiles(fileName,uploader );
		String json = new Gson().toJson(list);
		return json;
	}
	
	@RequestMapping(value = "/file/getFile/{fileID}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getFileByID(@PathVariable("fileID")int fileID) {
		FileDTO file = fileService.getFileByID(fileID);
		String json = new Gson().toJson(file);
		return json;
}
}
