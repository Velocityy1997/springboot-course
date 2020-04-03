package io.ken.springboot.course.model.exam;

import java.util.List;

/**
 * 考试题号+答案
 * @author GouYudong
 * @create 2019-11-22 10:27
 **/
public class ExamInfoModel {

    private  String examId;
    private  String type;//考试类型
    private  String examName;//考试名称
    private  String idNumber;//学号
    private  Object flagData;//操作题
    private  Object scjData;//手持机相关
    private  List<HandExamPaperModel> testData;//操作与填空考试题
    private  List<CommExamModel> mesData;//通讯题



    public String getExamId() {
        return examId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public List<HandExamPaperModel> getTestData() {
        return testData;
    }

    public void setTestData(List<HandExamPaperModel> testData) {
        this.testData = testData;
    }

    public List<CommExamModel> getMesData() {
        return mesData;
    }

    public void setMesData(List<CommExamModel> mesData) {
        this.mesData = mesData;
    }

    public Object getScjData() {
        return scjData;
    }

    public void setScjData(Object scjData) {
        this.scjData = scjData;
    }
}
