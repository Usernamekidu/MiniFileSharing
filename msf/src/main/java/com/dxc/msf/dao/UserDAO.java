package com.dxc.msf.dao;

import java.util.List;

import com.dxc.msf.model.UserDTO;

public interface UserDAO {
	public boolean createUser(UserDTO user);
	public List<UserDTO> getListUser();
	//Nhut Lam
	public boolean updateUser(int userID);
//	public boolean disableUser(UserDTO user);
	public boolean isActive(int userID,int status);
	public UserDTO getUser(int userID);
	public List<Object[]> countUserActive();
}
