package io.ken.springboot.course.tools;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PhotoUtil {
	//存图片
	public static String photoToSql(MultipartFile file) {
		BASE64Encoder encoder = new BASE64Encoder();		
	    String image = "";
		
		try {
			image = encoder.encode(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return image;
	
}
	//取图片
	public static byte[] sqlTophoto(String photo) {
		byte[] bytes = photo.getBytes();
		try {
			String pph = new String(bytes,"UTF-8");
			BASE64Decoder encoder = new BASE64Decoder();
			byte[] data = encoder.decodeBuffer(pph);
			for(int i=0;i<data.length;i++) {
				if(data[i] < 0) {
					data[i] += 256;
				}
			}
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	
}
}