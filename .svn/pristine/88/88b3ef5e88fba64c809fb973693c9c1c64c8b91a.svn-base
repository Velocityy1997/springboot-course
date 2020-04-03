package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.model.QuestionModel;
import java.util.List;
import java.util.Map;


/**
 * ClassName: IPaperService 
 * user:cll
 * @date 2019年11月14日
 */
public interface IPaperService {

	List<Map<String,Object>> getPapers(String subject);

	//获取科目的题型分类
	List<QuestionType> getSubjectSons(Integer subject);

	List<QuestionModel> getFillBySubject(int subject);
	List<QuestionModel> getChooseBySubject(int subject);
	List<QuestionModel> getDecideBySubject(int subject);
	List<QuestionModel> getHandBySubject(int subject);
}
