package io.ken.springboot.course.tools;

import java.util.Calendar;
import java.util.Random;

/**
 * 添加试题获取随机数
 * @author lq
 *RandomNum.java
 * 2019年12月3日
 */
public class RandomNum {
	private static long num=0;

	public static String getRandNum() {
		 Calendar calendar = Calendar.getInstance();
		 Random random = new Random();
		 int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		 int hour =calendar.get(Calendar.HOUR_OF_DAY);
		 int min = calendar.get(Calendar.MINUTE);
		 int second = calendar.get(Calendar.SECOND);
		 int millisecond=calendar.get(Calendar.MILLISECOND);
		 String number = String.valueOf(year)+String.valueOf(month)+String.valueOf(min)+ String.valueOf(second)+ String.valueOf(millisecond)+ String.valueOf(num);
		 num++;
		 System.out.println(number.length());
		return number;
	}

}
