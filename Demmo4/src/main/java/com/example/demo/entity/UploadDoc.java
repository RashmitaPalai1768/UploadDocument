package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
@Entity
public class UploadDoc {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(name = "upload_doc_path")
	private String uploadDocPath;
	@Lob
	private byte[] Data;
	private String ContentType;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUploadDocPath() {
		return uploadDocPath;
	}
	public void setUploadDocPath(String uploadDocPath) {
		this.uploadDocPath = uploadDocPath;
	}
	public byte[] getData() {
		return Data;
	}
	public void setData(byte[] data) {
		Data = data;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	} 
	
	
}
