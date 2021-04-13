package com.uestc.getthecourse.service;


import com.uestc.getthecourse.config.Const;
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

    public Student getStudentByToken(String token, HttpServletResponse response) {
        Student student = redisService.get(UserKey.user_token, token, Student.class);
        if (student == null) throw new GlobalException(CodeMsg.INVALID_TOKEN);
        addCookie(response, token, student);
        return student;
    }


    /**
     * 用户登录
     * 1.数据库中查用户是否存在
     * 2. 对比密码是否正确
     * 3. 加入redis缓存中并设计过期时间
     *
     * @param studentId
     * @param password
     * @param response
     * @return
     */
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

    /**
     * 更改个人信息
     *
     * @param classes
     * @param student
     * @return
     */
    public Result<Boolean> updateInfo(String classes, Student student) {
        if (student == null) throw new GlobalException(CodeMsg.USER_EMPTY);
        int resCount = userDao.updateInfo(student.getStudentId(), classes);
        if (resCount <= 0) throw new GlobalException(CodeMsg.DB_ERROR);
        return Result.success(true);
    }

    private static final String SUCCESS_UPDATE_PSW = "修改密码成功";

    /**
     * 修改密码
     *
     * @param sId    学生学号
     * @param psw    学生密码
     * @param newPsw 新密码
     * @return
     */
    public Result<String> updatePaw(String sId, String psw, String newPsw) {
        Student student = userDao.getUserById(sId);
        if (student == null) throw new GlobalException(CodeMsg.USER_EMPTY);
        String salt = student.getSlat();
        String dbPassword = student.getPassword();
        String inputPassword = MD5Util.inputPassToDBPass(psw, salt);
        if (dbPassword.equals(inputPassword)) {
            newPsw = MD5Util.inputPassToDBPass(newPsw, salt);
            int result = userDao.updatePsw(sId, newPsw);
            if (result > 0) return Result.success(SUCCESS_UPDATE_PSW);
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        throw new GlobalException(CodeMsg.USER_PASSWORD_ERROR);
    }

    public void addCookie(HttpServletResponse response, String token, Student student) {
        redisService.set(UserKey.user_token, token, student);
        Cookie cookie = new Cookie(Const.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.user_token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
