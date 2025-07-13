package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.OutingRegistration;
import com.neusoft.coreserver.entity.PageResponseBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface OutingRegistrationService {
    PageResponseBean<List<OutingRegistration>> page(@RequestBody Map<String, Object> request);

    int update(OutingRegistration outingRegistration);
}
