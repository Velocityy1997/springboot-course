package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.CommExam;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.bean.paper.PicturePaper;
import io.ken.springboot.course.dao.ExamMapper;
import io.ken.springboot.course.dao.UserMapper;
import io.ken.springboot.course.dao.exam.ExamInfoMapper;
import io.ken.springboot.course.model.exam.CommExamModel;
import io.ken.springboot.course.model.exam.HandExamInfoModel;
import io.ken.springboot.course.model.exam.HandExamModel;
import io.ken.springboot.course.model.exam.HandExamPaperModel;
import io.ken.springboot.course.model.exam.MobileExamModel;
import io.ken.springboot.course.model.exam.MobileInModel;
import io.ken.springboot.course.model.exam.MobileInModel1;
import io.ken.springboot.course.model.exam.MobileOutModel;
import io.ken.springboot.course.model.exam.TestDataExamModel;
import io.ken.springboot.course.model.exam.model.ExamInfoNewModel;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.model.exam.newExam.ExampaperModel;
import io.ken.springboot.course.model.exam.newExam.TestInnerData;
import io.ken.springboot.course.service.IChooseSelectStoreService;
import io.ken.springboot.course.service.ICommExamService;
import io.ken.springboot.course.service.IDecideStoreService;
import io.ken.springboot.course.service.IFillExamService;
import io.ken.springboot.course.service.IHandExamService;
import io.ken.springboot.course.tools.PhotoUtil;
import io.ken.springboot.course.tools.PictureExamResult;
import io.ken.springboot.course.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author GouYudong
 * @create 2019-11-22 14:29
 **/
@Service
public class ExamInfoServiceImpl {

    @Resource
    private ExamInfoMapper examInfoMapper;

    @Resource
    private ExamMapper examMapper;

    @Autowired
    private IChooseSelectStoreService iChooseSelectStoreService;

    @Autowired
    private IFillExamService iFillExamService;

    @Autowired
    private IDecideStoreService iDecideStoreService;

    @Autowired
    private ExamServiceImpl examServiceImpl;

    @Autowired
    private ChooseSelectStoreServiceImpl chooseSelectStoreServiceImpl;

    //handExamServiceImpl   fillExamServiceImpl     chooseExamServiceImpl   decideStoreServiceImpl
    @Autowired
    private HandExamServiceImpl handExamServiceImpl;

    @Autowired
    private FillExamServiceImpl fillExamServiceImpl;

    @Autowired
    private ChooseSelectStoreServiceImpl chooseExamServiceImpl;

    @Autowired
    private DecideStoreServiceImpl decideStoreServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ICommExamService iCommExamService;

    @Autowired
    private IHandExamService iHandExamService;

    /**
     * 处理操作题
     *
     * @param arr:操作题号数组
     * @param startQuestionNum：题号
     * @return 操作题的集合
     * by gyd
     */
    public HandExamInfoModel getHandExam(String arr[], int startQuestionNum, String examId) {

        HandExamInfoModel handExamInfoModel = new HandExamInfoModel();

        List<HandExamStore> handExamList = new ArrayList<HandExamStore>();

        List<HandExamStore> handExamInfoList = new ArrayList<HandExamStore>();
        HandExamModel handExamModel = new HandExamModel();

        Map<String, Map<String, String>> outMap = new HashMap<String, Map<String, String>>();

        boolean flag = false;

        if (arr == null) {
            //操作题为空
            //操作题为空，填充操作题的数据,type=0
            Map<String, String> inMap1 = new HashMap<String, String>();
            Map<String, String> inMap2 = new HashMap<String, String>();
            Map<String, String> inMap3 = new HashMap<String, String>();
            Map<String, String> inMap4 = new HashMap<String, String>();
            Map<String, String> inMap5 = new HashMap<String, String>();
            Map<String, String> inMap6 = new HashMap<String, String>();
            Map<String, String> inMap7 = new HashMap<String, String>();
            Map<String, String> inMap8 = new HashMap<String, String>();

            //starting
            inMap1.put("type", "0");
            inMap1.put("number", "");
            outMap.put("starting", inMap1);

            //highLight
            inMap2.put("type", "0");
            inMap2.put("number", "");
            outMap.put("highLight", inMap2);

            //sound
            inMap3.put("type", "0");
            inMap3.put("number", "");
            outMap.put("sound", inMap3);

            //noElevation
            inMap4.put("type", "0");
            inMap4.put("number", "");
            outMap.put("noElevation", inMap4);

            //mute
            inMap5.put("type", "0");
            inMap5.put("number", "");
            outMap.put("mute", inMap5);

            //alwaysLight
            inMap6.put("type", "0");
            inMap6.put("number", "");
            outMap.put("alwaysLight", inMap6);

            //resetName
            inMap7.put("type", "0");
            inMap7.put("number", "");
            outMap.put("resetName", inMap7);

            //sendLocation
            inMap8.put("type", "0");
            inMap8.put("number", "");
            outMap.put("sendLocation", inMap8);

            handExamInfoModel.setMap(outMap);
        } else {
            //不为空

            handExamList = examInfoMapper.getAllHandExam();

            for (String handQuestionCode : arr) {
                for (HandExamStore handExamStore : handExamList) {
                    if (handExamStore.getQuestionCode().equals(handQuestionCode)) {
                        handExamInfoList.add(handExamStore);
                    }
                }
            }

            //处理封装操作题
            if (handExamInfoList.size() > 0) {

                //保存操作题答案
                int num = handExamInfoList.size();
                String handResult = "";
                for (int i = 0; i < num; i++) {
                    handResult += "true;";
                }

                int handSaveTag = examInfoMapper.updateHandExam(handResult, examId);

                if (handSaveTag == 1) {
                    System.out.println(examId + "操作题答案保存成功");
                    handExamInfoModel.setHandSaveFlag(true);
                } else {
                    System.out.println(examId + "操作题答案保存失败");
                    handExamInfoModel.setHandSaveFlag(false);

                    return handExamInfoModel;
                }

                List<String> examNameList = new ArrayList<String>();
                examNameList = examInfoMapper.getAllHandExamName();

                for (HandExamStore handExamStore : handExamInfoList) {//只有两个-- 开机、屏幕设置为高亮
                    for (String questionName : examNameList) {
                        if (handExamStore.getQuestionName().equals(questionName)) {

                            startQuestionNum++;//题号+1

                            if (questionName.equals("开机")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("starting", inMap);
                            } else if (questionName.equals("屏幕设置为高亮")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));

                                outMap.put("highLight", inMap);
                            } else if (questionName.equals("音量全开")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("sound", inMap);
                            } else if (questionName.equals("定位设置为无高程")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("noElevation", inMap);
                            } else if (questionName.equals("音量设置为静音")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("mute", inMap);
                            } else if (questionName.equals("屏幕设置为常亮")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("alwaysLight", inMap);
                            } else if (questionName.equals("将所在位置坐标存为路标，命名为站立点")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("resetName", inMap);
                            } else if (questionName.equals("向指挥机127747发送位置报告")) {
                                Map<String, String> inMap = new HashMap<String, String>();
                                inMap.put("type", "1");
                                inMap.put("number", String.valueOf(startQuestionNum));
                                outMap.put("sendLocation", inMap);
                            } else {
                                continue;
                            }
                        }
                    }
                }

                //去除显示的题
                for (HandExamStore handExamStore : handExamInfoList) {
                    if (examNameList.contains(handExamStore.getQuestionName())) {
                        examNameList.remove(handExamStore.getQuestionName());
                    }
                }

                //将不显示的题置空
                for (String questionName : examNameList) {
                    if (questionName.equals("开机")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("starting", inMap);
                    } else if (questionName.equals("屏幕设置为高亮")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");

                        outMap.put("highLight", inMap);
                    } else if (questionName.equals("音量全开")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("sound", inMap);
                    } else if (questionName.equals("定位设置为无高程")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("noElevation", inMap);
                    } else if (questionName.equals("音量设置为静音")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("mute", inMap);
                    } else if (questionName.equals("屏幕设置为常亮")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("alwaysLight", inMap);
                    } else if (questionName.equals("将所在位置坐标存为路标，命名为站立点")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("resetName", inMap);
                    } else if (questionName.equals("向指挥机127747发送位置报告")) {
                        Map<String, String> inMap = new HashMap<String, String>();
                        inMap.put("type", "0");
                        inMap.put("number", "");
                        outMap.put("sendLocation", inMap);
                    } else {
                        continue;
                    }
                }

                //handExamModel.setObject(outMap);
                handExamInfoModel.setMap(outMap);
            }
        }

        return handExamInfoModel;
    }

    //查当前考试
    public Exam getExamById(String examId) {

        Exam exam = examInfoMapper.getExam(examId);
        return exam;
    }

    /**
     * 处理操作题
     *
     * @param arr:操作题号数组
     * @param startQuestionNum：题号
     * @return 操作题的集合
     * by gyd
     */
    public Map<String, Map> getHandExam1(String arr[], int startQuestionNum, String examId) {

        List<HandExamStore> handExamList = new ArrayList<HandExamStore>();
        handExamList = examInfoMapper.getAllHandExam();
        Map<String, Map> flagData = new HashMap<String, Map>();
        Map<String, String> typeData1 = new HashMap<String, String>();
        Map<String, String> typeData2 = new HashMap<String, String>();
        Map<String, String> typeData3 = new HashMap<String, String>();
        Map<String, String> typeData4 = new HashMap<String, String>();
        Map<String, String> typeData5 = new HashMap<String, String>();
        Map<String, String> typeData6 = new HashMap<String, String>();
        typeData1.put("type", "1");
        typeData1.put("number", "1");
        flagData.put("starting", typeData1);
        typeData2.put("type", "0");
        typeData2.put("number", "");
        flagData.put("highLight", typeData2);
        typeData3.put("type", "0");
        typeData3.put("number", "");
        flagData.put("sound", typeData3);
        typeData4.put("type", "0");
        typeData4.put("number", "");
        flagData.put("noElevation", typeData4);
        typeData5.put("type", "0");
        typeData5.put("number", "");
        flagData.put("mute", typeData5);
        typeData6.put("type", "0");
        typeData6.put("number", "");
        flagData.put("alwaysLight", typeData6);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].equals("1.2")) {
                flagData.get("highLight").put("type", "1");
                flagData.get("highLight").put("number", i + 1);
            }
            if (arr[i].equals("1.3")) {
                flagData.get("sound").put("type", "1");
                flagData.get("sound").put("number", i + 1);
            }
            if (arr[i].equals("1.4")) {
                flagData.get("noElevation").put("type", "1");
                flagData.get("noElevation").put("number", i + 1);
            }
            if (arr[i].equals("1.5")) {
                flagData.get("mute").put("type", "1");
                flagData.get("mute").put("number", i + 1);
            }
            if (arr[i].equals("1.6")) {
                flagData.get("alwaysLight").put("type", "1");
                flagData.get("alwaysLight").put("number", i + 1);
            }
        }

        return flagData;
    }

    /**
     * 获取手持机操作题
     *
     * @param exam：考试题
     * @param questionNum：题号
     * @return 手持机考试题的实体类
     * by gyd
     */
    public MobileExamModel getMobileExam(Exam exam, int questionNum) {

        MobileExamModel mobileExamModel = new MobileExamModel();

        MobileOutModel mobileOutModel = new MobileOutModel();

        Map<String, MobileInModel> map = new LinkedHashMap<String, MobileInModel>();

        MobileInModel mobile1 = new MobileInModel();//本机ID
        //mobile1.setType();

        MobileInModel mobile2 = new MobileInModel();//通信等级
        MobileInModel mobile3 = new MobileInModel();//服务频度
        MobileInModel mobile4 = new MobileInModel();//序列号

        MobileInModel RDDDModelX = new MobileInModel();//大地坐标经度X
        MobileInModel RDDDModelY = new MobileInModel();//大地坐标纬度Y
        MobileInModel RDDDModelZ = new MobileInModel();//大地坐标纬度Z

        MobileInModel RNDDModelX = new MobileInModel();//大地坐标经度X
        MobileInModel RNDDModelY = new MobileInModel();//大地坐标纬度Y
        MobileInModel RNDDModelZ = new MobileInModel();//大地坐标纬度Z

        MobileInModel RDDDModelX2 = new MobileInModel();//大地坐标经度X
        MobileInModel RDDDModelY2 = new MobileInModel();//大地坐标纬度Y
        MobileInModel RDDDModelZ2 = new MobileInModel();//大地坐标纬度Z

        MobileInModel RNDDModelX2 = new MobileInModel();//大地坐标经度X
        MobileInModel RNDDModelY2 = new MobileInModel();//大地坐标纬度Y
        MobileInModel RNDDModelZ2 = new MobileInModel();//大地坐标纬度Z

        //--------------------------

        MobileInModel RDGSModelX = new MobileInModel();//高斯坐标经度X  RD
        MobileInModel RDGSModelY = new MobileInModel();//高斯坐标纬度Y  RD
        MobileInModel RDGSModelZ = new MobileInModel();//高斯坐标高度Z  RD

        MobileInModel RNGSModelX = new MobileInModel();//高斯坐标经度X  RN
        MobileInModel RNGSModelY = new MobileInModel();//高斯坐标纬度Y  RN
        MobileInModel RNGSModelZ = new MobileInModel();//高斯坐标高度Z  RN

        MobileInModel RDGSModelX2 = new MobileInModel();//高斯坐标经度X  RD
        MobileInModel RDGSModelY2 = new MobileInModel();//高斯坐标纬度Y  RD
        MobileInModel RDGSModelZ2 = new MobileInModel();//高斯坐标高度Z  RD

        MobileInModel RNGSModelX2 = new MobileInModel();//高斯坐标经度X  RN
        MobileInModel RNGSModelY2 = new MobileInModel();//高斯坐标纬度Y  RN
        MobileInModel RNGSModelZ2 = new MobileInModel();//高斯坐标高度Z  RN

        //-----------------------------

        MobileInModel RDKJModelX = new MobileInModel();//空间直角经度X
        MobileInModel RDKJModelY = new MobileInModel();//空间直角纬度Y
        MobileInModel RDKJModelZ = new MobileInModel();//空间直角高度Z

        MobileInModel RNKJModelX = new MobileInModel();//空间直角经度X
        MobileInModel RNKJModelY = new MobileInModel();//空间直角纬度Y
        MobileInModel RNKJModelZ = new MobileInModel();//空间直角高度Z

        MobileInModel RDKJModelX2 = new MobileInModel();//空间直角经度X
        MobileInModel RDKJModelY2 = new MobileInModel();//空间直角纬度Y
        MobileInModel RDKJModelZ2 = new MobileInModel();//空间直角高度Z

        MobileInModel RNKJModelX2 = new MobileInModel();//空间直角经度X
        MobileInModel RNKJModelY2 = new MobileInModel();//空间直角纬度Y
        MobileInModel RNKJModelZ2 = new MobileInModel();//空间直角高度Z

        //-----------------------------

        MobileInModel RDMKTModelX = new MobileInModel();//墨卡托经度X
        MobileInModel RDMKTModelY = new MobileInModel();//墨卡托纬度Y
        MobileInModel RDMKTModelZ = new MobileInModel();//墨卡托高度Z

        MobileInModel RNMKTModelX = new MobileInModel();//墨卡托经度X
        MobileInModel RNMKTModelY = new MobileInModel();//墨卡托纬度Y
        MobileInModel RNMKTModelZ = new MobileInModel();//墨卡托高度Z

        MobileInModel RDMKTModelX2 = new MobileInModel();//墨卡托经度X
        MobileInModel RDMKTModelY2 = new MobileInModel();//墨卡托纬度Y
        MobileInModel RDMKTModelZ2 = new MobileInModel();//墨卡托高度Z

        MobileInModel RNMKTModelX2 = new MobileInModel();//墨卡托经度X
        MobileInModel RNMKTModelY2 = new MobileInModel();//墨卡托纬度Y
        MobileInModel RNMKTModelZ2 = new MobileInModel();//墨卡托高度Z

        String gsX = "3784560." + String.valueOf(getRandomCode(3));
        String gsY = "19301334." + String.valueOf(getRandomCode(3));
        String gsZ = String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3));

        String ddX = "118." + String.valueOf(getRandomCode(3));
        String ddY = "39." + String.valueOf(getRandomCode(2)) + String.valueOf(getRandomCode(2)) + "." + String.valueOf(getRandomCode(3));
        String ddZ = String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3));

