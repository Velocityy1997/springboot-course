package io.ken.springboot.course.bean;

/**
 * @author GouYudong
 * @create 2019-11-25 17:04
 **/
public class CommExam {

    private String id;

    private String result;

    private String questionName;

    private String questionCode;

    private int type;

    private String grade;

    private String passTableId;

    private String tFTableId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getPassTableId() {
        return passTableId;
    }

    public void setPassTableId(String passTableId) {
        this.passTableId = passTableId;
    }

    public String gettFTableId() {
        return tFTableId;
    }

    public void settFTableId(String tFTableId) {
        this.tFTableId = tFTableId;
    }
}
