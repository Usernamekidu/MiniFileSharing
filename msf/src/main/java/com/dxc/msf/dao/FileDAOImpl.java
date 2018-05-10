package com.dxc.msf.dao;

import java.nio.file.Files;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;

@Repository
public class FileDAOImpl implements FileDAO {

	@Autowired
	SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@Override
	public boolean AddUploadFile(FileDTO file) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(file);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Nhut Lam update file
	@Override
	public boolean UpdateFile(FileDTO file) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(file);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Nhut Lam delete file
	@Override
	public boolean DeleteFile(FileDTO file) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(file);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<FileDTO> getListFile() {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<FileDTO> list = (List<FileDTO>) session.createQuery(
					"from Files").list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	// vungo
	public Double sumSizeFileUploaded(int userID) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();

		String query = "select sum(fileSize) from Files where userID ="
				+ userID;
		List<Double> resultSumSizeUploadFile = (List<Double>) session
				.createQuery(query).list();
		Double result = resultSumSizeUploadFile.get(0);

		transaction.commit();
		session.close();
		return result;
	}

	@Override
	// vungo
	public void autoUpRank(int userID) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();

		UserDTO user = (UserDTO) session.get(UserDTO.class, userID);
		double totalSizeUploaded = sumSizeFileUploaded(user.getUserID());

		if (totalSizeUploaded >= 50 && totalSizeUploaded <= 100)
			user.setUserRank("Silver");
		else if (totalSizeUploaded > 100)
			user.setUserRank("Gold");
		else
			user.setUserRank("Bronze");

		session.update(user);
		transaction.commit();
		session.close();
	}

	@Override
	// vungo
	public boolean checkFileSizeUpload(int userID, double currentFileSize) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();

			UserDTO user = (UserDTO) session.get(UserDTO.class, userID);

			String rankUser = user.getUserRank();
			boolean isCheck = false;
			if (rankUser.equals("Bronze") && currentFileSize <= 5)
				isCheck = true;
			else if (rankUser.equals("Silver") && currentFileSize <= 10)
				isCheck = true;
			else if (rankUser.equals("Gold") && currentFileSize <= 20)
				isCheck = true;
			transaction.commit();
			session.close();
			return isCheck;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	// vungo
	public List<FileDTO> searchFiles(String searchByFileName,
			String searchByUploader) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "";
			if (searchByFileName.equals(""))
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "WHERE (Users.userName LIKE '%"
						+ searchByUploader
						+ "%')";
			else if (searchByUploader.equals(""))
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "WHERE (Files.fileName LIKE '%"
						+ searchByFileName
						+ "%')";
			else
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "WHERE (Users.userName LIKE '%"
						+ searchByUploader
						+ "%' OR Files.fileName LIKE '%"
						+ searchByFileName
						+ "%') AND Files.categoryID=1";

			List<FileDTO> list = (List<FileDTO>) session.createSQLQuery(query)
					.addEntity(FileDTO.class).list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public FileDTO getFileByID(int fileID) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			FileDTO file = (FileDTO) session.get(FileDTO.class, new Integer(fileID));
			transaction.commit();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();

		}
	}

}
