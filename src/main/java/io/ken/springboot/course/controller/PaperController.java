package io.ken.springboot.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.bean.Subject;
import io.ken.springboot.course.bean.UserLevelDic;
import io.ken.springboot.course.common.utils.Base64Utils;
import io.ken.springboot.course.common.utils.ExcelUtils;
import io.ken.springboot.course.common.utils.MultipartFileToFile;
import io.ken.springboot.course.common.utils.RandomNumUtils;
import io.ken.springboot.course.common.utils.UUIDUtils;
import io.ken.springboot.course.model.ExcelModel;
import io.ken.springboot.course.model.QuestionModel;
import io.ken.springboot.course.service.IChooseSelectStoreService;
import io.ken.springboot.course.service.ICommExamService;
import io.ken.springboot.course.service.IDecideStoreService;
import io.ken.springboot.course.service.IExamService;
import io.ken.springboot.course.service.IFillExamService;
import io.ken.springboot.course.service.IPaperService;
import io.ken.springboot.course.service.IQuestionTypeService;
import io.ken.springboot.course.service.IUserLevelDicService;
import io.ken.springboot.course.service.IUserService;
import io.ken.springboot.course.service.implement.HandExamServiceImpl;
import io.ken.springboot.course.service.implement.PaperServiceImpl;
import io.ken.springboot.course.service.implement.SubjectServiceImpl;
import io.ken.springboot.course.tools.RandomNum;
import io.ken.springboot.course.tools.Result;
import io.ken.springboot.course.tools.TypeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 分数管理
 *
 * @author cll
 * ExamController.java
 * 2019年11月20日
 */
