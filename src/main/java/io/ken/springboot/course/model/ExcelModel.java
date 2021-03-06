package io.ken.springboot.course.model;

/**
 * @author ZhaoWenHao
 * Excel表格导入试题的模型
 * @create 2019-12-27 15:52
 **/
public class ExcelModel {

    private String id;

    private String subject;

    private String questionName;

    private String questionCode;

    private String type;

    private String questionType;//题目类型

    private String result;

    private String picturePath;

    private String levelId;

    private int flag;

    private String selectA;

    private String grade;
    private String passTableId;
    private String transflag;

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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

    public String getSelectA() {
        return selectA;
    }

    public void setSelectA(String selectA) {
        this.selectA = selectA;
    }
}
