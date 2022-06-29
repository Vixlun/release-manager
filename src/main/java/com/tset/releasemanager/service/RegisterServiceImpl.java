package com.tset.releasemanager.service;

import com.tset.releasemanager.dto.ServiceInfo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

@Service
public class RegisterServiceImpl implements IRegisterService {
    private final TreeMap<Integer, Set<ServiceInfo>> systemMap = new TreeMap<>();

    @Override
    public void register(ServiceInfo service) {
        if(systemMap.isEmpty()) {
            systemMap.put(1, Set.of(service));
        } else if(serviceHasChanged(service)){
            Set<ServiceInfo> setOfServices = new HashSet<>(systemMap.lastEntry().getValue());
            setOfServices.remove(service);
            setOfServices.add(service);
            systemMap.put(lastSystemVersionNumber() + 1, setOfServices);
        }
    }

    @Override
    public Set<ServiceInfo> deployedServices(Integer systemVersion) {
        return systemMap.get(systemVersion);
    }

    @Override
    public int lastSystemVersionNumber() {
        return systemMap.lastKey();
    }

    private boolean serviceHasChanged(ServiceInfo service) {
        final var setOfServices = systemMap.lastEntry().getValue();
        return !setOfServices.contains(service);
    }
}
