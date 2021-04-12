package com.uestc.getthecourse.redis;

public class BloomFilterKey extends BasePrefix {
    public BloomFilterKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public BloomFilterKey(String prefix) {
        super(prefix);
    }
    public static BloomFilterKey bloomFilterKey = new BloomFilterKey(0,"BLOOM_FIlTER");
}
