package io.ken.springboot.course.service;

import io.ken.springboot.course.bean.User;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IUserService
 * user:hwy
 *
 * @date 2019年11月14日
 */
public interface IUserService {

    public User getUserByName(String name);

    public User getUserById(String id);

    public List<User> getAllUser();

    public int addUser(User user);

    public int getUserCount();

    public int deleteUserById(String name);

    public int updateByUser(User user);

    /* 查询单条数据*/
    public User getUserInfo(String id_number);

    public int getUserByClass(String classId);

    public int getType(String userId);

    public int updateByType(User user);

    public int delUserByIds(List<String> idList);

    /* public List<User> getFindUser(User user); */

    public List<User> getFindUser(String name, String classId, String levelId, String type);

    public List<Map<String, String>> getUserGroupByClass();

    public int getUserByClassAndSex(String classId);

    public Map<String, String> queryByidNumber(String idNumber);

    public List<String> getByIdNum(String idNum);

    public String getNameByIdNum(String idNum);

    public int updateTel(String tel, String idNumber);

    public int updatePhoto(String photo, String idNumber);

    public List<User> findUserByIdNumAndPassword(String idNumber, String passWord);

    public List<User> queryList(String classId);

    public int resetPd(String mdPd, String idNumber);

    public int changePd(String newPd, String idNumber);

    int addUserExcel(List<User> userList);

    /**
     * 自动出题追加待考
     * @param idNumber 要添加的人
     * @param testName 试卷名
     * @return
     */
    int updateExamNames(String[] idNumber, String testName);
}
