package com.uestc.getthecourse.redis;

public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();


}
