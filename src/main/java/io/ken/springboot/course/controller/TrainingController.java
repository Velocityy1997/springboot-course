package io.ken.springboot.course.controller;

import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.Training;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.model.exam.HandExamInfoModel;
import io.ken.springboot.course.model.exam.MobileInModel1;
import io.ken.springboot.course.model.exam.TestDataExamModel;
import io.ken.springboot.course.service.IChooseSelectStoreService;
import io.ken.springboot.course.service.ICommExamService;
import io.ken.springboot.course.service.IDecideStoreService;
import io.ken.springboot.course.service.IExamService;
import io.ken.springboot.course.service.IFillExamService;
import io.ken.springboot.course.service.IHandExamService;
import io.ken.springboot.course.service.ITrainService;
import io.ken.springboot.course.service.implement.ExamInfoServiceImpl;
import io.ken.springboot.course.tools.PhotoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 训练抽题
 * @author cll
 *ExamController.java
 * 2019年11月20日
 */
@Controller
@RequestMapping("/train")
public class TrainingController {

	
	@Autowired
	private IExamService examService;
	
	@Autowired
    private ExamInfoServiceImpl examInfoServiceImpl;
	
	@Autowired
	private ICommExamService commExamService;
	
	@Autowired
	private IFillExamService fillExamService;
	
	@Autowired
	private IHandExamService handExamService;
	
	@Autowired
	private ITrainService trainService;
	
	@Autowired
	private IChooseSelectStoreService chooseSelectStoreService;
	
	@Autowired
	private IDecideStoreService decideStoreService;
	
