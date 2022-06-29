package com.tset.releasemanager.controller;

import com.tset.releasemanager.dto.ServiceInfo;
import com.tset.releasemanager.service.ICheckServiceVersion;
import com.tset.releasemanager.service.IRegisterService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ReleaseManagerController {

    private final ICheckServiceVersion checkServiceVersion;
    private final IRegisterService registerService;

    public ReleaseManagerController(ICheckServiceVersion checkServiceVersion, IRegisterService registerService) {
        this.checkServiceVersion = checkServiceVersion;
        this.registerService = registerService;
    }

    @GetMapping("/services")
    public Set<ServiceInfo> deployedServices(@RequestParam int systemVersion) {
        return registerService.deployedServices(systemVersion);
    }

    @PostMapping("/deploy")
    public int deployService(@RequestBody ServiceInfo serviceInfo) {
        if(!checkServiceVersion.hasTheSameVersion(serviceInfo)) {
            registerService.register(serviceInfo);
        }
        return registerService.lastSystemVersionNumber();
    }

}
