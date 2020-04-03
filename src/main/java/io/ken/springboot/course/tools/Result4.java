package io.ken.springboot.course.tools;

import io.ken.springboot.course.bean.QuestionType;
import java.util.List;

/**
 * @author ZhaoWenHao
 * 试题类型结果集
 * @create 2020-01-06 15:40
 **/
public class Result4 {

    private Integer code;

    private String msg;

    private List<QuestionType> rows;

    private Long total;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<QuestionType> getRows() {
        return rows;
    }

    public void setRows(List<QuestionType> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


}
