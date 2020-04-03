package io.ken.springboot.course.tools;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author GouYudong
 * @create 2019-11-19 15:51
 **/
public class RestResponse<T> {

    @JsonProperty("code")
    private String code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("rows")
    private T rows;


    public RestResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
