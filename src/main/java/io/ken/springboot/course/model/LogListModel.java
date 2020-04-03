package io.ken.springboot.course.model;

import java.sql.Timestamp;

/**
 * 返回前台实体类
 * @author lq
 *LogListModel.java
 * 2019年11月27日
 */
public class LogListModel {

	private String name;
	
	private String className;
	
	private String jurisdiction;
	
	
	private String loginTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


	
	
	
	
	
}
