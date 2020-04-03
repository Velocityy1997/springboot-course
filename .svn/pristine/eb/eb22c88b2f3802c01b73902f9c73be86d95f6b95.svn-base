package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.model.QuestionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * ClassName: mapper user:cll 试题库的mapper
 *
 * @date 2019年11月14日
 */
@Mapper
public interface PaperMapper {

	//试题分页查询

	@Select("select d.question_code,d.type,d.question_name,d.grade,d.t_name  from (select a.question_code,a.type,a.question_name,a.grade,'a' as t_name from comm_exam_store a   WHERE subject =#{subject} UNION all\n" +
			"select b.question_code,b.type,b.question_name,b.grade,'b' as t_name from fill_blank_exam_store b  WHERE subject =#{subject} UNION all "
			+ "select c.question_code,c.type,c.question_name,c.grade,'c' as t_name from hand_exam_store c  WHERE subject =#{subject} "
			+ " UNION all select ss.question_code,ss.type,ss.question_name,ss.grade,'ss' as t_name from choose_select_store ss  WHERE subject =#{subject} "
			+ " UNION all select dd.question_code,dd.type,dd.question_name,dd.grade,'dd' as t_name from decide_store dd  WHERE subject =#{subject} ) d")
	List<Map<String, Object>> getPapers(@Param("subject") String subject);

	/**
	 * 查看科目的试题分类
	 *
	 * @param subject
	 * @return
	 */
	@Select("select  *  FROM  question_type  WHERE  subject = #{subject}")
	List<QuestionType> querySons(@Param("subject") int subject);

	//根据科目查填空题
	@Select("SELECT  sub.NAME AS object, q.typename as famliy,fill.question_name as question,fill.grade,fill.question_code  as id  FROM fill_blank_exam_store AS fill LEFT JOIN 	question_type AS q on fill.type = q.id  LEFT JOIN  subject AS sub   ON  fill.subject = sub.type WHERE  fill.subject =#{subject}" )
	List<QuestionModel> queryFillBySubject(@Param("subject") int subject);

	//根据科目查选择题
	@Select("SELECT  sub.NAME AS object, q.typename as famliy,choose.question_name as question,choose.grade,choose.question_code  as id  FROM choose_select_store AS choose LEFT JOIN 	question_type AS q on choose.type = q.id  LEFT JOIN  subject AS sub   ON  choose.subject = sub.type WHERE  choose.subject =#{subject}" )
	List<QuestionModel> queryChooseBySubject(@Param("subject") int subject);

	//根据科目查判断题
	@Select("SELECT  sub.NAME AS object, q.typename as famliy,decide.question_name as question,decide.grade,decide.question_code  as id  FROM decide_store AS decide LEFT JOIN 	question_type AS q on decide.type = q.id  LEFT JOIN  subject AS sub   ON  decide.subject = sub.type WHERE  decide.subject =#{subject}" )
	List<QuestionModel> queryDecideBySubject(@Param("subject") int subject);

	//根据科目查操作题
	@Select("SELECT  sub.NAME AS object, q.typename as famliy,hand.question_name as question,hand.grade,hand.question_code  as id  FROM hand_exam_store AS hand LEFT JOIN 	question_type AS q on hand.type = q.id  LEFT JOIN  subject AS sub   ON  hand.subject = sub.type WHERE  hand.subject =#{subject}" )
	List<QuestionModel> queryHandBySubject(@Param("subject") int subject);

}
