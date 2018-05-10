package com.dxc.msf.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.UserDTO;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean createUser(UserDTO user) {
		try {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<UserDTO> getListUser() {
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<UserDTO> list = (List<UserDTO>) session.createQuery("from Users").list();
			transaction.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	// Nhut Lam updateUser
	@Override
	public boolean updateUser(UserDTO user) {
		try {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Nhut Lam disableUser
//	@Override
//	public boolean disableUser(UserDTO user) {
//		try {
//			Session session = getSessionFactory().openSession();
//			Transaction transaction = session.beginTransaction();
//			user.setUserActive(0);
//			session.update(user);
//			transaction.commit();
//			session.close();
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}

	@Override
	public UserDTO getUser(int userID) {
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			UserDTO user = (UserDTO) session.get(UserDTO.class, new Integer(userID));
			transaction.commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean isActive(int userID, int status) {
		
		try {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			UserDTO user = getUser(userID);
			user.setUserActive(status);
			session.update(user);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}



}
