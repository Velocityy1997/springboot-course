package io.ken.springboot.course.tools;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 

* @author 作者 Your-Name: 

* @version 创建时间：2019年7月11日 下午5:10:08 

* 类说明 

*/
@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter{
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
	    }
}
