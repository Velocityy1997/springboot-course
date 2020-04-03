package io.ken.springboot.course.controller.train;

import io.ken.springboot.course.model.CollectModel;
import io.ken.springboot.course.model.TrainModel;
import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.Training;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.service.IChooseSelectStoreService;
import io.ken.springboot.course.service.ICollectService;
import io.ken.springboot.course.service.ICommExamService;
import io.ken.springboot.course.service.IDecideStoreService;
import io.ken.springboot.course.service.IFillExamService;
import io.ken.springboot.course.service.IHandExamService;
import io.ken.springboot.course.service.implement.HandExamServiceImpl;
import io.ken.springboot.course.service.implement.TrainServiceImpl;
import io.ken.springboot.course.tools.RestResponse;
import io.ken.springboot.course.tools.Result2;
import io.ken.springboot.course.tools.TypeUtil;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GouYudong
 * @create 2019-11-19 15:49
 **/

@Controller
@RequestMapping("/train")
public class TrainController {

    @Resource
    private TrainServiceImpl trainServiceImpl;

    @Autowired
	private ICollectService collectService;
    
    @Autowired
	private IHandExamService handService;
	@Autowired
	private IFillExamService  fillExamService;
	@Autowired
	private ICommExamService commExamService;
	@Autowired
	private IChooseSelectStoreService chooseSelectStoreService;
	@Autowired
	private IDecideStoreService decideStoreService;
    
