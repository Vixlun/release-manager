package com.tset.releasemanager.service;

import com.tset.releasemanager.dto.ServiceInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckServiceVersionImpl implements ICheckServiceVersion {
    private final Map<String, Integer> mapOfService = new HashMap<>();

    @Override
    public boolean hasTheSameVersion(ServiceInfo service) {
        if (mapOfService.containsKey(service.getName())) {
            return mapOfService.get(service.getName()).equals(service.getVersion());
        } else {
            //new version
            mapOfService.put(service.getName(), service.getVersion());
            return false;
        }
    }
}
