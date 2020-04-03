package io.ken.springboot.course.dao.exam;

import io.ken.springboot.course.bean.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @author YuDong
 * @create 2020-01-07 9:39
 **/

@Mapper
public interface SubjectMapper {

    @Select("SELECT *    FROM   subject")
    List<Subject> queryAll();

    @Select("SELECT DISTINCT  name  FROM   subject")
    List<String> queryAllname();

}
