package io.ken.springboot.course.bean;


/**
 * 
 * @author 
 *HandExamStore.java
 * 2019年11月19日
 */
public class HandExamStore {

	
	private String id;
	private String questionName;
	private String questionCode;
	private int type;
	private String grade;
	private String result;
	private  String subject;
	private  String passTableId;
	private  String levelId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPassTableId() {
		return passTableId;
	}
	public void setPassTableId(String passTableId) {
		this.passTableId = passTableId;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
			
	
	
}
