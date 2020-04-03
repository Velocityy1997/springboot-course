package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.dao.CommExamMapper;
import io.ken.springboot.course.model.exam.newExam.CreatNewExam;
import io.ken.springboot.course.service.ICommExamService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lq
 *CommExamServiceImpl.java
 * 2019年11月28日
 */
@Service("CommExamService")
public class CommExamServiceImpl implements ICommExamService{

	@Resource
	private CommExamMapper  commExamMapper;

	@Override
	public CommExamStore getNameByCode(String comCode) {
		// TODO Auto-generated method stub
		
		CommExamStore comm = commExamMapper.getNameByCode(comCode);
		return comm;
	}

	@Override
	public List<CommExamStore> getByName(String name) {
		// TODO Auto-generated method stub
		
		List<CommExamStore> comm = commExamMapper.getByName(name);
		return comm;
	}

	@Override
	public int getCount(String subject, String level) {
		return commExamMapper.getCount(subject,level);
	}

	@Override
	public List<CommExamStore> getQuestion(CreatNewExam creatNewExam) {
		List<CommExamStore> commExamStoreList=new ArrayList<CommExamStore>();
		if("0".equals(creatNewExam.getLevel())){
			creatNewExam.setLevel("1,2,3,4,5,6");
			commExamStoreList=commExamMapper.getQuestion(creatNewExam);
			creatNewExam.setLevel("0");
		}else {
			commExamStoreList=commExamMapper.getQuestion(creatNewExam);
		}
		return commExamStoreList;
	}

	@Override
	public int add(CommExamStore exam) {
		// TODO Auto-generated method stub
		return commExamMapper.add(exam);
	}

	@Override
	public List<Map<String, Object>> getCommQuestionRandom() {
		List<Map<String,Object>> list = commExamMapper.getCommQuestionRandom();
		return list;
	}

	
}
