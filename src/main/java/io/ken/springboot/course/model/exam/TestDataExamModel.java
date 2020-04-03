package io.ken.springboot.course.model.exam;

/**
 * 操作题Model
 * @author cll
 * 
 * @create 2019-11-25 13:35
 **/
public class TestDataExamModel {

    private  String number;
    private  String table;
    private  String type;
    private  String question_code;
    private  String question_name;
    private  String select;
    private  String result;
    private  byte[]  img;
    private String id;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getQuestion_code() {
        return question_code;
    }

    public void setQuestion_code(String question_code) {
        this.question_code = question_code;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}
