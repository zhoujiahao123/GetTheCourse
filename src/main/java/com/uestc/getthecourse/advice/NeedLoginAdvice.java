package com.uestc.getthecourse.advice;

import com.uestc.getthecourse.config.Const;
import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.exception.GlobalException;
import com.uestc.getthecourse.redis.RedisService;
import com.uestc.getthecourse.result.CodeMsg;
import com.uestc.getthecourse.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@Aspect
public class NeedLoginAdvice {
    @Resource
    UserService userService;

    @Pointcut("execution(* com.uestc.getthecourse.controller.*.*(..))")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void needLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        isLogin(request, response);
    }

    /**
     * 从request从拿到cookie，然后拿到token，再判断是否已经登录
     *
     * @param request
     */
    public void isLogin(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) throw new GlobalException(CodeMsg.COOKIE_NOT_PRESENT);
        String value = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Const.COOKIE_NAME_TOKEN)) {
                value = cookie.getValue();
                break;
            }
        }
        Student student = userService.getStudentByToken(value, response);
        if (student == null) throw new GlobalException(CodeMsg.NEED_LOGIN);
    }
}

