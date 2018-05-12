package com.dxc.msf.service;

import java.util.List;

import com.dxc.msf.model.UserDTO;

public interface UserService {

	public boolean createUser(UserDTO user);
	public List<UserDTO> getListUser();
	// Nhut Lam update & delete User
	public boolean updateUser(int userID);
//	public boolean disableUser(UserDTO user);
	public UserDTO getUser(int userID);
	public boolean isActive(int userID, int status);
	public List<Object[]> countUserActive();
	
}
