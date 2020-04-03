package io.ken.springboot.course.tools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



@Configuration
@ConfigurationProperties(prefix = "data")
@EnableConfigurationProperties(DataConfig.class)
//如果只有一个主配置类文件，@PropertySource可以不写
@PropertySource(value = "classpath:inputT9.properties",encoding = "UTF-8")
public class DataConfig {
	
	private Map<String, String> data1 = new HashMap<String, String>();
	private Map<String, String> data2 = new HashMap<String, String>();
	
	public Map<String, String> getData1() {
		return data1;
	}
	public void setData1(Map<String, String> data1) {
		this.data1 = data1;
	}
	public Map<String, String> getData2() {
		return data2;
	}
	public void setData2(Map<String, String> data2) {
		this.data2 = data2;
	}
    
	
}
