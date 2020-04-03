package io.ken.springboot.course.controller.exam;

import io.ken.springboot.course.bean.StudentExamInfo;
import io.ken.springboot.course.service.implement.HandInfoServiceImpl;
import io.ken.springboot.course.tools.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GouYudong
 * @create 2019-12-23 16:24
 **/

@RestController
@RequestMapping("/exam")
public class HandInController {

    @Resource
    private HandInfoServiceImpl handInfoServiceImpl;


    /**
     * 最新交卷
     * @param examName
     * @return
     */
    @ApiOperation(value = "查考生最新交卷",notes = "查考生最新交卷")
    @ResponseBody
    @RequestMapping(value = "/getNew",method = RequestMethod.POST)
    public Result getInfo1(String examName) {
        Result result = new Result();

        //
        List<StudentExamInfo> list = new ArrayList<StudentExamInfo>();
        if (examName == null || examName.equals("")) {

            result.setSuccess(false);
            result.setMsg("考试名称为空");
            result.setData("");
            result.setCode(404);

        } else {
            try {
                list = handInfoServiceImpl.getNewHandInfo(examName);

                if (list.size() > 0) {
                    result.setSuccess(true);
                    result.setMsg("查询成功");
                    result.setData(list);
                    result.setCode(200);



                } else {
                    result.setSuccess(false);
                    result.setMsg("暂无考生交卷");
                    result.setData("");
                    result.setCode(404);
                }

            } catch (Exception e) {

                e.printStackTrace();

                result.setSuccess(false);
                result.setMsg("查询失败，请重试！");
                result.setData("");
                result.setCode(500);

            }

        }

        return result;

    }

    /**
     * 查最新交卷
     * @param examName
     * @return
     */
    @ApiOperation(value = "查最新交卷",notes = "查最新交卷")
    @ResponseBody
    @RequestMapping(value = "/getNewStudent",method = RequestMethod.POST)
    public Result getInfo6(String examName) {
        Result result = new Result();

        //
        List<StudentExamInfo> list = new ArrayList<StudentExamInfo>();
        StudentExamInfo studentExamInfo = new StudentExamInfo();

        if (examName == null || examName.equals("")) {

            result.setSuccess(false);
            result.setMsg("考试名称为空");
            result.setData("");
            result.setCode(404);

        } else {
            try {
                list = handInfoServiceImpl.getNewHandInfo(examName);

                if (list.size() > 0) {

                    studentExamInfo = list.get(0);

                    result.setSuccess(true);
                    result.setMsg("查询成功");
                    result.setData(studentExamInfo);
                    result.setCode(200);



                } else {
                    result.setSuccess(false);
                    result.setMsg("暂无考生交卷");
                    result.setData("");
                    result.setCode(404);
                }

            } catch (Exception e) {

                e.printStackTrace();

                result.setSuccess(false);
                result.setMsg("查询失败，请重试！");
                result.setData("");
                result.setCode(500);

            }

        }

        return result;

    }

    /**
     * 获取考生排名情况
     * @param examName
     * @return
     */
    @ApiOperation(value ="获取考生排名情况",notes = "获取考生排名情况")
    @ResponseBody
    @RequestMapping(value = "/getOrder",method = RequestMethod.POST)
    public Result getInfo2(String examName) {
        Result result = new Result();

        //
        List<StudentExamInfo> list = new ArrayList<StudentExamInfo>();
        if (examName == null || "".equals(examName)) {

            result.setSuccess(false);
            result.setMsg("考试名称为空");
            result.setData("");
            result.setCode(404);

        } else {
            try {

                list = handInfoServiceImpl.getExamOrder(examName);

                if (list.size() > 0) {
                    result.setSuccess(true);
                    result.setMsg("查询成功");
                    result.setData(list);
                    result.setCode(200);

                } else {
                    result.setSuccess(false);
                    result.setMsg("暂无考生交卷");
                    result.setData("");
                    result.setCode(404);
                }

            } catch (Exception e) {

                e.printStackTrace();

                result.setSuccess(false);
                result.setMsg("查询失败，请重试！");
                result.setData("");
                result.setCode(500);

            }

        }

        return result;

    }

    /**
     * 考试进度
     * @param
     * @return
     */
    @ApiOperation(value ="考试进度",notes = "考试进度")
    @ResponseBody
    @RequestMapping(value = "/getProgress",method = RequestMethod.POST)
    public Result getInfo3(String examName) {
        Result result = new Result();

        String rate = "";
            try {
                rate = handInfoServiceImpl.getUnHandleExam(examName);

                if (rate.equals("0")) {

                    result.setSuccess(false);
                    result.setMsg("本场考试暂无考生");
                    result.setData("");
                    result.setCode(404);

                } else {
                    result.setSuccess(true);
                    result.setMsg("查询成功");
                    result.setData(rate);
                    result.setCode(200);
                }

            } catch (Exception e) {

                e.printStackTrace();

                result.setSuccess(false);
                result.setMsg("查询失败，请重试！");
                result.setData("");
                result.setCode(500);

            }


        return result;

    }

    /**
     * 获取当前考试列表
     * @param
     * @return
     */
    @ApiOperation(value ="获取进行中的考试列表",notes = "获取进行中的考试列表")
    @ResponseBody
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public Result getInfo4() {
        Result result = new Result();

        //
       // List<Exam> list = new ArrayList<Exam>();
        List<String> list = new ArrayList<String>();

            try {
                list = handInfoServiceImpl.getExamName();

                //list = handInfoServiceImpl.getExamOrder("11");

                if (list.size() > 0) {
                    result.setSuccess(true);
                    result.setMsg("查询成功");
                    result.setData(list);
                    result.setCode(200);

                } else {
                    result.setSuccess(false);
                    result.setMsg("暂无进行中的考试");
                    result.setData("");
                    result.setCode(404);
                }

            } catch (Exception e) {


                e.printStackTrace();

                result.setSuccess(false);
                result.setMsg("查询失败，请重试！");
                result.setData("");
                result.setCode(500);

            }


        return result;

    }

}
