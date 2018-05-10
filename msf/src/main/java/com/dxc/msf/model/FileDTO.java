package com.dxc.msf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity(name="Files")
public class FileDTO extends AbstractDTO{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fileID")
	private int fileID;
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileSize")
	private Double fileSize;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryID", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonDeserialize(as = CategoryDTO.class)
	private CategoryDTO categoryPk;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userID", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonDeserialize(as = UserDTO.class)
	private UserDTO userPk;
	
	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryDTO getCategoryPk() {
		return categoryPk;
	}

	public void setCategoryPk(CategoryDTO categoryPk) {
		this.categoryPk = categoryPk;
	}

	public UserDTO getUserPk() {
		return userPk;
	}

	public void setUserPk(UserDTO userPk) {
		this.userPk = userPk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toJSon() throws JsonProcessingException {
		ObjectMapper objectmapper = new ObjectMapper();
		return objectmapper.writeValueAsString(this);
	}
}
