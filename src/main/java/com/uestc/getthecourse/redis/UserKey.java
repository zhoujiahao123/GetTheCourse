package com.uestc.getthecourse.redis;

public class UserKey extends BasePrefix {
    public UserKey(String prefix) {
        super(prefix);
    }
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    public static UserKey user_token = new UserKey("token");
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
