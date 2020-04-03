package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.bean.StudentExamInfo;
import io.ken.springboot.course.dao.exam.HandInfoMapper;
import io.ken.springboot.course.service.IHandInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author GouYudong
 * @create 2019-12-24 9:38
 **/

@Service
public class HandInfoServiceImpl implements IHandInfoService {

    @Resource
    private HandInfoMapper handInfoMapper;

    @Override
    public List<StudentExamInfo> getNewHandInfo(String examName) {

        List<StudentExamInfo> list = new ArrayList<StudentExamInfo>();

        list = handInfoMapper.getNewHandInfo(examName);

        if (list.size() > 0) {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            int i = 0;

            String costTime = "";
            Double hour = 0D;
            Double minute = 0D;
            Double useTime = 0D;

            for (StudentExamInfo studentExamInfo : list) {

                i++;
                studentExamInfo.setId(i);
                String time = studentExamInfo.getCost();

                if (time != null) {

                    Double timeMill = Double.parseDouble(time);//将时间格式转换为long，分钟数

                    //useTime = studentExamInfo.getStartTime().getTime()+timeMill;
                    costTime = getCostTime(timeMill);
                    studentExamInfo.setTime(costTime);//格式化为  时分秒

                } else {
                    studentExamInfo.setTime("交卷时间异常");//格式化为  时分秒
                }

            }
        }

        return  list ;
    }

    public String getCostTime(Double time) {
        //入参为小时数
        // 1.5小时

        String timeStr = String.valueOf(time);
        String userTime = "";

        String [] timeArr = null;

        String hourStr = "";
        String minuteStr = "00";

        Double hour = 0D;
        Double minute = 0D;
        Double second = 0D;

        if (time > 1) {

            timeArr = timeStr.split("\\.");
            hourStr = timeArr[0];
            minute = time - Double.parseDouble(hourStr);//分钟数：单位为小时

            if (minute > 0.01 || minute==0.01) {
                String tempStr = String.valueOf(minute * 60.0);
                int index = tempStr.indexOf(".");
                minuteStr = tempStr.substring(0,index);//12.666666
            }

            userTime = hourStr + ":" + minuteStr + ":00";

        } else {
            //小于一小时
            //0.6 小时
            minute = time * 60.0;//36.00000
            String minStr = String.valueOf(minute);

            //String []minuteArr = minStr.split("\\."); //取到分钟数  .前的数据
            int index = minStr.indexOf(".");
            minuteStr = minStr.substring(0, index);

            userTime = "00" + ":" + minuteStr + ":00";

        }

        return userTime;
    }

    //获取考试排名
    @Override
    public List<StudentExamInfo> getExamOrder(String examName) {

        List<StudentExamInfo> list = new ArrayList<StudentExamInfo>();
        list = handInfoMapper.getExamOrder(examName);

        List<String> gradeList = new LinkedList<String>();

        //分数排名  50，40，30
        gradeList =  handInfoMapper.queryGradeOrder(examName);

        int order = 0;
        for (String grade : gradeList) {
            order++;
            for (StudentExamInfo studentExamInfo : list) {
                if (grade.equals(studentExamInfo.getGrade())) {
                    studentExamInfo.setId(order);
                }
            }

        }

        return list;
    }



    //查进行中的考试
    @Override
    public List<String> getExamName() {

        List<Exam> examList = new ArrayList<Exam>();
        List<String> examNameList = new ArrayList<String>();
        //List<Exam> examBackList = new ArrayList<Exam>();

        Long examStart =0L;
        Double duration = 0D;
        Double durationTime = 0D;
        long times = 0L;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timemills = System.currentTimeMillis();
        String nowStr = sdf.format(timemills);

        examList = handInfoMapper.getExamIngList(nowStr);//获取正在进行的考试
        //examBackList = handInfoMapper.getExamIngList();//获取正在进行的考试

        for (Exam exam : examList) {

            Timestamp startTime = exam.getStartTime();
            examStart = startTime.getTime();
            Long currentTime = System.currentTimeMillis();
            times = currentTime - examStart;  //现在时间-考试开始,考试耗时
            duration = Double.parseDouble(String.valueOf(exam.getDuration()));
            durationTime =duration*(60*1000);  //90分钟换算成毫秒

            if ( times > durationTime) { //现在时间-考试开始 > 90，已经结束
                //已经结束的考试
                //examBackList.remove(exam);
                continue;

            }else if ( times < 0 ){
                //尚未开始的考试
               // examBackList.remove(exam);
                continue;

            }else {
                //进行中的考试
                examNameList.add(exam.getExamName());
            }


        }
        return examNameList;
    }

    @Override
    public String getUnHandleExam(String examName) {

        Long sum = 0L;
        Long unHandle = 0L;

        Double sumD = 0D;
        Double unHandleD = 0D;

        Double rate = 0D;
        String rateStr = "0";
        String tempStr = "";

        sum = handInfoMapper.getStudentNum(examName);

        if ( sum > 0 ) {

            unHandle = handInfoMapper.getUnHandleExam(examName);

            sumD = Double.parseDouble(String.valueOf(sum));
            unHandleD = Double.parseDouble(String.valueOf(unHandle));

            rate = 100.0-unHandleD/sumD*100;//完成率
            tempStr = String.valueOf(rate);//45.662262
            int index = 0;
            index = tempStr.indexOf(".");
            rateStr = tempStr.substring(0, index);
        }


        return rateStr;


    }

    @Override
    public List<String> getGradeOrder(String examName) {
        return handInfoMapper.queryGradeOrder(examName);
    }
}
