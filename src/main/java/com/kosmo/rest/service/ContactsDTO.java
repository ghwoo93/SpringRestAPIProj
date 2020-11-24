package com.kosmo.rest.service;
/*
  2  CONTACT_ID NUMBER PRIMARY KEY,
  3   NO NUMBER,
  4  NAME NVARCHAR2(10) NOT NULL,
  5  PHOTO VARCHAR2(100),
  6  TEL VARCHAR2(20));
 */
public class ContactsDTO {
	private String contact_id;
	private String no;
	private String name;
	private String photo;
	private String tel;
	
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
