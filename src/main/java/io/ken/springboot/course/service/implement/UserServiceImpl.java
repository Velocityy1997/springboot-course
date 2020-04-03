package io.ken.springboot.course.service.implement;

import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.dao.ExamMapper;
import io.ken.springboot.course.dao.LogMapper;
import io.ken.springboot.course.dao.TrainMapper;
import io.ken.springboot.course.dao.UserMapper;
import io.ken.springboot.course.service.IUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserServiceImpl 
 * user:hwy
 * @date 2019年11月14日
 */
@Service("UserService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private LogMapper logMapper;
    @Resource
    private TrainMapper trainMapper;
	
	@Override
    public User getUserByName(String name){
		User user = new User();
		user = userMapper.queryByName(name);
        return user;
    }
	
	@Override
    public Map<String,String> queryByidNumber(String idNumber){
		Map<String,String> map = userMapper.queryByidNumber(idNumber);
        return map;
    }

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return userMapper.queryById(id);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userMapper.queryAll();
	}

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return userMapper.getUserCount();
	}
	
	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.add(user) == 1 ? 0 : 1;//0为插入成功，1为插入失败
	}

	@Override
	public int deleteUserById(String name) {
		// TODO Auto-generated method stub
		return userMapper.delByName(name) == 1 ? 0 : 1;//0为成功，1为失败
	}

	@Override
	public int updateByUser(User user) {
		// TODO Auto-generated mint
		int tag = 0;
		int  num  = userMapper.updateById(user);
		if (num == 1) {
			tag = 1;
		}
		return tag;
	}
	
	
	@Override
	public User getUserInfo(String id_number) {
		
		User user = new User();
		user =  userMapper.getUserInfo(id_number);
		return user;
		
	}
//每个班有多少人
	@Override
	public List<Map<String,String>> getUserGroupByClass() {
		
		List<Map<String,String>> map =  userMapper.getUserGroupByClass();
		return map;
		
	}
	
	@Override
	public int getType(String userId) {
		// TODO Auto-generated method stub
		int  type = userMapper.getType(userId);
		return type;
	}

	@Override
	public int updateByType(User user) {
		// TODO Auto-generated method stub
		return  userMapper.updateByType(user)== 1 ? 0 : 1;//0为插入成功，1为插入失败
	}

	@Override
	public int delUserByIds(List<String> idList) {
		// TODO Auto-generated method stub
		int num  = 0;
		for(int i =0; i < idList.size(); i++) {
			
			String id = idList.get(i);
			
			num = userMapper.delById(id);
			
			 if(num >0) { 
				 continue;
			 }else {
				 return num;
			}
		}
		return num;
	}

	/*
	 * @Override public List<User> getFindUser(User user) { // TODO Auto-generated
	 * method stub List<User> userList = new ArrayList<User>();
	 * 
	 * userList = userMapper.FindUser(user); return userList; }
	 */

	@Override
	public List<User> getFindUser(String name, String classId, String levelId, String type) {
		// TODO Auto-generated method stub
		List<User> userList = new ArrayList<User>();
		userList = userMapper.FindUser(name,classId,levelId,type);
		return userList;
	}

	
	@Override
	public int getUserByClassAndSex(String classId) {
		// TODO Auto-generated method stub
		return  userMapper.getUserByClassAndSex(classId);
	}

	@Override
	public int getUserByClass(String classId) {
		// TODO Auto-generated method stub
		return  userMapper.getUserByClass(classId);
	}

	
	@Override
	public List<String> getByIdNum(String idNum) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list = userMapper.getByIdNum(idNum);
		return list;
	}

	@Override
	public String getNameByIdNum(String idNum) {
		// TODO Auto-generated method stub
		
		String userName = userMapper.getNameByIdNum(idNum);
		return userName;
	}

	@Override
	public int updateTel(String tel, String idNumber) {
		int tag = 0;
		tag = userMapper.updateTelphone(tel,idNumber);

		return tag; //1 ? 0 : 1;//0为成功，1为失败
	}

	@Override
	public int updatePhoto(String photo, String idNumber) {
		int tag = 0;
		tag = userMapper.updatePhoto(photo,idNumber);

		return tag; //1 ? 0 : 1;//0为成功，1为失败
	}

	@Override
	public List<User> findUserByIdNumAndPassword(String idNumber, String passWord) {
		// TODO Auto-generated method stub
		
		List<User> list = userMapper.findUserByIdNumAndPassword( idNumber,  passWord);
		return list;
	}

	@Override
	public List<User> queryList(String classId) {
		// TODO Auto-generated method stub
		
		List<User>  list = new ArrayList<User>();
		list = userMapper.queryList(classId);
		return list;
	}

	@Override
	public int resetPd(String mdPd, String idNumber) {
		// TODO Auto-generated method stub
		return userMapper.resetPd(mdPd,idNumber);
	}

	@Override
	public int changePd(String newPd, String idNumber) {
		// TODO Auto-generated method stub
		return userMapper.changePd(newPd,idNumber);
	}

	@Override
	public int addUserExcel(List<User> userList) {

		return userMapper.addUserExcel(userList);
	}

	@Override
	public int updateExamNames(String[] idNumber, String testName) {
		/**
		 * 1.取出所在人员待考的信息
		 * 2.追加信息，进行添加
		 */
		List<User> userList=userMapper.selectExamNames(idNumber);
		List<User> userList1=new ArrayList<User>();
int num=0;
		for (User user:userList){
			if ("".equals(user.getExamNames()) ||user.getExamNames()==null){
				user.setExamNames(testName);
			}else {
				String examNames=user.getExamNames()+","+testName;
				user.setExamNames(examNames);
			}
			num=userMapper.updateExamNames(user);
		}
		return num;
	}
}
