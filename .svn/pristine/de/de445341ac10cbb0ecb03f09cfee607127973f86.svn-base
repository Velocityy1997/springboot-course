package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.Log;
import io.ken.springboot.course.dao.LogMapper;
import io.ken.springboot.course.model.LogModel;
import io.ken.springboot.course.service.ILogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("LogService")
public class LogServiceImpl  implements ILogService{

	
	@Resource
	private LogMapper logMapper;
	
	@Override
	public List<LogModel> getList(String name) {
		// TODO Auto-generated method stub
		List<LogModel> list = new ArrayList<LogModel>();
		list = logMapper.getList(name);
		return list;
	}

	@Override
	public int insertModel(Log log) {
		// TODO Auto-generated method stub
		return logMapper.insertModel(log) ;
	}

	

}
