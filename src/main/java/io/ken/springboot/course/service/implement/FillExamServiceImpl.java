package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.dao.FillExamMapper;
import io.ken.springboot.course.model.ExcelModel;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.service.IFillExamService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lq
 * FillExamServiceImpl.java
 * 2019年11月28日
 */
@Service("FillExamService")
public class FillExamServiceImpl implements IFillExamService {

    @Resource
    private FillExamMapper fillExamMapper;

    @Override
    public FillBlankExamStore getNameByCode(String fillQCode) {
        // TODO Auto-generated method stub
        FillBlankExamStore fill = fillExamMapper.getNameByCode(fillQCode);
        return fill;
    }

    @Override
    public List<FillBlankExamStore> getAll() {
        return fillExamMapper.queryAll();
    }

    @Override
    public List<FillBlankExamStore> getByName(String name) {
        // TODO Auto-generated method stub

        List<FillBlankExamStore> comm = fillExamMapper.getByName(name);
        return comm;
    }

    @Override
    public int addExcel(List<ExcelModel> excelModel) {
        return fillExamMapper.addExcel(excelModel);
    }

    @Override
    public ExcelModel queryName(String name, String result) {
        ExcelModel excelModel = fillExamMapper.queryName(name, result);
        if (excelModel == null) {
            return null;
        }
        return excelModel;
    }

    @Override
    public int updateQuestions(ExcelModel excelModel) {
        return fillExamMapper.updateQuestions(excelModel);
    }

    @Override
    public int add(FillBlankExamStore exam) {
        // TODO Auto-generated method stub
        return fillExamMapper.add(exam);
    }

    @Override
    public List<Map<String, String>> getFillQuestionRandom(String subject) {
        List<Map<String, String>> list = fillExamMapper.getFillQuestionRandom(subject);
        return list;
    }

    @Override
    public int addModel(FillBlankExamStore exam) {
        // TODO Auto-generated method stub
        return fillExamMapper.add(exam);
    }

    @Override
    public int getCount(String subject, String level) {
        return fillExamMapper.getCount(subject, level);
    }

    @Override
    public int getCount(String subject, double begin, double end, String level) {
        return fillExamMapper.getCountB(subject,begin,end,level);
    }

    @Override
    public List<FillBlankExamStore> getQuestion(CreatNewExam creatNewExam) {

        List<FillBlankExamStore> fillBlankExamStoreList = new ArrayList<>();

        if ("0".equals(creatNewExam.getLevel())){
            creatNewExam.setLevel("1,2,3,4,5,6");
            fillBlankExamStoreList= fillExamMapper.getQUestion(creatNewExam);
            creatNewExam.setLevel("0");

        }else {
            fillBlankExamStoreList= fillExamMapper.getQUestion(creatNewExam);

        }


        return fillBlankExamStoreList;
    }

    @Override
    public List<FillBlankExamStore> getQuestion(CreatNewExam creatNewExam, double begin, double end) {
        List<FillBlankExamStore> fillBlankExamStoreList = new ArrayList<>();

        if ("0".equals(creatNewExam.getLevel())){
            creatNewExam.setLevel("1,2,3,4,5,6");
            fillBlankExamStoreList= fillExamMapper.getQUestionB(creatNewExam,begin,end);
            creatNewExam.setLevel("0");

        }else {
            fillBlankExamStoreList= fillExamMapper.getQUestionB(creatNewExam,begin,end);

        }


        return fillBlankExamStoreList;
    }
}
