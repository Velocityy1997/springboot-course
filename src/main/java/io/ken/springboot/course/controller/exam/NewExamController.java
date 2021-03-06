package io.ken.springboot.course.controller.exam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.ClassDic;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.model.CommModel;
import io.ken.springboot.course.model.NewExam;
import io.ken.springboot.course.model.TreeClass;
import io.ken.springboot.course.model.TreeClassModel;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.service.IChooseSelectStoreService;
import io.ken.springboot.course.service.IClassDicService;
import io.ken.springboot.course.service.ICommExamService;
import io.ken.springboot.course.service.IDecideStoreService;
import io.ken.springboot.course.service.IExamService;
import io.ken.springboot.course.service.IFillExamService;
import io.ken.springboot.course.service.IHandExamService;
import io.ken.springboot.course.service.IQuestionTypeService;
import io.ken.springboot.course.service.IUserService;
import io.ken.springboot.course.tools.PageUtil;
import io.ken.springboot.course.tools.PhotoUtil;
import io.ken.springboot.course.tools.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 出题
 *
 * @author NewExamController.java
 * 2019年11月21日
 */
@Controller
@RequestMapping("/newexam")
public class NewExamController {

    @Autowired
    private IExamService examService;

    @Autowired
    private IQuestionTypeService questionTypeService;

    @Autowired
    private IClassDicService classDicService;

    @Autowired
    private IChooseSelectStoreService iChooseSelectStoreService;

    @Autowired
    private IFillExamService iFillExamService;

    @Autowired
    private IDecideStoreService iDecideStoreService;

    @Autowired
    private IExamService iExamService;

    @Autowired
    private IHandExamService iHandExamService;

    @Autowired
    private ICommExamService iCommExamService;
    @Autowired
    private IUserService iUserService;

