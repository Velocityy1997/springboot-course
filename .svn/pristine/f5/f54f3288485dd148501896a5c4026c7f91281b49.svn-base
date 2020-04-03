package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.dao.ChooseSelectStoreMapper;
import io.ken.springboot.course.model.ExcelModel;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.service.IChooseSelectStoreService;
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
@Service("ChooseSelectStoreService")
public class ChooseSelectStoreServiceImpl implements IChooseSelectStoreService {

    @Resource
    private ChooseSelectStoreMapper chooseSelectStoreMapper;

    @Override
    public ChooseSelectStore getNameByCode(String fillQCode) {
        // TODO Auto-generated method stub
        ChooseSelectStore fill = chooseSelectStoreMapper.getNameByCode(fillQCode);
        return fill;
    }

    @Override
    public List<Map<String, Object>> getSelectQuestionRandom(String subject) {
        List<Map<String, Object>> list = chooseSelectStoreMapper.getSelectQuestionRandom(subject);
        return list;
    }

    @Override
    public List<ChooseSelectStore> getAll() {
        return chooseSelectStoreMapper.queryAll();
    }

    @Override
    public int addExcel(List<ExcelModel> chooseSelectStoreList) {

        return chooseSelectStoreMapper.addExcel(chooseSelectStoreList);
    }

    @Override
    public ExcelModel queryName(String name, String result) {
        ExcelModel excelModel = chooseSelectStoreMapper.queryName(name, result);
        return excelModel;
    }

    @Override
    public int updateQuestions(ExcelModel excelModel) {

        return chooseSelectStoreMapper.updateQuestions(excelModel);
    }

    @Override
    public List<ChooseSelectStore> getByName(String name) {
        // TODO Auto-generated method stub

        List<ChooseSelectStore> comm = chooseSelectStoreMapper.getByName(name);
        return comm;
    }

    @Override
    public int add(ChooseSelectStore exam) {
        // TODO Auto-generated method stub
        return chooseSelectStoreMapper.add(exam);
    }

    @Override
    public int addModel(ChooseSelectStore exam) {
        // TODO Auto-generated method stub
        return chooseSelectStoreMapper.add(exam);
    }

    @Override
    public int getCount(String subject, int flag, String level) {

        return chooseSelectStoreMapper.getCount(subject, flag, level);
    }

    @Override
    public List<ChooseSelectStore> getQuestion(CreatNewExam creatNewExam) {

        List<ChooseSelectStore> chooseSelectStoreList = new ArrayList<>();
        if ("0".equals(creatNewExam.getLevel())){
            creatNewExam.setLevel("1,2,3,4,5,6");
            chooseSelectStoreList = chooseSelectStoreMapper.getQuestion(creatNewExam);
            creatNewExam.setLevel("0");
        }else {
             chooseSelectStoreList = chooseSelectStoreMapper.getQuestion(creatNewExam);

        }

        return chooseSelectStoreList;
    }
}
