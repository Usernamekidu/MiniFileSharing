// Nhut Lam
package com.dxc.msf.dao;

import java.util.List;

import com.dxc.msf.model.CategoryDTO;

public interface CategoryDAO {
	public boolean UpdateCategory(CategoryDTO category);
	public boolean DeleteCategory(CategoryDTO categoryID);
	public boolean CreateCategory(CategoryDTO category);
	public CategoryDTO getCategoryByID(int categoryID);	
	public List<CategoryDTO> getListCategory();

}
