package com.uestc.getthecourse.service;


import com.mysql.jdbc.StringUtils;
import com.uestc.getthecourse.dao.UserDao;
import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.exception.GlobalException;
import com.uestc.getthecourse.result.CodeMsg;
import com.uestc.getthecourse.result.Result;
import com.uestc.getthecourse.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    UserDao userDao;

    public Result<String> login(String studentId, String password) {
        Student student = userDao.getUserById(studentId);
        if(student == null) throw new GlobalException(CodeMsg.USER_EMPTY);
        String salt = student.getSlat();
        String dbPassword = student.getPassword();
        String inputPassword = MD5Util.inputPassToDBPass(password,salt);
        if(dbPassword.equals(inputPassword)){
            //todo 放入redis
            return null;
        }
        return Result.error(CodeMsg.USER_PASSWORD_ERROR);
    }
}
