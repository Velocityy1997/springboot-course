package io.ken.springboot.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.ken.springboot.course.bean.ClassDic;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.model.ClassModel;

/**
 * ClassName: ClassMapper 
 * user:hwy
 * @date 2019年11月14日
 */
@Mapper
public interface ClassMapper {

	@Select("SELECT * FROM class_dic WHERE class_id = #{id}")
	ClassDic queryById(@Param("id") String id);

	@Select("SELECT user.name ,user.class_id , class_dic.class_id , class_dic.class_name"
			+ " FROM class_dic RIGHT JOIN user ON user.class_id = class_dic.class_id")
	List<ClassModel> getList();

	@Select("SELECT * FROM class_dic")
	List<ClassDic> getNameList();

	@Select("SELECT * FROM class_dic")
	List<ClassDic> getClassList();

	@Insert("INSERT INTO class_dic (class_id,class_name) VALUES (#{classId},#{className}) ")
	int addClass(ClassDic classDic);

	
	@Delete("DELETE FROM class_dic WHERE class_id = #{classId}")
	int delClass(@Param("classId")String classId);
	
	@Update("UPDATE class_dic SET class_id = #{classId},class_name = #{className} WHERE class_id = #{classId}")
	int updateById(ClassDic classDic);
	
	@Select("SELECT * FROM class_dic WHERE class_name = #{className}")
	List<ClassDic> queryByName(@Param("className") String className);
}
