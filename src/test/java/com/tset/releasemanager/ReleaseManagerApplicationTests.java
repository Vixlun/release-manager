package com.tset.releasemanager;

import com.tset.releasemanager.controller.ReleaseManagerController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class ReleaseManagerApplicationTests {

    @Autowired
    private ReleaseManagerController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }



}
