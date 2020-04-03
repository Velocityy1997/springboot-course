package io.ken.springboot.course.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.ken.springboot.course.bean.ClassDic;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.model.ClassModel;
import io.ken.springboot.course.model.ClassModels;
import io.ken.springboot.course.service.IClassDicService;
import io.ken.springboot.course.service.IUserService;
import io.ken.springboot.course.tools.Result;

/**
 * 班级管理
 * @author lq
 *ClassManagement.java
 * 2019年11月21日
 */

@Controller
@RequestMapping("/class")
public class ClassManagement {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IClassDicService classDicService;
	
	/**
	 * 
	 * @param limit条数
	 * @param offset页数
	 * @return
	 */
	@RequestMapping("/getcalssmanage")
    @ResponseBody
	public Map<String, Object>  getClassManage(int limit, int offset) {
		
		PageHelper.startPage(offset, limit);
		List<ClassModel> list = new ArrayList<ClassModel>();
		List<ClassDic> nameList = new ArrayList<ClassDic>();
		List<ClassModels> modelList = new ArrayList<ClassModels>();
		String className  = null;
		String className1 = null;
		String classId  = null;
		
		try {
			nameList = classDicService.getNameList();
			list = classDicService.getList();
			PageInfo<ClassDic> pageInfo = new PageInfo<ClassDic>(nameList);
			List<ClassDic> pageName = pageInfo.getList();
			
			for (int i = 0; i < pageName.size(); i++) {
				int num = 0;
				className = pageName.get(i).getClassName();
				classId = pageName.get(i).getClassId();
				ClassModels t1 = new ClassModels();
				for (int j = 0; j < list.size(); j++) {
						className1 = list.get(j).getClassName();
						if (className.equals(className1)) {
							num++;
						} else {
							continue;
						}
				}
				t1.setClassName(className);
				t1.setId(classId);
				t1.setStuNum(String.valueOf(num));
				modelList.add(t1);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", modelList);
			map.put("total", pageInfo.getTotal());
			
			return map;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", modelList);
			map.put("total", "0");
			return map;
		}								
	}
	
	/**
	 * @author lq 
	 * @return
	 */
	
	@RequestMapping("/list")
	@ResponseBody
	public List<ClassDic> getList(){
		
		 List<ClassDic>  classList = new ArrayList<ClassDic>();
		 try {
			 classList=  classDicService.getNameList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 
		return classList;
		
	}
	
	/**
	 * 添加班级 lq
	 * @param className
	 * @return
	 */
	@RequestMapping("/Add")
	@ResponseBody
	public Result addClas(String className) {
		
		
		Result result = new Result();
		ClassDic classDic = new ClassDic();
		
		List<ClassDic> cl = classDicService.queryByName(className);
		if(cl != null && cl.size() >0) {
			result.setCode(404);
			result.setMsg("班级名已存在");
			return result;
		}
		
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		classDic.setClassId(uuid);
		classDic.setClassName(className);
		int num = classDicService.addClass(classDic);
		if (num > 0) {
			result.setCode(200);
			result.setMsg("添加成功");
			result.setData(true);
			
		} else {
			result.setCode(404);
			result.setData(false);
		}
		
		return result;
		
	}
	
	
	/**
	 * 删除班级
	 * @param classId
	 * @return
	 */
	@RequestMapping("/Delete")
	@ResponseBody
	public Result delClass(String id) {
		
		Result result = new Result();
		String className = null;
		String classNum []= id.split(",");
		List<User> userList = new ArrayList<User>();
		List<String> list = new ArrayList<String>();
		String reason = null;
		try {
			result.setMsg("删除成功");	
			for (int i = 0; i < classNum.length; i++) {
				id = classNum[i];
				className = classDicService.getClassNameById(id);
				userList = userService.queryList(id);
				if ( userList.size() > 0 ) {
					reason = "班级" + className + "存在学员，无法删除"+ ";";
					list.add(reason);
					continue;
				}else {
					int num = classDicService.delClass(id);
					if (num > 0) {
						result.setCode(200);
						result.setData(true);
					} else {
						result.setCode(404);
						result.setData(false);
					}
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}					
			String reasons = String.join(";", list);
			if(reasons != null && !(reasons.equals(""))) {
			result.setMsg(reasons);	
			}
			return result;
	}
	
	@RequestMapping("/Edit")
	@ResponseBody
	public Result edit(String id, String className) {
		String name = classDicService.getClassNameById(id);
		List<ClassDic> cl = classDicService.queryByName(className);
		Result result = new Result();
		if(!(name.equals(className)) && cl.size() >0) {
			result.setCode(404);
			result.setMsg("班级名已存在");
			return result;
		}
		else {
			ClassDic dic = new ClassDic();
			dic.setClassId(id);
			dic.setClassName(className);
			classDicService.updateById(dic);
			result.setCode(200);
			result.setMsg("修改成功");
			return result;
		}
	}

}
