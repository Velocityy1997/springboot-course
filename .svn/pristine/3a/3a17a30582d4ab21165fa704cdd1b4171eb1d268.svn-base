package io.ken.springboot.course.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import io.ken.springboot.course.model.LogListModel;
import io.ken.springboot.course.model.LogModel;
import io.ken.springboot.course.service.IClassDicService;
import io.ken.springboot.course.service.ILogService;
import io.ken.springboot.course.tools.Result2;
import io.ken.springboot.course.tools.Result3;


/**
 * 登录日志
 * @author lq
 *LogController.java
 * 2019年11月27日
 */
@RequestMapping("/log")
@Controller
public class LogController {
	
	@Autowired
	private ILogService logService;
	
	@Autowired
	private IClassDicService classDicService;
	
	
	/**
	 * 登录日志列表
	 * @param limit
	 * @param offset
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	 public Result3 getList(int limit, int offset,String name) {
		
		if (name.equals("")) {
			name = null;
		} 		
		Result3 result= new Result3();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<LogModel> logList = new ArrayList<LogModel>();
		List<LogListModel> modelList = new ArrayList<LogListModel>();
		try {
			PageHelper.offsetPage(offset, limit);
			logList = logService.getList(name);
			PageInfo<LogModel> pageInfo = new PageInfo<LogModel>(logList);
			
			for (int i = 0; i < logList.size(); i++) {
				LogListModel model = new LogListModel();
				String classId = logList.get(i).getClassId();
				String className = classDicService.getClassNameById(classId);
				String userName = logList.get(i).getName();
				Timestamp time = logList.get(i).getTime();
				
				int type = logList.get(i).getTyep();
				if (type == 0) {
					model.setJurisdiction("管理员");
				}else {
					model.setJurisdiction("学员");
				}
				model.setClassName(className);
				model.setLoginTime(sdf.format(time));
				model.setName(userName);
				modelList.add(model);
				
			}
			
			
			result.setCode(200);
			result.setMsg("ok");
			result.setRows(modelList);
			result.setTotal(pageInfo.getTotal());
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setCode(400);
			result.setMsg("ok");
			result.setRows(modelList);
			result.setTotal(0L);
		}
		return result;
		
	}

}
