package com.tset.releasemanager.service;

import com.tset.releasemanager.dto.ServiceInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckServiceVersionTest {

    @Test
    public void test_checkServiceVersio() {
        CheckServiceVersionImpl serviceImpl = new CheckServiceVersionImpl();
        Assertions.assertFalse(serviceImpl.hasTheSameVersion(ServiceInfo.builder().name("serviceA").version(0).build()));
    }

    @Test
    public void test_checkServiceVersion_same_service_version() {
        CheckServiceVersionImpl serviceImpl = new CheckServiceVersionImpl();
        ServiceInfo serviceA = ServiceInfo.builder().name("serviceA").version(0).build();
        Assertions.assertFalse(serviceImpl.hasTheSameVersion(serviceA)); //first attempt -> put to map
        Assertions.assertTrue(serviceImpl.hasTheSameVersion(serviceA)); //second attempt -> already exist
    }
}
