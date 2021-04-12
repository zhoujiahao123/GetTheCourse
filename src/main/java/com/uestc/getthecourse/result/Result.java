package com.uestc.getthecourse.result;

import org.aspectj.apache.bcel.classfile.Code;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 正确的时候返回的结果
     */
    private Result(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }


    /**
     * 返回错误的结果
     *
     * @param codeMsg
     */
    private Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.data = null;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }


    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
