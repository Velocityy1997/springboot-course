package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.dao.QuestionTypeMapper;
import io.ken.springboot.course.model.ExcelModel;
import io.ken.springboot.course.service.IQuestionTypeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhaoWenHao
 *
 * @create 2020-01-06 14:46
 **/

@Service
public class QuestionTypeServiceImpl implements IQuestionTypeService {
    @Resource
    QuestionTypeMapper questionTypeMapper;
    @Resource
    QuestionType questionType;
    @Override
    public int addQuestionType(QuestionType questionType) {
       int result= questionTypeMapper.addQuestionType(questionType);
        return result;
    }

    @Override
    public int deleteQuestionType(String[] id) {
        int result= questionTypeMapper.deleteQuestionType(id);
        return result;
    }

    @Override
    public int updateQuestionType(QuestionType questionType) {
        int result= questionTypeMapper.updateQuestionType(questionType);
        return result;
    }

    @Override
    public List<QuestionType> selectQuestionType(String subject) {
        List<QuestionType> list= questionTypeMapper.selectQuestionType(subject);
        return list;
    }

    @Override
    public QuestionType selectRepeat(QuestionType questionType) {

      QuestionType result=  questionTypeMapper.selectRepeat(questionType);

        return result;
    }

    @Override
    public Integer getTypeId(ExcelModel excelModel) {
        questionType.setSubject(excelModel.getSubject());
        questionType.setTypeName(excelModel.getType());
        QuestionType typeId=  questionTypeMapper.selectRepeat(questionType);
        try {
            return typeId.getId();
        }catch (NullPointerException e){
            return null;
        }


    }
    
    @Override
    public Integer getTypeIdBySAndT(String subject,String type) {
        questionType.setSubject(subject);
        questionType.setTypeName(type);
        QuestionType typeId=  questionTypeMapper.selectRepeat(questionType);
        try {
            return typeId.getId();
        }catch (NullPointerException e){
            return null;
        }


    }
    
}
