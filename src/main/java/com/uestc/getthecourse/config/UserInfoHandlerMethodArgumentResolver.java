package com.uestc.getthecourse.config;
import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.exception.GlobalException;
import com.uestc.getthecourse.redis.RedisService;
import com.uestc.getthecourse.redis.UserKey;
import com.uestc.getthecourse.result.CodeMsg;
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
    UserService userService;

    /**
     * 判断哪些方法需要被方法参数解析器解析，带有注解Userinfo的参数都需要被解析。
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(UserInfo.class);
    }

    /**
     * 方法参数解决器：根据请求头中的cookie拿到token，再利用token从redis中取到student放入参数中
     *
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String token = getTokenByCookies(request, Const.COOKIE_NAME_TOKEN);
        if (token == null) return null;
        Student student = userService.getStudentByToken(token, response);
        if (student == null) throw new GlobalException(CodeMsg.TOKEN_INVALID);
        return student;
    }

    /**
     * 从前端传入的cookie中拿到token,约定用cookieName做key，token是对应的value值。
     *
     * @param request
     * @param cookieName
     * @return
     */
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
