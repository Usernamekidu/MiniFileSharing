// Nhut Lam
package com.dxc.msf.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.UserDTO;

@Repository
public class CategoryDAOImpl implements CategoryDAO{
	@Autowired
	SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}
	@Override
	public boolean CreateCategory(CategoryDTO category) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction =session.beginTransaction();
			session.save(category);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean UpdateCategory(CategoryDTO category) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction =session.beginTransaction();
			session.update(category);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean DeleteCategory(CategoryDTO category) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction =session.beginTransaction();
			session.delete(category);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<CategoryDTO> getListCategory() {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			try {
				List<CategoryDTO> list = (List<CategoryDTO>) session.createQuery("from Categories").list();
				transaction.commit();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				session.close();
			}
	}

	@Override
	public CategoryDTO getCategoryByID(int categoryID) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			CategoryDTO category = (CategoryDTO) session.get(CategoryDTO.class, new Integer(categoryID));
			transaction.commit();
			return category;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
}
