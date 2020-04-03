package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.UserLevelDic;
import java.util.List;

public interface IUserLevelDicService {

	String getLevelName(String level);

	List<UserLevelDic> getList();

}
