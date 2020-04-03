package io.ken.springboot.course.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



/**
 * 收藏查询
 * @author lq
 *CollectMapper.java
 * 2019年11月19日
 */
@Mapper
public interface CollectMapper {

	//操作题题目查询
	@Select("SELECT question_name FROM hand_exam_store  WHERE question_code = #{question_code} ")
	String getHandQues(@Param("question_code")String question_code);

	@Select("SELECT question_name FROM fill_blank_exam_store  WHERE question_code = #{question_code} ")
	String getFillQues(@Param("question_code")String question_code);

	@Select("SELECT question_name FROM comm_exam_store  WHERE question_code = #{question_code}")
	String getCommQues(@Param("question_code")String question_code);

	

}
