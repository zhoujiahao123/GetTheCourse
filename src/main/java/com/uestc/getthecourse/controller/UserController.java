package com.uestc.getthecourse.controller;

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
    @ResponseBody
    public Result<String> login(@RequestParam("studentId") String studentId, @RequestParam("password") String password, HttpServletResponse response){
        return userService.login(studentId, password,response);
    }

}
