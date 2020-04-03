package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.Training;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 练习
 * @author YuDong
 * @create 2019-11-20 14:04
 **/

@Mapper
public interface TrainMapper {

    //根据用户id查练习
    @Select("SELECT * FROM  training   where id_number = #{userId}  ORDER BY start_time DESC")
    List<Training> selectByUserId(@Param("userId") String userId);

    @Delete("DELETE FROM training WHERE id_number =#{id}")
	int delById(@Param("id")String id);

    @Insert({
		"INSERT INTO training(training_id,id_number,type,start_time) "
		+ "VALUES(#{trainingId},#{idNumber},#{type},#{startTime})" })
    int add(Training training);
}
