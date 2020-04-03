package io.ken.springboot.course.dao.exam;

import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.CommExam;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

/**
 * 出考试题
 * @author YuDong
 * @create 2019-11-22 14:28
 **/

@Mapper
public interface ExamInfoMapper {

    /*获取当前考试题*/
    @Select("SELECT * FROM exam WHERE id_number =#{id_number}  AND type = #{subject}  and  status = 0  ORDER BY start_time DESC")
    List<Exam> getCurrentExam(@Param("id_number")String id_number,@Param("subject")String subject);

    /*获取当前科目的考试题*/
    @Select("SELECT * FROM exam WHERE  type = #{subject}  and  status = 0 ")
    List<Exam> selectExamBySub(@Param("subject")String subject);

    @Select("SELECT * FROM exam WHERE  type = 3  and level = #{level}  and  status = 0 ")
    List<Exam> selectTheroyExamBySub(@Param("level")String level);


    /*获取一个学员待考数量，现在时间减去考试开始时间小于考试时长就为待考*/
    @Select("SELECT * FROM exam WHERE id_number =#{id_number}  and  status = 0  and timestampdiff(MINUTE,start_time,NOW())<duration")
    List<Exam> getCurrentExam1(@Param("id_number")String id_number);
    
    //获取已考的列表
    @Select("SELECT * FROM exam WHERE id_number =#{id_number}  and  status = 1")
    List<Exam> getAlreadyExam(@Param("id_number")String id_number);
    
    
    //查当前考试
    @Select("SELECT * FROM exam WHERE exam_id =#{exam_id} ")
    Exam getExam(@Param("exam_id")String examId);
    
    /*查询所有操作题*/
    @Select("SELECT * FROM hand_exam_store")
    List<HandExamStore> getAllHandExam();


    /*查询所有操作题名称*/
    @Select("SELECT DISTINCT question_name FROM hand_exam_store")
    List<String> getAllHandExamName();


    /*查询所有填空题*/
    @Select("SELECT * FROM fill_blank_exam_store")
    List<FillBlankExamStore> getAllFillExam();

    /*查询所有填空题*/
    @Select("SELECT substring(question_code, 3)  FROM fill_blank_exam_store")
    List<String> getAllFillExamNum();


//    @Select("SELECT RIGHT(question_code,1)  FROM fill_blank_exam_store")
//    List<String> getAllFillExamNum();

    /*查询所有通讯题*/
    @Select("SELECT * FROM comm_exam_store")
    List<CommExamStore> getAllCommExam();


    //根据题号查操作题
    @Select("SELECT * FROM   hand_exam_store   where question_code = #{question_code}")
    HandExamStore getHandExamByCode(@Param("question_code")String question_code);

    //根据题号查操作题
    @Select("SELECT id,question_name,question_code,type,grade,result,pass_table_id,subject FROM   fill_blank_exam_store   where question_code = #{question_code}")
    HandExamStore getHandExamByCode1(@Param("question_code")String question_code);
    
    //根据题号查填空题
    @Select("SELECT * FROM   fill_blank_exam_store   where question_code = #{question_code}")
    FillBlankExamStore getFillExamByCode(@Param("question_code")String question_code);

    //根据题号查通讯题
    @Select("SELECT * FROM   comm_exam_store   where question_code = #{question_code}")
    CommExam getCommExamByCode(@Param("question_code")String question_code);

    //根据题号选择题
    @Select("SELECT * FROM  choose_select_store  where question_code = #{question_code}")
    ChooseSelectStore getChooseExamByCode(@Param("question_code")String question_code);

    //根据题号查判断题
    @Select("SELECT * FROM   decide_store   where question_code = #{question_code}")
    DecideStore getDecideExamByCode(@Param("question_code")String question_code);

    //保存考试题答案
    //1.保存操作题答案
    @Update("update exam SET  hand_exam_result = #{handResult} where exam_id = #{examId} ")
    int updateHandExam(@Param("handResult")String handResult,@Param("examId")String examId);

    //2.保存填空题答案
    @Update("update exam SET   fill_blank_exam_result =#{fillResult}  where exam_id = #{examId} ")
    int updateFillExam(@Param("fillResult")String fillResult,@Param("examId")String examId);

    //3.保存通讯题答案
    @Update("update exam SET  comm_exam_result = #{commResult}  where exam_id = #{examId} ")
    int updateCommExam(@Param("commResult")String commResult,@Param("examId")String examId);


    //得分置为0
    @Update("update exam SET  status = 1 , grade ='0'  where exam_id = #{examId} ")
    int setGrade(@Param("examId")String examId);

    //得分置为0
    @Update("update exam  SET  status = 1    where exam_id = #{examId} ")
    int expireExamById(@Param("examId")String examId);


    //统计北斗手持机考试的数量
    @Update("SELECT COUNT(1) FROM  exam  WHERE  status  = 0  AND  id_number= #{idNumber} AND start_time >= #{nowTime}  ")
    Long getSum(@Param("idNumber")String idNumber,@Param("nowTime")String nowTime);
    
    /*获取考生所有的试卷*/
    @Select("SELECT * FROM exam WHERE id_number =#{id_number}  AND type = #{subject}   ORDER BY start_time DESC  limit 0,200" )
    List<Exam> selectAllExam(@Param("id_number")String id_number,@Param("subject")String subject);

    /*理论---获取距现在最近已开考的考试(学员级别：全部)*/
    @Select("SELECT *  FROM  exam  where  type=#{subject} AND  status = 1  and level like Concat('%',#{level},'%')  ")
    List<Exam> selectBeforeNowTheoryExam(@Param("subject") String subject,@Param("level") String level);

    /*获取距现在最近已开考的考试(学员级别：全部)*/
    @Select("SELECT *  FROM  exam  where  type=#{subject} AND  status = 1    AND start_time  <=#{now}  ORDER BY start_time DESC")
    List<Exam> selectBeforeNowExam(@Param("subject") String subject,@Param("now") String now);


    /*获取距现在最近未开考的考试(学员级别：全部)*/
    @Select("SELECT *  FROM  exam  where  type=#{subject} AND  status = 0  and level like Concat('%',#{level},'%')  AND start_time  >=#{now}  ORDER BY start_time asc")
    List<Exam> selectAfterNowExam(@Param("subject") String subject,@Param("now") String now,@Param("level") String level);


    /*查新学员最近的一场考试*/
    @Select("select * from exam WHERE id_number =#{id_number}  AND type= #{subject}   AND  status  = 0  and  level=0  ORDER BY start_time ASC")
    List<Exam> selectRecentExam(@Param("id_number")String id_number,@Param("subject")String subject);


    /*查新学员最近的一场考试*/
    @Select("select * from exam WHERE id_number =#{id_number}  AND type= #{subject}   AND  status  = 0  and  level=#{level}  ")
    List<Exam> selectRecentExamBySub(@Param("id_number")String id_number,@Param("subject")String subject,@Param("level") String level);


}