	@RequestMapping("/gettraining")
	@ResponseBody
	public Map<String, Object> getTraining(HttpServletRequest request,String subject ) {
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		String result=" ";
		User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户	
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		if(subject.equals("1")) {
			HandExamInfoModel handExamInfoModel = new HandExamInfoModel();										
			map.put("examId", uuid);
			List<Map<String,String>> handList = handExamService.getQuestionRandom(subject);
			String[] arr =new String[handList.size()+1];
			
			arr[0] = "1.1";
			for (int i=0;i<handList.size();i++){
				arr[i+1] = handList.get(i).get("question_code");
			}

	        Map<String,Map> handmap = examInfoServiceImpl.getHandExam1(arr, 0,uuid);//操作题	      
			List<Map<String,String>> fillList = fillExamService.getFillQuestionRandom(subject);
			String[] arrfill =new String[fillList.size()];
			for(int i=0;i<fillList.size();i++) {
				arrfill[i] = fillList.get(i).get("question_code");
			}
			Map<String, MobileInModel1> fillmap = examInfoServiceImpl.getMobileExamByCodeTrain();//填空题
			
			map.put("flagData", handmap);
			map.put("scjData", fillmap);
			
			List<TestDataExamModel> testmapList = new ArrayList<TestDataExamModel>();//所有的题
			TestDataExamModel testmap1 = new TestDataExamModel();
			testmap1.setNumber(""+1);
			testmap1.setTable("hand_exam_store");
			testmap1.setQuestion_code("1.1");
			testmap1.setQuestion_name("开机");
			testmap1.setResult("true");
			testmap1.setSelect("");
			testmap1.setType("0");
			testmap1.setImg(null);
			testmapList.add(testmap1);
			for(int i=0; i<handList.size();i++) {//去拿选出的操作题2
				testmap1 = new TestDataExamModel();
				testmap1.setNumber(""+testmapList.size()+1);
				testmap1.setTable("hand_exam_store");
				testmap1.setQuestion_code(handList.get(i).get("question_code"));
				testmap1.setQuestion_name(handList.get(i).get("question_name"));
				testmap1.setResult("true");
				testmap1.setSelect("");
				testmap1.setType("0");
				testmap1.setImg(null);
				testmapList.add(testmap1);
			}
			List<TestDataExamModel>  list1 =  examInfoServiceImpl.getFillbycode(arrfill,testmapList.size(),fillmap);//填空题2
			testmapList.addAll(list1);
			List<Map<String,Object>> commList = commExamService.getCommQuestionRandom();//通信题1
			for(int i=0; i<commList.size();i++) {//去拿选出的操作题			
				TestDataExamModel testmap = new TestDataExamModel();
				testmap.setNumber(""+(testmapList.size()+1));//题号
				testmap.setTable("comm_exam_store");
				testmap.setQuestion_code((String) commList.get(i).get("question_code"));
				testmap.setQuestion_name((String) commList.get(i).get("question_name"));
				if ((String)commList.get(i).get("result")!=null){
					result=(String)commList.get(i).get("result");
				}else {
					result=" ";
				}
				testmap.setResult(result);
				if((Integer)commList.get(i).get("type") == 4) {
					testmap.setType("1");
				}
				if((Integer)commList.get(i).get("type") == 5) {
					testmap.setType("2");
				}
				if(commList.get(i).get("pass_table_id") != null) {
					testmap.setImg(PhotoUtil.sqlTophoto((String)commList.get(i).get("pass_table_id")));
				}
				testmap.setSelect("");
				testmapList.add(testmap);
			}
			List<Map<String,Object>> selectList = chooseSelectStoreService.getSelectQuestionRandom(subject);//选择题2
			int num =testmapList.size();
			for(int i=0; i<selectList.size();i++) {//去拿选出的选择题			
				TestDataExamModel testmap = new TestDataExamModel();
				testmap.setNumber(""+(num++));
				testmap.setTable("choose_select_store");
				testmap.setQuestion_code((String) selectList.get(i).get("question_code"));
				testmap.setQuestion_name((String) selectList.get(i).get("question_name"));
				if ((String) selectList.get(i).get("result")!=null){
					result=(String) selectList.get(i).get("result");
				}else {
					result=" ";
				}
				testmap.setResult(result);
				if((Integer)selectList.get(i).get("flag") == 0) {
					testmap.setType("3");
				}
				if((Integer)selectList.get(i).get("flag") == 1) {
					testmap.setType("4");
				}
				if(selectList.get(i).get("pass_table_id") != null) {													
					testmap.setImg(PhotoUtil.sqlTophoto((String)selectList.get(i).get("pass_table_id")));
				}
				testmap.setSelect((String) selectList.get(i).get("select_a"));
				testmapList.add(testmap);
//				num++;
			}
			List<Map<String,Object>> decideList =  decideStoreService.getDecideQuestionRandom(subject);//判断题1
			num = testmapList.size();
			for(int i=0; i<decideList.size();i++) {//去拿选出的判断题			
				TestDataExamModel testmap = new TestDataExamModel();
				testmap.setNumber(""+(num++));
				testmap.setTable("decide_store");
				testmap.setQuestion_code((String) decideList.get(i).get("question_code"));
				testmap.setQuestion_name((String) decideList.get(i).get("question_name"));
				if ((String) decideList.get(i).get("result")!=null){
					result=(String) decideList.get(i).get("result");
				}else {
					result=" ";
				}
				testmap.setResult(result);
				testmap.setType("3");
				if(decideList.get(i).get("pass_table_id") != null) {
					testmap.setImg(PhotoUtil.sqlTophoto((String)decideList.get(i).get("pass_table_id")));
				}
				testmap.setSelect((String) decideList.get(i).get("select_a"));
				testmapList.add(testmap);
			}
			map.put("testData", testmapList);
		}
		else {										
			map.put("examId", uuid);
			
			List<TestDataExamModel> testmapList = new ArrayList<TestDataExamModel>();//所有的题
			TestDataExamModel testmap1 = new TestDataExamModel();
			List<HandExamStore> handList = handExamService.getQuestionRandom1(subject);
			int num = 1;
			if(handList != null && handList.size() > 0) {
				for(int i=0; i<handList.size();i++) {
					TestDataExamModel testmap = new TestDataExamModel();
					testmap.setNumber(""+num);
					testmap.setTable("hand_exam_store");
					testmap.setQuestion_code(handList.get(i).getQuestionCode());
					testmap.setQuestion_name(handList.get(i).getQuestionName());
					if (handList.get(i).getResult()!=null){
						result=handList.get(i).getResult();
					}else {
						result=" ";
					}

					testmap.setResult(result);
					
					testmap.setType("0");
					
					if(handList.get(i).getPassTableId() != null) {
						testmap.setImg(PhotoUtil.sqlTophoto(handList.get(i).getPassTableId()));
					}
					testmap.setSelect("");
					testmapList.add(testmap);
					num++;
				}								
			}
			
			List<Map<String,String>> fillList = fillExamService.getFillQuestionRandom(subject);
			if(fillList != null && fillList.size() > 0) {
				
				for(int i=0; i<fillList.size();i++) {
					FillBlankExamStore fillExam = fillExamService.getNameByCode(fillList.get(i).get("question_code"));
					TestDataExamModel testmap = new TestDataExamModel();
					testmap.setNumber(""+num);
					testmap.setTable("fill_blank_exam_store");
					testmap.setQuestion_code(fillExam.getQuestionCode());
					testmap.setQuestion_name(fillExam.getQuestionName());
					if (fillExam.getResult()!=null){
						result=fillExam.getResult();
					}else {
						result=" ";
					}
					testmap.setResult(result);
					testmap.setType("1");					
					if(fillExam.getPassTableId() != null) {
						testmap.setImg(PhotoUtil.sqlTophoto(fillExam.getPassTableId()));
					}
					testmap.setSelect("");
					testmapList.add(testmap);
					num++;
				}						
			}
						
			List<Map<String,Object>> selectList = chooseSelectStoreService.getSelectQuestionRandom(subject);//选择题2
			
			for(int i=0; i<selectList.size();i++) {//去拿选出的选择题			
				TestDataExamModel testmap = new TestDataExamModel();
				testmap.setNumber(""+num);
				testmap.setTable("choose_select_store");
				testmap.setQuestion_code((String) selectList.get(i).get("question_code"));
				testmap.setQuestion_name((String) selectList.get(i).get("question_name"));
				if ((String) selectList.get(i).get("result")!=null){
					result=(String) selectList.get(i).get("result");
				}else {
					result=" ";
				}
				testmap.setResult(result);
				if((Integer)selectList.get(i).get("flag") == 0) {
					testmap.setType("2");
				}
				if((Integer)selectList.get(i).get("flag") == 1) {
					testmap.setType("3");
				}
				if(selectList.get(i).get("pass_table_id") != null) {
					testmap.setImg(PhotoUtil.sqlTophoto((String)selectList.get(i).get("pass_table_id")));
				}
				testmap.setSelect((String) selectList.get(i).get("select_a"));
				testmapList.add(testmap);
				num++;
			}
			List<Map<String,Object>> decideList =  decideStoreService.getDecideQuestionRandom(subject);//判断题1
			for(int i=0; i<decideList.size();i++) {//去拿选出的判断题			
				TestDataExamModel testmap = new TestDataExamModel();
				testmap.setNumber(""+num);
				testmap.setTable("decide_store");
				testmap.setQuestion_code((String) decideList.get(i).get("question_code"));
				testmap.setQuestion_name((String) decideList.get(i).get("question_name"));
				if ((String) decideList.get(i).get("result")!=null){
					result=(String) decideList.get(i).get("result");
				}else {
					result=" ";
				}
				testmap.setResult(result);
				testmap.setType("3");
				if(decideList.get(i).get("pass_table_id") != null) {
					testmap.setImg(PhotoUtil.sqlTophoto((String)decideList.get(i).get("pass_table_id")));
				}
				testmap.setSelect((String) decideList.get(i).get("select_a"));
				testmapList.add(testmap);
				num++;
			}
			map.put("testData", testmapList);
			map.put("testData", testmapList);
			map.put("examPhoto", "./识图用图/middle-map.png");
		}
		map1.put("data", map);
		map1.put("code", 500);
		Training train = new Training();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String time = df.format(new Date());

		Timestamp ts = Timestamp.valueOf(time);
		train.setIdNumber(user.getIdNumber());
		train.setTrainingId(uuid);
		train.setType(1);
		train.setStartTime(ts);
		trainService.add(train);
		return map1;
	}	
	
}