@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private IPaperService paperService;

    @Autowired
    private IExamService examService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICommExamService commExamService;

    @Autowired
    private IFillExamService fillExamService;

    @Autowired
    private HandExamServiceImpl handExamServiceImpl;

    @Autowired
    private IChooseSelectStoreService chooseSelectStoreService;

    @Autowired
    private IDecideStoreService decideStoreService;

    @Autowired
    private PaperServiceImpl paperServiceImpl;

    @Autowired
    private SubjectServiceImpl subjectServiceImpl;

    @Autowired
    private IQuestionTypeService questionTypeService;

    @Autowired
    private IUserLevelDicService iUserLevelDicService;

    /**
     * 科目的试题分页查询
     *
     * @param limit
     * @param offset
     * @param subject
     * @return
     */
    @ApiOperation(value = "科目的试题分页查询", notes = "科目的试题分页查询")
    @RequestMapping("/getallQuestions")
    @ResponseBody
    public Map<String, Object> getallQuestions(int limit, int offset, String subject) {

        PageHelper.startPage(offset, limit);
        List<QuestionModel> modelList = new ArrayList<QuestionModel>();
        int start = limit * offset;
        List<Map<String, Object>> questions = new ArrayList<Map<String, Object>>();

        Map<String, String> subMap = new HashMap<String, String>();

        subMap.put("1", "北斗手持机");
        subMap.put("2", "电台操作");
        subMap.put("3", "理论知识");
        subMap.put("4", "航片判读");
        subMap.put("5", "作战计算");
        subMap.put("6", "识图用图");

        String subjectName = subMap.get(subject);

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            if ("1".equals(subject)) {
                //北斗
                questions = paperService.getPapers(subject);
                PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(questions);
                for (Map<String, Object> maps : questions) {

                    QuestionModel model = new QuestionModel();
                    model.setId((String) maps.get("question_code"));
                    model.setObject(subjectName);

                    if (maps.get("type") != null && ((Integer) maps.get("type")) == 1) {
                        model.setFamliy("设置");//类型
                    }
                    if (maps.get("type") != null && ((Integer) maps.get("type")) == 2) {
                        model.setFamliy("查询");
                    }
                    if (maps.get("type") != null && ((Integer) maps.get("type")) == 3) {
                        model.setFamliy("定位");
                    }
                    if (maps.get("type") != null && ((Integer) maps.get("type")) == 4) {
                        model.setFamliy("通信");
                    }
                    if (maps.get("type") != null && ((Integer) maps.get("type")) == 5) {
                        model.setFamliy("通信");
                    }
                    if (maps.get("t_name") != null && ((String) maps.get("t_name")).equals("a")) {
                        model.setType("填空");//类别
                        model.setType1("通信");
                    }
                    if (maps.get("t_name") != null && ((String) maps.get("t_name")).equals("b")) {
                        model.setType("填空");
                        model.setType1("填空");
                    }
                    if (maps.get("t_name") != null && ((String) maps.get("t_name")).equals("c")) {
                        model.setType("操作");
                        model.setType1("操作");
                    }
                    if (maps.get("t_name") != null && ((String) maps.get("t_name")).equals("ss")) {
                        model.setType("选择");
                        model.setType1("选择");
                    }
                    if (maps.get("t_name") != null && ((String) maps.get("t_name")).equals("dd")) {
                        model.setType("判断");
                        model.setType1("判断");
                    }
                    model.setGrade((String) maps.get("grade"));
                    model.setQuestion((String) maps.get("question_name"));
                    model.setOperation("查看");
                    modelList.add(model);
                }

                map.put("rows", modelList);
                map.put("code", 200);
                map.put("total", pageInfo.getTotal());
            } else {
                List<QuestionModel> fillModelList = new ArrayList<QuestionModel>();
                List<QuestionModel> chooseModelList = new ArrayList<QuestionModel>();
                List<QuestionModel> decideModelList = new ArrayList<QuestionModel>();
                List<QuestionModel> handModelList = new ArrayList<QuestionModel>();
                List<QuestionModel> allModelList = new ArrayList<QuestionModel>();
                //填空题
                try {
                    fillModelList = paperServiceImpl.getFillBySubject(Integer.valueOf(subject));
                    allModelList.addAll(fillModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //选择
                try {
                    chooseModelList = paperServiceImpl.getChooseBySubject(Integer.valueOf(subject));
                    allModelList.addAll(chooseModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //判断
                try {
                    decideModelList = paperServiceImpl.getDecideBySubject(Integer.valueOf(subject));
                    allModelList.addAll(decideModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //操作
                try {
                    handModelList = paperServiceImpl.getHandBySubject(Integer.valueOf(subject));
                    allModelList.addAll(handModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PageInfo<QuestionModel> pageInfo1 = new PageInfo<QuestionModel>(allModelList);
                map.put("rows", allModelList);
                map.put("code", 200);
                map.put("total", pageInfo1.getTotal());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("rows", "");
            map.put("code", 500);
            map.put("total", "");
        }

        return map;
    }

    /**
     * 试题库添题:按照试题类型区分（填空、选择等）
     *
     * @param option：题型
     * @param answer：答案
     * @param issue           ：题目内容
     * @param fileList：附件(图片)
     * @param type：类型
     * @param flag:0单选，1多选
     * @param score：得分
     * @param selectA：选项
     * @param subject         科目
     * @return by:cll
     * update:gyd
     */
    @ApiOperation(value = "试题库添题", notes = "试题库添题")
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public Result addSubject(String typeId, String typeName, String option, String answer, String issue, String fileList, String type,
                             String flag, String score, String selectA, String subject, String level) {
         /*
         1	北斗手持机
        2	电台操作
        3	理论知识
        4	航片判读
        5	作战计算
        6	识图用图 */
        Result result = new Result();
        //通讯题
        CommExamStore comModel = new CommExamStore();
        //填空题
        FillBlankExamStore fillModel = new FillBlankExamStore();
        //选择题
        ChooseSelectStore chooseModel = new ChooseSelectStore();
        //判断题
        DecideStore decideModel = new DecideStore();
        RandomNum randomNub = new RandomNum();

        List<String> subjectNameList = new ArrayList<String>();
        List<String> questionTypeList = new ArrayList<String>();
        List<Subject> subjectList = new ArrayList<Subject>();

        //1.查科目
        //subjectList = subjectServiceImpl.queryAll();
        //subjectNameList = subjectServiceImpl.getAllname();

        /*1	北斗手持机
        2	电台操作
        3	理论知识
        4	航片判读
        5	作战计算
        6	识图用图*/
        // 北斗手持机
        boolean tag = true;
        if ("1".equals(subject)) {
            if ("操作题".equals(option)) {
                tag = paperServiceImpl.questionExist("4", issue);

                if (tag) {
                    //2.1存在
                    result.setCode(200);
                    result.setMsg("这道题在试题库中已经存在了");
                    result.setSuccess(false);
                } else {
                    //操作题赋值
                    HandExamStore handQuestion = new HandExamStore();

                    handQuestion.setId(UUIDUtils.getUUID());
                    handQuestion.setSubject("1");
                    handQuestion.setType(Integer.parseInt(typeId));
                    handQuestion.setGrade(score);
                    handQuestion.setQuestionCode(UUIDUtils.getUUID(6));
                    handQuestion.setQuestionName(issue);
                    handQuestion.setLevelId(level);
                    try {

                        int saveFlag = handExamServiceImpl.addModel(handQuestion);

                        if (saveFlag > 0) {
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("添加成功");
                            result.setData("添加成功");
                        } else {
                            result.setCode(500);
                            result.setSuccess(false);
                            result.setMsg("添加失败，请重试");
                            result.setData("添加失败，请重试");
                        }
                    } catch (Exception e) {
                        //保存失败
                        e.printStackTrace();
                        result.setCode(500);
                        result.setSuccess(false);
                        result.setMsg("添加失败，请重试");
                    }
                }
            } else if ("填空题".equals(option)) {
                if ("通信".equals(type)) {
                    List<CommExamStore> comms = commExamService.getByName(issue);
                    if (comms != null && comms.size() > 0) {
                        result.setCode(200);
                        result.setData("这道题在试题库中已经存在了");
                        return result;
                    }
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");

                    comModel.setId(uuid);
                    String code = randomNub.getRandNum();
                    comModel.setQuestionCode(code);
                    comModel.setQuestionName(issue);
                    if (fileList == null || fileList.equals("")) {
                        comModel.setType(4);
                    } else {
                        comModel.setType(5);
                    }
                    comModel.setPassTableId(fileList);
                    comModel.setResult(answer);
                    comModel.setGrade(score);
                    comModel.setLevelId(level);
                    int num = commExamService.add(comModel);
                    if (num > 0) {
                        result.setCode(200);
                        result.setData("添加成功");
                    } else {
                        result.setCode(500);
                        result.setData("添加失败");
                    }
                } else {
                    List<FillBlankExamStore> comms = fillExamService.getByName(issue);
                    if (comms != null && comms.size() > 0) {
                        result.setCode(200);
                        result.setData("这道题在试题库中已经存在了");
                        return result;
                    }
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    fillModel.setFlag(1);
                    fillModel.setId(uuid);
                    String code = randomNub.getRandNum();
                    fillModel.setQuestionCode(code);
                    fillModel.setSubject("1");
                    fillModel.setQuestionName(issue);
                    if (type.equals("系统")) {
                        fillModel.setType(1);
                    }
                    if (type.equals("查询")) {
                        fillModel.setType(2);
                    }
                    if (type.equals("定位")) {
                        fillModel.setType(3);
                    }
                    fillModel.setResult(answer);
                    fillModel.setGrade(score);
                    fillModel.setLevelId(level);
                    int num = fillExamService.addModel(fillModel);
                    if (num > 0) {
                        result.setCode(200);
                        result.setData("添加成功");
                    } else {
                        result.setCode(500);
                        result.setData("添加失败");
                    }
                }
            }
            if ("选择题".equals(option)) {

                List<ChooseSelectStore> comms = chooseSelectStoreService.getByName(issue);
                if (comms != null && comms.size() > 0) {
                    result.setCode(200);
                    result.setData("这道题在试题库中已经存在了");
                    return result;
                }
                String code = randomNub.getRandNum();
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                chooseModel.setId(uuid);
                chooseModel.setQuestionName(issue);
                chooseModel.setQuestionCode(code);
                if (type.equals("系统")) {
                    chooseModel.setType(1);
                }
                if (type.equals("查询")) {
                    chooseModel.setType(2);
                }
                if (type.equals("定位")) {
                    chooseModel.setType(3);
                }
                if (type.equals("通信")) {
                    chooseModel.setType(4);
                }
                chooseModel.setGrade(score);
                chooseModel.setFlag(Integer.valueOf(flag));
                chooseModel.setResult(answer);
                chooseModel.setPassTableId(fileList);
                chooseModel.setSubject("1");
                chooseModel.setSelectA(selectA);
                chooseModel.setLevel_id(level);
                int num = chooseSelectStoreService.addModel(chooseModel);
                if (num > 0) {
                    result.setCode(200);
                    result.setData("添加成功");
                } else {
                    result.setCode(500);
                    result.setData("添加失败");
                }
            }
            if (option.equals("判断题")) {

                List<DecideStore> comms = decideStoreService.getByName(issue);
                if (comms != null && comms.size() > 0) {
                    result.setCode(200);
                    result.setData("这道题在试题库中已经存在了");
                    return result;
                }
                String code = randomNub.getRandNum();
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                decideModel.setId(uuid);
                decideModel.setQuestionName(issue);
                decideModel.setQuestionCode(code);
                decideModel.setSelectA("A.对;B.错");
                if (type.equals("系统")) {
                    decideModel.setType(1);
                }
                if (type.equals("查询")) {
                    decideModel.setType(2);
                }
                if (type.equals("定位")) {
                    decideModel.setType(3);
                }
                if (type.equals("通信")) {
                    decideModel.setType(4);
                }
                decideModel.setGrade(score);
                if (answer.equals("对")) {
                    decideModel.setResult("A");
                } else if (answer.equals("错")) {
                    decideModel.setResult("B");
                }

                decideModel.setPassTableId(fileList);
                decideModel.setSubject("1");
                decideModel.setLevel_id(level);
                int num = decideStoreService.addModel(decideModel);
                if (num > 0) {
                    result.setCode(200);
                    result.setData("添加成功");
                } else {
                    result.setCode(500);
                    result.setData("添加失败");
                }
            }
        } else {
             /*
            2	电台操作
            3	理论知识
            4	航片判读
            5	作战计算
            6	识图用图*/
            //2--6没有通讯题

            //String typeId ,String option, String answer, String issue, String fileList, String type,
            //String flag, String score, String selectA, String subject)

            if ("填空题".equals(option)) {
                //typeId区分题型
                // 1.判断题是否存在
                tag = paperServiceImpl.questionExist("1", issue);

                if (tag) {
                    //2.1存在
                    result.setCode(200);
                    result.setData("这道题在试题库中已经存在了");
                    return result;
                } else {
                    //2.2不存在
                    FillBlankExamStore fillquestion = new FillBlankExamStore();
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    fillquestion.setId(uuid);

                    if ("地貌识别".equals(typeName)) {
                        Random random = new Random();
                        fillquestion.setQuestionName("地貌识别题" + (RandomNumUtils.getRandomCode(3)));
                    } else {
                        fillquestion.setQuestionName(issue);
                    }
                    fillquestion.setPassTableId(fileList);
                    fillquestion.setType(Integer.parseInt(typeId));//题目类型
                    fillquestion.setGrade(score);
                    fillquestion.setFlag(Integer.parseInt(flag));
                    fillquestion.setResult(answer);
                    fillquestion.setQuestionCode(UUIDUtils.getUUID(6));
                    fillquestion.setSubject(subject);
                    fillquestion.setLevelId(level);
                    try {
                        int saveFlag = 0;
                        saveFlag = fillExamService.addModel(fillquestion);
                        if (saveFlag > 0) {
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("添加成功");
                            result.setData("添加成功");
                        } else {
                            result.setCode(500);
                            result.setSuccess(false);
                            result.setMsg("添加失败，请重试");
                            result.setData("添加失败，请重试");
                        }
                    } catch (Exception e) {
                        //保存失败
                        e.printStackTrace();
                        result.setCode(500);
                        result.setSuccess(false);
                        result.setMsg("添加失败，请重试");
                    }
                }
            } else if ("选择题".equals(option)) {
                tag = paperServiceImpl.questionExist("2", issue);
                if (tag) {
                    //2.1存在
                    result.setCode(200);
                    result.setData("这道题在试题库中已经存在了");
                    return result;
                } else {
                    //2.2不存在
                    ChooseSelectStore chooseQuestion = new ChooseSelectStore();
                    chooseQuestion.setId(UUIDUtils.getUUID());
                    chooseQuestion.setSubject(subject);
                    chooseQuestion.setType(Integer.parseInt(typeId));
                    chooseQuestion.setFlag(Integer.parseInt(flag));
                    chooseQuestion.setGrade(score);
                    chooseQuestion.setPassTableId(fileList);
                    chooseQuestion.setQuestionCode(UUIDUtils.getUUID(6));
                    chooseQuestion.setQuestionName(issue);
                    chooseQuestion.setResult(answer);//答案 AB
                    chooseQuestion.setSelectA(selectA);//选项 A.xx;B.xx;C.xx
                    chooseQuestion.setLevel_id(level);
                    try {

                        int saveFlag = chooseSelectStoreService.addModel(chooseQuestion);
                        if (saveFlag > 0) {
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("添加成功");
                            result.setData("添加成功");
                        } else {
                            result.setCode(500);
                            result.setSuccess(false);
                            result.setMsg("添加失败，请重试");
                            result.setData("添加失败，请重试");
                        }
                    } catch (Exception e) {
                        //保存失败
                        e.printStackTrace();
                        result.setCode(500);
                        result.setSuccess(false);
                        result.setMsg("添加失败，请重试");
                    }
                }
            } else if ("判断题".equals(option)) {

                tag = paperServiceImpl.questionExist("3", issue);

                if (tag) {
                    //2.1存在
                    result.setCode(200);
                    result.setMsg("这道题在试题库中已经存在了");
                    result.setSuccess(false);
                } else {
                    //判断题赋值
                    DecideStore decideQuestion = new DecideStore();

                    decideQuestion.setId(UUIDUtils.getUUID());
                    decideQuestion.setSubject(subject);
                    decideQuestion.setType(Integer.parseInt(typeId));
                    decideQuestion.setGrade(score);
                    decideQuestion.setPassTableId(fileList);
                    decideQuestion.setQuestionCode(UUIDUtils.getUUID(6));
                    decideQuestion.setQuestionName(issue);
                    decideQuestion.setResult(answer);
                    decideQuestion.setSelectA(selectA);
                    decideQuestion.setLevel_id(level);
                    try {

                        int saveFlag = decideStoreService.addModel(decideQuestion);

                        if (saveFlag > 0) {
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("添加成功");
                            result.setData("添加成功");
                        } else {
                            result.setCode(500);
                            result.setSuccess(false);
                            result.setMsg("添加失败，请重试");
                            result.setData("添加失败，请重试");
                        }
                    } catch (Exception e) {
                        //保存失败
                        e.printStackTrace();
                        result.setCode(500);
                        result.setSuccess(false);
                        result.setMsg("添加失败，请重试");
                    }
                }
            } else if ("操作题".equals(option)) {
                tag = paperServiceImpl.questionExist("4", issue);

                if (tag) {
                    //2.1存在
                    result.setCode(200);
                    result.setMsg("这道题在试题库中已经存在了");
                    result.setSuccess(false);
                } else {
                    //操作题赋值
                    HandExamStore handQuestion = new HandExamStore();

                    handQuestion.setId(UUIDUtils.getUUID());
                    handQuestion.setSubject(subject);
                    handQuestion.setType(Integer.parseInt(typeId));
                    handQuestion.setGrade(score);
                    handQuestion.setQuestionCode(UUIDUtils.getUUID(6));
                    handQuestion.setQuestionName(issue);
                    handQuestion.setLevelId(level);
                    if (answer.contains("。")) {
                        answer = answer.replace("。", ",");
                    }
                    handQuestion.setResult(answer);
                    try {

                        int saveFlag = handExamServiceImpl.addModel(handQuestion);

                        if (saveFlag > 0) {
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("添加成功");
                            result.setData("添加成功");
                        } else {
                            result.setCode(500);
                            result.setSuccess(false);
                            result.setMsg("添加失败，请重试");
                            result.setData("添加失败，请重试");
                        }
                    } catch (Exception e) {
                        //保存失败
                        e.printStackTrace();
                        result.setCode(500);
                        result.setSuccess(false);
                        result.setMsg("添加失败，请重试");
                    }
                }
            } else {
                //扩展
                result.setCode(404);
                result.setMsg("该题型尚未录入，请重试");
                result.setSuccess(false);
            }
        }
        return result;
    }

    //删除试题
    @RequestMapping("/delQuestions")
    @ResponseBody
    public Map<String, Object> delQuestions(String id, String type1, String subject) {
        Map<String, Object> map = new HashMap<String, Object>();
        String codes[] = id.split(",");
        String types[] = type1.split(",");
        String message = "删除成功";
        int code = 200;
        try {
            for (int i = 0; i < types.length; i++) {
                if (types[i].equals("通信")) {
                    List<String> list1 = examService.selectBycommCode(codes[i]);
                    if (list1.size() > 0) {
                        message = "您选中的题目中至少有一条已经被添加到试卷，无法删除";
                        code = 500;
                    } else {
                        examService.delByCodeComm(codes[i]);
                    }
                }
                if (types[i].equals("选择")) {
                    List<String> list1 = examService.selectBySelectCode(codes[i]);
                    if (list1.size() > 0) {
                        message = "您选中的题目中至少有一条已经被添加到试卷，无法删除";
                        code = 500;
                    } else {
                        examService.delByCodeChoose(codes[i]);
                    }
                }
                if (types[i].equals("判断")) {
                    List<String> list1 = examService.selectByDecideCode(codes[i]);
                    if (list1.size() > 0) {
                        message = "您选中的题目中至少有一条已经被添加到试卷，无法删除";
                        code = 500;
                    } else {
                        examService.delByCodeDecide(codes[i]);
                    }
                }
                if (types[i].equals("填空")) {
                    List<String> list2 = examService.selectByfillCode(codes[i]);
                    if (list2.size() > 0 || codes[i].equals("2.1") || codes[i].equals("2.2") ||
                            codes[i].equals("2.3") || codes[i].equals("2.4") || codes[i].equals("2.5") ||
                            codes[i].equals("2.51") || codes[i].equals("2.6") || codes[i].equals("2.61") ||
                            codes[i].equals("2.7") || codes[i].equals("2.71") || codes[i].equals("2.8") ||
                            codes[i].equals("2.81")) {
                        message = "您选中的题目中至少有一条已经被添加到试卷，无法删除";
                        code = 500;
                    } else {
                        examService.delByCodeFill(codes[i]);
                    }
                }
                if (types[i].equals("操作")) {
					/*List<String> list3 = examService.selectByHandCode(codes[i]);
					if(list3.size() > 0) {*/
                    message = "操作题暂时无法删除";
                    code = 500;
                    /*
                     * } else { examService.delByCodeHand(codes[i]); }
                     */
                }
            }
            map.put("code", code);
            map.put("message", message);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }

    /**
     * excel文件批量添加试题
     *
     * @param excelFile
     * @return
     */
    @ApiOperation(value = "批量导入", notes = "批量导入")
    @PostMapping("/addExcel")
    @ResponseBody
    public Map<String, Object> addExcel(@RequestParam(value = "file") MultipartFile excelFile, @RequestParam(value = "path") String path) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        Set<String> list = new TreeSet<String>();//存在存放不存在的题目类别
        List<String> pictureList = new ArrayList<String>();//存放不存在的图片名称
        Set<String> questionTypeSet = new TreeSet<String>();//存放不存在的题目类型
        Set<String> levelDicSet = new HashSet<String>();
        List<ExcelModel> chooseSelectStoreList = new ArrayList<ExcelModel>();//存放不重复的选择题
        List<ExcelModel> decideStoreList = new ArrayList<ExcelModel>();//存放不重复的判断题
        List<ExcelModel> fillBlankExamStoreList = new ArrayList<ExcelModel>();//存放不重复的填空题
        File file = null;
        File pictureFile = new File(path);
        if (excelFile.isEmpty() || excelFile.getSize() == 0) {
            map1.put("msg", "上传失败");
            map1.put("code", 500);
            return map1;
        } else {
            try {
                file = MultipartFileToFile.multipartFileToFile(excelFile);//转为file
            } catch (IOException e) {
                map1.put("msg", "添加失败");
                map1.put("code", 400);
                return map1;
            }
            try {
                RandomNum randomNum = new RandomNum();
                ExcelModel excelModel1 = null;//接收重名试题id
                List<Map> mapList = ExcelUtils.getImport(file);
                MultipartFileToFile.deleteFile();//删除转换时，产生的临时文件
                if (mapList != null || mapList.size() == 0) {
                    String key = null;
                    String value = null;
                    List<UserLevelDic> userLevelDicList = iUserLevelDicService.getList();//获取军团级别
                    for (Map map : mapList) {
                        ExcelModel excelModel = new ExcelModel();//用来存储Excel中的试题信息,每道题都为新的实例
                        excelModel.setTransflag("1");
                        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = iterator.next();
                            key = entry.getKey();
                            value = entry.getValue();
                            if (key.equals("科目")) {
                                //将科目转为对应的数字
                                excelModel.setSubject(TypeUtil.getSubject(value));
                            } else if (key.equals("题目类型")) {
                                excelModel.setQuestionType(value);
                            } else if (key.equals("题目名称")) {
                                excelModel.setQuestionName(value);
                            } else if (key.equals("题目答案")) {
                                value = value.replaceAll("；", ";");//将中文；替换成英文
                                excelModel.setResult(value);
                            } else if (key.substring(0, 2).equals("选项")) {
                                value = value.replaceAll("；", ";");//将中文；替换成英文
                                excelModel.setSelectA(value.trim());
                            } else if (key.equals("分数")) {
                                excelModel.setGrade(value);
                            } else if (key.equals("题目类别")) {
                                excelModel.setType(value);
                            } else if (key.equals("图片名称")) {
                                excelModel.setPicturePath(value);
                            } else if (key.equals("题目等级")) {
                                value = value.replaceAll("；", ";");
                                excelModel.setLevelId(value);
                            } else {
                                map1.put("msg", "请按规范编辑试题文件");
                                map1.put("code", 500);
                                return map;
                            }
                        }
                        //获取文件夹下的所有文件的名称，查看是否存在该文件
                        String picturePath = excelModel.getPicturePath();//图片路径

                        boolean isExist = false;//默认图片不存在
                        if (picturePath == null || picturePath.equals("")) {
                            isExist = true;
                        } else {
                            for (String name : pictureFile.list()) {
                                if (name.equals(picturePath.trim())) {
                                    excelModel.setPicturePath(path + "/" + picturePath);
                                    //将图片转为Base64
                                    String picture = Base64Utils.ImageToBase64ByLocal(excelModel.getPicturePath());
                                    excelModel.setPassTableId(picture);
                                    isExist = true;
                                    break;
                                }
                            }
                        }

                        //判断题目难度的军团等级是否存在
                        String[] levelArray = excelModel.getLevelId().split(";");
                        String levelId = new String();
                        boolean right = false;//默认不存在军团等级
                        int num = 0;//用于判断本试题题目等级是否合格
                        if (excelModel.getLevelId().equals("0")) {
                            for (UserLevelDic userLevelDic : userLevelDicList) {
                                if (levelId.length() > 0) {
                                    levelId += ",";
                                }
                                levelId += userLevelDic.getLevelId();
                            }
                            right = true;
                        } else {
                            for (String level : levelArray) {
                                for (UserLevelDic userLevelDic : userLevelDicList) {
                                    if (userLevelDic.getLevelId() == (Integer.parseInt(level))) {
                                        if (levelId.length() > 0) {
                                            levelId += ",";
                                        }
                                        right = true;
                                        levelId += userLevelDic.getLevelId();
                                        break;
                                    }
                                }
                                if (right == false) {
                                    num = 1;
                                    levelDicSet.add(level);
                                } else {
                                    right = false;
                                }
                            }
                        }
                        excelModel.setLevelId(levelId);

                        //根据题名和图片路径查询是否有相同的数据
                        String questionType = excelModel.getQuestionType();//获取试题类型(选择、填空、判断)
                        if (questionType.equals("单选") || questionType.equals("多选")) {
                            excelModel1 = chooseSelectStoreService.queryName(excelModel.getQuestionName(), excelModel.getResult());
                        } else if (questionType.equals("判断")) {
                            excelModel1 = decideStoreService.queryName(excelModel.getQuestionName(), excelModel.getResult());
                        } else if (questionType.equals("填空")) {
                            excelModel1 = fillExamService.queryName(excelModel.getQuestionName(), excelModel.getResult());
                        } else {//当题目类型不属于选择、填空、判断，将题目类型放入questionTypeList
                            questionTypeSet.add(questionType);
                        }

                        //查看题型是否存在，并返回题型id，若不存在，则记录该类型
                        Integer typeId = questionTypeService.getTypeId(excelModel);
                        if (typeId == null || typeId.equals("")) {
                            list.add(excelModel.getType());
                            //当图片也不在文件夹存在的时候，将不存在的图片存入pictureList
                            if (isExist == false) {
                                pictureList.add(excelModel.getPicturePath());
                            }
                        } else if (isExist == false) {
                            pictureList.add(excelModel.getPicturePath());
                        } else if (num == 1) {//当题目等级不合法的时候，不执行之后操作
                            continue;
                        } else {

                            //将题型id存入实例
                            excelModel.setType(String.valueOf(typeId));
                            //
                            if (excelModel1 == null) {
                                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                                excelModel.setId(uuid);

                                //不重复试题存入对应集合,统一调用Service
                                excelModel.setQuestionCode(randomNum.getRandNum());

                                if (questionType != null && !questionType.equals("")) {
                                    if (questionType.equals("多选")) {
                                        excelModel.setFlag(1);
                                        chooseSelectStoreList.add(excelModel);
                                    } else if (questionType.equals("单选")) {
                                        excelModel.setFlag(0);
                                        chooseSelectStoreList.add(excelModel);
                                    } else if (questionType.equals("填空")) {
                                        //是否有标准答案
                                        if (excelModel.getResult() == null || excelModel.getResult().equals("")) {
                                            excelModel.setFlag(0);
                                        } else {
                                            excelModel.setFlag(1);
                                        }
                                        fillBlankExamStoreList.add(excelModel);
                                    } else if (questionType.equals("判断")) {
                                        decideStoreList.add(excelModel);
                                    }
                                }
                            } else {//重复试题，进行覆盖，更新试题内容
                                String id = excelModel1.getId();
                                excelModel.setId(id);

                                if (questionType.equals("多选")) {
                                    excelModel.setFlag(1);
                                    chooseSelectStoreService.updateQuestions(excelModel);
                                } else if (questionType.equals("单选")) {
                                    excelModel.setFlag(0);
                                    chooseSelectStoreService.updateQuestions(excelModel);
                                } else if (questionType.equals("填空")) {
                                    //是否有标准答案
                                    if (excelModel.getResult() == null || excelModel.getResult().equals("")) {
                                        excelModel.setFlag(0);
                                    } else {
                                        excelModel.setFlag(1);
                                    }
                                    fillExamService.updateQuestions(excelModel);
                                } else if (questionType.equals("判断")) {
                                    decideStoreService.updateQuestions(excelModel);
                                }
                            }
                        }
                    }
                }
            //试题入库
            if (chooseSelectStoreList.size() > 0) {
                int i = chooseSelectStoreService.addExcel(chooseSelectStoreList);
            }
            if (decideStoreList.size() > 0) {
                decideStoreService.addExcel(decideStoreList);
            }
            if (fillBlankExamStoreList.size() > 0) {
                fillExamService.addExcel(fillBlankExamStoreList);
            }
            if (list.size() > 0 || pictureList.size() > 0 || questionTypeSet.size() > 0 || levelDicSet.size() > 0) {
                map1.put("msg", "不存在以下题目类别,或题目类型,或图片，题目等级");
                map1.put("code", 200);
                map1.put("type", list);
                map1.put("picture", pictureList);
                map1.put("levelDic", levelDicSet);
                map1.put("questionType", questionTypeSet);
                return map1;
            } else {
                map1.put("msg", "添加成功");
                map1.put("code", 200);
                return map1;
            }
            } catch (Exception e) {
                map1.put("msg", "添加失败");
                map1.put("code", 400);
                return map1;
            }
        }
    }

    /**
     * 获取科目题型的分类
     *
     * @param subject
     * @return by:gyd
     */
    @ApiOperation(value = "获取科目题型的分类", notes = "获取科目题型的分类")
    @RequestMapping(value = "/getSubectInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getInfo6(String subject) {

        Result result = new Result();
        List<QuestionType> sonList = new ArrayList<QuestionType>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", "");
        dataMap.put("text", "全部");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(dataMap);
        /*Map<String, Integer> subMap = new HashMap<String, Integer>();
        subMap.put("北斗手持机", 1);
        subMap.put("电台操作", 2);
        subMap.put("理论知识", 3);
        subMap.put("航片判读", 4);
        subMap.put("作战计算", 5);
        subMap.put("识图用图", 6);*/
        if (subject != null) {

            try {
                sonList = paperServiceImpl.getSubjectSons(Integer.parseInt(subject));

                for (QuestionType questionType : sonList) {
                    dataMap = new HashMap<String, Object>();
                    dataMap.put("id", questionType.getId());
                    dataMap.put("text", questionType.getTypeName());
                    list.add(dataMap);
                }

                result.setMsg("查询成功");
                result.setCode(200);
                result.setSuccess(true);
                result.setData(list);
            } catch (Exception e) {
                e.printStackTrace();
                result.setMsg("请重试！");
                result.setCode(500);
                result.setSuccess(false);
            }
        } else {
            //科目参数为空
            result.setMsg("科目为空");
            result.setCode(404);
            result.setSuccess(false);
        }
        return result;
    }
}
