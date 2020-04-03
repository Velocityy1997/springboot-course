package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lq
 *CommExamMapper.java
 * 2019年11月28日
 */
@Mapper
public interface CommExamMapper {

	
	@Select("SELECT *  FROM  comm_exam_store WHERE question_code =#{comCode}")
	CommExamStore getNameByCode(@Param("comCode")String comCode);
	
	@Select("SELECT *  FROM  comm_exam_store WHERE question_name =#{name}")
	List<CommExamStore> getByName(@Param("name")String name);
	
	
	@Insert({
		"INSERT INTO comm_exam_store(id,question_name,result,question_code,type,grade,pass_table_id,t_f_table_id,level_id) "
		+ "VALUES(#{id},#{questionName},#{result},#{questionCode},#{type},#{grade},"
				+ "#{passTableId},#{tFTableId},#{levelId})" })
    int add(CommExamStore exam);
	
	@Select("select question_name,question_code,type,pass_table_id,result FROM "
			+ "comm_exam_store ")
	//ORDER BY rand() LIMIT 0, 1
	List<Map<String,Object>> getCommQuestionRandom();
	/**
	 * 统计可选择的题数
	 *
	 * @param subject 科目
	 * @param level   级别
	 * @return
	 */
	@Select("SELECT count(*) FROM comm_exam_store WHERE subject=#{subject} AND level_id LIKE CONCAT('%',#{level},'%')")
    int getCount(@Param("subject") String subject,@Param("level") String level);

	/**
	 * 取出一定数量的题
	 * @param creatNewExam
	 * @return
	 */
	@Select("SELECT * from comm_exam_store where subject=#{subject} AND level_id=#{level} LIKE CONCAT('%',#{level},'%') order by rand() limit #{pack}")
	List<CommExamStore> getQuestion(CreatNewExam creatNewExam);
}
