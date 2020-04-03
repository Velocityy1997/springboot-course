package io.ken.springboot.course.bean;

/**
 * @author GouYudong
 * @create 2020-01-07 9:38
 **/
public class Subject {

    private  String id;
    private  String type;
    private  String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
