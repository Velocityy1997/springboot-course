package io.ken.springboot.course.model.exam.newExam;

import java.util.List;

/**
 *
 * 考试题
 * @author GouYudong
 * @create 2019-12-19 15:39
 **/
public class ExampaperModel {

    private  String examId;
    private  Integer type;//考试类型
    private  String examName;//考试名称
    private  String idNumber;//学号
    private  Object flagData;//操作题
    private  Object scjData;//手持机相关
    private  List<TestInnerData> testData;//操作与填空考试题

    public String getExamId() {
        return examId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Object getFlagData() {
        return flagData;
    }

    public void setFlagData(Object flagData) {
        this.flagData = flagData;
    }

    public List<TestInnerData> getTestData() {
        return testData;
    }

    public void setTestData(List<TestInnerData> testData) {
        this.testData = testData;
    }

    public Object getScjData() {
        return scjData;
    }

    public void setScjData(Object scjData) {
        this.scjData = scjData;
    }

}
