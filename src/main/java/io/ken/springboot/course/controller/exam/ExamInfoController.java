package io.ken.springboot.course.controller.exam;

import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.bean.paper.PicturePaper;
import io.ken.springboot.course.dao.exam.ExamInfoMapper;
import io.ken.springboot.course.model.exam.CommExamModel;
import io.ken.springboot.course.model.exam.ExamInfoModel;
import io.ken.springboot.course.model.exam.HandExamInfoModel;
import io.ken.springboot.course.model.exam.HandExamModel;
import io.ken.springboot.course.model.exam.HandExamPaperModel;
import io.ken.springboot.course.model.exam.MobileExamModel;
import io.ken.springboot.course.model.exam.MobileInModel;
import io.ken.springboot.course.model.exam.MobileOutModel;
import io.ken.springboot.course.model.exam.SubjectInfo;
import io.ken.springboot.course.model.exam.model.ExamInfoNewModel;
import io.ken.springboot.course.model.exam.newExam.ExampaperModel;
import io.ken.springboot.course.model.exam.newExam.TestInnerData;
import io.ken.springboot.course.service.implement.ChooseSelectStoreServiceImpl;
import io.ken.springboot.course.service.implement.DecideStoreServiceImpl;
import io.ken.springboot.course.service.implement.ExamInfoServiceImpl;
import io.ken.springboot.course.service.implement.ExamServiceImpl;
import io.ken.springboot.course.service.implement.FillExamServiceImpl;
import io.ken.springboot.course.service.implement.HandExamServiceImpl;
import io.ken.springboot.course.service.implement.UserServiceImpl;
import io.ken.springboot.course.tools.PhotoUtil;
import io.ken.springboot.course.tools.PictureExamResult;
import io.ken.springboot.course.tools.Result;
import io.ken.springboot.course.tools.ResultSubject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 考试相关
 * @author GouYudong
 * @create 2019-11-22 14:35
 **/

@Controller
@RequestMapping("/exam")
public class ExamInfoController {

    @Autowired
    private ExamInfoServiceImpl examInfoServiceImpl;
    
    @Autowired
    private ExamServiceImpl examServiceImpl;

    @Autowired
    private FillExamServiceImpl fillExamServiceImpl;

    @Autowired
    private ChooseSelectStoreServiceImpl chooseExamServiceImpl;

    @Autowired
    private DecideStoreServiceImpl decideStoreServiceImpl;
    
    @Autowired
    private HandExamServiceImpl handExamServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ExamInfoMapper examInfoMapper;


