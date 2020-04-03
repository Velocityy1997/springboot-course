package io.ken.springboot.course.controller;
	
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.model.DataModel;
import io.ken.springboot.course.service.IClassDicService;
import io.ken.springboot.course.service.IUserService;
import io.ken.springboot.course.tools.Result;
import io.ken.springboot.course.tools.TimeTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: UserController 
 * user:cll
 * @date 2019年11月14日
 */
@Controller
public class LoginController {
	/**
	 * 跳转登录页
	 * @param request
	 * @param systemUser
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "loginAni";
	}


}
