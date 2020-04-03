package io.ken.springboot.course.model;
/**
 * 
 * @author cll
 *试题库model
 * 2019年11月20日
 */
public class QuestionModel {

	private String id;
	private String object;
	private String famliy;
	private String type;
	private String type1;
	private String question;
	private String grade;
	private String operation;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getFamliy() {
		return famliy;
	}
	public void setFamliy(String famliy) {
		this.famliy = famliy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}		
	
	
	
}
