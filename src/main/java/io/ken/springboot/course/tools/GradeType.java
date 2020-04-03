package io.ken.springboot.course.tools;


/**
 * 根据分数进行分数等级（优良中差）
 * @author lq
 *GradeType.java
 * 2019年11月29日
 */
public class GradeType {
	
	
	public static String getGradeType(String grades,String totalGrade) {
		
		
		String operation = "";	
		double scroe = Double.valueOf(grades) / Double.valueOf(totalGrade);

		if (scroe >= 0.60) {
			operation = "合格";
		} else {
			operation = "不合格";
		}
		return operation;
	}

}
