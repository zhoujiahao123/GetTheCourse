package com.uestc.getthecourse.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;
import com.uestc.getthecourse.redis.BloomFilterKey;
import com.uestc.getthecourse.redis.KeyPrefix;
import com.uestc.getthecourse.redis.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;

public class BloomFilterHelper<T> {

    private int numHashFunctions;//所需要的hash函数的个数
    private int bitSize;//所需要的布隆过滤器数组的大小
    private Funnel<T> funnel;
    private static volatile BloomFilterHelper helper;


    /**
     * 以单例模式实现布隆过滤器
     *
     * @return
     */
    public static BloomFilterHelper getBloomFilterHelper() {
        if (helper == null) {
            synchronized (BloomFilterHelper.class) {
                if (helper == null) {
                    helper = new BloomFilterHelper<>((Funnel<String>) (from,
                                                                       into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
                }
            }
        }
        return helper;
    }

    private static final String KEY = "BLOOM_FILTER";


    /**
     * fpp :可以容忍的误判率
     * expectedInsertions ： 输入对象的个数
     *
     * @param funnel
     * @param expectedInsertions
     * @param fpp
     */
    private BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel 不能为空");
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];//每一个hash函数都会得到一个值
        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();//计算传入数据的hash值。
        int hash1 = (int) hash64;//取低32位
        int hash2 = (int) (hash64 >>> 32);//再取高32位
        //计算hash函数，简单的使用hash1 + i*hash2来实现不同的hash函数
        for (int i = 0; i < numHashFunctions; ++i) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i] = nextHash % bitSize;
        }
        return offset;
    }

    /**
     * 根据公式计算hash的输出范围，也就是确定位数组的大小
     *
     * @param expectedInsertions
     * @param fpp
     * @return
     */
    private int optimalNumOfBits(long expectedInsertions, double fpp) {
        if (fpp == 0) fpp = Double.MIN_VALUE;
        return (int) (-expectedInsertions * Math.log(fpp) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算所需要的hash函数值
     * 公式：k = ln2 * m/n
     *
     * @param n
     * @param m
     * @return
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

}
