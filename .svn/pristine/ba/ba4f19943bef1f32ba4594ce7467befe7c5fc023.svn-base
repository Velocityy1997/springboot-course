package io.ken.springboot.course.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
	public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
	public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
	
}
