package io.ken.springboot.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ken.springboot.course.bean.QuestionType;
import io.ken.springboot.course.service.IQuestionTypeService;
import io.ken.springboot.course.tools.Result4;
import io.ken.springboot.course.tools.TypeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @author ZhaoWenHao
 * QuestionTypeController.java
 * @create 2020-01-06 15:11
 **/
@Controller
@RequestMapping("/questionType")
public class QuestionTypeController {

    @Autowired
    private IQuestionTypeService questionTypeServiceImpl;

    /**
     * 按科目查试题类型
     *
     * @param subject
     * @return
     */
    @ApiOperation(value = "按科目查试题类型", notes = "按科目查试题类型")
    @RequestMapping(value = "/selectQuestionType", method = RequestMethod.GET)
    @ResponseBody
    public Result4 selectQuestionType(String subject, @RequestParam(value = "limit") Integer limit, @RequestParam(value = "offset") Integer offset) {
        Result4 result4 = new Result4();
        try {
            PageHelper.startPage(offset, limit);
            boolean isNum = subject.matches("[0-9]+");  
            if(!isNum) {
            	subject = TypeUtil.getSubject(subject);
            }
            List<QuestionType> list = questionTypeServiceImpl.selectQuestionType(subject);

            PageInfo<QuestionType> questionTypePageInfo = new PageInfo<QuestionType>(list);
            result4.setCode(200);
            result4.setMsg("查询成功");
            result4.setTotal(questionTypePageInfo.getTotal());
            result4.setRows(questionTypePageInfo.getList());
            return result4;
        } catch (Exception e) {
            e.printStackTrace();
            result4.setCode(500);
            result4.setMsg("查询失败");
            result4.setRows(null);
            return result4;
        }
    }

    /**
     * 添加科目类型
     *
     * @param questionType
     * @return
     */
    @ApiOperation(value = "添加类型", notes = "添加类型")
    @RequestMapping(value = "/addQuestionType")
    @ResponseBody
    public Result4 addQuestionType(QuestionType questionType) {

        Result4 result4 = new Result4();
        try {
            //转换科目为对应数字
            questionType.setSubject(TypeUtil.getSubject(questionType.getSubject()));

            //查询是否已经存在
            QuestionType questionType1 = questionTypeServiceImpl.selectRepeat(questionType);
            if (questionType1 != null) {
                result4.setCode(400);
                result4.setMsg("该类型已经存在");
            } else {
                int result = questionTypeServiceImpl.addQuestionType(questionType);
                if (result > 0) {
                    result4.setCode(200);
                    result4.setMsg("添加成功");
                } else {
                    result4.setCode(500);
                    result4.setMsg("添加失败");
                }
                return result4;
            }

            result4.setCode(500);
            result4.setMsg("添加失败");
            return result4;
        } catch (Exception e) {
            result4.setCode(500);
            result4.setMsg("添加失败");
            return result4;
        }
    }

    /**
     * 修改科目类型
     *
     * @param questionType
     * @return
     */
    @ApiOperation(value = "修改试题", notes = "修改")
    @RequestMapping("/updateQuestionType")
    @ResponseBody
    public Result4 updateQuestionType(QuestionType questionType) {

        Result4 result4 = new Result4();
        try {
            int result = questionTypeServiceImpl.updateQuestionType(questionType);
            if (result > 0) {
                result4.setCode(200);
                result4.setMsg("修改成功");
            } else {
                result4.setCode(500);
                result4.setMsg("修改失败");
            }
            return result4;
        } catch (Exception e) {
            result4.setCode(500);
            result4.setMsg("修改失败");
            return result4;
        }
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping("/deleteQuestionType")
    @ResponseBody
    public Result4 deleteQuestionType(String id) {
        Result4 result4 = new Result4();

        try {
            String[] ids = id.split(",");
            int result = questionTypeServiceImpl.deleteQuestionType(ids);
            if (result > 0) {
                result4.setCode(200);
                result4.setMsg("删除成功");
            } else {
                result4.setCode(500);
                result4.setMsg("删除失败");
            }
            return result4;
        } catch (Exception e) {
            result4.setCode(500);
            result4.setMsg("删除失败");
            return result4;
        }
    }
}
