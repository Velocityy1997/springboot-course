package io.ken.springboot.course.tools;

import io.ken.springboot.course.bean.Exam;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class BatchQueue {
	
	// 默认间隔处理队列时间
	private static int DEFAULT_TIME = 1000;
	// 默认队列处理长度
	private static int DEFAULT_COUNT = 300;
	private static ArrayBlockingQueue<Exam> queue = new ArrayBlockingQueue<Exam>(10000);

	//private static IExamService service = ApplicationContextProvider.getBean(IExamService.class);


    // 用来存放从队列拿出的数据
 	private List<Exam> dataList;
    // 设置队列处理时间
 	private long handleTime;
 	// 设置队列处理长度
 	private int handleLength;
    // 往队列添加数据
 	public void add(Exam t){
 		queue.add(t);
 	}
 	
    // 清理生成的list
 	public void clearList(){
 		dataList = null;
 		dataList = new ArrayList<Exam>();
 	}
 	
 	public BatchQueue(){
		 this(DEFAULT_TIME, DEFAULT_COUNT);
	}
 	public BatchQueue(int handleTime, int handleQueueLength){
		this.handleTime = handleTime;
		this.handleLength = handleQueueLength;
		start();
	}
 	
     private void  start(){
		
		dataList = new ArrayList<Exam>(handleLength);
		DataListener listener = new  DataListener();
		new Thread(listener).start();
		
	}
 	
  // 队列监听，当队列达到一定数量和时间后处理队列
 	class DataListener implements Runnable{
 		
 		public void run() {
 			long startTime = System.currentTimeMillis();
 			Exam t = null;
 			while(true) {
 				System.out.println("批量修改考试信息");
 				try {
 				// 从队列拿出队列头部的元素，如果没有就阻塞
					t = queue.take();
					if(null != t){
						 dataList.add(t);
					}
					if(dataList.size() >= DEFAULT_COUNT){
						startTime = callBack(dataList);
						continue;
  					}
					long currentTime = System.currentTimeMillis();
					if(currentTime - startTime > handleTime){
						startTime = callBack(dataList);
						continue;
					}
 				}
 				catch (Exception e) {
					e.printStackTrace();
				}
 			}			
 		}
 	}
 	private long callBack(List<Exam> dataList) {
		
		// 处理队列
		try{
			System.out.println("批量修改考试信息");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// 清理掉dataList中的元素
			clearList();
		}
		
		
		return System.currentTimeMillis();
	}
	
     
}
