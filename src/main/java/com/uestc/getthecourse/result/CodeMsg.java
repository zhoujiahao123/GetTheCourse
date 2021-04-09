package com.uestc.getthecourse.result;

public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 给出一些通用的返回码
     * @return
     */
    //约定：200表示返回正确结果
    public static CodeMsg SUCCESS = new CodeMsg(200,"success");

    //约定：501表示用户不存在，502表示输入的密码错误
    public static CodeMsg USER_EMPTY = new CodeMsg(501,"用户不存在");
    public static CodeMsg USER_PASSWORD_ERROR = new CodeMsg(502,"用户密码错误");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 往msg中填充参数
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String msg = String.format(this.msg, args);
        return new CodeMsg(code, msg);
    }
}