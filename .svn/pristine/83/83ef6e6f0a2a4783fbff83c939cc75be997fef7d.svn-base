package io.ken.springboot.course.dao.exam.batch;
/*
 *  @author: GouYudong
 *  创建时间:  2020年3月12日下午2:25:15
 */

import io.ken.springboot.course.bean.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ExamHadleMapper {

	//批量更新
	int batchUpdate(@Param("list") List<Exam> list);

	//单个更新
	int updateExam(@Param("examObj") Exam exam);

	//批量插入数据
	int batchInsert(@Param("list") List<Exam> list);

}

