package com.tset.releasemanager.service;

import com.tset.releasemanager.dto.ServiceInfo;

import java.util.Set;

public interface IRegisterService {
    void register(ServiceInfo service);
    Set<ServiceInfo> deployedServices(Integer systemVersion);
    int lastSystemVersionNumber();
}
