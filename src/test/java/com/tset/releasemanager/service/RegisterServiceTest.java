package com.tset.releasemanager.service;

import com.tset.releasemanager.dto.ServiceInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterServiceTest {
    @Test
    public void test_register_one_service() {
        RegisterServiceImpl serviceImpl = new RegisterServiceImpl();
        serviceImpl.register(ServiceInfo.builder().name("serviceA").version(0).build());
        Assertions.assertEquals(1, serviceImpl.lastSystemVersionNumber());
    }

    @Test
    public void test_register_same_service_with_different_version() {
        RegisterServiceImpl serviceImpl = new RegisterServiceImpl();
        serviceImpl.register(ServiceInfo.builder().name("serviceA").version(0).build());
        serviceImpl.register(ServiceInfo.builder().name("serviceA").version(1).build());
        Assertions.assertEquals(2, serviceImpl.lastSystemVersionNumber());
    }

    @Test
    public void test_register_same_service_with_same_version() {
        RegisterServiceImpl serviceImpl = new RegisterServiceImpl();
        serviceImpl.register(ServiceInfo.builder().name("serviceA").version(0).build());
        serviceImpl.register(ServiceInfo.builder().name("serviceA").version(0).build());
        Assertions.assertEquals(1, serviceImpl.lastSystemVersionNumber());
    }
}
