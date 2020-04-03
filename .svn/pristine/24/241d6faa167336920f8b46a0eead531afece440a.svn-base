package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.model.ExcelModel;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lq
 *IFillExamService.java
 * 2019年11月28日
 */
public interface IFillExamService {

	FillBlankExamStore getNameByCode(String fillQCode);

	List<FillBlankExamStore> getAll();

	int add(FillBlankExamStore exam);
	
	List<Map<String,String>> getFillQuestionRandom(String subject);

	int addModel(FillBlankExamStore fillModel);
	
	public List<FillBlankExamStore> getByName(String name);

	/**
	 * excel添加试题
	 * @param excelModel
	 * @return
	 */
	int addExcel(List<ExcelModel> excelModel);
	/**
	 * 用于批量添加试题，查询题名
	 * @param name
	 * @return
	 */
	public ExcelModel queryName(String name,String result);
	/*
	 * 用于修改excel导入试题重复的试题
	 * @param excelModel
	 * @return
	 */
	public int updateQuestions(ExcelModel excelModel);
	/**
	 * 获取当前级别的的试题数量
	 * @param subject 科目
	 * @param level 级别
	 * @return 总数
	 */
    int getCount(String subject, String level);

	/**
	 * 北斗手持机统计题使用
	 * @param subject
	 * @param begin 题号开始位置
	 * @param end 题号结束位置
	 * @param level
	 * @return
	 */
	int getCount(String subject, double begin, double end, String level);

	/**
	 * 随机取出一定数的题
	 * @param creatNewExam
	 * @return
	 */
    List<FillBlankExamStore> getQuestion(CreatNewExam creatNewExam);
	/**
	 * 随机取出一定数的题(手持机使用)
	 * @param creatNewExam
	 * @return
	 */
	List<FillBlankExamStore> getQuestion(CreatNewExam creatNewExam, double begin, double end);
}
