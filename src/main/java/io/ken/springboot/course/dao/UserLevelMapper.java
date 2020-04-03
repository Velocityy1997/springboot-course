package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.UserLevelDic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserLevelMapper {

	
	@Select("SELECT name from user_level_dic  WHERE level_id = #{level}")
	String getLevelName(@Param("level")String level);

	@Select("SELECT * from user_level_dic")
	List<UserLevelDic> getList();

}
