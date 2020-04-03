package io.ken.springboot.course.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.ken.springboot.course.bean.UserLevelDic;
import io.ken.springboot.course.service.IUserLevelDicService;

/**
 * 
 * @author lq
 *UserLevelDicController.java
 * 2019年11月26日
 */
@Controller
@RequestMapping("/userlevel")
public class UserLevelDicController {
	
	
	@Autowired
	private IUserLevelDicService userLevelDicService; 
	
	
	@RequestMapping("/list")
	@ResponseBody
	public List<UserLevelDic> getList(){
		List<UserLevelDic> list = new ArrayList<UserLevelDic>();
		try {
			list = userLevelDicService.getList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}

}
