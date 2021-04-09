package com.uestc.getthecourse.service;


import com.mysql.jdbc.StringUtils;
import com.uestc.getthecourse.dao.UserDao;
import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.exception.GlobalException;
import com.uestc.getthecourse.redis.RedisService;
import com.uestc.getthecourse.redis.UserKey;
import com.uestc.getthecourse.result.CodeMsg;
import com.uestc.getthecourse.result.Result;
import com.uestc.getthecourse.util.MD5Util;
import com.uestc.getthecourse.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    UserDao userDao;
    @Resource
    RedisService redisService;

    public Result<String> login(String studentId, String password, HttpServletResponse response) {
        Student student = userDao.getUserById(studentId);
        if (student == null) throw new GlobalException(CodeMsg.USER_EMPTY);
        String salt = student.getSlat();
        String dbPassword = student.getPassword();
        String inputPassword = MD5Util.inputPassToDBPass(password, salt);
        if (dbPassword.equals(inputPassword)) {
            //todo 放入redis
            String token = UUIDUtil.uuid();
            addCookie(response, token, student);
            return Result.success(token);
        }
        return Result.error(CodeMsg.USER_PASSWORD_ERROR);
    }

    public static final String COOKIE_NAME_TOKEN = "token";

    public void addCookie(HttpServletResponse response, String token, Student student) {
        redisService.set(UserKey.user_token, token, student);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.user_token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
