package com.hwsafe.template.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author chengql
 * 
 * @Api：用在类上，说明该类的作用。
 * 
 *                    @ApiOperation：注解来给API增加方法说明。
 * 
 *                    @ApiImplicitParams : 用在方法上包含一组参数说明。
 * 
 * @ApiImplicitParam：用来注解来给方法入参增加说明。
 * 
 *                                   @ApiResponses：用于表示一组响应
 * 
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 * 
 *                                               l code：数字，例如400
 * 
 *                                               l message：信息，例如"请求参数没填好"
 * 
 *                                               l response：抛出异常的类
 * 
 * @ApiModel：描述一个Model的信息（一般用在请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 * 
 *                                                                l @ApiModelProperty：描述一个model的属性
 *
 *
 *                                                                http://localhost:port/swagger-ui.html
 */
@Api
@RestController(value = "SwaggerSimpleController|一个用来测试swagger注解的控制器")
@Controller
public class SwaggerSimpleController {

    @ResponseBody
    @RequestMapping(value = "/getUserName", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户编号获取用户姓名", notes = "test: 仅1和2有正确返回")
    @ApiImplicitParam(paramType = "query", name = "userNumber", value = "用户编号", required = true, dataType = "Integer")
    public String getUserName(@RequestParam Integer userNumber) {
        if (userNumber == 1) {
            return "张三丰";
        } else if (userNumber == 2) {
            return "慕容复";
        } else {
            return "未知";
        }
    }

    @ResponseBody
    @RequestMapping("/updatePassword")
    @ApiOperation(value = "修改用户密码", notes = "根据用户id修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newPassword", value = "新密码", required = true, dataType = "String") })
    public String updatePassword(@RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "newPassword") String newPassword) {
        if (userId <= 0 || userId > 2) {
            return "未知的用户";
        }
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)) {
            return "密码不能为空";
        }
        if (password.equals(newPassword)) {
            return "新旧密码不能相同";
        }
        return "密码修改成功!";
    }

}
