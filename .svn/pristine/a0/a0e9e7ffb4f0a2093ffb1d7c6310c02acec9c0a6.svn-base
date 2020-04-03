package io.ken.springboot.course.controller.exam;

import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.service.IExamService;
import io.ken.springboot.course.tools.Result;
import io.ken.springboot.course.tools.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 考试时间
 * @author lq
 *ExamTimeController.java
 * 2019年11月20日
 */

@Controller
@RequestMapping("/examTime")
public class ExamTimeController {
	
	@Autowired
	private IExamService examService;
	

	//获取学员剩余的考试时间
	@RequestMapping("/getexamtime")
	@ResponseBody
	public Result getExamTime(String examId) {
		
		Exam exam = new Exam();
		Result  result = new Result();
		TimeTools tools = new TimeTools();
		try {
			exam = examService.getModel(examId);
			Timestamp ts = exam.getStartTime();//获取学员考试开始时间
			int duration = exam.getDuration();
			String dataTime = tools.getTime(ts, duration);//获取根据时长与考试时间
			Map<String, String> map = new HashMap<String, String>();
			map.put("dataTime", dataTime);
			result.setData(map);
			result.setCode(200);
			result.setMsg("msg");
		} catch (Exception e) {
			// TODO: handle exception
			Map<String, String> map = new HashMap<String, String>();
			map.put("dataTime", "0");
			result.setData(map);
			result.setCode(404);
			result.setMsg("msg");
		}
		return result;
		
	}

}
