package io.ken.springboot.course.common.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ZhaoWenHao
 * @create 2020-02-26 10:53
 **/
public class MultipartFileToFile {
static File file=null;
    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
         file=new File(multipartFile.getOriginalFilename());
        InputStream inputStream=multipartFile.getInputStream();
        OutputStream outputStream=new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return file;
    }
    public static void deleteFile(){
        File del = new File(file.toURI()); //删除项目产生的临时文件
        del.delete();
    }
}
