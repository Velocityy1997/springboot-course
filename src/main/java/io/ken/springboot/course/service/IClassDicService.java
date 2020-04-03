package io.ken.springboot.course.service;

import java.util.List;

import io.ken.springboot.course.bean.ClassDic;
import io.ken.springboot.course.model.ClassModel;

/**
 * ClassName: IClassDicService 
 * user:hwy
 * @date 2019年11月14日
 */
public interface IClassDicService {

	public String getClassNameById(String id);

	public List<ClassModel> getList();

	public List<ClassDic> getNameList();

	public List<ClassDic> getClassList();

	public int addClass(ClassDic classDic);

	public int delClass(String classId);
	
	public List<ClassDic> queryByName(String name);
	
	public int updateById(ClassDic classDic);
}
