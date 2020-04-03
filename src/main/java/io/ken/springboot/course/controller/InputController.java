package io.ken.springboot.course.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import io.ken.springboot.course.model.CollectModel;
import io.ken.springboot.course.model.InputChinese;
import io.ken.springboot.course.service.ICollectService;
import io.ken.springboot.course.tools.DataConfig;

/**
 * 输入法
 * @author 
 */

@Controller
@RequestMapping("/inputc")
public class InputController {
	
	@Autowired 
	private DataConfig dataConfig;
		
	@RequestMapping("/getinputC")
    @ResponseBody
	public Map<String,List<InputChinese>> getinputC(String code) {
		 
		Map<String, String> data1 = dataConfig.getData1();
		Map<String, String> data2 = dataConfig.getData2();
		String lettercodes = data1.get(code);
		if(lettercodes != null) {
			List<InputChinese> inputs = new ArrayList<InputChinese>();
			String[] letters = lettercodes.split(",");
			for(String letter:letters) {
				InputChinese input = new InputChinese();
				input.setInputC(data2.get(letter));
				input.setLetterCode(letter);
				inputs.add(input);
			}
			Map<String,List<InputChinese>> map = new HashMap<String, List<InputChinese>>();
			map.put("data", inputs);
			return map;
		}
		return null;
	}

	
	
	
	
}
