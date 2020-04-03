package io.ken.springboot.course.model.exam;

/**
 * 手持机内部数据
 * @author 
 * @create 2019-11-22 15:20
 **/
public class MobileInModel1 {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    //构造函数
    public MobileInModel1() {
        this.value = "";
    }
}
