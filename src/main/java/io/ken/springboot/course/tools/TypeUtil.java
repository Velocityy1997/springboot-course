package io.ken.springboot.course.tools;

public class TypeUtil {

    public static String getSubject(String subject) {

        if (subject.equals("北斗手持机")) {
            return "1";
        }
        if (subject.equals("电台操作")) {
            return "2";
        }
        if (subject.equals("理论知识")) {
            return "3";
        }
        if (subject.equals("航片判读")) {
            return "4";
        }
        if (subject.equals("作战计算")) {
            return "5";
        }
        if (subject.equals("识图用图")) {
            return "6";
        }
        return "1";
    }
    public static String getSubject(int type) {

        if (type==1){
            return "北斗手持机";
        }else if(type==2){
            return "电台操作";
        }else if(type==3){
            return "理论知识";
        }else if(type==4){
            return "航片判读";
        }else if(type==5){
            return "作战计算";
        }else if(type==6){
            return "识图用图";
        }
        return String.valueOf(type);
    }
    public static String getSubject1(String type) {

        if (type.equals("1")){
            return "北斗手持机";
        }else if(type.equals("2")){
            return "电台操作";
        }else if(type.equals("3")){
            return "理论知识";
        }else if(type.equals("4")){
            return "航片判读";
        }else if(type.equals("5")){
            return "作战计算";
        }else if(type.equals("6")){
            return "识图用图";
        }
        return String.valueOf(type);
    }
}
