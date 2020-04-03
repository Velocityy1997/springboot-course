package io.ken.springboot.course.model.exam;

import java.util.Map;

/**
 * 手持机外部数据结构
 * @author GouYudong
 * @create 2019-11-22 15:22
 **/
public class MobileOutModel {

    private Map<String,MobileInModel> map;

    public Map<String, MobileInModel> getMap() {
        return map;
    }

    public void setMap(Map<String, MobileInModel> map) {
        this.map = map;
    }
}
