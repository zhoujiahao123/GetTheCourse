package com.uestc.getthecourse.exception;


import com.uestc.getthecourse.result.CodeMsg;
import com.uestc.getthecourse.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 使用RestControllerAdvice可以代替@ControllerAdvice+@ResponseBody
 * 一个全局的异常处理类：
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if(e instanceof BindException){
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            System.err.println("---------------------------------");
            System.err.println(msg);
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else if(e instanceof GlobalException){
            CodeMsg cm = ((GlobalException) e).getCodeMsg();
            return Result.error(cm);
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}