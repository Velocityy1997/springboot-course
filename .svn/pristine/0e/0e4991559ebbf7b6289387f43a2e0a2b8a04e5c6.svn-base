package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.model.ExcelModel;
import java.util.List;

/**
 * @author ZhaoWenHao
 * 试题类型
 * @create 2020-01-06 14:45
 **/
public interface IQuestionTypeService {

    /**
     * 添加科目试题类型
     * @param questionType
     * @return
     */
   public int addQuestionType (QuestionType questionType);

    /**
     * 删除科目试题类型
     * @param id
     * @return
     */
   public int deleteQuestionType (String[] id);

    /**
     * 修改科目试题类型
     * @param questionType
     * @return
     */
   public int updateQuestionType (QuestionType questionType);

    /**
     * 查询科目试题类型
     * @return
     */
   public List<QuestionType> selectQuestionType(String subject);

    /**
     * 查询是否已经存在科目试题类型
     * @param questionType
     * @return
     */
    QuestionType selectRepeat(QuestionType questionType);

    /**
     * 添加试题时，获取试题类型id
     * @param excelModel
     * @return
     */
    Integer getTypeId(ExcelModel excelModel);
    
    public Integer getTypeIdBySAndT(String subject,String type);
}
