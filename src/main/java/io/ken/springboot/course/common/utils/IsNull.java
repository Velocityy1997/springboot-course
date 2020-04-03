package io.ken.springboot.course.common.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * 判断对象是否为空
 *  @author: GouYudong
 *  创建时间:  2019年7月31日上午11:43:06
 */
public class IsNull {
	
	/**
	 * 判断字符串是否为空
	 * 为空返回 true;不为空返回false
	 * @param testStr
	 * @return
	 */
	public static boolean isStrNull (String testStr) {
		
		boolean tag = false;
		
		if (testStr == null || testStr.equals("")) {
			tag = true;
		}
		
		return tag;
	}
	
	/**
	 * 判断整数是否为空
	 * 为空返回 true;不为空返回false
	 * @param testInt
	 * @return
	 */
	public static boolean isIntegerNull (Integer testInt) {
		
		boolean tag = false;
		
		if (testInt == null) {
			tag = true;
		}
		
		return tag;
	}
	
	/**
	 * 判断时间是否为空
	 * 为空返回 true;不为空返回false
	 * @param time
	 * @return
	 */
	public static boolean isDateNull (Date time) {
		
		boolean tag = false;
		
		if (time == null || time.equals("")) {
			tag = true;
		}
		
		return tag;
	}
	
	/**
	 * 判断集合list是否为空
	 * 为空返回 true;不为空返回false
	 * @param list
	 * @return
	 */
	public static <T> boolean isListNull (List<T> list) {
		
		boolean tag = false;
		
		
		if (list == null || list.size() == 0) {
			tag = true;
		}
		
		return tag;
	}
	
	/**
	 * 判断集合Map是否为空
	 * 为空返回 true;不为空返回false
	 * @param map
	 * @return
	 */
	public static <T> boolean isMapNull (Map<T,T> map) {
		
		boolean tag = false;
		
		if ( map == null || map.size() == 0) {
			tag = true;
		}
		
		return tag;
	}
	
	/**
	 * 判断字符串数组是否为空
	 * 为空返回 true;不为空返回false
	 * @param String[] strs
	 * @return
	 */
	public static <T> boolean isArrayNull (T[] array) {
		
		boolean tag = false;
		
		if ( array == null || array.length == 0) {
			tag = true;
		}
		
		return tag;
	}
	
	
	/**
	 * 判断对象是否为空
	 * @param t
	 * @return
	 */
	public static <T> boolean isObjectNull (T t) {
		
		boolean tag = false;
		
		if ( t == null) {
			tag = true;
		}
		
		return tag;
	}
	
	
	
	

}