    /**
     * @param type     = 1(开机设置查询hand_exam_store表)
     *                 type=2 （本机信息查询 查询fill_blank_exam_store）
     *                 type =3 (定位 查询hand_exam_store表 和 fill_blank_exam_store表)
     *                 tyep = 4 (短报文通信 查询comm_exam_store)
     * @param limit条数
     * @param offset页数
     * @return
     */
    @RequestMapping("/getneweam")
    @ResponseBody
    public Map<String, Object> getNewExam(String type, int limit, int offset, String subject, String level, String category) {
        //type 是填空或选择
        PageHelper.startPage(offset, limit);
        List<HandExamStore> handList = new ArrayList<HandExamStore>();
        List<ChooseSelectStore> selectList = new ArrayList<ChooseSelectStore>();
        List<DecideStore> decideList = new ArrayList<DecideStore>();
        List<CommModel> modelList = new ArrayList<CommModel>();
        List<NewExam> newExamList = new ArrayList<NewExam>();
        Map<String, Object> map = new HashMap<String, Object>();
        int cate = 0;
        if (!(category.equals("全部"))) {
            cate = questionTypeService.getTypeIdBySAndT(subject, category);
        }
        try {

            if (type.equals("1")) {    //操作题
                PageHelper.startPage(offset, limit);
                if ((category.equals("全部"))) {
                    handList = examService.getHandList(subject, level);
                } else {
                    handList = examService.getHandList1(subject, cate, level);
                }
                PageInfo<HandExamStore> pageInfo = new PageInfo<HandExamStore>(handList);
                for (int i = 0; i < handList.size(); i++) {
                    String id = handList.get(i).getId();
                    String questionName = handList.get(i).getQuestionName();
                    String questionCode = handList.get(i).getQuestionCode();
                    int typer = handList.get(i).getType();
                    String grade = handList.get(i).getGrade();
                    CommModel model = new CommModel();
                    model.setId(id);
                    model.setGrade(grade);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setTypeQ("操作题");
                    modelList.add(model);
                }

                map.put("rows", modelList);
                map.put("total", pageInfo.getTotal());
            } else if (type.equals("2")) {//填空题
                PageHelper.startPage(offset, limit);
                if ((category.equals("全部"))) {
                    if ((subject.equals("1"))) {
                        newExamList = examService.getHF(subject, level);//联合查询通信题和填空题
                    } else {
                        newExamList = examService.getFill(subject, level);//查询填空题
                    }
                } else {

                    newExamList = examService.getHF1(subject, cate, level);//查询填空题
                }

                PageInfo<NewExam> pageInfo = new PageInfo<NewExam>(newExamList);
                for (int i = 0; i < newExamList.size(); i++) {

                    CommModel model = new CommModel();
                    String id = newExamList.get(i).getId();
                    String questionName = newExamList.get(i).getQuestionName();
                    String questionCode = newExamList.get(i).getQuestionCode();
                    int typer = newExamList.get(i).getType();
                    String grade = newExamList.get(i).getGrade();
                    model.setId(id);
                    model.setGrade(grade);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setGrade(grade);
                    model.setPicturePath(newExamList.get(i).getPicturePath());
                    if (newExamList.get(i).getPassTableId() != null) {
                        model.setImg(PhotoUtil.sqlTophoto(newExamList.get(i).getPassTableId()));
                    }
                    String typeQ = newExamList.get(i).getTypeQ();
                    if (typeQ.equals("comm")) {
                        model.setTypeQ("通信题");
                    } else {
                        model.setTypeQ("填空题");
                    }
                    modelList.add(model);
                }
                map.put("rows", modelList);
                map.put("total", pageInfo.getTotal());
            } else if (type.equals("3")) {//选择题
                PageHelper.startPage(offset, limit);
                if ((category.equals("全部"))) {
                    selectList = examService.getSelect(subject, level);
                } else {
                    selectList = examService.getSelect1(subject, cate, level);
                }

                PageInfo<ChooseSelectStore> pageInfo = new PageInfo<ChooseSelectStore>(selectList);
                for (int i = 0; i < selectList.size(); i++) {

                    CommModel model = new CommModel();
                    String id = selectList.get(i).getId();
                    String questionName = selectList.get(i).getQuestionName();
                    String questionCode = selectList.get(i).getQuestionCode();
                    int typer = selectList.get(i).getType();
                    String grade = selectList.get(i).getGrade();
                    model.setId(id);
                    model.setGrade(grade);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setGrade(grade);
                    model.setPicturePath(selectList.get(i).getPicturePath());
                    if (selectList.get(i).getPassTableId() != null) {
                        model.setImg(PhotoUtil.sqlTophoto(selectList.get(i).getPassTableId()));
                    }
                    model.setSelect(selectList.get(i).getSelectA());
                    model.setTypeQ("选择题");
                    modelList.add(model);
                }
                map.put("rows", modelList);
                map.put("total", pageInfo.getTotal());
            } else if (type.equals("4")) {//判断题
                PageHelper.startPage(offset, limit);
                if ((category.equals("全部"))) {
                    decideList = examService.getDecide(subject, level);
                } else {
                    decideList = examService.getDecide1(subject, cate, level);
                }

                PageInfo<DecideStore> pageInfo = new PageInfo<DecideStore>(decideList);
                for (int i = 0; i < decideList.size(); i++) {
                    CommModel model = new CommModel();
                    String id = decideList.get(i).getId();
                    String questionName = decideList.get(i).getQuestionName();
                    String questionCode = decideList.get(i).getQuestionCode();
                    int typer = decideList.get(i).getType();
                    String grade = decideList.get(i).getGrade();
                    model.setId(id);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setGrade(grade);
                    model.setPicturePath(decideList.get(i).getPicturePath());
                    if (decideList.get(i).getPassTableId() != null) {
                        model.setImg(PhotoUtil.sqlTophoto(decideList.get(i).getPassTableId()));
                    }
                    model.setSelect(decideList.get(i).getSelectA());
                    model.setTypeQ("判断题");
                    modelList.add(model);
                }
                map.put("rows", modelList);
                map.put("total", pageInfo.getTotal());
            } else {
                if ((category.equals("全部"))) {
                    handList = examService.getHandList(subject, level);
                } else {
                    handList = examService.getHandList1(subject, cate, level);
                }
                for (int i = 0; i < handList.size(); i++) {
                    String id = handList.get(i).getId();
                    String questionName = handList.get(i).getQuestionName();
                    String questionCode = handList.get(i).getQuestionCode();
                    int typer = handList.get(i).getType();
                    String grade = handList.get(i).getGrade();
                    CommModel model = new CommModel();
                    model.setId(id);
                    model.setGrade(grade);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setTypeQ("操作题");
                    modelList.add(model);
                }
                if ((category.equals("全部"))) {
                    if ((subject.equals("1"))) {
                        newExamList = examService.getHF(subject, level);//联合查询通信题和填空题
                    } else {
                        newExamList = examService.getFill(subject, level);//查询填空题
                    }
                } else {
                    newExamList = examService.getHF1(subject, cate, level);//查询填空题
                }

                for (int i = 0; i < newExamList.size(); i++) {

                    CommModel model = new CommModel();
                    String id = newExamList.get(i).getId();
                    String questionName = newExamList.get(i).getQuestionName();
                    String questionCode = newExamList.get(i).getQuestionCode();
                    int typer = newExamList.get(i).getType();
                    String grade = newExamList.get(i).getGrade();
                    model.setId(id);
                    model.setGrade(grade);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setGrade(grade);
                    model.setPicturePath(newExamList.get(i).getPicturePath());
                    if (newExamList.get(i).getPassTableId() != null) {
                        model.setImg(PhotoUtil.sqlTophoto(newExamList.get(i).getPassTableId()));
                    }
                    String typeQ = newExamList.get(i).getTypeQ();
                    if (typeQ.equals("comm")) {
                        model.setTypeQ("通信题");
                    } else {
                        model.setTypeQ("填空题");
                    }
                    modelList.add(model);
                }
                if ((category.equals("全部"))) {
                    selectList = examService.getSelect(subject, level);
                } else {
                    selectList = examService.getSelect1(subject, cate, level);
                }

                for (int i = 0; i < selectList.size(); i++) {

                    CommModel model = new CommModel();
                    String id = selectList.get(i).getId();
                    String questionName = selectList.get(i).getQuestionName();
                    String questionCode = selectList.get(i).getQuestionCode();
                    int typer = selectList.get(i).getType();
                    String grade = selectList.get(i).getGrade();
                    model.setId(id);
                    model.setGrade(grade);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setGrade(grade);
                    model.setPicturePath(selectList.get(i).getPicturePath());
                    if (selectList.get(i).getPassTableId() != null) {
                        model.setImg(PhotoUtil.sqlTophoto(selectList.get(i).getPassTableId()));
                    }
                    model.setSelect(selectList.get(i).getSelectA());
                    model.setTypeQ("选择题");
                    modelList.add(model);
                }
                if ((category.equals("全部"))) {
                    decideList = examService.getDecide(subject, level);
                } else {
                    decideList = examService.getDecide1(subject, cate, level);
                }

                for (int i = 0; i < decideList.size(); i++) {
                    CommModel model = new CommModel();
                    String id = decideList.get(i).getId();
                    String questionName = decideList.get(i).getQuestionName();
                    String questionCode = decideList.get(i).getQuestionCode();
                    int typer = decideList.get(i).getType();
                    String grade = decideList.get(i).getGrade();
                    model.setId(id);
                    model.setQuestionName(questionName);
                    model.setQuestionCode(questionCode);
                    model.setType(typer);
                    model.setGrade(grade);
                    model.setPicturePath(decideList.get(i).getPicturePath());
                    if (decideList.get(i).getPassTableId() != null) {
                        model.setImg(PhotoUtil.sqlTophoto(decideList.get(i).getPassTableId()));
                    }
                    model.setSelect(decideList.get(i).getSelectA());
                    model.setTypeQ("判断题");
                    modelList.add(model);
                }
                List<CommModel> modelList1 = new ArrayList<CommModel>();
                modelList1 = PageUtil.startPage(modelList, limit, offset);
                map.put("rows", modelList1);
                map.put("total", modelList.size());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            map.put("rows", null);
            map.put("total", 0);
        }
        return map;
    }

    /**
     * 获取班级及班级下所属学生
     *
     * @return
     */
    @RequestMapping(value = "/getclassandstu", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeClassModel> getClassAndStu(String level) {

        List<TreeClass> treeList = new ArrayList<TreeClass>();
        List<ClassDic> nameList = new ArrayList<ClassDic>();
        List<TreeClassModel> modelList1 = new ArrayList<TreeClassModel>();

        String className = null;//班级名
        String stuClass = null;// 学生所属班级
        String classId = null;//班级ID

        try {
            nameList = classDicService.getNameList();
            treeList = examService.getTreeList(level);
            TreeClassModel t1 = new TreeClassModel();
            for (int i = 0; i < nameList.size(); i++) {
                List<TreeClassModel> modelList3 = new ArrayList<TreeClassModel>();
                t1 = new TreeClassModel();
                className = nameList.get(i).getClassName();
                classId = nameList.get(i).getClassId();
                for (int j = 0; j < treeList.size(); j++) {
                    TreeClassModel t2 = new TreeClassModel();
                    stuClass = treeList.get(j).getClassName();
                    String[] model = null;
                    if (className.equals(stuClass)) {
                        String idNum = treeList.get(j).getIdNumber();
                        String name = treeList.get(j).getName();
                        t2.setChildren(new ArrayList<Object>());
                        t2.setExpanded("false");
                        t2.setId(idNum);
                        t2.setType("2");
                        t2.setTitle(name);
                        t2.setName(name);
                        modelList3.add(t2);
                    }
                }

                t1.setChildren(modelList3);
                t1.setExpanded("false");
                t1.setId(classId);
                t1.setType("1");
                t1.setTitle(className);
                t1.setName(className);
                modelList1.add(t1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            TreeClassModel t1 = new TreeClassModel();
            t1.setChildren(modelList1);
            t1.setExpanded("false");
            t1.setId(classId);
            t1.setType("1");
            t1.setTitle(className);
            t1.setName(className);
            modelList1.add(t1);
        }

        return modelList1;
    }

    /**
     * 生成新试卷
     *
     * @param hand
     * @return
     */
    @ApiOperation(value = "生成新试卷", notes = "新试卷")
    @RequestMapping(value = "/createNewExam")
    @ResponseBody
    public Result createNewExam(@RequestParam("hand") String hand) {
        Result result = new Result();
        CreatNewExam creatNewExam = JSON.parseObject(hand, CreatNewExam.class);
        boolean tag = iExamService.checkExamName(creatNewExam.getTestName());
        boolean tag1 = iExamService.checkExam(creatNewExam.getSubject(),creatNewExam.getLevel());

        if (tag) {
            //名称存在
            result.setMsg("考试名称已存在");
            result.setCode(500);
            result.setSuccess(true);
            result.setData("");
            return result;
        }
        if (tag1) {
            //名称存在
            result.setMsg("试卷已存在，不用重复添加了");
            result.setCode(500);
            result.setSuccess(true);
            result.setData("");
            return result;
        }
        //存放试题code
        List<String> chooseCode = new ArrayList<String>();
        List<String> decideCode = new ArrayList<String>();
        List<String> fillBlankCode = new ArrayList<String>();
        List<String> handCode = new ArrayList<String>();
        List<String> commCode = new ArrayList<String>();
        //存放试题答案
        List<String> chooseResult = new ArrayList<String>();
        List<String> decideResult = new ArrayList<String>();
        List<String> fillBlankResult = new ArrayList<String>();
        List<String> handResult = new ArrayList<String>();
        List<String> commResult = new ArrayList<String>();
        //取题
        List<ChooseSelectStore> chooseSelectStoreListR = new ArrayList<ChooseSelectStore>();//单选题
        List<ChooseSelectStore> chooseSelectStoreListC = new ArrayList<ChooseSelectStore>();//多选
        List<DecideStore> decideStoreList = new ArrayList<DecideStore>();//判断
        List<FillBlankExamStore> fillBlankExamStoreList = new ArrayList<FillBlankExamStore>();//填空
        List<HandExamStore> handExamStoreList = new ArrayList<HandExamStore>();//操作题
        List<CommExamStore> commExamStoreList = new ArrayList<CommExamStore>();//通讯题
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date="";

        /**
         *  这里进行说明一下
         *  因为手持机的题型与其他题不一样，所以如果是手持机的试卷，前台发过来的json每种题的数量，含义进行等值一下
         *  选择单选 = 本机信息（填空题）
         *  选择多选  =  设置题（操作题）
         *  填空题  =定位题（填空题）
         *  判断题  =  短报题（通信题）
         */

        //6种题的数量
        String singleChooseNum = "0";//单选
        String manyChooseNum = "0";//多选
        String decideNum = "0";//判断
        String fillNum = "0";//填空 || 如果是手持机则：本机号，定位数量“，”隔开
        String handNum = "0";//操作题
        String commNum = "0";//通讯题

        try {
            if (creatNewExam.getSubject().equals("1")) {
                if (creatNewExam.getRadio() != 0) {//本机信息
                    fillBlankExamStoreList = iFillExamService.getQuestion(creatNewExam, 2.1, 2.4);
                    for (FillBlankExamStore fillBlankExamStore : fillBlankExamStoreList) {
                        fillBlankCode.add(fillBlankExamStore.getQuestionCode());
                        fillBlankResult.add(fillBlankExamStore.getResult());
                    }
                    fillNum = String.valueOf(fillBlankExamStoreList.size());
                }
                if (creatNewExam.getPack() != 0) {//定位题
                    fillBlankExamStoreList = iFillExamService.getQuestion(creatNewExam, 2.5, 2.81);
                    for (FillBlankExamStore fillBlankExamStore : fillBlankExamStoreList) {
                        fillBlankCode.add(fillBlankExamStore.getQuestionCode());
                        fillBlankResult.add(fillBlankExamStore.getResult());
                    }
                    fillNum = fillNum + "," + String.valueOf(fillBlankExamStoreList.size());
                }
                if (creatNewExam.getJudge() != 0) {//通讯题
                    commExamStoreList = iCommExamService.getQuestion(creatNewExam);
                    for (CommExamStore commExamStore : commExamStoreList) {
                        commCode.add(commExamStore.getQuestionCode());
                        commResult.add(commExamStore.getResult());
                    }
                    commNum = String.valueOf(commExamStoreList.size());
                }
                if (creatNewExam.getCheckbox() != 0) {//操作题
                    handExamStoreList = iHandExamService.getQuestion(creatNewExam, 1.1, 1.7);
                    for (HandExamStore handExamStore : handExamStoreList) {
                        handCode.add(handExamStore.getQuestionCode());
                        handResult.add(handExamStore.getResult());
                    }
                    handNum = String.valueOf(handExamStoreList.size());
                }
            } else {

                if (creatNewExam.getRadio() != 0) {
                    creatNewExam.setFlag("0");
                    //单选
                    chooseSelectStoreListR = iChooseSelectStoreService.getQuestion(creatNewExam);

                    for (ChooseSelectStore chooseSelectStore : chooseSelectStoreListR) {
                        chooseCode.add(chooseSelectStore.getQuestionCode());
                        chooseResult.add(chooseSelectStore.getResult());
                    }

                    singleChooseNum = String.valueOf(chooseSelectStoreListR.size());
                }
                if (creatNewExam.getCheckbox() != 0) {
                    creatNewExam.setFlag("1");
                    //多选
                    chooseSelectStoreListC = iChooseSelectStoreService.getQuestion(creatNewExam);

                    for (ChooseSelectStore chooseSelectStore : chooseSelectStoreListC) {
                        chooseCode.add(chooseSelectStore.getQuestionCode());
                        chooseResult.add(chooseSelectStore.getResult());
                    }

                    manyChooseNum = String.valueOf(chooseSelectStoreListC.size());
                }
                if (creatNewExam.getJudge() != 0) {
                    //判断
                    decideStoreList = iDecideStoreService.getQuestion(creatNewExam);
                    for (DecideStore decideStore : decideStoreList) {
                        decideCode.add(decideStore.getQuestionCode());
                        decideResult.add(decideStore.getResult());
                    }

                    decideNum = String.valueOf(decideStoreList.size());
                }
                if (creatNewExam.getPack() != 0) {
                    //填空
                    fillBlankExamStoreList = iFillExamService.getQuestion(creatNewExam);
                    for (DecideStore decideStore : decideStoreList) {
                        fillBlankCode.add(decideStore.getQuestionCode());
                        fillBlankResult.add(decideStore.getResult());
                    }

                    fillNum = String.valueOf(fillBlankExamStoreList.size());
                }
            }
            String chooseCodes = String.join(";", chooseCode);//单选和多选的题号
            String chooseResults = String.join(";", chooseResult);
            String decideCodes = String.join(";", decideCode);//判断题号
            String decideResults = String.join(";", decideResult);
            String fillBlankCodes = String.join(";", fillBlankCode);//填空题号
            String fillBlankResults = String.join(";", fillBlankResult);
            String handCodes=String.join(";",handCode);//操作题题号
            String handResults=String.join(";",handResult);
            String commCodes=String.join(";",commCode);//通讯题题号
            String commResults=String.join(";",commResult);

            //创建试卷
            //String[] idNumber = creatNewExam.getIdNumber().split(",");
            List<Exam> examList = new ArrayList<Exam>();//存放试卷
            String uuid = "";
            Exam exam = null;
            String level = creatNewExam.getLevel();
           // for (String id : idNumber) {
                exam = new Exam();
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
                exam.setExamId(uuid);
                if (level != null) {
                    exam.setLevel(level);
                } else {
                    exam.setLevel("0");
                }

                exam.setType(Integer.parseInt(creatNewExam.getSubject()));
                //将生成试卷时间改成考试开始时间
                date = df.format(new Date());
                exam.setStartTime(Timestamp.valueOf(date));
                exam.setStatus(0);
                exam.setExamName(creatNewExam.getTestName());
                exam.setQuestionNum(creatNewExam.getQuestionNum());
                exam.setTotalGrade(creatNewExam.getTotalGrade());
                exam.setDuration(creatNewExam.getDuration());
                exam.setQuestionGrade(creatNewExam.getQuestionGrade());
                exam.setCreateExam(0);
                //exam.setIdNumber(creatNewExam.getIdNumber());
                //exam.setIdNumber(id);

                exam.setChooseExamResult(chooseResults);
                exam.setChooseExamQuestion(chooseCodes);

                exam.setDecideExamResult(decideResults);
                exam.setDecideExamQuestion(decideCodes);

                exam.setFillBlankExamResult(fillBlankResults);
                exam.setFillBlankExamQuestion(fillBlankCodes);

                exam.setHandExamResult(handResults);
                exam.setHandExamQuestion(handCodes);

                exam.setCommExamResult(commResults);
                exam.setCommExamQuestion(commCodes);

                //保存题的数量
                exam.setSingleChooseNum(singleChooseNum);
                exam.setManyChooseNum(manyChooseNum);
                exam.setDecideNum(decideNum);
                exam.setFillNum(fillNum);
                exam.setHandNum(handNum);
                exam.setCommNum(commNum);
                examList.add(exam);


            int numExcel = examService.addExam(examList);
            /**
             * 批量更新用户未考信息
             */
           // int numUser= iUserService.updateExamNames(idNumber,creatNewExam.getTestName());

            result.setMsg("添加成功");
            result.setCode(200);
            result.setData("");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg("添加失败");
            result.setCode(400);
            result.setData("");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "题数统计", notes = "题数统计")
    @RequestMapping(value = "/countExam")
    @ResponseBody
    public Map<String, Object> countExam(String subject, String level) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, Object> map1 = new HashMap<String, Object>();

        if ("0".equals(level)) {
            level = "1,2,3,4,5,6";
        }
        /**
         * 当subject为1（北斗手持机时，以下参数为||后面的含义）
         */
        int radio = 0;//选择单选 || 本机信息
        int checkbox = 0;//选择多选  ||  设置题
        int pack = 0;  //填空题  ||定位题
        int judge = 0;//判断题  ||  短报题
        try {
            if (subject.equals("1")) {//北斗手持机专用
                radio = iFillExamService.getCount(subject, 2.1, 2.4, level);//本机信息的题号为2.1-2.4
                pack = iFillExamService.getCount(subject, 2.5, 2.81, level);//定位题为2.5-2.81
                checkbox = iHandExamService.getCount(subject, 1.1, 1.7, level);//操作题题号为1.1-1.7
                judge = iCommExamService.getCount(subject, level);//短报题
            } else {
                radio = iChooseSelectStoreService.getCount(subject, 0, level);//选择单选
                checkbox = iChooseSelectStoreService.getCount(subject, 1, level);
                pack = iFillExamService.getCount(subject, level);
                judge = iDecideStoreService.getCount(subject, level);
            }
            map.put("radio", radio);
            map.put("checkbox", checkbox);
            map.put("pack", pack);
            map.put("judge", judge);
            map1.put("code", 200);
            map1.put("data", map);
        } catch (Exception e) {
            map1.put("code", 400);
            map1.put("data", "查询失败");
        }
        return map1;
    }
}
