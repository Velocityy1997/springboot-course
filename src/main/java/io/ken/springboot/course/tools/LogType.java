package io.ken.springboot.course.tools;


/**
 * 	根据操作type 进行对应操作
 * @author lq
 *LogType.java
 * 2019年11月27日
 */
public class LogType {

	
	
	
	public static String  getOperation(int type) {
		
		String operation = "";
		
		switch (type) {
		case 1:
			operation = "用户登录";
			break;
		case 2:
			operation = "新增一名用户";
			break;
		case 3:
			operation = "删除用户";
			break;
		case 4:
			operation = "修改用户";
			break;
		case 5:
			operation = "查询用户";
			break;
		default:
			break;
		}
		return operation;
		
	}
}