    /**
     * 我的练习
     * 学员证件号
     * @param request
     * @param id_number：学员证件号
     * @return
     */
    @ApiOperation(value="获取我的练习",notes="获取我的练习")
    @RequestMapping(value = "/getTrain", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse getInfo1(HttpServletRequest request,String id_number) {

        RestResponse result = new RestResponse();
        List<Training> beanList = new ArrayList<Training>();
        List<TrainModel> modelList = new ArrayList<TrainModel>();
        try {

            if (id_number == null) {
                //学号为空
                result.setMsg("学号为空");
                result.setCode("404");
                result.setRows("");

                return  result;

            }else {
                beanList = trainServiceImpl.selectByUserId(id_number);

                if (beanList .size() > 0) {
                    //1.封装结果集
                    for (Training training : beanList) {
                        TrainModel model = new TrainModel();
                        if (training.getTrainingId() !=null){

                            model.setTrainingId(training.getTrainingId());
                            model.setObject("北斗手持机操作");
                           
                            if (training.getStartTime() !=null){
                                if (training.getStartTime().toString().length() > 16) {
                                    model.setTime(training.getStartTime().toString().substring(0, 16));
                                } else {
                                    model.setTime("暂无数据");
                                }


                            }else {
                                model.setTime("暂无数据");
                            }
                            //if ()  答题数
                            //model.setNumber(String.valueOf(sumQuestion(training)));
                            model.setNumber("8");
                        }

                        modelList.add(model);

                    }

                }else {
                    result.setMsg("该学生无练习数据");
                    result.setCode("404");
                    result.setRows("");

                }


                result.setMsg("查询成功");
                result.setCode("200");
                result.setRows(modelList);
            }



        } catch (Exception e) {

            e.printStackTrace();
            result.setMsg("服务器内部异常");
            result.setCode("500");
            result.setRows("");

        }

        return result;

    }

    
    /**
	 * 我的错题集接口
	 * @param id_number
	 * @return
	 */
	
	@RequestMapping("/getError")
    @ResponseBody
	public Result2 getErrors(HttpServletRequest request ) {
		 
		Result2 result2 = new Result2();
		
		List<CollectModel> modeList1 = new ArrayList<CollectModel>();
		User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户
		
		try {
			//操作题题号
			String handCollect = user.getErrhandCollectList();
			if(handCollect != null && !(handCollect.equals(""))) {
				String handCollectList []= handCollect.split(";");
				for(int i = 0; i < handCollectList.length; i++) {
						CollectModel t1 = new CollectModel();
						String handNum = handCollectList[i];
						HandExamStore ss = handService.getNameByCode(handNum);
						t1.setId(handNum);
						t1.setObject(ss.getQuestionName());
						t1.setTable_name("hand_exam_store");
						String type = TypeUtil.getSubject1(ss.getSubject());
						t1.setType(type);
						t1.setOperation("查看试卷");
						modeList1.add(t1);
				}
			}
			String fillCollect = user.getErrfillBlankCollectList();
			if(fillCollect != null && !(fillCollect.equals(""))) {
				String fillBlankCollectList [] = fillCollect.split(";");
				for (int i = 0; i < fillBlankCollectList.length; i++) {
						CollectModel t2 = new CollectModel();
						String fillNum = fillBlankCollectList[i];
						FillBlankExamStore fill = fillExamService.getNameByCode(fillNum);
						t2.setId(fillNum);
						t2.setObject(fill.getQuestionName());
						String type = TypeUtil.getSubject1(fill.getSubject());
						t2.setType(type);
						t2.setTable_name("fill_blank_exam_store");
						t2.setOperation("查看试卷");
						modeList1.add(t2);
				}
			}
			//填空题题号
			
			//通信题题号
			String commCollect = user.getErrcommCollectList();
			if(commCollect != null && !(commCollect.equals(""))) {
				String commCollectList [] = commCollect.split(";");
				for (int i = 0; i < commCollectList.length; i++) {
						CollectModel t3 = new CollectModel();
						String commNum = commCollectList[i];
						CommExamStore comm = commExamService.getNameByCode(commNum);						
						t3.setId(commNum);
						t3.setObject(comm.getQuestionName());
						t3.setTable_name("comm_exam_store");
						String type = TypeUtil.getSubject1(comm.getSubject());
						t3.setType(type);
						t3.setOperation("查看试卷");
						modeList1.add(t3);
				}
			}	
			
			//通信题题号
			String chooseCollect = user.getErrchooseCollectList();
			if(chooseCollect != null && !(chooseCollect.equals(""))) {
				String chooseCollectList [] = chooseCollect.split(";");
				for (int i = 0; i < chooseCollectList.length; i++) {
						CollectModel t3 = new CollectModel();
						String commNum = chooseCollectList[i];
						ChooseSelectStore choose = chooseSelectStoreService.getNameByCode(commNum);						
						t3.setId(commNum);
						t3.setObject(choose.getQuestionName());
						t3.setTable_name("choose_select_store");
						String type = TypeUtil.getSubject1(choose.getSubject());
						t3.setType(type);
						t3.setOperation("查看试卷");
						modeList1.add(t3);
				}
			}	
			
			//通信题题号
			String decideCollect = user.getErrdecideBlankCollectList();
			if(decideCollect != null && !(decideCollect.equals(""))) {
				String decideCollectList [] = decideCollect.split(";");
				for (int i = 0; i < decideCollectList.length; i++) {
						CollectModel t3 = new CollectModel();
						String commNum = decideCollectList[i];
						DecideStore comm = decideStoreService.getNameByCode(commNum);						
						t3.setId(commNum);
						t3.setObject(comm.getQuestionName());
						t3.setTable_name("decide_store");
						String type = TypeUtil.getSubject1(comm.getSubject());
						t3.setType(type);
						t3.setOperation("查看试卷");
						modeList1.add(t3);
				}
			}	
			result2.setRows(modeList1);
			result2.setCode(200);
			result2.setMsg("msg");									
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		 	 result2.setRows(null);
			 result2.setCode(404);
			 result2.setMsg("msg");
		}										
		return result2;
		
	}
    
    
    /**
     * 获取错题集
     * @param request
     * @param id_number
     * @return
     */
	/*
	 * @ApiOperation(value="获取错题集",notes="获取错题集")
	 * 
	 * @RequestMapping(value = "/getWrong", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public RestResponse getInfo2(HttpServletRequest request,String
	 * id_number) {
	 * 
	 * RestResponse result = new RestResponse(); List<Training> beanList = new
	 * ArrayList<Training>(); List<TrainModel> modelList = new
	 * ArrayList<TrainModel>(); try {
	 * 
	 * if (id_number == null) { //学号为空 result.setMsg("学号为空"); result.setCode("404");
	 * result.setRows("");
	 * 
	 * return result;
	 * 
	 * }else { beanList = trainServiceImpl.selectByUserId(id_number);
	 * 
	 * if (beanList .size() > 0) { //1.封装结果集 for (Training training : beanList) {
	 * TrainModel model = new TrainModel(); if (training.getTrainingId() !=null){
	 * 
	 * model.setTrainingId(training.getTrainingId()); model.setObject("北斗手持机操作");
	 * 
	 * if (training.getStartTime() !=null){ if
	 * (training.getStartTime().toString().length() > 16) {
	 * model.setTime(training.getStartTime().toString().substring(0, 16)); } else {
	 * model.setTime("暂无数据"); }
	 * 
	 * 
	 * }else { model.setTime("暂无数据"); } //if () 答题数
	 * model.setNumber(String.valueOf(sumQuestion(training))); }
	 * 
	 * modelList.add(model);
	 * 
	 * }
	 * 
	 * }else { result.setMsg("该学生无练习数据"); result.setCode("404"); result.setRows("");
	 * 
	 * }
	 * 
	 * 
	 * result.setMsg("查询成功"); result.setCode("200"); result.setRows(modelList); }
	 * 
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); result.setMsg("服务器内部异常"); result.setCode("500");
	 * result.setRows("");
	 * 
	 * }
	 * 
	 * return result;
	 * 
	 * }
	 */


    /**
     * 统计每个练习中3种题型的总数
     * @param training
     * @return sum
     */
    public  Integer sumQuestion(Training training) {

        int sum = 0;

        String handQuestion = null;
        String fillQuestion = null;
        String commQuestion = null;

        int handSum = 0;
        int fillSum = 0;
        int commSum = 0;

        handQuestion = training.getHandTrainingQuestion();
        fillQuestion = training.getFillBlankTrainingQuestion();
        commQuestion = training.getCommTrainingQuestion();

        if (handQuestion != null) {
            String[] handArr = handQuestion.split(";");
            if (handArr.length > 0) {
                handSum += handArr.length;
            }
        }

        if (fillQuestion != null) {
            String[] fillArr = fillQuestion.split(";");
            if (fillArr.length > 0) {
                fillSum += fillArr.length;
            }
        }

        if (commQuestion != null) {
            String[] commArr = commQuestion.split(";");
            if (commArr.length > 0) {
                commSum += commArr.length;
            }
        }

        sum = handSum + fillSum + commSum;

        return sum;
    }


}


