package com.upbchain.springmvc.model;

//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

public class User {
	private Integer id;
//	@NotEmpty
	private String name;
	
//	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date birth;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.util.Date getBirth() {
		return birth;
	}

	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birth=" + birth + "]";
	}
}
