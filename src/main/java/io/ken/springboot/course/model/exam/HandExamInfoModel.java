package io.ken.springboot.course.model.exam;

import java.util.Map;

/**
 * @author GouYudong
 * @create 2019-12-09 11:12
 **/
public class HandExamInfoModel {

    private   Map<String, Map<String, String>> map;
    private  boolean handSaveFlag ;

    public Map<String, Map<String, String>> getMap() {
        return map;
    }

    public void setMap(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    public boolean getHandSaveFlag() {
        return handSaveFlag;
    }

    public void setHandSaveFlag(boolean handSaveFlag) {
        this.handSaveFlag = handSaveFlag;
    }
}
