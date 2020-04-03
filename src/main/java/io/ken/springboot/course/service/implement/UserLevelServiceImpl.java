package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.UserLevelDic;
import io.ken.springboot.course.dao.UserLevelMapper;
import io.ken.springboot.course.service.IUserLevelDicService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("UserLevelService")
public class UserLevelServiceImpl  implements IUserLevelDicService{

	@Resource
	private UserLevelMapper userLevelMapper;
	
	
	
	@Override
	public String getLevelName(String level) {
		// TODO Auto-generated method stub
		
		String levelName = userLevelMapper.getLevelName(level);
		
		return levelName;
	}



	@Override
	public List<UserLevelDic> getList() {
		// TODO Auto-generated method stub
		List<UserLevelDic> list = new ArrayList<UserLevelDic>();
		 list = userLevelMapper.getList();
		return list;
	}

}
