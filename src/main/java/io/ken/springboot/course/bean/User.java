package io.ken.springboot.course.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

/**
 * ClassName: User 
 * user:hwy
 * @date 2019年11月14日
 */
public class User {

    private String id;

    private String name;

    private int sex;

	private String idNumber;

	private String examNames;//学员待考考试名称集合

	private String finishedExam;//学员已考考试名称

	@JsonFormat
    private Timestamp birthday;
    
    private int type;
   
    private String password;
    
    private String classId;
    
    private String className;
    
    private String levelId;
    
    private String tel;
    
    private String photo;
    
    private String handCollectList;
    
    private String fillBlankCollectList;
    
    private String  commCollectList;

    private String chooseBlankCollectList;
    
    private String decideBlankCollectList;
    
    private String errhandCollectList;
    
    private String errfillBlankCollectList;
    
    private String  errcommCollectList;

    private String errchooseCollectList;
    
    private String errdecideBlankCollectList;

    private String state;//用户状态：1：新用户(新学员主动登录的)；0：老用户(管理员录入的人)

	public String getFinishedExam() {
		return finishedExam;
	}

	public void setFinishedExam(String finishedExam) {
		this.finishedExam = finishedExam;
	}

	public String getExamNames() {
		return examNames;
	}

	public void setExamNames(String examNames) {
		this.examNames = examNames;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	
	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getHandCollectList() {
		return handCollectList;
	}

	public void setHandCollectList(String handCollectList) {
		this.handCollectList = handCollectList;
	}

	public String getFillBlankCollectList() {
		return fillBlankCollectList;
	}

	public void setFillBlankCollectList(String fillBlankCollectList) {
		this.fillBlankCollectList = fillBlankCollectList;
	}

	public String getCommCollectList() {
		return commCollectList;
	}

	public void setCommCollectList(String commCollectList) {
		this.commCollectList = commCollectList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getErrhandCollectList() {
		return errhandCollectList;
	}

	public void setErrhandCollectList(String errhandCollectList) {
		this.errhandCollectList = errhandCollectList;
	}

	public String getErrfillBlankCollectList() {
		return errfillBlankCollectList;
	}

	public void setErrfillBlankCollectList(String errfillBlankCollectList) {
		this.errfillBlankCollectList = errfillBlankCollectList;
	}

	public String getErrcommCollectList() {
		return errcommCollectList;
	}

	public void setErrcommCollectList(String errcommCollectList) {
		this.errcommCollectList = errcommCollectList;
	}

	public String getChooseBlankCollectList() {
		return chooseBlankCollectList;
	}

	public void setChooseBlankCollectList(String chooseBlankCollectList) {
		this.chooseBlankCollectList = chooseBlankCollectList;
	}

	public String getDecideBlankCollectList() {
		return decideBlankCollectList;
	}

	public void setDecideBlankCollectList(String decideBlankCollectList) {
		this.decideBlankCollectList = decideBlankCollectList;
	}

	public String getErrchooseCollectList() {
		return errchooseCollectList;
	}

	public void setErrchooseCollectList(String errchooseCollectList) {
		this.errchooseCollectList = errchooseCollectList;
	}

	public String getErrdecideBlankCollectList() {
		return errdecideBlankCollectList;
	}

	public void setErrdecideBlankCollectList(String errdecideBlankCollectList) {
		this.errdecideBlankCollectList = errdecideBlankCollectList;
	}



}
