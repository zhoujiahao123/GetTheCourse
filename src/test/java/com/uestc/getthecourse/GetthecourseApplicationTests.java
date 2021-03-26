package com.uestc.getthecourse;

import com.uestc.getthecourse.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GetthecourseApplicationTests {

    @Test
    public void testPassword(){
        System.out.println(MD5Util.inputPassToDBPass("lanlan520","1a2b3c"));
    }
    @Test
    void contextLoads() {

    }

}
