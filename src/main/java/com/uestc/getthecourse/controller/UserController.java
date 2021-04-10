package com.uestc.getthecourse.controller;

import com.uestc.getthecourse.config.UserInfo;
import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.result.Result;
import com.uestc.getthecourse.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestParam("studentId") String studentId, @RequestParam("password") String password, HttpServletResponse response) {
        return userService.login(studentId, password, response);
    }

    @PostMapping("/update_info")
    public Result<Boolean> updateInfo(@RequestParam("classes") String classes, @UserInfo Student student) {
        return userService.updateInfo(classes, student);
    }

    @PostMapping("/update_Pas")
    public Result<String> updatePassword(@RequestParam("sId") String sId, @RequestParam("pws") String password, @RequestParam("newPsw") String newPsw) {
        return null;
    }

}
