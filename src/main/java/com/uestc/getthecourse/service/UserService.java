package com.uestc.getthecourse.service;


import com.mysql.jdbc.StringUtils;
import com.uestc.getthecourse.dao.UserDao;
import com.uestc.getthecourse.entity.Student;
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

    public boolean login(String studentId, String password) {
        Student student = userDao.getUserById(studentId);
        logger.info(student.toString());
        String salt = student.getSlat();
        String dbPassword = student.getPassword();
        String inputPassword = MD5Util.inputPassToDBPass(password,salt);
        if(dbPassword.equals(inputPassword)){
            return true;
        }
        return false;
    }
}
