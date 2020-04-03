package io.ken.springboot.course.model;

/**
 * 练习
 * @author GouYudong
 * @create 2019-11-19 16:50
 **/
public class TrainModel {

   /* "time":"2019-09-10 15:00",//时间
            "object":"北斗手持机操作",  //科目
            "number":"10"  //答题数*/

    private String time;
    private String object;
    private String number;
    private String trainingId;

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
