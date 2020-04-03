package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.dao.DecideStoreMapper;
import io.ken.springboot.course.model.ExcelModel;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.service.IDecideStoreService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cll
 * FillExamServiceImpl.java
 * 2019年11月28日
 */
@Service("DecideStoreService")
public class DecideStoreServiceImpl implements IDecideStoreService {

    @Resource
    private DecideStoreMapper decideStoreMapper;

    @Override
    public DecideStore getNameByCode(String fillQCode) {
        // TODO Auto-generated method stub
        DecideStore fill = decideStoreMapper.getNameByCode(fillQCode);
        return fill;
    }

    @Override
    public List<DecideStore> getByName(String name) {
        // TODO Auto-generated method stub

        List<DecideStore> comm = decideStoreMapper.getByName(name);
        return comm;
    }

    @Override
    public int add(DecideStore exam) {
        // TODO Auto-generated method stub
        return decideStoreMapper.add(exam);
    }

    @Override
    public List<Map<String, Object>> getDecideQuestionRandom(String subject) {
        List<Map<String, Object>> list = decideStoreMapper.getDecideQuestionRandom(subject);
        return list;
    }

    @Override
    public int addExcel(List<ExcelModel> excelModel) {

        return decideStoreMapper.addExcel(excelModel);
    }

    @Override
    public ExcelModel queryName(String name, String result) {

        ExcelModel excelModel = decideStoreMapper.queryName(name, result);
        return excelModel;
    }

    @Override
    public int updateQuestions(ExcelModel excelModel) {

        return decideStoreMapper.updateQuestions(excelModel);
    }

    @Override
    public int addModel(DecideStore exam) {
        // TODO Auto-generated method stub
        return decideStoreMapper.add(exam);
    }

    @Override
    public int getCount(String subject, String level) {
        return decideStoreMapper.getCount(subject, level);
    }

    @Override
    public List<DecideStore> getQuestion(CreatNewExam creatNewExam) {

        List<DecideStore> decideStoreList = new ArrayList<>();

        if ("0".equals(creatNewExam.getLevel())){
            creatNewExam.setLevel("1,2,3,4,5,6");
            decideStoreList = decideStoreMapper.getQuestion(creatNewExam);
            creatNewExam.setLevel("0");
        }else {
            decideStoreList = decideStoreMapper.getQuestion(creatNewExam);

        }

        return decideStoreList;
    }
}
