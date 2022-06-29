package com.tset.releasemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tset.releasemanager.dto.ServiceInfo;
import com.tset.releasemanager.service.ICheckServiceVersion;
import com.tset.releasemanager.service.IRegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ReleaseManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICheckServiceVersion checkServiceVersion;

    @MockBean
    private IRegisterService registerService;

    @Test
    public void test_get_services() throws Exception {
        when(registerService.deployedServices(any())).thenReturn(Set.of(ServiceInfo.builder().name("Service A").version(1).build()));
        this.mockMvc.perform(get("/services")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("systemVersion", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"name\":\"Service A\",\"version\":1}]"));
    }

    @Test
    public void test_get_services_empty_response() throws Exception {
        when(registerService.deployedServices(any())).thenReturn(Collections.emptySet());
        this.mockMvc.perform(get("/services")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("systemVersion", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void test_deploy_service_new_version() throws Exception {
        when(checkServiceVersion.hasTheSameVersion(any())).thenReturn(Boolean.FALSE);
        when(registerService.lastSystemVersionNumber()).thenReturn(1);
        this.mockMvc.perform(post("/deploy")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ServiceInfo.builder().name("ServiceA").version(1).build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("1"));
        verify(registerService, times(1)).register(any());
    }

    @Test
    public void test_deploy_service_same_version() throws Exception {
        when(checkServiceVersion.hasTheSameVersion(any())).thenReturn(Boolean.TRUE);
        when(registerService.lastSystemVersionNumber()).thenReturn(1);
        this.mockMvc.perform(post("/deploy")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ServiceInfo.builder().name("ServiceA").version(1).build())))
                .andDo(print())
                .andExpect(status().isOk());
        verify(registerService, times(0)).register(any());
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
