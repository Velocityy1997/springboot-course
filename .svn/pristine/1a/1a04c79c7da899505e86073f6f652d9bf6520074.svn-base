package io.ken.springboot.course.controller;
	
import io.ken.springboot.course.bean.ClassDic;
import io.ken.springboot.course.service.IClassDicService;
import io.ken.springboot.course.service.IExamService;
import io.ken.springboot.course.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: 数据分析controller
 * user:cll
 * @date 2019年11月14日
 */
@Controller
@RequestMapping("/dataAnalysis")
public class DataAnalysisController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IClassDicService classDicService;
	
	@Autowired
	private IExamService examService;

	/*
	 * 总体情况
	 */
    @RequestMapping("/zongti")
    @ResponseBody
    public Map<String, Object> queryZongti() {
    	Map<String, Object> map1 = new HashMap<String, Object>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<ClassDic> classList = classDicService.getClassList();
    	map.put("classNum", classList.size());
    	int userNum = userService.getUserCount();
    	map.put("classStudent", userNum);
    	List<String> nameList = examService.getExamName();
    	map.put("textNum", nameList.size());
    	int isExamNum = examService.getExamByStatus();
    	map.put("testStudent", isExamNum);
    	int exams = examService.getExamNum();
    	String st= "0";
    	if(exams != 0) {
    		double level = Double.valueOf(isExamNum)/Double.valueOf(exams);//考过的除以所有的
        	DecimalFormat df=new DecimalFormat("0.##");
        	st=df.format(level);
    	}   	    	
    	map.put("testObject", 1); 
    	map.put("completePre", st);   	
    	map1.put("data", map);
    	return map1;
    }

    //班级和学生情况
    @RequestMapping("/classandstudent")
    @ResponseBody
    public Map<String, Object> classAndStudent() {
    	Map<String, Object> map1 = new HashMap<String, Object>();
    	List<Map<String,String>> yMap = new ArrayList<Map<String,String>>();
    	List<ClassDic>  classList = classDicService.getNameList();
    	for(ClassDic classname:classList) {
    		Map map = new HashMap<String, Object>();
    		map.put("yName", classname.getClassName());
    		int num = userService.getUserByClass(classname.getClassId());//总人数
    		int manVal = userService.getUserByClassAndSex(classname.getClassId());//男生数量
    		String count = String.valueOf(num);
    		int girlVal = num - manVal;
    		map.put("manVal", manVal);
    		map.put("girlVal", girlVal);
    		yMap.add(map);
    	}
    	
    	
    	map1.put("data", yMap);
    	return map1;
    }
	@ApiOperation(value = "合格", notes = "合格")
    @RequestMapping("/getexcellent")
    @ResponseBody
    public Map<String, Object> getExcellent() {
    	Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Map<String,String>> map =null;
		//listMap出现的目的是为了配合前台的json格式，将科目按照一定顺序排列返回
		List<Map<String,Map<String,String>>> listMap=new ArrayList<Map<String, Map<String,String>>>();
    	Map<String,String> yMap =null;

		double i1=0;//合格数
		double i2=0;//不合格数
		double sum=1.0;//总人数

		for (int i=1;i<7;i++){
			//考试科目：1-北斗手持机；2-电台操作；3-理论知识；4-航片判读；5-作战计算；6-识图用图
			yMap = new HashMap<String,String>();
			i1 = examService.getStandard(i);
			i2 = examService.getNotStandard(i);
			if(i1!=0||i2!=0){
				sum=i1+i2;
			}

            yMap.put("pass", String.valueOf(new DecimalFormat("0.00").format(i1/sum*100) ));
            yMap .put("unPass",String.valueOf(new DecimalFormat("0.00").format(i2/sum*100) ));

			map = new HashMap<String, Map<String,String>>();
			switch (i){
				case 1:
					map.put("bdscj", yMap);
					break;
				case 2:
					map.put("dtcz", yMap);
					break;
				case 3:
					map.put("llzs", yMap);
					break;
				case 4:
					map.put("hppd", yMap);
					break;
				case 5:
					map.put("zzjs", yMap);
					break;
				case 6:
					map.put("styt", yMap);
					break;
			}
			listMap.add(map);
		}

		map1.put("data", listMap);
        return map1;
    }
}
