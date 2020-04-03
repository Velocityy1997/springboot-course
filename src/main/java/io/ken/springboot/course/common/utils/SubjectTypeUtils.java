package io.ken.springboot.course.common.utils;

/**
 * @author ZhaoWenHao
 * 科目类型工具
 * @create 2020-01-07 11:05
 **/
public class SubjectTypeUtils {


    /**
     * 将科目转为对应的数字
     * @param subject
     * @return
     */
    public static String subjectType(String subject){
         String result=null;

        if(subject.equals("北斗手持机")){
            result="1";
        } else if (subject.equals("电台操作")) {
            result="2";
        } else if (subject.equals("理论知识")) {
            result="3";
        } else if (subject.equals("航片判读")) {
            result="4";
        } else if (subject.equals("作战计算")) {
            result="5";
        } else if (subject.equals("识图用图")) {
            result="6";
        }else {
            result=subject;
        }
        return result;
    }

}
