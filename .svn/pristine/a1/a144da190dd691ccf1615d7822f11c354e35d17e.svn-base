package io.ken.springboot.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
* @author :hwy
* @createtime：2019年11月22日 上午10:33:00
* @detail :
*/

@Controller
@RequestMapping("/huoqu")
public class TestController {

	@ResponseBody
	@RequestMapping(value = "/json/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String getByJSON(@RequestBody JSONObject jsonParam) {

	    System.out.println(jsonParam.toJSONString());

	    // 将获取的json数据封装一层，然后在给返回
	    JSONObject result = new JSONObject();
	    result.put("msg", "ok");
	    result.put("method", "json");
	    result.put("data", jsonParam);

	    return result.toJSONString();
	}


}
