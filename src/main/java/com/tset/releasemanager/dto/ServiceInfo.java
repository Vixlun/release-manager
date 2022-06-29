package com.tset.releasemanager.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ServiceInfo {
    private String name;
    private int version;
}
