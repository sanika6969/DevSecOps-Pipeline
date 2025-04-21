package com.javaproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = DatabaseServiceProjectApplication.class)
@ActiveProfiles("test")
class DatabaseServiceProjectApplicationTests {

    @Test
    void contextLoads() {
        // Basic context loading test
    }
}
