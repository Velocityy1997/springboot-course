package io.ken.springboot.course.tools;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: TimeTools
 * user:hwy
 * detail:获取当前时间
 * @date 2019年11月14日
 */
public class TimeTools {

	//获取当前系统时间年
	public static int getCurrentYear() {
		Calendar now = Calendar.getInstance();  
		return now.get(Calendar.YEAR);
	}
	
	//获取date数据的年
	public static int getDateTimeYear(Date date) {
		int year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(Calendar.YEAR);
		return year;
	}
	
	

	
	/**
	 * @author lq
	 * @param ts 考试开始时间
	 * @param duration 考试时长（分钟）
	 * @return
	 * @throws Exception//获取时间添加若干小时后的时间与系统时间相差多少秒
	 */
	public String getTime(Timestamp ts, int duration) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		Date sysDate = sdf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.MINUTE, duration);//开始时间加开始时长
		Date afDate = cal.getTime();
		
		long StarTime =sysDate.getTime();//系统时间
		long endTime  = afDate.getTime();//
		int  seconds = (int)((StarTime - endTime) / 1000);//计算相差时间（秒）
		
		String value = String .valueOf(seconds);	 
		return value;
	}
	
	
	/**
	 * 计算考试完成时间 时分秒
	 * @throws ParseException 
	 */
	
	public String getCompleteTime(Timestamp  startTime,Timestamp endTime) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date begin=sdf.parse((sdf.format(startTime)));
		java.util.Date end = sdf.parse((sdf.format(endTime))); 
		long between=(end.getTime()-begin.getTime());
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		
		String time= hour+"小时"+min+"分"+s+"秒";
		return time;
		
	}
	
	
}
