package io.ken.springboot.course.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.ken.springboot.course.bean.ChooseSelectStore;
import io.ken.springboot.course.bean.CommExamStore;
import io.ken.springboot.course.bean.DecideStore;
import io.ken.springboot.course.bean.FillBlankExamStore;
import io.ken.springboot.course.bean.HandExamStore;
import io.ken.springboot.course.bean.User;
import io.ken.springboot.course.model.CollectModel;
import io.ken.springboot.course.service.IChooseSelectStoreService;
import io.ken.springboot.course.service.ICollectService;
import io.ken.springboot.course.service.ICommExamService;
import io.ken.springboot.course.service.IDecideStoreService;
import io.ken.springboot.course.service.IFillExamService;
import io.ken.springboot.course.service.IHandExamService;
import io.ken.springboot.course.service.IUserService;
import io.ken.springboot.course.tools.Result2;
import io.ken.springboot.course.tools.TypeUtil;

/**
 * 收藏试题
 * @author lq
 *	CollectController.java
 * 2019年11月19日
 */

@Controller
@RequestMapping("/collect")
public class CollectController {
	
	@Autowired
	private ICollectService collectService;
	
	@Autowired
	private IUserService userService;
	
	
	@Autowired
	private IHandExamService handService;
	@Autowired
	private IFillExamService  fillExamService;
	@Autowired
	private ICommExamService commExamService;
	@Autowired
	private IChooseSelectStoreService chooseSelectStoreService;
	@Autowired
	private IDecideStoreService decideStoreService;
	
	
	/**
	 * 收藏
	 * @param id_number
	 * @return
	 */
	
	@RequestMapping("/getques")
    @ResponseBody
	public Result2 getCollection(HttpServletRequest request ) {
		 
		Result2 result2 = new Result2();
		
		List<CollectModel> modeList1 = new ArrayList<CollectModel>();
		User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户
		
		try {
			//操作题题号
			String handCollect = user.getHandCollectList();
			if(handCollect != null && !(handCollect.equals(""))) {
				String handCollectList []= handCollect.split(";");
				for(int i = 0; i < handCollectList.length; i++) {
						CollectModel t1 = new CollectModel();
						String handNum = handCollectList[i];
						HandExamStore ss = handService.getNameByCode(handNum); 
						t1.setId(handNum);
						t1.setObject(ss.getQuestionName());
						t1.setTable_name("hand_exam_store");
						t1.setOperation("查看试卷");
						String type = TypeUtil.getSubject1(ss.getSubject());
						t1.setType(type);
						modeList1.add(t1);
				}
			}
			String fillCollect = user.getFillBlankCollectList();
			if(fillCollect != null && !(fillCollect.equals(""))) {
				String fillBlankCollectList [] = fillCollect.split(";");
				for (int i = 0; i < fillBlankCollectList.length; i++) {
						CollectModel t2 = new CollectModel();
						String fillNum = fillBlankCollectList[i];
						FillBlankExamStore fill = fillExamService.getNameByCode(fillNum);
						t2.setId(fillNum);
						t2.setObject(fill.getQuestionName());
						t2.setTable_name("fill_blank_exam_store");
						String type = TypeUtil.getSubject1(fill.getSubject());
						t2.setType(type);
						t2.setOperation("查看试卷");
						modeList1.add(t2);
				}
			}
			//填空题题号
			
			//通信题题号
			String commCollect = user.getCommCollectList();
			if(commCollect != null && !(commCollect.equals(""))) {
				String commCollectList [] = commCollect.split(";");
				for (int i = 0; i < commCollectList.length; i++) {
						CollectModel t3 = new CollectModel();
						
						String commNum = commCollectList[i];
						CommExamStore comm = commExamService.getNameByCode(commNum);
						t3.setId(commNum);
						t3.setObject(comm.getQuestionName());
						t3.setTable_name("comm_exam_store");
						String type = TypeUtil.getSubject1(comm.getSubject());
						t3.setType(type);
						t3.setOperation("查看试卷");
						modeList1.add(t3);
				}
			}	
			//选择题题号
			String chooseCollect = user.getChooseBlankCollectList();
			if(chooseCollect != null && !(chooseCollect.equals(""))) {
				String commCollectList [] = chooseCollect.split(";");
				for (int i = 0; i < commCollectList.length; i++) {
						CollectModel t3 = new CollectModel();
						String commNum = commCollectList[i];
						ChooseSelectStore fill = chooseSelectStoreService.getNameByCode(commNum);//题目
						t3.setId(commNum);
						t3.setObject(fill.getQuestionName());
						t3.setTable_name("choose_select_store");
						String type = TypeUtil.getSubject1(fill.getSubject());
						t3.setType(type);
						t3.setOperation("查看试卷");
						modeList1.add(t3);
				}
			}	
			//通信题题号
			String decideCollect = user.getDecideBlankCollectList();
			if(decideCollect != null && !(decideCollect.equals(""))) {
				String commCollectList [] = decideCollect.split(";");
				for (int i = 0; i < commCollectList.length; i++) {
						CollectModel t3 = new CollectModel();
						String commNum = commCollectList[i];
						DecideStore fill = decideStoreService.getNameByCode(commNum);//题目
						t3.setId(commNum);
						t3.setObject(fill.getQuestionName());
						String type = TypeUtil.getSubject1(fill.getSubject());
						t3.setType(type);
						t3.setTable_name("decide_store");
						t3.setOperation("查看试卷");
						modeList1.add(t3);
				}
			}	
			result2.setRows(modeList1);
			result2.setCode(200);
			result2.setMsg("msg");									
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
			 	result2.setRows(null);
				result2.setCode(404);
				result2.setMsg("msg");
		}										
		return result2;
		
	}

	
	
	
	
