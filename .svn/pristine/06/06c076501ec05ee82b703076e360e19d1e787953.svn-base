package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface HandExamMapper {

	
	@Select("SELECT *  FROM  hand_exam_store WHERE question_code =#{handQCode}")
	HandExamStore getNameByCode(@Param("handQCode")String handQCode);
	
	@Select("select question_name,question_code FROM hand_exam_store where  question_code != '1.1'  and subject =#{subject}")
	List<Map<String,String>> getQuestionRandom(@Param("subject")String subject);
	
	@Select("select * FROM hand_exam_store where  question_code != '1.1'  and subject =#{subject}")
	List<HandExamStore> getQuestionRandom1(@Param("subject")String subject);


	@Select("SELECT *  FROM  hand_exam_store WHERE question_name =#{name}")
	List<HandExamStore> getByName(@Param("name")String name);

	//新增
	@Insert("insert into hand_exam_store(id,question_name,question_code,type,grade,subject,level_id) values(#{id},#{questionName},#{questionCode},#{type},#{grade},#{subject},#{levelId})")
	int insertModel(HandExamStore handExam);
	/**
	 * 北斗手持机题数统计
	 * @param subject 科目
	 * @param begin 题号开始位置
	 * @param end 结束位置
	 * @param level 级别
	 * @return
	 */
	@Select("SELECT count(*) FROM hand_exam_store WHERE subject=#{subject} AND question_code BETWEEN #{begin} AND #{end} AND level_id LIKE CONCAT('%',#{level},'%')")
    int getCount(@Param("subject") String subject,@Param("begin") double begin, @Param("end") double end, @Param("level") String level);

	/**
	 * 北斗手持机取出一定量题
	 * @param creatNewExam
	 * @return
	 */
	@Select("SELECT * from hand_exam_store where subject=#{subject} AND level_id=#{level} LIKE CONCAT('%',#{level},'%') order by rand() limit #{pack}")
	List<HandExamStore> getQuestion(CreatNewExam creatNewExam);
}
