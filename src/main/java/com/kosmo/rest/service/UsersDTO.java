package com.kosmo.rest.service;

public class UsersDTO {
	private String name;
	private String age;
	private String addr;
	
	public UsersDTO() {
	
	}
	
	public UsersDTO(String name, String age, String addr) {
		super();
		this.name = name;
		this.age = age;
		this.addr = addr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "UsersDTO [name=" + name + ", age=" + age + ", addr=" + addr + "]";
	}
}
