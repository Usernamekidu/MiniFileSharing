package com.dxc.msf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxc.msf.model.UserDTO;
import com.dxc.msf.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/create", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventCreate(@RequestBody UserDTO user) {
		boolean success = userService.createUser(user);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	// List
	@RequestMapping(value = "/user/list", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getListUser() {
		List<UserDTO> list = userService.getListUser();
		String json = new Gson().toJson(list);
		return json;
	}

	// Nhut Lam updateUser

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventEdit(@RequestBody int userID) {
		boolean success = userService.updateUser(userID);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	// Nhut Lam disableUser

	@RequestMapping(value = "/user/disable-id={userID}&&status={userStatus}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventDisable(
			@PathVariable("userID") int userID,
			@PathVariable("userStatus") int userStatus) {
		boolean success = userService.isActive(userID, userStatus);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/user/getuser/{userID}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getUser(@PathVariable("userID") int userID) {
		UserDTO user = userService.getUser(userID);
		String json = new Gson().toJson(user);
		return json;
	}
	@RequestMapping(value = "/user/countUserActive", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String countUserActive() {
		List<Object[]> list = userService.countUserActive();
		
		JsonArray arrayJson = new JsonArray();
		for(int i = 0 ; i<list.size();i++)
		{
			JsonObject newObject = new JsonObject() ;
			newObject.addProperty("label",list.get(i)[0].toString());
			newObject.addProperty("value",list.get(i)[1].toString());
			arrayJson.add(newObject);
		}
		String json = new Gson().toJson(arrayJson);
		return json;
	}

}
