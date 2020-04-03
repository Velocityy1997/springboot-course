package io.ken.springboot.course.tools;

import io.ken.springboot.course.bean.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * 检查session状态
 * @author GouYudong
 * @create 2019-12-10 10:48
 **/


@Controller
@RequestMapping("/session")
public class SessionController {

    /**
     * 查看session
     * @param request
     * @return
     */
    @ApiOperation(value ="查看session" ,notes = "查看session")
    @RequestMapping(value = "/checkStatus", method = RequestMethod.GET)
    @ResponseBody
    public Result getInfo2(HttpServletRequest request) {

        Result result = new Result();

        User user = (User) request.getSession().getAttribute("loginInfo");// 取得当前登录用户

        if (user == null) {
            result.setMsg("false");
            result.setCode(404);
            result.setData("session过期");

        } else {
            result.setMsg("true");
            result.setCode(200);
            result.setData("session正常");
        }

        return result;

    }

}
