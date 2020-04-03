package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.dao.ChooseSelectStoreMapper;
import io.ken.springboot.course.dao.CommExamMapper;
import io.ken.springboot.course.dao.DecideStoreMapper;
import io.ken.springboot.course.dao.FillExamMapper;
import io.ken.springboot.course.dao.HandExamMapper;
import io.ken.springboot.course.dao.PaperMapper;
import io.ken.springboot.course.model.QuestionModel;
import io.ken.springboot.course.service.IPaperService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("PaperService")
public class PaperServiceImpl  implements IPaperService{

	@Resource
	private PaperMapper paperMapper;

	@Resource
	private ChooseSelectStoreMapper chooseSelectStoreMapper;

	@Resource
	private FillExamMapper fillExamMapper;

	@Resource
	private DecideStoreMapper decideStoreMapper;

	@Resource
	private HandExamMapper handExamMapper;

	@Resource
	private CommExamMapper commExamMapper;

	@Override
	public List<Map<String, Object>> getPapers(String subject) {
		// TODO Auto-generated method stub
		return paperMapper.getPapers(subject);
	}

	@Override
	public List<QuestionType> getSubjectSons(Integer subject) {
		return paperMapper.querySons(subject);
	}

	@Override
	public List<QuestionModel> getFillBySubject(int subject) {
		List<QuestionModel> list = new ArrayList<QuestionModel>();
		list = paperMapper.queryFillBySubject(subject);
		return setcategoryTool(list,"填空");
	}

	@Override
	public List<QuestionModel> getChooseBySubject(int subject) {

		List<QuestionModel> list = new ArrayList<QuestionModel>();
		list =paperMapper.queryChooseBySubject(subject);
		return setcategoryTool(list,"选择");
	}

	@Override
	public List<QuestionModel> getDecideBySubject(int subject) {

		List<QuestionModel> list = new ArrayList<QuestionModel>();
		list =paperMapper.queryDecideBySubject(subject);
		return setcategoryTool(list,"判断");
	}

	@Override
	public List<QuestionModel> getHandBySubject(int subject) {

		List<QuestionModel> list = new ArrayList<QuestionModel>();
		list =paperMapper.queryHandBySubject(subject);
		return setcategoryTool(list,"操作");
	}

	public List<QuestionModel> setcategoryTool(List<QuestionModel> list,String categoryName) {
		for (QuestionModel questionModel : list) {
			questionModel.setType(categoryName);
			questionModel.setType1(categoryName);
			questionModel.setOperation("查看");
		}
		return list;
	}

	/**
	 * 判断题是否已经存在
	 * 不存在返回ture,存在返回false
	 * @param questionType:题型
	 * @param questionName：题目内容
	 * @return
	 */
	public boolean questionExist(String questionType , String questionName) {

		boolean tag = true;

		if ("1".equals(questionType)) {
			//填空
			List<FillBlankExamStore> list = fillExamMapper.getByName(questionName);

			if (list.size() == 0) {
				tag = false;
			}

		}else if ("2".equals(questionType)) {
			//选择
			List<ChooseSelectStore> list = chooseSelectStoreMapper.getByName(questionName);
			if (list.size() == 0) {
				tag = false;
			}

		} else if ("3".equals(questionType)) {
			//判断
			List<DecideStore> list = decideStoreMapper.getByName(questionName);
			if (list.size() == 0) {
				tag = false;
			}

		} else if ("4".equals(questionType)) {
			//操作
			List<HandExamStore> list = handExamMapper.getByName(questionName);
			if (list.size() == 0) {
				tag = false;
			}

		} else {

		}

		return tag;
	}
}
