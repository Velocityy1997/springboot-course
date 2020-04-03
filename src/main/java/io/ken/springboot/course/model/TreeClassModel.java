package io.ken.springboot.course.model;

import java.util.List;

/**
 * 班级树返回前台
 * @author lq
 *TreeClassModel.java
 * 2019年11月22日
 */
public class TreeClassModel <T> {
	
	private List<T>  children;
	
	private String expanded;
	
	private String id;
	
	private String type;
	
	private String title;
	
	private String name;

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}				

	
	
}
