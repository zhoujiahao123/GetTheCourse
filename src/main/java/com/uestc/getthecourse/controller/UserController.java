package com.uestc.getthecourse.controller;

import com.uestc.getthecourse.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login")
    public boolean login(@RequestParam("studentId") String studentId, @RequestParam("password") String password){
        return userService.login(studentId, password);
    }

}
