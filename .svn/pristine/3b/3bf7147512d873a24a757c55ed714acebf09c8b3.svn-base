package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lq
 *IHandExamService.java
 * 2019年11月28日
 */
public interface IHandExamService {

	HandExamStore getNameByCode(String handQCode);
	
	List<Map<String,String>> getQuestionRandom(String subject);
	
	List<HandExamStore> getQuestionRandom1(String subject);

	int addModel(HandExamStore handExamStore);
	/**
	 * 北斗手持机题数统计
	 * @param subject 科目
	 * @param begin 题号开始位置
	 * @param end 结束位置
	 * @param level 级别
	 * @return
	 */
    int getCount(String subject, double begin, double end, String level);

	/**
	 * 北斗手持机取题
	 * @param creatNewExam
	 * @param begin
	 * @param end
	 * @return
	 */
	List<HandExamStore> getQuestion(CreatNewExam creatNewExam, double begin, double end);
}
