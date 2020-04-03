package io.ken.springboot.course.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author ZhaoWenHao
 * @create 2020-01-08 14:37
 **/
public class TestFile {

    public static void main(String[] a) {
//            deleteFile();
            copyFile();
    }

    public static void copyFile(){

        try {

            FileInputStream in = null;
            FileOutputStream out = null;
            BufferedInputStream bI = null;
            BufferedOutputStream bO = null;
            String path = "C:/Users/hangguang/Desktop/导入试题耗时测试/";

            for (int i = 2; i <= 200; i++) {
                byte[] byteArray = new byte[1024];
                int num = 0;
                String j = String.valueOf(i);
                in = new FileInputStream("C://Users/hangguang/Desktop/导入试题耗时测试/1.jpg");
                bI = new BufferedInputStream(in);
                out = new FileOutputStream(path + j + ".jpg");
                bO = new BufferedOutputStream(out);
                while ((num = bI.read(byteArray)) != -1) {
                    bO.write(byteArray);
                }
                bO.flush();
            }
            bO.close();
            out.close();
            bI.close();
            in.close();
        } catch (Exception e) {

        }
    }


    /**
     * 删除图片
     */
    public static void deleteFile(){

        String path = "C:/Users/hangguang/Desktop/导入试题耗时测试/";
        for (int i=1;i<=200;i++){
            File file=new File(path+i+".jpg");
            file.delete();
        }
    }
}
