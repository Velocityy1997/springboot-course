package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lq
 *ICommExamService.java
 * 2019年11月28日
 */
public interface ICommExamService {

	CommExamStore getNameByCode(String comCode);

	int add(CommExamStore exam);
	
	List<Map<String,Object>> getCommQuestionRandom();

	public List<CommExamStore> getByName(String name);
	/**
	 * 统计可选择的题数
	 *
	 * @param subject 科目
	 * @param level   级别
	 * @return
	 */
    int getCount(String subject, String level);

	/**
	 * 随机取出一定量的题
	 * @param creatNewExam
	 * @return
	 */
	List<CommExamStore> getQuestion(CreatNewExam creatNewExam);
}
