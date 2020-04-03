package io.ken.springboot.course.dao;

import io.ken.springboot.course.bean.QuestionType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

/**
 * @author ZhaoWenHao
 * 试题类型
 * @create 2020-01-06 14:56
 **/
@Mapper
public interface QuestionTypeMapper {
    @Insert("Insert into question_type values (#{id},#{subject},#{typeName})")
    public int addQuestionType(QuestionType questionType);

    @Delete("<script> " +
            "delete from question_type where id in " +
            "<foreach collection='array' item='id' open='(' separator=',' close=')'>" +
            " #{id}" +
            "</foreach> " +
            "</script>")
    public int deleteQuestionType(String[] id);
    @Update("update question_type set typeName=#{typeName} where id=#{id}")
    public int updateQuestionType(QuestionType questionType);
    @Select("select * from question_type where subject=#{subject}")
    public List<QuestionType> selectQuestionType(@Param("subject")String subject);

    /**
     * 添加试题时，查询题目类别id
     * @param questionType
     * @return
     */
    @Select("select id from question_type where subject=#{subject} and typeName=#{typeName}")
   public QuestionType selectRepeat(QuestionType questionType);


}
