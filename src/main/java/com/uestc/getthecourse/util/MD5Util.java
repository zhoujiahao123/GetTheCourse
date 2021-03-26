package com.uestc.getthecourse.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 利用DigestUtils提供的MD5算法进行加密
 * 两次加密过程：
 * 1：用户上传密码，通过服务器本地的slat进行一次加密。
 * 2：将加密结果利用数据库的slat进行二次加密。
 * 这样即便是数据库的slat被盗，盗窃者也不知道服务器本地的slat，因此也无法获取密码
 */
public class MD5Util {

    private static final String SALT = "1a2b3c4d";

    /**
     * 加密算法
     *
     * @param str
     * @return
     */
    private static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 返回两次加密结果
     * @param inputPassword
     * @param salt
     * @return
     */
    public static String inputPassToDBPass(String inputPassword, String salt) {
        return formPassToDBPass(inputPassToFormPass(inputPassword), salt);
    }

    /**
     * 将用户输入的密码通过本地SALT转化为一次加密的表单式密码
     *
     * @param inputPassword
     * @return
     */
    private static String inputPassToFormPass(String inputPassword) {
        String pas = SALT.charAt(0) + SALT.charAt(1) + inputPassword + SALT.charAt(4) + SALT.charAt(5);
        return md5(pas);
    }

    /**
     * 将加密一次过后的表单密码通过数据库的salt进行二次加密
     *
     * @param formPassword
     * @param salt
     * @return
     */
    private static String formPassToDBPass(String formPassword, String salt) {
        String pas = salt.charAt(0) + salt.charAt(1) + formPassword + salt.charAt(4) + salt.charAt(5);
        return md5(pas);
    }
}
