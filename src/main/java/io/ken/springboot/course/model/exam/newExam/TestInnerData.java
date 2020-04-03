package io.ken.springboot.course.model.exam.newExam;

/**
 * testData所有题--内部的题节点
 *
 * @author GouYudong
 * @create 2019-12-19 15:43
 **/
public class TestInnerData {

    private String number;
    private String table;
    //0为操作题，1为普通通信题，2为加密通信题，3为单选，4为多选，其他类型为""
    private String type;
    private String question_code;
    private String question_name;
    private String select;//选择判断题选项，没有为""
    private String result;
    private String grade;//题的分数
    private  byte[] img;//图片数据  没有为""

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