	/**
	 * 收藏
	 * @param id_number
	 * @return
	 */
	
	@RequestMapping("/collectExamSingle")
    @ResponseBody
	public Result2 collectExamSingle(HttpServletRequest request,String questionCode, String table ) {
		 
		User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户
		Result2 result2 = new Result2();
		
		List<CollectModel> modeList1 = new ArrayList<CollectModel>();
			
		try {
			if(table.equals("hand_exam_store")) {
				String handCollects = user.getHandCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					handCollects="";
				}
				boolean flag=true;
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					
					for(String hands:list) {
						if(hands.equals(questionCode)) {
							flag = false;
							break;
						}
					}
				}
				
				if(flag) {
					String handCollect = handCollects + questionCode + ";";
					user.setHandCollectList(handCollect);
				}				
			}
			if(table.equals("fill_blank_exam_store")) {
				String handCollects = user.getFillBlankCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					handCollects="";
				}
				boolean flag=true;
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					
					for(String hands:list) {
						if(hands.equals(questionCode)) {
							flag = false;
							break;
						}
					}
				}				
				if(flag) {
					String handCollect = handCollects + questionCode + ";";
					user.setFillBlankCollectList(handCollect);
				}				
			}
			if(table.equals("comm_exam_store")) {
				
				String handCollects = user.getCommCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					handCollects="";
				}
				boolean flag=true;
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");				
					for(String hands:list) {
						if(hands.equals(questionCode)) {
							flag = false;
							break;
						}
					}
				}				
				if(flag) {
					String handCollect = handCollects + questionCode + ";";
					user.setCommCollectList(handCollect);
				}				
			}	
			if(table.equals("choose_select_store")) {
				String handCollects = user.getChooseBlankCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					handCollects="";
				}
				boolean flag=true;
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					
					for(String hands:list) {
						if(hands.equals(questionCode)) {
							flag = false;
							break;
						}
					}
				}				
				if(flag) {
					String handCollect = handCollects + questionCode + ";";
					user.setChooseBlankCollectList(handCollect);
				}				
			}
			if(table.equals("decide_store")) {
				String handCollects = user.getDecideBlankCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					handCollects="";
				}
				boolean flag=true;
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					
					for(String hands:list) {
						if(hands.equals(questionCode)) {
							flag = false;
							break;
						}
					}
				}				
				if(flag) {
					String handCollect = handCollects + questionCode + ";";
					user.setDecideBlankCollectList(handCollect);
				}				
			}
			userService.updateByUser(user);	
			HttpSession session = request.getSession();
    		session.setAttribute("loginInfo", user);
			result2.setCode(200);
			result2.setMsg("msg");						
			
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
			 	result2.setRows(null);
				result2.setCode(404);
				result2.setMsg("msg");
		}
		
	      return result2;		
	}

	/**
	 * 删除收藏
	 * @param id_number
	 * @return
	 */
	
	@RequestMapping("/deletecollect")
    @ResponseBody
	public Result2 deleteCollect(HttpServletRequest request,String questionCode, String table ) {
		 
		User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户
		Result2 result2 = new Result2();
		
		List<CollectModel> modeList1 = new ArrayList<CollectModel>();
			
		try {
			if(table.equals("hand_exam_store")) {
				String handCollects = user.getHandCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					result2.setCode(200);
					result2.setMsg("删除成功");
					return result2;
				}
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					if(list != null) {
						List<String> list1= new ArrayList<String>(Arrays.asList(list));
						list1.remove(questionCode);
						String handCollect =  String.join(";", list1);
						user.setHandCollectList(handCollect);
					}
					
				}												
			}
			if(table.equals("fill_blank_exam_store")) {
				
				String handCollects = user.getFillBlankCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					result2.setCode(200);
					result2.setMsg("删除成功");
					return result2;
				}
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					if(list != null) {
						List<String> list1= new ArrayList<String>(Arrays.asList(list));
						list1.remove(questionCode);
						String handCollect =  String.join(";", list1);
						user.setFillBlankCollectList(handCollect);
					}
					
				}		
					
			}
			if(table.equals("comm_exam_store")) {
								
				String handCollects = user.getCommCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					result2.setCode(200);
					result2.setMsg("删除成功");
					return result2;
				}
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					if(list != null) {
						List<String> list1= new ArrayList<String>(Arrays.asList(list));
						list1.remove(questionCode);
						String handCollect =  String.join(";", list1);
						user.setCommCollectList(handCollect);
					}
					
				}							
			}	
			if(table.equals("choose_select_store")) {
							
				String handCollects = user.getChooseBlankCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					result2.setCode(200);
					result2.setMsg("删除成功");
					return result2;
				}
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					if(list != null) {
						List<String> list1= new ArrayList<String>(Arrays.asList(list));
						list1.remove(questionCode);
						String handCollect =  String.join(";", list1);
						user.setChooseBlankCollectList(handCollect);
					}
					
				}		
						
				}
			if(table.equals("decide_store")) {
				
				String handCollects = user.getDecideBlankCollectList();
				if(handCollects == null || handCollects.equals("null")) {
					result2.setCode(200);
					result2.setMsg("删除成功");
					return result2;
				}
				if(handCollects != null && !(handCollects.equals(""))) {
					String[] list = handCollects.split(";");
					if(list != null) {
						List<String> list1= new ArrayList<String>(Arrays.asList(list));
						list1.remove(questionCode);
						String handCollect =  String.join(";", list1);
						user.setDecideBlankCollectList(handCollect);
					}					
				}							
			}
			userService.updateByUser(user);	
			HttpSession session = request.getSession();
    		session.setAttribute("loginInfo", user);
			result2.setCode(200);
			result2.setMsg("删除成功");						
			
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
			 	result2.setRows(null);
				result2.setCode(404);
				result2.setMsg("删除失败");
		}
		
	return result2;
		
	}
	
	
}
