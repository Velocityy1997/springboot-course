package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.ClassDic;
import io.ken.springboot.course.dao.ClassMapper;
import io.ken.springboot.course.model.ClassModel;
import io.ken.springboot.course.service.IClassDicService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ClassDicServiceImpl 
 * user:hwy
 * @date 2019年11月14日
 */
@Service("ClassDicService")
public class ClassDicServiceImpl implements IClassDicService{

    @Resource
    private ClassMapper classMapper;
	
	@Override
	public String getClassNameById(String id) {
		// TODO Auto-generated method stub
		ClassDic classDic = new ClassDic();
		classDic = classMapper.queryById(id);
		if(classDic != null) {
			return classDic.getClassName();
		}
		return null;
	}

	@Override
	public List<ClassModel> getList() {
		// TODO Auto-generated method stub
		List<ClassModel> list = new ArrayList<ClassModel>();
		list = classMapper.getList();
		
		return list;
	}

	@Override
	public List<ClassDic> queryByName(String name) {
		// TODO Auto-generated method stub
		List<ClassDic> list = new ArrayList<ClassDic>();
		list = classMapper.queryByName(name);
		
		return list;
	}
	
	@Override
	public List<ClassDic> getNameList() {
		// TODO Auto-generated method stub
		List<ClassDic> list = new ArrayList<ClassDic>();
		list = classMapper.getNameList();
		
		return list;
	}	
	
	@Override
	public List<ClassDic> getClassList() {
		// TODO Auto-generated method stub
		List<ClassDic> list = new ArrayList<ClassDic>();
		list = classMapper.getClassList();
		
		return list;
	}

	@Override
	public int addClass(ClassDic classDic) {
		// TODO Auto-generated method stub
		return classMapper.addClass(classDic);
	}

	@Override
	public int updateById(ClassDic classDic) {
		// TODO Auto-generated method stub
		return classMapper.updateById(classDic);
	}

	
	
	@Override
	public int delClass(String classId) {
		// TODO Auto-generated method stub
		return classMapper.delClass( classId);
	}	
}