    /**
     * 根据学号获取考试题
     * @param request
     * @param id_number:学号
     * @return
     * by:gyd
     */
    @ApiOperation(value ="获取考试题" ,notes = "获取考试题")
    @RequestMapping(value = "/getExamPaper2", method = RequestMethod.POST)
    @ResponseBody
    public Result getInfo1(HttpServletRequest request, String id_number,String subject) {

        Result result = new Result();
        List<Object> list = new ArrayList<Object>();
        List<Object> modelList = new ArrayList<Object>();
        User user1 = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户

        MobileExamModel mobileExamModel = new MobileExamModel();//手持机考试题
        HandExamInfoModel handExamInfoModel = new HandExamInfoModel();

        try {
            if (id_number == null  || id_number.equals("")) {
                result.setMsg("学号为空，请检查！");
                result.setCode(404);
                result.setData("");

            }else {

                Exam exam = examInfoServiceImpl.getCurrentExam(user1.getIdNumber(),subject);

                if (exam.getExamId() != null) {
                    //考试题不为空
                    //判断是否在考试时间之内
                    Timestamp startTime = exam.getStartTime();
                    Long examStart = startTime.getTime();
                    int duration = exam.getDuration()*60*1000;  //90分钟换算成毫秒
                    Long currentTime = System.currentTimeMillis();

                    long times = currentTime - examStart;  //现在时间-考试开始

//                    if ((currentTime - examStart) > duration) { //现在时间-考试开始 > 90，已经结束
//                        //超时，考试已经结束
//                        result.setMsg(user1.getName()+"你好，"+exam.getExamName()+"本场考试已经结束！");
//                        result.setCode(404);
//                        result.setSuccess(false);
//                        result.setData("");
//                        return  result;
//
//                    }else
                    if ((currentTime - examStart) < 0 ){
                        result.setMsg(user1.getName()+"你好，"+exam.getExamName()+"本场考试尚未开始！/"+times*(-1)/1000);
                        result.setCode(404);
                        result.setSuccess(false);
                        result.setData("");
                        return  result;

                    }
                    else {
                        //   0 < currentTime - examStart <= 90
                        //考试题不为空
                        int questionNum = 0;

                        ExamInfoModel examInfoModel = new ExamInfoModel();
                        examInfoModel.setExamId(exam.getExamId());
                        examInfoModel.setExamName(exam.getExamName());
                        examInfoModel.setIdNumber(id_number);
                        examInfoModel.setType("1");

                        String handQuestion = exam.getHandExamQuestion();
                        String fillQuestion = exam.getFillBlankExamQuestion();
                        String commQuestion = exam.getCommExamQuestion();

                        String []handArr;
                        String []fillArr;
                        String []commArr;

                        List<HandExamStore> handExamList = new ArrayList<HandExamStore>();
                        List<FillBlankExamStore> fillExamList = new ArrayList<FillBlankExamStore>();
                        List<CommExamStore> commExamList = new ArrayList<CommExamStore>();

                        List<HandExamStore> handExamInfoList = new ArrayList<HandExamStore>();
                        List<FillBlankExamStore> fillExamInfoList = new ArrayList<FillBlankExamStore>();
                        List<CommExamStore> commExaminfoList = new ArrayList<CommExamStore>();

                        HandExamModel handExamModel = new HandExamModel();

                        Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();
                        int num = 1;

                        // 1.1操作题
                        if (handQuestion == null || handQuestion.equals("")) {
                            //操作题为空
                            examInfoModel.setFlagData("");

                        } else {
                            handArr = handQuestion.split(";");

                            if (handArr.length > 0) {
                                num = handArr.length;
                                //outMap = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2
                                handExamInfoModel = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2

                                if (handExamInfoModel == null) {
                                    result.setData(examInfoModel);
                                    result.setMsg("获取失败，操作题题号为空，请重试");
                                    result.setCode(404);

                                    //return  result;

                                }else {
                                    if ( !handExamInfoModel.getHandSaveFlag()) {
                                        result.setData(examInfoModel);
                                        result.setMsg("获取操作题失败，请重试！");
                                        result.setCode(500);

                                        //return  result;

                                    } else {

                                        outMap = handExamInfoModel.getMap();
                                    }
                                }

                                if (handExamModel == null) {
                                    examInfoModel.setFlagData("");

                                } else {
                                    examInfoModel.setFlagData(outMap);
                                    result.setData(examInfoModel);
                                }

                            } else {
                                //操作题为空
                                examInfoModel.setFlagData("");
                            }
                        }

                        // scjData 1.2手持机相关
                        MobileOutModel mobileOutModel = new MobileOutModel();
                        Map<String, Object> mobileMap = new HashMap<String, Object>();
                        Map<String, MobileInModel> map = new HashMap<String, MobileInModel>();

                        Exam newExam = examServiceImpl.getModel(exam.getExamId());

                        //mobileOutModel = examInfoServiceImpl.getMobileExam(exam,3);//传入考试题和开始题号
                        mobileExamModel = examInfoServiceImpl.getMobileExam(newExam,num);//传入考试题和开始题号

                        //
                        if (mobileExamModel == null) {
                            result.setData(examInfoModel);
                            result.setMsg("获取失败，填空题题号为空，请重试");
                            result.setCode(404);
                            //return  result;
                        }else {
                            if (!mobileExamModel.getFillSaveFlag()) {
                                result.setData(examInfoModel);
                                result.setMsg("获取填空题失败，请重试！");
                                result.setCode(500);
                                //return  result;
                            } else {
                                map = mobileExamModel.getMap();
                            }
                        }

                        Exam moreNewExam = examServiceImpl.getModel(newExam.getExamId());

                        //填充手持机操作题
                        examInfoModel.setScjData(map);
                        result.setData(examInfoModel);

                        //testData  1.3操作题&填空题 ------显示所有的题
                        List<HandExamPaperModel> handFillExamList = new ArrayList<HandExamPaperModel>();
                        if (fillQuestion ==null ||  fillQuestion.equals("")){
                            //填空题为空

                        }else {
                            handFillExamList = examInfoServiceImpl.getHandFillExam(moreNewExam);

                        }

                        examInfoModel.setTestData(handFillExamList);
                        result.setData(examInfoModel);
                        fillArr = fillQuestion.split(";");

                        if (fillArr.length > 0) {
                            num += fillArr.length;
                        }


                        //mesData 1.4通讯题
                        List<CommExamModel> commExamModelList = new ArrayList<CommExamModel>();
                        if (commQuestion ==null  || commQuestion.equals("")){
                            //通讯题为空
                        }else {
                            commArr = commQuestion.split(";");
                            if (commArr.length > 0) {

                                commExamModelList = examInfoServiceImpl.getCommExam(moreNewExam, num);
                                //1.4.1 封装通讯题
                                examInfoModel.setMesData(commExamModelList);
                                result.setData(examInfoModel);
                                result.setMsg("true");
                                result.setCode(200);
                                exam.setStatus(1);

                                examServiceImpl.updateModel(moreNewExam);

                                //-------------
                            }

                        }

                        //更新分数，置零
                        String examinationId = moreNewExam.getExamId();
                        int gradeTag = examInfoServiceImpl.initGrade(examinationId);

                        if (gradeTag > 0) {
                            System.out.println(moreNewExam.getExamName() + "得分置为0成功");
                        } else {
                            System.out.println(moreNewExam.getExamName()+"得分置为0失败");
                        }
                    }

                    } else {
                    result.setMsg("该考生考试题为空");
                    result.setCode(404);
                    result.setData("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("后台错误");
            result.setCode(500);
            result.setData("");
        }
        return result;
    }


    //-----------------------------------新接口---------------------------------------

    /**
     * 获取考试题
     * @param request
     * @param id_number
     * @param subject
     * @return
     */
    @ApiOperation(value ="获取考试题(废弃接口)" ,notes = "获取考试题(废弃接口)")
    @RequestMapping(value = "/getExamPaperOverTime", method = RequestMethod.POST)
    @ResponseBody
    public Result getInfo3(HttpServletRequest request, String id_number,String subject) {

        Result result = new Result();
        List<Object> list = new ArrayList<Object>();
        List<Object> modelList = new ArrayList<Object>();
        User user1 = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户

        MobileExamModel mobileExamModel = new MobileExamModel();//手持机考试题
        HandExamInfoModel handExamInfoModel = new HandExamInfoModel();

        try {
            
                Exam exam = examInfoServiceImpl.getCurrentExam(user1.getIdNumber(),subject);

                if (exam.getExamId() != null) {
                    //考试题不为空
                    //判断是否在考试时间之内
                    Timestamp startTime = exam.getStartTime();
                    Long examStart = startTime.getTime();
                    int duration = exam.getDuration()*60*1000;  //90分钟换算成毫秒
                    Long currentTime = System.currentTimeMillis();

                    long times = currentTime - examStart;  //现在时间-考试开始

                    if ((currentTime - examStart) > duration) { //现在时间-考试开始 > 90，已经结束
                        //超时，考试已经结束
                        //将本场考试status置为 1
                        try {
                            int tag = 0;
                            tag = examInfoServiceImpl.expireExam(exam.getExamId());
                            if (tag == 1) {
                                System.out.println(exam.getExamName() + "过期成功");
                            }else{
                                System.out.println(exam.getExamName() + "过期失败");
                            }

                        }catch (Exception e){
                            e.printStackTrace();

                        }
                        result.setMsg(user1.getName()+"你好，"+exam.getExamName()+"本场考试已经结束！");
                        result.setCode(404);
                        result.setSuccess(false);
                        result.setData("");
                        return  result;

                    }else if ((currentTime - examStart) < 0 ){
                        result.setMsg(user1.getName()+"你好，"+exam.getExamName()+"本场考试尚未开始！/"+times*(-1)/1000);
                        result.setCode(404);
                        result.setSuccess(false);
                        result.setData("");
                        return  result;

                    }
                    else {
                        if(subject == null || "".equals(subject)) {
                            //科目为空
                            result.setMsg(user1.getName()+"你好，你的科目信息异常，请重试！");
                            result.setCode(404);
                            result.setSuccess(false);
                            result.setData("");

                        }else{
                            //科目不为空
                            if ("1".equals(subject)){
                                //1.北斗手持机考试
                                //   0 < currentTime - examStart <= 90
                                //考试题不为空
                                int questionNum = 0;
                                ExampaperModel examPaper = new ExampaperModel();//新试卷
                                examPaper.setExamId(exam.getExamId());
                                examPaper.setExamName(exam.getExamName());
                                examPaper.setIdNumber(user1.getIdNumber());
                                examPaper.setType(exam.getType());

                                ExamInfoNewModel examInfoModel = new ExamInfoNewModel();
                                examInfoModel.setExamId(exam.getExamId());
                                examInfoModel.setExamName(exam.getExamName());
                                examInfoModel.setType(String.valueOf(exam.getType()));
                                examInfoModel.setIdNumber(user1.getIdNumber());

                                /*;
                                examInfoModel.setExamName(exam.getExamName());
                                examInfoModel.setIdNumber(id_number);
                                examInfoModel.setType("1");*/

                                String handQuestion = exam.getHandExamQuestion();
                                String fillQuestion = exam.getFillBlankExamQuestion();
                                String commQuestion = exam.getCommExamQuestion();
                                String chooseQuestion = exam.getChooseExamQuestion();//选择题
                                String decideQuestion = exam.getDecideExamQuestion();//判断题

                                String []handArr = null;
                                String []fillArr;
                                String []commArr;
                                String []chooseArr;//选择
                                String []decideArr;//判断

                                List<HandExamStore> handExamList = new ArrayList<HandExamStore>();
                                List<FillBlankExamStore> fillExamList = new ArrayList<FillBlankExamStore>();
                                List<CommExamStore> commExamList = new ArrayList<CommExamStore>();

                                List<HandExamStore> handExamInfoList = new ArrayList<HandExamStore>();
                                List<FillBlankExamStore> fillExamInfoList = new ArrayList<FillBlankExamStore>();
                                List<CommExamStore> commExaminfoList = new ArrayList<CommExamStore>();

                                HandExamModel handExamModel = new HandExamModel();

                                Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();
                                int num = 1;

                                // 1.1操作题
                                if (handQuestion == null || handQuestion.equals("")) {
                                    //操作题为空,但是题不能为空，type=0
                                    handExamInfoModel = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2
                                    outMap = handExamInfoModel.getMap();
                                    examInfoModel.setFlagData(outMap);

                                } else {
                                    handArr = handQuestion.split(";");
                                    if (handArr.length > 0) {
                                        num = handArr.length;
                                        //outMap = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2
                                        handExamInfoModel = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2

                                        if (handExamInfoModel == null) {
                                            result.setData(examInfoModel);
                                            result.setMsg("获取失败，操作题题号为空，请重试");
                                            result.setCode(404);
                                            //return  result;
                                        }else {
                                            if ( !handExamInfoModel.getHandSaveFlag()) {
                                                result.setData(examInfoModel);
                                                result.setMsg("获取操作题失败，请重试！");
                                                result.setCode(500);
                                                //return  result;
                                            } else {
                                                outMap = handExamInfoModel.getMap();
                                            }
                                        }

                                        if (handExamModel == null) {
                                            examInfoModel.setFlagData("");

                                        } else {
                                            examInfoModel.setFlagData(outMap);
                                            result.setData(examInfoModel);
                                        }
                                    } else {
                                        //操作题为空
                                        examInfoModel.setFlagData("");
                                    }
                                }
                                // scjData 1.2手持机相关
                                MobileOutModel mobileOutModel = new MobileOutModel();
                                Map<String, Object> mobileMap = new HashMap<String, Object>();
                                Map<String, MobileInModel> map = new HashMap<String, MobileInModel>();
                                Exam newExam = examServiceImpl.getModel(exam.getExamId());
                                //mobileOutModel = examInfoServiceImpl.getMobileExam(exam,3);//传入考试题和开始题号
                                mobileExamModel = examInfoServiceImpl.getMobileExam(newExam,num);//传入考试题和开始题号
                                //
                                if (mobileExamModel == null) {
                                    result.setData(examInfoModel);
                                    result.setMsg("获取失败，填空题题号为空，请重试");
                                    result.setCode(404);
                                    //return  result;
                                }else {
                                    if (!mobileExamModel.getFillSaveFlag()) {
                                        result.setData(examInfoModel);
                                        result.setMsg("获取填空题失败，请重试！");
                                        result.setCode(500);
                                        //return  result;
                                    } else {
                                        map = mobileExamModel.getMap();
                                    }
                                }
                                Exam moreNewExam = examServiceImpl.getModel(newExam.getExamId());
                                //填充手持机操作题
                                examInfoModel.setScjData(map);
                                result.setData(examInfoModel);
                                //testData  1.3操作题&填空题 ------显示所有的题
                                //*************所有的考试题，按顺序排列************

                                // List<HandExamPaperModel> handFillExamList = new ArrayList<HandExamPaperModel>();
                                List<TestInnerData> dataList = new ArrayList<TestInnerData>();//存放Testdata内部的题节点

                                if (fillQuestion ==null ||  fillQuestion.equals("")){
                                    //填空题为空
                                }else {
                                    fillArr = fillQuestion.split(";");

                                    if (fillArr.length > 0) {
                                        num += fillArr.length;
                                    }
                                }
                                dataList = examInfoServiceImpl.getDataList(moreNewExam);
                                examInfoModel.setTestData(dataList);
                                result.setData(examInfoModel);
                                result.setCode(200);
                                result.setSuccess(true);
                                result.setMsg("查询成功");

                                //更新分数，置零
                                //考题置为已考：1
                                String examinationId = moreNewExam.getExamId();
                                int gradeTag = examInfoServiceImpl.initGrade(examinationId);

                                if (gradeTag > 0) {
                                    System.out.println(moreNewExam.getExamName() + "得分置为0成功");
                                } else {
                                    System.out.println(moreNewExam.getExamName()+"得分置为0失败");
                                }

                            }else {
                                //2.其他科目
                                int questionNum = 0;
                                //if ("6".equals(subject)) {

                                    //返回前端的数据
                                    PictureExamResult pictureExamResult = new PictureExamResult();

                                    Map<String, Object> dataMap = new HashMap<String, Object>();

                                    List<PicturePaper> pictureQuestionList = new LinkedList();

                                    //题号
                                    String handQuestionCodes = exam.getHandExamQuestion();
                                    String fillQuestionCodes = exam.getFillBlankExamQuestion();//222,345,6756,
                                    String chooseQuestionCodes = exam.getChooseExamQuestion();
                                    String decideQuestionCodes = exam.getDecideExamQuestion();

                                    //题号数组
                                    String[]handCodeArr =null;
                                    String[]fillCodeArr =null;
                                    String[]chooseCodeArr =null;
                                    String[]decideCodeArr =null;

                                    //3.1 操作题
                                    //3.2 填空题（存在范围问题）
                                    try {
                                    	if (handQuestionCodes != null || !("".equals(handQuestionCodes))) {
                                    		String[] handArr = handQuestionCodes.split(";");                                                                                    
                                            for (String arr : handArr) {
                                            	    questionNum++;
                                                    HandExamStore store = handExamServiceImpl.getNameByCode(arr);
                                                    //找填空题
                                                    PicturePaper picturePaper = new PicturePaper();
                                                    picturePaper.setNumber(questionNum);
                                                    picturePaper.setTable("hand_exam_store");
                                                    picturePaper.setQuestion_code(arr);
                                                    picturePaper.setType("0");
                                                    picturePaper.setQuestion_name(store.getQuestionName());
                                                    picturePaper.setSelect("");
                                                    picturePaper.setResult(store.getResult());
                                                    if(store.getResult() == null) {
                                                    	 picturePaper.setResult("");
                                                    }
                                                    if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                                        //picturePaper.setImg("");
                                                    }else{
                                                        picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                                    }
                                                   pictureQuestionList.add(picturePaper);
                                                                                                                                   
                                    	}
                                    	                                                                                                                      
                                        if (fillQuestionCodes == null || "".equals(fillQuestionCodes)) {
                                            //填空题为空
                                        }else {
                                            //4.1取出题号
                                            fillCodeArr = fillQuestionCodes.split(";");
                                            List<FillBlankExamStore> fillBlankExamStoreList = new ArrayList<FillBlankExamStore>();
                                            fillBlankExamStoreList = fillExamServiceImpl.getAll();

                                            for (String fillCode : fillCodeArr) {
                                                questionNum++;
                                                for (FillBlankExamStore fillBlankExamStore : fillBlankExamStoreList) {
                                                    if (fillCode.equals(fillBlankExamStore.getQuestionCode())) {
                                                        //找填空题
                                                        PicturePaper picturePaper = new PicturePaper();
                                                        picturePaper.setNumber(questionNum);
                                                        picturePaper.setTable("fill_blank_exam_store");
                                                        picturePaper.setQuestion_code(fillCode);
                                                        picturePaper.setType("1");
                                                        picturePaper.setQuestion_name(fillBlankExamStore.getQuestionName());
                                                        picturePaper.setSelect("");
                                                        picturePaper.setResult(fillBlankExamStore.getResult());
                                                        if(fillBlankExamStore.getResult() == null) {
                                                       	    picturePaper.setResult("");
                                                        }
                                                        if (fillBlankExamStore.getPassTableId() == null || "".equals(fillBlankExamStore.getPassTableId())) {
                                                            //picturePaper.setImg("");
                                                        }else{
                                                            picturePaper.setImg(PhotoUtil.sqlTophoto(fillBlankExamStore.getPassTableId()));
                                                        }


                                                        pictureQuestionList.add(picturePaper);

                                                    }
                                                }

                                            }
                                        }
                                    	}
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    try {
                                        //3.3 选择题
                                        //注意区分单选和多选
                                        if (chooseQuestionCodes == null || "".equals(chooseQuestionCodes)) {

                                        }else {
                                            chooseCodeArr = chooseQuestionCodes.split(";");
                                            List<ChooseSelectStore> chooseSelectStoreList = new ArrayList<ChooseSelectStore>();
                                           
                                            String chooseResult = "";
                                            String[] chooseResultArr = null;
                                            for (String chooseCode : chooseCodeArr) {
                                                questionNum++;
                                                        ChooseSelectStore store = chooseExamServiceImpl.getNameByCode(chooseCode);
                                                        //找到填空题
                                                        PicturePaper picturePaper = new PicturePaper();
                                                        picturePaper.setNumber(questionNum);
                                                        picturePaper.setTable("choose_select_store");
                                                        picturePaper.setQuestion_code(chooseCode);
                                                        if (store.getFlag() == 0) {
                                                            //单选
                                                            picturePaper.setType("2");
                                                            picturePaper.setQuestion_name(store.getQuestionName()+"(单选)");
                                                        }else {
                                                            //多选
                                                            picturePaper.setType("3");
                                                            picturePaper.setQuestion_name(store.getQuestionName()+"(多选)");
                                                        }


                                                        picturePaper.setSelect(store.getSelectA());  //   “A.111;B.222;C.333;D.444;E.555;F.666”
                                                        picturePaper.setResult(store.getResult());
                                                        if(store.getResult() == null) {
                                                       	    picturePaper.setResult("");
                                                        }
                                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                                            //picturePaper.setImg("");
                                                        }else {
                                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                                        }
                                                        pictureQuestionList.add(picturePaper);
                                                   
                                            }
                                        }

                                        if (decideQuestionCodes == null || "".equals(decideQuestionCodes)) {

                                        }else {
                                        	decideCodeArr = decideQuestionCodes.split(";");
                                            
                                            for (String arr : decideCodeArr) {
                                                        questionNum++;
                                                        DecideStore store = decideStoreServiceImpl.getNameByCode(arr);
                                                        //找到填空题
                                                        PicturePaper picturePaper = new PicturePaper();
                                                        picturePaper.setNumber(questionNum);
                                                        picturePaper.setTable("decide_store");
                                                        picturePaper.setQuestion_code(arr);
                                                        picturePaper.setType("2");
                                                        picturePaper.setQuestion_name(store.getQuestionName()+"(判断)");
                                                        
                                                        picturePaper.setSelect(store.getSelectA());  //   “A.111;B.222;C.333;D.444;E.555;F.666”
                                                        picturePaper.setResult(store.getResult());
                                                        if(store.getResult() == null) {
                                                       	    picturePaper.setResult("");
                                                        }
                                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                                            //picturePaper.setImg("");
                                                        }else {
                                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                                        }
                                                        pictureQuestionList.add(picturePaper);                                                    
                                               
                                            }
                                        }
                                        
                                    }catch (Exception e){
                                        e.printStackTrace();

                                    }

                                    //更新分数，置零
                                    //考题置为已考：1
                                    String examinationId = exam.getExamId();
                                    int gradeTag = examInfoServiceImpl.initGrade(examinationId);

                                    if (gradeTag > 0) {
                                        System.out.println(exam.getExamName() + "得分置为0成功");
                                    } else {
                                        System.out.println(exam.getExamName()+"得分置为0失败");
                                    }

                                    //封装数据
                                    dataMap.put("examId", exam.getExamId());
                                    dataMap.put("testData", pictureQuestionList);
                                    dataMap.put("examPhoto", "./识图用图/map.png");
                                    result.setCode(200);
                                    result.setData(dataMap);
                                    result.setMsg("获取成功");
                                    result.setSuccess(true);


                                /*}else {
                                    result.setCode(404);
                                    result.setSuccess(false);
                                    result.setMsg("当前科目尚未开发完成，请等待！");
                                }*/
                            }
                        }
                    }

                } else {
                    result.setMsg("该考生考试题为空");
                    result.setCode(404);
                    result.setData("");
                    result.setSuccess(false);
                }
            
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("服务器出错，请重试");
            result.setCode(500);
            result.setData("");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 获取考核项目---底部有考试的科目显示
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value ="获取考核项目" ,notes = "获取考核项目")
    @RequestMapping(value = "/needTest", method = RequestMethod.GET)
    @ResponseBody
    public ResultSubject getInfo2(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户
        String idNumber = user.getIdNumber();
        String subject = "";

        Result result = new Result();

        ResultSubject resultSubject = new ResultSubject();

        Map<String, List<SubjectInfo>> map = new HashMap<String, List<SubjectInfo>>();

        List<SubjectInfo> list = new ArrayList<SubjectInfo>();

        SubjectInfo subjectInfoBD = new SubjectInfo();
        SubjectInfo subjectInfoRatio = new SubjectInfo();
        SubjectInfo subjectInfoTheory = new SubjectInfo();
        SubjectInfo subjectInfoFly = new SubjectInfo();
        SubjectInfo subjectInfoBattle = new SubjectInfo();
        SubjectInfo subjectInfoPicture = new SubjectInfo();

        //1-北斗手持机；2-电台操作；3-理论知识；4-航片判读；5-作战计算；6-识图用图
        String[] subjectArr = {"1", "2", "3", "4", "5","6"};

        for (String subjectNum : subjectArr) {

            List<Exam> examlist = examInfoServiceImpl.getCurrentExams(idNumber,subjectNum);

            //1北斗手持机
            if (subjectNum.equals("1")) {
                if (examlist == null) {
                    subjectInfoBD.setIsTest("false");
                }else {
                    if (examlist.size() == 0) {
                        subjectInfoBD.setIsTest("false");
                    } else {
                        subjectInfoBD.setIsTest("true");
                    }
                }
            }

            //2电台操作
            if (subjectNum.equals("2")) {
                if (examlist == null) {
                    subjectInfoRatio.setIsTest("false");
                }else {
                    if (examlist.size() == 0) {
                        subjectInfoRatio.setIsTest("false");
                    } else {
                        subjectInfoRatio.setIsTest("true");
                    }
                }
            }

            //3理论知识
            if (subjectNum.equals("3")) {
                if (examlist == null) {
                    subjectInfoTheory.setIsTest("false");
                }else {
                    if (examlist.size() == 0) {
                        subjectInfoTheory.setIsTest("false");
                    } else {
                        subjectInfoTheory.setIsTest("true");
                    }
                }
            }

            //4航片判读
            if (subjectNum.equals("4")) {
                if (examlist == null) {
                    subjectInfoFly.setIsTest("false");
                }else {
                    if (examlist.size() == 0) {
                        subjectInfoFly.setIsTest("false");
                    } else {
                        subjectInfoFly.setIsTest("true");
                    }
                }
            }

            //5作战计算
            if (subjectNum.equals("5")) {
                if (examlist == null) {
                    subjectInfoBattle.setIsTest("false");
                }else {
                    if (examlist.size() == 0) {
                        subjectInfoBattle.setIsTest("false");
                    } else {
                        subjectInfoBattle.setIsTest("true");
                    }
                }
            }

            //6-识图用图
            //5作战计算
            if (subjectNum.equals("6")) {
                if (examlist == null) {
                    subjectInfoPicture.setIsTest("false");
                }else {
                    if (examlist.size() == 0) {
                        subjectInfoPicture.setIsTest("false");
                    } else {
                        subjectInfoPicture.setIsTest("true");
                    }
                }
            }

        }

        subjectInfoBD.setImgSrc("./img/test/bdscj.png");
        subjectInfoBD.setImgActiveSrc("./img/test/bdscj-active.png");
        subjectInfoBD.setText("北斗手持机");


        subjectInfoRatio.setImgSrc("./img/test/dtcz.png");
        subjectInfoRatio.setImgActiveSrc("./img/test/dtcz-active.png");
        subjectInfoRatio.setText("电台操作");

        subjectInfoTheory.setImgSrc("./img/test/llzs.png");
        subjectInfoTheory.setImgActiveSrc("./img/test/llzs-active.png");
        subjectInfoTheory.setText("理论知识");

        subjectInfoFly.setImgSrc("./img/test/hppd.png");
        subjectInfoFly.setImgActiveSrc("./img/test/hppd-active.png");
        subjectInfoFly.setText("航片判读");

        subjectInfoBattle.setImgSrc("./img/test/zzjs.png");
        subjectInfoBattle.setImgActiveSrc("./img/test/zzjs-active.png");
        subjectInfoBattle.setText("作战计算");

        subjectInfoPicture.setImgSrc("./img/test/styt.png");
        subjectInfoPicture.setImgActiveSrc("./img/test/styt-active.png");
        subjectInfoPicture.setText("识图用图");

        list.add(subjectInfoBD);
        list.add(subjectInfoRatio);
        list.add(subjectInfoTheory);
        list.add(subjectInfoFly);
        list.add(subjectInfoBattle);
        list.add(subjectInfoPicture);

        map.put("data", list);

        resultSubject.setData(map);
        resultSubject.setCode("200");
        resultSubject.setMsg("success");


        return  resultSubject;

    }

    @ApiOperation(value ="考生交卷后获取分数" ,notes = "考生交卷后获取分数")
    @RequestMapping(value = "/getGrade", method = RequestMethod.POST)
    @ResponseBody
    public Result getInfo6(HttpServletRequest request,String examId ) {

        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginInfo");

        String userName = "";

        if (user != null) {
            userName = user.getName();
        }


        if (examId == null || examId.equals("")) {
            result.setCode(404);
            result.setSuccess(false);
            result.setMsg("考卷编号为空！");

        } else {
            try {
                String grade = "";
                Exam exam = examServiceImpl.getModel(examId);


                if (exam != null) {
                    grade = exam.getGrade();
                    if (grade == null || grade.equals("")) {
                        result.setCode(404);
                        result.setSuccess(false);
                        result.setMsg("分数计算中发生异常，请联系管理员！");
                        result.setData("");

                    } else {
                        result.setCode(200);
                        result.setSuccess(true);

                        result.setMsg("考试结束，"+userName+"本场考试分数："+grade);
                        result.setData(grade);
                    }


                } else {
                    result.setCode(404);
                    result.setSuccess(false);
                    result.setMsg("考生提交试卷过程中发生异常，请联系管理员！");
                    result.setData("");
                }
            } catch (Exception e) {

                e.printStackTrace();

                result.setCode(500);
                result.setSuccess(false);
                result.setMsg("查询发生异常，请联系管理员！");
                result.setData("");

            }
        }

        return result;

    }

    /**
     * 学员临时考试获取试卷
     * 暂时不用
     * @param request
     * @param subject  考试类型
     *  @param idNumber  学号
     * @return
     */
    @ApiOperation(value ="学员获取试卷(暂时不用)" ,notes = "学员获取试卷(暂时不用)")
    @RequestMapping(value = "/getExamPaperBefore", method = RequestMethod.POST)
    @ResponseBody
    public Result getInfo7(HttpServletRequest request,String subject ,String idNumber,String level) {

        Result result = new Result();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginInfo");
        idNumber = user.getIdNumber();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());

        //从数据库查出当前学员的信息
        User userInfo = userServiceImpl.getUserInfo(idNumber);
        user.setState(userInfo.getState());
        if (userInfo != null) {
            //新学员
            if (userInfo.getExamNames() != null) {
                user.setExamNames(userInfo.getExamNames());
                user.setFinishedExam(userInfo.getFinishedExam());
            }


        }

        try {
            //0.判断学员是否已有或者已考本场考试
            String examNames = "";
            String finishedExamNames = "";

            //学员的待考科目
            examNames = user.getExamNames();

            //已考
            finishedExamNames = user.getFinishedExam();

            String[] examNameArr = null;
            String[] finishedExamArr = null;

            List<String> examNameList = new ArrayList<String>();//待考
            List<String> finishedExamNameList = new ArrayList<>();//已考

            if (examNames != null) {
                examNameArr = examNames.split(",");
                for (String s : examNameArr) {
                    examNameList.add(s);
                }
            }

            if (finishedExamNames != null) {
                finishedExamArr = finishedExamNames.split(",");
                for (String s : finishedExamArr) {
                    finishedExamNameList.add(s);
                }
            }



            // 1、判断是否是新用户
            if ("1".equals(user.getState())){
                String makeExamMsg = "";
                //1.1新用户
//                if ("1".equals(subject) || "2".equals(subject) || "6".equals(subject)){
//
//                    //2. 所有科目都可以出题
//                    //3.1 北斗，电台，识图
//                    // 3.1.1 找到距离现在最近的考试，随机出一份给新学员
//                    //已开考&为开考
//                    List<Exam> examArrayList = new ArrayList<>();
//                    examArrayList= examInfoServiceImpl.getRecentExam(subject,nowTime);
//
//                    if (examArrayList.size() > 0){
//
//                        //3.1.2 判断学员是否已考当前考试
//                        //存在考试,给该考生出题
//                        for (Exam exam : examArrayList) {
//
//                                //判断是否已考
//                                if ( !finishedExamNameList.contains(exam.getExamName())) {
//
//                                    //用户已考科目不存在当前考试
//                                    //makeExamMsg += examInfoServiceImpl.saveCertainExam(user,session, exam);
//
//                                    //判断是否待考
//                                    if ( !examNameList.contains(exam.getExamName())) {
//                                        //用户待考科目不存在当前考试
//                                        makeExamMsg += examInfoServiceImpl.saveCertainExam(user,session, exam);
//                                    }
//
//                                }
//
//                        }
//
//                        result.setMsg(makeExamMsg);
//                        result.setCode(200);
//                        result.setSuccess(false);
//                        result.setData("");
//
//
//                    }else {
//                        //不存在考试
//                        result.setMsg("暂无考试，请稍后重试！");
//                        result.setCode(200);
//                        result.setSuccess(false);
//                        result.setData("");
//                    }
//
//                }else {
                //注：现在所有科目都是自动出卷，所以放弃上面的代码
                    // 需要调用出题接口
                    //3.2 理论，航片，作战（改为所有科目）

                if ( "3".equals(subject) ){
                    //理论  需要level
                    //查到距离现在最近的考试---已考和未考
                    List<Exam> examList = new ArrayList<>();
                    examList  = examInfoServiceImpl.getRecentExam(subject,nowTime,level);

                    if (examList.size() > 0) {
                        for (Exam exam : examList) {

                            //判断是否已考
                            if ( !finishedExamNameList.contains(exam.getExamName())) {

                                //用户已考科目不存在当前考试
                                //makeExamMsg += examInfoServiceImpl.saveCertainExam(user,session, exam);

                                //判断是否待考
                                if ( !examNameList.contains(exam.getExamName())) {
                                    //用户待考科目不存在当前考试
                                    //makeExamMsg += examInfoServiceImpl.saveRandomExam(user, session, idNumber, subject, exam);
                                }
                            }

                        }

                        result.setMsg(makeExamMsg);
                        result.setCode(200);
                        result.setSuccess(true);
                        result.setData("");


                    }
                    else {
                        result.setMsg(user.getName()+",暂无考试");
                        result.setCode(200);
                        result.setSuccess(false);
                        result.setData("");
                    }

                }else {
                    //其他不需要level=0
                    //查到距离现在最近的考试---已考和未考
                    List<Exam> examList = new ArrayList<>();
                    examList  = examInfoServiceImpl.getRecentExam(subject,nowTime,level);

                    if (examList.size() > 0) {
                        for (Exam exam : examList) {

                            //判断是否已考
                            if ( !finishedExamNameList.contains(exam.getExamName())) {

                                //用户已考科目不存在当前考试
                                //makeExamMsg += examInfoServiceImpl.saveCertainExam(user,session, exam);

                                //判断是否待考
                                if ( !examNameList.contains(exam.getExamName())) {
                                    //用户待考科目不存在当前考试
                                    makeExamMsg += examInfoServiceImpl.saveRandomExam(user, session, idNumber, subject, exam,level);
                                }
                            }

                        }

                        result.setMsg(makeExamMsg);
                        result.setCode(200);
                        result.setSuccess(true);
                        result.setData("");


                    }
                    else {
                        result.setMsg(user.getName()+",暂无考试");
                        result.setCode(200);
                        result.setSuccess(false);
                        result.setData("");
                    }

                }






//                }

            }


            //2.1 新老用户,直接获取试题
            //result = examInfoServiceImpl.getExamPaper(request,user,idNumber,subject);




        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("服务器出错，请重试");
            result.setCode(500);
            result.setData("");
            result.setSuccess(false);
        }



        return result;

    }



    /**
     * 学员临时考试获取试卷
     * 新老学员都可以考试
     * 一门考试可以重复考
     * @param request
     * @param subject  考试类型
     *  @param idNumber  学号
     * @return
     */
    @ApiOperation(value ="学员获取试卷(新老学员均可)" ,notes = "学员获取试卷(新老学员均可)")
    @RequestMapping(value = "/getExamPaper", method = RequestMethod.POST)
    @ResponseBody
    public Result getInfo8(HttpServletRequest request,String subject ,String idNumber,String level) {

        Result result = new Result();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginInfo");
        idNumber = user.getIdNumber();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());

        //从数据库查出当前学员的信息
        User userInfo = userServiceImpl.getUserInfo(idNumber);

        user.setState(userInfo.getState());

        if (userInfo != null) {
            //新学员
            if (userInfo.getExamNames() != null) {
                user.setExamNames(userInfo.getExamNames());
                user.setFinishedExam(userInfo.getFinishedExam());
            }
        }

        try {

            //1.获取当前科目的原生题
            List<Exam> examList = new ArrayList<>();

            if ("3".equals(subject)) {
                //理论考核
                examList = examInfoMapper.selectTheroyExamBySub(level);

            }else{
                //非理论考试
                examList = examInfoMapper.selectExamBySub(subject);
            }


            Exam rowExam = new Exam();
            Exam okExam = new Exam();

            String makeExamMsg = "";

            if (examList.size() > 0) {

                rowExam = examList.get(0);

                //后端暂时加上
                if ("".equals(level) || level == null) {
                    level = "0";
                }

                if (!"3".equals(subject)) {
                    rowExam.setLevel(level);
                }else {
                    rowExam.setLevel("0");
                }
                //后端暂时加上

                //获取到的原生试卷加上唯一id
                rowExam.setExamId(UUID.randomUUID().toString().replace("-", ""));


                //2.给该考生出题并且保存
                okExam= examInfoServiceImpl.saveRandomExam(user, session, idNumber, subject, rowExam,level);

                //3.获取试卷
                result = examInfoServiceImpl.getExamPaper(request, user, idNumber, subject,level,okExam.getExamId());

            }else {
                //
                System.out.println("该科目原生题未录入");
                result.setMsg("该科目原生题未录入");
                result.setCode(404);
                result.setData("");
                result.setSuccess(false);
            }




        }catch (Exception e){

            e.printStackTrace();

            result.setMsg("请重试");
            result.setCode(500);
            result.setData("");
            result.setSuccess(false);
        }



        return result;

    }



}
