package com.uestc.getthecourse.redis;

public class UserKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public UserKey(int expire, String prefix) {
        super(expire, prefix);
    }

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey user_token = new UserKey(TOKEN_EXPIRE, "token");
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
    
}
