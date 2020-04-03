package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.dao.HandExamMapper;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.service.IHandExamService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("HandExamService")
public class HandExamServiceImpl implements IHandExamService {

    @Resource
    private HandExamMapper handExamMapper;

    @Override
    public HandExamStore getNameByCode(String handQCode) {
        // TODO Auto-generated method stub
        HandExamStore model = new HandExamStore();
        model = handExamMapper.getNameByCode(handQCode);
        return model;
    }

    @Override
    public List<Map<String, String>> getQuestionRandom(String subject) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = handExamMapper.getQuestionRandom(subject);
        return list;
    }

    @Override
    public List<HandExamStore> getQuestionRandom1(String subject) {
        // TODO Auto-generated method stub
        List<HandExamStore> list = handExamMapper.getQuestionRandom1(subject);
        return list;
    }

    @Override
    public int addModel(HandExamStore handExamStore) {
        return handExamMapper.insertModel(handExamStore);
    }

    @Override
    public int getCount(String subject, double begin, double end, String level) {
        return handExamMapper.getCount(subject, begin, end, level);
    }

    @Override
    public List<HandExamStore> getQuestion(CreatNewExam creatNewExam, double begin, double end) {
        List<HandExamStore> handExamStoreList = new ArrayList<HandExamStore>();
        //音量全开和静音不能在同一张试卷，题号分别为1.3,1.5
        int num = 0;
        while (num != 2) {
            num = 0;
            if (creatNewExam.getLevel().equals("0")) {
                creatNewExam.setLevel("1,2,3,4,5,6");
                handExamStoreList = handExamMapper.getQuestion(creatNewExam);
                creatNewExam.setLevel("0");
            } else {
                handExamStoreList = handExamMapper.getQuestion(creatNewExam);
            }
            for (HandExamStore h : handExamStoreList) {
                if (h.getQuestionCode().equals("1.3") || h.getQuestionCode().equals("1.5")) {
                    num++;
                }
            }
        }
        return handExamStoreList;
    }
}
