package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.dao.CollectMapper;
import io.ken.springboot.course.service.ICollectService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * @author lq
 *CollectServiceImpl.java
 * 2019年11月19日
 */

@Service("CollectService")
public class CollectServiceImpl implements ICollectService{

	 @Resource
	 private CollectMapper  collectMapper;
	
	
	
	@Override
	public String getHandQues(String handNum) {
		// TODO Auto-generated method stub
		String handQue = collectMapper.getHandQues(handNum);
		return handQue;
	}
	
	@Override
	public String getFillQues(String fillNum) {
		String fillQue = collectMapper.getFillQues(fillNum);
		return fillQue;
		
	}
	
	@Override
	public String getCommQues(String commNum) {
		String commQue = collectMapper.getCommQues(commNum);
		return commQue;
		
	}

	
	
}
