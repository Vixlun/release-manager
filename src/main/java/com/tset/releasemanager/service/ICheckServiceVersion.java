package com.tset.releasemanager.service;

import com.tset.releasemanager.dto.ServiceInfo;

public interface ICheckServiceVersion {
    boolean hasTheSameVersion(ServiceInfo serviceInfo);
}
