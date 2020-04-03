package io.ken.springboot.course.model.exam.newExam;

/**
 * @author ZhaoWenHao
 * @create 2020-03-11 16:21
 * 接收自动创建试卷参数
 **/
public class CreatNewExam {
    private String subject;
    private String level;

    private Integer radio;//单选数量
    private Integer checkbox;//多选
    private Integer pack;//填空
    private Integer judge;//判断
    private String flag;//0：单选；1:多选

    private String idNumber;
    private Integer duration;
    private String startTime;
    private String testName;
    private String questionGrade;
    private Integer questionNum;
    private String totalGrade;
    private int createExam;
    private String transflag;

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public int getCreateExam() {
        return createExam;
    }

    public void setCreateExam(int createExam) {
        this.createExam = createExam;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    public Integer getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getRadio() {
        return radio;
    }

    public void setRadio(Integer radio) {
        this.radio = radio;
    }

    public Integer getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Integer checkbox) {
        this.checkbox = checkbox;
    }

    public Integer getPack() {
        return pack;
    }

    public void setPack(Integer pack) {
        this.pack = pack;
    }

    public Integer getJudge() {
        return judge;
    }

    public void setJudge(Integer judge) {
        this.judge = judge;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getQuestionGrade() {
        return questionGrade;
    }

    public void setQuestionGrade(String questionGrade) {
        this.questionGrade = questionGrade;
    }
}
