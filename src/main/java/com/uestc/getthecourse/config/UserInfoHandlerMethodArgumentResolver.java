package com.uestc.getthecourse.config;

import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.redis.RedisService;
import com.uestc.getthecourse.redis.UserKey;
import com.uestc.getthecourse.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    RedisService redisService;

    @Resource
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(UserInfo.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String token = getTokenByCookies(request, Const.COOKIE_NAME_TOKEN);
        if (token == null) return null;
        //todo 从redis中取对象
        Student student = redisService.get(UserKey.user_token, token, Student.class);
        userService.addCookie(response,token,student);
        return student;
    }

    private String getTokenByCookies(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
