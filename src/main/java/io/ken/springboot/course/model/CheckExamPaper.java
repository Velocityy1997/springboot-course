package io.ken.springboot.course.model;


/**
 * 查看试卷
 * @author lq
 *CheckPaper.java
 * 2019年11月28日
 */
public class CheckExamPaper {

	private int number;	
	
	private String table;
	
	private String type;
	
	private String question_code;
	
	private String select;
	
	private String question_name;
	
	private byte[] img;
	
	private boolean right;
	
	private String  answer;
	
	private String  result;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getQuestion_name() {
		return question_name;
	}

	public void setQuestion_name(String question_name) {
		this.question_name = question_name;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public boolean getRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuestion_code() {
		return question_code;
	}

	public void setQuestion_code(String question_code) {
		this.question_code = question_code;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}		
	
}
