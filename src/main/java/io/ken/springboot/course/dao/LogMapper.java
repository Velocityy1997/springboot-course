package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.Log;
import io.ken.springboot.course.model.LogModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 
 * @author lq
 * LogMapper.java
 * 2019年11月27日
 */
@Mapper
public interface LogMapper {

	
	//@Select("select l.name,u.class_id,u.type,l.time from log l,user u where u.id_number=l.id_number  ORDER BY time DESC")
	@Select("<script>" 
			+ "select l.name,u.class_id,u.type,l.time from log l,user u where u.id_number=l.id_number"
			+" <if test = 'name  != null'> And  l.name = #{name} </if>"
			+ "ORDER BY time DESC"
			+"</script>"
			)
	List<LogModel> getList(@Param("name")String name);

	@Delete("DELETE FROM log WHERE id_number = #{id}")
	int delById(@Param("id")String id);

	@Insert("INSERT INTO log(id,type,name,id_number,time) VALUES (#{id},#{type},#{name},#{id_number},#{time})")
	int insertModel(Log log);

}
