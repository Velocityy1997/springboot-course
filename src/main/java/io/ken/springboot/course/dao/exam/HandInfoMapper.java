package io.ken.springboot.course.dao.exam;

import io.ken.springboot.course.bean.Exam;
import io.ken.springboot.course.bean.StudentExamInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 交卷情况
 *
 * @author YuDong
 * @create 2019-12-24 9:27
 **/

@Mapper
public interface HandInfoMapper {

    //查看最新交卷情况   按交卷时间倒序
    @Select("SELECT   ex.exam_name,ex.end_time, u.id as userId, ex.start_time,u.name,ex.grade,(UNIX_TIMESTAMP(ex.end_time)-UNIX_TIMESTAMP(ex.start_time))/3600  AS cost FROM  exam  AS ex    LEFT JOIN   user  as u  ON ex.id_number = u.id_number   WHERE exam_name = #{examName} AND  status = 1   ORDER BY end_time DESC")
    List<StudentExamInfo> getNewHandInfo(@Param("examName") String examName);

    //查看分数排名
    @Select("SELECT   u.name,ex.grade  FROM   exam  AS ex    LEFT JOIN   user  as u ON ex.id_number = u.id_number WHERE exam_name = #{examName}  AND   status  = 1  ORDER BY grade*1 DESC  ")
    List<StudentExamInfo> getExamOrder(@Param("examName") String examName);

    @Select("SELECT  DISTINCT   ex.grade  FROM   exam  AS ex    LEFT JOIN   user  as u ON ex.id_number = u.id_number WHERE exam_name = #{examName}  AND   status  = 1  ORDER BY grade*1 DESC  ")
    List<String> queryGradeOrder(@Param("examName") String examName);


    //查进行中的考试
    @Select("SELECT * FROM   exam    where  start_time <= #{nowTime}   GROUP BY exam_name  ORDER BY  start_time ASC")
    List<Exam> getExamIngList(@Param("nowTime") String nowTime);


    //查本场考试未交卷的数量
    @Select("SELECT COUNT(1) FROM exam WHERE exam_name = #{examName}  AND  status = 0")
    Long getUnHandleExam(@Param("examName") String examName);

    //查本场考试考生数量
    @Select("SELECT COUNT(1) FROM exam WHERE exam_name = #{examName} ")
    Long getStudentNum(@Param("examName") String examName);



}