//
        String mktX = String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3));
        String mktY = String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3));
        String mktZ = String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(2));

        String kjX = "-" + String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3));
        String kjY = String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3));
        String kjZ = String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(2));

        if (exam != null) {

            mobileExamModel.setFillSaveFlag(false);

            String fillQuestion = exam.getFillBlankExamQuestion();

            List<String> questNumList = examInfoMapper.getAllFillExamNum();

            // 1.1获取题号
            if (fillQuestion == null || fillQuestion.equals("")) {
                //填空题为空---返回空还是？？
                //-----------
                //1.本机ID  六位生成
                mobile1.setType("1");
                mobile1.setValue(getRandomCode(6));
                mobile1.setNumber(String.valueOf(questionNum));
                map.put("bjid", mobile1);

                //2.通信等级  1~3
                mobile2.setType("1");
                mobile2.setValue(getRandomCode(1));
                mobile2.setNumber(String.valueOf(questionNum));
                map.put("txdj", mobile2);

                //3服务频度   5~60
                mobile3.setType("1");
                mobile3.setValue(getRandomCode(2));
                mobile3.setNumber(String.valueOf(questionNum));
                map.put("fwpd", mobile3);

                //4序列号  7位生成
                mobile4.setType("1");
                mobile4.setValue(getRandomCode(7));
                mobile4.setNumber(String.valueOf(questionNum));
                map.put("xlh", mobile4);

                //5RD--高斯坐标经度
                // 经度、纬度、高度
                RDGSModelX.setType("1");
                RDGSModelX.setValue(gsX);
                RDGSModelX.setNumber(String.valueOf(questionNum));

                RDGSModelY.setType("1");
                RDGSModelY.setValue(gsY);
                RDGSModelY.setNumber(String.valueOf(questionNum));

                RDGSModelZ.setType("1");
                RDGSModelZ.setValue(gsZ);
                RDGSModelZ.setNumber(String.valueOf(questionNum));

                map.put("RDSSgszbX", RDGSModelX);
                map.put("RDSSgszbY", RDGSModelY);
                map.put("RDSSgszbgc", RDGSModelZ);

                //6RN-高斯坐标经度
                // 经度、纬度、高度
                RNGSModelX.setType("1");
                RNGSModelX.setValue(gsX);
                RNGSModelX.setNumber(String.valueOf(questionNum));

                RNGSModelY.setType("1");
                RNGSModelY.setValue(gsY);
                RNGSModelY.setNumber(String.valueOf(questionNum));

                RNGSModelZ.setType("1");
                RNGSModelZ.setValue(gsZ);
                RNGSModelZ.setNumber(String.valueOf(questionNum));

                map.put("RNSSgszbX", RNGSModelX);
                map.put("RNSSgszbY", RNGSModelY);
                map.put("RNSSgszbgc", RNGSModelZ);

                //7大地坐标
                // 经度、纬度、高度
                RDDDModelX.setType("1");
                RDDDModelX.setValue(ddX);
                RDDDModelX.setNumber(String.valueOf(questionNum));

                RDDDModelY.setType("1");
                // 34°10'10.161"
                RDDDModelY.setValue(ddY);
                RDDDModelY.setNumber(String.valueOf(questionNum));

                RDDDModelZ.setType("1");
                RDDDModelZ.setValue(ddZ);
                RDDDModelZ.setNumber(String.valueOf(questionNum));

                map.put("RDSSddzbX", RDDDModelX);
                map.put("RDSSddzbY", RDDDModelY);
                map.put("RDSSddzbgc", RDDDModelZ);

                //8大地坐标
                // 经度、纬度、高度
                RNDDModelX.setType("1");
                RNDDModelX.setValue(ddX);
                RNDDModelX.setNumber(String.valueOf(questionNum));

                RNDDModelY.setType("1");
                // 34°10'10.161"
                RNDDModelY.setValue(ddY);
                RNDDModelY.setNumber(String.valueOf(questionNum));

                RNDDModelZ.setType("1");
                RNDDModelZ.setValue(ddZ);
                RNDDModelZ.setNumber(String.valueOf(questionNum));

                map.put("RNSSddzbX", RNDDModelX);
                map.put("RNSSddzbY", RNDDModelY);
                map.put("RNSSddzbgc", RNDDModelZ);

                //9空间直角经度
                // 经度、纬度、高度
                RDKJModelX.setType("1");
                RDKJModelX.setValue(kjX);
                RDKJModelX.setNumber(String.valueOf(questionNum));

                RDKJModelY.setType("1");
                RDKJModelY.setValue(kjY);
                RDKJModelY.setNumber(String.valueOf(questionNum));

                RDKJModelZ.setType("1");
                RDKJModelZ.setValue(kjZ);
                RDKJModelZ.setNumber(String.valueOf(questionNum));

                map.put("RDSSkjzbX", RDKJModelX);
                map.put("RDSSkjzbY", RDKJModelY);
                map.put("RDSSkjzbZ", RDKJModelZ);

                //10空间直角经度
                // 经度、纬度、高度
                RNKJModelX.setType("1");
                RNKJModelX.setValue(kjX);
                RNKJModelX.setNumber(String.valueOf(questionNum));

                RNKJModelY.setType("1");
                RNKJModelY.setValue(kjY);
                RNKJModelY.setNumber(String.valueOf(questionNum));

                RNKJModelZ.setType("1");
                RNKJModelZ.setValue(kjZ);
                RNKJModelZ.setNumber(String.valueOf(questionNum));

                map.put("RNSSkjzbX", RNKJModelX);
                map.put("RNSSkjzbY", RNKJModelY);
                map.put("RNSSkjzbZ", RNKJModelZ);

                //11墨卡托经度
                // 经度、纬度、高度
                RDMKTModelX.setType("1");
                RDMKTModelX.setValue(mktX);
                RDMKTModelX.setNumber(String.valueOf(questionNum));

                RDMKTModelY.setType("1");
                RDMKTModelY.setValue(mktY);
                RDMKTModelY.setNumber(String.valueOf(questionNum));

                RDMKTModelZ.setType("1");
                RDMKTModelZ.setValue(mktZ);
                RDMKTModelZ.setNumber(String.valueOf(questionNum));

                map.put("RDSSmktzbX", RDMKTModelX);
                map.put("RDSSmktzbY", RDMKTModelY);
                map.put("RDSSmktzbZ", RDMKTModelZ);

                //12墨卡托经度
                // 经度、纬度、高度
                RNMKTModelX.setType("1");
                RNMKTModelX.setValue(mktX);
                RNMKTModelX.setNumber(String.valueOf(questionNum));

                RNMKTModelY.setType("1");
                RNMKTModelY.setValue(mktY);
                RNMKTModelY.setNumber(String.valueOf(questionNum));

                RNMKTModelZ.setType("1");
                RNMKTModelZ.setValue(mktZ);
                RNMKTModelZ.setNumber(String.valueOf(questionNum));

                map.put("RNSSmktzbX", RNMKTModelX);
                map.put("RNSSmktzbY", RNMKTModelY);
                map.put("RNSSmktzbZ", RNMKTModelZ);

                mobileExamModel.setMap(map);
                mobileExamModel.setFillSaveFlag(true);

                //-----------
                return mobileExamModel;
            } else {
                //不为空
                String[] fillCodeArr = fillQuestion.split(";");//"2.1;2.2;2.5;2.6"

                //获取题号2.1后面的  1
                List<String> questionList = new ArrayList<String>();

                for (String questionCode : fillCodeArr) {//2.1  2.2  2.5   2.6
                    if (questionCode == null || questionCode.equals("")) {
                        //题号为空
                        continue;
                    } else {
                        String[] questionNumArr = questionCode.split("\\.");//2 1
                        questionList.add(questionNumArr[1]);// 1,2,5,6
                    }
                }

                //1.2填充题的内容
                if (fillCodeArr.length > 0) {

                    String fillResult = "";

                    for (String num : fillCodeArr) {

                        questionNum++;
                        FillBlankExamStore fillBlankExamStore = examInfoMapper.getFillExamByCode(num);
                        MobileInModel mobile = new MobileInModel();

                        if (fillBlankExamStore.getFlag() == 1) {
                            //有标准答案
                            mobile.setType("1");
                            mobile.setValue(fillBlankExamStore.getResult());
                            mobile.setNumber(String.valueOf(questionNum));
                            //map.put("bjid", mobile);

                            fillResult += mobile.getValue() + ";";
                        } else {
                            //无标准答案
                            if (num.equals("2.1")) {
                                //1本机ID  六位生成
                                mobile1.setType("1");
                                mobile1.setValue(getRandomCode(6));
                                mobile1.setNumber(String.valueOf(questionNum));
                                map.put("bjid", mobile1);

                                fillResult += mobile1.getValue() + ";";
                            } else if (num.equals("2.2")) {
                                //2通信等级  1~3
                                mobile2.setType("1");
                                mobile2.setValue(getRandomCode(1));
                                mobile2.setNumber(String.valueOf(questionNum));
                                map.put("txdj", mobile2);

                                fillResult += mobile2.getValue() + ";";
                            } else if (num.equals("2.3")) {
                                //3服务频度   5~60
                                mobile3.setType("1");
                                mobile3.setValue(getRandomCode(2));
                                mobile3.setNumber(String.valueOf(questionNum));
                                map.put("fwpd", mobile3);

                                fillResult += mobile3.getValue() + ";";
                            } else if (num.equals("2.4")) {
                                //4序列号  7位生成
                                mobile4.setType("1");
                                mobile4.setValue(getRandomCode(7));
                                mobile4.setNumber(String.valueOf(questionNum));
                                map.put("xlh", mobile4);

                                fillResult += mobile4.getValue() + ";";
                            } else if (num.equals("2.5")) {
                                //5RD--高斯坐标经度
                                // 经度、纬度、高度
                                RDGSModelX.setType("1");
                                RDGSModelX.setValue(gsX);
                                RDGSModelX.setNumber(String.valueOf(questionNum));

                                RDGSModelY.setType("1");
                                RDGSModelY.setValue(gsY);
                                RDGSModelY.setNumber(String.valueOf(questionNum));

                                RDGSModelZ.setType("1");
                                RDGSModelZ.setValue(gsZ);
                                RDGSModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RDSSgszbX", RDGSModelX);
                                map.put("RDSSgszbY", RDGSModelY);
                                map.put("RDSSgszbgc", RDGSModelZ);

                                fillResult += RDGSModelX.getValue() + ",";
                                fillResult += RDGSModelY.getValue() + ",";
                                fillResult += RDGSModelZ.getValue() + ";";
                            } else if (num.equals("2.51")) {
                                //6RN-高斯坐标经度
                                // 经度、纬度、高度
                                RNGSModelX.setType("1");
                                RNGSModelX.setValue(gsX);
                                RNGSModelX.setNumber(String.valueOf(questionNum));

                                RNGSModelY.setType("1");
                                RNGSModelY.setValue(gsY);
                                RNGSModelY.setNumber(String.valueOf(questionNum));

                                RNGSModelZ.setType("1");
                                RNGSModelZ.setValue(gsZ);
                                RNGSModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RNSSgszbX", RNGSModelX);
                                map.put("RNSSgszbY", RNGSModelY);
                                map.put("RNSSgszbgc", RNGSModelZ);

                                fillResult += RNGSModelX.getValue() + ",";
                                fillResult += RNGSModelY.getValue() + ",";
                                fillResult += RNGSModelZ.getValue() + ";";
                            } else if (num.equals("2.6")) {
                                //7大地坐标
                                // 经度、纬度、高度
                                RDDDModelX.setType("1");
                                RDDDModelX.setValue(ddX);
                                RDDDModelX.setNumber(String.valueOf(questionNum));

                                RDDDModelY.setType("1");
                                // 34°10'10.161"
                                RDDDModelY.setValue(ddY);
                                RDDDModelY.setNumber(String.valueOf(questionNum));

                                RDDDModelZ.setType("1");
                                RDDDModelZ.setValue(ddZ);
                                RDDDModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RDSSddzbX", RDDDModelX);
                                map.put("RDSSddzbY", RDDDModelY);
                                map.put("RDSSddzbgc", RDDDModelZ);

                                fillResult += RDDDModelX.getValue() + ",";
                                fillResult += RDDDModelY.getValue() + ",";
                                fillResult += RDDDModelZ.getValue() + ";";
                            } else if (num.equals("2.61")) {
                                //8大地坐标
                                // 经度、纬度、高度
                                RNDDModelX.setType("1");
                                RNDDModelX.setValue(ddX);
                                RNDDModelX.setNumber(String.valueOf(questionNum));

                                RNDDModelY.setType("1");
                                // 34°10'10.161"
                                RNDDModelY.setValue(ddY);
                                RNDDModelY.setNumber(String.valueOf(questionNum));

                                RNDDModelZ.setType("1");
                                RNDDModelZ.setValue(ddZ);
                                RNDDModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RNSSddzbX", RNDDModelX);
                                map.put("RNSSddzbY", RNDDModelY);
                                map.put("RNSSddzbgc", RNDDModelZ);

                                fillResult += RNDDModelX.getValue() + ",";
                                fillResult += RNDDModelY.getValue() + ",";
                                fillResult += RNDDModelZ.getValue() + ";";
                            } else if (num.equals("2.7")) {
                                //9空间直角经度
                                // 经度、纬度、高度
                                RDKJModelX.setType("1");
                                RDKJModelX.setValue(kjX);
                                RDKJModelX.setNumber(String.valueOf(questionNum));

                                RDKJModelY.setType("1");
                                RDKJModelY.setValue(kjY);
                                RDKJModelY.setNumber(String.valueOf(questionNum));

                                RDKJModelZ.setType("1");
                                RDKJModelZ.setValue(kjZ);
                                RDKJModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RDSSkjzbX", RDKJModelX);
                                map.put("RDSSkjzbY", RDKJModelY);
                                map.put("RDSSkjzbZ", RDKJModelZ);

                                fillResult += RDKJModelX.getValue() + ",";
                                fillResult += RDKJModelY.getValue() + ",";
                                fillResult += RDKJModelZ.getValue() + ";";
                            } else if (num.equals("2.71")) {
                                //10空间直角经度
                                // 经度、纬度、高度
                                RNKJModelX.setType("1");
                                RNKJModelX.setValue(kjX);
                                RNKJModelX.setNumber(String.valueOf(questionNum));

                                RNKJModelY.setType("1");
                                RNKJModelY.setValue(kjY);
                                RNKJModelY.setNumber(String.valueOf(questionNum));

                                RNKJModelZ.setType("1");
                                RNKJModelZ.setValue(kjZ);
                                RNKJModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RNSSkjzbX", RNKJModelX);
                                map.put("RNSSkjzbY", RNKJModelY);
                                map.put("RNSSkjzbZ", RNKJModelZ);

                                fillResult += RNKJModelX.getValue() + ",";
                                fillResult += RNKJModelY.getValue() + ",";
                                fillResult += RNKJModelZ.getValue() + ";";
                            } else if (num.equals("2.8")) {
                                //11墨卡托经度
                                // 经度、纬度、高度
                                RDMKTModelX.setType("1");
                                RDMKTModelX.setValue(mktX);
                                RDMKTModelX.setNumber(String.valueOf(questionNum));

                                RDMKTModelY.setType("1");
                                RDMKTModelY.setValue(mktY);
                                RDMKTModelY.setNumber(String.valueOf(questionNum));

                                RDMKTModelZ.setType("1");
                                RDMKTModelZ.setValue(mktZ);
                                RDMKTModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RDSSmktzbX", RDMKTModelX);
                                map.put("RDSSmktzbY", RDMKTModelY);
                                map.put("RDSSmktzbZ", RDMKTModelZ);

                                fillResult += RDMKTModelX.getValue() + ",";
                                fillResult += RDMKTModelY.getValue() + ",";
                                fillResult += RDMKTModelZ.getValue() + ";";
                            } else if (num.equals("2.81")) {
                                //12墨卡托经度
                                // 经度、纬度、高度
                                RNMKTModelX.setType("1");
                                RNMKTModelX.setValue(mktX);
                                RNMKTModelX.setNumber(String.valueOf(questionNum));

                                RNMKTModelY.setType("1");
                                RNMKTModelY.setValue(mktY);
                                RNMKTModelY.setNumber(String.valueOf(questionNum));

                                RNMKTModelZ.setType("1");
                                RNMKTModelZ.setValue(mktZ);
                                RNMKTModelZ.setNumber(String.valueOf(questionNum));

                                map.put("RNSSmktzbX", RNMKTModelX);
                                map.put("RNSSmktzbY", RNMKTModelY);
                                map.put("RNSSmktzbZ", RNMKTModelZ);

                                fillResult += RNMKTModelX.getValue() + ",";
                                fillResult += RNMKTModelY.getValue() + ",";
                                fillResult += RNMKTModelZ.getValue() + ";";//  111,222,333;123,111,222;
                            } else {

                            }
                        }
                    }

                    //保存填空题到数据库
                    int fillSaveTag = examInfoMapper.updateFillExam(fillResult, exam.getExamId());
                    boolean fillSaveFlag = false;

                    if (fillSaveTag == 1) {
                        System.out.println("保存填空题成功");
                        fillSaveFlag = true;
                        mobileExamModel.setMap(map);
                        mobileExamModel.setFillSaveFlag(true);
                    } else {
                        System.out.println("保存填空题失败");
                        mobileExamModel.setFillSaveFlag(false);
                    }
                }

                //将不是考试题的题也加进去，内容置空
                //获取题号
                for (String num : questionList) {
                    if (questNumList.contains(num)) {
                        questNumList.remove(num);
                    }
                }

                //填充题的内容
                for (String num : questNumList) {
                    if (num.equals("1")) {
                        //本机ID  六位生成
                        mobile1.setType("0");
                        mobile1.setValue(getRandomCode(6));
                        mobile1.setNumber("");
                        map.put("bjid", mobile1);
                    }
                    if (num.equals("2")) {
                        //通信等级  1~3
                        mobile2.setType("0");
                        mobile2.setValue(getRandomCode(1));
                        mobile2.setNumber("");
                        map.put("txdj", mobile2);
                    }
                    if (num.equals("3")) {
                        //服务频度   5~60
                        mobile3.setType("0");
                        mobile3.setValue(getRandomCode(2));
                        mobile3.setNumber("");
                        map.put("fwpd", mobile3);
                    }
                    if (num.equals("4")) {
                        //序列号  7位生成
                        mobile4.setType("0");
                        mobile4.setValue(getRandomCode(7));
                        mobile4.setNumber("");
                        map.put("xlh", mobile4);
                    }
                    if (num.equals("5")) {
                        //高斯坐标经度   RD
                        // 经度、纬度、高度
                        RDGSModelX2.setType("0");
                        RDGSModelX2.setValue("");
                        RDGSModelX2.setNumber("");

                        RDGSModelY2.setType("0");
                        RDGSModelY2.setValue("");
                        RDGSModelY2.setNumber("");

                        RDGSModelZ2.setType("0");
                        RDGSModelZ2.setValue("");
                        RDGSModelZ2.setNumber("");

                        map.put("RDSSgszbX", RDGSModelX2);
                        map.put("RDSSgszbY", RDGSModelY2);
                        map.put("RDSSgszbgc", RDGSModelZ2);
                    }
                    if (num.equals("51")) {
                        //RN--高斯坐标经度   RN
                        // 经度、纬度、高度
                        RNGSModelX2.setType("0");
                        RNGSModelX2.setValue("");
                        RNGSModelX2.setNumber("");

                        RNGSModelY2.setType("0");
                        RNGSModelY2.setValue("");
                        RNGSModelY2.setNumber("");

                        RNGSModelZ2.setType("0");
                        RNGSModelZ2.setValue("");
                        RNGSModelZ2.setNumber("");

                        map.put("RNSSgszbX", RNGSModelX2);
                        map.put("RNSSgszbY", RNGSModelY2);
                        map.put("RNSSgszbgc", RNGSModelZ2);
                    }
                    if (num.equals("6")) {
                        //大地坐标  RD
                        // 经度、纬度、高度
                        RDDDModelX2.setType("0");
                        RDDDModelX2.setValue("");
                        RDDDModelX2.setNumber("");

                        RDDDModelY2.setType("0");
                        // 34°10'10.161"
                        RDDDModelY2.setValue("");
                        RDDDModelY2.setNumber("");

                        RDDDModelZ2.setType("0");
                        RDDDModelZ2.setValue("");
                        RDDDModelZ2.setNumber("");

                        map.put("RDSSddzbX", RDDDModelX2);
                        map.put("RDSSddzbY", RDDDModelY2);
                        map.put("RDSSddzbgc", RDDDModelZ2);
                    }
                    if (num.equals("61")) {
                        //大地坐标  RN
                        // 经度、纬度、高度
                        RNDDModelX2.setType("0");
                        RNDDModelX2.setValue("");
                        RNDDModelX2.setNumber("");

                        RNDDModelY2.setType("0");
                        // 34°10'10.161"
                        RNDDModelY2.setValue("");
                        RNDDModelY2.setNumber("");

                        RNDDModelZ2.setType("0");
                        RDDDModelZ2.setValue("");
                        RDDDModelZ2.setNumber("");

                        map.put("RNSSddzbX", RNDDModelX2);
                        map.put("RNSSddzbY", RNDDModelX2);
                        map.put("RNSSddzbgc", RNDDModelX2);
                    }
                    if (num.equals("7")) {
                        //空间直角经度  RD
                        // 经度、纬度、高度
                        RDKJModelX2.setType("0");
                        RDKJModelX2.setValue("");
                        RDKJModelX2.setNumber("");

                        RDKJModelY2.setType("0");
                        RDKJModelY2.setValue("");
                        RDKJModelY2.setNumber("");

                        RDKJModelZ2.setType("0");
                        RDKJModelZ2.setValue("");
                        RDKJModelZ2.setNumber("");

                        map.put("RDSSkjzbX", RDKJModelX2);
                        map.put("RDSSkjzbY", RDKJModelY2);
                        map.put("RDSSkjzbZ", RDKJModelZ2);
                    }
                    if (num.equals("71")) {
                        //空间直角经度  RN
                        // 经度、纬度、高度
                        RNKJModelX2.setType("0");
                        RNKJModelX2.setValue("");
                        RNKJModelX2.setNumber("");

                        RNKJModelY2.setType("0");
                        RNKJModelY2.setValue("");
                        RNKJModelY2.setNumber("");

                        RNKJModelZ2.setType("0");
                        RNKJModelZ2.setValue("");
                        RNKJModelZ2.setNumber("");

                        map.put("RNSSkjzbX", RNKJModelX2);
                        map.put("RNSSkjzbY", RNKJModelY2);
                        map.put("RNSSkjzbZ", RNKJModelZ2);
                    }
                    if (num.equals("8")) {
                        //墨卡托经度
                        // 经度、纬度、高度
                        RDMKTModelX2.setType("0");
                        RDMKTModelY2.setValue("");
                        RDMKTModelZ2.setNumber("");

                        RDMKTModelX2.setType("0");
                        RDMKTModelY2.setValue("");
                        RDMKTModelZ2.setNumber("");

                        RDMKTModelX2.setType("0");
                        RDMKTModelY2.setValue("");
                        RDMKTModelZ2.setNumber("");

                        map.put("RDSSmktzbX", RDMKTModelX2);
                        map.put("RDSSmktzbY", RDMKTModelY2);
                        map.put("RDSSmktzbZ", RDMKTModelZ2);
                    }
                    if (num.equals("81")) {
                        //墨卡托经度
                        // 经度、纬度、高度
                        RNMKTModelX2.setType("0");
                        RNMKTModelX2.setValue("");
                        RNMKTModelX2.setNumber("");

                        RNMKTModelY2.setType("0");
                        RNMKTModelY2.setValue("");
                        RNMKTModelY2.setNumber("");

                        RNMKTModelZ2.setType("0");
                        RNMKTModelZ2.setValue("");
                        RNMKTModelZ2.setNumber("");

                        map.put("RNSSmktzbX", RDMKTModelX2);
                        map.put("RNSSmktzbY", RDMKTModelY2);
                        map.put("RNSSmktzbZ", RDMKTModelZ2);
                    }
                }
            }
        }

        return mobileExamModel;
    }

    //根据题号获取手持机题
    public Map<String, MobileInModel> getMobileExamByCode(String[] fillArr, int questionNum) {

        MobileOutModel mobileOutModel = new MobileOutModel();

        Map<String, MobileInModel> map = new LinkedHashMap<String, MobileInModel>();

        MobileInModel mobile1 = new MobileInModel();//本机ID
        //mobile1.setType();

        MobileInModel mobile2 = new MobileInModel();//通信等级
        MobileInModel mobile3 = new MobileInModel();//服务频度
        MobileInModel mobile4 = new MobileInModel();//序列号

        MobileInModel mobile5 = new MobileInModel();//大地坐标经度X
        MobileInModel mobile6 = new MobileInModel();//大地坐标纬度Y
        MobileInModel mobile7 = new MobileInModel();//大地坐标纬度Z

        MobileInModel mobile55 = new MobileInModel();//大地坐标经度X
        MobileInModel mobile66 = new MobileInModel();//大地坐标纬度Y
        MobileInModel mobile77 = new MobileInModel();//大地坐标纬度Z

        MobileInModel mobile8 = new MobileInModel();//高斯坐标经度X
        MobileInModel mobile9 = new MobileInModel();//高斯坐标纬度Y
        MobileInModel mobile10 = new MobileInModel();//高斯坐标高度Z\

        MobileInModel mobile88 = new MobileInModel();//高斯坐标经度X
        MobileInModel mobile99 = new MobileInModel();//高斯坐标纬度Y
        MobileInModel mobile100 = new MobileInModel();//高斯坐标高度Z

        MobileInModel mobile11 = new MobileInModel();//空间直角经度X
        MobileInModel mobile12 = new MobileInModel();//空间直角纬度Y
        MobileInModel mobile13 = new MobileInModel();//空间直角高度Z

        MobileInModel mobile111 = new MobileInModel();//空间直角经度X
        MobileInModel mobile122 = new MobileInModel();//空间直角纬度Y
        MobileInModel mobile133 = new MobileInModel();//空间直角高度Z

        MobileInModel mobile14 = new MobileInModel();//墨卡托经度X
        MobileInModel mobile15 = new MobileInModel();//墨卡托纬度Y
        MobileInModel mobile16 = new MobileInModel();//墨卡托高度Z

        MobileInModel mobile144 = new MobileInModel();//墨卡托经度X
        MobileInModel mobile155 = new MobileInModel();//墨卡托纬度Y
        MobileInModel mobile166 = new MobileInModel();//墨卡托高度Z

        List<String> questionList = new ArrayList<String>();

        for (String questionCode : fillArr) {//2.1  2.2  2.5   2.6
            if (questionCode == null || questionCode.equals("")) {
                //题号为空
                continue;
            } else {

                questionList.add(questionCode);// 1,2,5,6
            }
        }

        //1.2填充题的内容
        if (questionList.size() > 0) {
            mobile1.setType("0");
            mobile1.setValue(getRandomCode(6));
            mobile1.setNumber("");
            mobile2.setType("0");
            mobile2.setValue(getRandomCode(1));
            mobile2.setNumber("");
            mobile3.setType("0");
            mobile3.setValue(getRandomCode(2));
            mobile3.setNumber("");
            mobile4.setType("0");
            mobile4.setValue(getRandomCode(7));
            mobile4.setNumber("");
            mobile8.setType("0");
            mobile8.setValue("3784560." + String.valueOf(getRandomCode(3)));
            mobile8.setNumber("");

            mobile9.setType("0");
            mobile9.setValue("19 301334." + String.valueOf(getRandomCode(3)));
            mobile9.setNumber("");

            mobile10.setType("0");
            mobile10.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
            mobile10.setNumber("");
            mobile88.setType("0");
            mobile88.setValue("3784560." + String.valueOf(getRandomCode(3)));
            mobile88.setNumber("");

            mobile99.setType("0");
            mobile99.setValue("19301334." + String.valueOf(getRandomCode(3)));
            mobile99.setNumber("");

            mobile100.setType("0");
            mobile100.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
            mobile100.setNumber("");
            mobile5.setType("0");
            mobile5.setValue("118." + String.valueOf(getRandomCode(3)));
            mobile5.setNumber("");

            mobile6.setType("0");
            // 34°10'10.161"
            mobile6.setValue("39." + String.valueOf(getRandomCode(2)) + String.valueOf(getRandomCode(2)) + "." + String.valueOf(getRandomCode(3)));
            mobile6.setNumber("");

            mobile7.setType("0");
            mobile7.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
            mobile7.setNumber("");

            mobile55.setType("0");
            mobile55.setValue("118." + String.valueOf(getRandomCode(3)));
            mobile55.setNumber("");

            mobile66.setType("0");
            // 34°10'10.161"
            mobile66.setValue("39." + String.valueOf(getRandomCode(2)) + String.valueOf(getRandomCode(2)) + "." + String.valueOf(getRandomCode(3)));
            mobile66.setNumber("");

            mobile77.setType("0");
            mobile77.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
            mobile77.setNumber("");
            mobile11.setType("0");
            mobile11.setValue("-" + String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile11.setNumber("");

            mobile12.setType("0");
            mobile12.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile12.setNumber("");

            mobile13.setType("0");
            mobile13.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(2)));
            mobile13.setNumber("");

            mobile111.setType("0");
            mobile111.setValue("-" + String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile111.setNumber("");

            mobile122.setType("0");
            mobile122.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile122.setNumber("");

            mobile133.setType("0");
            mobile133.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(2)));
            mobile133.setNumber("");
            mobile14.setType("0");
            mobile14.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile14.setNumber("");

            mobile15.setType("0");
            mobile15.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile15.setNumber("");

            mobile16.setType("0");
            mobile16.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(2)));
            mobile16.setNumber("");

            mobile144.setType("0");
            mobile144.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile144.setNumber("");

            mobile155.setType("0");
            mobile155.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
            mobile155.setNumber("");

            mobile166.setType("0");
            mobile166.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(2)));
            mobile166.setNumber("");

            for (String num : questionList) {

                questionNum++;

                if (num.equals("2.1")) {
                    //本机ID  六位生成

                    mobile1.setType("1");
                    mobile1.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.2")) {
                    //通信等级  1~3
                    mobile2.setType("1");
                    mobile2.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.3")) {
                    //服务频度   5~60
                    mobile3.setType("1");
                    mobile3.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.4")) {
                    //序列号  7位生成
                    mobile4.setType("1");
                    mobile4.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.5")) {
                    //高斯坐标经度
                    // 经度、纬度、高度
                    mobile8.setType("1");
                    mobile9.setType("1");
                    mobile10.setType("1");
                    mobile8.setNumber(String.valueOf(questionNum));
                    mobile9.setNumber(String.valueOf(questionNum));
                    mobile10.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.51")) {
                    //高斯坐标经度
                    // 经度、纬度、高度
                    mobile88.setType("1");
                    mobile99.setType("1");
                    mobile100.setType("1");
                    mobile88.setNumber(String.valueOf(questionNum));
                    mobile99.setNumber(String.valueOf(questionNum));
                    mobile100.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.6")) {
                    //大地坐标
                    // 经度、纬度、高度
                    mobile5.setType("1");
                    mobile6.setType("1");
                    mobile7.setType("1");
                    mobile5.setNumber(String.valueOf(questionNum));
                    mobile6.setNumber(String.valueOf(questionNum));
                    mobile7.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.61")) {
                    //大地坐标
                    // 经度、纬度、高度
                    mobile55.setType("1");
                    mobile66.setType("1");
                    mobile77.setType("1");
                    mobile55.setNumber(String.valueOf(questionNum));
                    mobile66.setNumber(String.valueOf(questionNum));
                    mobile77.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.7")) {
                    //空间直角经度
                    // 经度、纬度、高度
                    mobile11.setType("1");
                    mobile12.setType("1");
                    mobile13.setType("1");
                    mobile11.setNumber(String.valueOf(questionNum));
                    mobile12.setNumber(String.valueOf(questionNum));
                    mobile13.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.71")) {
                    //空间直角经度
                    // 经度、纬度、高度
                    mobile111.setType("1");
                    mobile122.setType("1");
                    mobile133.setType("1");
                    mobile111.setNumber(String.valueOf(questionNum));
                    mobile122.setNumber(String.valueOf(questionNum));
                    mobile133.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.8")) {
                    //墨卡托经度
                    // 经度、纬度、高度
                    mobile14.setType("1");
                    mobile15.setType("1");
                    mobile16.setType("1");
                    mobile14.setNumber(String.valueOf(questionNum));
                    mobile15.setNumber(String.valueOf(questionNum));
                    mobile16.setNumber(String.valueOf(questionNum));
                } else if (num.equals("2.81")) {
                    //墨卡托经度
                    // 经度、纬度、高度
                    mobile144.setType("1");
                    mobile155.setType("1");
                    mobile166.setType("1");
                    mobile144.setNumber(String.valueOf(questionNum));
                    mobile155.setNumber(String.valueOf(questionNum));
                    mobile166.setNumber(String.valueOf(questionNum));
                } else {

                }
            }
            map.put("bjid", mobile1);
            map.put("txdj", mobile2);
            map.put("fwpd", mobile3);
            map.put("xlh", mobile4);

            map.put("RDSSddzbX", mobile5);
            map.put("RNSSddzbX", mobile55);
            map.put("RDSSddzbY", mobile6);
            map.put("RNSSddzbY", mobile66);
            map.put("RDSSddzbgc", mobile7);
            map.put("RNSSddzbgc", mobile77);
            map.put("RDSSgszbX", mobile8);
            map.put("RNSSgszbX", mobile88);
            map.put("RDSSgszbY", mobile9);
            map.put("RNSSgszbY", mobile99);
            map.put("RDSSgszbgc", mobile10);
            map.put("RNSSgszbgc", mobile100);
            map.put("RDSSkjzbX", mobile11);
            map.put("RNSSkjzbX", mobile111);
            map.put("RDSSkjzbY", mobile12);
            map.put("RNSSkjzbY", mobile122);
            map.put("RDSSkjzbZ", mobile13);
            map.put("RNSSkjzbZ", mobile133);
            map.put("RDSSmktzbX", mobile14);
            map.put("RNSSmktzbX", mobile144);
            map.put("RDSSmktzbY", mobile15);
            map.put("RNSSmktzbY", mobile155);
            map.put("RDSSmktzbZ", mobile16);
            map.put("RNSSmktzbZ", mobile166);
        }
        return map;
    }

    //训练的手持机data
    public Map<String, MobileInModel1> getMobileExamByCodeTrain() {

        MobileOutModel mobileOutModel = new MobileOutModel();

        Map<String, MobileInModel1> map = new LinkedHashMap<String, MobileInModel1>();

        MobileInModel1 mobile1 = new MobileInModel1();//本机ID
        //mobile1.setType();

        MobileInModel1 mobile2 = new MobileInModel1();//通信等级
        MobileInModel1 mobile3 = new MobileInModel1();//服务频度
        MobileInModel1 mobile4 = new MobileInModel1();//序列号

        MobileInModel1 mobile5 = new MobileInModel1();//大地坐标经度X
        MobileInModel1 mobile6 = new MobileInModel1();//大地坐标纬度Y
        MobileInModel1 mobile7 = new MobileInModel1();//大地坐标纬度Z

        MobileInModel1 mobile55 = new MobileInModel1();//大地坐标经度X
        MobileInModel1 mobile66 = new MobileInModel1();//大地坐标纬度Y
        MobileInModel1 mobile77 = new MobileInModel1();//大地坐标纬度Z

        MobileInModel1 mobile8 = new MobileInModel1();//高斯坐标经度X
        MobileInModel1 mobile9 = new MobileInModel1();//高斯坐标纬度Y
        MobileInModel1 mobile10 = new MobileInModel1();//高斯坐标高度Z\

        MobileInModel1 mobile88 = new MobileInModel1();//高斯坐标经度X
        MobileInModel1 mobile99 = new MobileInModel1();//高斯坐标纬度Y
        MobileInModel1 mobile100 = new MobileInModel1();//高斯坐标高度Z

        MobileInModel1 mobile11 = new MobileInModel1();//空间直角经度X
        MobileInModel1 mobile12 = new MobileInModel1();//空间直角纬度Y
        MobileInModel1 mobile13 = new MobileInModel1();//空间直角高度Z

        MobileInModel1 mobile111 = new MobileInModel1();//空间直角经度X
        MobileInModel1 mobile122 = new MobileInModel1();//空间直角纬度Y
        MobileInModel1 mobile133 = new MobileInModel1();//空间直角高度Z

        MobileInModel1 mobile14 = new MobileInModel1();//墨卡托经度X
        MobileInModel1 mobile15 = new MobileInModel1();//墨卡托纬度Y
        MobileInModel1 mobile16 = new MobileInModel1();//墨卡托高度Z

        MobileInModel1 mobile144 = new MobileInModel1();//墨卡托经度X
        MobileInModel1 mobile155 = new MobileInModel1();//墨卡托纬度Y
        MobileInModel1 mobile166 = new MobileInModel1();//墨卡托高度Z      
        mobile1.setValue(getRandomCode(6));
        mobile2.setValue(getRandomCode(1));
        mobile3.setValue(getRandomCode(2));
        mobile4.setValue(getRandomCode(7));
        mobile8.setValue("3784560." + String.valueOf(getRandomCode(3)));
        mobile9.setValue("19301334." + String.valueOf(getRandomCode(3)));
        mobile10.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
        mobile88.setValue("3784560." + String.valueOf(getRandomCode(3)));
        mobile99.setValue("19301334." + String.valueOf(getRandomCode(3)));
        mobile100.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
        mobile5.setValue("118." + String.valueOf(getRandomCode(3)));
        // 34°10'10.161"
        mobile6.setValue("39." + String.valueOf(getRandomCode(2)) + String.valueOf(getRandomCode(2)) + "." + String.valueOf(getRandomCode(3)));
        mobile7.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
        mobile55.setValue("118." + String.valueOf(getRandomCode(3)));
        // 34°10'10.161"
        mobile66.setValue("39." + String.valueOf(getRandomCode(2)) + String.valueOf(getRandomCode(2)) + "." + String.valueOf(getRandomCode(3)));
        mobile77.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(3)));
        mobile11.setValue("-" + String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile12.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile13.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(2)));
        mobile111.setValue("-" + String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile122.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile133.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(2)));
        mobile14.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile15.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile16.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(2)));
        mobile144.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile155.setValue(String.valueOf(getRandomCode(7)) + "." + String.valueOf(getRandomCode(3)));
        mobile166.setValue(String.valueOf(getRandomCode(3)) + "." + String.valueOf(getRandomCode(2)));
        map.put("bjid", mobile1);
        map.put("txdj", mobile2);
        map.put("fwpd", mobile3);
        map.put("xlh", mobile4);
        map.put("RDSSddzbX", mobile5);
        map.put("RNSSddzbX", mobile55);
        map.put("RDSSddzbY", mobile6);
        map.put("RNSSddzbY", mobile66);
        map.put("RDSSddzbgc", mobile7);
        map.put("RNSSddzbgc", mobile77);
        map.put("RDSSgszbX", mobile8);
        map.put("RNSSgszbX", mobile88);
        map.put("RDSSgszbY", mobile9);
        map.put("RNSSgszbY", mobile99);
        map.put("RDSSgszbgc", mobile10);
        map.put("RNSSgszbgc", mobile100);
        map.put("RDSSkjzbX", mobile11);
        map.put("RNSSkjzbX", mobile111);
        map.put("RDSSkjzbY", mobile12);
        map.put("RNSSkjzbY", mobile122);
        map.put("RDSSkjzbZ", mobile13);
        map.put("RNSSkjzbZ", mobile133);
        map.put("RDSSmktzbX", mobile14);
        map.put("RNSSmktzbX", mobile144);
        map.put("RDSSmktzbY", mobile15);
        map.put("RNSSmktzbY", mobile155);
        map.put("RDSSmktzbZ", mobile16);
        map.put("RNSSmktzbZ", mobile166);
        return map;
    }

    public List<TestDataExamModel> getFillbycode(String[] questionList, int questionNum, Map<String, MobileInModel1> fillmap) {

        List<TestDataExamModel> list = new ArrayList<TestDataExamModel>();
        for (String num : questionList) {

            questionNum++;
            TestDataExamModel model = new TestDataExamModel();
            HandExamStore handExam = examInfoMapper.getHandExamByCode1(num);
            if (num.equals("2.1")) {
                //本机ID  六位生成
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setResult(fillmap.get("bjid").getValue());
                model.setSelect("");
                model.setType("");
                model.setImg(null);
            } else if (num.equals("2.2")) {
                //通信等级  1~3
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setResult(fillmap.get("txdj").getValue());
                model.setType("");
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.3")) {
                //服务频度   5~60
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setResult(fillmap.get("fwpd").getValue());
                model.setType("");
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.4")) {
                //序列号  7位生成
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setResult(fillmap.get("xlh").getValue());
                model.setType("");
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.5")) {
                //高斯坐标经度
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RDSSgszbX").getValue() + "," + fillmap.get("RDSSgszbY").getValue()
                        + "," + fillmap.get("RDSSgszbgc").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.51")) {
                //高斯坐标经度
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RNSSgszbX").getValue() + "," + fillmap.get("RNSSgszbY").getValue()
                        + "," + fillmap.get("RNSSgszbgc").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.6")) {
                //大地坐标
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RDSSddzbX").getValue() + "," + fillmap.get("RDSSddzbY").getValue()
                        + "," + fillmap.get("RDSSddzbgc").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.61")) {
                //大地坐标
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RNSSddzbX").getValue() + "," + fillmap.get("RNSSddzbY").getValue()
                        + "," + fillmap.get("RNSSddzbgc").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.7")) {
                //空间直角经度
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RDSSkjzbX").getValue() + "," + fillmap.get("RDSSkjzbY").getValue()
                        + "," + fillmap.get("RDSSkjzbZ").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.71")) {
                //空间直角经度
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RNSSkjzbX").getValue() + "," + fillmap.get("RNSSkjzbY").getValue()
                        + "," + fillmap.get("RNSSkjzbZ").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.8")) {
                //墨卡托经度
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RDSSmktzbX").getValue() + "," + fillmap.get("RDSSmktzbY").getValue()
                        + "," + fillmap.get("RDSSmktzbZ").getValue());
                model.setSelect("");
                model.setImg(null);
            } else if (num.equals("2.81")) {
                //墨卡托经度
                // 经度、纬度、高度
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setType("");
                model.setResult(fillmap.get("RNSSmktzbX").getValue() + "," + fillmap.get("RNSSmktzbY").getValue()
                        + "," + fillmap.get("RNSSmktzbZ").getValue());
                model.setSelect("");
                model.setImg(null);
            } else {
                model.setNumber("" + questionNum);
                model.setQuestion_code(num);
                model.setQuestion_name(handExam.getQuestionName());
                model.setTable("fill_blank_exam_store");
                model.setResult(handExam.getResult());
                model.setSelect("");
                model.setType("");
                model.setImg(null);
                if (handExam.getPassTableId() != null) {
                    model.setImg(PhotoUtil.sqlTophoto((String) handExam.getPassTableId()));
                }
            }
            list.add(model);
        }
        return list;
    }

    /**
     * 获取操作、填空题
     *
     * @param exam：考试题
     * @return
     */
    public List<HandExamPaperModel> getHandFillExam(Exam exam) {

        List<HandExamPaperModel> list = new ArrayList<HandExamPaperModel>();

        String handQuestion = exam.getHandExamQuestion();
        String fillQuestion = exam.getFillBlankExamQuestion();

        String handResult = exam.getHandExamResult();
        String fillResult = exam.getFillBlankExamResult();

        String[] handResultArr = handResult.split(";");
        String[] fillResultArr = fillResult.split(";");

        int questionNum = 0;

        //1.1操作题
        if (handQuestion == null || handQuestion.equals("")) {
            //操作题答案为空
            //return list;
        } else {
            String[] handArr = handQuestion.split(";");

            for (String examNum : handArr) {
                questionNum++;
                HandExamStore handExam = examInfoMapper.getHandExamByCode(examNum);
                if (handExam != null) {
                    HandExamPaperModel handModel = new HandExamPaperModel();
                    handModel.setTable("hand_exam_store");
                    handModel.setNumber(String.valueOf(questionNum));
                    handModel.setQuestion_code(handExam.getQuestionCode());
                    handModel.setQuestion_name(handExam.getQuestionName());
                    handModel.setGrade(handExam.getGrade());
                    handModel.setResult("true");
                    list.add(handModel);
                }
            }
        }
        //1.2 填空题
        if (fillQuestion == null || fillQuestion.equals("")) {
            //填空题答案为空
            //return list;
        } else {
            String[] fillArr = fillQuestion.split(";");
            for (int i = 0; i < fillArr.length; i++) {
                {
                    questionNum++;
                    FillBlankExamStore fillExam = examInfoMapper.getFillExamByCode(fillArr[i]);

                    if (fillExam != null) {

                        HandExamPaperModel handModel = new HandExamPaperModel();
                        handModel.setTable("fill_blank_exam_store");
                        handModel.setNumber(String.valueOf(questionNum));
                        handModel.setQuestion_code(fillExam.getQuestionCode());
                        handModel.setQuestion_name(fillExam.getQuestionName());
                        handModel.setGrade(fillExam.getGrade());
                        handModel.setResult(fillResultArr[i]);
                        list.add(handModel);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取通讯题
     *
     * @param exam:考试题
     * @param questionNum：题号 7-8
     * @return
     */
    public List<CommExamModel> getCommExam(Exam exam, int questionNum) {
        List<CommExamModel> list = new ArrayList<CommExamModel>();

        String commQuestion = exam.getCommExamQuestion();

        String commResult = exam.getCommExamResult();

        String[] commResultArr = commResult.split(";");

        //1.1操作题
        if (commQuestion == null || commQuestion.equals("")) {
            //操作题答案为空
            return list;
        } else {

            String[] commArr = commQuestion.split(";");
            List<CommExam> commExamStoreList = new ArrayList<CommExam>();
            for (String examNum : commArr) {
                questionNum++;

                CommExam commExam = examInfoMapper.getCommExamByCode(examNum);

                commExamStoreList.add(commExam);
                if (commExam != null) {

                    CommExamModel commModel = new CommExamModel();
                    commModel.setTable("comm_exam_store");
                    commModel.setNumber(String.valueOf(questionNum));
                    commModel.setQuestion_code(commExam.getQuestionCode());
                    commModel.setQuestion_name(commExam.getQuestionName());
                    commModel.setGrade(commExam.getGrade());
                    commModel.setResult(commExam.getResult());
                    if (commExam.getType() == 4) {
                        commModel.setType("普通通信题");
                    }
                    if (commExam.getType() == 5) {
                        commModel.setType("加密通信题");
                    }
                    if (commExam.getPassTableId() != null) {
                        commModel.setImg(PhotoUtil.sqlTophoto(commExam.getPassTableId()));
                    }

                    //handModel.setResult("true");
                    list.add(commModel);
                }
            }

            //填充result答案
          /*  for (int i = 0; i < commExamStoreList.size(); i++) {
                list.get(i).setResult(commExamStoreList.get(i).getResult());
            }*/

        }

        return list;
    }

    /**
     * 获取当前考试题
     *
     * @param id_number
     * @return by:gyd
     */
    public Exam getCurrentExam(String id_number, String subject) {

        Exam exam = new Exam();
        List<Exam> list = new ArrayList<Exam>();

        list = examInfoMapper.getCurrentExam(id_number, subject);

        if (list.size() > 0) {
            exam = list.get(0);
        }

        return exam;
    }

    /**
     * 获取当前科目的考试题
     *
     * @param subject
     * @return by:gyd
     */
    public Exam getExamBySub(String subject) {

        Exam exam = new Exam();
        List<Exam> list = new ArrayList<Exam>();

        list = examInfoMapper.selectExamBySub( subject);

        if (list.size() > 0) {
            exam = list.get(0);
        }

        return exam;
    }

    //获取一个学员未考过的试题
    public List<Exam> getCurrentExams(String id_number, String subject) {

        Exam exam = new Exam();
        List<Exam> list = new ArrayList<Exam>();

        list = examInfoMapper.getCurrentExam(id_number, subject);

        return list;
    }

    //获取一个学员待考数量，时间过了就不算待考了
    public List<Exam> getCurrentExams1(String id_number) {

        Exam exam = new Exam();
        List<Exam> list = new ArrayList<Exam>();

        list = examInfoMapper.getCurrentExam1(id_number);

        return list;
    }

    //获取一个学员已考过的试题
    public List<Exam> getAlreadyExam(String id_number) {

        Exam exam = new Exam();
        List<Exam> list = new ArrayList<Exam>();

        list = examInfoMapper.getAlreadyExam(id_number);

        return list;
    }

    /**
     * 手持机--生成随机数方法
     *
     * @param codeLength:数字的长度(1-7位)
     * @return by:gyd
     */
    public String getRandomCode(int codeLength) {

        int num = 0;

        if (codeLength > 0) {
            Random random = new Random();

            if (codeLength == 6) {
                num = random.nextInt(900000) + 100000;//899999+100000=999999
            } else if (codeLength == 4) {
                num = random.nextInt(9000) + 1000;//8999+1000=9999=1000-9999
            } else if (codeLength == 5) {
                num = random.nextInt(90000) + 10000;//8999+1000=9999=1000-9999
            } else if (codeLength == 2) {
                //5-60
                num = random.nextInt(56) + 5; //55+5
            } else if (codeLength == 3) {
                //5-60
                num = random.nextInt(900) + 100; //899+100=100-999
            } else if (codeLength == 1) {
                num = random.nextInt(3) + 1;//1~3
            } else if (codeLength == 7) {
                num = random.nextInt(9000000) + 1000000;
            } else {
                num = 0;
            }
        }

        return String.valueOf(num);
    }

    /**
     * 得分置为0
     *
     * @param examId
     * @return
     */
    public int initGrade(String examId) {

        int tag = 0;
        tag = examInfoMapper.setGrade(examId);

        return tag;
    }

    public Long sumBDExam(String idNumber, String time) {

        Long sum = 0L;
        sum = examInfoMapper.getSum(idNumber, time);

        return sum;
    }

    /**
     * 获取考试题
     *
     * @param exam：考试题
     * @return by:gyd  2019.12.20
     */
    public List<TestInnerData> getDataList(Exam exam) {

        List<HandExamPaperModel> list = new ArrayList<HandExamPaperModel>();
        List<TestInnerData> dataList = new ArrayList<TestInnerData>();//存放Testdata内部的题节点

        String handQuestion = exam.getHandExamQuestion(); //操作题
        String fillQuestion = exam.getFillBlankExamQuestion(); //填空题
        String chooseQuestion = exam.getChooseExamQuestion(); //选择题
        String decideQuestion = exam.getDecideExamQuestion();//判断题
        String commQuestion = exam.getCommExamQuestion();//通讯题

        int questionNum = 0;
        //  0为操作题，1为普通通信题，2为加密通信题，3为单选，4为多选，其他类型(填空题)为 ""

        //1.1操作题
        if (handQuestion == null || handQuestion.equals("")) {
            //操作题答案为空
            //return list;
        } else {
            String[] handArr = handQuestion.split(";");

            for (String examNum : handArr) {
                questionNum++;
                HandExamStore handExam = examInfoMapper.getHandExamByCode(examNum);

                if (handExam != null) {

                    TestInnerData innerData = new TestInnerData();

                    innerData.setTable("hand_exam_store");
                    innerData.setNumber(String.valueOf(questionNum));
                    innerData.setType("0");
                    innerData.setQuestion_code(handExam.getQuestionCode());
                    innerData.setQuestion_name(handExam.getQuestionName());
                    innerData.setSelect("");
                    innerData.setResult("true");
                    innerData.setGrade(handExam.getGrade());

                    //操作题img属性为空
                    dataList.add(innerData);
                }
            }
        }

        //1.2 填空题
        if (fillQuestion == null || fillQuestion.equals("")) {
            //填空题答案为空
            //return list;
        } else {
            String[] fillArr = fillQuestion.split(";");
            for (int i = 0; i < fillArr.length; i++) {
                questionNum++;
                FillBlankExamStore fillExam = examInfoMapper.getFillExamByCode(fillArr[i]);

                if (fillExam != null) {

                    TestInnerData innerData = new TestInnerData();

                    innerData.setNumber(String.valueOf(questionNum));
                    innerData.setTable("fill_blank_exam_store");
                    innerData.setQuestion_code(fillExam.getQuestionCode());
                    innerData.setQuestion_name(fillExam.getQuestionName());
                    innerData.setSelect("");
                    innerData.setType("");
                    innerData.setGrade(fillExam.getGrade());

                    if (fillExam.getResult() != null) {
                        innerData.setResult(fillExam.getResult());//填空题答案
                    } else {
                        innerData.setResult("");//填空题答案
                    }

                    dataList.add(innerData);
                }
            }
        }

        //1.3 选择题
        if (chooseQuestion == null || chooseQuestion.equals("")) {
            //答案为空
            //return list;
        } else {
            String[] chooseArr = chooseQuestion.split(";");
            for (int i = 0; i < chooseArr.length; i++) {
                questionNum++;
                ChooseSelectStore chooseExam = examInfoMapper.getChooseExamByCode(chooseArr[i]);

                if (chooseExam != null) {

                    TestInnerData innerData = new TestInnerData();

                    innerData.setTable("choose_select_store");
                    innerData.setNumber(String.valueOf(questionNum));

                    innerData.setQuestion_code(chooseExam.getQuestionCode());
                    //0单选，1多选
                    if (chooseExam.getFlag() == 0) {
                        innerData.setQuestion_name(chooseExam.getQuestionName() + "（单选）");
                        innerData.setType("3");
                    } else {
                        innerData.setQuestion_name(chooseExam.getQuestionName() + "（多选）");
                        innerData.setType("4");
                    }
                    innerData.setSelect(chooseExam.getSelectA());
                    innerData.setResult(chooseExam.getResult());
                    innerData.setGrade(chooseExam.getGrade());

                    dataList.add(innerData);
                }
            }
        }

        //1.4 判断题
        if (decideQuestion == null || decideQuestion.equals("")) {
            //填空题答案为空
            //return list;
        } else {
            String[] decideArr = decideQuestion.split(";");
            for (int i = 0; i < decideArr.length; i++) {

                questionNum++;
                DecideStore decideStoreExam = examInfoMapper.getDecideExamByCode(decideArr[i]);

                if (decideStoreExam != null) {

                    TestInnerData innerData = new TestInnerData();

                    innerData.setTable("decide_store");
                    innerData.setNumber(String.valueOf(questionNum));
                    innerData.setQuestion_code(decideStoreExam.getQuestionCode());
                    innerData.setQuestion_name(decideStoreExam.getQuestionName());
                    innerData.setSelect("A.正确;B.错误");//选项
                    innerData.setResult(decideStoreExam.getResult());//答案
                    innerData.setType("3");
                    innerData.setGrade(decideStoreExam.getGrade());
                    dataList.add(innerData);
                }
            }
        }

        //1.5 通讯题
        if (commQuestion == null || "".equals(commQuestion)) {
            //填空题答案为空
            //return list;
        } else {
            String[] commArr = commQuestion.split(";");
            for (int i = 0; i < commArr.length; i++) {

                questionNum++;
                CommExam commExam = examInfoMapper.getCommExamByCode(commArr[i]);

                if (commExam != null) {

                    TestInnerData innerData = new TestInnerData();

                    innerData.setTable("comm_exam_store");
                    innerData.setNumber(String.valueOf(questionNum));
                    innerData.setQuestion_code(commExam.getQuestionCode());
                    innerData.setQuestion_name(commExam.getQuestionName());
                    innerData.setResult(commExam.getResult());
                    innerData.setGrade(commExam.getGrade());

                    //innerData.setSelect();//选项
                  /*  if (commExam.getResult().contains("。。")) {
                        //427，420。。127747
                        innerData.setResult(commExam.getResult().replace("。。",";"));//答案
                    }
                    if (commExam.getResult().contains("。")) {
                        //2468013579。12546
                        innerData.setResult(commExam.getResult().replace("。",";"));//答案
                    }*/

                    //1为普通通信题，2为加密通信题
                    if (commExam.getType() == 4) {
                        //4.普通通信，5.密码通信）
                        innerData.setType("1");
                    }
                    if (commExam.getType() == 5) {
                        innerData.setType("2");
                    }

                    if (commExam.getPassTableId() != null) {
                        //密码表图片
                        innerData.setImg(PhotoUtil.sqlTophoto(commExam.getPassTableId()));
                    }

                    dataList.add(innerData);
                }
            }
        }

        return dataList;
    }

    /**
     * 过期考试
     *
     * @param examId
     * @return
     */
    public int expireExam(String examId) {
        int tag = 0;

        tag = examInfoMapper.expireExamById(examId);

        if (tag > 0) {
            //修改成功
            return 1;
        }

        return tag;
    }

    /**
     * 获取距现在最近的考试
     * @param subject
     * @param nowTime
     * @param level
     * @return
     * @throws ParseException
     */
    public List<Exam> getRecentExam(String subject, String nowTime,String level) throws ParseException {

        //已开考
        List<Exam> beforeExamlist = new ArrayList<Exam>();

        //未开考
        List<Exam> afterExamlist = new ArrayList<Exam>();

        List<Exam> examlist = new ArrayList<Exam>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //1、查最近已考考试
            if ("3".equals(subject)) {
                //理论知识
                beforeExamlist = examInfoMapper.selectBeforeNowTheoryExam(subject,level);

            }else {
                beforeExamlist = examInfoMapper.selectBeforeNowExam(subject, nowTime);

            }

            Exam beforeExam = new Exam();

            Long now = sdf.parse(nowTime).getTime();

            //1.1 查出未结束的考试
            if (beforeExamlist.size() > 0) {

                for (Exam exam : beforeExamlist) {

                    Long startTime = exam.getStartTime().getTime();
                    Long passTime = 0L;
                    passTime = (now - startTime) / (1000 * 60);//开始距现在的分钟数

                    if (passTime < exam.getDuration() || passTime == exam.getDuration()) {
                        //正在进行中已开考的考试
                        beforeExam = exam;

                        //只取一场考试
                        examlist.add(beforeExam);
                        break;
                    }
                }
            }

            //2、查最近未考考试
            afterExamlist = examInfoMapper.selectAfterNowExam(subject, nowTime,level);
            Exam afterExam = new Exam();

            //1.1 查出未开始的考试,取一条
            if (afterExamlist.size() > 0) {
                examlist.add(afterExamlist.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return examlist;
    }

    /**
     * 临时考生生成理论，航片，作战试卷
     *
     * @param idNumber：学号
     * @param subject：科目
     * @param creatNewExam：随机生成题用用到的类
     * @param existExam:存在的考试
     * @return by:gyd
     */
    //为临时考生生成理论，航片，作战试卷
    //根考试类型，题的数量来生成考卷
    public Result makeTempExam(String idNumber, String subject, CreatNewExam creatNewExam, Exam existExam) {

        Result result = new Result();

        Exam okExam = new Exam();

        int flag = 0;

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

                if (creatNewExam.getRadio() != null) {
                    if (creatNewExam.getRadio() != 0) {//本机信息
                        fillBlankExamStoreList = iFillExamService.getQuestion(creatNewExam, 2.1, 2.4);
                        for (FillBlankExamStore fillBlankExamStore : fillBlankExamStoreList) {
                            fillBlankCode.add(fillBlankExamStore.getQuestionCode());
                            fillBlankResult.add(fillBlankExamStore.getResult());
                        }
                        fillNum = String.valueOf(fillBlankExamStoreList.size());
                    }
                }

                if (creatNewExam.getRadio() != 0) {//本机信息
                    fillBlankExamStoreList = iFillExamService.getQuestion(creatNewExam, 2.1, 2.4);
                    for (FillBlankExamStore fillBlankExamStore : fillBlankExamStoreList) {
                        fillBlankCode.add(fillBlankExamStore.getQuestionCode());
                        fillBlankResult.add(fillBlankExamStore.getResult());
                    }
                    fillNum = String.valueOf(fillBlankExamStoreList.size());
                }

                if (creatNewExam.getPack() != null) {
                    if (creatNewExam.getPack() != 0) {//定位题
                        fillBlankExamStoreList = iFillExamService.getQuestion(creatNewExam, 2.5, 2.81);
                        for (FillBlankExamStore fillBlankExamStore : fillBlankExamStoreList) {
                            fillBlankCode.add(fillBlankExamStore.getQuestionCode());
                            fillBlankResult.add(fillBlankExamStore.getResult());
                        }
                        fillNum = fillNum + "," + String.valueOf(fillBlankExamStoreList.size());
                    }

                }



                if (creatNewExam.getJudge() != null) {
                    if (creatNewExam.getJudge() != 0) {//通讯题
                        commExamStoreList = iCommExamService.getQuestion(creatNewExam);
                        for (CommExamStore commExamStore : commExamStoreList) {
                            commCode.add(commExamStore.getQuestionCode());
                            commResult.add(commExamStore.getResult());
                        }
                        commNum = String.valueOf(commExamStoreList.size());
                    }
                }


                if (creatNewExam.getCheckbox() !=null){
                    if (creatNewExam.getCheckbox() != 0) {//操作题
                        handExamStoreList = iHandExamService.getQuestion(creatNewExam, 1.1, 1.7);
                        for (HandExamStore handExamStore : handExamStoreList) {
                            handCode.add(handExamStore.getQuestionCode());
                            handResult.add(handExamStore.getResult());
                        }
                        handNum = String.valueOf(handExamStoreList.size());
                    }
                }

            } else {


                if (creatNewExam.getRadio() != null) {
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

                }


                if (creatNewExam.getCheckbox() != null) {
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

                }

                if (creatNewExam.getJudge() != null) {
                    if (creatNewExam.getJudge() != 0) {
                        //判断
                        decideStoreList = iDecideStoreService.getQuestion(creatNewExam);
                        for (DecideStore decideStore : decideStoreList) {
                            decideCode.add(decideStore.getQuestionCode());
                            decideResult.add(decideStore.getResult());
                        }

                        decideNum = String.valueOf(decideStoreList.size());
                    }

                }


                if (creatNewExam.getPack() != null) {
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
            Exam exam = new Exam();
//            exam.setLevel("0");
            exam.setLevel(creatNewExam.getLevel());
            exam.setIdNumber(idNumber);
            exam.setExamId(UUID.randomUUID().toString().replaceAll("-", ""));
            exam.setType(existExam.getType());
            exam.setStartTime(existExam.getStartTime());
            exam.setStatus(0);
            exam.setExamName(existExam.getExamName());
            exam.setQuestionNum(existExam.getQuestionNum());
            exam.setTotalGrade(existExam.getTotalGrade());
            exam.setDuration(existExam.getDuration());
            exam.setQuestionGrade(existExam.getQuestionGrade());
            exam.setCreateExam(0);

            exam.setChooseExamResult(chooseResults);
            exam.setChooseExamQuestion(chooseCodes);

            exam.setDecideExamResult(decideResults);
            exam.setDecideExamQuestion(decideCodes);

            exam.setFillBlankExamResult(fillBlankResults);
            exam.setFillBlankExamQuestion(fillBlankCodes);

            exam.setHandExamResult(handResults);
            exam.setHandExamQuestion(handCodes);

            exam.setCommExamResult(commResults);
            exam.setHandExamQuestion(commCodes);

            //保存题的数量
            exam.setSingleChooseNum(singleChooseNum);
            exam.setManyChooseNum(manyChooseNum);
            exam.setDecideNum(decideNum);
            exam.setFillNum(fillNum);
            exam.setHandNum(handNum);
            exam.setCommNum(commNum);
            //保存试卷
            int tag = examMapper.add(exam);

            if (tag == 1) {
                //保存成功
                flag = 1;
                result.setData(exam);
                result.setSuccess(true);

            }else {
                //result.setData(exam);
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 学员获取考试题
     * 暂时不用
     *
     * @param request
     * @param id_number
     * @param subject
     * @return 112233
     * by:gyd
     */
    public Result getExamPaperOld(HttpServletRequest request, User user, String id_number, String subject) {

        Result result = new Result();
        List<Object> list = new ArrayList<Object>();
        List<Object> modelList = new ArrayList<Object>();
        HttpSession session = request.getSession();
        User user1 = (User) session.getAttribute("loginInfo");// 取得当前登录用户

        MobileExamModel mobileExamModel = new MobileExamModel();//手持机考试题
        HandExamInfoModel handExamInfoModel = new HandExamInfoModel();

        try {

            Exam exam = new Exam();


            boolean freshTag = true;

           /* if ("1".equals(user.getState())) {
                //新学员
                //查最近的一场考试，根据时间排序
                List<Exam> examList = examInfoMapper.selectRecentExam(id_number, subject);

                if (examList.size() > 0) {
                    exam = examList.get(0);
                }
            } else {
                //老学员
                exam = getCurrentExam(user1.getIdNumber(), subject);
            }*/

            exam = getCurrentExam(user1.getIdNumber(), subject);

            if (exam.getExamId() != null) {
                //考试题不为空
                //判断是否在考试时间之内
                Timestamp startTime = exam.getStartTime();
                Long examStart = startTime.getTime();
                int duration = exam.getDuration() * 60 * 1000;  //90分钟换算成毫秒
                Long currentTime = System.currentTimeMillis();

                long times = currentTime - examStart;  //现在时间-考试开始

//                if ((currentTime - examStart) > duration) { //现在时间-考试开始 > 90，已经结束
//                    //超时，考试已经结束
//                    //将本场考试status置为 1
//                    try {
//                        int tag = 0;
//                        tag = expireExam(exam.getExamId());
//
//                        if (tag == 1) {
//                            System.out.println(exam.getExamName() + "过期成功");
//
//                            freshTag = FreshUserExam(user,exam,session);
//                            if (freshTag) {
//                                System.out.println("更新用户待考考试成功");
//                            } else {
//                                System.out.println("更新用户待考考试失败");
//                            }
//
//                        }else{
//                            System.out.println(exam.getExamName() + "过期失败");
//                        }
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//
//                    }
//                    result.setMsg(user1.getName()+"你好，"+exam.getExamName()+"本场考试已经结束！");
//                    result.setCode(404);
//                    result.setSuccess(false);
//                    result.setData("");
//                    return  result;
//
//                }else
                if ((currentTime - examStart) < 0) {
                    result.setMsg(user1.getName() + "你好，" + exam.getExamName() + "本场考试尚未开始！/" + times * (-1) / 1000);
                    result.setCode(404);
                    result.setSuccess(false);
                    result.setData("");
                    return result;
                } else {
                    if (subject == null || "".equals(subject)) {
                        //科目为空
                        result.setMsg(user1.getName() + "你好，你的科目信息异常，请重试！");
                        result.setCode(404);
                        result.setSuccess(false);
                        result.setData("");
                    } else {
                        //科目不为空
                        if ("1".equals(subject)) {
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

                            String[] handArr = null;
                            String[] fillArr;
                            String[] commArr;
                            String[] chooseArr;//选择
                            String[] decideArr;//判断

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
                                handExamInfoModel = getHandExam(handArr, 0, exam.getExamId()); // 1.1,1.2
                                outMap = handExamInfoModel.getMap();
                                examInfoModel.setFlagData(outMap);
                            } else {
                                handArr = handQuestion.split(";");
                                if (handArr.length > 0) {
                                    num = handArr.length;
                                    //outMap = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2
                                    handExamInfoModel = getHandExam(handArr, 0, exam.getExamId()); // 1.1,1.2

                                    if (handExamInfoModel == null) {
                                        result.setData(examInfoModel);
                                        result.setMsg("获取失败，操作题题号为空，请重试");
                                        result.setCode(404);
                                        //return  result;
                                    } else {
                                        if (!handExamInfoModel.getHandSaveFlag()) {
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
                            mobileExamModel = getMobileExam(newExam, num);//传入考试题和开始题号
                            //
                            if (mobileExamModel == null) {
                                result.setData(examInfoModel);
                                result.setMsg("获取失败，填空题题号为空，请重试");
                                result.setCode(404);
                                //return  result;
                            } else {
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

                            if (fillQuestion == null || fillQuestion.equals("")) {
                                //填空题为空
                            } else {
                                fillArr = fillQuestion.split(";");

                                if (fillArr.length > 0) {
                                    num += fillArr.length;
                                }
                            }
                            dataList = getDataList(moreNewExam);
                            examInfoModel.setTestData(dataList);
                            result.setData(examInfoModel);
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("查询成功");

                            //更新学员待考考试名称
                            //freshTag = FreshUserExam(user, exam, session);
                           /* if (freshTag) {
                                System.out.println("更新用户待考考试成功");
                            } else {
                                System.out.println("更新用户待考考试失败");
                            }*/

                            //更新分数，置零
                            //考题置为已考：1
                            String examinationId = moreNewExam.getExamId();
                            int gradeTag = initGrade(examinationId);

                            if (gradeTag > 0) {
                                System.out.println(moreNewExam.getExamName() + "得分置为0成功");
                            } else {
                                System.out.println(moreNewExam.getExamName() + "得分置为0失败");
                            }
                        } else {
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
                            String[] handCodeArr = null;
                            String[] fillCodeArr = null;
                            String[] chooseCodeArr = null;
                            String[] decideCodeArr = null;

                            //3.1 操作题
                            //3.2 填空题（存在范围问题）
                            try {
                                if (handQuestionCodes == null || "".equals(handQuestionCodes)) {

                                } else {
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
                                        if (store.getResult() == null) {
                                            picturePaper.setResult("");
                                        }
                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                            //picturePaper.setImg("");
                                        } else {
                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                        }
                                        pictureQuestionList.add(picturePaper);
                                    }

                                    if (fillQuestionCodes == null || "".equals(fillQuestionCodes)) {
                                        //填空题为空
                                    } else {
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
                                                    if (fillBlankExamStore.getResult() == null) {
                                                        picturePaper.setResult("");
                                                    }
                                                    if (fillBlankExamStore.getPassTableId() == null || "".equals(fillBlankExamStore.getPassTableId())) {
                                                        //picturePaper.setImg("");
                                                    } else {
                                                        picturePaper.setImg(PhotoUtil.sqlTophoto(fillBlankExamStore.getPassTableId()));
                                                    }

                                                    pictureQuestionList.add(picturePaper);
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                //3.3 选择题
                                //注意区分单选和多选
                                if (chooseQuestionCodes == null || "".equals(chooseQuestionCodes)) {

                                } else {
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
                                            picturePaper.setQuestion_name(store.getQuestionName() + "(单选)");
                                        } else {
                                            //多选
                                            picturePaper.setType("3");
                                            picturePaper.setQuestion_name(store.getQuestionName() + "(多选)");
                                        }

                                        picturePaper.setSelect(store.getSelectA());  //   “A.111;B.222;C.333;D.444;E.555;F.666”
                                        picturePaper.setResult(store.getResult());
                                        if (store.getResult() == null) {
                                            picturePaper.setResult("");
                                        }
                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                            //picturePaper.setImg("");
                                        } else {
                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                        }
                                        pictureQuestionList.add(picturePaper);
                                    }
                                }

                                if (decideQuestionCodes == null || "".equals(decideQuestionCodes)) {

                                } else {
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
                                        picturePaper.setQuestion_name(store.getQuestionName() + "(判断)");

                                        picturePaper.setSelect(store.getSelectA());  //   “A.111;B.222;C.333;D.444;E.555;F.666”
                                        picturePaper.setResult(store.getResult());
                                        if (store.getResult() == null) {
                                            picturePaper.setResult("");
                                        }
                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                            //picturePaper.setImg("");
                                        } else {
                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                        }
                                        pictureQuestionList.add(picturePaper);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //更新分数，置零
                            //考题置为已考：1
                            String examinationId = exam.getExamId();
                            int gradeTag = initGrade(examinationId);

                            if (gradeTag > 0) {
                                System.out.println(exam.getExamName() + "得分置为0成功");
                            } else {
                                System.out.println(exam.getExamName() + "得分置为0失败");
                            }
                            //修改考试开始时间
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = simpleDateFormat.format(new Date());
                            Timestamp StartTime = Timestamp.valueOf(date);
                            exam.setStartTime(StartTime);
                            examMapper.updateStartTime(exam);

                            //封装数据
                            dataMap.put("examId", exam.getExamId());
                            dataMap.put("testData", pictureQuestionList);
                            dataMap.put("examPhoto", "./识图用图/map.png");
                            result.setCode(200);
                            result.setData(dataMap);
                            result.setMsg("获取成功");
                            result.setSuccess(true);

                            //更新学员待考考试名称
                            freshTag = FreshUserExam(user, exam, session);
                            if (freshTag) {
                                System.out.println("更新用户待考考试成功");
                            } else {
                                System.out.println("更新用户待考考试失败");
                            }


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
     * 学员获取考试题
     *
     * @param request
     * @param id_number
     * @param subject
     * @return 112233
     * by:gyd
     */
    public Result getExamPaper(HttpServletRequest request, User user, String id_number, String subject,String level,String examId) {

        Result result = new Result();
        List<Object> list = new ArrayList<Object>();
        List<Object> modelList = new ArrayList<Object>();
        HttpSession session = request.getSession();
        User user1 = (User) session.getAttribute("loginInfo");// 取得当前登录用户

        MobileExamModel mobileExamModel = new MobileExamModel();//手持机考试题
        HandExamInfoModel handExamInfoModel = new HandExamInfoModel();

        try {

            Exam exam = new Exam();

            boolean freshTag = true;

           //获取本科目的考试
            /*List<Exam> examList = examInfoMapper.selectRecentExamBySub(id_number, subject,level);

            if (examList.size() > 0) {
                exam = examList.get(0);
            }*/

          // exam = getExamBySub(subject);


            exam = examMapper.getModel(examId);

            if (exam.getExamId() != null) {

                //学员获取的每个试卷都是唯一的
               // exam.setExamId(UUID.randomUUID().toString().replace("-",""));

                //判断是否在考试时间之内
                Long examStart = System.currentTimeMillis();
                int duration = exam.getDuration() * 60 * 1000;  //90分钟换算成毫秒
                Long currentTime = System.currentTimeMillis();

                long times = currentTime - examStart;  //现在时间-考试开始


                if ((currentTime - examStart) < 0) {
                    result.setMsg(user1.getName() + "你好，" + exam.getExamName() + "本场考试尚未开始！/" + times * (-1) / 1000);
                    result.setCode(404);
                    result.setSuccess(false);
                    result.setData("");
                    return result;


                } else {
                    if (subject == null || "".equals(subject)) {
                        //科目为空
                        result.setMsg(user1.getName() + "你好，你的科目信息异常，请重试！");
                        result.setCode(404);
                        result.setSuccess(false);
                        result.setData("");


                    } else {
                        //科目不为空
                        if ("1".equals(subject)) {
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

                            String[] handArr = null;
                            String[] fillArr;
                            String[] commArr;
                            String[] chooseArr;//选择
                            String[] decideArr;//判断

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
                                handExamInfoModel = getHandExam(handArr, 0, exam.getExamId()); // 1.1,1.2
                                outMap = handExamInfoModel.getMap();
                                examInfoModel.setFlagData(outMap);
                            } else {
                                handArr = handQuestion.split(";");
                                if (handArr.length > 0) {
                                    num = handArr.length;
                                    //outMap = examInfoServiceImpl.getHandExam(handArr, 0,exam.getExamId()); // 1.1,1.2
                                    handExamInfoModel = getHandExam(handArr, 0, exam.getExamId()); // 1.1,1.2

                                    if (handExamInfoModel == null) {
                                        result.setData(examInfoModel);
                                        result.setMsg("获取失败，操作题题号为空，请重试");
                                        result.setCode(404);
                                        //return  result;
                                    } else {
                                        if (!handExamInfoModel.getHandSaveFlag()) {
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
                            mobileExamModel = getMobileExam(newExam, num);//传入考试题和开始题号
                            //
                            if (mobileExamModel == null) {
                                result.setData(examInfoModel);
                                result.setMsg("获取失败，填空题题号为空，请重试");
                                result.setCode(404);
                                //return  result;
                            } else {
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

                            if (fillQuestion == null || fillQuestion.equals("")) {
                                //填空题为空
                            } else {
                                fillArr = fillQuestion.split(";");

                                if (fillArr.length > 0) {
                                    num += fillArr.length;
                                }
                            }
                            dataList = getDataList(moreNewExam);
                            examInfoModel.setTestData(dataList);
                            result.setData(examInfoModel);
                            result.setCode(200);
                            result.setSuccess(true);
                            result.setMsg("查询成功");

                            //更新学员待考考试名称
                            freshTag = FreshUserExam(user, exam, session);
                            if (freshTag) {
                                System.out.println("更新用户待考考试成功");
                            } else {
                                System.out.println("更新用户待考考试失败");
                            }

                            //更新分数，置零
                            //考题置为已考：1
                            String examinationId = moreNewExam.getExamId();
                            int gradeTag = initGrade(examinationId);

                            if (gradeTag > 0) {
                                System.out.println(moreNewExam.getExamName() + "得分置为0成功");
                            } else {
                                System.out.println(moreNewExam.getExamName() + "得分置为0失败");
                            }
                        } else {
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
                            String[] handCodeArr = null;
                            String[] fillCodeArr = null;
                            String[] chooseCodeArr = null;
                            String[] decideCodeArr = null;

                            //3.1 操作题
                            //3.2 填空题（存在范围问题）
                            try {
                                if (handQuestionCodes == null || "".equals(handQuestionCodes)) {

                                } else {
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
                                        if (store.getResult() == null) {
                                            picturePaper.setResult("");
                                        }
                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                            //picturePaper.setImg("");
                                        } else {
                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                        }
                                        pictureQuestionList.add(picturePaper);
                                    }

                                    if (fillQuestionCodes == null || "".equals(fillQuestionCodes)) {
                                        //填空题为空
                                    } else {
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
                                                    if (fillBlankExamStore.getResult() == null) {
                                                        picturePaper.setResult("");
                                                    }
                                                    if (fillBlankExamStore.getPassTableId() == null || "".equals(fillBlankExamStore.getPassTableId())) {
                                                        //picturePaper.setImg("");
                                                    } else {
                                                        picturePaper.setImg(PhotoUtil.sqlTophoto(fillBlankExamStore.getPassTableId()));
                                                    }

                                                    pictureQuestionList.add(picturePaper);
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                //3.3 选择题
                                //注意区分单选和多选
                                if (chooseQuestionCodes == null || "".equals(chooseQuestionCodes)) {

                                } else {
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
                                            picturePaper.setQuestion_name(store.getQuestionName() + "(单选)");
                                        } else {
                                            //多选
                                            picturePaper.setType("3");
                                            picturePaper.setQuestion_name(store.getQuestionName() + "(多选)");
                                        }

                                        picturePaper.setSelect(store.getSelectA());  //   “A.111;B.222;C.333;D.444;E.555;F.666”
                                        picturePaper.setResult(store.getResult());
                                        if (store.getResult() == null) {
                                            picturePaper.setResult("");
                                        }
                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                            //picturePaper.setImg("");
                                        } else {
                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                        }
                                        pictureQuestionList.add(picturePaper);
                                    }
                                }

                                if (decideQuestionCodes == null || "".equals(decideQuestionCodes)) {

                                } else {
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
                                        picturePaper.setQuestion_name(store.getQuestionName() + "(判断)");

                                        picturePaper.setSelect(store.getSelectA());  //   “A.111;B.222;C.333;D.444;E.555;F.666”
                                        picturePaper.setResult(store.getResult());
                                        if (store.getResult() == null) {
                                            picturePaper.setResult("");
                                        }
                                        if (store.getPassTableId() == null || "".equals(store.getPassTableId())) {
                                            //picturePaper.setImg("");
                                        } else {
                                            picturePaper.setImg(PhotoUtil.sqlTophoto(store.getPassTableId()));
                                        }
                                        pictureQuestionList.add(picturePaper);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //更新分数，置零
                            //考题置为已考：1
                            String examinationId = exam.getExamId();
                            int gradeTag = initGrade(examinationId);

                            if (gradeTag > 0) {
                                System.out.println(exam.getExamName() + "得分置为0成功");
                            } else {
                                System.out.println(exam.getExamName() + "得分置为0失败");
                            }
                            //修改考试开始时间
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = simpleDateFormat.format(new Date());
                            Timestamp StartTime = Timestamp.valueOf(date);
                            exam.setStartTime(StartTime);
                            examMapper.updateStartTime(exam);

                            //封装数据
                            dataMap.put("examId", exam.getExamId());
                            dataMap.put("testData", pictureQuestionList);
                            dataMap.put("examPhoto", "./识图用图/map.png");
                            result.setCode(200);
                            result.setData(dataMap);
                            result.setMsg("获取成功");
                            result.setSuccess(true);

                            //更新学员待考考试名称
                            freshTag = FreshUserExam(user, exam, session);
                            if (freshTag) {
                                System.out.println("更新用户待考考试成功");
                            } else {
                                System.out.println("更新用户待考考试失败");
                            }


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
     * 保存只有固定题
     * 126类型
     *
     * @param user
     * @param exam
     * @return
     */
    public String saveCertainExam(User user, HttpSession session, Exam exam) {

        Result result = new Result();
        String msg = "";

        exam.setExamId(UUID.randomUUID().toString().replace("-", ""));
        exam.setIdNumber(user.getIdNumber());
        exam.setStatus(0);

        //将试卷保存
        try {
            //考试状态至为0：未考
            exam.setStatus(0);
            exam.setLevel("0");
            int tag = examServiceImpl.addExam(exam);

            if (tag == 0) {
                //保存成功
                msg += exam.getExamName() + " 试卷生成成功，请点击对应科目进行查看！";

                String examName = user.getExamNames() + "," + exam.getExamName();
                user.setExamNames(examName);
                session.setAttribute("loginInfo", user);

                int userTag = userServiceImpl.updateByUser(user);

                if (userTag == 1) {
                    System.out.println("更新用户待考信息成功");
                } else {
                    System.out.println("更新用户待考信息失败");
                }
            } else {
                //失败
                msg += exam.getExamName() + " 试卷生成成功，请点击对应科目进行查看！";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

    /**
     * 保存含随机题的考试
     * 类型是345的考试（看下）
     *
     *所有 科目
     * @param user
     * @param session
     * @param idNumber
     * @param subject
     * @param exam
     * @return
     */
    public Exam saveRandomExam(User user, HttpSession session, String idNumber, String subject, Exam exam,String level) {

        String msg = "";

        CreatNewExam creatNewExam = new CreatNewExam();

        creatNewExam.setSubject(subject);
        creatNewExam.setTransflag("1");
        //creatNewExam.setLevel(exam.getLevel());
        creatNewExam.setLevel(level);

        Exam readyExam = new Exam();

        if ("1".equals(subject)) {//北斗手持机题数
            /**
             * 选择单选 = 本机信息（填空题）
             * 选择多选  =  设置题（操作题）
             * 填空题  =定位题（填空题）
             * 判断题  =  短报题（通信题）
             */
            //填空题
            if (exam.getFillNum() != null) {
                String[] fillNum = exam.getFillNum().split(",");
                creatNewExam.setPack(Integer.valueOf(fillNum[1]));//定位题
                creatNewExam.setRadio(Integer.valueOf(fillNum[0]));//本机信息题
            } else {
                creatNewExam.setPack(0);
                creatNewExam.setRadio(0);
            }
            //操作题
            if (exam.getHandNum() != null) {
                creatNewExam.setCheckbox(Integer.valueOf(exam.getHandNum()));
            }
            //判断题
            if (exam.getCommNum() != null) {
                creatNewExam.setJudge(Integer.valueOf(exam.getCommNum()));
            } else {
                creatNewExam.setJudge(0);
            }
        }else {
            //单选题
            if (exam.getSingleChooseNum() != null) {
                creatNewExam.setRadio(Integer.valueOf(exam.getSingleChooseNum()));
            } else {
                creatNewExam.setRadio(0);
            }

            //多选题
            if (exam.getManyChooseNum() != null) {
                creatNewExam.setCheckbox(Integer.valueOf(exam.getManyChooseNum()));
            } else {
                creatNewExam.setCheckbox(0);
            }

            //填空题
            if (exam.getFillNum() != null) {
                creatNewExam.setPack(Integer.valueOf(exam.getFillNum()));
            } else {
                creatNewExam.setPack(0);
            }

            //判断题
            if (exam.getDecideNum() != null) {
                creatNewExam.setJudge(Integer.valueOf(exam.getDecideNum()));
            } else {
                creatNewExam.setJudge(0);
            }
        }

        //3.2.1 调用随机出题的接口
        //考试状态至为0：未考
        exam.setStatus(0);
        exam.setIdNumber(user.getIdNumber());
        exam.setLevel("0");
        exam.setExamId(UUID.randomUUID().toString().replace("-", ""));

        //生成随机考试题
        //Result makeResult = makeTempExam(idNumber, subject, creatNewExam, exam);
        Result makeResult = makeTempExam(idNumber, subject, creatNewExam, exam);

        if (makeResult.isSuccess()) {
            //出题成功
            msg += exam.getExamName() + " 试卷生成成功，请点击对应科目进行查看！";

            String examName = "";
            readyExam = (Exam) makeResult.getData();

            //第一次用户待考名都是空
            if (user.getExamNames() != null) {
                examName = user.getExamNames() + "," + exam.getExamName();
                user.setExamNames(examName);
            } else {
                user.setExamNames(exam.getExamName());
            }

            /*session.setAttribute("loginInfo", user);

            int userTag = userServiceImpl.updateByUser(user);

            if (userTag == 1) {
                System.out.println("更新用户待考信息成功");
            } else {
                System.out.println("更新用户待考信息失败");
            }*/
        }

        return readyExam;
    }

    /**
     * 更新用户的待考和已考考试名称
     *
     * @param user
     * @param exam
     * @param session
     * @return 111
     */
    public boolean FreshUserExam(User user, Exam exam, HttpSession session) {

        boolean resultFlag = false;

        boolean unflag = true;
        boolean finishedflag = true;

        //出题后去掉用户对应的待考
        String examNames = user.getExamNames();

        //出题后新增已考名称
        String finisedExamNames = user.getFinishedExam();

        List<String> nameList = new ArrayList<>();
        List<String> finishedNameList = new ArrayList<>();

        String oldName = exam.getExamName();

        //更新待考
        if (examNames != null) {

            String[] nameArr = examNames.split(",");

            for (String s : nameArr) {
                if (!s.equals(oldName)) {
                    nameList.add(s);
                }
            }

            String examName = String.join(",", nameList);
            user.setExamNames(examName);

            //更新session
            session.setAttribute("loginInfo", user);
            String id_number = user.getIdNumber();

            //更新数据库中用户的待考科目
            int tag = userMapper.updateUserExam(examName, id_number);

            if (tag == 1) {
                System.out.println("更新用户待考考试成功");
            } else {
                System.out.println("更新用户待考考试失败");
                unflag = false;
            }
        }

        //更新已考
        String finisedExamName = "";
        if (user.getFinishedExam() != null) {
            finisedExamName = user.getFinishedExam() + "," + exam.getExamName() + ",";
            user.setFinishedExam(finisedExamName);
        } else {
            user.setFinishedExam(exam.getExamName());
        }

        //更新session
        session.setAttribute("loginInfo", user);
        String id_number = user.getIdNumber();

        //更新数据库中用户的待考科目
        int tag = userMapper.updateUserFinishedExam(finisedExamName, id_number);

        if (tag == 1) {
            System.out.println("更新用户已考成功");
        } else {

            System.out.println("更新用户已考失败");
            finishedflag = false;
        }

        if (unflag && finishedflag) {
            resultFlag = true;
        }

        return resultFlag;
    }
}
