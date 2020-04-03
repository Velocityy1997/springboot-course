package io.ken.springboot.course.bean;

import java.sql.Timestamp;

/**
 * 
 * @author lq
 *Exam.java
 * 2019年11月20日
 */
public class Exam {
	
	private String examId;
	private String idNumber;
	private String level;//学员级别
	//0：全部；1：院校领导   2：学员大队领导；  3：分队军官
	//4：参谋军官；   5：教员  6：教学管理保障人员

	private String grade;
	private String totalGrade;
	private String handExamQuestion;

	private String chooseExamQuestion;//choose_exam_question
	private String fillBlankExamQuestion;
	private String decideExamQuestion;
	private String commExamQuestion;
	private String handExamResult;
	
	private String chooseExamResult;
	private String decideExamResult;
	private String fillBlankExamResult;
	private String commExamResult;
	private String handExamAnswer;
	
	private String fillBlankExamAnswer;
	private String chooseExamAnswer;
	private String decideExamAnswer;
	private String commExamAnswer;
	private String examName;
	
	private int type;
	private int status;
	private int duration;
	private int questionNum;
	private Timestamp startTime;
	
	private Timestamp endTime;
	private String examPhoto;
	private String questionGrade;

	private int createExam;

	private String fillNum;//填空题数量
	private String singleChooseNum;//单选择题数量
	private String manyChooseNum;//多选择题数量
	private String decideNum;//判断题数量
	private String handNum;//操作题数量
	private String commNum;//操作题数量

    public String getHandNum() {
        return handNum;
    }

    public void setHandNum(String handNum) {
        this.handNum = handNum;
    }

    public String getCommNum() {
        return commNum;
    }

    public void setCommNum(String commNum) {
        this.commNum = commNum;
    }

    public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFillNum() {
		return fillNum;
	}

	public void setFillNum(String fillNum) {
		this.fillNum = fillNum;
	}

	public String getSingleChooseNum() {
		return singleChooseNum;
	}

	public void setSingleChooseNum(String singleChooseNum) {
		this.singleChooseNum = singleChooseNum;
	}

	public String getManyChooseNum() {
		return manyChooseNum;
	}

	public void setManyChooseNum(String manyChooseNum) {
		this.manyChooseNum = manyChooseNum;
	}

	public String getDecideNum() {
		return decideNum;
	}

	public void setDecideNum(String decideNum) {
		this.decideNum = decideNum;
	}

	public int getCreateExam() {
		return createExam;
	}

	public void setCreateExam(int createExam) {
		this.createExam = createExam;
	}

	public String getQuestionGrade() {
		return questionGrade;
	}

	public void setQuestionGrade(String questionGrade) {
		this.questionGrade = questionGrade;
	}

	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTotalGrade() {
		return totalGrade;
	}
	public void setTotalGrade(String totalGrade) {
		this.totalGrade = totalGrade;
	}
	public String getHandExamQuestion() {
		return handExamQuestion;
	}
	public void setHandExamQuestion(String handExamQuestion) {
		this.handExamQuestion = handExamQuestion;
	}
	public String getFillBlankExamQuestion() {
		return fillBlankExamQuestion;
	}
	public void setFillBlankExamQuestion(String fillBlankExamQuestion) {
		this.fillBlankExamQuestion = fillBlankExamQuestion;
	}
	public String getCommExamQuestion() {
		return commExamQuestion;
	}
	public void setCommExamQuestion(String commExamQuestion) {
		this.commExamQuestion = commExamQuestion;
	}
	public String getHandExamResult() {
		return handExamResult;
	}
	public void setHandExamResult(String handExamResult) {
		this.handExamResult = handExamResult;
	}
	public String getFillBlankExamResult() {
		return fillBlankExamResult;
	}
	public void setFillBlankExamResult(String fillBlankExamResult) {
		this.fillBlankExamResult = fillBlankExamResult;
	}
	public String getCommExamResult() {
		return commExamResult;
	}
	public void setCommExamResult(String commExamResult) {
		this.commExamResult = commExamResult;
	}
	public String getHandExamAnswer() {
		return handExamAnswer;
	}
	public void setHandExamAnswer(String handExamAnswer) {
		this.handExamAnswer = handExamAnswer;
	}
	public String getFillBlankExamAnswer() {
		return fillBlankExamAnswer;
	}
	public void setFillBlankExamAnswer(String fillBlankExamAnswer) {
		this.fillBlankExamAnswer = fillBlankExamAnswer;
	}
	public String getCommExamAnswer() {
		return commExamAnswer;
	}
	public void setCommExamAnswer(String commExamAnswer) {
		this.commExamAnswer = commExamAnswer;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public String getChooseExamQuestion() {
		return chooseExamQuestion;
	}

	public void setChooseExamQuestion(String chooseExamQuestion) {
		this.chooseExamQuestion = chooseExamQuestion;
	}

	public String getDecideExamQuestion() {
		return decideExamQuestion;
	}

	public void setDecideExamQuestion(String decideExamQuestion) {
		this.decideExamQuestion = decideExamQuestion;
	}

	public String getChooseExamResult() {
		return chooseExamResult;
	}

	public void setChooseExamResult(String chooseExamResult) {
		this.chooseExamResult = chooseExamResult;
	}

	public String getDecideExamResult() {
		return decideExamResult;
	}

	public void setDecideExamResult(String decideExamResult) {
		this.decideExamResult = decideExamResult;
	}
	public String getChooseExamAnswer() {
		return chooseExamAnswer;
	}
	public void setChooseExamAnswer(String chooseExamAnswer) {
		this.chooseExamAnswer = chooseExamAnswer;
	}
	public String getDecideExamAnswer() {
		return decideExamAnswer;
	}
	public void setDecideExamAnswer(String decideExamAnswer) {
		this.decideExamAnswer = decideExamAnswer;
	}
	public String getExamPhoto() {
		return examPhoto;
	}
	public void setExamPhoto(String examPhoto) {
		this.examPhoto = examPhoto;
	}

	@Override
	public String toString() {
		return "Exam{" +
				"examId='" + examId + '\'' +
				", idNumber='" + idNumber + '\'' +
				", grade='" + grade + '\'' +
				", totalGrade='" + totalGrade + '\'' +
				", handExamQuestion='" + handExamQuestion + '\'' +
				", chooseExamQuestion='" + chooseExamQuestion + '\'' +
				", fillBlankExamQuestion='" + fillBlankExamQuestion + '\'' +
				", decideExamQuestion='" + decideExamQuestion + '\'' +
				", commExamQuestion='" + commExamQuestion + '\'' +
				", handExamResult='" + handExamResult + '\'' +
				", chooseExamResult='" + chooseExamResult + '\'' +
				", decideExamResult='" + decideExamResult + '\'' +
				", fillBlankExamResult='" + fillBlankExamResult + '\'' +
				", commExamResult='" + commExamResult + '\'' +
				", handExamAnswer='" + handExamAnswer + '\'' +
				", fillBlankExamAnswer='" + fillBlankExamAnswer + '\'' +
				", chooseExamAnswer='" + chooseExamAnswer + '\'' +
				", decideExamAnswer='" + decideExamAnswer + '\'' +
				", commExamAnswer='" + commExamAnswer + '\'' +
				", examName='" + examName + '\'' +
				", type=" + type +
				", status=" + status +
				", duration=" + duration +
				", questionNum=" + questionNum +
				", startTime=" + startTime +
				", endTime=" + endTime +
				'}';
	}
}
